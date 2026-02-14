package com.qoobot.openscm.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qoobot.openscm.order.entity.OrderPayment;

import java.util.List;

/**
 * 订单支付服务接口
 */
public interface OrderPaymentService extends IService<OrderPayment> {

    /**
     * 创建支付记录
     */
    Long createPayment(OrderPayment payment);

    /**
     * 根据订单ID查询支付记录
     */
    List<OrderPayment> getPaymentsByOrderId(Long orderId);

    /**
     * 根据支付编号查询
     */
    OrderPayment getByPaymentNo(String paymentNo);

    /**
     * 确认支付
     */
    void confirmPayment(Long paymentId);
}
