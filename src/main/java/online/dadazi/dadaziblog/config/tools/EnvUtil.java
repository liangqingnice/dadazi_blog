package online.dadazi.dadaziblog.config.tools;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * 项目配置
 *
 * @author lqk
 */
@Configuration
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EnvUtil {
    public static final String DEV = "dev";
    public static final String TEST = "test";
    public static final String PRO = "PRO";

    @Value("${spring.profiles.active}")
    private String env;

    /**
     * 是否是开发环境
     *
     * @return true 或者false
     */
    public Boolean isDev() {
        return DEV.equals(this.env);
    }

    /**
     * 是否是测试环境
     *
     * @return true 或者false
     */
    public Boolean isTest() {
        return TEST.equals(this.env);
    }

    /**
     * 是否是正式环境
     *
     * @return true 或者false
     */
    public Boolean isPro() {
        return PRO.equals(this.env);
    }


}
