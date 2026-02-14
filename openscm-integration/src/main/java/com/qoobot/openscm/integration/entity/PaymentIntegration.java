package com.qoobot.openscm.integration.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qoobot.openscm.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支付集成实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("payment_integration")
public class PaymentIntegration extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 支付单号
     */
    private String paymentNo;

    /**
     * 支付平台
     */
    private String paymentPlatform;

    /**
     * 支付方式
     */
    private String paymentMethod;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 业务单号
     */
    private String businessNo;

    /**
     * 支付金额
     */
    private BigDecimal amount;

    /**
     * 货币类型
     */
    private String currency;

    /**
     * 支付状态
     */
    private String paymentStatus;

    /**
     * 第三方支付单号
     */
    private String thirdPartyNo;

    /**
     * 支付时间
     */
    private LocalDateTime paymentTime;

    /**
     * 回调时间
     */
    private LocalDateTime callbackTime;

    /**
     * 回调内容
     */
    private String callbackContent;

    /**
     * 支付IP
     */
    private String paymentIp;

    /**
     * 支付终端
     */
    private String paymentTerminal;

    /**
     * 备注
     */
    private String remark;

    /**
     * 租户ID
     */
    private Long tenantId;
}
