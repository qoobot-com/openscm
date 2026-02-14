package com.qoobot.openscm.inventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qoobot.openscm.inventory.entity.OutboundOrderItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 出库单明细Mapper
 */
@Mapper
public interface OutboundOrderItemMapper extends BaseMapper<OutboundOrderItem> {
}
