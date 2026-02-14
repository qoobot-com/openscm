package com.qoobot.openscm.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qoobot.openscm.auth.entity.LoginSecurityConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 登录安全配置Mapper接口
 */
@Mapper
public interface LoginSecurityConfigMapper extends BaseMapper<LoginSecurityConfig> {

    /**
     * 根据租户ID查询配置
     */
    LoginSecurityConfig selectByTenantId(@Param("tenantId") Long tenantId);

    /**
     * 查询全局配置
     */
    LoginSecurityConfig selectGlobalConfig();
}
