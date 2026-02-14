package com.qoobot.openscm.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户信息 VO
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户信息 VO")
public class UserInfoVO {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "部门ID")
    private Long deptId;

    @Schema(description = "部门名称")
    private String deptName;

    @Schema(description = "角色列表")
    private List<RoleVO> roles;

    @Schema(description = "权限列表")
    private List<String> permissions;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "角色 VO")
    public static class RoleVO {
        @Schema(description = "角色ID")
        private Long id;

        @Schema(description = "角色名称")
        private String roleName;

        @Schema(description = "角色编码")
        private String roleCode;
    }
}
