package com.qoobot.openscm.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.auth.entity.Tenant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 租户Mapper接口
 */
@Mapper
public interface TenantMapper extends BaseMapper<Tenant> {

    /**
     * 分页查询租户
     */
    IPage<Tenant> selectTenantPage(Page<Tenant> page,
                                    @Param("tenantCode") String tenantCode,
                                    @Param("tenantName") String tenantName,
                                    @Param("status") Integer status);

    /**
     * 根据租户编码查询
     */
    Tenant selectByTenantCode(@Param("tenantCode") String tenantCode);

    /**
     * 检查租户用户数是否超限
     */
    Integer countUsersByTenantId(@Param("tenantId") Long tenantId);
}
