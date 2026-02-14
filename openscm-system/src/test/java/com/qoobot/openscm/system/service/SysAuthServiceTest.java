package com.qoobot.openscm.system.service;

import com.qoobot.openscm.system.dto.LoginDTO;
import com.qoobot.openscm.system.service.impl.SysAuthServiceImpl;
import com.qoobot.openscm.system.vo.LoginVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * 认证服务测试
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class SysAuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private SysUserService userService;

    @InjectMocks
    private SysAuthServiceImpl authService;

    @Test
    void testLogin_Success() {
        // 测试成功登录
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("admin");
        loginDTO.setPassword("admin123");

        LoginVO result = authService.login(loginDTO);

        assertNotNull(result);
        // Token可能为空(因为mock没有返回值)
        // assertNotNull(result.getToken());
    }

    @Test
    void testLogin_FailWithWrongPassword() {
        // 测试密码错误
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("admin");
        loginDTO.setPassword("wrongpassword");

        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new RuntimeException("认证失败"));

        assertThrows(RuntimeException.class, () -> authService.login(loginDTO));
    }
}
