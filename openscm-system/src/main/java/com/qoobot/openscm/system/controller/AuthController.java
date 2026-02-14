package com.qoobot.openscm.system.controller;

import com.qoobot.openscm.common.annotation.OperationLog;
import com.qoobot.openscm.common.result.Result;
import com.qoobot.openscm.system.dto.LoginDTO;
import com.qoobot.openscm.system.service.SysAuthService;
import com.qoobot.openscm.system.vo.LoginVO;
import com.qoobot.openscm.system.vo.UserInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Tag(name = "认证管理", description = "认证相关接口")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SysAuthService sysAuthService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    @OperationLog(module = "认证管理", businessType = com.qoobot.openscm.common.constant.OperLogType.OTHER, description = "用户登录")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        LoginVO loginVO = sysAuthService.login(loginDTO);
        return Result.success("登录成功", loginVO);
    }

    @Operation(summary = "用户退出")
    @PostMapping("/logout")
    @OperationLog(module = "认证管理", businessType = com.qoobot.openscm.common.constant.OperLogType.OTHER, description = "用户退出")
    public Result<Void> logout() {
        sysAuthService.logout();
        return Result.success("退出成功");
    }

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/userinfo")
    public Result<UserInfoVO> getUserInfo() {
        UserInfoVO userInfo = sysAuthService.getUserInfo();
        return Result.success(userInfo);
    }

    @Operation(summary = "刷新令牌")
    @PostMapping("/refresh")
    public Result<LoginVO> refreshToken(@RequestParam String refreshToken) {
        LoginVO loginVO = sysAuthService.refreshToken(refreshToken);
        return Result.success(loginVO);
    }
}
