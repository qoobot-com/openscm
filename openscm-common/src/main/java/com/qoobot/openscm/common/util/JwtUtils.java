package com.qoobot.openscm.common.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * JWT 工具类
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Component
public class JwtUtils {

    @Value("${jwt.secret:OpenSCM2024SecretKeyForJWTTokenGeneration}")
    private String secret;

    @Value("${jwt.expiration:86400000}")
    private Long expiration;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成 JWT Token
     *
     * @param subject   主题（通常是用户ID）
     * @param claims    自定义声明
     * @return          JWT Token
     */
    public String generateToken(String subject, Map<String, Object> claims) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSecretKey())
                .compact();
    }

    /**
     * 生成 JWT Token（简化版）
     *
     * @param userId    用户ID
     * @param username  用户名
     * @return          JWT Token
     */
    public String generateToken(Long userId, String username) {
        return generateToken(userId.toString(), Map.of(
                "userId", userId,
                "username", username
        ));
    }

    /**
     * 解析 JWT Token
     *
     * @param token     JWT Token
     * @return          Claims
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 从 Token 中获取用户ID
     *
     * @param token     JWT Token
     * @return          用户ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("userId", Long.class);
    }

    /**
     * 从 Token 中获取用户名
     *
     * @param token     JWT Token
     * @return          用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("username", String.class);
    }

    /**
     * 验证 Token 是否有效
     *
     * @param token     JWT Token
     * @return          是否有效
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * 刷新 Token
     *
     * @param token     旧 Token
     * @return          新 Token
     */
    public String refreshToken(String token) {
        Claims claims = parseToken(token);
        return generateToken(claims.getSubject(), claims);
    }
}
