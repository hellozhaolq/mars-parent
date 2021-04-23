package com.zhaolq.mars.tool.core.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Jackson工具类
 *
 * json     to
 *              object
 *              list
 *              map
 * object   to
 *              json
 *              list    无法转换
 *              map     object转object
 * list     to
 *              json    object转json
 *              object  无法转换
 *              map     object转object
 * map      to
 *              json    object转json
 *              object  object转object
 *              list    object转object
 *
 * @author zhaolq
 * @date 2021/4/20 14:54
 */
public class JacksonUtil {

    /**
     * 定义jackson对象
     */
    private static final ObjectMapper objectMapper;
    private static final ObjectMapper objectMapperIgnoreNull;

    private JacksonUtil() {
    }

    static {
        // 初始化
        objectMapperIgnoreNull = new ObjectMapper();
        // 忽略空值
        objectMapperIgnoreNull.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        objectMapper = objectMapperIgnoreNull;
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

    /**
     * 获取ObjectMapper单例，忽略空值
     *
     * @param
     * @return com.fasterxml.jackson.databind.ObjectMapper
     */
    public static ObjectMapper getInstanceIgnoreNull() {
        return objectMapperIgnoreNull;
    }

    /********************************** json to object/list/map **********************************/

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
