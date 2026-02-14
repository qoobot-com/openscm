package com.qoobot.openscm.purchase.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * 采购入库单DTO
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Data
public class PurchaseInboundDTO {

    /**
     * 入库单ID
     */
    private Long id;

    /**
     * 采购订单ID
     */
    @NotNull(message = "采购订单不能为空")
    private Long orderId;

    /**
     * 仓库ID
     */
    @NotNull(message = "仓库不能为空")
    private Long warehouseId;

    /**
     * 是否需要质检
     */
    @NotNull(message = "是否需要质检不能为空")
    private Integer needQualityCheck;

    /**
     * 入库明细列表
     */
    @NotNull(message = "入库明细不能为空")
    private List<PurchaseInboundItemDTO> items;

    /**
     * 备注
     */
    private String remark;
}
