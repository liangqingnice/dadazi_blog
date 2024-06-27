package online.dadazi.dadaziblog.config.annotation.impl;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import online.dadazi.dadaziblog.config.tools.RedisUtils;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 防重复实现
 * @author lqk
 */
@Component
public class RepeatSubmitImpl {
    @Resource
    private RedisUtils redisUtils;

    /**
     * 判断是否重复
     *
     * @param request
     * @return
     */
    public boolean isRepeatSubmit(HttpServletRequest request, Long time) {
        String token = request.getHeader("token");
        String requestURI = request.getRequestURI();
        String sign = token + requestURI;
        if (redisUtils.hasKey(sign)) {
            return true;
        }
        redisUtils.setCacheObject(sign, sign, time, TimeUnit.SECONDS);
        return false;
    }
}
