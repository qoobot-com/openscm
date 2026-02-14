package com.qoobot.openscm.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qoobot.openscm.common.model.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据字典数据实体
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_data")
@Schema(description = "数据字典数据实体")
public class SysDictData extends BaseEntity {

    @TableId(type = IdType.AUTO)
    @Schema(description = "字典编码")
    private Long id;

    @Schema(description = "字典类型")
    private String dictType;

    @Schema(description = "字典标签")
    private String dictLabel;

    @Schema(description = "字典键值")
    private String dictValue;

    @Schema(description = "字典排序")
    private Integer dictSort;

    @Schema(description = "样式属性")
    private String cssClass;

    @Schema(description = "表格回显样式")
    private String listClass;

    @Schema(description = "是否默认")
    private Integer isDefault;

    @Schema(description = "状态（0停用 1正常）")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
}
