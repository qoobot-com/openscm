package com.qoobot.openscm.auth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.common.core.domain.Result;
import com.qoobot.openscm.auth.entity.LoginLog;
import com.qoobot.openscm.auth.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 登录日志控制器
 */
@RestController
@RequestMapping("/auth/loginLog")
public class LoginLogController {

    @Autowired
    private LoginLogService loginLogService;

    /**
     * 记录登录日志
     */
    @PostMapping("/record")
    public Result<Void> recordLoginLog(@RequestBody LoginLog loginLog) {
        loginLogService.recordLoginLog(loginLog);
        return Result.success();
    }

    /**
     * 分页查询登录日志
     */
    @GetMapping("/page")
    public Result<IPage<LoginLog>> logPage(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Integer loginType,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        Page<LoginLog> page = new Page<>(current, size);
        IPage<LoginLog> result = loginLogService.logPage(page, username, loginType, status, startTime, endTime);
        return Result.success(result);
    }

    /**
     * 登录统计
     */
    @GetMapping("/statistics")
    public Result<Map<String, Object>> statistics() {
        Map<String, Object> statistics = loginLogService.statistics();
        return Result.success(statistics);
    }
}
