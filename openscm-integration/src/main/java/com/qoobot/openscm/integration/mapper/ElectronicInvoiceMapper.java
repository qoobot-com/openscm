package com.qoobot.openscm.integration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qoobot.openscm.integration.entity.ElectronicInvoice;
import org.apache.ibatis.annotations.Mapper;

/**
 * 电子发票Mapper
 */
@Mapper
public interface ElectronicInvoiceMapper extends BaseMapper<ElectronicInvoice> {
}
