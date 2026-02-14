package com.qoobot.openscm.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qoobot.openscm.auth.entity.Tenant;

import java.util.Map;

/**
 * 租户服务接口
 */
public interface TenantService extends IService<Tenant> {

    /**
     * 创建租户
     */
    Long createTenant(Tenant tenant);

    /**
     * 分页查询租户
     */
    IPage<Tenant> tenantPage(Page<Tenant> page, String tenantCode, String tenantName, Integer status);

    /**
     * 更新租户状态
     */
    void updateTenantStatus(Long tenantId, Integer status);

    /**
     * 检查租户用户数是否超限
     */
    Boolean checkUserLimit(Long tenantId);

    /**
     * 租户统计
     */
    Map<String, Object> statistics();
}
