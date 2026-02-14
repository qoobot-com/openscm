package com.qoobot.openscm.common.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JWT工具类测试
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@SpringBootTest
class JwtUtilsTest {

    private JwtUtils jwtUtils;

    private String testSecret = "TestSecretKeyForJWTTokenGeneration123456";
    private Long testExpiration = 86400000L;

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();
        // 使用反射设置私有字段
        try {
            var secretField = JwtUtils.class.getDeclaredField("secret");
            secretField.setAccessible(true);
            secretField.set(jwtUtils, testSecret);

            var expirationField = JwtUtils.class.getDeclaredField("expiration");
            expirationField.setAccessible(true);
            expirationField.set(jwtUtils, testExpiration);
        } catch (Exception e) {
            fail("Failed to set up test fields");
        }
    }

    @Test
    void testGenerateToken_Success() {
        // 测试生成Token
        String token = jwtUtils.generateToken(1L, "testuser");

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testValidateToken_ValidToken() {
        // 测试验证有效Token
        String token = jwtUtils.generateToken(1L, "testuser");
        boolean isValid = jwtUtils.validateToken(token);

        assertTrue(isValid);
    }

    @Test
    void testValidateToken_InvalidToken() {
        // 测试验证无效Token
        String invalidToken = "invalid.token.string";
        boolean isValid = jwtUtils.validateToken(invalidToken);

        assertFalse(isValid);
    }

    @Test
    void testGetUserIdFromToken_Success() {
        // 测试从Token获取用户ID
        Long userId = 123L;
        String token = jwtUtils.generateToken(userId, "testuser");

        Long extractedUserId = jwtUtils.getUserIdFromToken(token);

        assertEquals(userId, extractedUserId);
    }

    @Test
    void testGetUsernameFromToken_Success() {
        // 测试从Token获取用户名
        String username = "testuser";
        String token = jwtUtils.generateToken(1L, username);

        String extractedUsername = jwtUtils.getUsernameFromToken(token);

        assertEquals(username, extractedUsername);
    }

    @Test
    void testRefreshToken_Success() {
        // 测试刷新Token
        String oldToken = jwtUtils.generateToken(1L, "testuser");
        String newToken = jwtUtils.refreshToken(oldToken);

        assertNotNull(newToken);
        assertFalse(newToken.isEmpty());
        assertNotEquals(oldToken, newToken);
    }
}
