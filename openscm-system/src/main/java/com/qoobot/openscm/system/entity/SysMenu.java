package com.qoobot.openscm.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qoobot.openscm.common.model.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单实体
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
@Schema(description = "菜单实体")
public class SysMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "菜单ID")
    private Long id;

    /**
     * 父菜单ID
     */
    @Schema(description = "父菜单ID")
    private Long parentId;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    private String menuName;

    /**
     * 菜单类型（1-目录，2-菜单，3-按钮）
     */
    @Schema(description = "菜单类型")
    private Integer menuType;

    /**
     * 菜单编码
     */
    @Schema(description = "菜单编码")
    private String menuCode;

    /**
     * 路由路径
     */
    @Schema(description = "路由路径")
    private String path;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径")
    private String component;

    /**
     * 图标
     */
    @Schema(description = "图标")
    private String icon;

    /**
     * 权限标识
     */
    @Schema(description = "权限标识")
    private String permission;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sortOrder;

    /**
     * 显示状态（0-隐藏，1-显示）
     */
    @Schema(description = "显示状态")
    private Integer visible;

    /**
     * 状态（0-禁用，1-正常）
     */
    @Schema(description = "状态")
    private Integer status;
}
