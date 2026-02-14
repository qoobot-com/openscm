package com.qoobot.openscm.purchase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qoobot.openscm.purchase.dto.PurchasePlanDTO;
import com.qoobot.openscm.purchase.dto.PurchasePlanItemDTO;
import com.qoobot.openscm.common.util.Result;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 采购计划控制器测试
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class PurchasePlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 测试创建采购计划
     */
    @Test
    public void testCreatePurchasePlan() throws Exception {
        PurchasePlanDTO planDTO = createTestPlanDTO();
        String json = objectMapper.writeValueAsString(planDTO);

        MvcResult result = mockMvc.perform(post("/api/purchase/plan")
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
     * 测试分页查询采购计划
     */
    @Test
    public void testGetPurchasePlansByPage() throws Exception {
        mockMvc.perform(get("/api/purchase/plan/page")
                .param("page", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * 测试根据ID查询采购计划
     */
    @Test
    public void testGetPurchasePlanById() throws Exception {
        // 先创建一个采购计划
        PurchasePlanDTO planDTO = createTestPlanDTO();
        String json = objectMapper.writeValueAsString(planDTO);
        
        MvcResult createResult = mockMvc.perform(post("/api/purchase/plan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andReturn();
        
        Result createResultObj = objectMapper.readValue(createResult.getResponse().getContentAsString(), Result.class);
        Long planId = ((Number) createResultObj.getData()).longValue();

        // 查询采购计划
        mockMvc.perform(get("/api/purchase/plan/" + planId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value(planId));
    }

    /**
     * 测试更新采购计划
     */
    @Test
    public void testUpdatePurchasePlan() throws Exception {
        // 先创建一个采购计划
        PurchasePlanDTO planDTO = createTestPlanDTO();
        String createJson = objectMapper.writeValueAsString(planDTO);
        
        MvcResult createResult = mockMvc.perform(post("/api/purchase/plan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson))
                .andReturn();
        
        Result createResultObj = objectMapper.readValue(createResult.getResponse().getContentAsString(), Result.class);
        Long planId = ((Number) createResultObj.getData()).longValue();

        // 更新采购计划
        PurchasePlanDTO updateDTO = new PurchasePlanDTO();
        updateDTO.setPlanName("更新后的采购计划");
        updateDTO.setRemark("更新备注");
        String updateJson = objectMapper.writeValueAsString(updateDTO);

        mockMvc.perform(put("/api/purchase/plan/" + planId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * 测试删除采购计划
     */
    @Test
    public void testDeletePurchasePlan() throws Exception {
        // 先创建一个采购计划
        PurchasePlanDTO planDTO = createTestPlanDTO();
        String createJson = objectMapper.writeValueAsString(planDTO);
        
        MvcResult createResult = mockMvc.perform(post("/api/purchase/plan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson))
                .andReturn();
        
        Result createResultObj = objectMapper.readValue(createResult.getResponse().getContentAsString(), Result.class);
        Long planId = ((Number) createResultObj.getData()).longValue();

        // 删除采购计划
        mockMvc.perform(delete("/api/purchase/plan/" + planId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * 测试提交审批
     */
    @Test
    public void testSubmitForApproval() throws Exception {
        // 先创建一个采购计划
        PurchasePlanDTO planDTO = createTestPlanDTO();
        String createJson = objectMapper.writeValueAsString(planDTO);
        
        MvcResult createResult = mockMvc.perform(post("/api/purchase/plan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson))
                .andReturn();
        
        Result createResultObj = objectMapper.readValue(createResult.getResponse().getContentAsString(), Result.class);
        Long planId = ((Number) createResultObj.getData()).longValue();

        // 提交审批
        mockMvc.perform(put("/api/purchase/plan/" + planId + "/submit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * 测试审批通过
     */
    @Test
    public void testApprovePurchasePlan() throws Exception {
        // 创建并提交采购计划
        PurchasePlanDTO planDTO = createTestPlanDTO();
        String createJson = objectMapper.writeValueAsString(planDTO);
        
        MvcResult createResult = mockMvc.perform(post("/api/purchase/plan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson))
                .andReturn();
        
        Result createResultObj = objectMapper.readValue(createResult.getResponse().getContentAsString(), Result.class);
        Long planId = ((Number) createResultObj.getData()).longValue();

        mockMvc.perform(put("/api/purchase/plan/" + planId + "/submit"))
                .andReturn();

        // 审批通过
        String approveJson = "{\"approver\":\"李四\",\"approvalRemark\":\"同意\"}";
        mockMvc.perform(put("/api/purchase/plan/" + planId + "/approve")
                .contentType(MediaType.APPLICATION_JSON)
                .content(approveJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * 测试审批拒绝
     */
    @Test
    public void testRejectPurchasePlan() throws Exception {
        // 创建并提交采购计划
        PurchasePlanDTO planDTO = createTestPlanDTO();
        String createJson = objectMapper.writeValueAsString(planDTO);
        
        MvcResult createResult = mockMvc.perform(post("/api/purchase/plan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson))
                .andReturn();
        
        Result createResultObj = objectMapper.readValue(createResult.getResponse().getContentAsString(), Result.class);
        Long planId = ((Number) createResultObj.getData()).longValue();

        mockMvc.perform(put("/api/purchase/plan/" + planId + "/submit"))
                .andReturn();

        // 审批拒绝
        String rejectJson = "{\"approver\":\"李四\",\"approvalRemark\":\"预算超限\"}";
        mockMvc.perform(put("/api/purchase/plan/" + planId + "/reject")
                .contentType(MediaType.APPLICATION_JSON)
                .content(rejectJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * 测试执行采购计划
     */
    @Test
    public void testExecutePurchasePlan() throws Exception {
        // 创建并审批采购计划
        PurchasePlanDTO planDTO = createTestPlanDTO();
        String createJson = objectMapper.writeValueAsString(planDTO);
        
        MvcResult createResult = mockMvc.perform(post("/api/purchase/plan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson))
                .andReturn();
        
        Result createResultObj = objectMapper.readValue(createResult.getResponse().getContentAsString(), Result.class);
        Long planId = ((Number) createResultObj.getData()).longValue();

        mockMvc.perform(put("/api/purchase/plan/" + planId + "/submit")).andReturn();
        
        String approveJson = "{\"approver\":\"李四\",\"approvalRemark\":\"同意\"}";
        mockMvc.perform(put("/api/purchase/plan/" + planId + "/approve")
                .contentType(MediaType.APPLICATION_JSON)
                .content(approveJson))
                .andReturn();

        // 执行采购计划
        mockMvc.perform(put("/api/purchase/plan/" + planId + "/execute"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * 创建测试用的采购计划DTO
     */
    private PurchasePlanDTO createTestPlanDTO() {
        PurchasePlanDTO planDTO = new PurchasePlanDTO();
        planDTO.setPlanCode("PP20260215TEST");
        planDTO.setPlanName("测试采购计划");
        planDTO.setPlanType(1);
        planDTO.setPlanDate(LocalDate.now());
        planDTO.setPlanAmount(new BigDecimal("50000.00"));
        planDTO.setRequester("测试用户");
        planDTO.setDepartment("测试部门");
        planDTO.setRemark("测试数据");

        List<PurchasePlanItemDTO> items = new ArrayList<>();
        PurchasePlanItemDTO itemDTO = new PurchasePlanItemDTO();
        itemDTO.setMaterialCode("M001");
        itemDTO.setMaterialName("测试物料");
        itemDTO.setSpecification("测试规格");
        itemDTO.setUnit("件");
        itemDTO.setPlanQuantity(new BigDecimal("10.00"));
        itemDTO.setUnitPrice(new BigDecimal("5000.00"));
        itemDTO.setPlanAmount(new BigDecimal("50000.00"));
        items.add(itemDTO);
        planDTO.setItems(items);

        return planDTO;
    }
}
