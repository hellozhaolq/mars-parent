package com.zhaolq.mars.tool.core.utils;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Jackson工具类
 *
 * json     to
 *              object
 *              map
 *              list
 * object   to
 *              json
 *              map     object转object
 *              list    在一定前提下可以转换
 * map      to
 *              json    object转json
 *              object  object转object
 *              list    在一定前提下可以转换
 * list     to
 *              json    object转json
 *              object  无法转换
 *              map     在一定前提下可以转换
 *
 * @author zhaolq
 * @date 2021/4/20 14:54
 */
public final class JacksonUtils {

    private static final ObjectMapper objectMapper;

    private JacksonUtils() {
    }

    static {
        objectMapper = new ObjectMapper();
        // 设置时间转换所使用的默认时区
        objectMapper.setTimeZone(TimeZone.getDefault());
        // 忽略空值，null不生成到json字符串中
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 在Date类型变量上使用注解 @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")，序列化时将转换成指定的格式。注解优先。
        objectMapper.setDateFormat(new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN));

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_MS_PATTERN)))
                .addDeserializer(LocalDateTime.class,
                        new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_MS_PATTERN)));
        objectMapper.registerModule(javaTimeModule);
    }

    /**
     * 获取ObjectMapper单例
     *
     * @param
     * @return com.fasterxml.jackson.databind.ObjectMapper
     */
    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    /********************************** json to object/map/list **********************************/

    /**
     * json转object
     *
     * @param jsonString
     * @param clazz
     * @return T
     */
    public static <T> T jsonToObject(String jsonString, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json转List
     *
     * @param jsonString
     * @return java.util.List<java.lang.Object>
     */
    public static List<Object> jsonToList(String jsonString) {
        return jsonToList(jsonString, Object.class);
    }

    /**
     * json转List
     *
     * @param jsonString
     * @param clazz
     * @return java.util.List<T>
     */
    public static <T> List<T> jsonToList(String jsonString, Class<T> clazz) {
        return (List<T>) jsonToList(jsonString, List.class, clazz);
    }

    /**
     * json转List。也适用于单列集合嵌套。
     *
     * @param jsonString
     * @param clazz
     * @return java.util.List<T>
     */
    public static Collection jsonToList(String jsonString, Class<?> collectionClass, Class<?>... clazz) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, clazz);
        try {
            return objectMapper.readValue(jsonString, javaType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json转list递归，如果list内部的元素存在jsonString，继续解析
     *
     * @param jsonString
     */
    private static List<Object> jsonToListRecursion(String jsonString) {
        return null;
    }

    /**
     * json转map
     *
     * @param jsonString
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    public static Map<String, Object> jsonToMap(String jsonString) {
        return jsonToMap(jsonString, String.class, Object.class);
    }

    /**
     * json转map
     *
     * @param jsonString
     * @param keyClass
     * @param valueClass
     * @return java.util.Map<K, V>
     */
    public static <K, V> Map<K, V> jsonToMap(String jsonString, Class<K> keyClass, Class<V> valueClass) {
        return jsonToMap(jsonString, Map.class, keyClass, valueClass);
    }

    /**
     * json转map
     *
     * @param jsonString
     * @param mapClass
     * @param keyClass
     * @param valueClass
     * @return java.util.Map<K, V>
     */
    public static <K, V> Map<K, V> jsonToMap(String jsonString, Class<? extends Map> mapClass, Class<K> keyClass, Class<V> valueClass) {
        JavaType javaType = objectMapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
        try {
            return objectMapper.readValue(jsonString, javaType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json转map递归，如果map内部的value存在jsonString，继续解析
     *
     * @param jsonString
     */
    private static Map<String, Object> jsonToMapRecursion(String jsonString) {
        return null;
    }

    /********************************** object to json/object **********************************/

    /**
     * object转json，忽略空值。
     *
     * @param obj
     * @return java.lang.String
     */
    public static String objectToJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * object转object。如：map转object
     *
     * @param obj
     * @param clazz
     * @return T
     */
    public static <T> T objectToObject(Object obj, Class<T> clazz) {
        return objectMapper.convertValue(obj, clazz);
    }
}
