package com.qoobot.openscm.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 租户控制器测试
 */
@SpringBootTest
@AutoConfigureMockMvc
class TenantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateTenant() throws Exception {
        com.qoobot.openscm.auth.entity.Tenant tenant = new com.qoobot.openscm.auth.entity.Tenant();
        tenant.setTenantName("测试租户");
        tenant.setTenantType(0);
        tenant.setContactPerson("张三");
        tenant.setContactPhone("13800138000");
        tenant.setContactEmail("test@example.com");
        tenant.setIndustry("制造业");
        tenant.setCompanyScale("100-500人");
        tenant.setCreatedBy("admin");

        mockMvc.perform(post("/auth/tenant/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tenant)))
                .andExpect(status().isOk());
    }

    @Test
    void testTenantPage() throws Exception {
        mockMvc.perform(get("/auth/tenant/page")
                .param("current", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testUpdateTenantStatus() throws Exception {
        mockMvc.perform(post("/auth/tenant/status/1")
                .param("status", "0"))
                .andExpect(status().isOk());
    }

    @Test
    void testStatistics() throws Exception {
        mockMvc.perform(get("/auth/tenant/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}
