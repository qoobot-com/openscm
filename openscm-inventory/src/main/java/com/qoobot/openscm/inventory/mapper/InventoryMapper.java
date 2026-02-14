package com.qoobot.openscm.inventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qoobot.openscm.inventory.entity.Inventory;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存信息Mapper
 */
@Mapper
public interface InventoryMapper extends BaseMapper<Inventory> {
}
