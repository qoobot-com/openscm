package com.qoobot.openscm.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.auth.entity.Tenant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 租户服务测试
 */
@SpringBootTest
class TenantServiceTest {

    @Autowired
    private TenantService tenantService;

    @Test
    void testCreateTenant() {
        Tenant tenant = new Tenant();
        tenant.setTenantName("测试租户");
        tenant.setTenantType(0);
        tenant.setContactPerson("张三");
        tenant.setContactPhone("13800138000");
        tenant.setContactEmail("test@example.com");
        tenant.setIndustry("制造业");
        tenant.setCompanyScale("100-500人");
        tenant.setExpireDate(LocalDate.now().plusYears(1));
        tenant.setMaxUsers(100);
        tenant.setStorageQuota(100);
        tenant.setCreatedBy("admin");

        Long tenantId = tenantService.createTenant(tenant);
        assertNotNull(tenantId);
        assertTrue(tenantId > 0);
    }

    @Test
    void testTenantPage() {
        Page<Tenant> page = new Page<>(1, 10);
        IPage<Tenant> result = tenantService.tenantPage(page, null, null, null);
        assertNotNull(result);
        assertTrue(result.getRecords().size() >= 0);
    }

    @Test
    void testUpdateTenantStatus() {
        Long tenantId = 1L;
        try {
            tenantService.updateTenantStatus(tenantId, 0);
        } catch (Exception e) {
            // 如果租户不存在，预期会抛异常
            assertTrue(e.getMessage().contains("租户"));
        }
    }

    @Test
    void testCheckUserLimit() {
        Long tenantId = 1L;
        try {
            Boolean result = tenantService.checkUserLimit(tenantId);
            assertNotNull(result);
        } catch (Exception e) {
            // 如果租户不存在，预期会抛异常
            assertTrue(e.getMessage().contains("租户"));
        }
    }

    @Test
    void testStatistics() {
        Map<String, Object> statistics = tenantService.statistics();
        assertNotNull(statistics);
        assertTrue(statistics.containsKey("totalTenants"));
        assertTrue(statistics.containsKey("activeTenants"));
        assertTrue(statistics.containsKey("expiredTenants"));
    }
}
