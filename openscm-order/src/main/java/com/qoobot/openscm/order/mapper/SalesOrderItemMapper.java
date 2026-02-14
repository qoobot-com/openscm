package com.qoobot.openscm.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 销售订单明细Mapper接口
 */
@Mapper
public interface SalesOrderItemMapper extends BaseMapper<SalesOrderItem> {

    /**
     * 根据订单ID查询明细列表
     */
    List<SalesOrderItem> selectByOrderId(@Param("orderId") Long orderId);
}
