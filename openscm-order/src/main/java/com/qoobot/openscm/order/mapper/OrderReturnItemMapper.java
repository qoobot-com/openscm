package com.qoobot.openscm.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单退货明细Mapper接口
 */
@Mapper
public interface OrderReturnItemMapper extends BaseMapper<OrderReturnItem> {

    /**
     * 根据退货单ID查询明细列表
     */
    List<OrderReturnItem> selectByReturnId(@Param("returnId") Long returnId);
}
