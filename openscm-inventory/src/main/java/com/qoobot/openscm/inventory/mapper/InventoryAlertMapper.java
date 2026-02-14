package com.qoobot.openscm.inventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qoobot.openscm.inventory.entity.InventoryAlert;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存预警Mapper
 */
@Mapper
public interface InventoryAlertMapper extends BaseMapper<InventoryAlert> {
}
