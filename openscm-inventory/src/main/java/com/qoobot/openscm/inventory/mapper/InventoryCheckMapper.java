package com.qoobot.openscm.inventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qoobot.openscm.inventory.entity.InventoryCheck;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存盘点单Mapper
 */
@Mapper
public interface InventoryCheckMapper extends BaseMapper<InventoryCheck> {
}
