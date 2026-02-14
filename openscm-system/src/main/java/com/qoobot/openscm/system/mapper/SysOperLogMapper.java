package com.qoobot.openscm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qoobot.openscm.system.entity.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志Mapper
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Mapper
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {
}
