package com.zhaolq.mars.tool.core.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author zhaolq
 * @date 2021/4/20 14:54
 */
public class JacksonUtil {

    /**
     * 定义jackson对象
     */
    private static final ObjectMapper objectMapper;
    /**
     * 忽略空值
     */
    private static final ObjectMapper objectMapperIgnoreNull;

    private JacksonUtil() {
    }

    static {
        // 初始化
        objectMapper = new ObjectMapper();
        // 初始化
        objectMapperIgnoreNull = new ObjectMapper();
        // 忽略空值
        objectMapperIgnoreNull.setSerializationInclusion(JsonInclude.Include.NON_NULL);
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

    /**
     * 将JavaBean、列表数组转为json。
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
     * 将JavaBean、列表数组转为json，忽略空值。
     *
     * @param obj
     * @return java.lang.String
     */
    public static String objectToJsonIgnoreNull(Object obj) {
        try {
            return objectMapperIgnoreNull.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json转为JavaBean
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
     * 将json转为 JavaBean List
     *
     * @param jsonString
     * @param clazz
     * @return java.util.List<T>
     */
    public static <T> List<T> jsonToList(String jsonString, Class<T> clazz) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
        try {
            return objectMapper.readValue(jsonString, javaType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Map json2Map(String jsonString, Class keyType, Class valueType) {
        JavaType javaType = objectMapper.getTypeFactory().constructMapType(Map.class, keyType, valueType);
        try {
            return objectMapper.readValue(jsonString, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
