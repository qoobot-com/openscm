package com.qoobot.openscm.integration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.openscm.common.exception.BusinessException;
import com.qoobot.openscm.common.util.SecurityUtils;
import com.qoobot.openscm.integration.entity.ErpIntegration;
import com.qoobot.openscm.integration.entity.ErpSyncLog;
import com.qoobot.openscm.integration.mapper.ErpIntegrationMapper;
import com.qoobot.openscm.integration.mapper.ErpSyncLogMapper;
import com.qoobot.openscm.integration.service.ErpIntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * ERP集成服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ErpIntegrationServiceImpl extends ServiceImpl<ErpIntegrationMapper, ErpIntegration> implements ErpIntegrationService {

    private final ErpSyncLogMapper erpSyncLogMapper;

    @Override
    public Page<ErpIntegration> selectPage(Page<ErpIntegration> page, String integrationName, String erpType, Boolean enabled) {
        LambdaQueryWrapper<ErpIntegration> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ErpIntegration::getTenantId, SecurityUtils.getTenantId());
        if (integrationName != null && !integrationName.isEmpty()) {
            wrapper.like(ErpIntegration::getIntegrationName, integrationName);
        }
        if (erpType != null && !erpType.isEmpty()) {
            wrapper.eq(ErpIntegration::getErpType, erpType);
        }
        if (enabled != null) {
            wrapper.eq(ErpIntegration::getEnabled, enabled);
        }
        wrapper.orderByDesc(ErpIntegration::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public Boolean testConnection(Long id) {
        ErpIntegration erpIntegration = this.getById(id);
        if (erpIntegration == null) {
            throw new BusinessException("ERP集成配置不存在");
        }

        try {
            // TODO: 实现实际的连接测试逻辑
            // 这里模拟测试连接成功
            log.info("测试ERP连接: {}, 类型: {}", erpIntegration.getIntegrationName(), erpIntegration.getErpType());
            return true;
        } catch (Exception e) {
            log.error("测试ERP连接失败", e);
            throw new BusinessException("测试ERP连接失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean syncData(Long id, String syncType) {
        ErpIntegration erpIntegration = this.getById(id);
        if (erpIntegration == null) {
            throw new BusinessException("ERP集成配置不存在");
        }

        if (!erpIntegration.getEnabled()) {
            throw new BusinessException("ERP集成未启用");
        }

        // 创建同步日志
        ErpSyncLog syncLog = new ErpSyncLog();
        syncLog.setErpConfigId(id);
        syncLog.setSyncType(syncType);
        syncLog.setSyncDirection("PULL");
        syncLog.setStartTime(LocalDateTime.now());
        syncLog.setSyncStatus("PROCESSING");
        syncLog.setTenantId(SecurityUtils.getTenantId());
        erpSyncLogMapper.insert(syncLog);

        try {
            // TODO: 实现实际的数据同步逻辑
            log.info("开始同步ERP数据: {}, 类型: {}", erpIntegration.getIntegrationName(), syncType);

            // 模拟同步成功
            syncLog.setEndTime(LocalDateTime.now());
            syncLog.setSyncStatus("SUCCESS");
            syncLog.setSuccessCount(100);
            syncLog.setFailureCount(0);
            erpSyncLogMapper.updateById(syncLog);

            // 更新最后同步时间
            erpIntegration.setLastSyncTime(LocalDateTime.now());
            erpIntegration.setSyncStatus("SUCCESS");
            this.updateById(erpIntegration);

            return true;
        } catch (Exception e) {
            log.error("同步ERP数据失败", e);

            syncLog.setEndTime(LocalDateTime.now());
            syncLog.setSyncStatus("FAILED");
            syncLog.setErrorMessage(e.getMessage());
            syncLog.setSuccessCount(0);
            syncLog.setFailureCount(1);
            erpSyncLogMapper.updateById(syncLog);

            erpIntegration.setSyncStatus("FAILED");
            erpIntegration.setErrorMessage(e.getMessage());
            this.updateById(erpIntegration);

            throw new BusinessException("同步ERP数据失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateEnabled(Long id, Boolean enabled) {
        ErpIntegration erpIntegration = this.getById(id);
        if (erpIntegration == null) {
            throw new BusinessException("ERP集成配置不存在");
        }

        erpIntegration.setEnabled(enabled);
        return this.updateById(erpIntegration);
    }
}
