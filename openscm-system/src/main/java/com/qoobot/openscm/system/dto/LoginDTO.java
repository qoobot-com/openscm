package com.qoobot.openscm.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 登录 DTO
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Data
@Schema(description = "登录 DTO")
public class LoginDTO {

    @Schema(description = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description = "验证码")
    private String captcha;

    @Schema(description = "验证码key")
    private String captchaKey;
}
