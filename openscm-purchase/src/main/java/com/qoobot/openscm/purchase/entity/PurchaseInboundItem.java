package com.qoobot.openscm.purchase.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qoobot.openscm.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 采购入库单明细实体类
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("purchase_inbound_item")
public class PurchaseInboundItem extends BaseEntity {

    /**
     * 入库单ID
     */
    private Long inboundId;

    /**
     * 入库单编号
     */
    private String inboundNo;

    /**
     * 订单明细ID
     */
    private Long orderItemId;

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
     * 订单数量
     */
    private BigDecimal orderQuantity;

    /**
     * 入库数量
     */
    private BigDecimal inboundQuantity;

    /**
     * 合格数量
     */
    private BigDecimal qualifiedQuantity;

    /**
     * 不合格数量
     */
    private BigDecimal unqualifiedQuantity;

    /**
     * 货位编码
     */
    private String locationCode;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 备注
     */
    private String remark;
}
