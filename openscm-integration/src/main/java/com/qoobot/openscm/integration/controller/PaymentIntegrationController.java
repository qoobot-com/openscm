package com.qoobot.openscm.integration.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.common.result.Result;
import com.qoobot.openscm.integration.entity.PaymentIntegration;
import com.qoobot.openscm.integration.service.PaymentIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * 支付集成控制器
 */
@Tag(name = "支付集成管理", description = "支付集成相关接口")
@RestController
@RequestMapping("/api/integration/payment")
@RequiredArgsConstructor
@Validated
public class PaymentIntegrationController {

    private final PaymentIntegrationService paymentIntegrationService;

    @Operation(summary = "分页查询支付记录")
    @GetMapping("/page")
    public Result<Page<PaymentIntegration>> page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String paymentNo,
            @RequestParam(required = false) String paymentPlatform,
            @RequestParam(required = false) String paymentMethod,
            @RequestParam(required = false) String paymentStatus) {
        Page<PaymentIntegration> page = new Page<>(current, size);
        return Result.success(paymentIntegrationService.selectPage(page, paymentNo, paymentPlatform, paymentMethod, paymentStatus));
    }

    @Operation(summary = "根据ID查询支付记录")
    @GetMapping("/{id}")
    public Result<PaymentIntegration> getById(@PathVariable Long id) {
        return Result.success(paymentIntegrationService.getById(id));
    }

    @Operation(summary = "创建支付订单")
    @PostMapping("/create")
    public Result<String> createPaymentOrder(
            @RequestParam String businessType,
            @RequestParam String businessNo,
            @RequestParam BigDecimal amount,
            @RequestParam String paymentPlatform,
            @RequestParam String paymentMethod) {
        return Result.success(paymentIntegrationService.createPaymentOrder(businessType, businessNo, amount, paymentPlatform, paymentMethod));
    }

    @Operation(summary = "处理支付回调")
    @PostMapping("/callback")
    public Result<Boolean> handlePaymentCallback(
            @RequestParam String thirdPartyNo,
            @RequestParam String paymentStatus) {
        return Result.success(paymentIntegrationService.handlePaymentCallback(thirdPartyNo, paymentStatus));
    }

    @Operation(summary = "查询支付状态")
    @GetMapping("/status")
    public Result<String> queryPaymentStatus(@RequestParam String paymentNo) {
        return Result.success(paymentIntegrationService.queryPaymentStatus(paymentNo));
    }

    @Operation(summary = "申请退款")
    @PostMapping("/refund")
    public Result<Boolean> applyRefund(
            @RequestParam String paymentNo,
            @RequestParam BigDecimal refundAmount) {
        return Result.success(paymentIntegrationService.applyRefund(paymentNo, refundAmount));
    }

    @Operation(summary = "查询退款状态")
    @GetMapping("/refund-status")
    public Result<String> queryRefundStatus(@RequestParam String refundNo) {
        return Result.success(paymentIntegrationService.queryRefundStatus(refundNo));
    }
}
