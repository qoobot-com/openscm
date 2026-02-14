package com.qoobot.openscm.supplier.controller;

import com.qoobot.openscm.common.annotation.OperationLog;
import com.qoobot.openscm.common.constant.OperLogType;
import com.qoobot.openscm.common.result.Result;
import com.qoobot.openscm.supplier.entity.SupplierCategory;
import com.qoobot.openscm.supplier.mapper.SupplierCategoryMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 供应商分类管理控制器
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Tag(name = "供应商分类管理")
@RestController
@RequestMapping("/api/supplier/category")
@RequiredArgsConstructor
public class SupplierCategoryController {

    private final SupplierCategoryMapper categoryMapper;

    @Operation(summary = "获取所有分类")
    @GetMapping("/list")
    @OperationLog(module = "供应商管理", businessType = OperLogType.OTHER, description = "获取所有分类")
    public Result<List<SupplierCategory>> list() {
        List<SupplierCategory> list = categoryMapper.selectList(null);
        return Result.success(list);
    }

    @Operation(summary = "根据ID获取分类")
    @GetMapping("/{id}")
    @OperationLog(module = "供应商管理", businessType = OperLogType.OTHER, description = "根据ID获取分类")
    public Result<SupplierCategory> getById(@PathVariable Long id) {
        SupplierCategory category = categoryMapper.selectById(id);
        return Result.success(category);
    }

    @Operation(summary = "创建分类")
    @PostMapping
    @OperationLog(module = "供应商管理", businessType = OperLogType.INSERT, description = "创建分类")
    public Result<Void> create(@RequestBody SupplierCategory category) {
        categoryMapper.insert(category);
        return Result.success();
    }

    @Operation(summary = "更新分类")
    @PutMapping("/{id}")
    @OperationLog(module = "供应商管理", businessType = OperLogType.UPDATE, description = "更新分类")
    public Result<Void> update(@PathVariable Long id, @RequestBody SupplierCategory category) {
        category.setId(id);
        categoryMapper.updateById(category);
        return Result.success();
    }

    @Operation(summary = "删除分类")
    @DeleteMapping("/{id}")
    @OperationLog(module = "供应商管理", businessType = OperLogType.DELETE, description = "删除分类")
    public Result<Void> delete(@PathVariable Long id) {
        categoryMapper.deleteById(id);
        return Result.success();
    }
}
