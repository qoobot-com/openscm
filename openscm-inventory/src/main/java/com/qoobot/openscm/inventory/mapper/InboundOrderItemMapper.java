package com.qoobot.openscm.inventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qoobot.openscm.inventory.entity.InboundOrderItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 入库单明细Mapper
 */
@Mapper
public interface InboundOrderItemMapper extends BaseMapper<InboundOrderItem> {
}
