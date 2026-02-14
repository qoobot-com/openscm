package com.qoobot.openscm.purchase.controller;

import com.qoobot.openscm.common.annotation.OperationLog;
import com.qoobot.openscm.common.result.Result;
import com.qoobot.openscm.common.result.PageResult;
import com.qoobot.openscm.purchase.dto.PurchaseOrderDTO;
import com.qoobot.openscm.purchase.service.PurchaseOrderService;
import com.qoobot.openscm.purchase.vo.PurchaseOrderVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 采购订单控制器
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Tag(name = "采购订单管理", description = "采购订单相关接口")
@RestController
@RequestMapping("/api/purchase/order")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    @Operation(summary = "分页查询采购订单")
    @GetMapping("/page")
    public Result<PageResult<PurchaseOrderVO>> queryPage(
            @Parameter(description = "当前页") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "查询参数") @RequestParam(required = false) Map<String, Object> params) {
        PageResult<PurchaseOrderVO> result = purchaseOrderService.queryPage(current, size, params);
        return Result.success(result);
    }

    @Operation(summary = "根据ID查询采购订单")
    @GetMapping("/{id}")
    public Result<PurchaseOrderVO> getById(@Parameter(description = "订单ID") @PathVariable Long id) {
        return Result.success(purchaseOrderService.getOrderVOById(id));
    }

    @Operation(summary = "创建采购订单")
    @PostMapping
    @OperationLog("创建采购订单")
    public Result<Long> create(@Valid @RequestBody PurchaseOrderDTO dto) {
        Long id = purchaseOrderService.createOrder(dto);
        return Result.success(id);
    }

    @Operation(summary = "更新采购订单")
    @PutMapping("/{id}")
    @OperationLog("更新采购订单")
    public Result<Void> update(
            @Parameter(description = "订单ID") @PathVariable Long id,
            @Valid @RequestBody PurchaseOrderDTO dto) {
        purchaseOrderService.updateOrder(id, dto);
        return Result.success();
    }

    @Operation(summary = "删除采购订单")
    @DeleteMapping("/{id}")
    @OperationLog("删除采购订单")
    public Result<Void> delete(@Parameter(description = "订单ID") @PathVariable Long id) {
        purchaseOrderService.deleteOrder(id);
        return Result.success();
    }

    @Operation(summary = "确认订单")
    @PutMapping("/{id}/confirm")
    @OperationLog("确认采购订单")
    public Result<Void> confirm(@Parameter(description = "订单ID") @PathVariable Long id) {
        purchaseOrderService.confirmOrder(id);
        return Result.success();
    }

    @Operation(summary = "取消订单")
    @PutMapping("/{id}/cancel")
    @OperationLog("取消采购订单")
    public Result<Void> cancel(
            @Parameter(description = "订单ID") @PathVariable Long id,
            @Parameter(description = "取消原因") @RequestParam String reason) {
        purchaseOrderService.cancelOrder(id, reason);
        return Result.success();
    }

    @Operation(summary = "发货")
    @PutMapping("/{id}/ship")
    @OperationLog("采购订单发货")
    public Result<Void> ship(@Parameter(description = "订单ID") @PathVariable Long id) {
        purchaseOrderService.shipOrder(id);
        return Result.success();
    }

    @Operation(summary = "收货")
    @PutMapping("/{id}/receive")
    @OperationLog("采购订单收货")
    public Result<Void> receive(@Parameter(description = "订单ID") @PathVariable Long id) {
        purchaseOrderService.receiveOrder(id);
        return Result.success();
    }

    @Operation(summary = "完成订单")
    @PutMapping("/{id}/complete")
    @OperationLog("完成采购订单")
    public Result<Void> complete(@Parameter(description = "订单ID") @PathVariable Long id) {
        purchaseOrderService.completeOrder(id);
        return Result.success();
    }
}
