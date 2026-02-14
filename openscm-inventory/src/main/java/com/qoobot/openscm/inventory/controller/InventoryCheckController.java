package com.qoobot.openscm.inventory.controller;

import com.qoobot.openscm.common.util.Result;
import com.qoobot.openscm.inventory.entity.InventoryCheck;
import com.qoobot.openscm.inventory.service.InventoryCheckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 库存盘点管理控制器
 */
@Tag(name = "库存盘点管理", description = "库存盘点管理接口")
@RestController
@RequestMapping("/api/inventory/check")
public class InventoryCheckController {

    @Autowired
    private InventoryCheckService inventoryCheckService;

    @Operation(summary = "创建盘点单")
    @PostMapping
    public Result<Long> createInventoryCheck(@Valid @RequestBody InventoryCheck inventoryCheck) {
        Long id = inventoryCheckService.createInventoryCheck(inventoryCheck);
        return Result.success(id);
    }

    @Operation(summary = "更新盘点单")
    @PutMapping("/{id}")
    public Result<Void> updateInventoryCheck(@PathVariable Long id, @Valid @RequestBody InventoryCheck inventoryCheck) {
        inventoryCheckService.updateInventoryCheck(id, inventoryCheck);
        return Result.success();
    }

    @Operation(summary = "删除盘点单")
    @DeleteMapping("/{id}")
    public Result<Void> deleteInventoryCheck(@PathVariable Long id) {
        inventoryCheckService.deleteInventoryCheck(id);
        return Result.success();
    }

    @Operation(summary = "根据ID查询盘点单")
    @GetMapping("/{id}")
    public Result<InventoryCheck> getInventoryCheckById(@PathVariable Long id) {
        InventoryCheck inventoryCheck = inventoryCheckService.getInventoryCheckById(id);
        return Result.success(inventoryCheck);
    }

    @Operation(summary = "分页查询盘点单")
    @GetMapping("/page")
    public Result<Page<InventoryCheck>> getInventoryChecksByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long warehouseId,
            @RequestParam(required = false) Integer status) {
        Page<InventoryCheck> result = inventoryCheckService.getInventoryChecksByPage(page, size, warehouseId, status);
        return Result.success(result);
    }

    @Operation(summary = "开始盘点")
    @PutMapping("/{id}/start")
    public Result<Void> startCheck(@PathVariable Long id) {
        inventoryCheckService.startCheck(id);
        return Result.success();
    }

    @Operation(summary = "完成盘点")
    @PutMapping("/{id}/complete")
    public Result<Void> completeCheck(@PathVariable Long id) {
        inventoryCheckService.completeCheck(id);
        return Result.success();
    }

    @Operation(summary = "审核盘点单")
    @PutMapping("/{id}/audit")
    public Result<Void> auditCheck(@PathVariable Long id, @RequestParam String auditor) {
        inventoryCheckService.auditCheck(id, auditor);
        return Result.success();
    }
}
