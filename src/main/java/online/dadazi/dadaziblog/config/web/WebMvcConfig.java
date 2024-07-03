package online.dadazi.dadaziblog.config.web;

import jakarta.annotation.Resource;
import online.dadazi.dadaziblog.config.enums.ResPrefixEnums;
import online.dadazi.dadaziblog.config.pojo.ProjectConfig;
import online.dadazi.dadaziblog.config.tools.FileUtil;
import online.dadazi.dadaziblog.config.web.interceptors.SysInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * webMvc配置
 *
 * @author lqk
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private SysInterceptor sysInterceptor;

    /**
     * 接口统一加前缀
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 控制器路由统一加前缀
        for (ResPrefixEnums restPrefix : ResPrefixEnums.values()) {
            configurer.addPathPrefix(restPrefix.getPathPrefix(), c -> ResPrefixEnums.isAddPrefix(c, restPrefix));
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sysInterceptor).excludePathPatterns("/sys/user/login", "/sys/user/login", "/" + FileUtil.UPLOAD_DIR + "/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(FileUtil.UPLOAD_DIR + "/**").addResourceLocations("file:" + ProjectConfig.getUploadDir() + "/");
    }
}
