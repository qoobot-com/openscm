package com.qoobot.openscm.supplier.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.common.annotation.OperationLog;
import com.qoobot.openscm.common.constant.OperLogType;
import com.qoobot.openscm.common.result.Result;
import com.qoobot.openscm.supplier.entity.SupplierAssessment;
import com.qoobot.openscm.supplier.mapper.SupplierAssessmentMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 供应商绩效评估控制器
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Tag(name = "供应商绩效评估")
@RestController
@RequestMapping("/api/supplier/assessment")
@RequiredArgsConstructor
public class SupplierAssessmentController {

    private final SupplierAssessmentMapper assessmentMapper;

    @Operation(summary = "分页查询绩效评估")
    @GetMapping("/page")
    @OperationLog(module = "供应商管理", businessType = OperLogType.OTHER, description = "分页查询绩效评估")
    public Result<Page<SupplierAssessment>> page(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long supplierId,
            @RequestParam(required = false) String assessmentPeriod) {

        Page<SupplierAssessment> page = new Page<>(current, size);
        LambdaQueryWrapper<SupplierAssessment> wrapper = new LambdaQueryWrapper<>();

        if (supplierId != null) {
            wrapper.eq(SupplierAssessment::getSupplierId, supplierId);
        }
        if (assessmentPeriod != null && !assessmentPeriod.isEmpty()) {
            wrapper.eq(SupplierAssessment::getAssessmentPeriod, assessmentPeriod);
        }

        wrapper.orderByDesc(SupplierAssessment::getAssessmentPeriod);
        assessmentMapper.selectPage(page, wrapper);

        return Result.success(page);
    }

    @Operation(summary = "根据供应商ID查询评估记录")
    @GetMapping("/supplier/{supplierId}")
    @OperationLog(module = "供应商管理", businessType = OperLogType.OTHER, description = "根据供应商ID查询评估记录")
    public Result<Page<SupplierAssessment>> getBySupplierId(
            @PathVariable Long supplierId,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {

        Page<SupplierAssessment> page = new Page<>(current, size);
        LambdaQueryWrapper<SupplierAssessment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SupplierAssessment::getSupplierId, supplierId);
        wrapper.orderByDesc(SupplierAssessment::getAssessmentPeriod);
        assessmentMapper.selectPage(page, wrapper);

        return Result.success(page);
    }

    @Operation(summary = "创建绩效评估")
    @PostMapping
    @OperationLog(module = "供应商管理", businessType = OperLogType.INSERT, description = "创建绩效评估")
    public Result<Void> create(@RequestBody SupplierAssessment assessment) {
        // 计算综合得分
        java.math.BigDecimal totalScore = assessment.getQualityScore()
                .add(assessment.getDeliveryScore())
                .add(assessment.getServiceScore())
                .add(assessment.getPriceScore())
                .divide(new java.math.BigDecimal("4"), 2, java.math.RoundingMode.HALF_UP);
        assessment.setTotalScore(totalScore);

        // 计算评估等级
        if (totalScore.compareTo(new java.math.BigDecimal("90")) >= 0) {
            assessment.setAssessmentGrade("A");
        } else if (totalScore.compareTo(new java.math.BigDecimal("80")) >= 0) {
            assessment.setAssessmentGrade("B");
        } else if (totalScore.compareTo(new java.math.BigDecimal("70")) >= 0) {
            assessment.setAssessmentGrade("C");
        } else {
            assessment.setAssessmentGrade("D");
        }

        assessmentMapper.insert(assessment);
        return Result.success();
    }

    @Operation(summary = "更新绩效评估")
    @PutMapping("/{id}")
    @OperationLog(module = "供应商管理", businessType = OperLogType.UPDATE, description = "更新绩效评估")
    public Result<Void> update(@PathVariable Long id, @RequestBody SupplierAssessment assessment) {
        // 计算综合得分
        java.math.BigDecimal totalScore = assessment.getQualityScore()
                .add(assessment.getDeliveryScore())
                .add(assessment.getServiceScore())
                .add(assessment.getPriceScore())
                .divide(new java.math.BigDecimal("4"), 2, java.math.RoundingMode.HALF_UP);
        assessment.setTotalScore(totalScore);

        // 计算评估等级
        if (totalScore.compareTo(new java.math.BigDecimal("90")) >= 0) {
            assessment.setAssessmentGrade("A");
        } else if (totalScore.compareTo(new java.math.BigDecimal("80")) >= 0) {
            assessment.setAssessmentGrade("B");
        } else if (totalScore.compareTo(new java.math.BigDecimal("70")) >= 0) {
            assessment.setAssessmentGrade("C");
        } else {
            assessment.setAssessmentGrade("D");
        }

        assessment.setId(id);
        assessmentMapper.updateById(assessment);
        return Result.success();
    }

    @Operation(summary = "删除绩效评估")
    @DeleteMapping("/{id}")
    @OperationLog(module = "供应商管理", businessType = OperLogType.DELETE, description = "删除绩效评估")
    public Result<Void> delete(@PathVariable Long id) {
        assessmentMapper.deleteById(id);
        return Result.success();
    }
}
