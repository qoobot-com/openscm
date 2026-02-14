package com.qoobot.openscm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qoobot.openscm.system.dto.UserDTO;
import com.qoobot.openscm.system.entity.SysUser;
import com.qoobot.openscm.system.vo.UserInfoVO;

/**
 * 用户服务接口
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    SysUser getByUsername(String username);

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    UserInfoVO getCurrentUserInfo();

    /**
     * 创建用户
     *
     * @param userDTO 用户 DTO
     * @return 用户ID
     */
    Long createUser(UserDTO userDTO);

    /**
     * 更新用户
     *
     * @param userDTO 用户 DTO
     * @return 是否成功
     */
    Boolean updateUser(UserDTO userDTO);

    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return 是否成功
     */
    Boolean deleteUser(Long userId);

    /**
     * 修改密码
     *
     * @param userId 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 是否成功
     */
    Boolean changePassword(Long userId, String oldPassword, String newPassword);
}
