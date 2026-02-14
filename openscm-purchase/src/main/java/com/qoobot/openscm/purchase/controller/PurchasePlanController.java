package com.qoobot.openscm.purchase.controller;

import com.qoobot.openscm.common.annotation.OperationLog;
import com.qoobot.openscm.common.result.Result;
import com.qoobot.openscm.common.result.PageResult;
import com.qoobot.openscm.purchase.dto.PurchasePlanDTO;
import com.qoobot.openscm.purchase.service.PurchasePlanService;
import com.qoobot.openscm.purchase.vo.PurchasePlanVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 采购计划控制器
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Tag(name = "采购计划管理", description = "采购计划相关接口")
@RestController
@RequestMapping("/api/purchase/plan")
@RequiredArgsConstructor
public class PurchasePlanController {

    private final PurchasePlanService purchasePlanService;

    @Operation(summary = "分页查询采购计划")
    @GetMapping("/page")
    public Result<PageResult<PurchasePlanVO>> queryPage(
            @Parameter(description = "当前页") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "查询参数") @RequestParam(required = false) Map<String, Object> params) {
        PageResult<PurchasePlanVO> result = purchasePlanService.queryPage(current, size, params);
        return Result.success(result);
    }

    @Operation(summary = "根据ID查询采购计划")
    @GetMapping("/{id}")
    public Result<PurchasePlanVO> getById(@Parameter(description = "计划ID") @PathVariable Long id) {
        return Result.success(purchasePlanService.convertToVO(purchasePlanService.getById(id)));
    }

    @Operation(summary = "创建采购计划")
    @PostMapping
    @OperationLog("创建采购计划")
    public Result<Long> create(@Valid @RequestBody PurchasePlanDTO dto) {
        Long id = purchasePlanService.createPlan(dto);
        return Result.success(id);
    }

    @Operation(summary = "更新采购计划")
    @PutMapping("/{id}")
    @OperationLog("更新采购计划")
    public Result<Void> update(
            @Parameter(description = "计划ID") @PathVariable Long id,
            @Valid @RequestBody PurchasePlanDTO dto) {
        purchasePlanService.updatePlan(id, dto);
        return Result.success();
    }

    @Operation(summary = "删除采购计划")
    @DeleteMapping("/{id}")
    @OperationLog("删除采购计划")
    public Result<Void> delete(@Parameter(description = "计划ID") @PathVariable Long id) {
        purchasePlanService.deletePlan(id);
        return Result.success();
    }

    @Operation(summary = "提交审批")
    @PutMapping("/{id}/submit")
    @OperationLog("提交采购计划审批")
    public Result<Void> submit(@Parameter(description = "计划ID") @PathVariable Long id) {
        purchasePlanService.submitForApproval(id);
        return Result.success();
    }

    @Operation(summary = "审批采购计划")
    @PutMapping("/{id}/approve")
    @OperationLog("审批采购计划")
    public Result<Void> approve(
            @Parameter(description = "计划ID") @PathVariable Long id,
            @Parameter(description = "是否同意") @RequestParam Boolean approved,
            @Parameter(description = "审批意见") @RequestParam(required = false) String remark) {
        purchasePlanService.approvePlan(id, approved, remark);
        return Result.success();
    }

    @Operation(summary = "执行采购计划")
    @PutMapping("/{id}/execute")
    @OperationLog("执行采购计划")
    public Result<Void> execute(@Parameter(description = "计划ID") @PathVariable Long id) {
        purchasePlanService.executePlan(id);
        return Result.success();
    }
}
