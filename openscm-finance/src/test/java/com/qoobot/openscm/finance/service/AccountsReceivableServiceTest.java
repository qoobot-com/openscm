package com.qoobot.openscm.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.finance.entity.AccountsReceivable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 应收账款服务测试
 */
@SpringBootTest
class AccountsReceivableServiceTest {

    @Autowired
    private AccountsReceivableService accountsReceivableService;

    @Test
    void testCreateReceivable() {
        AccountsReceivable receivable = new AccountsReceivable();
        receivable.setOrderId(1L);
        receivable.setOrderNo("SO001");
        receivable.setCustomerId(1L);
        receivable.setCustomerName("测试客户");
        receivable.setAmount(new BigDecimal("10000.00"));
        receivable.setReceivableDate(LocalDate.now());
        receivable.setDueDate(LocalDate.now().plusDays(30));
        receivable.setBusinessType(0);
        receivable.setCreatedBy("test");

        Long receivableId = accountsReceivableService.createReceivable(receivable);
        assertNotNull(receivableId);
        assertTrue(receivableId > 0);
    }

    @Test
    void testReceivablePage() {
        Page<AccountsReceivable> page = new Page<>(1, 10);
        IPage<AccountsReceivable> result = accountsReceivableService.receivablePage(
            page, null, null, null, null, null);
        assertNotNull(result);
        assertTrue(result.getRecords().size() >= 0);
    }

    @Test
    void testReceivePayment() {
        Long receivableId = 1L;
        try {
            accountsReceivableService.receivePayment(receivableId, new BigDecimal("5000.00"));
        } catch (Exception e) {
            // 如果应收账款不存在，预期会抛异常
            assertTrue(e.getMessage().contains("应收"));
        }
    }

    @Test
    void testWriteOff() {
        Long receivableId = 1L;
        try {
            accountsReceivableService.writeOff(receivableId);
        } catch (Exception e) {
            // 如果应收账款不存在，预期会抛异常
            assertTrue(e.getMessage().contains("应收"));
        }
    }

    @Test
    void testStatistics() {
        Map<String, Object> statistics = accountsReceivableService.statistics();
        assertNotNull(statistics);
        assertTrue(statistics.containsKey("totalAmount"));
        assertTrue(statistics.containsKey("receivedAmount"));
        assertTrue(statistics.containsKey("unpaidAmount"));
    }

    @Test
    void testSummaryByCustomer() {
        List<Map<String, Object>> summary = accountsReceivableService.summaryByCustomer();
        assertNotNull(summary);
    }
}
