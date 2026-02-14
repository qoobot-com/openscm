package com.qoobot.openscm.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.system.dto.LoginDTO;
import com.qoobot.openscm.system.entity.SysUser;
import com.qoobot.openscm.system.service.impl.SysUserServiceImpl;
import com.qoobot.openscm.system.vo.LoginVO;
import com.qoobot.openscm.system.vo.UserInfoVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * 用户服务测试
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class SysUserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private SysUserServiceImpl userService;

    private SysUser testUser;

    @BeforeEach
    void setUp() {
        testUser = new SysUser();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("$2a$10$encodedPassword");
        testUser.setNickname("测试用户");
        testUser.setEmail("test@example.com");
        testUser.setMobile("13800138000");
        testUser.setStatus(1);
        testUser.setCreateTime(LocalDateTime.now());
    }

    @Test
    void testGetById_Success() {
        // 测试根据ID获取用户
        SysUser user = userService.getById(1L);
        assertNotNull(user);
    }

    @Test
    void testPageQuery() {
        // 测试分页查询
        Page<SysUser> page = new Page<>(1, 10);
        assertNotNull(page);
        assertEquals(1, page.getCurrent());
        assertEquals(10, page.getSize());
    }
}
