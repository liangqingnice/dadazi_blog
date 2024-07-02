package online.dadazi.dadaziblog.config.web;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import online.dadazi.dadaziblog.config.exception.CustomException;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.List;

/**
 * 自定义Yml扫描
 * 如果是多个yml需要@PropertySource注解 配置name属性来注入
 *
 * @author lqk
 */
@Slf4j
public class YmlPropertySourceFactory implements PropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader();
        List<PropertySource<?>> load = yamlPropertySourceLoader.load(name, resource.getResource());
        return load.get(0);
    }
}
