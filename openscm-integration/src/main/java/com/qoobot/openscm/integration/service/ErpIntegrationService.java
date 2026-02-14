package com.qoobot.openscm.integration.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qoobot.openscm.integration.entity.ErpIntegration;

/**
 * ERP集成服务接口
 */
public interface ErpIntegrationService extends IService<ErpIntegration> {

    /**
     * 分页查询ERP集成配置
     */
    Page<ErpIntegration> selectPage(Page<ErpIntegration> page, String integrationName, String erpType, Boolean enabled);

    /**
     * 测试ERP连接
     */
    Boolean testConnection(Long id);

    /**
     * 手动同步ERP数据
     */
    Boolean syncData(Long id, String syncType);

    /**
     * 启用/禁用ERP集成
     */
    Boolean updateEnabled(Long id, Boolean enabled);
}
