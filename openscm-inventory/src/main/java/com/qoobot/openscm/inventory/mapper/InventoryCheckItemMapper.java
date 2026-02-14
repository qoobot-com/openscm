package com.qoobot.openscm.inventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qoobot.openscm.inventory.entity.InventoryCheckItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存盘点单明细Mapper
 */
@Mapper
public interface InventoryCheckItemMapper extends BaseMapper<InventoryCheckItem> {
}
