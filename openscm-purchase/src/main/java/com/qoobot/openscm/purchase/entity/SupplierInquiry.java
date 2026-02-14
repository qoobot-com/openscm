package com.qoobot.openscm.purchase.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qoobot.openscm.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 供应商询价实体类
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("supplier_inquiry")
public class SupplierInquiry extends BaseEntity {

    /**
     * 询价单编号
     */
    private String inquiryNo;

    /**
     * 询价主题
     */
    private String subject;

    /**
     * 询价状态: 0-待询价 1-询价中 2-已完成
     */
    private Integer status;

    /**
     * 询价开始日期
     */
    private LocalDate startDate;

    /**
     * 询价截止日期
     */
    private LocalDate endDate;

    /**
     * 询价人ID
     */
    private Long inquirerId;

    /**
     * 询价人姓名
     */
    private String inquirerName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 询价说明
     */
    private String description;

    /**
     * 备注
     */
    private String remark;
}
