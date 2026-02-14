package com.qoobot.openscm.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.logistics.entity.LogisticsException;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物流异常Mapper接口
 */
@Mapper
public interface LogisticsExceptionMapper extends BaseMapper<LogisticsException> {

    /**
     * 分页查询异常记录
     */
    IPage<LogisticsException> selectExceptionPage(Page<LogisticsException> page,
                                                   @Param("exceptionNo") String exceptionNo,
                                                   @Param("handleStatus") Integer handleStatus,
                                                   @Param("exceptionLevel") Integer exceptionLevel,
                                                   @Param("startDate") String startDate,
                                                   @Param("endDate") String endDate);

    /**
     * 根据运单号查询异常记录
     */
    List<LogisticsException> selectByWaybillNo(@Param("waybillNo") String waybillNo);

    /**
     * 根据运输计划ID查询异常记录
     */
    List<LogisticsException> selectByPlanId(@Param("planId") Long planId);

    /**
     * 根据配送单ID查询异常记录
     */
    List<LogisticsException> selectByDeliveryId(@Param("deliveryId") Long deliveryId);

    /**
     * 根据异常编号查询
     */
    LogisticsException selectByExceptionNo(@Param("exceptionNo") String exceptionNo);
}
