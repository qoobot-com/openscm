package com.qoobot.openscm.supplier.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.supplier.entity.Supplier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 供应商Mapper
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Mapper
public interface SupplierMapper extends BaseMapper<Supplier> {

    /**
     * 分页查询供应商列表
     *
     * @param page          分页对象
     * @param supplierName  供应商名称（模糊）
     * @param supplierType  供应商类型
     * @param status        状态
     * @return              供应商分页列表
     */
    IPage<Supplier> selectSupplierPage(
            Page<Supplier> page,
            @Param("supplierName") String supplierName,
            @Param("supplierType") Integer supplierType,
            @Param("supplierStatus") Integer supplierStatus
    );
}
