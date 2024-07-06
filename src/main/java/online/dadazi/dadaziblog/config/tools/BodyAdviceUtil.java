package online.dadazi.dadaziblog.config.tools;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.dadazi.dadaziblog.config.web.advice.BodyAdvice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * BodyAdvice工具
 *
 * @author lqk
 */
public class BodyAdviceUtil {
    /**
     * 处理简单对象
     *
     * @param body
     * @param annotationClass
     * @param bodyAdvice
     * @return void
     * @throws Exception
     */
    public static void resetSimpleObject(Object body, Class<? extends Annotation> annotationClass, BodyAdvice bodyAdvice) throws Exception {
        if (body == null) {
            return;
        }
        Class<? extends Object> bodyClass = body.getClass();
        if (!bodyClass.isAnnotationPresent(annotationClass)) {
            return;
        }
        resetObject(body, annotationClass, bodyAdvice, bodyClass);

    }

    /**
     * 处理复杂对象
     *
     * @param body
     * @param annotationClass
     * @param bodyAdvice
     * @return void
     * @throws Exception
     */
    public static void resetComplexObject(Object body, Class<? extends Annotation> annotationClass, BodyAdvice bodyAdvice) throws Exception {

        if (body == null) {
            return;
        }

        // 如果返回的是String
        if (body instanceof String) {
            return;
        }

        if (body instanceof Page<?>) {
            resetComplexObjectList(((Page<?>) body).getRecords(), annotationClass, bodyAdvice);
            return;
        }

        if (body instanceof List<?>) {
            resetComplexObjectList(((List<?>) body), annotationClass, bodyAdvice);
            return;
        }

        Class<? extends Object> bodyClass = body.getClass();

        if (bodyClass.isArray()) {
            int length = Array.getLength(body);
            for (int i = 0; i < length; i++) {
                resetComplexObject(Array.get(body, i), annotationClass, bodyAdvice);
            }
            return;
        }

        if (!bodyClass.isAnnotationPresent(annotationClass)) {
            return;
        }

        resetObject(body, annotationClass, bodyAdvice, bodyClass);

    }

    /**
     * 对象列表处理
     *
     * @param bodyList
     * @param annotationClass
     * @param bodyAdvice
     * @throws Exception
     */
    private static void resetComplexObjectList(List<?> bodyList, Class<? extends Annotation> annotationClass, BodyAdvice bodyAdvice) throws Exception {
        for (Object object : bodyList) {
            resetComplexObject(object, annotationClass, bodyAdvice);
        }

    }

    /**
     * 处理实体对象
     *
     * @param body            实体
     * @param annotationClass 注解
     * @param bodyAdvice      内部实现
     * @param bodyClass
     * @throws IllegalAccessException
     * @throws Exception
     */
    private static void resetObject(Object body, Class<? extends Annotation> annotationClass, BodyAdvice bodyAdvice, Class<? extends Object> bodyClass) throws IllegalAccessException, Exception {
        //是否用反射获取所有列
        Field[] bodyFields = bodyClass.getDeclaredFields();
        for (Field field : bodyFields) {
            //判断属性上是否有注解
            if (field.isAnnotationPresent(annotationClass)) {
                //开启私有属性访问
                field.setAccessible(true);
                //获取值
                Object fieldValue = field.get(body);
                if (fieldValue == null) {
                    continue;
                }
                //根据类型处理不同的数据
                if (fieldValue instanceof String) {
                    field.set(body, bodyAdvice.getAdviceFieldValue(fieldValue));
                } else if (fieldValue instanceof List) {
                    List<String> arr = new ArrayList<>();
                    for (Object object : ((List<?>) fieldValue)) {
                        if (object instanceof String) {
                            arr.add(bodyAdvice.getAdviceFieldValue(object).toString());
                        } else {
                            resetComplexObjectList(((List<?>) fieldValue), annotationClass, bodyAdvice);
                        }
                    }
                    field.set(body, arr);
                } else {
                    resetComplexObject(fieldValue, annotationClass, bodyAdvice);
                }
            }
        }
    }

}
