package com.qoobot.openscm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qoobot.openscm.system.entity.SysRole;
import com.qoobot.openscm.system.vo.RoleMenuVO;

import java.util.List;

/**
 * 角色服务接口
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 分页查询角色列表
     *
     * @param current   当前页
     * @param size      每页大小
     * @param roleName  角色名称（模糊查询）
     * @param status    状态
     * @return          角色列表
     */
    List<SysRole> pageRoles(Long current, Long size, String roleName, Integer status);

    /**
     * 获取用户角色列表
     *
     * @param userId    用户ID
     * @return          角色列表
     */
    List<SysRole> getUserRoles(Long userId);

    /**
     * 分配角色菜单权限
     *
     * @param roleId    角色ID
     * @param menuIds   菜单ID列表
     */
    void assignRoleMenus(Long roleId, List<Long> menuIds);

    /**
     * 获取角色菜单权限
     *
     * @param roleId    角色ID
     * @return          菜单权限VO
     */
    RoleMenuVO getRoleMenus(Long roleId);
}
