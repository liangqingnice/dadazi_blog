package online.dadazi.dadaziblog.config.pojo;

import lombok.Data;
import lombok.ToString;
import online.dadazi.dadaziblog.config.web.YmlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * oss配置类
 *
 * @author lqk
 */
@Configuration
@ConfigurationProperties(prefix = "oss")
@PropertySource(name = "oss", value = "classpath:config/yml/oss.yml", factory = YmlPropertySourceFactory.class)
@Component
@ToString
@Data
public class OssConfig {
    /**
     * 是否开启oss
     */
    public static Boolean enable;
    /**
     * 服务商
     */
    private Integer provider;

    /**
     * 地域
     */
    private Integer demesne;
    /**
     * accessKey
     */
    private String accessKey;

    /**
     * secretKey
     */
    private String secretKey;

    /**
     * 业务域名
     */
    public static String serviceDomain;


    public static String getServiceDomain() {
        return serviceDomain;
    }

    public void setServiceDomain(String serviceDomain) {
        OssConfig.serviceDomain = serviceDomain;
    }

    public static Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        OssConfig.enable = enable;
    }
}
