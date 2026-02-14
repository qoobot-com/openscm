package com.qoobot.openscm.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物流轨迹Mapper接口
 */
@Mapper
public interface LogisticsTrackingMapper extends BaseMapper<LogisticsTracking> {

    /**
     * 根据运单号查询轨迹列表
     */
    List<com.qoobot.openscm.logistics.entity.LogisticsTracking> selectByWaybillNo(@Param("waybillNo") String waybillNo);

    /**
     * 根据运输计划ID查询轨迹列表
     */
    List<com.qoobot.openscm.logistics.entity.LogisticsTracking> selectByPlanId(@Param("planId") Long planId);

    /**
     * 根据配送单ID查询轨迹列表
     */
    List<com.qoobot.openscm.logistics.entity.LogisticsTracking> selectByDeliveryId(@Param("deliveryId") Long deliveryId);
}
