package com.qoobot.openscm.inventory.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 货位信息实体
 */
@Data
@TableName("warehouse_location")
public class WarehouseLocation {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 货位编码
     */
    private String locationCode;

    /**
     * 仓库ID
     */
    private Long warehouseId;

    /**
     * 区域编码
     */
    private String zoneCode;

    /**
     * 货架编码
     */
    private String shelfCode;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 货位类型 1-普通货位 2-冷藏货位 3-危险品货位 4-大件货位
     */
    private Integer locationType;

    /**
     * 货位容量
     */
    private BigDecimal capacity;

    /**
     * 已使用容量
     */
    private BigDecimal usedCapacity;

    /**
     * 货位状态 0-空闲 1-占用 2-锁定 3-维修
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
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /**
     * 删除标记 0-未删除 1-已删除
     */
    @TableLogic
    private Integer deleted;
}
