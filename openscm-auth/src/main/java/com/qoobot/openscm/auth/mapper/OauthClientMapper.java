package com.qoobot.openscm.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qoobot.openscm.auth.entity.OauthClient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * OAuth客户端Mapper接口
 */
@Mapper
public interface OauthClientMapper extends BaseMapper<OauthClient> {

    /**
     * 根据客户端ID查询
     */
    OauthClient selectByClientId(@Param("clientId") String clientId);
}
