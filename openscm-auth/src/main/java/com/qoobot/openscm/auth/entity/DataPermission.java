package com.qoobot.openscm.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 数据权限实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("data_permission")
public class DataPermission {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限编码
     */
    private String permissionCode;

    /**
     * 权限类型（0-部门数据，1-个人数据，2-全部数据，3-自定义）
     */
    private Integer permissionType;

    /**
     * 部门ID（部门数据权限使用）
     */
    private Long deptId;

    /**
     * 部门ID列表（自定义权限使用）
     */
    private String deptIds;

    /**
     * 用户ID列表（自定义权限使用）
     */
    private String userIds;

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
