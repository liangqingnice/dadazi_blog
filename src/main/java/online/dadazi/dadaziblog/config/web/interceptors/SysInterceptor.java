package online.dadazi.dadaziblog.config.web.interceptors;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import online.dadazi.dadaziblog.config.annotation.impl.VerifyTokenImpl;
import online.dadazi.dadaziblog.config.pojo.JsonResult;
import online.dadazi.dadaziblog.config.tools.JwtUtil;
import online.dadazi.dadaziblog.config.tools.RedisUtils;
import online.dadazi.dadaziblog.config.tools.ServletUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import java.util.Map;

import static cn.hutool.core.util.CharsetUtil.UTF_8;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON;

/**
 * 后台拦截
 * @author lqk
 */
@Component
public class SysInterceptor implements AsyncHandlerInterceptor {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private VerifyTokenImpl verifyToken;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        try {
            if (verifyToken.isVerifyToken((HandlerMethod) handler)) {
                Boolean expression = redisUtils.hasKey(token);
                Assert.isTrue(expression,"登录状态已过期！请重新登录");
            }
            return true;
        }catch (Exception e){
            response.setStatus(401);
            response.setCharacterEncoding(UTF_8);
            response.setContentType(String.valueOf(APPLICATION_JSON));
            ServletUtil.renderString(response,JSON.toJSONString(JsonResult.success(401,"用户未授权！请重新登录",null)),401);
            return false;
        }
    }
}
