package com.qoobot.openscm.finance.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.openscm.finance.entity.PaymentOrder;
import com.qoobot.openscm.finance.mapper.PaymentOrderMapper;
import com.qoobot.openscm.finance.service.PaymentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 收付款单服务实现
 */
@Service
public class PaymentOrderServiceImpl extends ServiceImpl<PaymentOrderMapper, PaymentOrder> implements PaymentOrderService {

    @Autowired
    private PaymentOrderMapper paymentOrderMapper;

    @Autowired
    private com.qoobot.openscm.finance.service.AccountsReceivableService accountsReceivableService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPayment(PaymentOrder payment) {
        // 生成收付款单号
        String paymentNo = generatePaymentNo();
        payment.setPaymentNo(paymentNo);
        payment.setStatus(0); // 待审核
        payment.setPaymentDate(java.time.LocalDateTime.now());

        paymentOrderMapper.insert(payment);
        return payment.getId();
    }

    @Override
    public IPage<PaymentOrder> paymentPage(Page<PaymentOrder> page, String paymentNo, Integer orderType,
                                            Integer status, String startDate, String endDate) {
        return paymentOrderMapper.selectPaymentPage(page, paymentNo, orderType, status, startDate, endDate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approvePayment(Long paymentId, Boolean approve, String approveRemark) {
        PaymentOrder payment = paymentOrderMapper.selectById(paymentId);
        if (payment == null) {
            throw new RuntimeException("收付款单不存在");
        }
        
        if (approve) {
            payment.setStatus(1); // 已审核
            payment.setApprovedBy("current_user"); // 实际应该从上下文获取
            payment.setApproveTime(java.time.LocalDateTime.now());
            payment.setApproveRemark(approveRemark);
            
            // 如果是收款单，更新应收账款
            if (payment.getOrderType() == 0 && payment.getAccountId() != null) {
                accountsReceivableService.receivePayment(payment.getAccountId(), payment.getAmount());
            }
        } else {
            payment.setStatus(2); // 已拒绝
            payment.setApprovedBy("current_user");
            payment.setApproveTime(java.time.LocalDateTime.now());
            payment.setApproveRemark(approveRemark);
        }
        
        paymentOrderMapper.updateById(payment);
    }

    @Override
    public Map<String, Object> statistics() {
        Map<String, Object> result = new HashMap<>();
        
        // 收款总额
        BigDecimal receiptAmount = paymentOrderMapper.sumReceiptAmount();
        result.put("receiptAmount", receiptAmount != null ? receiptAmount : BigDecimal.ZERO);
        
        // 付款总额
        BigDecimal paymentAmount = paymentOrderMapper.sumPaymentAmount();
        result.put("paymentAmount", paymentAmount != null ? paymentAmount : BigDecimal.ZERO);
        
        // 净现金流
        BigDecimal netCashFlow = (receiptAmount != null ? receiptAmount : BigDecimal.ZERO)
            .subtract(paymentAmount != null ? paymentAmount : BigDecimal.ZERO);
        result.put("netCashFlow", netCashFlow);
        
        return result;
    }

    private String generatePaymentNo() {
        return "PAY" + System.currentTimeMillis();
    }
}
