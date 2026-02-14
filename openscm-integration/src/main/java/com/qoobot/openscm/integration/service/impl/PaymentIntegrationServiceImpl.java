package com.qoobot.openscm.integration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.openscm.common.exception.BusinessException;
import com.qoobot.openscm.common.util.SecurityUtils;
import com.qoobot.openscm.integration.entity.PaymentIntegration;
import com.qoobot.openscm.integration.mapper.PaymentIntegrationMapper;
import com.qoobot.openscm.integration.service.PaymentIntegrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 支付集成服务实现
 */
@Slf4j
@Service
public class PaymentIntegrationServiceImpl extends ServiceImpl<PaymentIntegrationMapper, PaymentIntegration> implements PaymentIntegrationService {

    @Override
    public Page<PaymentIntegration> selectPage(Page<PaymentIntegration> page, String paymentNo, String paymentPlatform,
                                               String paymentMethod, String paymentStatus) {
        LambdaQueryWrapper<PaymentIntegration> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PaymentIntegration::getTenantId, SecurityUtils.getTenantId());
        if (paymentNo != null && !paymentNo.isEmpty()) {
            wrapper.eq(PaymentIntegration::getPaymentNo, paymentNo);
        }
        if (paymentPlatform != null && !paymentPlatform.isEmpty()) {
            wrapper.eq(PaymentIntegration::getPaymentPlatform, paymentPlatform);
        }
        if (paymentMethod != null && !paymentMethod.isEmpty()) {
            wrapper.eq(PaymentIntegration::getPaymentMethod, paymentMethod);
        }
        if (paymentStatus != null && !paymentStatus.isEmpty()) {
            wrapper.eq(PaymentIntegration::getPaymentStatus, paymentStatus);
        }
        wrapper.orderByDesc(PaymentIntegration::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createPaymentOrder(String businessType, String businessNo, BigDecimal amount,
                                     String paymentPlatform, String paymentMethod) {
        try {
            // TODO: 实现实际的支付订单创建逻辑
            log.info("创建支付订单: 业务类型={}, 业务单号={}, 金额={}, 平台={}, 方式={}",
                    businessType, businessNo, amount, paymentPlatform, paymentMethod);

            String paymentNo = "PAY" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            PaymentIntegration payment = new PaymentIntegration();
            payment.setPaymentNo(paymentNo);
            payment.setPaymentPlatform(paymentPlatform);
            payment.setPaymentMethod(paymentMethod);
            payment.setBusinessType(businessType);
            payment.setBusinessNo(businessNo);
            payment.setAmount(amount);
            payment.setCurrency("CNY");
            payment.setPaymentStatus("PENDING");
            payment.setTenantId(SecurityUtils.getTenantId());
            this.save(payment);

            return paymentNo;
        } catch (Exception e) {
            log.error("创建支付订单失败", e);
            throw new BusinessException("创建支付订单失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean handlePaymentCallback(String thirdPartyNo, String paymentStatus) {
        try {
            LambdaQueryWrapper<PaymentIntegration> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(PaymentIntegration::getThirdPartyNo, thirdPartyNo);
            PaymentIntegration payment = this.getOne(wrapper);

            if (payment == null) {
                log.warn("支付记录不存在: thirdPartyNo={}", thirdPartyNo);
                return false;
            }

            payment.setPaymentStatus(paymentStatus);
            if ("SUCCESS".equals(paymentStatus)) {
                payment.setPaymentTime(LocalDateTime.now());
            }
            payment.setCallbackTime(LocalDateTime.now());
            this.updateById(payment);

            return true;
        } catch (Exception e) {
            log.error("处理支付回调失败", e);
            throw new BusinessException("处理支付回调失败: " + e.getMessage());
        }
    }

    @Override
    public String queryPaymentStatus(String paymentNo) {
        PaymentIntegration payment = this.lambdaQuery()
                .eq(PaymentIntegration::getPaymentNo, paymentNo)
                .one();

        if (payment == null) {
            throw new BusinessException("支付记录不存在");
        }

        try {
            // TODO: 实现实际的支付状态查询逻辑
            return payment.getPaymentStatus();
        } catch (Exception e) {
            log.error("查询支付状态失败", e);
            throw new BusinessException("查询支付状态失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean applyRefund(String paymentNo, BigDecimal refundAmount) {
        PaymentIntegration payment = this.lambdaQuery()
                .eq(PaymentIntegration::getPaymentNo, paymentNo)
                .one();

        if (payment == null) {
            throw new BusinessException("支付记录不存在");
        }

        if (!"SUCCESS".equals(payment.getPaymentStatus())) {
            throw new BusinessException("支付未成功，无法退款");
        }

        if (refundAmount.compareTo(payment.getAmount()) > 0) {
            throw new BusinessException("退款金额不能超过支付金额");
        }

        try {
            // TODO: 实现实际的退款申请逻辑
            log.info("申请退款: 支付单号={}, 退款金额={}", paymentNo, refundAmount);
            return true;
        } catch (Exception e) {
            log.error("申请退款失败", e);
            throw new BusinessException("申请退款失败: " + e.getMessage());
        }
    }

    @Override
    public String queryRefundStatus(String refundNo) {
        try {
            // TODO: 实现实际的退款状态查询逻辑
            return "SUCCESS";
        } catch (Exception e) {
            log.error("查询退款状态失败", e);
            throw new BusinessException("查询退款状态失败: " + e.getMessage());
        }
    }
}
