package com.qoobot.openscm.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.order.entity.OrderReturn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单退货Mapper接口
 */
@Mapper
public interface OrderReturnMapper extends BaseMapper<OrderReturn> {

    /**
     * 分页查询退货单
     */
    IPage<OrderReturn> selectReturnPage(Page<OrderReturn> page,
                                          @Param("returnNo") String returnNo,
                                          @Param("orderId") Long orderId,
                                          @Param("customerId") Long customerId,
                                          @Param("refundStatus") Integer refundStatus);

    /**
     * 根据订单ID查询退货记录
     */
    List<OrderReturn> selectByOrderId(@Param("orderId") Long orderId);

    /**
     * 根据退货单号查询
     */
    OrderReturn selectByReturnNo(@Param("returnNo") String returnNo);
}
