package com.qoobot.openscm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.openscm.common.exception.BusinessException;
import com.qoobot.openscm.common.util.SecurityUtils;
import com.qoobot.openscm.system.dto.UserDTO;
import com.qoobot.openscm.system.entity.SysUser;
import com.qoobot.openscm.system.mapper.SysUserMapper;
import com.qoobot.openscm.system.service.SysUserService;
import com.qoobot.openscm.system.vo.UserInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * 用户服务实现
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public SysUser getByUsername(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        return this.getOne(wrapper);
    }

    @Override
    public UserInfoVO getCurrentUserInfo() {
        Long userId = SecurityUtils.getCurrentUserId();
        SysUser user = this.getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setId(user.getId());
        userInfoVO.setUsername(user.getUsername());
        userInfoVO.setNickname(user.getNickname());
        userInfoVO.setEmail(user.getEmail());
        userInfoVO.setMobile(user.getMobile());
        userInfoVO.setAvatar(user.getAvatar());
        userInfoVO.setDeptId(user.getDeptId());
        // TODO: 查询角色和权限
        userInfoVO.setRoles(Collections.emptyList());
        userInfoVO.setPermissions(Collections.emptyList());
        return userInfoVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUser(UserDTO userDTO) {
        // 检查用户名是否存在
        SysUser existUser = this.getByUsername(userDTO.getUsername());
        if (existUser != null) {
            throw new BusinessException("用户名已存在");
        }

        SysUser user = new SysUser();
        user.setUsername(userDTO.getUsername());
        // 密码加密
        if (userDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        user.setNickname(userDTO.getNickname());
        user.setEmail(userDTO.getEmail());
        user.setMobile(userDTO.getMobile());
        user.setAvatar(userDTO.getAvatar());
        user.setDeptId(userDTO.getDeptId());
        user.setStatus(userDTO.getStatus() != null ? userDTO.getStatus() : 1);

        this.save(user);

        // TODO: 保存用户角色关联

        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateUser(UserDTO userDTO) {
        if (userDTO.getId() == null) {
            throw new BusinessException("用户ID不能为空");
        }

        SysUser user = this.getById(userDTO.getId());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        user.setNickname(userDTO.getNickname());
        user.setEmail(userDTO.getEmail());
        user.setMobile(userDTO.getMobile());
        user.setAvatar(userDTO.getAvatar());
        user.setDeptId(userDTO.getDeptId());
        if (userDTO.getStatus() != null) {
            user.setStatus(userDTO.getStatus());
        }

        // TODO: 更新用户角色关联

        return this.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteUser(Long userId) {
        // 检查是否为管理员
        if (userId == 1L) {
            throw new BusinessException("不能删除管理员");
        }

        // TODO: 删除用户角色关联

        return this.removeById(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean changePassword(Long userId, String oldPassword, String newPassword) {
        SysUser user = this.getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 验证旧密码
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BusinessException("旧密码不正确");
        }

        // 更新密码
        user.setPassword(passwordEncoder.encode(newPassword));
        return this.updateById(user);
    }
}
