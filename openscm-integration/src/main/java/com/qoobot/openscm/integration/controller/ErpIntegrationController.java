package com.qoobot.openscm.integration.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.common.result.Result;
import com.qoobot.openscm.integration.entity.ErpIntegration;
import com.qoobot.openscm.integration.service.ErpIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * ERP集成控制器
 */
@Tag(name = "ERP集成管理", description = "ERP系统集成相关接口")
@RestController
@RequestMapping("/api/integration/erp")
@RequiredArgsConstructor
@Validated
public class ErpIntegrationController {

    private final ErpIntegrationService erpIntegrationService;

    @Operation(summary = "分页查询ERP集成配置")
    @GetMapping("/page")
    public Result<Page<ErpIntegration>> page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String integrationName,
            @RequestParam(required = false) String erpType,
            @RequestParam(required = false) Boolean enabled) {
        Page<ErpIntegration> page = new Page<>(current, size);
        return Result.success(erpIntegrationService.selectPage(page, integrationName, erpType, enabled));
    }

    @Operation(summary = "根据ID查询ERP集成配置")
    @GetMapping("/{id}")
    public Result<ErpIntegration> getById(@PathVariable Long id) {
        return Result.success(erpIntegrationService.getById(id));
    }

    @Operation(summary = "创建ERP集成配置")
    @PostMapping
    public Result<Boolean> create(@RequestBody ErpIntegration erpIntegration) {
        return Result.success(erpIntegrationService.save(erpIntegration));
    }

    @Operation(summary = "更新ERP集成配置")
    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @RequestBody ErpIntegration erpIntegration) {
        erpIntegration.setId(id);
        return Result.success(erpIntegrationService.updateById(erpIntegration));
    }

    @Operation(summary = "删除ERP集成配置")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(erpIntegrationService.removeById(id));
    }

    @Operation(summary = "测试ERP连接")
    @PostMapping("/{id}/test-connection")
    public Result<Boolean> testConnection(@PathVariable Long id) {
        return Result.success(erpIntegrationService.testConnection(id));
    }

    @Operation(summary = "手动同步ERP数据")
    @PostMapping("/{id}/sync")
    public Result<Boolean> syncData(@PathVariable Long id, @RequestParam String syncType) {
        return Result.success(erpIntegrationService.syncData(id, syncType));
    }

    @Operation(summary = "启用/禁用ERP集成")
    @PutMapping("/{id}/enabled")
    public Result<Boolean> updateEnabled(@PathVariable Long id, @RequestParam Boolean enabled) {
        return Result.success(erpIntegrationService.updateEnabled(id, enabled));
    }
}
