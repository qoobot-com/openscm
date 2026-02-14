package com.qoobot.openscm.inventory.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 入库单实体
 */
@Data
@TableName("inbound_order")
public class InboundOrder {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 入库单编号
     */
    private String inboundCode;

    /**
     * 入库类型 1-采购入库 2-生产入库 3-退货入库 4-调拨入库 5-其他入库
     */
    private Integer inboundType;

    /**
     * 仓库ID
     */
    private Long warehouseId;

    /**
     * 来源单据类型
     */
    private String sourceType;

    /**
     * 来源单据ID
     */
    private Long sourceId;

    /**
     * 供应商ID
     */
    private Long supplierId;

    /**
     * 入库日期
     */
    private LocalDate inboundDate;

    /**
     * 状态 0-待入库 1-入库中 2-已入库 3-已完成
     */
    private Integer status;

    /**
     * 总数量
     */
    private BigDecimal totalQuantity;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 经办人
     */
    private String handler;

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
