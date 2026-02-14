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
import com.qoobot.openscm.purchase.dto.PurchaseInboundDTO;
import com.qoobot.openscm.purchase.dto.PurchaseInboundItemDTO;
import com.qoobot.openscm.purchase.entity.PurchaseInbound;
import com.qoobot.openscm.purchase.entity.PurchaseInboundItem;
import com.qoobot.openscm.purchase.entity.PurchaseOrder;
import com.qoobot.openscm.purchase.entity.PurchaseOrderItem;
import com.qoobot.openscm.purchase.mapper.PurchaseInboundItemMapper;
import com.qoobot.openscm.purchase.mapper.PurchaseInboundMapper;
import com.qoobot.openscm.purchase.mapper.PurchaseOrderItemMapper;
import com.qoobot.openscm.purchase.mapper.PurchaseOrderMapper;
import com.qoobot.openscm.purchase.service.PurchaseInboundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 采购入库服务实现
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Service
@RequiredArgsConstructor
public class PurchaseInboundServiceImpl extends ServiceImpl<PurchaseInboundMapper, PurchaseInbound> implements PurchaseInboundService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final PurchaseInboundItemMapper inboundItemMapper;
    private final PurchaseOrderMapper orderMapper;
    private final PurchaseOrderItemMapper orderItemMapper;

    @Override
    public PageResult<PurchaseInbound> queryPage(Integer current, Integer size, java.util.Map<String, Object> params) {
        Page<PurchaseInbound> page = new Page<>(current, size);
        LambdaQueryWrapper<PurchaseInbound> wrapper = new LambdaQueryWrapper<>();

        if (params != null) {
            String inboundNo = (String) params.get("inboundNo");
            if (StrUtil.isNotBlank(inboundNo)) {
                wrapper.like(PurchaseInbound::getInboundNo, inboundNo);
            }

            Long orderId = (Long) params.get("orderId");
            if (orderId != null) {
                wrapper.eq(PurchaseInbound::getOrderId, orderId);
            }

            Integer status = (Integer) params.get("status");
            if (status != null) {
                wrapper.eq(PurchaseInbound::getStatus, status);
            }
        }

        wrapper.orderByDesc(PurchaseInbound::getCreateTime);
        IPage<PurchaseInbound> iPage = page(page, wrapper);

        return PageResult.of(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createInbound(PurchaseInboundDTO dto) {
        // 验证采购订单
        PurchaseOrder order = orderMapper.selectById(dto.getOrderId());
        if (order == null) {
            throw new BusinessException("采购订单不存在");
        }

        PurchaseInbound inbound = new PurchaseInbound();
        BeanUtil.copyProperties(dto, inbound);

        // 生成入库单编号
        String inboundNo = "IB" + LocalDate.now().format(DATE_FORMATTER) + IdUtil.getSnowflakeNextIdStr().substring(12);
        inbound.setInboundNo(inboundNo);

        // 设置订单信息
        inbound.setOrderNo(order.getOrderNo());
        inbound.setSupplierId(order.getSupplierId());
        inbound.setSupplierName(order.getSupplierName());

        // 设置初始状态
        inbound.setStatus(dto.getNeedQualityCheck() == 1 ? 1 : 0); // 需要质检则状态为待质检，否则为待入库

        save(inbound);

        // 保存入库明细
        saveInboundItems(inbound.getId(), inboundNo, dto.getItems());

        return inbound.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean qualityCheck(Long id, Integer result, String remark) {
        PurchaseInbound inbound = getById(id);
        if (inbound == null) {
            throw new BusinessException("入库单不存在");
        }

        if (inbound.getNeedQualityCheck() != 1) {
            throw new BusinessException("该入库单不需要质检");
        }

        if (inbound.getStatus() != 1 && inbound.getStatus() != 2) {
            throw new BusinessException("当前状态不允许质检");
        }

        // 设置质检信息
        inbound.setQualityInspectorId(SecurityUtils.getUserId());
        inbound.setQualityInspectorName(SecurityUtils.getUsername());
        inbound.setQualityCheckDate(LocalDate.now());
        inbound.setQualityResult(result);
        inbound.setQualityRemark(remark);

        // 更新状态
        if (result == 1) {
            inbound.setStatus(3); // 质检合格，待入库
        } else {
            inbound.setStatus(4); // 质检不合格
        }

        return updateById(inbound);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean inbound(Long id) {
        PurchaseInbound inbound = getById(id);
        if (inbound == null) {
            throw new BusinessException("入库单不存在");
        }

        // 待入库或质检合格状态可以入库
        if (inbound.getStatus() != 0 && inbound.getStatus() != 3) {
            throw new BusinessException("当前状态不允许入库");
        }

        // 设置入库人信息
        inbound.setInbounderId(SecurityUtils.getUserId());
        inbound.setInbounderName(SecurityUtils.getUsername());
        inbound.setInboundDate(LocalDate.now());
        inbound.setStatus(5); // 已完成

        // TODO: 更新库存（需要调用库存服务）
        // updateInventory(inbound);

        // 更新订单收货数量
        updateOrderReceivedQuantity(inbound);

        return updateById(inbound);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean completeInbound(Long id) {
        PurchaseInbound inbound = getById(id);
        if (inbound == null) {
            throw new BusinessException("入库单不存在");
        }

        if (inbound.getStatus() != 5) {
            throw new BusinessException("当前状态不允许完成");
        }

        // 入库单已完成状态保持不变，这里可以做后续处理
        return true;
    }

    private void saveInboundItems(Long inboundId, String inboundNo, List<PurchaseInboundItemDTO> itemDTOs) {
        if (itemDTOs == null || itemDTOs.isEmpty()) {
            return;
        }

        for (PurchaseInboundItemDTO itemDTO : itemDTOs) {
            PurchaseOrderItem orderItem = orderItemMapper.selectById(itemDTO.getOrderItemId());
            if (orderItem == null) {
                throw new BusinessException("订单明细不存在");
            }

            PurchaseInboundItem item = new PurchaseInboundItem();
            BeanUtil.copyProperties(itemDTO, item);
            item.setInboundId(inboundId);
            item.setInboundNo(inboundNo);
            item.setMaterialCode(orderItem.getMaterialCode());
            item.setMaterialName(orderItem.getMaterialName());
            item.setSpecification(orderItem.getSpecification());
            item.setUnit(orderItem.getUnit());
            item.setOrderQuantity(orderItem.getQuantity());
            item.setQualifiedQuantity(itemDTO.getInboundQuantity());
            item.setUnqualifiedQuantity(BigDecimal.ZERO);

            inboundItemMapper.insert(item);
        }
    }

    private void updateOrderReceivedQuantity(PurchaseInbound inbound) {
        LambdaQueryWrapper<PurchaseInboundItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PurchaseInboundItem::getInboundId, inbound.getId());
        List<PurchaseInboundItem> inboundItems = inboundItemMapper.selectList(wrapper);

        for (PurchaseInboundItem inboundItem : inboundItems) {
            PurchaseOrderItem orderItem = orderItemMapper.selectById(inboundItem.getOrderItemId());
            if (orderItem != null) {
                BigDecimal currentQuantity = orderItem.getReceivedQuantity() != null
                        ? orderItem.getReceivedQuantity()
                        : BigDecimal.ZERO;
                BigDecimal newQuantity = currentQuantity.add(inboundItem.getQualifiedQuantity());
                orderItem.setReceivedQuantity(newQuantity);

                // 更新收货状态
                BigDecimal orderQuantity = orderItem.getQuantity();
                if (newQuantity.compareTo(orderQuantity) >= 0) {
                    orderItem.setReceiveStatus(2); // 全部收货
                } else if (newQuantity.compareTo(BigDecimal.ZERO) > 0) {
                    orderItem.setReceiveStatus(1); // 部分收货
                }

                orderItemMapper.updateById(orderItem);
            }
        }

        // 检查订单是否全部收货，如果是则更新订单状态
        checkAndUpdateOrderStatus(inbound.getOrderId());
    }

    private void checkAndUpdateOrderStatus(Long orderId) {
        LambdaQueryWrapper<PurchaseOrderItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PurchaseOrderItem::getOrderId, orderId);
        List<PurchaseOrderItem> orderItems = orderItemMapper.selectList(wrapper);

        boolean allReceived = orderItems.stream()
                .allMatch(item -> item.getReceiveStatus() == 2);

        if (allReceived) {
            PurchaseOrder order = orderMapper.selectById(orderId);
            if (order != null && order.getStatus() == 3) { // 已收货状态
                // 这里可以自动完成订单，或者等待人工确认
                // order.setStatus(4); // 已完成
                // orderMapper.updateById(order);
            }
        }
    }
}
