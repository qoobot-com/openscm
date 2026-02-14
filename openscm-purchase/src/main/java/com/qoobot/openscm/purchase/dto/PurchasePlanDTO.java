package com.qoobot.openscm.purchase.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 采购计划DTO
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Data
public class PurchasePlanDTO {

    /**
     * 计划ID
     */
    private Long id;

    /**
     * 计划名称
     */
    @NotBlank(message = "计划名称不能为空")
    private String planName;

    /**
     * 计划年度
     */
    @NotNull(message = "计划年度不能为空")
    private Integer planYear;

    /**
     * 计划月份
     */
    private Integer planMonth;

    /**
     * 计划开始日期
     */
    @NotNull(message = "计划开始日期不能为空")
    private LocalDate startDate;

    /**
     * 计划结束日期
     */
    @NotNull(message = "计划结束日期不能为空")
    private LocalDate endDate;

    /**
     * 计划总金额
     */
    private BigDecimal totalAmount;

    /**
     * 采购计划明细列表
     */
    private List<PurchasePlanItemDTO> items;

    /**
     * 备注
     */
    private String remark;
}
