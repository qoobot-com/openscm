package com.qoobot.openscm.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qoobot.openscm.auth.entity.ThirdPartyLogin;

import java.util.List;

/**
 * 第三方登录服务接口
 */
public interface ThirdPartyLoginService extends IService<ThirdPartyLogin> {

    /**
     * 绑定第三方账号
     */
    Long bindThirdParty(ThirdPartyLogin thirdPartyLogin);

    /**
     * 解绑第三方账号
     */
    void unbindThirdParty(Long userId, Integer loginType);

    /**
     * 根据第三方信息获取用户
     */
    ThirdPartyLogin getUserByThirdParty(Integer loginType, String thirdPartyUserId);

    /**
     * 获取用户的第三方登录列表
     */
    List<ThirdPartyLogin> getUserThirdParties(Long userId);

    /**
     * 更新登录时间
     */
    void updateLastLoginTime(Long id);
}
