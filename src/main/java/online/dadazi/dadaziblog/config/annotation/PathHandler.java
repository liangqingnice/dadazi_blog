package online.dadazi.dadaziblog.config.annotation;

import java.lang.annotation.*;

/**
 * 文件路径处理注解
 * @author lqk
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE, ElementType.FIELD })
@Inherited
@Documented
public @interface PathHandler {
}
