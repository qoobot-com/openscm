package com.qoobot.openscm.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单退货明细实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order_return_item")
public class OrderReturnItem {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 退货单ID
     */
    private Long returnId;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单明细ID
     */
    private Long orderItemId;

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
     * 规格型号
     */
    private String specification;

    /**
     * 单位
     */
    private String unit;

    /**
     * 原订单数量
     */
    private BigDecimal originalQuantity;

    /**
     * 退货数量
     */
    private BigDecimal returnQuantity;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 退货金额
     */
    private BigDecimal returnAmount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
