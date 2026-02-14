package com.qoobot.openscm.system.controller;

import com.qoobot.openscm.common.result.Result;
import com.qoobot.openscm.system.entity.SysRole;
import com.qoobot.openscm.system.service.SysRoleService;
import com.qoobot.openscm.system.vo.RoleMenuVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理控制器
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Tag(name = "角色管理", description = "角色管理接口")
@RestController
@RequestMapping("/api/system/role")
@RequiredArgsConstructor
public class RoleController {

    private final SysRoleService roleService;

    @Operation(summary = "分页查询角色列表")
    @GetMapping("/page")
    public Result<List<SysRole>> pageRoles(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String roleName,
            @RequestParam(required = false) Integer status) {
        List<SysRole> roles = roleService.pageRoles(current, size, roleName, status);
        return Result.success(roles);
    }

    @Operation(summary = "获取角色详情")
    @GetMapping("/{id}")
    public Result<SysRole> getRole(@PathVariable Long id) {
        SysRole role = roleService.getById(id);
        return Result.success(role);
    }

    @Operation(summary = "创建角色")
    @PostMapping
    public Result<Void> createRole(@RequestBody SysRole role) {
        roleService.save(role);
        return Result.success();
    }

    @Operation(summary = "更新角色")
    @PutMapping("/{id}")
    public Result<Void> updateRole(@PathVariable Long id, @RequestBody SysRole role) {
        role.setId(id);
        roleService.updateById(role);
        return Result.success();
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/{id}")
    public Result<Void> deleteRole(@PathVariable Long id) {
        roleService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "分配角色菜单权限")
    @PostMapping("/{roleId}/menus")
    public Result<Void> assignRoleMenus(
            @PathVariable Long roleId,
            @RequestBody List<Long> menuIds) {
        roleService.assignRoleMenus(roleId, menuIds);
        return Result.success();
    }

    @Operation(summary = "获取角色菜单权限")
    @GetMapping("/{roleId}/menus")
    public Result<RoleMenuVO> getRoleMenus(@PathVariable Long roleId) {
        RoleMenuVO roleMenuVO = roleService.getRoleMenus(roleId);
        return Result.success(roleMenuVO);
    }
}
