package com.qoobot.openscm.inventory.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 库存预警实体
 */
@Data
@TableName("inventory_alert")
public class InventoryAlert {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 仓库ID
     */
    private Long warehouseId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 规格型号
     */
    private String specification;

    /**
     * 当前库存
     */
    private String currentStock;

    /**
     * 预警类型 1-库存下限 2-库存上限 3-库存呆滞 4-库存过期 5-库存短缺
     */
    private Integer alertType;

    /**
     * 预警级别 1-普通 2-重要 3-紧急
     */
    private Integer alertLevel;

    /**
     * 预警值
     */
    private String alertValue;

    /**
     * 实际值
     */
    private String actualValue;

    /**
     * 差值
     */
    private String diffValue;

    /**
     * 预警内容
     */
    private String alertContent;

    /**
     * 状态 0-未处理 1-已处理
     */
    private Integer status;

    /**
     * 处理人
     */
    private String handler;

    /**
     * 处理时间
     */
    private LocalDateTime handleTime;

    /**
     * 处理备注
     */
    private String handleRemark;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /**
     * 删除标记 0-未删除 1-已删除
     */
    @TableLogic
    private Integer deleted;
}
