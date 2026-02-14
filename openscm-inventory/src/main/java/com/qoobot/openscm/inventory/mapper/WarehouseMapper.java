package com.qoobot.openscm.inventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qoobot.openscm.inventory.entity.Warehouse;
import org.apache.ibatis.annotations.Mapper;

/**
 * 仓库信息Mapper
 */
@Mapper
public interface WarehouseMapper extends BaseMapper<Warehouse> {
}
