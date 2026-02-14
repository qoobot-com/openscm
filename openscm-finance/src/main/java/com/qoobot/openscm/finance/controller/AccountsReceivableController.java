package com.qoobot.openscm.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.common.core.domain.Result;
import com.qoobot.openscm.finance.entity.AccountsReceivable;
import com.qoobot.openscm.finance.service.AccountsReceivableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 应收账款控制器
 */
@RestController
@RequestMapping("/finance/receivable")
public class AccountsReceivableController {

    @Autowired
    private AccountsReceivableService accountsReceivableService;

    /**
     * 创建应收账款
     */
    @PostMapping("/create")
    public Result<Long> createReceivable(@RequestBody AccountsReceivable receivable) {
        Long receivableId = accountsReceivableService.createReceivable(receivable);
        return Result.success(receivableId);
    }

    /**
     * 分页查询应收账款
     */
    @GetMapping("/page")
    public Result<IPage<AccountsReceivable>> receivablePage(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String receivableNo,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Integer settlementStatus,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        Page<AccountsReceivable> page = new Page<>(current, size);
        IPage<AccountsReceivable> result = accountsReceivableService.receivablePage(
            page, receivableNo, customerId, settlementStatus, startDate, endDate);
        return Result.success(result);
    }

    /**
     * 收款
     */
    @PostMapping("/receive/{receivableId}")
    public Result<Void> receivePayment(@PathVariable Long receivableId, @RequestParam BigDecimal amount) {
        accountsReceivableService.receivePayment(receivableId, amount);
        return Result.success();
    }

    /**
     * 核销应收账款
     */
    @PostMapping("/writeOff/{receivableId}")
    public Result<Void> writeOff(@PathVariable Long receivableId) {
        accountsReceivableService.writeOff(receivableId);
        return Result.success();
    }

    /**
     * 应收账款统计
     */
    @GetMapping("/statistics")
    public Result<Map<String, Object>> statistics() {
        Map<String, Object> statistics = accountsReceivableService.statistics();
        return Result.success(statistics);
    }

    /**
     * 客户应收汇总
     */
    @GetMapping("/summaryByCustomer")
    public Result<List<Map<String, Object>>> summaryByCustomer() {
        List<Map<String, Object>> summary = accountsReceivableService.summaryByCustomer();
        return Result.success(summary);
    }
}
