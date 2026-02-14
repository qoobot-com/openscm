package com.qoobot.openscm.integration.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qoobot.openscm.integration.entity.PaymentIntegration;

import java.math.BigDecimal;

/**
 * 支付集成服务接口
 */
public interface PaymentIntegrationService extends IService<PaymentIntegration> {

    /**
     * 分页查询支付记录
     */
    Page<PaymentIntegration> selectPage(Page<PaymentIntegration> page, String paymentNo, String paymentPlatform,
                                         String paymentMethod, String paymentStatus);

    /**
     * 创建支付订单
     */
    String createPaymentOrder(String businessType, String businessNo, BigDecimal amount, String paymentPlatform, String paymentMethod);

    /**
     * 处理支付回调
     */
    Boolean handlePaymentCallback(String thirdPartyNo, String paymentStatus);

    /**
     * 查询支付状态
     */
    String queryPaymentStatus(String paymentNo);

    /**
     * 申请退款
     */
    Boolean applyRefund(String paymentNo, BigDecimal refundAmount);

    /**
     * 查询退款状态
     */
    String queryRefundStatus(String refundNo);
}
