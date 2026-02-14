package com.qoobot.openscm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qoobot.openscm.common.service.OperLogService;
import com.qoobot.openscm.system.entity.SysOperLog;

/**
 * 操作日志服务接口
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
public interface SysOperLogService extends IService<SysOperLog>, OperLogService {

    /**
     * 保存操作日志
     *
     * @param operLog 操作日志
     */
    void saveOperLog(SysOperLog operLog);
}
