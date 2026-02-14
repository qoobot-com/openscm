package com.qoobot.openscm.auth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.common.core.domain.Result;
import com.qoobot.openscm.auth.entity.Tenant;
import com.qoobot.openscm.auth.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 租户管理控制器
 */
@RestController
@RequestMapping("/auth/tenant")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    /**
     * 创建租户
     */
    @PostMapping("/create")
    public Result<Long> createTenant(@RequestBody Tenant tenant) {
        Long tenantId = tenantService.createTenant(tenant);
        return Result.success(tenantId);
    }

    /**
     * 分页查询租户
     */
    @GetMapping("/page")
    public Result<IPage<Tenant>> tenantPage(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String tenantCode,
            @RequestParam(required = false) String tenantName,
            @RequestParam(required = false) Integer status) {
        Page<Tenant> page = new Page<>(current, size);
        IPage<Tenant> result = tenantService.tenantPage(page, tenantCode, tenantName, status);
        return Result.success(result);
    }

    /**
     * 更新租户状态
     */
    @PostMapping("/status/{tenantId}")
    public Result<Void> updateTenantStatus(@PathVariable Long tenantId, @RequestParam Integer status) {
        tenantService.updateTenantStatus(tenantId, status);
        return Result.success();
    }

    /**
     * 租户统计
     */
    @GetMapping("/statistics")
    public Result<Map<String, Object>> statistics() {
        Map<String, Object> statistics = tenantService.statistics();
        return Result.success(statistics);
    }
}
