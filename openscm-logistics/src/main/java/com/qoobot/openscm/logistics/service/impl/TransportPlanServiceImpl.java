package com.qoobot.openscm.logistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.openscm.logistics.entity.LogisticsTracking;
import com.qoobot.openscm.logistics.entity.TransportPlan;
import com.qoobot.openscm.logistics.mapper.TransportPlanMapper;
import com.qoobot.openscm.logistics.service.TransportPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运输计划服务实现
 */
@Service
public class TransportPlanServiceImpl extends ServiceImpl<TransportPlanMapper, TransportPlan> implements TransportPlanService {

    @Autowired
    private TransportPlanMapper transportPlanMapper;

    @Autowired
    private com.qoobot.openscm.logistics.mapper.LogisticsTrackingMapper logisticsTrackingMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPlan(TransportPlan plan) {
        // 生成运输计划编号
        String planNo = generatePlanNo();
        plan.setPlanNo(planNo);
        plan.setStatus(0); // 待出发

        transportPlanMapper.insert(plan);

        // 创建初始轨迹
        LogisticsTracking tracking = new LogisticsTracking();
        tracking.setTrackingNo("TRK" + System.currentTimeMillis());
        tracking.setPlanId(plan.getId());
        tracking.setWaybillNo(planNo);
        tracking.setNode(0);
        tracking.setNodeName("已下单");
        tracking.setNodeDesc("运输计划已创建");
        tracking.setNodeTime(java.time.LocalDateTime.now());
        logisticsTrackingMapper.insert(tracking);

        return plan.getId();
    }

    @Override
    public IPage<TransportPlan> planPage(Page<TransportPlan> page, String planNo, Long orderId,
                                            Integer status, String startDate, String endDate) {
        return transportPlanMapper.selectPlanPage(page, planNo, orderId, status, startDate, endDate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startTransport(Long planId) {
        TransportPlan plan = transportPlanMapper.selectById(planId);
        if (plan == null) {
            throw new RuntimeException("运输计划不存在");
        }
        if (plan.getStatus() != 0) {
            throw new RuntimeException("运输状态不正确");
        }
        plan.setStatus(1); // 运输中
        plan.setActualDepartureTime(java.time.LocalDateTime.now());
        transportPlanMapper.updateById(plan);

        // 记录轨迹
        LogisticsTracking tracking = new LogisticsTracking();
        tracking.setTrackingNo("TRK" + System.currentTimeMillis());
        tracking.setPlanId(planId);
        tracking.setWaybillNo(plan.getPlanNo());
        tracking.setNode(2);
        tracking.setNodeName("运输中");
        tracking.setNodeDesc("货物已出发，开始运输");
        tracking.setNodeTime(java.time.LocalDateTime.now());
        logisticsTrackingMapper.insert(tracking);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeTransport(Long planId) {
        TransportPlan plan = transportPlanMapper.selectById(planId);
        if (plan == null) {
            throw new RuntimeException("运输计划不存在");
        }
        if (plan.getStatus() != 1) {
            throw new RuntimeException("运输状态不正确");
        }
        plan.setStatus(2); // 已送达
        plan.setActualArrivalTime(java.time.LocalDateTime.now());
        transportPlanMapper.updateById(plan);

        // 记录轨迹
        LogisticsTracking tracking = new LogisticsTracking();
        tracking.setTrackingNo("TRK" + System.currentTimeMillis());
        tracking.setPlanId(planId);
        tracking.setWaybillNo(plan.getPlanNo());
        tracking.setNode(4);
        tracking.setNodeName("已送达");
        tracking.setNodeDesc("货物已送达目的地");
        tracking.setNodeTime(java.time.LocalDateTime.now());
        logisticsTrackingMapper.insert(tracking);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelPlan(Long planId) {
        TransportPlan plan = transportPlanMapper.selectById(planId);
        if (plan == null) {
            throw new RuntimeException("运输计划不存在");
        }
        if (plan.getStatus() >= 2) {
            throw new RuntimeException("运输已完成或已取消，无法取消");
        }
        plan.setStatus(3); // 已取消
        transportPlanMapper.updateById(plan);
    }

    @Override
    public Map<String, Object> statistics() {
        Map<String, Object> result = new HashMap<>();

        // 总运输计划数
        Long totalPlans = transportPlanMapper.selectCount(null);
        result.put("totalPlans", totalPlans);

        // 各状态运输计划数
        Map<Integer, Long> statusCount = new HashMap<>();
        for (int i = 0; i <= 3; i++) {
            Long count = transportPlanMapper.selectCount(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<TransportPlan>()
                    .eq(TransportPlan::getStatus, i)
            );
            statusCount.put(i, count);
        }
        result.put("statusCount", statusCount);

        // 总运输费用
        List<TransportPlan> plans = transportPlanMapper.selectList(null);
        BigDecimal totalFee = plans.stream()
            .map(TransportPlan::getTransportFee)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        result.put("totalFee", totalFee);

        return result;
    }

    @Override
    public List<LogisticsTracking> getTrackingByWaybillNo(String waybillNo) {
        return logisticsTrackingMapper.selectByWaybillNo(waybillNo);
    }

    private String generatePlanNo() {
        return "TP" + System.currentTimeMillis();
    }
}
