package com.qoobot.openscm.integration.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qoobot.openscm.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 电子发票实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("electronic_invoice")
public class ElectronicInvoice extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 发票代码
     */
    private String invoiceCode;

    /**
     * 发票号码
     */
    private String invoiceNo;

    /**
     * 发票类型
     */
    private String invoiceType;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 税号
     */
    private String taxNumber;

    /**
     * 开票日期
     */
    private LocalDateTime invoiceDate;

    /**
     * 金额(不含税)
     */
    private BigDecimal amount;

    /**
     * 税额
     */
    private BigDecimal taxAmount;

    /**
     * 价税合计
     */
    private BigDecimal totalAmount;

    /**
     * 发票状态
     */
    private String invoiceStatus;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 业务单号
     */
    private String businessNo;

    /**
     * PDF文件路径
     */
    private String pdfFilePath;

    /**
     * PDF文件URL
     */
    private String pdfFileUrl;

    /**
     * 开票平台
     */
    private String platform;

    /**
     * 开票状态
     */
    private String issueStatus;

    /**
     * 开票时间
     */
    private LocalDateTime issueTime;

    /**
     * 电子发票URL
     */
    private String eInvoiceUrl;

    /**
     * 备注
     */
    private String remark;

    /**
     * 租户ID
     */
    private Long tenantId;
}
