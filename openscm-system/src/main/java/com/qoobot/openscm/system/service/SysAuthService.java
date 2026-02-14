package com.qoobot.openscm.system.service;

import com.qoobot.openscm.system.dto.LoginDTO;
import com.qoobot.openscm.system.vo.LoginVO;
import com.qoobot.openscm.system.vo.UserInfoVO;

/**
 * 认证服务接口
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
public interface SysAuthService {

    /**
     * 用户登录
     *
     * @param loginDTO 登录 DTO
     * @return 登录响应 VO
     */
    LoginVO login(LoginDTO loginDTO);

    /**
     * 用户退出
     */
    void logout();

    /**
     * 获取当前用户信息
     *
     * @return 用户信息 VO
     */
    UserInfoVO getUserInfo();

    /**
     * 刷新令牌
     *
     * @param refreshToken 刷新令牌
     * @return 新的登录响应 VO
     */
    LoginVO refreshToken(String refreshToken);
}
