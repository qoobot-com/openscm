package com.qoobot.openscm.inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qoobot.openscm.common.util.Result;
import com.qoobot.openscm.inventory.entity.Warehouse;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 仓库控制器测试
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class WarehouseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 测试创建仓库
     */
    @Test
    public void testCreateWarehouse() throws Exception {
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseCode("WH001");
        warehouse.setWarehouseName("测试仓库");
        warehouse.setWarehouseType(1);
        warehouse.setAddress("测试地址");
        warehouse.setContactPerson("张三");
        warehouse.setContactPhone("13800138000");
        warehouse.setCapacity(new BigDecimal("10000.00"));
        warehouse.setStatus(1);
        
        String json = objectMapper.writeValueAsString(warehouse);

        MvcResult result = mockMvc.perform(post("/api/inventory/warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        Result resultObj = objectMapper.readValue(response, Result.class);
        assertNotNull(resultObj.getData());
    }

    /**
     * 测试分页查询仓库
     */
    @Test
    public void testGetWarehousesByPage() throws Exception {
        mockMvc.perform(get("/api/inventory/warehouse/page")
                .param("page", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * 测试根据ID查询仓库
     */
    @Test
    public void testGetWarehouseById() throws Exception {
        // 先创建仓库
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseCode("WH002");
        warehouse.setWarehouseName("查询测试仓库");
        warehouse.setWarehouseType(1);
        warehouse.setStatus(1);
        
        String createJson = objectMapper.writeValueAsString(warehouse);
        
        MvcResult createResult = mockMvc.perform(post("/api/inventory/warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson))
                .andReturn();
        
        Result createResultObj = objectMapper.readValue(createResult.getResponse().getContentAsString(), Result.class);
        Long id = ((Number) createResultObj.getData()).longValue();

        // 查询仓库
        mockMvc.perform(get("/api/inventory/warehouse/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value(id));
    }

    /**
     * 测试更新仓库
     */
    @Test
    public void testUpdateWarehouse() throws Exception {
        // 先创建仓库
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseCode("WH003");
        warehouse.setWarehouseName("更新测试仓库");
        warehouse.setWarehouseType(1);
        warehouse.setStatus(1);
        
        String createJson = objectMapper.writeValueAsString(warehouse);
        
        MvcResult createResult = mockMvc.perform(post("/api/inventory/warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson))
                .andReturn();
        
        Result createResultObj = objectMapper.readValue(createResult.getResponse().getContentAsString(), Result.class);
        Long id = ((Number) createResultObj.getData()).longValue();

        // 更新仓库
        Warehouse update = new Warehouse();
        update.setWarehouseName("更新后的名称");
        String updateJson = objectMapper.writeValueAsString(update);

        mockMvc.perform(put("/api/inventory/warehouse/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * 测试删除仓库
     */
    @Test
    public void testDeleteWarehouse() throws Exception {
        // 先创建仓库
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseCode("WH004");
        warehouse.setWarehouseName("删除测试仓库");
        warehouse.setWarehouseType(1);
        warehouse.setStatus(1);
        
        String createJson = objectMapper.writeValueAsString(warehouse);
        
        MvcResult createResult = mockMvc.perform(post("/api/inventory/warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson))
                .andReturn();
        
        Result createResultObj = objectMapper.readValue(createResult.getResponse().getContentAsString(), Result.class);
        Long id = ((Number) createResultObj.getData()).longValue();

        // 删除仓库
        mockMvc.perform(delete("/api/inventory/warehouse/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * 测试更新仓库状态
     */
    @Test
    public void testUpdateWarehouseStatus() throws Exception {
        // 先创建仓库
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseCode("WH005");
        warehouse.setWarehouseName("状态测试仓库");
        warehouse.setWarehouseType(1);
        warehouse.setStatus(1);
        
        String createJson = objectMapper.writeValueAsString(warehouse);
        
        MvcResult createResult = mockMvc.perform(post("/api/inventory/warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson))
                .andReturn();
        
        Result createResultObj = objectMapper.readValue(createResult.getResponse().getContentAsString(), Result.class);
        Long id = ((Number) createResultObj.getData()).longValue();

        // 更新状态
        mockMvc.perform(put("/api/inventory/warehouse/" + id + "/status")
                .param("status", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}
