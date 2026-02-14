package com.qoobot.openscm.integration.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.integration.entity.ElectronicInvoice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 电子发票服务测试
 */
@SpringBootTest
class ElectronicInvoiceServiceTest {

    @Autowired
    private ElectronicInvoiceService electronicInvoiceService;

    @Test
    void testSelectPage() {
        Page<ElectronicInvoice> page = new Page<>(1, 10);
        Page<ElectronicInvoice> result = electronicInvoiceService.selectPage(page, null, null, null, null);
        System.out.println("查询电子发票成功，记录数: " + result.getRecords().size());
    }

    @Test
    void testCreate() {
        ElectronicInvoice invoice = new ElectronicInvoice();
        invoice.setInvoiceNo("INV" + System.currentTimeMillis());
        invoice.setInvoiceType("SPECIAL");
        invoice.setInvoiceTitle("测试公司");
        invoice.setTaxNumber("91110000123456789X");
        invoice.setInvoiceDate(LocalDateTime.now());
        invoice.setAmount(new BigDecimal("1000.00"));
        invoice.setTaxAmount(new BigDecimal("130.00"));
        invoice.setTotalAmount(new BigDecimal("1130.00"));
        invoice.setInvoiceStatus("PENDING");
        invoice.setBusinessType("SALES");
        invoice.setBusinessNo("SO001");
        invoice.setPlatform("BAIWANG");
        invoice.setIssueStatus("PENDING");
        invoice.setTenantId(1L);

        boolean result = electronicInvoiceService.save(invoice);
        System.out.println("创建电子发票: " + (result ? "成功" : "失败") + ", ID: " + invoice.getId());
    }

    @Test
    void testIssueInvoice() {
        try {
            boolean result = electronicInvoiceService.issueInvoice(1L);
            System.out.println("开具电子发票: " + (result ? "成功" : "失败"));
        } catch (Exception e) {
            System.out.println("开具电子发票异常: " + e.getMessage());
        }
    }

    @Test
    void testQueryInvoiceStatus() {
        try {
            String status = electronicInvoiceService.queryInvoiceStatus(1L);
            System.out.println("电子发票状态: " + status);
        } catch (Exception e) {
            System.out.println("查询电子发票状态异常: " + e.getMessage());
        }
    }
}
