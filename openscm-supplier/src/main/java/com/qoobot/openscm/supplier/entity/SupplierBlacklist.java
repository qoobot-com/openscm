package com.qoobot.openscm.supplier.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qoobot.openscm.common.model.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 供应商黑名单实体
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("supplier_blacklist")
@Schema(description = "供应商黑名单实体")
public class SupplierBlacklist extends BaseEntity {

    @TableId(type = IdType.AUTO)
    @Schema(description = "黑名单ID")
    private Long id;

    @Schema(description = "供应商ID")
    private Long supplierId;

    @Schema(description = "供应商名称")
    private String supplierName;

    @Schema(description = "列入黑名单原因")
    private String reason;

    @Schema(description = "列入黑名单日期")
    private LocalDate blacklistDate;

    @Schema(description = "预计解除日期")
    private LocalDate expectedReleaseDate;

    @Schema(description = "解除日期")
    private LocalDate releaseDate;

    @Schema(description = "状态（0-已解除 1-生效中）")
    private Integer status;

    @Schema(description = "处理人ID")
    private Long handlerId;

    @Schema(description = "备注")
    private String remark;
}
