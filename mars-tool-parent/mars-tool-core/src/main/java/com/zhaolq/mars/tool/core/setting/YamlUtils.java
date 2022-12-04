package com.zhaolq.mars.tool.core.setting;


import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import com.zhaolq.mars.tool.core.io.FileUtils;
import com.zhaolq.mars.tool.core.utils.JsonUtils;

import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Yaml工具类 提供静态方法获取配置文件
 *
 * @author zhaolq
 * @date 2021/5/21 14:24
 */
public final class YamlUtils {

    /**
     * 默认配置文件扩展名
     */
    public final static String EXT_NAME = "yml";

    private static final Map<String, JSONObject> yamlMap = new ConcurrentHashMap<>();

    /**
     * 获取当前环境下的配置文件
     *
     * @param name 可以为不包括扩展名的文件名（默认.yml），也可以是文件名全称
     * @return java.lang.Object
     */
    public static JSONObject get(String name) {
        return yamlMap.computeIfAbsent(name, (filePath) -> {
            final String extName = FileUtil.extName(filePath);
            if (StringUtils.isEmpty(extName)) {
                filePath = filePath + "." + YamlUtils.EXT_NAME;
            }
            Yaml yaml = new Yaml();
            InputStream inputStream = FileUtils.getInputStream(filePath);
            return JsonUtils.parseObj(yaml.loadAs(inputStream, LinkedHashMap.class));
        });
    }

    public static String getValueFromKey(String key, JSONObject jsonObject) {
        String result = null;
        try {
            String[] strArr = StringUtils.split(key, ".");
            JSONObject jsonObjectTemp = JsonUtils.parseObj(jsonObject.toString());
            for (int index = 0; index < strArr.length - 1; index++) {
                jsonObjectTemp = JsonUtils.parseObj(jsonObjectTemp.get(strArr[index]));
            }
            result = jsonObjectTemp.get(strArr[strArr.length - 1]).toString();
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

}
