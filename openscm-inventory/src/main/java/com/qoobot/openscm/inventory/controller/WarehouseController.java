package com.qoobot.openscm.inventory.controller;

import com.qoobot.openscm.common.util.Result;
import com.qoobot.openscm.inventory.entity.Warehouse;
import com.qoobot.openscm.inventory.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 仓库管理控制器
 */
@Tag(name = "仓库管理", description = "仓库信息管理接口")
@RestController
@RequestMapping("/api/inventory/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @Operation(summary = "创建仓库")
    @PostMapping
    public Result<Long> createWarehouse(@Valid @RequestBody Warehouse warehouse) {
        Long id = warehouseService.createWarehouse(warehouse);
        return Result.success(id);
    }

    @Operation(summary = "更新仓库")
    @PutMapping("/{id}")
    public Result<Void> updateWarehouse(@PathVariable Long id, @Valid @RequestBody Warehouse warehouse) {
        warehouseService.updateWarehouse(id, warehouse);
        return Result.success();
    }

    @Operation(summary = "删除仓库")
    @DeleteMapping("/{id}")
    public Result<Void> deleteWarehouse(@PathVariable Long id) {
        warehouseService.deleteWarehouse(id);
        return Result.success();
    }

    @Operation(summary = "根据ID查询仓库")
    @GetMapping("/{id}")
    public Result<Warehouse> getWarehouseById(@PathVariable Long id) {
        Warehouse warehouse = warehouseService.getWarehouseById(id);
        return Result.success(warehouse);
    }

    @Operation(summary = "分页查询仓库")
    @GetMapping("/page")
    public Result<Page<Warehouse>> getWarehousesByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String warehouseCode,
            @RequestParam(required = false) String warehouseName,
            @RequestParam(required = false) Integer status) {
        Page<Warehouse> result = warehouseService.getWarehousesByPage(page, size, warehouseCode, warehouseName, status);
        return Result.success(result);
    }

    @Operation(summary = "启用/禁用仓库")
    @PutMapping("/{id}/status")
    public Result<Void> updateWarehouseStatus(@PathVariable Long id, @RequestParam Integer status) {
        warehouseService.updateWarehouseStatus(id, status);
        return Result.success();
    }
}
