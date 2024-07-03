package online.dadazi.dadaziblog.config.exception;

import lombok.Getter;

import java.io.Serial;

/***
 * 自定义异常
 * @author lqk
 */
public class CustomException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 状态码
     */
    private Integer statusCode;

    public CustomException(int statusCode, String message) {
        // 调用父类构造方法传递消息
        super(message);
        // 设置状态码
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

}
