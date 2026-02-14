package com.qoobot.openscm.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.logistics.entity.TransportPlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 运输计划Mapper接口
 */
@Mapper
public interface TransportPlanMapper extends BaseMapper<TransportPlan> {

    /**
     * 分页查询运输计划
     */
    IPage<TransportPlan> selectPlanPage(Page<TransportPlan> page,
                                         @Param("planNo") String planNo,
                                         @Param("orderId") Long orderId,
                                         @Param("status") Integer status,
                                         @Param("startDate") String startDate,
                                         @Param("endDate") String endDate);

    /**
     * 根据计划编号查询
     */
    TransportPlan selectByPlanNo(@Param("planNo") String planNo);

    /**
     * 根据订单ID查询运输计划
     */
    List<TransportPlan> selectByOrderId(@Param("orderId") Long orderId);
}
