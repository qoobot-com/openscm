package com.qoobot.openscm.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.common.annotation.OperationLog;
import com.qoobot.openscm.common.constant.OperLogType;
import com.qoobot.openscm.common.result.PageResult;
import com.qoobot.openscm.common.result.Result;
import com.qoobot.openscm.system.dto.UserDTO;
import com.qoobot.openscm.system.entity.SysUser;
import com.qoobot.openscm.system.service.SysUserService;
import com.qoobot.openscm.system.vo.UserInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制器
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Tag(name = "用户管理", description = "用户管理相关接口")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final SysUserService sysUserService;

    @Operation(summary = "分页查询用户列表")
    @GetMapping("/page")
    public Result<PageResult<SysUser>> getUserPage(
            @Parameter(description = "当前页") @RequestParam(defaultValue = "1") Long pageNum,
            @Parameter(description = "每页条数") @RequestParam(defaultValue = "10") Long pageSize,
            @Parameter(description = "用户名") @RequestParam(required = false) String username,
            @Parameter(description = "手机号") @RequestParam(required = false) String mobile) {
        Page<SysUser> page = new Page<>(pageNum, pageSize);
        // TODO: 添加查询条件
        sysUserService.page(page);
        return Result.success(PageResult.of(pageNum, pageSize, page.getTotal(), page.getRecords()));
    }

    @Operation(summary = "根据ID查询用户")
    @GetMapping("/{id}")
    public Result<SysUser> getUserById(@PathVariable Long id) {
        SysUser user = sysUserService.getById(id);
        return Result.success(user);
    }

    @Operation(summary = "创建用户")
    @PostMapping
    @OperationLog(module = "用户管理", businessType = OperLogType.INSERT, description = "创建用户")
    public Result<Long> createUser(@Valid @RequestBody UserDTO userDTO) {
        Long userId = sysUserService.createUser(userDTO);
        return Result.success("创建成功", userId);
    }

    @Operation(summary = "更新用户")
    @PutMapping
    @OperationLog(module = "用户管理", businessType = OperLogType.UPDATE, description = "更新用户")
    public Result<Void> updateUser(@Valid @RequestBody UserDTO userDTO) {
        sysUserService.updateUser(userDTO);
        return Result.success("更新成功");
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    @OperationLog(module = "用户管理", businessType = OperLogType.DELETE, description = "删除用户")
    public Result<Void> deleteUser(@PathVariable Long id) {
        sysUserService.deleteUser(id);
        return Result.success("删除成功");
    }

    @Operation(summary = "修改密码")
    @PutMapping("/password")
    @OperationLog(module = "用户管理", businessType = OperLogType.UPDATE, description = "修改密码")
    public Result<Void> changePassword(
            @RequestParam Long userId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        sysUserService.changePassword(userId, oldPassword, newPassword);
        return Result.success("修改密码成功");
    }
}
