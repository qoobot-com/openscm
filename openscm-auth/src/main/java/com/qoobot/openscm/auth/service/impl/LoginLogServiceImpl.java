package com.qoobot.openscm.auth.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.openscm.auth.entity.LoginLog;
import com.qoobot.openscm.auth.mapper.LoginLogMapper;
import com.qoobot.openscm.auth.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录日志服务实现
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recordLoginLog(LoginLog loginLog) {
        loginLogMapper.insert(loginLog);
    }

    @Override
    public IPage<LoginLog> logPage(Page<LoginLog> page, String username, Integer loginType,
                                        Integer status, LocalDateTime startTime, LocalDateTime endTime) {
        return loginLogMapper.selectLogPage(page, username, loginType, status, startTime, endTime);
    }

    @Override
    public LoginLog getLatestLogin(Long userId) {
        return loginLogMapper.selectLatestByUserId(userId);
    }

    @Override
    public Integer countFailedAttempts(String username, LocalDateTime startTime) {
        return loginLogMapper.countFailedAttempts(username, startTime);
    }

    @Override
    public Map<String, Object> statistics() {
        Map<String, Object> result = new HashMap<>();
        
        // 总登录次数
        Long totalLogins = loginLogMapper.selectCount(null);
        result.put("totalLogins", totalLogins);
        
        // 成功登录次数
        Long successLogins = loginLogMapper.selectCount(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<LoginLog>()
                .eq(LoginLog::getStatus, 0)
        );
        result.put("successLogins", successLogins);
        
        // 失败登录次数
        Long failedLogins = loginLogMapper.selectCount(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<LoginLog>()
                .eq(LoginLog::getStatus, 1)
        );
        result.put("failedLogins", failedLogins);
        
        // 成功率
        if (totalLogins != null && totalLogins > 0) {
            Double successRate = (double) successLogins / totalLogins * 100;
            result.put("successRate", successRate);
        }
        
        return result;
    }
}
