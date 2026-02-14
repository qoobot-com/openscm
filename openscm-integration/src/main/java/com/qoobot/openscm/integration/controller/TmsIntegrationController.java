package com.qoobot.openscm.integration.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.common.result.Result;
import com.qoobot.openscm.integration.entity.TmsIntegration;
import com.qoobot.openscm.integration.service.TmsIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * TMS集成控制器
 */
@Tag(name = "TMS集成管理", description = "TMS系统集成相关接口")
@RestController
@RequestMapping("/api/integration/tms")
@RequiredArgsConstructor
@Validated
public class TmsIntegrationController {

    private final TmsIntegrationService tmsIntegrationService;

    @Operation(summary = "分页查询TMS集成配置")
    @GetMapping("/page")
    public Result<Page<TmsIntegration>> page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String integrationName,
            @RequestParam(required = false) String tmsType,
            @RequestParam(required = false) Boolean enabled) {
        Page<TmsIntegration> page = new Page<>(current, size);
        return Result.success(tmsIntegrationService.selectPage(page, integrationName, tmsType, enabled));
    }

    @Operation(summary = "根据ID查询TMS集成配置")
    @GetMapping("/{id}")
    public Result<TmsIntegration> getById(@PathVariable Long id) {
        return Result.success(tmsIntegrationService.getById(id));
    }

    @Operation(summary = "创建TMS集成配置")
    @PostMapping
    public Result<Boolean> create(@RequestBody TmsIntegration tmsIntegration) {
        return Result.success(tmsIntegrationService.save(tmsIntegration));
    }

    @Operation(summary = "更新TMS集成配置")
    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @RequestBody TmsIntegration tmsIntegration) {
        tmsIntegration.setId(id);
        return Result.success(tmsIntegrationService.updateById(tmsIntegration));
    }

    @Operation(summary = "删除TMS集成配置")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(tmsIntegrationService.removeById(id));
    }

    @Operation(summary = "测试TMS连接")
    @PostMapping("/{id}/test-connection")
    public Result<Boolean> testConnection(@PathVariable Long id) {
        return Result.success(tmsIntegrationService.testConnection(id));
    }

    @Operation(summary = "手动同步TMS数据")
    @PostMapping("/{id}/sync")
    public Result<Boolean> syncData(@PathVariable Long id, @RequestParam String syncType) {
        return Result.success(tmsIntegrationService.syncData(id, syncType));
    }

    @Operation(summary = "启用/禁用TMS集成")
    @PutMapping("/{id}/enabled")
    public Result<Boolean> updateEnabled(@PathVariable Long id, @RequestParam Boolean enabled) {
        return Result.success(tmsIntegrationService.updateEnabled(id, enabled));
    }
}
