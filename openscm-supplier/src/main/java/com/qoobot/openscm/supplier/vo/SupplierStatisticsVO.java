package com.qoobot.openscm.supplier.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 供应商统计VO
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "供应商统计VO")
public class SupplierStatisticsVO {

    @Schema(description = "供应商总数")
    private Integer totalCount;

    @Schema(description = "正常供应商数")
    private Integer normalCount;

    @Schema(description = "审核中供应商数")
    private Integer auditingCount;

    @Schema(description = "黑名单供应商数")
    private Integer blacklistCount;

    @Schema(description = "A级供应商数")
    private Integer gradeACount;

    @Schema(description = "B级供应商数")
    private Integer gradeBCount;

    @Schema(description = "C级供应商数")
    private Integer gradeCCount;

    @Schema(description = "D级供应商数")
    private Integer gradeDCount;

    @Schema(description = "本月新增供应商数")
    private Integer monthNewCount;

    @Schema(description = "总采购金额")
    private BigDecimal totalPurchaseAmount;
}
