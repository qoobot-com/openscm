package com.qoobot.openscm.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.openscm.system.entity.SysOperLog;
import com.qoobot.openscm.system.mapper.SysOperLogMapper;
import com.qoobot.openscm.system.service.SysOperLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 操作日志服务实现
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements SysOperLogService {

    @Async
    @Override
    public void saveOperLog(SysOperLog operLog) {
        try {
            save(operLog);
            log.info("操作日志保存成功: {}", operLog.getModule());
        } catch (Exception e) {
            log.error("操作日志保存失败: {}", e.getMessage());
        }
    }

    @Override
    public void saveOperLog(String module, Integer businessType, String method,
                          String requestMethod, String url, String ip, String location,
                          String operName, Integer status, String errorMsg,
                          Long costTime, LocalDateTime operTime, Map<String, Object> params, Object result) {
        SysOperLog operLog = new SysOperLog();
        operLog.setModule(module);
        operLog.setBusinessType(businessType);
        operLog.setMethod(method);
        operLog.setRequestMethod(requestMethod);
        operLog.setOperUrl(url);
        operLog.setOperatorType(1); // 1-后台用户
        operLog.setOperIp(ip);
        operLog.setOperLocation(location);
        operLog.setOperName(operName);
        operLog.setStatus(status);
        operLog.setErrorMsg(errorMsg);
        operLog.setCostTime(costTime);
        operLog.setOperTime(operTime);

        // 保存请求参数
        if (params != null && !params.isEmpty()) {
            try {
                operLog.setOperParam(JSON.toJSONString(params));
            } catch (Exception e) {
                log.error("序列化请求参数失败: {}", e.getMessage());
            }
        }

        // 保存返回结果
        if (result != null) {
            try {
                operLog.setJsonResult(JSON.toJSONString(result));
            } catch (Exception e) {
                log.error("序列化返回结果失败: {}", e.getMessage());
            }
        }

        saveOperLog(operLog);
    }
}
