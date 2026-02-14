package com.qoobot.openscm.inventory.controller;

import com.qoobot.openscm.common.util.Result;
import com.qoobot.openscm.inventory.entity.InboundOrder;
import com.qoobot.openscm.inventory.service.InboundOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 入库单管理控制器
 */
@Tag(name = "入库单管理", description = "入库单管理接口")
@RestController
@RequestMapping("/api/inventory/inbound")
public class InboundOrderController {

    @Autowired
    private InboundOrderService inboundOrderService;

    @Operation(summary = "创建入库单")
    @PostMapping
    public Result<Long> createInboundOrder(@Valid @RequestBody InboundOrder inboundOrder) {
        Long id = inboundOrderService.createInboundOrder(inboundOrder);
        return Result.success(id);
    }

    @Operation(summary = "更新入库单")
    @PutMapping("/{id}")
    public Result<Void> updateInboundOrder(@PathVariable Long id, @Valid @RequestBody InboundOrder inboundOrder) {
        inboundOrderService.updateInboundOrder(id, inboundOrder);
        return Result.success();
    }

    @Operation(summary = "删除入库单")
    @DeleteMapping("/{id}")
    public Result<Void> deleteInboundOrder(@PathVariable Long id) {
        inboundOrderService.deleteInboundOrder(id);
        return Result.success();
    }

    @Operation(summary = "根据ID查询入库单")
    @GetMapping("/{id}")
    public Result<InboundOrder> getInboundOrderById(@PathVariable Long id) {
        InboundOrder inboundOrder = inboundOrderService.getInboundOrderById(id);
        return Result.success(inboundOrder);
    }

    @Operation(summary = "分页查询入库单")
    @GetMapping("/page")
    public Result<Page<InboundOrder>> getInboundOrdersByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long warehouseId,
            @RequestParam(required = false) String inboundCode,
            @RequestParam(required = false) Integer status) {
        Page<InboundOrder> result = inboundOrderService.getInboundOrdersByPage(page, size, warehouseId, inboundCode, status);
        return Result.success(result);
    }

    @Operation(summary = "提交入库")
    @PutMapping("/{id}/submit")
    public Result<Void> submitInbound(@PathVariable Long id) {
        inboundOrderService.submitInbound(id);
        return Result.success();
    }

    @Operation(summary = "完成入库")
    @PutMapping("/{id}/complete")
    public Result<Void> completeInbound(@PathVariable Long id) {
        inboundOrderService.completeInbound(id);
        return Result.success();
    }

    @Operation(summary = "审核入库单")
    @PutMapping("/{id}/audit")
    public Result<Void> auditInbound(
            @PathVariable Long id,
            @RequestParam String auditor,
            @RequestParam String auditRemark) {
        inboundOrderService.auditInbound(id, auditor, auditRemark);
        return Result.success();
    }
}
