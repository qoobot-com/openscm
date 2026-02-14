package com.qoobot.openscm.purchase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qoobot.openscm.purchase.entity.PurchaseOrderItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采购订单明细Mapper接口
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Mapper
public interface PurchaseOrderItemMapper extends BaseMapper<PurchaseOrderItem> {
}
