package com.qoobot.openscm.purchase.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 采购统计实体类
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Data
@TableName("purchase_statistics")
public class PurchaseStatistics {

    /**
     * 统计ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 统计类型: 1-按月 2-按季度 3-按年
     */
    private Integer statisticType;

    /**
     * 统计周期
     */
    private String period;

    /**
     * 采购订单数量
     */
    private Integer orderCount;

    /**
     * 采购总金额
     */
    private BigDecimal totalAmount;

    /**
     * 已付款金额
     */
    private BigDecimal paidAmount;

    /**
     * 未付款金额
     */
    private BigDecimal unpaidAmount;

    /**
     * 供应商数量
     */
    private Integer supplierCount;

    /**
     * 平均订单金额
     */
    private BigDecimal avgOrderAmount;

    /**
     * 创建时间
     */
    private String createTime;
}
