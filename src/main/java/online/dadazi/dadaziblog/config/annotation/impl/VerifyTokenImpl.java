package online.dadazi.dadaziblog.config.annotation.impl;

import online.dadazi.dadaziblog.config.annotation.VerifyToken;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;

/**
 * @author lqk
 */
@Component
public class VerifyTokenImpl {
    /**
     * 验证是否登录
     *
     * @param handlerMethod
     * @return
     */
    public boolean isVerifyToken(HandlerMethod handlerMethod) {
        VerifyToken validateToken = getAnnotationsVerifyToken(handlerMethod);
        if (validateToken == null) {
            return true;
        }
        return validateToken.isCheck();
    }


    /**
     * 判断类或者方法
     *
     * @param handlerMethod
     * @return
     */
    public VerifyToken getAnnotationsVerifyToken(HandlerMethod handlerMethod) {
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(VerifyToken.class)) {
            return method.getAnnotation(VerifyToken.class);
        }
        Class<?> type = handlerMethod.getBeanType();
        if (type.isAnnotationPresent(VerifyToken.class)) {
            return type.getAnnotation(VerifyToken.class);
        }
        return null;
    }


}
