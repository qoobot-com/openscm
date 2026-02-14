package com.qoobot.openscm.integration.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qoobot.openscm.integration.entity.WmsIntegration;

/**
 * WMS集成服务接口
 */
public interface WmsIntegrationService extends IService<WmsIntegration> {

    /**
     * 分页查询WMS集成配置
     */
    Page<WmsIntegration> selectPage(Page<WmsIntegration> page, String integrationName, String wmsType, Boolean enabled);

    /**
     * 测试WMS连接
     */
    Boolean testConnection(Long id);

    /**
     * 手动同步WMS数据
     */
    Boolean syncData(Long id, String syncType);

    /**
     * 启用/禁用WMS集成
     */
    Boolean updateEnabled(Long id, Boolean enabled);
}
