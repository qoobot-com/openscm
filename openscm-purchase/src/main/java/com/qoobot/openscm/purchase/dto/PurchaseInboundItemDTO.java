package com.qoobot.openscm.purchase.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * 采购入库单明细DTO
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Data
public class PurchaseInboundItemDTO {

    /**
     * 明细ID
     */
    private Long id;

    /**
     * 订单明细ID
     */
    @NotNull(message = "订单明细不能为空")
    private Long orderItemId;

    /**
     * 入库数量
     */
    @NotNull(message = "入库数量不能为空")
    @Positive(message = "入库数量必须大于0")
    private BigDecimal inboundQuantity;

    /**
     * 货位编码
     */
    private String locationCode;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 备注
     */
    private String remark;
}
