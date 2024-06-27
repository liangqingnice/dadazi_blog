package online.dadazi.dadaziblog.config.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@AllArgsConstructor
/**
 * @author lqk
 * JSON返回实体
 * @param <T>
 */ public class JsonResult<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 信息
     */
    private String message;
    /**
     * 相应数据
     */
    private T data;

    /**
     * 禁止实例化
     */
    private JsonResult() {
    }

    ;

    public static <E> JsonResult<E> success(E data) {
        return new JsonResult<>(200, "操作成功", data);
    }

    public static JsonResult success() {
        return new JsonResult(200, "操作成功", null);
    }

    public static JsonResult success(String message) {
        return new JsonResult(200, message, null);
    }

    public static <E> JsonResult<E> success(String message, E data) {
        return new JsonResult(200, message, data);
    }

    public static <E> JsonResult<E> success(Integer code, String message, E data) {
        return new JsonResult(code, message, data);
    }

    public static JsonResult error(String message) {
        return new JsonResult(500, message, null);
    }

    public static JsonResult error() {
        return new JsonResult(500, "操作失败", null);
    }
}
