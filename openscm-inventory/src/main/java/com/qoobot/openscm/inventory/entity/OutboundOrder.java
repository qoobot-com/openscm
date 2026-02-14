package com.qoobot.openscm.inventory.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 出库单实体
 */
@Data
@TableName("outbound_order")
public class OutboundOrder {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 出库单编号
     */
    private String outboundCode;

    /**
     * 出库类型 1-销售出库 2-生产领料 3-调拨出库 4-退货出库 5-报废出库 6-其他出库
     */
    private Integer outboundType;

    /**
     * 仓库ID
     */
    private Long warehouseId;

    /**
     * 目标仓库ID(调拨时使用)
     */
    private Long targetWarehouseId;

    /**
     * 来源单据类型
     */
    private String sourceType;

    /**
     * 来源单据ID
     */
    private Long sourceId;

    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 出库日期
     */
    private LocalDate outboundDate;

    /**
     * 状态 0-待出库 1-出库中 2-已出库 3-已完成
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
     * 收货人
     */
    private String receiver;

    /**
     * 收货地址
     */
    private String receiverAddress;

    /**
     * 联系电话
     */
    private String contactPhone;

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
