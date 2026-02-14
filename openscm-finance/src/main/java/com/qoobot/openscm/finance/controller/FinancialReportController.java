package com.qoobot.openscm.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.common.core.domain.Result;
import com.qoobot.openscm.finance.entity.FinancialReport;
import com.qoobot.openscm.finance.service.FinancialReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 财务报表控制器
 */
@RestController
@RequestMapping("/finance/report")
public class FinancialReportController {

    @Autowired
    private FinancialReportService financialReportService;

    /**
     * 生成财务报表
     */
    @PostMapping("/generate")
    public Result<Long> generateReport(@RequestParam Integer reportType, 
                                       @RequestParam String reportPeriod) {
        Long reportId = financialReportService.generateReport(reportType, reportPeriod);
        return Result.success(reportId);
    }

    /**
     * 分页查询财务报表
     */
    @GetMapping("/page")
    public Result<IPage<FinancialReport>> reportPage(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer reportType,
            @RequestParam(required = false) String reportPeriod,
            @RequestParam(required = false) Integer status) {
        Page<FinancialReport> page = new Page<>(current, size);
        IPage<FinancialReport> result = financialReportService.reportPage(
            page, reportType, reportPeriod, status);
        return Result.success(result);
    }

    /**
     * 审核财务报表
     */
    @PostMapping("/approve/{reportId}")
    public Result<Void> approveReport(@PathVariable Long reportId) {
        financialReportService.approveReport(reportId);
        return Result.success();
    }

    /**
     * 获取财务概况
     */
    @GetMapping("/overview")
    public Result<Map<String, Object>> getFinancialOverview(@RequestParam String reportPeriod) {
        Map<String, Object> overview = financialReportService.getFinancialOverview(reportPeriod);
        return Result.success(overview);
    }
}
