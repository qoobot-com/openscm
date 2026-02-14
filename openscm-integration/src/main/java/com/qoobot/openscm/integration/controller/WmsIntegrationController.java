package com.qoobot.openscm.integration.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.common.result.Result;
import com.qoobot.openscm.integration.entity.WmsIntegration;
import com.qoobot.openscm.integration.service.WmsIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * WMS集成控制器
 */
@Tag(name = "WMS集成管理", description = "WMS系统集成相关接口")
@RestController
@RequestMapping("/api/integration/wms")
@RequiredArgsConstructor
@Validated
public class WmsIntegrationController {

    private final WmsIntegrationService wmsIntegrationService;

    @Operation(summary = "分页查询WMS集成配置")
    @GetMapping("/page")
    public Result<Page<WmsIntegration>> page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String integrationName,
            @RequestParam(required = false) String wmsType,
            @RequestParam(required = false) Boolean enabled) {
        Page<WmsIntegration> page = new Page<>(current, size);
        return Result.success(wmsIntegrationService.selectPage(page, integrationName, wmsType, enabled));
    }

    @Operation(summary = "根据ID查询WMS集成配置")
    @GetMapping("/{id}")
    public Result<WmsIntegration> getById(@PathVariable Long id) {
        return Result.success(wmsIntegrationService.getById(id));
    }

    @Operation(summary = "创建WMS集成配置")
    @PostMapping
    public Result<Boolean> create(@RequestBody WmsIntegration wmsIntegration) {
        return Result.success(wmsIntegrationService.save(wmsIntegration));
    }

    @Operation(summary = "更新WMS集成配置")
    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @RequestBody WmsIntegration wmsIntegration) {
        wmsIntegration.setId(id);
        return Result.success(wmsIntegrationService.updateById(wmsIntegration));
    }

    @Operation(summary = "删除WMS集成配置")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(wmsIntegrationService.removeById(id));
    }

    @Operation(summary = "测试WMS连接")
    @PostMapping("/{id}/test-connection")
    public Result<Boolean> testConnection(@PathVariable Long id) {
        return Result.success(wmsIntegrationService.testConnection(id));
    }

    @Operation(summary = "手动同步WMS数据")
    @PostMapping("/{id}/sync")
    public Result<Boolean> syncData(@PathVariable Long id, @RequestParam String syncType) {
        return Result.success(wmsIntegrationService.syncData(id, syncType));
    }

    @Operation(summary = "启用/禁用WMS集成")
    @PutMapping("/{id}/enabled")
    public Result<Boolean> updateEnabled(@PathVariable Long id, @RequestParam Boolean enabled) {
        return Result.success(wmsIntegrationService.updateEnabled(id, enabled));
    }
}
