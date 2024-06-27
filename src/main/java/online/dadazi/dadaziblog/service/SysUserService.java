package online.dadazi.dadaziblog.service;

import online.dadazi.dadaziblog.config.pojo.JsonResult;
import online.dadazi.dadaziblog.pojo.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import online.dadazi.dadaziblog.pojo.dto.RegisterDTO;
import online.dadazi.dadaziblog.pojo.dto.RestPasswordDTO;
import online.dadazi.dadaziblog.pojo.dto.SysLoginDTO;

/**
 * 系统用户服务
 *
 * @author lqk
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 系统用户注册
     *
     * @param registerDTO 注册DTO
     * @return 成功和失败消息
     */
    JsonResult<?> register(RegisterDTO registerDTO);

    /**
     * 系统用户登录
     * @param sysLoginDTO 用户登录传输
     * @return {@link JsonResult }<{@link ? }>
     */
    JsonResult<?> login(SysLoginDTO sysLoginDTO);

    /**
     * 获取验证码
     * @return 返回验证码Dto
     */
    JsonResult<?> getCaptcha();

    /**
     * 获取个人信息
     * @return {@link JsonResult }<{@link ? }>
     */
    JsonResult<?> getUserInfo();

    /**
     * 重置密码
     * @param restPasswordDTO 重置密码实体
     * @return {@link JsonResult }<{@link ? }>
     */
    JsonResult<?> changePassword(RestPasswordDTO restPasswordDTO);
}
