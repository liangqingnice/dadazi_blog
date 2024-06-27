package online.dadazi.dadaziblog.config.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 防止重复注解
 * @author lqk
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatSubmit {
    /**
     * 重置消息
     * @return
     */
    String message() default "操作频繁";

    /**
     * 默认5秒
     * @return
     */
    int lockTime() default 5;
}
