package com.qoobot.openscm.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 订单退货实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order_return")
public class OrderReturn {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 退货单号
     */
    private String returnNo;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 退货日期
     */
    private LocalDate returnDate;

    /**
     * 退货原因
     */
    private String returnReason;

    /**
     * 退货类型（0-质量问题，1-发错货，2-客户取消，3-其他）
     */
    private Integer returnType;

    /**
     * 退货数量
     */
    private BigDecimal returnQuantity;

    /**
     * 退货金额
     */
    private BigDecimal returnAmount;

    /**
     * 退款状态（0-待审核，1-审核通过，2-已退款，3-审核拒绝）
     */
    private Integer refundStatus;

    /**
     * 审核人
     */
    private String approvedBy;

    /**
     * 审核时间
     */
    private LocalDateTime approveTime;

    /**
     * 审核意见
     */
    private String approveRemark;

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
