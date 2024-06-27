package online.dadazi.dadaziblog.config.tools;

import cn.hutool.core.util.StrUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.Map;

import static cn.hutool.core.util.CharsetUtil.UTF_8;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON;

/**
 * Servlet 工具
 *
 * @author lqk
 */
@Component
public class ServletUtil {


    /**
     * @param response
     * @param string
     * @param code
     * @return {@link String }
     */
    public static String renderString(HttpServletResponse response, String string, int code) {
        try {
            response.setStatus(code);
            response.setContentType(String.valueOf(APPLICATION_JSON));
            response.setCharacterEncoding(UTF_8);
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param response
     * @param string
     * @return {@link String }
     */
    public static String renderString(HttpServletResponse response, String string) {
        return renderString(response, string, 200);
    }

    /**
     * 获取token
     ** @return
     */
    public static String getToken() {
        String token = getRequest().getHeader("Authorization");
        return StrUtil.isNotBlank(token) ? token : "";
    }

    /**
     * @return
     */
    public static Long getLoginId() {
        String token = getToken();
        Map<String, Object> map = JwtUtil.parseToken(token);
        return Long.valueOf(map.get("id").toString());
    }


    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取Request属性
     *
     * @return {@link ServletRequestAttributes }
     */
    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    public static String getDoMainName() {
        HttpServletRequest request=getRequest();
        StringBuffer url = request.getRequestURL();
        String contextPath = request.getServletContext().getContextPath();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
    }

}
