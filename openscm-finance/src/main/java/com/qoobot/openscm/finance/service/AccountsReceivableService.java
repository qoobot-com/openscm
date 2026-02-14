package com.qoobot.openscm.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qoobot.openscm.finance.entity.AccountsReceivable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 应收账款服务接口
 */
public interface AccountsReceivableService extends IService<AccountsReceivable> {

    /**
     * 创建应收账款
     */
    Long createReceivable(AccountsReceivable receivable);

    /**
     * 分页查询应收账款
     */
    IPage<AccountsReceivable> receivablePage(Page<AccountsReceivable> page, String receivableNo,
                                              Long customerId, Integer settlementStatus,
                                              String startDate, String endDate);

    /**
     * 收款
     */
    void receivePayment(Long receivableId, BigDecimal amount);

    /**
     * 核销应收账款
     */
    void writeOff(Long receivableId);

    /**
     * 应收账款统计
     */
    Map<String, Object> statistics();

    /**
     * 客户应收汇总
     */
    List<Map<String, Object>> summaryByCustomer();
}
