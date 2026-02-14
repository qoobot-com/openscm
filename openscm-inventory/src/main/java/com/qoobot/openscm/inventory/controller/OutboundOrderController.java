package com.qoobot.openscm.inventory.controller;

import com.qoobot.openscm.common.util.Result;
import com.qoobot.openscm.inventory.entity.OutboundOrder;
import com.qoobot.openscm.inventory.service.OutboundOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 出库单管理控制器
 */
@Tag(name = "出库单管理", description = "出库单管理接口")
@RestController
@RequestMapping("/api/inventory/outbound")
public class OutboundOrderController {

    @Autowired
    private OutboundOrderService outboundOrderService;

    @Operation(summary = "创建出库单")
    @PostMapping
    public Result<Long> createOutboundOrder(@Valid @RequestBody OutboundOrder outboundOrder) {
        Long id = outboundOrderService.createOutboundOrder(outboundOrder);
        return Result.success(id);
    }

    @Operation(summary = "更新出库单")
    @PutMapping("/{id}")
    public Result<Void> updateOutboundOrder(@PathVariable Long id, @Valid @RequestBody OutboundOrder outboundOrder) {
        outboundOrderService.updateOutboundOrder(id, outboundOrder);
        return Result.success();
    }

    @Operation(summary = "删除出库单")
    @DeleteMapping("/{id}")
    public Result<Void> deleteOutboundOrder(@PathVariable Long id) {
        outboundOrderService.deleteOutboundOrder(id);
        return Result.success();
    }

    @Operation(summary = "根据ID查询出库单")
    @GetMapping("/{id}")
    public Result<OutboundOrder> getOutboundOrderById(@PathVariable Long id) {
        OutboundOrder outboundOrder = outboundOrderService.getOutboundOrderById(id);
        return Result.success(outboundOrder);
    }

    @Operation(summary = "分页查询出库单")
    @GetMapping("/page")
    public Result<Page<OutboundOrder>> getOutboundOrdersByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long warehouseId,
            @RequestParam(required = false) String outboundCode,
            @RequestParam(required = false) Integer status) {
        Page<OutboundOrder> result = outboundOrderService.getOutboundOrdersByPage(page, size, warehouseId, outboundCode, status);
        return Result.success(result);
    }

    @Operation(summary = "提交出库")
    @PutMapping("/{id}/submit")
    public Result<Void> submitOutbound(@PathVariable Long id) {
        outboundOrderService.submitOutbound(id);
        return Result.success();
    }

    @Operation(summary = "完成出库")
    @PutMapping("/{id}/complete")
    public Result<Void> completeOutbound(@PathVariable Long id) {
        outboundOrderService.completeOutbound(id);
        return Result.success();
    }

    @Operation(summary = "审核出库单")
    @PutMapping("/{id}/audit")
    public Result<Void> auditOutbound(
            @PathVariable Long id,
            @RequestParam String auditor,
            @RequestParam String auditRemark) {
        outboundOrderService.auditOutbound(id, auditor, auditRemark);
        return Result.success();
    }
}
