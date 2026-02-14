package com.qoobot.openscm.finance.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 成本核算实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("cost_accounting")
public class CostAccounting {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 核算单号
     */
    private String accountingNo;

    /**
     * 核算期间
     */
    private String accountingPeriod;

    /**
     * 成本类型（0-采购成本，1-销售成本，2-库存成本，3-运输成本）
     */
    private Integer costType;

    /**
     * 关联订单ID
     */
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 物料ID
     */
    private Long materialId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 单位成本
     */
    private BigDecimal unitCost;

    /**
     * 总成本
     */
    private BigDecimal totalCost;

    /**
     * 核算日期
     */
    private LocalDate accountingDate;

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
