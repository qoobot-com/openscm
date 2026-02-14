package com.qoobot.openscm.logistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.common.core.domain.Result;
import com.qoobot.openscm.logistics.entity.LogisticsTracking;
import com.qoobot.openscm.logistics.entity.TransportPlan;
import com.qoobot.openscm.logistics.service.TransportPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 运输计划控制器
 */
@RestController
@RequestMapping("/logistics/transport")
public class TransportPlanController {

    @Autowired
    private TransportPlanService transportPlanService;

    /**
     * 创建运输计划
     */
    @PostMapping("/create")
    public Result<Long> createPlan(@RequestBody TransportPlan plan) {
        Long planId = transportPlanService.createPlan(plan);
        return Result.success(planId);
    }

    /**
     * 分页查询运输计划
     */
    @GetMapping("/page")
    public Result<IPage<TransportPlan>> planPage(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String planNo,
            @RequestParam(required = false) Long orderId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        Page<TransportPlan> page = new Page<>(current, size);
        IPage<TransportPlan> result = transportPlanService.planPage(page, planNo, orderId, status, startDate, endDate);
        return Result.success(result);
    }

    /**
     * 开始运输
     */
    @PostMapping("/start/{planId}")
    public Result<Void> startTransport(@PathVariable Long planId) {
        transportPlanService.startTransport(planId);
        return Result.success();
    }

    /**
     * 完成运输
     */
    @PostMapping("/complete/{planId}")
    public Result<Void> completeTransport(@PathVariable Long planId) {
        transportPlanService.completeTransport(planId);
        return Result.success();
    }

    /**
     * 取消运输计划
     */
    @PostMapping("/cancel/{planId}")
    public Result<Void> cancelPlan(@PathVariable Long planId) {
        transportPlanService.cancelPlan(planId);
        return Result.success();
    }

    /**
     * 物流统计分析
     */
    @GetMapping("/statistics")
    public Result<Map<String, Object>> statistics() {
        Map<String, Object> statistics = transportPlanService.statistics();
        return Result.success(statistics);
    }

    /**
     * 根据运单号查询轨迹
     */
    @GetMapping("/tracking/{waybillNo}")
    public Result<List<LogisticsTracking>> getTrackingByWaybillNo(@PathVariable String waybillNo) {
        List<LogisticsTracking> tracking = transportPlanService.getTrackingByWaybillNo(waybillNo);
        return Result.success(tracking);
    }
}
