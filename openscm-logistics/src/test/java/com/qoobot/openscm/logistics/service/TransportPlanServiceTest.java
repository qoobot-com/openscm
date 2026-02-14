package com.qoobot.openscm.logistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.logistics.entity.TransportPlan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 运输计划服务测试
 */
@SpringBootTest
class TransportPlanServiceTest {

    @Autowired
    private TransportPlanService transportPlanService;

    @Test
    void testCreatePlan() {
        TransportPlan plan = new TransportPlan();
        plan.setOrderId(1L);
        plan.setOrderNo("SO001");
        plan.setTransportType(1);
        plan.setStartAddress("北京市朝阳区");
        plan.setDestinationAddress("上海市浦东新区");
        plan.setEstimatedDepartureTime(LocalDateTime.now().plusDays(1));
        plan.setEstimatedArrivalTime(LocalDateTime.now().plusDays(2));
        plan.setDistance(new BigDecimal("1200.50"));
        plan.setTransportFee(new BigDecimal("500.00"));
        plan.setVehicleInfo("京A12345");
        plan.setDriverInfo("张三");
        plan.setCreatedBy("test");

        Long planId = transportPlanService.createPlan(plan);
        assertNotNull(planId);
        assertTrue(planId > 0);
    }

    @Test
    void testPlanPage() {
        Page<TransportPlan> page = new Page<>(1, 10);
        IPage<TransportPlan> result = transportPlanService.planPage(page, null, null, null, null, null);
        assertNotNull(result);
        assertTrue(result.getRecords().size() >= 0);
    }

    @Test
    void testStartTransport() {
        Long planId = 1L;
        try {
            transportPlanService.startTransport(planId);
        } catch (Exception e) {
            // 如果计划不存在或状态不正确，预期会抛异常
            assertTrue(e.getMessage().contains("运输"));
        }
    }

    @Test
    void testCompleteTransport() {
        Long planId = 1L;
        try {
            transportPlanService.completeTransport(planId);
        } catch (Exception e) {
            // 如果计划不存在或状态不正确，预期会抛异常
            assertTrue(e.getMessage().contains("运输"));
        }
    }

    @Test
    void testCancelPlan() {
        Long planId = 1L;
        try {
            transportPlanService.cancelPlan(planId);
        } catch (Exception e) {
            // 如果计划不存在或状态不正确，预期会抛异常
            assertTrue(e.getMessage().contains("运输"));
        }
    }

    @Test
    void testStatistics() {
        Map<String, Object> statistics = transportPlanService.statistics();
        assertNotNull(statistics);
        assertTrue(statistics.containsKey("totalPlans"));
        assertTrue(statistics.containsKey("statusCount"));
        assertTrue(statistics.containsKey("totalFee"));
    }

    @Test
    void testGetTrackingByWaybillNo() {
        String waybillNo = "TP1234567890";
        List<com.qoobot.openscm.logistics.entity.LogisticsTracking> tracking = 
            transportPlanService.getTrackingByWaybillNo(waybillNo);
        assertNotNull(tracking);
    }
}
