package com.qoobot.openscm.integration.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.integration.entity.ErpIntegration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * ERP集成服务测试
 */
@SpringBootTest
class ErpIntegrationServiceTest {

    @Autowired
    private ErpIntegrationService erpIntegrationService;

    @Test
    void testSelectPage() {
        Page<ErpIntegration> page = new Page<>(1, 10);
        Page<ErpIntegration> result = erpIntegrationService.selectPage(page, null, null, null);
        System.out.println("查询ERP集成配置成功，记录数: " + result.getRecords().size());
    }

    @Test
    void testCreate() {
        ErpIntegration erp = new ErpIntegration();
        erp.setIntegrationName("测试ERP系统");
        erp.setErpType("SAP");
        erp.setErpVersion("S/4HANA");
        erp.setApiUrl("https://test.example.com/api");
        erp.setAccessKey("test_key");
        erp.setSecretKey("test_secret");
        erp.setSyncMode("PULL");
        erp.setSyncFrequency("HOURLY");
        erp.setEnabled(true);
        erp.setTenantId(1L);

        boolean result = erpIntegrationService.save(erp);
        System.out.println("创建ERP集成配置: " + (result ? "成功" : "失败") + ", ID: " + erp.getId());
    }

    @Test
    void testUpdateEnabled() {
        boolean result = erpIntegrationService.updateEnabled(1L, true);
        System.out.println("启用ERP集成: " + (result ? "成功" : "失败"));
    }

    @Test
    void testTestConnection() {
        try {
            boolean result = erpIntegrationService.testConnection(1L);
            System.out.println("测试ERP连接: " + (result ? "成功" : "失败"));
        } catch (Exception e) {
            System.out.println("测试ERP连接异常: " + e.getMessage());
        }
    }
}
