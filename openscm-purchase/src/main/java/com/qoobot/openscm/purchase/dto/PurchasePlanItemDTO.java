package com.qoobot.openscm.purchase.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * 采购计划明细DTO
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Data
public class PurchasePlanItemDTO {

    /**
     * 明细ID
     */
    private Long id;

    /**
     * 物料编码
     */
    @NotBlank(message = "物料编码不能为空")
    private String materialCode;

    /**
     * 物料名称
     */
    @NotBlank(message = "物料名称不能为空")
    private String materialName;

    /**
     * 规格型号
     */
    private String specification;

    /**
     * 单位
     */
    @NotBlank(message = "单位不能为空")
    private String unit;

    /**
     * 计划数量
     */
    @NotNull(message = "计划数量不能为空")
    @Positive(message = "计划数量必须大于0")
    private BigDecimal quantity;

    /**
     * 预估单价
     */
    @NotNull(message = "预估单价不能为空")
    @Positive(message = "预估单价必须大于0")
    private BigDecimal estimatedPrice;

    /**
     * 预估金额
     */
    private BigDecimal estimatedAmount;
}
