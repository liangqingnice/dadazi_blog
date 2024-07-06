package online.dadazi.dadaziblog.config.web.advice;

/**
 * BodyAdvice
 *
 * @author lqk
 */
public interface BodyAdvice {
    /**
     * 获取字段值
     * @param fieldValue
     * @return
     * @throws Exception
     */
    public Object getAdviceFieldValue(Object fieldValue) throws Exception;

}
