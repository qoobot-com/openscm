package com.qoobot.openscm.purchase.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qoobot.openscm.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 采购入库单实体类
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("purchase_inbound")
public class PurchaseInbound extends BaseEntity {

    /**
     * 入库单编号
     */
    private String inboundNo;

    /**
     * 采购订单ID
     */
    private Long orderId;

    /**
     * 采购订单编号
     */
    private String orderNo;

    /**
     * 供应商ID
     */
    private Long supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 仓库ID
     */
    private Long warehouseId;

    /**
     * 仓库名称
     */
    private String warehouseName;

    /**
     * 入库状态: 0-待入库 1-待质检 2-质检中 3-质检合格 4-质检不合格 5-已完成
     */
    private Integer status;

    /**
     * 是否质检: 0-不需要 1-需要
     */
    private Integer needQualityCheck;

    /**
     * 质检人ID
     */
    private Long qualityInspectorId;

    /**
     * 质检人姓名
     */
    private String qualityInspectorName;

    /**
     * 质检时间
     */
    private LocalDate qualityCheckDate;

    /**
     * 质检结果: 1-合格 2-不合格
     */
    private Integer qualityResult;

    /**
     * 质检备注
     */
    private String qualityRemark;

    /**
     * 入库人ID
     */
    private Long inbounderId;

    /**
     * 入库人姓名
     */
    private String inbounderName;

    /**
     * 入库时间
     */
    private LocalDate inboundDate;

    /**
     * 备注
     */
    private String remark;
}
