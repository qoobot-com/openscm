package com.qoobot.openscm.integration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qoobot.openscm.integration.entity.ErpSyncLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * ERP同步日志Mapper
 */
@Mapper
public interface ErpSyncLogMapper extends BaseMapper<ErpSyncLog> {
}
