package online.dadazi.dadaziblog.config.web.advice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import online.dadazi.dadaziblog.config.annotation.PathHandler;
import online.dadazi.dadaziblog.config.exception.CustomException;
import online.dadazi.dadaziblog.config.pojo.JsonResult;
import online.dadazi.dadaziblog.config.tools.BodyAdviceUtil;
import online.dadazi.dadaziblog.config.tools.FileUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.Serial;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 处理请求body数据
 *
 * @author lqk
 */

@RestControllerAdvice
@Slf4j
public class HandlerResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    /**
     * 是否开启拦截
     * @param returnType the return type
     * @param converterType the selected converter type
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * 处理返回json
     * @param body the body to be written
     * @param returnType the return type of the controller method
     * @param selectedContentType the content type selected through content negotiation
     * @param selectedConverterType the converter type selected to write to the response
     * @param request the current request
     * @param response the current response
     * @return
     */

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (Objects.isNull(body)) {
            return body;
        }
        Object dataBody = body;
        if (body instanceof JsonResult<?>) {
            dataBody = ((JsonResult<?>) body).getData();
        }
        try {
            BodyAdviceUtil.resetComplexObject(dataBody, PathHandler.class, new BodyAdvice() {
                @Override
                public Object getAdviceFieldValue(Object fieldValue) throws Exception {
                    return FileUtil.getAllFilePath(String.valueOf(fieldValue));
                }
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return body;
    }
}
