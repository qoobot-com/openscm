package com.qoobot.openscm.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 用户 DTO
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Data
@Schema(description = "用户 DTO")
public class UserDTO {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_]{4,20}$", message = "用户名必须是4-20位字母、数字或下划线")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "昵称", required = true)
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @Schema(description = "邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Schema(description = "手机号")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String mobile;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "部门ID")
    private Long deptId;

    @Schema(description = "状态（0-禁用，1-正常）")
    private Integer status;

    @Schema(description = "角色ID列表")
    private java.util.List<Long> roleIds;
}
