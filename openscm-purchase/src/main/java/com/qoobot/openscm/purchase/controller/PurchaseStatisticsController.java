package com.qoobot.openscm.purchase.controller;

import com.qoobot.openscm.common.result.Result;
import com.qoobot.openscm.purchase.service.PurchaseStatisticsService;
import com.qoobot.openscm.purchase.vo.PurchaseStatisticsVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

/**
 * 采购统计控制器
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Tag(name = "采购统计分析", description = "采购统计分析相关接口")
@RestController
@RequestMapping("/api/purchase/statistics")
@RequiredArgsConstructor
public class PurchaseStatisticsController {

    private final PurchaseStatisticsService purchaseStatisticsService;

    @Operation(summary = "获取采购统计信息")
    @GetMapping
    public Result<PurchaseStatisticsVO> getStatistics(
            @Parameter(description = "开始日期") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @Parameter(description = "结束日期") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        PurchaseStatisticsVO statistics = purchaseStatisticsService.getStatistics(startDate, endDate);
        return Result.success(statistics);
    }

    @Operation(summary = "按月统计采购数据")
    @GetMapping("/monthly")
    public Result<Map<String, Object>> getMonthlyStatistics(
            @Parameter(description = "年份") @RequestParam Integer year) {
        Map<String, Object> statistics = purchaseStatisticsService.getMonthlyStatistics(year);
        return Result.success(statistics);
    }

    @Operation(summary = "按供应商统计采购数据")
    @GetMapping("/supplier")
    public Result<Map<String, Object>> getSupplierStatistics(
            @Parameter(description = "开始日期") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @Parameter(description = "结束日期") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        Map<String, Object> statistics = purchaseStatisticsService.getSupplierStatistics(startDate, endDate);
        return Result.success(statistics);
    }

    @Operation(summary = "按物料统计采购数据")
    @GetMapping("/material")
    public Result<Map<String, Object>> getMaterialStatistics(
            @Parameter(description = "开始日期") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @Parameter(description = "结束日期") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        Map<String, Object> statistics = purchaseStatisticsService.getMaterialStatistics(startDate, endDate);
        return Result.success(statistics);
    }

    @Operation(summary = "获取采购成本分析")
    @GetMapping("/cost-analysis")
    public Result<Map<String, Object>> getCostAnalysis(
            @Parameter(description = "开始日期") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @Parameter(description = "结束日期") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        Map<String, Object> analysis = purchaseStatisticsService.getCostAnalysis(startDate, endDate);
        return Result.success(analysis);
    }
}
