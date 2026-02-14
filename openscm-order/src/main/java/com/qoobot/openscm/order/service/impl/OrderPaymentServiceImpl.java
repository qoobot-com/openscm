package com.qoobot.openscm.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.openscm.order.entity.OrderPayment;
import com.qoobot.openscm.order.mapper.OrderPaymentMapper;
import com.qoobot.openscm.order.service.OrderPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单支付服务实现
 */
@Service
public class OrderPaymentServiceImpl extends ServiceImpl<OrderPaymentMapper, OrderPayment> implements OrderPaymentService {

    @Autowired
    private OrderPaymentMapper orderPaymentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPayment(OrderPayment payment) {
        // 生成支付编号
        String paymentNo = generatePaymentNo();
        payment.setPaymentNo(paymentNo);
        payment.setStatus(0); // 待支付
        
        orderPaymentMapper.insert(payment);
        return payment.getId();
    }

    @Override
    public List<OrderPayment> getPaymentsByOrderId(Long orderId) {
        return orderPaymentMapper.selectByOrderId(orderId);
    }

    @Override
    public OrderPayment getByPaymentNo(String paymentNo) {
        return orderPaymentMapper.selectByPaymentNo(paymentNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmPayment(Long paymentId) {
        OrderPayment payment = orderPaymentMapper.selectById(paymentId);
        if (payment == null) {
            throw new RuntimeException("支付记录不存在");
        }
        if (payment.getStatus() != 0) {
            throw new RuntimeException("支付状态不正确");
        }
        payment.setStatus(1); // 已支付
        payment.setPaymentTime(java.time.LocalDateTime.now());
        orderPaymentMapper.updateById(payment);
    }

    private String generatePaymentNo() {
        return "PAY" + System.currentTimeMillis();
    }
}
