package com.qoobot.openscm.logistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qoobot.openscm.logistics.entity.TransportPlan;

import java.util.List;
import java.util.Map;

/**
 * 运输计划服务接口
 */
public interface TransportPlanService extends IService<TransportPlan> {

    /**
     * 创建运输计划
     */
    Long createPlan(TransportPlan plan);

    /**
     * 分页查询运输计划
     */
    IPage<TransportPlan> planPage(Page<TransportPlan> page, String planNo, Long orderId,
                                    Integer status, String startDate, String endDate);

    /**
     * 开始运输
     */
    void startTransport(Long planId);

    /**
     * 完成运输
     */
    void completeTransport(Long planId);

    /**
     * 取消运输计划
     */
    void cancelPlan(Long planId);

    /**
     * 物流统计分析
     */
    Map<String, Object> statistics();

    /**
     * 根据运单号查询轨迹
     */
    List<com.qoobot.openscm.logistics.entity.LogisticsTracking> getTrackingByWaybillNo(String waybillNo);
}
