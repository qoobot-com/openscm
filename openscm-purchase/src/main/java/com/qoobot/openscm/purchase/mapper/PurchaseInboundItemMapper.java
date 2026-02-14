package com.qoobot.openscm.purchase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qoobot.openscm.purchase.entity.PurchaseInboundItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采购入库单明细Mapper接口
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Mapper
public interface PurchaseInboundItemMapper extends BaseMapper<PurchaseInboundItem> {
}
