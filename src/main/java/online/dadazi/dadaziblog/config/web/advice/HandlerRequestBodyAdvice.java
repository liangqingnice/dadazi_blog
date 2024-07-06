package online.dadazi.dadaziblog.config.web.advice;

import lombok.extern.slf4j.Slf4j;
import online.dadazi.dadaziblog.config.annotation.PathHandler;
import online.dadazi.dadaziblog.config.tools.BodyAdviceUtil;
import online.dadazi.dadaziblog.config.tools.FileUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * 处理请求body数据
 *
 * @author lqk
 */

@RestControllerAdvice
@Slf4j
public class HandlerRequestBodyAdvice implements RequestBodyAdvice {
    /**
     * 开启拦截
     * @param methodParameter the method parameter
     * @param targetType the target type, not necessarily the same as the method
     * parameter type, e.g. for {@code HttpEntity<String>}.
     * @param converterType the selected converter type
     * @return Boolean
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * body之前处理
     * @param inputMessage the request
     * @param parameter the target method parameter
     * @param targetType the target type, not necessarily the same as the method
     * parameter type, e.g. for {@code HttpEntity<String>}.
     * @param converterType the converter used to deserialize the body
     * @return HttpInputMessage
     * @throws IOException
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    /**
     * body之后处理
     * @param body set to the converter Object before the first advice is called
     * @param inputMessage the request
     * @param parameter the target method parameter
     * @param targetType the target type, not necessarily the same as the method
     * parameter type, e.g. for {@code HttpEntity<String>}.
     * @param converterType the converter used to deserialize the body
     * @return Object
     */
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        if (Objects.isNull(body) ) {
            return body;
        }

        try {
            BodyAdviceUtil.resetSimpleObject(body, PathHandler.class, new BodyAdvice() {
                @Override
                public Object getAdviceFieldValue(Object fieldValue) {
                    return FileUtil.getDbFilePath(String.valueOf(fieldValue));
                }
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return body;
    }

    /**
     * 空值body处理
     * @param body usually set to {@code null} before the first advice is called
     * @param inputMessage the request
     * @param parameter the method parameter
     * @param targetType the target type, not necessarily the same as the method
     * parameter type, e.g. for {@code HttpEntity<String>}.
     * @param converterType the selected converter type
     * @return handleEmptyBody
     */
    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
