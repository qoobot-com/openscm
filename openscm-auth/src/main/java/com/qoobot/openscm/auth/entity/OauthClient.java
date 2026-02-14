package com.qoobot.openscm.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * OAuth客户端实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oauth_client")
public class OauthClient {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 授权类型（password,client_credentials,refresh_token,authorization_code）
     */
    private String grantTypes;

    /**
     * 授权范围
     */
    private String scopes;

    /**
     * 重定向URI
     */
    private String redirectUris;

    /**
     * 访问令牌有效期（秒）
     */
    private Integer accessTokenValidity;

    /**
     * 刷新令牌有效期（秒）
     */
    private Integer refreshTokenValidity;

    /**
     * 是否信任（自动批准）
     */
    private Boolean trusted;

    /**
     * 客户端状态（0-禁用，1-启用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
