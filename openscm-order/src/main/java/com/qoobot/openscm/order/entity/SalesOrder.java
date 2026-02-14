package com.qoobot.openscm.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 销售订单实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sales_order")
public class SalesOrder {

    @TableId(type = IdType.AUTO)
    private Long id;

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
     * 订单日期
     */
    private LocalDate orderDate;

    /**
     * 交货日期
     */
    private LocalDate deliveryDate;

    /**
     * 订单状态（0-草稿，1-待确认，2-已确认，3-生产中，4-已发货，5-已完成，6-已取消）
     */
    private Integer status;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 折扣金额
     */
    private BigDecimal discountAmount;

    /**
     * 实付金额
     */
    private BigDecimal paidAmount;

    /**
     * 付款方式（0-货到付款，1-银行转账，2-在线支付，3-月结）
     */
    private Integer paymentMethod;

    /**
     * 送货地址
     */
    private String deliveryAddress;

    /**
     * 联系人
     */
    private String contactPerson;

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

    /**
     * 是否删除（0-未删除，1-已删除）
     */
    @TableLogic
    private Integer deleted;
}
