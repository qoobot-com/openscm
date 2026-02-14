package com.qoobot.openscm.integration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.openscm.common.exception.BusinessException;
import com.qoobot.openscm.common.util.SecurityUtils;
import com.qoobot.openscm.integration.entity.WmsIntegration;
import com.qoobot.openscm.integration.mapper.WmsIntegrationMapper;
import com.qoobot.openscm.integration.service.WmsIntegrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * WMS集成服务实现
 */
@Slf4j
@Service
public class WmsIntegrationServiceImpl extends ServiceImpl<WmsIntegrationMapper, WmsIntegration> implements WmsIntegrationService {

    @Override
    public Page<WmsIntegration> selectPage(Page<WmsIntegration> page, String integrationName, String wmsType, Boolean enabled) {
        LambdaQueryWrapper<WmsIntegration> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WmsIntegration::getTenantId, SecurityUtils.getTenantId());
        if (integrationName != null && !integrationName.isEmpty()) {
            wrapper.like(WmsIntegration::getIntegrationName, integrationName);
        }
        if (wmsType != null && !wmsType.isEmpty()) {
            wrapper.eq(WmsIntegration::getWmsType, wmsType);
        }
        if (enabled != null) {
            wrapper.eq(WmsIntegration::getEnabled, enabled);
        }
        wrapper.orderByDesc(WmsIntegration::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public Boolean testConnection(Long id) {
        WmsIntegration wmsIntegration = this.getById(id);
        if (wmsIntegration == null) {
            throw new BusinessException("WMS集成配置不存在");
        }

        try {
            // TODO: 实现实际的连接测试逻辑
            log.info("测试WMS连接: {}, 类型: {}", wmsIntegration.getIntegrationName(), wmsIntegration.getWmsType());
            return true;
        } catch (Exception e) {
            log.error("测试WMS连接失败", e);
            throw new BusinessException("测试WMS连接失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean syncData(Long id, String syncType) {
        WmsIntegration wmsIntegration = this.getById(id);
        if (wmsIntegration == null) {
            throw new BusinessException("WMS集成配置不存在");
        }

        if (!wmsIntegration.getEnabled()) {
            throw new BusinessException("WMS集成未启用");
        }

        try {
            // TODO: 实现实际的数据同步逻辑
            log.info("开始同步WMS数据: {}, 类型: {}", wmsIntegration.getIntegrationName(), syncType);

            wmsIntegration.setLastSyncTime(LocalDateTime.now());
            wmsIntegration.setSyncStatus("SUCCESS");
            this.updateById(wmsIntegration);

            return true;
        } catch (Exception e) {
            log.error("同步WMS数据失败", e);
            wmsIntegration.setSyncStatus("FAILED");
            wmsIntegration.setErrorMessage(e.getMessage());
            this.updateById(wmsIntegration);
            throw new BusinessException("同步WMS数据失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateEnabled(Long id, Boolean enabled) {
        WmsIntegration wmsIntegration = this.getById(id);
        if (wmsIntegration == null) {
            throw new BusinessException("WMS集成配置不存在");
        }

        wmsIntegration.setEnabled(enabled);
        return this.updateById(wmsIntegration);
    }
}
