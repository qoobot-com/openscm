package com.qoobot.openscm.common.aspect;

import com.qoobot.openscm.common.annotation.OperationLog;
import com.qoobot.openscm.common.service.OperLogService;
import com.qoobot.openscm.common.util.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 操作日志切面
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OperationLogAspect {

    private final HttpServletRequest request;
    private final OperLogService operLogService;

    @Pointcut("@annotation(com.qoobot.openscm.common.annotation.OperationLog)")
    public void logPointCut() {}

    @Around("logPointCut() && @annotation(operationLog)")
    public Object around(ProceedingJoinPoint point, OperationLog operationLog) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = null;
        Exception exception = null;

        try {
            result = point.proceed();
            return result;
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            long costTime = System.currentTimeMillis() - beginTime;
            saveOperLog(point, operationLog, costTime, exception, result);
        }
    }

    /**
     * 保存操作日志
     */
    private void saveOperLog(ProceedingJoinPoint point, OperationLog operationLog, long costTime, Exception exception, Object result) {
        try {
            String ip = getIpAddr(request);
            String operName = "anonymous";
            try {
                operName = SecurityUtils.getCurrentUsername();
            } catch (Exception e) {
                // 用户未登录时使用匿名
            }

            // 获取请求参数
            Map<String, Object> params = new HashMap<>();
            Object[] args = point.getArgs();
            if (args != null && args.length > 0) {
                params.put("args", args);
            }

            // 调用服务保存日志
            operLogService.saveOperLog(
                    operationLog.module(),
                    operationLog.businessType().ordinal(),
                    point.getSignature().getName(),
                    request.getMethod(),
                    request.getRequestURI(),
                    ip,
                    "未知", // TODO: 根据IP获取地理位置
                    operName,
                    exception != null ? 1 : 0,
                    exception != null ? exception.getMessage() : null,
                    costTime,
                    LocalDateTime.now(),
                    params,
                    result
            );

            log.info("操作日志 - 用户: {}, IP: {}, URI: {}, 模块: {}, 操作: {}, 耗时: {}ms",
                    operName, ip, request.getRequestURI(),
                    operationLog.module(), operationLog.businessType().name(), costTime);

        } catch (Exception e) {
            log.error("保存操作日志失败: {}", e.getMessage());
        }
    }

    /**
     * 获取IP地址
     */
    private String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}
