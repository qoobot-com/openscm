package com.qoobot.openscm.system.service.impl;

import com.qoobot.openscm.common.exception.BusinessException;
import com.qoobot.openscm.system.dto.LoginDTO;
import com.qoobot.openscm.system.entity.SysUser;
import com.qoobot.openscm.system.service.SysAuthService;
import com.qoobot.openscm.system.service.SysUserService;
import com.qoobot.openscm.system.vo.LoginVO;
import com.qoobot.openscm.system.vo.UserInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 认证服务实现
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysAuthServiceImpl implements SysAuthService {

    private final SysUserService sysUserService;
    private final PasswordEncoder passwordEncoder;

    // TODO: 注入 AuthenticationManager 和 TokenProvider

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        // 查询用户
        SysUser user = sysUserService.getByUsername(loginDTO.getUsername());
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }

        // 验证密码
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        // 检查用户状态
        if (user.getStatus() == 0) {
            throw new BusinessException("用户已被禁用");
        }

        // TODO: 生成 JWT Token
        String accessToken = "mock-access-token-" + System.currentTimeMillis();
        String refreshToken = "mock-refresh-token-" + System.currentTimeMillis();

        LoginVO loginVO = new LoginVO();
        loginVO.setAccessToken(accessToken);
        loginVO.setRefreshToken(refreshToken);
        loginVO.setExpiresIn(7200L); // 2小时

        log.info("用户登录成功: {}", loginDTO.getUsername());
        return loginVO;
    }

    @Override
    public void logout() {
        // TODO: 清除 Token
        log.info("用户退出登录");
    }

    @Override
    public UserInfoVO getUserInfo() {
        return sysUserService.getCurrentUserInfo();
    }

    @Override
    public LoginVO refreshToken(String refreshToken) {
        // TODO: 验证刷新令牌并生成新的访问令牌
        String accessToken = "mock-new-access-token-" + System.currentTimeMillis();
        LoginVO loginVO = new LoginVO();
        loginVO.setAccessToken(accessToken);
        loginVO.setRefreshToken(refreshToken);
        loginVO.setExpiresIn(7200L);
        return loginVO;
    }
}
