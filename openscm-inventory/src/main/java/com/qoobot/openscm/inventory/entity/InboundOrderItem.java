package com.qoobot.openscm.inventory.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 入库单明细实体
 */
@Data
@TableName("inbound_order_item")
public class InboundOrderItem {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 入库单ID
     */
    private Long inboundId;

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
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 货位ID
     */
    private Long locationId;

    /**
     * 批次号
     */
    private String batchNumber;

    /**
     * 生产日期
     */
    private LocalDate productionDate;

    /**
     * 有效期至
     */
    private LocalDate expiryDate;

    /**
     * 供应商ID
     */
    private Long supplierId;

    /**
     * 实际数量
     */
    private BigDecimal actualQuantity;

    /**
     * 差异数量
     */
    private BigDecimal diffQuantity;

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
