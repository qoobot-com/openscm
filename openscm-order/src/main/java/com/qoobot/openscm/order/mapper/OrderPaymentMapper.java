package com.qoobot.openscm.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单支付记录Mapper接口
 */
@Mapper
public interface OrderPaymentMapper extends BaseMapper<OrderPayment> {

    /**
     * 根据订单ID查询支付记录
     */
    List<OrderPayment> selectByOrderId(@Param("orderId") Long orderId);

    /**
     * 根据支付编号查询
     */
    OrderPayment selectByPaymentNo(@Param("paymentNo") String paymentNo);
}
