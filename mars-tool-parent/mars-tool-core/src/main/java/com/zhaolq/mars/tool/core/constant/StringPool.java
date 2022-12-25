/*
 * Copyright (c) Zhaolq Technologies Co., Ltd. 大约40亿年前-9999. All rights reserved.
 */

package com.zhaolq.mars.tool.core.constant;

import com.zhaolq.mars.tool.core.system.SystemUtils;

import cn.hutool.core.text.StrPool;

/**
 * 常用字符串常量定义
 *
 * @author zhaolq
 * @date 2022/3/12 17:25
 * @see
 * <a href="https://github.com/baomidou/mybatis-plus/blob/3.0/mybatis-plus-core/src/main/java/com/baomidou/mybatisplus/core/toolkit/StringPool.java">StringPool</a>
 * @since 1.0.0
 */
public interface StringPool extends StrPool {

    String NULL = "null";
    String EMPTY = "";
    String SPACE = " ";

    String UTF_8 = "UTF-8";
    String US_ASCII = "US-ASCII";
    String ISO_8859_1 = "ISO-8859-1";

    String YES = "yes";
    String NO = "no";

    String ZERO = "0";
    String ONE = "1";

    String ON = "on";
    String OFF = "off";

    String TRUE = "true";
    String FALSE = "false";

    String DOT_CLASS = ".class";
    String DOT_JAVA = ".java";
    String DOT_XML = ".xml";
    String DOT_PROPERTIES = ".properties";

    String DOT_TXT = ".txt";
    String DOT_DOCX = ".docx";
    String DOT_PPTX = ".pptx";
    String DOT_XLSX = ".xlsx";

    /**
     * 系统文件分隔符
     */
    String FILE_SEPARATOR = SystemUtils.get("file.separator", "");

    /**
     * 系统路径分隔符
     */
    String PATH_SEPARATOR = SystemUtils.get("path.separator", "");

    /**
     * 系统直线分隔符
     */
    String LINE_SEPARATOR = SystemUtils.get("line.separator", "");
}
