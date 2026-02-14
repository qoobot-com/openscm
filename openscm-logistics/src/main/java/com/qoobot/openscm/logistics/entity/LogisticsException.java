package com.qoobot.openscm.logistics.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 物流异常实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("logistics_exception")
public class LogisticsException {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 异常编号
     */
    private String exceptionNo;

    /**
     * 关联运输计划ID
     */
    private Long planId;

    /**
     * 关联配送单ID
     */
    private Long deliveryId;

    /**
     * 运单号
     */
    private String waybillNo;

    /**
     * 异常类型（0-延误，1-丢失，2-损坏，3-拒收，4-地址错误，5-其他）
     */
    private Integer exceptionType;

    /**
     * 异常级别（0-一般，1-严重，2-紧急）
     */
    private Integer exceptionLevel;

    /**
     * 异常描述
     */
    private String exceptionDesc;

    /**
     * 处理状态（0-待处理，1-处理中，2-已解决，3-已关闭）
     */
    private Integer handleStatus;

    /**
     * 处理人
     */
    private String handler;

    /**
     * 处理时间
     */
    private LocalDateTime handleTime;

    /**
     * 处理意见
     */
    private String handleOpinion;

    /**
     * 异常图片
     */
    private String exceptionImage;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
