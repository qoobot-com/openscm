package com.qoobot.openscm.inventory.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 库存盘点单实体
 */
@Data
@TableName("inventory_check")
public class InventoryCheck {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 盘点单编号
     */
    private String checkCode;

    /**
     * 仓库ID
     */
    private Long warehouseId;

    /**
     * 盘点类型 1-全盘 2-抽样盘点 3-循环盘点
     */
    private Integer checkType;

    /**
     * 盘点日期
     */
    private LocalDate checkDate;

    /**
     * 状态 0-待盘点 1-盘点中 2-盘点完成 3-已审核
     */
    private Integer status;

    /**
     * 盘点总数量
     */
    private BigDecimal checkQuantity;

    /**
     * 系统数量
     */
    private BigDecimal systemQuantity;

    /**
     * 盘盈数量
     */
    private BigDecimal profitQuantity;

    /**
     * 盘亏数量
     */
    private BigDecimal lossQuantity;

    /**
     * 盘点人
     */
    private String checker;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新人
     */
    private String updatedBy;

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
