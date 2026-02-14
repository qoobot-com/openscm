package com.qoobot.openscm.supplier.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qoobot.openscm.common.model.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 供应商实体
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("supplier")
@Schema(description = "供应商实体")
public class Supplier extends BaseEntity {

    @TableId(type = IdType.AUTO)
    @Schema(description = "供应商ID")
    private Long id;

    @Schema(description = "供应商编码")
    private String supplierCode;

    @Schema(description = "供应商名称")
    private String supplierName;

    @Schema(description = "供应商类型（1-原料 2-辅料 3-设备 4-服务）")
    private Integer supplierType;

    @Schema(description = "联系人")
    private String contactPerson;

    @Schema(description = "联系电话")
    private String contactPhone;

    @Schema(description = "联系邮箱")
    private String contactEmail;

    @Schema(description = "供应商地址")
    private String address;

    @Schema(description = "供应商等级（1-A级 2-B级 3-C级 4-D级）")
    private Integer supplierGrade;

    @Schema(description = "信用额度")
    private BigDecimal creditLimit;

    @Schema(description = "合作开始日期")
    private LocalDate cooperationStartDate;

    @Schema(description = "营业执照号")
    private String licenseNumber;

    @Schema(description = "税务登记号")
    private String taxNumber;

    @Schema(description = "银行账号")
    private String bankAccount;

    @Schema(description = "开户银行")
    private String bankName;

    @Schema(description = "供应商状态（0-停用 1-正常 2-审核中 3-黑名单）")
    private Integer supplierStatus;

    @Schema(description = "审核状态（0-待审核 1-已通过 2-已拒绝）")
    private Integer auditStatus;

    @Schema(description = "审核人ID")
    private Long auditorId;

    @Schema(description = "审核时间")
    private java.time.LocalDateTime auditTime;

    @Schema(description = "审核意见")
    private String auditRemark;

    @Schema(description = "备注")
    private String remark;
}
