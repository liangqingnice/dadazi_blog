package online.dadazi.dadaziblog.service.impl;


import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.base.Captcha;
import jakarta.annotation.Resource;
import online.dadazi.dadaziblog.config.pojo.BasePojo;
import online.dadazi.dadaziblog.config.pojo.JsonResult;
import online.dadazi.dadaziblog.config.tools.JwtUtil;
import online.dadazi.dadaziblog.config.tools.RedisUtils;
import online.dadazi.dadaziblog.config.tools.EnvUtil;
import online.dadazi.dadaziblog.config.tools.ServletUtil;
import online.dadazi.dadaziblog.pojo.SysUser;
import online.dadazi.dadaziblog.pojo.dto.RegisterDTO;
import online.dadazi.dadaziblog.pojo.dto.RestPasswordDTO;
import online.dadazi.dadaziblog.pojo.dto.SysLoginDTO;
import online.dadazi.dadaziblog.service.SysUserService;
import online.dadazi.dadaziblog.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author lqk
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private EnvUtil envUtil;

    /**
     * 系统用户注册
     *
     * @param registerDTO 注册DTO
     * @return 成功和失败消息
     */
    @Override
    public JsonResult<?> register(RegisterDTO registerDTO) {
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserLambdaQueryWrapper.eq(SysUser::getUsername, registerDTO.getUsername()).orderByDesc(BasePojo::getCreateTime);
        SysUser sysUser = baseMapper.selectOne(sysUserLambdaQueryWrapper);
        if (!Objects.isNull(sysUser)) {
            return JsonResult.error("用户名账号已注册");
        }
        insertSysUser(registerDTO);
        return JsonResult.error("注册成功");
    }

    @Override
    public JsonResult<?> login(SysLoginDTO sysLoginDTO) {
        if (!envUtil.isDev()) {
            String code = sysLoginDTO.getCode();
            String codeKey = sysLoginDTO.getCodeKey();
            if (StrUtil.isBlank(code)) {
                return JsonResult.error("验证码不能为空");
            }
            if (StrUtil.isBlank(codeKey)) {
                return JsonResult.error("验证码密钥不存在");
            }
            if (!redisUtils.hasKey(codeKey)) {
                return JsonResult.error("验证码已过期");
            }
            String cacheObject = redisUtils.getCacheObject(codeKey).toString();
            if (!code.equals(cacheObject)) {
                return JsonResult.error("验证码错误!");
            }
        }

        //验证用户是否存在
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserLambdaQueryWrapper.eq(SysUser::getUsername, sysLoginDTO.getUsername()).orderByDesc(BasePojo::getCreateTime);
        SysUser sysUser = baseMapper.selectOne(sysUserLambdaQueryWrapper);
        if (Objects.isNull(sysUser)) {
            return JsonResult.error("账号未注册,请先注册");
        }
        String password = sysUser.getPassword();
        String loginPassword = sysLoginDTO.getPassword();
        if (!password.equals(DigestUtil.md5Hex(loginPassword))) {
            return JsonResult.error("密码错误");
        }
        Long id = sysUser.getId();
        Map<String, Object> map = new HashMap<>(5);
        map.put("id", id);
        map.put("sysUser", JSON.toJSONString(sysUser));
        String token = JwtUtil.genToken(map);
        redisUtils.setCacheObject(token,token,JwtUtil.expireTime,TimeUnit.MILLISECONDS);
        return JsonResult.success("登录成功",token);
    }

    /**
     * 获取验证码
     *
     * @return 返回验证码Dto
     */
    @Override
    public JsonResult<?> getCaptcha() {
        String key = UUID.randomUUID().toString();
        Map<String, Object> map = new HashMap<>(5);
        map.put("codeKey", key);

        GifCaptcha captcha = new GifCaptcha(130, 38, 6);
        String base64 = captcha.toBase64();
        String code = captcha.text();
        captcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        if (envUtil.isDev()) {
            map.put("code", code);
        }
        map.put("img", base64);
        redisUtils.setCacheObject(key, code, 2L, TimeUnit.MINUTES);
        return JsonResult.success(map);
    }

    @Override
    public JsonResult<?> getUserInfo() {
        Long loginId = ServletUtil.getLoginId();
        SysUser sysUser = baseMapper.selectById(loginId);
        if(Objects.isNull(sysUser)){
            return JsonResult.error("用户不存在");
        }
        return JsonResult.success(sysUser);
    }

    @Override
    public JsonResult<?> changePassword(RestPasswordDTO restPasswordDTO) {
        String oldPassword = restPasswordDTO.getOldPassword();
        String newPassword = restPasswordDTO.getNewPassword();
        String restPassword = restPasswordDTO.getRestPassword();
        if(!newPassword.equals(restPassword)){
            return JsonResult.error("新密码与确认密码不一致");
        }
        Long loginId = ServletUtil.getLoginId();
        if(Objects.isNull(loginId)){
            return JsonResult.error("token已失效请重新登录");
        }
        SysUser sysUser = baseMapper.selectById(loginId);
        if(Objects.isNull(sysUser)){
            return JsonResult.error("token已失效请重新登录");
        }

        String enOldPassword = DigestUtil.md5Hex(oldPassword);
        if (!sysUser.getPassword().equals(enOldPassword)) {
            return JsonResult.error("旧密码验证失败");
        }
        String enNewPassword = DigestUtil.md5Hex(newPassword);
        if (sysUser.getPassword().equals(enNewPassword)) {
            return JsonResult.error("新密码和旧密码一致,无需重置");
        }
        SysUser user = new SysUser();
        user.setId(loginId);
        user.setPassword(enNewPassword);
        int update = baseMapper.updateById(user);
        //重置token
        String token = ServletUtil.getToken();
        redisUtils.deleteObject(token);
        return update>0?JsonResult.success():JsonResult.error();
    }
    /**
     * 添加系统用户
     *
     * @param registerDTO 用户传输
     */
    private void insertSysUser(RegisterDTO registerDTO) {
        SysUser user = new SysUser();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(DigestUtil.md5Hex(registerDTO.getPassword()));
        user.setEmail(registerDTO.getEmail());
        user.setNickname(registerDTO.getNickname());
        user.setUserPic(registerDTO.getUserPic());
        baseMapper.insert(user);
    }
}




