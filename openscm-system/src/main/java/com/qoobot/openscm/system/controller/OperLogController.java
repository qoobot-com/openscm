package com.qoobot.openscm.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.common.result.PageResult;
import com.qoobot.openscm.common.result.Result;
import com.qoobot.openscm.system.entity.SysOperLog;
import com.qoobot.openscm.system.service.SysOperLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 操作日志控制器
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Tag(name = "操作日志管理")
@RestController
@RequestMapping("/api/system/operlog")
@RequiredArgsConstructor
public class OperLogController {

    private final SysOperLogService operLogService;

    @Operation(summary = "分页查询操作日志")
    @GetMapping("/page")
    public Result<PageResult<SysOperLog>> page(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String module,
            @RequestParam(required = false) String operName,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {

        Page<SysOperLog> page = new Page<>(current, size);
        LambdaQueryWrapper<SysOperLog> wrapper = new LambdaQueryWrapper<>();

        if (module != null && !module.isEmpty()) {
            wrapper.like(SysOperLog::getModule, module);
        }
        if (operName != null && !operName.isEmpty()) {
            wrapper.like(SysOperLog::getOperName, operName);
        }
        if (startTime != null) {
            wrapper.ge(SysOperLog::getOperTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(SysOperLog::getOperTime, endTime);
        }

        wrapper.orderByDesc(SysOperLog::getOperTime);

        operLogService.page(page, wrapper);
        return Result.success(PageResult.of(page));
    }

    @Operation(summary = "根据ID查询操作日志")
    @GetMapping("/{id}")
    public Result<SysOperLog> getById(@PathVariable Long id) {
        SysOperLog operLog = operLogService.getById(id);
        return Result.success(operLog);
    }

    @Operation(summary = "删除操作日志")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        operLogService.removeById(id);
        return Result.success();
    }
}
