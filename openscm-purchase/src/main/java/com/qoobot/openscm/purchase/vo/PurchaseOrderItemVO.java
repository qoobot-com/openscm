package com.qoobot.openscm.purchase.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 采购订单明细VO
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Data
public class PurchaseOrderItemVO {

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
     * 采购数量
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
     * 已收货数量
     */
    private BigDecimal receivedQuantity;

    /**
     * 收货状态
     */
    private Integer receiveStatus;

    /**
     * 收货状态名称
     */
    private String receiveStatusName;
}
