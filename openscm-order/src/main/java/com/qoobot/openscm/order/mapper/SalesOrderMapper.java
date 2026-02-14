package com.qoobot.openscm.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.order.entity.SalesOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 销售订单Mapper接口
 */
@Mapper
public interface SalesOrderMapper extends BaseMapper<SalesOrder> {

    /**
     * 分页查询销售订单
     */
    IPage<SalesOrder> selectOrderPage(Page<SalesOrder> page,
                                        @Param("orderNo") String orderNo,
                                        @Param("customerId") Long customerId,
                                        @Param("status") Integer status,
                                        @Param("startDate") String startDate,
                                        @Param("endDate") String endDate);

    /**
     * 根据订单编号查询
     */
    SalesOrder selectByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 根据客户ID查询订单列表
     */
    List<SalesOrder> selectByCustomerId(@Param("customerId") Long customerId);
}
