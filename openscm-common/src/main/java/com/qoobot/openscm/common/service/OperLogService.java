package com.qoobot.openscm.common.service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 操作日志服务接口
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
public interface OperLogService {

    /**
     * 保存操作日志
     *
     * @param module        模块名称
     * @param businessType  业务类型
     * @param method        方法名称
     * @param requestMethod 请求方法
     * @param url           请求URL
     * @param ip            请求IP
     * @param location      操作地点
     * @param operName      操作人
     * @param status        操作状态
     * @param errorMsg      错误信息
     * @param costTime      耗时
     * @param operTime      操作时间
     * @param params        请求参数
     * @param result        返回结果
     */
    void saveOperLog(String module, Integer businessType, String method,
                   String requestMethod, String url, String ip, String location,
                   String operName, Integer status, String errorMsg,
                   Long costTime, LocalDateTime operTime, Map<String, Object> params, Object result);
}
