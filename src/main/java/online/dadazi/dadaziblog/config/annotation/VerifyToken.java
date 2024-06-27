package online.dadazi.dadaziblog.config.annotation;

import java.lang.annotation.*;

/**
 * 验证是否登录
 * @author lqk
 */
@Inherited
@Target(value = { ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VerifyToken {
    /**
     * 是否检查
     * @return
     */
    public boolean isCheck() default true;

}
