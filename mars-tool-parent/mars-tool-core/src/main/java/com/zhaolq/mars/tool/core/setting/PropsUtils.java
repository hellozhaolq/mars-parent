package com.zhaolq.mars.tool.core.setting;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.dialect.PropsUtil;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Props工具类 提供静态方法获取配置文件
 *
 * @author zhaolq
 * @date 2021/5/21 13:22
 */
public class PropsUtils extends PropsUtil {

    private static final Map<String, Props> propsMap = new ConcurrentHashMap<>();

    public static Props get(String name, Charset charset) {
        return propsMap.computeIfAbsent(name, (filePath) -> {
            final String extName = FileUtil.extName(filePath);
            if (StrUtil.isEmpty(extName)) {
                filePath = filePath + "." + Props.EXT_NAME;
            }
            return new Props(filePath, charset);
        });
    }

}
