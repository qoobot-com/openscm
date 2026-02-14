package com.qoobot.openscm.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.openscm.order.entity.SalesOrder;
import com.qoobot.openscm.order.entity.SalesOrderItem;
import com.qoobot.openscm.order.mapper.SalesOrderMapper;
import com.qoobot.openscm.order.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 销售订单服务实现
 */
@Service
public class SalesOrderServiceImpl extends ServiceImpl<SalesOrderMapper, SalesOrder> implements SalesOrderService {

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private com.qoobot.openscm.order.mapper.SalesOrderItemMapper salesOrderItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrder(SalesOrder order, List<SalesOrderItem> items) {
        // 生成订单编号
        String orderNo = generateOrderNo();
        order.setOrderNo(orderNo);
        order.setStatus(0); // 草稿状态
        
        // 计算订单金额
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal discountAmount = BigDecimal.ZERO;
        for (SalesOrderItem item : items) {
            BigDecimal itemAmount = item.getUnitPrice().multiply(item.getQuantity());
            BigDecimal itemDiscount = item.getDiscountRate() != null ? 
                itemAmount.multiply(item.getDiscountRate()).divide(new BigDecimal("100")) : BigDecimal.ZERO;
            item.setAmount(itemAmount);
            item.setDiscountAmount(itemDiscount);
            item.setPaidAmount(itemAmount.subtract(itemDiscount));
            item.setShippedQuantity(BigDecimal.ZERO);
            
            amount = amount.add(item.getPaidAmount());
            discountAmount = discountAmount.add(itemDiscount);
        }
        order.setAmount(amount);
        order.setDiscountAmount(discountAmount);
        order.setPaidAmount(amount.subtract(discountAmount));
        
        // 保存订单
        salesOrderMapper.insert(order);
        
        // 保存订单明细
        for (SalesOrderItem item : items) {
            item.setOrderId(order.getId());
            salesOrderItemMapper.insert(item);
        }
        
        return order.getId();
    }

    @Override
    public IPage<SalesOrder> orderPage(Page<SalesOrder> page, String orderNo, Long customerId, 
                                         Integer status, String startDate, String endDate) {
        return salesOrderMapper.selectOrderPage(page, orderNo, customerId, status, startDate, endDate);
    }

    @Override
    public SalesOrder getOrderDetail(Long orderId) {
        SalesOrder order = salesOrderMapper.selectById(orderId);
        if (order != null) {
            List<SalesOrderItem> items = salesOrderItemMapper.selectByOrderId(orderId);
            order.setRemark(order.getRemark()); // 这里可以用其他方式存储明细
        }
        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmOrder(Long orderId) {
        SalesOrder order = salesOrderMapper.selectById(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        if (order.getStatus() != 0) {
            throw new RuntimeException("订单状态不正确，无法确认");
        }
        order.setStatus(1); // 待确认
        salesOrderMapper.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(Long orderId) {
        SalesOrder order = salesOrderMapper.selectById(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        if (order.getStatus() >= 3) {
            throw new RuntimeException("订单已开始生产或发货，无法取消");
        }
        order.setStatus(6); // 已取消
        salesOrderMapper.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatus(Long orderId, Integer status) {
        SalesOrder order = salesOrderMapper.selectById(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        order.setStatus(status);
        salesOrderMapper.updateById(order);
    }

    @Override
    public Map<String, Object> orderStatistics() {
        Map<String, Object> result = new HashMap<>();
        
        // 总订单数
        Long totalOrders = salesOrderMapper.selectCount(null);
        result.put("totalOrders", totalOrders);
        
        // 各状态订单数
        Map<Integer, Long> statusCount = new HashMap<>();
        for (int i = 0; i <= 6; i++) {
            Long count = salesOrderMapper.selectCount(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<SalesOrder>()
                    .eq(SalesOrder::getStatus, i)
            );
            statusCount.put(i, count);
        }
        result.put("statusCount", statusCount);
        
        // 订单总金额
        List<SalesOrder> orders = salesOrderMapper.selectList(null);
        BigDecimal totalAmount = orders.stream()
            .map(SalesOrder::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        result.put("totalAmount", totalAmount);
        
        return result;
    }

    private String generateOrderNo() {
        // 简单的订单编号生成逻辑，实际应该根据业务需求实现
        return "SO" + System.currentTimeMillis();
    }
}
