package com.qoobot.openscm.integration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.openscm.common.exception.BusinessException;
import com.qoobot.openscm.common.util.SecurityUtils;
import com.qoobot.openscm.integration.entity.TmsIntegration;
import com.qoobot.openscm.integration.mapper.TmsIntegrationMapper;
import com.qoobot.openscm.integration.service.TmsIntegrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * TMS集成服务实现
 */
@Slf4j
@Service
public class TmsIntegrationServiceImpl extends ServiceImpl<TmsIntegrationMapper, TmsIntegration> implements TmsIntegrationService {

    @Override
    public Page<TmsIntegration> selectPage(Page<TmsIntegration> page, String integrationName, String tmsType, Boolean enabled) {
        LambdaQueryWrapper<TmsIntegration> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TmsIntegration::getTenantId, SecurityUtils.getTenantId());
        if (integrationName != null && !integrationName.isEmpty()) {
            wrapper.like(TmsIntegration::getIntegrationName, integrationName);
        }
        if (tmsType != null && !tmsType.isEmpty()) {
            wrapper.eq(TmsIntegration::getTmsType, tmsType);
        }
        if (enabled != null) {
            wrapper.eq(TmsIntegration::getEnabled, enabled);
        }
        wrapper.orderByDesc(TmsIntegration::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public Boolean testConnection(Long id) {
        TmsIntegration tmsIntegration = this.getById(id);
        if (tmsIntegration == null) {
            throw new BusinessException("TMS集成配置不存在");
        }

        try {
            // TODO: 实现实际的连接测试逻辑
            log.info("测试TMS连接: {}, 类型: {}", tmsIntegration.getIntegrationName(), tmsIntegration.getTmsType());
            return true;
        } catch (Exception e) {
            log.error("测试TMS连接失败", e);
            throw new BusinessException("测试TMS连接失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean syncData(Long id, String syncType) {
        TmsIntegration tmsIntegration = this.getById(id);
        if (tmsIntegration == null) {
            throw new BusinessException("TMS集成配置不存在");
        }

        if (!tmsIntegration.getEnabled()) {
            throw new BusinessException("TMS集成未启用");
        }

        try {
            // TODO: 实现实际的数据同步逻辑
            log.info("开始同步TMS数据: {}, 类型: {}", tmsIntegration.getIntegrationName(), syncType);

            tmsIntegration.setLastSyncTime(LocalDateTime.now());
            tmsIntegration.setSyncStatus("SUCCESS");
            this.updateById(tmsIntegration);

            return true;
        } catch (Exception e) {
            log.error("同步TMS数据失败", e);
            tmsIntegration.setSyncStatus("FAILED");
            tmsIntegration.setErrorMessage(e.getMessage());
            this.updateById(tmsIntegration);
            throw new BusinessException("同步TMS数据失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateEnabled(Long id, Boolean enabled) {
        TmsIntegration tmsIntegration = this.getById(id);
        if (tmsIntegration == null) {
            throw new BusinessException("TMS集成配置不存在");
        }

        tmsIntegration.setEnabled(enabled);
        return this.updateById(tmsIntegration);
    }
}
