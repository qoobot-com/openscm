package com.qoobot.openscm.integration.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qoobot.openscm.integration.entity.TmsIntegration;

/**
 * TMS集成服务接口
 */
public interface TmsIntegrationService extends IService<TmsIntegration> {

    /**
     * 分页查询TMS集成配置
     */
    Page<TmsIntegration> selectPage(Page<TmsIntegration> page, String integrationName, String tmsType, Boolean enabled);

    /**
     * 测试TMS连接
     */
    Boolean testConnection(Long id);

    /**
     * 手动同步TMS数据
     */
    Boolean syncData(Long id, String syncType);

    /**
     * 启用/禁用TMS集成
     */
    Boolean updateEnabled(Long id, Boolean enabled);
}
