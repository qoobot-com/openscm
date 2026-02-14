package com.qoobot.openscm.purchase.controller;

import com.qoobot.openscm.common.annotation.OperationLog;
import com.qoobot.openscm.common.result.Result;
import com.qoobot.openscm.common.result.PageResult;
import com.qoobot.openscm.purchase.dto.PurchaseInboundDTO;
import com.qoobot.openscm.purchase.entity.PurchaseInbound;
import com.qoobot.openscm.purchase.service.PurchaseInboundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 采购入库控制器
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Tag(name = "采购入库管理", description = "采购入库相关接口")
@RestController
@RequestMapping("/api/purchase/inbound")
@RequiredArgsConstructor
public class PurchaseInboundController {

    private final PurchaseInboundService purchaseInboundService;

    @Operation(summary = "分页查询入库单")
    @GetMapping("/page")
    public Result<PageResult<PurchaseInbound>> queryPage(
            @Parameter(description = "当前页") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "查询参数") @RequestParam(required = false) Map<String, Object> params) {
        PageResult<PurchaseInbound> result = purchaseInboundService.queryPage(current, size, params);
        return Result.success(result);
    }

    @Operation(summary = "根据ID查询入库单")
    @GetMapping("/{id}")
    public Result<PurchaseInbound> getById(@Parameter(description = "入库单ID") @PathVariable Long id) {
        return Result.success(purchaseInboundService.getById(id));
    }

    @Operation(summary = "创建入库单")
    @PostMapping
    @OperationLog("创建采购入库单")
    public Result<Long> create(@Valid @RequestBody PurchaseInboundDTO dto) {
        Long id = purchaseInboundService.createInbound(dto);
        return Result.success(id);
    }

    @Operation(summary = "质检")
    @PutMapping("/{id}/quality-check")
    @OperationLog("采购入库质检")
    public Result<Void> qualityCheck(
            @Parameter(description = "入库单ID") @PathVariable Long id,
            @Parameter(description = "质检结果: 1-合格 2-不合格") @RequestParam Integer result,
            @Parameter(description = "质检备注") @RequestParam(required = false) String remark) {
        purchaseInboundService.qualityCheck(id, result, remark);
        return Result.success();
    }

    @Operation(summary = "入库")
    @PutMapping("/{id}/inbound")
    @OperationLog("采购入库")
    public Result<Void> inbound(@Parameter(description = "入库单ID") @PathVariable Long id) {
        purchaseInboundService.inbound(id);
        return Result.success();
    }

    @Operation(summary = "完成入库")
    @PutMapping("/{id}/complete")
    @OperationLog("完成采购入库")
    public Result<Void> complete(@Parameter(description = "入库单ID") @PathVariable Long id) {
        purchaseInboundService.completeInbound(id);
        return Result.success();
    }
}
