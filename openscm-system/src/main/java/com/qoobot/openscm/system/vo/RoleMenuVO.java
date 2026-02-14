package com.qoobot.openscm.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 角色菜单VO
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "角色菜单VO")
public class RoleMenuVO {

    @Schema(description = "角色ID")
    private Long roleId;

    @Schema(description = "菜单ID列表")
    private List<Long> menuIds;
}
