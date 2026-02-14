package com.qoobot.openscm.purchase.service;

import com.qoobot.openscm.purchase.dto.PurchasePlanDTO;
import com.qoobot.openscm.purchase.dto.PurchasePlanItemDTO;
import com.qoobot.openscm.purchase.entity.PurchasePlan;
import com.qoobot.openscm.purchase.enums.PlanStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 采购计划服务测试
 */
@SpringBootTest
@Transactional
public class PurchasePlanServiceTest {

    @Autowired
    private PurchasePlanService purchasePlanService;

    /**
     * 测试创建采购计划
     */
    @Test
    public void testCreatePurchasePlan() {
        // 准备测试数据
        PurchasePlanDTO planDTO = new PurchasePlanDTO();
        planDTO.setPlanCode("PP20260215001");
        planDTO.setPlanName("原材料采购计划-测试");
        planDTO.setPlanType(1);
        planDTO.setPlanDate(LocalDate.of(2026, 2, 15));
        planDTO.setPlanAmount(new BigDecimal("100000.00"));
        planDTO.setRequester("张三");
        planDTO.setDepartment("采购部");
        planDTO.setRemark("单元测试数据");

        List<PurchasePlanItemDTO> items = new ArrayList<>();
        PurchasePlanItemDTO itemDTO = new PurchasePlanItemDTO();
        itemDTO.setMaterialCode("M001");
        itemDTO.setMaterialName("钢材");
        itemDTO.setSpecification("规格A");
        itemDTO.setUnit("吨");
        itemDTO.setPlanQuantity(new BigDecimal("10.00"));
        itemDTO.setUnitPrice(new BigDecimal("5000.00"));
        itemDTO.setPlanAmount(new BigDecimal("50000.00"));
        itemDTO.setRemark("测试物料");
        items.add(itemDTO);
        planDTO.setItems(items);

        // 执行创建
        Long planId = purchasePlanService.createPurchasePlan(planDTO);

        // 验证结果
        assertNotNull(planId);
        
        PurchasePlan createdPlan = purchasePlanService.getPurchasePlanById(planId);
        assertNotNull(createdPlan);
        assertEquals("PP20260215001", createdPlan.getPlanCode());
        assertEquals(PlanStatus.DRAFT, createdPlan.getStatus());
        assertEquals(new BigDecimal("100000.00"), createdPlan.getPlanAmount());
    }

    /**
     * 测试分页查询采购计划
     */
    @Test
    public void testGetPurchasePlansByPage() {
        // 执行查询
        Page<PurchasePlan> page = purchasePlanService.getPurchasePlansByPage(1, 10, null, null, null);

        // 验证结果
        assertNotNull(page);
        assertTrue(page.getContent().size() >= 0);
    }

    /**
     * 测试更新采购计划
     */
    @Test
    public void testUpdatePurchasePlan() {
        // 先创建一个采购计划
        PurchasePlanDTO planDTO = createTestPlanDTO();
        Long planId = purchasePlanService.createPurchasePlan(planDTO);

        // 更新采购计划
        PurchasePlanDTO updateDTO = new PurchasePlanDTO();
        updateDTO.setPlanName("更新后的采购计划");
        updateDTO.setRemark("更新备注");
        purchasePlanService.updatePurchasePlan(planId, updateDTO);

        // 验证更新结果
        PurchasePlan updatedPlan = purchasePlanService.getPurchasePlanById(planId);
        assertEquals("更新后的采购计划", updatedPlan.getPlanName());
        assertEquals("更新备注", updatedPlan.getRemark());
    }

    /**
     * 测试提交采购计划审批
     */
    @Test
    public void testSubmitForApproval() {
        // 创建采购计划
        PurchasePlanDTO planDTO = createTestPlanDTO();
        Long planId = purchasePlanService.createPurchasePlan(planDTO);

        // 提交审批
        purchasePlanService.submitForApproval(planId);

        // 验证状态
        PurchasePlan plan = purchasePlanService.getPurchasePlanById(planId);
        assertEquals(PlanStatus.PENDING_APPROVAL, plan.getStatus());
        assertNotNull(plan.getSubmittedAt());
    }

    /**
     * 测试审批采购计划
     */
    @Test
    public void testApprovePurchasePlan() {
        // 创建并提交采购计划
        PurchasePlanDTO planDTO = createTestPlanDTO();
        Long planId = purchasePlanService.createPurchasePlan(planDTO);
        purchasePlanService.submitForApproval(planId);

        // 审批通过
        purchasePlanService.approvePurchasePlan(planId, "李四", "同意");

        // 验证状态
        PurchasePlan plan = purchasePlanService.getPurchasePlanById(planId);
        assertEquals(PlanStatus.APPROVED, plan.getStatus());
        assertEquals("李四", plan.getApprover());
        assertNotNull(plan.getApprovedAt());
    }

    /**
     * 测试拒绝采购计划
     */
    @Test
    public void testRejectPurchasePlan() {
        // 创建并提交采购计划
        PurchasePlanDTO planDTO = createTestPlanDTO();
        Long planId = purchasePlanService.createPurchasePlan(planDTO);
        purchasePlanService.submitForApproval(planId);

        // 审批拒绝
        purchasePlanService.rejectPurchasePlan(planId, "李四", "预算超限");

        // 验证状态
        PurchasePlan plan = purchasePlanService.getPurchasePlanById(planId);
        assertEquals(PlanStatus.REJECTED, plan.getStatus());
        assertEquals("李四", plan.getApprover());
        assertEquals("预算超限", plan.getApprovalRemark());
    }

    /**
     * 测试执行采购计划
     */
    @Test
    public void testExecutePurchasePlan() {
        // 创建并审批采购计划
        PurchasePlanDTO planDTO = createTestPlanDTO();
        Long planId = purchasePlanService.createPurchasePlan(planDTO);
        purchasePlanService.submitForApproval(planId);
        purchasePlanService.approvePurchasePlan(planId, "李四", "同意");

        // 执行计划
        purchasePlanService.executePurchasePlan(planId);

        // 验证状态
        PurchasePlan plan = purchasePlanService.getPurchasePlanById(planId);
        assertEquals(PlanStatus.EXECUTING, plan.getStatus());
        assertNotNull(plan.getExecutedAt());
    }

    /**
     * 测试完成采购计划
     */
    @Test
    public void testCompletePurchasePlan() {
        // 创建并执行采购计划
        PurchasePlanDTO planDTO = createTestPlanDTO();
        Long planId = purchasePlanService.createPurchasePlan(planDTO);
        purchasePlanService.submitForApproval(planId);
        purchasePlanService.approvePurchasePlan(planId, "李四", "同意");
        purchasePlanService.executePurchasePlan(planId);

        // 完成计划
        purchasePlanService.completePurchasePlan(planId);

        // 验证状态
        PurchasePlan plan = purchasePlanService.getPurchasePlanById(planId);
        assertEquals(PlanStatus.COMPLETED, plan.getStatus());
        assertNotNull(plan.getCompletedAt());
    }

    /**
     * 测试删除采购计划
     */
    @Test
    public void testDeletePurchasePlan() {
        // 创建采购计划
        PurchasePlanDTO planDTO = createTestPlanDTO();
        Long planId = purchasePlanService.createPurchasePlan(planDTO);

        // 删除计划
        purchasePlanService.deletePurchasePlan(planId);

        // 验证删除结果
        assertThrows(RuntimeException.class, () -> {
            purchasePlanService.getPurchasePlanById(planId);
        });
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
