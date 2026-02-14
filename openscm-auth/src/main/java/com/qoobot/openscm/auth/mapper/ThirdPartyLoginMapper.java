package com.qoobot.openscm.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qoobot.openscm.auth.entity.ThirdPartyLogin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 第三方登录Mapper接口
 */
@Mapper
public interface ThirdPartyLoginMapper extends BaseMapper<ThirdPartyLogin> {

    /**
     * 根据第三方用户ID查询
     */
    ThirdPartyLogin selectByThirdPartyUserId(@Param("loginType") Integer loginType,
                                              @Param("thirdPartyUserId") String thirdPartyUserId);

    /**
     * 根据用户ID查询第三方登录信息
     */
    List<ThirdPartyLogin> selectByUserId(@Param("userId") Long userId);
}
