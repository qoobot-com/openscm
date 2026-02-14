package com.qoobot.openscm.inventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qoobot.openscm.inventory.entity.WarehouseLocation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 货位信息Mapper
 */
@Mapper
public interface WarehouseLocationMapper extends BaseMapper<WarehouseLocation> {
}
