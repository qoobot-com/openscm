package com.qoobot.openscm.order.controller;

import com.qoobot.common.core.domain.Result;
import com.qoobot.openscm.order.entity.OrderPayment;
import com.qoobot.openscm.order.service.OrderPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单支付控制器
 */
@RestController
@RequestMapping("/order/payment")
public class OrderPaymentController {

    @Autowired
    private OrderPaymentService orderPaymentService;

    /**
     * 创建支付记录
     */
    @PostMapping("/create")
    public Result<Long> createPayment(@RequestBody OrderPayment payment) {
        Long paymentId = orderPaymentService.createPayment(payment);
        return Result.success(paymentId);
    }

    /**
     * 根据订单ID查询支付记录
     */
    @GetMapping("/order/{orderId}")
    public Result<List<OrderPayment>> getPaymentsByOrderId(@PathVariable Long orderId) {
        List<OrderPayment> payments = orderPaymentService.getPaymentsByOrderId(orderId);
        return Result.success(payments);
    }

    /**
     * 根据支付编号查询
     */
    @GetMapping("/paymentNo/{paymentNo}")
    public Result<OrderPayment> getByPaymentNo(@PathVariable String paymentNo) {
        OrderPayment payment = orderPaymentService.getByPaymentNo(paymentNo);
        return Result.success(payment);
    }

    /**
     * 确认支付
     */
    @PostMapping("/confirm/{paymentId}")
    public Result<Void> confirmPayment(@PathVariable Long paymentId) {
        orderPaymentService.confirmPayment(paymentId);
        return Result.success();
    }
}
