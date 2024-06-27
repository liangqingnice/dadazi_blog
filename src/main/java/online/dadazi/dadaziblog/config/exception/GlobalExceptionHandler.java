package online.dadazi.dadaziblog.config.exception;


import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import online.dadazi.dadaziblog.config.pojo.JsonResult;
import org.apache.el.parser.Token;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import java.util.List;

/**
 * 全局异常处理
 *
 * @author lqk
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public JsonResult<?> exceptionHandler(Exception e) {
        e.printStackTrace();
        log.error(e.getMessage(),e);
        return JsonResult.error(StrUtil.isNotBlank(e.getMessage())?e.getMessage(): "操作失败");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public JsonResult<?> handleNoHandlerFoundException(NoResourceFoundException ex, HttpServletRequest request) {
        log.error("{}接口不存在 ", request.getRequestURI());
        return JsonResult.error("接口不存在");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResult<?> handleNoHandlerFoundException(MethodArgumentNotValidException mane, HttpServletRequest request) {
        log.error("请求参数有误！请求地址:{}", request.getRequestURI());
        List<ObjectError> allErrors = mane.getBindingResult().getAllErrors();
        log.error("错误：{}",allErrors.get(0).getDefaultMessage());
        return JsonResult.error(!allErrors.isEmpty()?allErrors.get(0).getDefaultMessage():"请求参数有误");
    }


    @ExceptionHandler( DuplicateKeyException.class)
    public JsonResult<?> handleDuplicateKeyException(DuplicateKeyException sicve, HttpServletRequest request) {
        log.error("入库数据有误！请求地址:{}", request.getRequestURI());
        log.error(sicve.getMessage());
        return JsonResult.error("数据已存在或冲突，请检查您输入的数据");
    }
    @ExceptionHandler(MyBatisSystemException.class)
    public JsonResult<?> handleMyBatisSystemException(MyBatisSystemException mbse,HttpServletRequest request){
        log.error("请求地址:{}", request.getRequestURI());
        log.error(mbse.getCause().getCause().getMessage());
        return JsonResult.error("数据请求异常");
    }

    /**
     * token过期异常
     * @param tee
     * @return {@link JsonResult }<{@link ? }>
     */
    @ExceptionHandler(TokenExpiredException.class)
    public JsonResult<?> handleTokenExpiredException(TokenExpiredException tee){
        log.error("token过期");
        return JsonResult.error("用户登录已失效,请重新登录");
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public JsonResult<?> HandlerMethodValidationExceptions(HandlerMethodValidationException hmve,HttpServletRequest request){
        log.error("参数错误,请求地址:{}", request.getRequestURI());
        return JsonResult.error("参数错误");
    }
}