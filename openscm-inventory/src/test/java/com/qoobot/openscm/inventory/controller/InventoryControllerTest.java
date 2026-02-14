package com.qoobot.openscm.inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qoobot.openscm.common.util.Result;
import com.qoobot.openscm.inventory.entity.Inventory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 库存控制器测试
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 测试增加库存
     */
    @Test
    public void testAddInventory() throws Exception {
        mockMvc.perform(post("/api/inventory/add")
                .param("warehouseId", "1")
                .param("locationId", "1")
                .param("materialCode", "M001")
                .param("quantity", "100.00")
                .param("unitPrice", "50.00")
                .param("batchNumber", "BATCH001")
                .param("supplierId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * 测试减少库存
     */
    @Test
    public void testReduceInventory() throws Exception {
        // 先增加库存
        mockMvc.perform(post("/api/inventory/add")
                .param("warehouseId", "1")
                .param("locationId", "1")
                .param("materialCode", "M002")
                .param("quantity", "100.00")
                .param("unitPrice", "50.00")
                .param("batchNumber", "BATCH002"))
                .andReturn();

        // 减少库存
        mockMvc.perform(post("/api/inventory/reduce")
                .param("warehouseId", "1")
                .param("materialCode", "M002")
                .param("quantity", "30.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * 测试锁定库存
     */
    @Test
    public void testLockInventory() throws Exception {
        // 先增加库存
        mockMvc.perform(post("/api/inventory/add")
                .param("warehouseId", "1")
                .param("locationId", "1")
                .param("materialCode", "M003")
                .param("quantity", "100.00")
                .param("unitPrice", "50.00")
                .param("batchNumber", "BATCH003"))
                .andReturn();

        // 锁定库存
        mockMvc.perform(post("/api/inventory/lock")
                .param("warehouseId", "1")
                .param("materialCode", "M003")
                .param("quantity", "20.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * 测试解锁库存
     */
    @Test
    public void testUnlockInventory() throws Exception {
        // 先增加并锁定库存
        mockMvc.perform(post("/api/inventory/add")
                .param("warehouseId", "1")
                .param("locationId", "1")
                .param("materialCode", "M004")
                .param("quantity", "100.00")
                .param("unitPrice", "50.00")
                .param("batchNumber", "BATCH004"))
                .andReturn();

        mockMvc.perform(post("/api/inventory/lock")
                .param("warehouseId", "1")
                .param("materialCode", "M004")
                .param("quantity", "20.00"))
                .andReturn();

        // 解锁库存
        mockMvc.perform(post("/api/inventory/unlock")
                .param("warehouseId", "1")
                .param("materialCode", "M004")
                .param("quantity", "10.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * 测试分页查询库存
     */
    @Test
    public void testGetInventoriesByPage() throws Exception {
        mockMvc.perform(get("/api/inventory/page")
                .param("page", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * 测试查询物料库存
     */
    @Test
    public void testGetInventoryByMaterial() throws Exception {
        // 先增加库存
        mockMvc.perform(post("/api/inventory/add")
                .param("warehouseId", "1")
                .param("locationId", "1")
                .param("materialCode", "M005")
                .param("quantity", "50.00")
                .param("unitPrice", "30.00")
                .param("batchNumber", "BATCH005"))
                .andReturn();

        // 查询库存
        mockMvc.perform(get("/api/inventory/material")
                .param("warehouseId", "1")
                .param("materialCode", "M005"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * 测试库存统计
     */
    @Test
    public void testGetInventoryStatistics() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/inventory/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        Result resultObj = objectMapper.readValue(response, Result.class);
        Map<String, Object> statistics = (Map<String, Object>) resultObj.getData();
        assertNotNull(statistics);
    }

    /**
     * 测试库存预警检查
     */
    @Test
    public void testCheckInventoryAlert() throws Exception {
        mockMvc.perform(post("/api/inventory/check-alert"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}
