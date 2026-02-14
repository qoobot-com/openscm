package com.qoobot.openscm.supplier.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.common.annotation.OperationLog;
import com.qoobot.openscm.common.constant.OperLogType;
import com.qoobot.openscm.common.result.Result;
import com.qoobot.openscm.supplier.entity.SupplierBlacklist;
import com.qoobot.openscm.supplier.mapper.SupplierBlacklistMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 供应商黑名单控制器
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Tag(name = "供应商黑名单管理")
@RestController
@RequestMapping("/api/supplier/blacklist")
@RequiredArgsConstructor
public class SupplierBlacklistController {

    private final SupplierBlacklistMapper blacklistMapper;

    @Operation(summary = "分页查询黑名单")
    @GetMapping("/page")
    @OperationLog(module = "供应商管理", businessType = OperLogType.OTHER, description = "分页查询黑名单")
    public Result<Page<SupplierBlacklist>> page(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long supplierId,
            @RequestParam(required = false) Integer status) {

        Page<SupplierBlacklist> page = new Page<>(current, size);
        LambdaQueryWrapper<SupplierBlacklist> wrapper = new LambdaQueryWrapper<>();

        if (supplierId != null) {
            wrapper.eq(SupplierBlacklist::getSupplierId, supplierId);
        }
        if (status != null) {
            wrapper.eq(SupplierBlacklist::getStatus, status);
        }

        wrapper.orderByDesc(SupplierBlacklist::getBlacklistDate);
        blacklistMapper.selectPage(page, wrapper);

        return Result.success(page);
    }

    @Operation(summary = "添加到黑名单")
    @PostMapping
    @OperationLog(module = "供应商管理", businessType = OperLogType.INSERT, description = "添加到黑名单")
    public Result<Void> add(@RequestBody SupplierBlacklist blacklist) {
        blacklist.setStatus(1); // 生效中
        blacklistMapper.insert(blacklist);
        return Result.success();
    }

    @Operation(summary = "解除黑名单")
    @PutMapping("/{id}/release")
    @OperationLog(module = "供应商管理", businessType = OperLogType.UPDATE, description = "解除黑名单")
    public Result<Void> release(@PathVariable Long id, @RequestParam(required = false) String remark) {
        SupplierBlacklist blacklist = blacklistMapper.selectById(id);
        if (blacklist != null) {
            blacklist.setStatus(0); // 已解除
            blacklist.setReleaseDate(java.time.LocalDate.now());
            blacklistMapper.updateById(blacklist);
        }
        return Result.success();
    }

    @Operation(summary = "删除黑名单记录")
    @DeleteMapping("/{id}")
    @OperationLog(module = "供应商管理", businessType = OperLogType.DELETE, description = "删除黑名单记录")
    public Result<Void> delete(@PathVariable Long id) {
        blacklistMapper.deleteById(id);
        return Result.success();
    }
}
