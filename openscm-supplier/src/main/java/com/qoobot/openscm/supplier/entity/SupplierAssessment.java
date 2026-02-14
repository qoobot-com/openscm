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
 * 供应商绩效评估实体
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("supplier_assessment")
@Schema(description = "供应商绩效评估实体")
public class SupplierAssessment extends BaseEntity {

    @TableId(type = IdType.AUTO)
    @Schema(description = "评估ID")
    private Long id;

    @Schema(description = "供应商ID")
    private Long supplierId;

    @Schema(description = "评估周期（年月）")
    private String assessmentPeriod;

    @Schema(description = "质量得分")
    private BigDecimal qualityScore;

    @Schema(description = "交付得分")
    private BigDecimal deliveryScore;

    @Schema(description = "服务得分")
    private BigDecimal serviceScore;

    @Schema(description = "价格得分")
    private BigDecimal priceScore;

    @Schema(description = "综合得分")
    private BigDecimal totalScore;

    @Schema(description = "评估等级（A/B/C/D）")
    private String assessmentGrade;

    @Schema(description = "评估说明")
    private String assessmentRemark;

    @Schema(description = "评估人ID")
    private Long assessorId;
}
