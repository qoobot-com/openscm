package com.qoobot.openscm.supplier.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qoobot.openscm.supplier.entity.SupplierBlacklist;
import org.apache.ibatis.annotations.Mapper;

/**
 * 供应商黑名单Mapper
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Mapper
public interface SupplierBlacklistMapper extends BaseMapper<SupplierBlacklist> {
}
