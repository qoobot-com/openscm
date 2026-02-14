package com.qoobot.openscm.purchase.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 采购计划明细VO
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Data
public class PurchasePlanItemVO {

    /**
     * 明细ID
     */
    private Long id;

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
     * 计划数量
     */
    private BigDecimal quantity;

    /**
     * 预估单价
     */
    private BigDecimal estimatedPrice;

    /**
     * 预估金额
     */
    private BigDecimal estimatedAmount;
}
