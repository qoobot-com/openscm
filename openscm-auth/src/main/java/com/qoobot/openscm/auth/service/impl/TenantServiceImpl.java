package com.qoobot.openscm.auth.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.openscm.auth.entity.Tenant;
import com.qoobot.openscm.auth.mapper.TenantMapper;
import com.qoobot.openscm.auth.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 租户服务实现
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements TenantService {

    @Autowired
    private TenantMapper tenantMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTenant(Tenant tenant) {
        // 生成租户编码
        String tenantCode = generateTenantCode();
        tenant.setTenantCode(tenantCode);
        tenant.setStatus(1); // 启用
        tenant.setCurrentUsers(0);
        tenant.setUsedStorage(0);

        tenantMapper.insert(tenant);
        return tenant.getId();
    }

    @Override
    public IPage<Tenant> tenantPage(Page<Tenant> page, String tenantCode, String tenantName, Integer status) {
        return tenantMapper.selectTenantPage(page, tenantCode, tenantName, status);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTenantStatus(Long tenantId, Integer status) {
        Tenant tenant = tenantMapper.selectById(tenantId);
        if (tenant == null) {
            throw new RuntimeException("租户不存在");
        }
        tenant.setStatus(status);
        tenantMapper.updateById(tenant);
    }

    @Override
    public Boolean checkUserLimit(Long tenantId) {
        Tenant tenant = tenantMapper.selectById(tenantId);
        if (tenant == null) {
            throw new RuntimeException("租户不存在");
        }
        if (tenant.getMaxUsers() == null || tenant.getMaxUsers() <= 0) {
            return true; // 无限制
        }
        Integer currentUsers = tenantMapper.countUsersByTenantId(tenantId);
        return currentUsers < tenant.getMaxUsers();
    }

    @Override
    public Map<String, Object> statistics() {
        Map<String, Object> result = new HashMap<>();
        
        // 总租户数
        Long totalTenants = tenantMapper.selectCount(null);
        result.put("totalTenants", totalTenants);
        
        // 启用租户数
        Long activeTenants = tenantMapper.selectCount(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Tenant>()
                .eq(Tenant::getStatus, 1)
        );
        result.put("activeTenants", activeTenants);
        
        // 过期租户数
        Long expiredTenants = tenantMapper.selectCount(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Tenant>()
                .eq(Tenant::getStatus, 2)
        );
        result.put("expiredTenants", expiredTenants);
        
        return result;
    }

    private String generateTenantCode() {
        return "T" + System.currentTimeMillis();
    }
}
