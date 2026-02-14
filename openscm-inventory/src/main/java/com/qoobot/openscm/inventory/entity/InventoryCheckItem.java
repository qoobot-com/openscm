package com.qoobot.openscm.inventory.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 库存盘点单明细实体
 */
@Data
@TableName("inventory_check_item")
public class InventoryCheckItem {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 盘点单ID
     */
    private Long checkId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 规格型号
     */
    private String specification;

    /**
     * 单位
     */
    private String unit;

    /**
     * 货位ID
     */
    private Long locationId;

    /**
     * 系统数量
     */
    private BigDecimal systemQuantity;

    /**
     * 实盘数量
     */
    private BigDecimal actualQuantity;

    /**
     * 差异数量
     */
    private BigDecimal diffQuantity;

    /**
     * 差异金额
     */
    private BigDecimal diffAmount;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 差异原因
     */
    private String reason;

    /**
     * 处理方式 1-调整库存 2-报废 3-其他
     */
    private Integer handleMethod;

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
}
