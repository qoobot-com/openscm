package com.qoobot.openscm.finance.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.openscm.finance.entity.AccountsReceivable;
import com.qoobot.openscm.finance.mapper.AccountsReceivableMapper;
import com.qoobot.openscm.finance.service.AccountsReceivableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应收账款服务实现
 */
@Service
public class AccountsReceivableServiceImpl extends ServiceImpl<AccountsReceivableMapper, AccountsReceivable> implements AccountsReceivableService {

    @Autowired
    private AccountsReceivableMapper accountsReceivableMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createReceivable(AccountsReceivable receivable) {
        // 生成应收单号
        String receivableNo = generateReceivableNo();
        receivable.setReceivableNo(receivableNo);
        receivable.setReceivedAmount(BigDecimal.ZERO);
        receivable.setUnpaidAmount(receivable.getAmount());
        receivable.setSettlementStatus(0); // 未结算

        accountsReceivableMapper.insert(receivable);
        return receivable.getId();
    }

    @Override
    public IPage<AccountsReceivable> receivablePage(Page<AccountsReceivable> page, String receivableNo,
                                                      Long customerId, Integer settlementStatus,
                                                      String startDate, String endDate) {
        return accountsReceivableMapper.selectReceivablePage(page, receivableNo, customerId, 
            settlementStatus, startDate, endDate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void receivePayment(Long receivableId, BigDecimal amount) {
        AccountsReceivable receivable = accountsReceivableMapper.selectById(receivableId);
        if (receivable == null) {
            throw new RuntimeException("应收账款不存在");
        }
        
        receivable.setReceivedAmount(receivable.getReceivedAmount().add(amount));
        receivable.setUnpaidAmount(receivable.getUnpaidAmount().subtract(amount));
        
        // 判断是否全部收款
        if (receivable.getUnpaidAmount().compareTo(BigDecimal.ZERO) == 0) {
            receivable.setSettlementStatus(2); // 已结算
        } else {
            receivable.setSettlementStatus(1); // 部分结算
        }
        
        accountsReceivableMapper.updateById(receivable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void writeOff(Long receivableId) {
        AccountsReceivable receivable = accountsReceivableMapper.selectById(receivableId);
        if (receivable == null) {
            throw new RuntimeException("应收账款不存在");
        }
        if (receivable.getUnpaidAmount().compareTo(BigDecimal.ZERO) != 0) {
            throw new RuntimeException("还有未收款，无法核销");
        }
        // 核销逻辑，这里可以添加更多核销相关操作
    }

    @Override
    public Map<String, Object> statistics() {
        Map<String, Object> result = new HashMap<>();
        
        // 总应收额
        BigDecimal totalAmount = accountsReceivableMapper.sumTotalAmount();
        result.put("totalAmount", totalAmount != null ? totalAmount : BigDecimal.ZERO);
        
        // 已收金额
        BigDecimal receivedAmount = accountsReceivableMapper.sumReceivedAmount();
        result.put("receivedAmount", receivedAmount != null ? receivedAmount : BigDecimal.ZERO);
        
        // 未收金额
        BigDecimal unpaidAmount = accountsReceivableMapper.sumUnpaidAmount();
        result.put("unpaidAmount", unpaidAmount != null ? unpaidAmount : BigDecimal.ZERO);
        
        // 收款率
        if (totalAmount != null && totalAmount.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal collectionRate = receivedAmount.divide(totalAmount, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal("100"));
            result.put("collectionRate", collectionRate);
        }
        
        return result;
    }

    @Override
    public List<Map<String, Object>> summaryByCustomer() {
        return accountsReceivableMapper.summaryByCustomer();
    }

    private String generateReceivableNo() {
        return "AR" + System.currentTimeMillis();
    }
}
