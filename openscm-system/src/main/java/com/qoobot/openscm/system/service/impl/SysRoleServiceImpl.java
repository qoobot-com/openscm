package com.qoobot.openscm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.openscm.system.entity.SysRole;
import com.qoobot.openscm.system.mapper.SysRoleMapper;
import com.qoobot.openscm.system.service.SysRoleService;
import com.qoobot.openscm.system.vo.RoleMenuVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色服务实现
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public List<SysRole> pageRoles(Long current, Long size, String roleName, Integer status) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(roleName)) {
            wrapper.like(SysRole::getRoleName, roleName);
        }
        if (status != null) {
            wrapper.eq(SysRole::getStatus, status);
        }
        
        wrapper.orderByDesc(SysRole::getCreateTime);
        
        // MyBatis-Plus 分页查询
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<SysRole> getUserRoles(Long userId) {
        return baseMapper.selectByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignRoleMenus(Long roleId, List<Long> menuIds) {
        // 先删除原有权限
        baseMapper.deleteRoleMenus(roleId);
        
        // 添加新权限
        if (menuIds != null && !menuIds.isEmpty()) {
            for (Long menuId : menuIds) {
                baseMapper.insertRoleMenu(roleId, menuId);
            }
        }
    }

    @Override
    public RoleMenuVO getRoleMenus(Long roleId) {
        List<Long> menuIds = baseMapper.selectMenuIdsByRoleId(roleId);
        
        RoleMenuVO vo = new RoleMenuVO();
        vo.setRoleId(roleId);
        vo.setMenuIds(menuIds);
        
        return vo;
    }
}
