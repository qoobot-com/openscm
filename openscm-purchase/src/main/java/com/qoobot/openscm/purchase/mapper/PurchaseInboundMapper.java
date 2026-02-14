package com.qoobot.openscm.purchase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qoobot.openscm.purchase.entity.PurchaseInbound;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采购入库单Mapper接口
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Mapper
public interface PurchaseInboundMapper extends BaseMapper<PurchaseInbound> {
}
