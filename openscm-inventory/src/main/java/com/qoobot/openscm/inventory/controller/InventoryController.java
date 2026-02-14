package com.qoobot.openscm.inventory.controller;

import com.qoobot.openscm.common.util.Result;
import com.qoobot.openscm.inventory.entity.Inventory;
import com.qoobot.openscm.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 库存管理控制器
 */
@Tag(name = "库存管理", description = "库存信息管理接口")
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Operation(summary = "增加库存")
    @PostMapping("/add")
    public Result<Void> addInventory(
            @RequestParam Long warehouseId,
            @RequestParam Long locationId,
            @RequestParam String materialCode,
            @RequestParam BigDecimal quantity,
            @RequestParam(required = false) BigDecimal unitPrice,
            @RequestParam(required = false) String batchNumber,
            @RequestParam(required = false) Long supplierId) {
        inventoryService.addInventory(warehouseId, locationId, materialCode, quantity, unitPrice, batchNumber, supplierId);
        return Result.success();
    }

    @Operation(summary = "减少库存")
    @PostMapping("/reduce")
    public Result<Void> reduceInventory(
            @RequestParam Long warehouseId,
            @RequestParam String materialCode,
            @RequestParam BigDecimal quantity) {
        inventoryService.reduceInventory(warehouseId, materialCode, quantity);
        return Result.success();
    }

    @Operation(summary = "锁定库存")
    @PostMapping("/lock")
    public Result<Void> lockInventory(
            @RequestParam Long warehouseId,
            @RequestParam String materialCode,
            @RequestParam BigDecimal quantity) {
        inventoryService.lockInventory(warehouseId, materialCode, quantity);
        return Result.success();
    }

    @Operation(summary = "解锁库存")
    @PostMapping("/unlock")
    public Result<Void> unlockInventory(
            @RequestParam Long warehouseId,
            @RequestParam String materialCode,
            @RequestParam BigDecimal quantity) {
        inventoryService.unlockInventory(warehouseId, materialCode, quantity);
        return Result.success();
    }

    @Operation(summary = "分页查询库存")
    @GetMapping("/page")
    public Result<Page<Inventory>> getInventoriesByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long warehouseId,
            @RequestParam(required = false) String materialCode) {
        Page<Inventory> result = inventoryService.getInventoriesByPage(page, size, warehouseId, materialCode);
        return Result.success(result);
    }

    @Operation(summary = "查询物料库存")
    @GetMapping("/material")
    public Result<Inventory> getInventoryByMaterial(
            @RequestParam Long warehouseId,
            @RequestParam String materialCode) {
        Inventory inventory = inventoryService.getInventoryByMaterial(warehouseId, materialCode);
        return Result.success(inventory);
    }

    @Operation(summary = "库存统计")
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getInventoryStatistics(
            @RequestParam(required = false) Long warehouseId) {
        Map<String, Object> statistics = inventoryService.getInventoryStatistics(warehouseId);
        return Result.success(statistics);
    }

    @Operation(summary = "库存预警检查")
    @PostMapping("/check-alert")
    public Result<Void> checkInventoryAlert(@RequestParam(required = false) Long warehouseId) {
        inventoryService.checkInventoryAlert(warehouseId);
        return Result.success();
    }
}
