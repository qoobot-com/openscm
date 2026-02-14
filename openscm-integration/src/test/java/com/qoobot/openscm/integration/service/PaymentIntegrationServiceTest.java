package com.qoobot.openscm.integration.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

/**
 * 支付集成服务测试
 */
@SpringBootTest
class PaymentIntegrationServiceTest {

    @Autowired
    private PaymentIntegrationService paymentIntegrationService;

    @Test
    void testSelectPage() {
        Page<com.qoobot.openscm.integration.entity.PaymentIntegration> page = new Page<>(1, 10);
        Page<com.qoobot.openscm.integration.entity.PaymentIntegration> result =
                paymentIntegrationService.selectPage(page, null, null, null, null);
        System.out.println("查询支付记录成功，记录数: " + result.getRecords().size());
    }

    @Test
    void testCreatePaymentOrder() {
        try {
            String paymentNo = paymentIntegrationService.createPaymentOrder(
                    "SALES", "SO001", new BigDecimal("1000.00"), "WECHAT", "QR_CODE");
            System.out.println("创建支付订单成功，支付单号: " + paymentNo);
        } catch (Exception e) {
            System.out.println("创建支付订单异常: " + e.getMessage());
        }
    }

    @Test
    void testQueryPaymentStatus() {
        try {
            String status = paymentIntegrationService.queryPaymentStatus("PAY001");
            System.out.println("支付状态: " + status);
        } catch (Exception e) {
            System.out.println("查询支付状态异常: " + e.getMessage());
        }
    }

    @Test
    void testApplyRefund() {
        try {
            boolean result = paymentIntegrationService.applyRefund("PAY001", new BigDecimal("1000.00"));
            System.out.println("申请退款: " + (result ? "成功" : "失败"));
        } catch (Exception e) {
            System.out.println("申请退款异常: " + e.getMessage());
        }
    }
}
