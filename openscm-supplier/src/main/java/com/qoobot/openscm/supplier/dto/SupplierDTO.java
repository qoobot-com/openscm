package com.qoobot.openscm.supplier.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 供应商DTO
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Data
@Schema(description = "供应商DTO")
public class SupplierDTO {

    @NotBlank(message = "供应商名称不能为空")
    @Schema(description = "供应商名称")
    private String supplierName;

    @NotNull(message = "供应商类型不能为空")
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

    @Schema(description = "备注")
    private String remark;
}
