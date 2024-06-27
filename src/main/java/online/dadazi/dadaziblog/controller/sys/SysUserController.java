package online.dadazi.dadaziblog.controller.sys;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import online.dadazi.dadaziblog.config.annotation.VerifyToken;
import online.dadazi.dadaziblog.config.pojo.JsonResult;
import online.dadazi.dadaziblog.pojo.SysUser;
import online.dadazi.dadaziblog.pojo.dto.RegisterDTO;
import online.dadazi.dadaziblog.pojo.dto.RestPasswordDTO;
import online.dadazi.dadaziblog.pojo.dto.SysLoginDTO;
import online.dadazi.dadaziblog.service.SysUserService;
import org.springframework.web.bind.annotation.*;

/**
 * @author lqk
 * 用户控制层
 */
@RestController("SysController")
@RequestMapping("/user")
public class SysUserController {
    @Resource
    private SysUserService sysUserService;

    @PostMapping("/register")
    public JsonResult<?> register(@RequestBody @Valid RegisterDTO registerDTO) {
        return sysUserService.register(registerDTO);
    }

    @PostMapping("/login")
    public JsonResult<?> login(@RequestBody @Valid SysLoginDTO sysLoginDTO) {
        return sysUserService.login(sysLoginDTO);
    }

    @GetMapping("/captcha")
    @VerifyToken(isCheck = false)
    public JsonResult<?> getCaptcha() {
        return sysUserService.getCaptcha();
    }

    @GetMapping("/userInfo")
    public JsonResult<?> getUserInfo() {
        return sysUserService.getUserInfo();
    }

    @PutMapping("/updateUser")
    public JsonResult<?> updateUser(@RequestBody SysUser sysUser) {
        return sysUserService.updateById(sysUser) ? JsonResult.success() : JsonResult.error();
    }
    @PutMapping("/changePassword")
    public JsonResult<?> updateUser(@RequestBody @Valid RestPasswordDTO restPasswordDTO) {
        return sysUserService.changePassword(restPasswordDTO);
    }
}
