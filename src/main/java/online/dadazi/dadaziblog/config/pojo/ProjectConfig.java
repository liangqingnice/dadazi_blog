package online.dadazi.dadaziblog.config.pojo;

import lombok.Data;
import lombok.ToString;
import online.dadazi.dadaziblog.config.web.YmlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 项目配置
 * @author lqk
 */
@Configuration
@ConfigurationProperties(prefix = "project")
@PropertySource(value="classpath:config/yml/project.yml",factory = YmlPropertySourceFactory.class)
@Component
@ToString
@Data
public class ProjectConfig {
    /**
     * 项目名称
     */
    public static String name;
    /**
     * 项目版本
     */
    public String version;
    /**
     * 项目描述
     */
    public String description;
    /**
     * 上传地址
     */
    public static String uploadDir;

    public static String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        ProjectConfig.uploadDir = uploadDir;
    }

    public static String getName() {
        return name;
    }

    public void setName(String name){
        ProjectConfig.name = name;
    }


}
