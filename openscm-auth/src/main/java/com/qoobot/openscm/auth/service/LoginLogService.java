package com.qoobot.openscm.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qoobot.openscm.auth.entity.LoginLog;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 登录日志服务接口
 */
public interface LoginLogService extends IService<LoginLog> {

    /**
     * 记录登录日志
     */
    void recordLoginLog(LoginLog loginLog);

    /**
     * 分页查询登录日志
     */
    IPage<LoginLog> logPage(Page<LoginLog> page, String username, Integer loginType,
                               Integer status, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取用户最近登录日志
     */
    LoginLog getLatestLogin(Long userId);

    /**
     * 统计登录失败次数
     */
    Integer countFailedAttempts(String username, LocalDateTime startTime);

    /**
     * 登录统计
     */
    Map<String, Object> statistics();
}
