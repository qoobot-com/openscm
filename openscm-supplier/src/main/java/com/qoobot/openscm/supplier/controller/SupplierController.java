package com.qoobot.openscm.supplier.controller;

import com.qoobot.openscm.common.annotation.OperationLog;
import com.qoobot.openscm.common.constant.OperLogType;
import com.qoobot.openscm.common.result.Result;
import com.qoobot.openscm.supplier.dto.SupplierDTO;
import com.qoobot.openscm.supplier.entity.Supplier;
import com.qoobot.openscm.supplier.service.SupplierService;
import com.qoobot.openscm.supplier.vo.SupplierStatisticsVO;
import com.qoobot.openscm.supplier.vo.SupplierVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 供应商管理控制器
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Tag(name = "供应商管理", description = "供应商管理接口")
@RestController
@RequestMapping("/api/supplier")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @Operation(summary = "分页查询供应商列表")
    @GetMapping("/page")
    @OperationLog(module = "供应商管理", businessType = OperLogType.OTHER, description = "分页查询供应商列表")
    public Result<List<SupplierVO>> pageSuppliers(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String supplierName,
            @RequestParam(required = false) Integer supplierType,
            @RequestParam(required = false) Integer supplierStatus) {
        List<SupplierVO> list = supplierService.pageSuppliers(current, size, supplierName, supplierType, supplierStatus);
        return Result.success(list);
    }

    @Operation(summary = "获取供应商详情")
    @GetMapping("/{id}")
    @OperationLog(module = "供应商管理", businessType = OperLogType.OTHER, description = "获取供应商详情")
    public Result<SupplierVO> getSupplier(@PathVariable Long id) {
        Supplier supplier = supplierService.getById(id);
        if (supplier == null) {
            return Result.error("供应商不存在");
        }
        SupplierVO vo = new SupplierVO();
        org.springframework.beans.BeanUtils.copyProperties(supplier, vo);
        return Result.success(vo);
    }

    @Operation(summary = "创建供应商")
    @PostMapping
    @OperationLog(module = "供应商管理", businessType = OperLogType.INSERT, description = "创建供应商")
    public Result<Long> createSupplier(@Valid @RequestBody SupplierDTO supplierDTO) {
        Long supplierId = supplierService.createSupplier(supplierDTO);
        return Result.success(supplierId);
    }

    @Operation(summary = "更新供应商")
    @PutMapping("/{id}")
    @OperationLog(module = "供应商管理", businessType = OperLogType.UPDATE, description = "更新供应商")
    public Result<Void> updateSupplier(@PathVariable Long id, @Valid @RequestBody SupplierDTO supplierDTO) {
        supplierService.updateSupplier(id, supplierDTO);
        return Result.success();
    }

    @Operation(summary = "删除供应商")
    @DeleteMapping("/{id}")
    @OperationLog(module = "供应商管理", businessType = OperLogType.DELETE, description = "删除供应商")
    public Result<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return Result.success();
    }

    @Operation(summary = "审核供应商")
    @PutMapping("/{id}/audit")
    @OperationLog(module = "供应商管理", businessType = OperLogType.UPDATE, description = "审核供应商")
    public Result<Void> auditSupplier(
            @PathVariable Long id,
            @RequestParam Integer auditStatus,
            @RequestParam(required = false) String auditRemark) {
        supplierService.auditSupplier(id, auditStatus, auditRemark);
        return Result.success();
    }

    @Operation(summary = "获取供应商统计信息")
    @GetMapping("/statistics")
    @OperationLog(module = "供应商管理", businessType = OperLogType.OTHER, description = "获取供应商统计信息")
    public Result<SupplierStatisticsVO> getSupplierStatistics() {
        SupplierStatisticsVO statistics = supplierService.getSupplierStatistics();
        return Result.success(statistics);
    }
}
