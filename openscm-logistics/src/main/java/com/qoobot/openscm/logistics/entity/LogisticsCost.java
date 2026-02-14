package com.qoobot.openscm.logistics.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 物流费用实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("logistics_cost")
public class LogisticsCost {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 费用编号
     */
    private String costNo;

    /**
     * 关联运输计划ID
     */
    private Long planId;

    /**
     * 关联配送单ID
     */
    private Long deliveryId;

    /**
     * 费用类型（0-运输费，1-配送费，2-保险费，3-装卸费，4-其他费用）
     */
    private Integer costType;

    /**
     * 费用金额
     */
    private BigDecimal amount;

    /**
     * 费用说明
     */
    private String costDesc;

    /**
     * 费用日期
     */
    private LocalDate costDate;

    /**
     * 支付状态（0-待支付，1-已支付，2-已结算）
     */
    private Integer paymentStatus;

    /**
     * 支付时间
     */
    private LocalDateTime paymentTime;

    /**
     * 经办人
     */
    private String handler;

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
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
