package com.qoobot.openscm.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.common.core.domain.Result;
import com.qoobot.openscm.finance.entity.PaymentOrder;
import com.qoobot.openscm.finance.service.PaymentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 收付款单控制器
 */
@RestController
@RequestMapping("/finance/payment")
public class PaymentOrderController {

    @Autowired
    private PaymentOrderService paymentOrderService;

    /**
     * 创建收付款单
     */
    @PostMapping("/create")
    public Result<Long> createPayment(@RequestBody PaymentOrder payment) {
        Long paymentId = paymentOrderService.createPayment(payment);
        return Result.success(paymentId);
    }

    /**
     * 分页查询收付款单
     */
    @GetMapping("/page")
    public Result<IPage<PaymentOrder>> paymentPage(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String paymentNo,
            @RequestParam(required = false) Integer orderType,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        Page<PaymentOrder> page = new Page<>(current, size);
        IPage<PaymentOrder> result = paymentOrderService.paymentPage(
            page, paymentNo, orderType, status, startDate, endDate);
        return Result.success(result);
    }

    /**
     * 审核收付款单
     */
    @PostMapping("/approve/{paymentId}")
    public Result<Void> approvePayment(@PathVariable Long paymentId, 
                                       @RequestParam Boolean approve,
                                       @RequestParam(required = false) String approveRemark) {
        paymentOrderService.approvePayment(paymentId, approve, approveRemark);
        return Result.success();
    }

    /**
     * 收付款统计
     */
    @GetMapping("/statistics")
    public Result<Map<String, Object>> statistics() {
        Map<String, Object> statistics = paymentOrderService.statistics();
        return Result.success(statistics);
    }
}
