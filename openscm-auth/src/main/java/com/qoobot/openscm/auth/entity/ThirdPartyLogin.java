package com.qoobot.openscm.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 第三方登录实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("third_party_login")
public class ThirdPartyLogin {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 登录类型（0-微信，1-钉钉，2-企业微信）
     */
    private Integer loginType;

    /**
     * 第三方用户ID
     */
    private String thirdPartyUserId;

    /**
     * 第三方用户昵称
     */
    private String thirdPartyNickname;

    /**
     * 第三方用户头像
     */
    private String thirdPartyAvatar;

    /**
     * 联合ID（用于绑定）
     */
    private String unionId;

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 令牌过期时间
     */
    private LocalDateTime tokenExpireTime;

    /**
     * 绑定状态（0-未绑定，1-已绑定）
     */
    private Integer bindStatus;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

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
