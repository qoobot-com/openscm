package com.qoobot.openscm.purchase.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.openscm.common.exception.BusinessException;
import com.qoobot.openscm.common.result.PageResult;
import com.qoobot.openscm.common.util.SecurityUtils;
import com.qoobot.openscm.purchase.dto.PurchaseOrderDTO;
import com.qoobot.openscm.purchase.dto.PurchaseOrderItemDTO;
import com.qoobot.openscm.purchase.entity.PurchaseOrder;
import com.qoobot.openscm.purchase.entity.PurchaseOrderItem;
import com.qoobot.openscm.purchase.mapper.PurchaseOrderMapper;
import com.qoobot.openscm.purchase.mapper.PurchaseOrderItemMapper;
import com.qoobot.openscm.purchase.service.PurchaseOrderService;
import com.qoobot.openscm.purchase.vo.PurchaseOrderVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 采购订单服务实现
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl extends ServiceImpl<PurchaseOrderMapper, PurchaseOrder> implements PurchaseOrderService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final PurchaseOrderItemMapper orderItemMapper;

    @Override
    public PageResult<PurchaseOrderVO> queryPage(Integer current, Integer size, java.util.Map<String, Object> params) {
        Page<PurchaseOrder> page = new Page<>(current, size);
        LambdaQueryWrapper<PurchaseOrder> wrapper = new LambdaQueryWrapper<>();

        if (params != null) {
            String orderNo = (String) params.get("orderNo");
            if (StrUtil.isNotBlank(orderNo)) {
                wrapper.like(PurchaseOrder::getOrderNo, orderNo);
            }

            Long supplierId = (Long) params.get("supplierId");
            if (supplierId != null) {
                wrapper.eq(PurchaseOrder::getSupplierId, supplierId);
            }

            Integer status = (Integer) params.get("status");
            if (status != null) {
                wrapper.eq(PurchaseOrder::getStatus, status);
            }

            Long planId = (Long) params.get("planId");
            if (planId != null) {
                wrapper.eq(PurchaseOrder::getPlanId, planId);
            }
        }

        wrapper.orderByDesc(PurchaseOrder::getCreateTime);
        IPage<PurchaseOrder> iPage = page(page, wrapper);

        return PageResult.of(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(),
                iPage.getRecords().stream()
                        .map(this::convertToVO)
                        .toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrder(PurchaseOrderDTO dto) {
        // 验证供应商是否存在（这里应该调用供应商服务）
        // Supplier supplier = supplierService.getById(dto.getSupplierId());
        // if (supplier == null) {
        //     throw new BusinessException("供应商不存在");
        // }

        PurchaseOrder order = new PurchaseOrder();
        BeanUtil.copyProperties(dto, order);

        // 生成订单编号
        String orderNo = "PO" + LocalDate.now().format(DATE_FORMATTER) + IdUtil.getSnowflakeNextIdStr().substring(12);
        order.setOrderNo(orderNo);

        // 设置下单人
        order.setPurchaserId(SecurityUtils.getUserId());
        order.setPurchaserName(SecurityUtils.getUsername());

        // 计算订单金额
        BigDecimal orderAmount = calculateOrderAmount(dto.getItems());
        order.setOrderAmount(orderAmount);
        order.setPaidAmount(BigDecimal.ZERO);

        // 设置初始状态
        order.setStatus(0); // 待确认

        save(order);

        // 保存订单明细
        saveOrderItems(order.getId(), orderNo, dto.getItems());

        return order.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateOrder(Long id, PurchaseOrderDTO dto) {
        PurchaseOrder order = getById(id);
        if (order == null) {
            throw new BusinessException("采购订单不存在");
        }

        // 只有待确认状态可以修改
        if (order.getStatus() != 0) {
            throw new BusinessException("当前状态不允许修改");
        }

        BeanUtil.copyProperties(dto, order);
        order.setOrderAmount(calculateOrderAmount(dto.getItems()));

        updateById(order);

        // 删除原有明细并重新保存
        LambdaQueryWrapper<PurchaseOrderItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PurchaseOrderItem::getOrderId, id);
        orderItemMapper.delete(wrapper);

        saveOrderItems(id, order.getOrderNo(), dto.getItems());

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteOrder(Long id) {
        PurchaseOrder order = getById(id);
        if (order == null) {
            throw new BusinessException("采购订单不存在");
        }

        // 只有待确认或已取消状态可以删除
        if (order.getStatus() != 0 && order.getStatus() != 5) {
            throw new BusinessException("当前状态不允许删除");
        }

        // 删除订单明细
        LambdaQueryWrapper<PurchaseOrderItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PurchaseOrderItem::getOrderId, id);
        orderItemMapper.delete(wrapper);

        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean confirmOrder(Long id) {
        PurchaseOrder order = getById(id);
        if (order == null) {
            throw new BusinessException("采购订单不存在");
        }

        if (order.getStatus() != 0) {
            throw new BusinessException("当前状态不允许确认");
        }

        order.setStatus(1); // 已确认
        return updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean cancelOrder(Long id, String reason) {
        PurchaseOrder order = getById(id);
        if (order == null) {
            throw new BusinessException("采购订单不存在");
        }

        // 待确认或已确认状态可以取消
        if (order.getStatus() != 0 && order.getStatus() != 1) {
            throw new BusinessException("当前状态不允许取消");
        }

        order.setStatus(5); // 已取消
        if (StrUtil.isNotBlank(reason)) {
            order.setRemark(order.getRemark() + " [取消原因:" + reason + "]");
        }
        return updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean shipOrder(Long id) {
        PurchaseOrder order = getById(id);
        if (order == null) {
            throw new BusinessException("采购订单不存在");
        }

        if (order.getStatus() != 1) {
            throw new BusinessException("当前状态不允许发货");
        }

        order.setStatus(2); // 已发货
        return updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean receiveOrder(Long id) {
        PurchaseOrder order = getById(id);
        if (order == null) {
            throw new BusinessException("采购订单不存在");
        }

        if (order.getStatus() != 2) {
            throw new BusinessException("当前状态不允许收货");
        }

        order.setStatus(3); // 已收货
        order.setActualDeliveryDate(LocalDate.now());
        return updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean completeOrder(Long id) {
        PurchaseOrder order = getById(id);
        if (order == null) {
            throw new BusinessException("采购订单不存在");
        }

        if (order.getStatus() != 3) {
            throw new BusinessException("当前状态不允许完成");
        }

        order.setStatus(4); // 已完成
        return updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePaidAmount(Long id, BigDecimal amount) {
        PurchaseOrder order = getById(id);
        if (order == null) {
            return;
        }

        BigDecimal currentAmount = order.getPaidAmount() != null ? order.getPaidAmount() : BigDecimal.ZERO;
        order.setPaidAmount(currentAmount.add(amount));
        updateById(order);
    }

    public PurchaseOrderVO getOrderVOById(Long id) {
        PurchaseOrder order = getById(id);
        if (order == null) {
            return null;
        }
        return convertToVO(order);
    }

    private PurchaseOrderVO convertToVO(PurchaseOrder order) {
        PurchaseOrderVO vo = BeanUtil.copyProperties(order, PurchaseOrderVO.class);
        vo.setStatusName(getStatusName(order.getStatus()));
        vo.setPaymentMethodName(getPaymentMethodName(order.getPaymentMethod()));

        // 加载订单明细
        LambdaQueryWrapper<PurchaseOrderItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PurchaseOrderItem::getOrderId, order.getId());
        List<PurchaseOrderItem> items = orderItemMapper.selectList(wrapper);

        vo.setItems(items.stream()
                .map(this::convertItemToVO)
                .collect(Collectors.toList()));

        return vo;
    }

    private PurchaseOrderVO convertItemToVO(PurchaseOrderItem item) {
        PurchaseOrderVO.PurchaseOrderItemVO vo = new PurchaseOrderVO.PurchaseOrderItemVO();
        BeanUtil.copyProperties(item, vo);
        vo.setReceiveStatusName(getReceiveStatusName(item.getReceiveStatus()));
        return vo;
    }

    private String getStatusName(Integer status) {
        return switch (status) {
            case 0 -> "待确认";
            case 1 -> "已确认";
            case 2 -> "已发货";
            case 3 -> "已收货";
            case 4 -> "已完成";
            case 5 -> "已取消";
            default -> "未知";
        };
    }

    private String getPaymentMethodName(Integer method) {
        return switch (method) {
            case 1 -> "货到付款";
            case 2 -> "预付款";
            case 3 -> "月结";
            case 4 -> "分期付款";
            default -> "未知";
        };
    }

    private String getReceiveStatusName(Integer status) {
        return switch (status) {
            case 0 -> "未收货";
            case 1 -> "部分收货";
            case 2 -> "全部收货";
            default -> "未知";
        };
    }

    private BigDecimal calculateOrderAmount(List<PurchaseOrderItemDTO> items) {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return items.stream()
                .map(item -> {
                    BigDecimal quantity = item.getQuantity() != null ? item.getQuantity() : BigDecimal.ZERO;
                    BigDecimal price = item.getUnitPrice() != null ? item.getUnitPrice() : BigDecimal.ZERO;
                    return quantity.multiply(price);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void saveOrderItems(Long orderId, String orderNo, List<PurchaseOrderItemDTO> itemDTOs) {
        if (itemDTOs == null || itemDTOs.isEmpty()) {
            return;
        }

        for (PurchaseOrderItemDTO itemDTO : itemDTOs) {
            PurchaseOrderItem item = new PurchaseOrderItem();
            BeanUtil.copyProperties(itemDTO, item);
            item.setOrderId(orderId);
            item.setOrderNo(orderNo);
            item.setReceivedQuantity(BigDecimal.ZERO);
            item.setReceiveStatus(0);
            orderItemMapper.insert(item);
        }
    }
}
