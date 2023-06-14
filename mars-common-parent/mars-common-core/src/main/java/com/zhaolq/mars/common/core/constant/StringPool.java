/*
 * Copyright (c) Zhaolq Technologies Co., Ltd. 大约40亿年前-9999. All rights reserved.
 */

package com.zhaolq.mars.common.core.constant;

import org.apache.commons.lang3.SystemUtils;

/**
 * 常用字符串常量定义
 *
 * @author zhaolq
 * @date 2022/3/12 17:25
 * @see
 * <a href="https://github.com/baomidou/mybatis-plus/blob/3.0/mybatis-plus-core/src/main/java/com/baomidou/mybatisplus/core/toolkit/StringPool.java">StringPool</a>
 * @since 1.0.0
 */
public interface StringPool {

    String NULL = "null";
    String EMPTY = "";
    String SPACE = " ";

    String UTF_8 = "UTF-8";
    String US_ASCII = "US-ASCII";
    String ISO_8859_1 = "ISO-8859-1";
    String GBK = "GBK";

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
    String FILE_SEPARATOR = SystemUtils.getEnvironmentVariable("file.separator", "");

    /**
     * 系统路径分隔符
     */
    String PATH_SEPARATOR = SystemUtils.getEnvironmentVariable("path.separator", "");

    /**
     * 系统直线分隔符
     */
    String LINE_SEPARATOR = SystemUtils.getEnvironmentVariable("line.separator", "");


    /**
     * 字符常量：空格符 {@code ' '}
     */
    char C_SPACE = CharPool.SPACE;

    /**
     * 字符常量：制表符 {@code '\t'}
     */
    char C_TAB = CharPool.TAB;

    /**
     * 字符常量：点 {@code '.'}
     */
    char C_DOT = CharPool.DOT;

    /**
     * 字符常量：斜杠 {@code '/'}
     */
    char C_SLASH = CharPool.SLASH;

    /**
     * 字符常量：反斜杠 {@code '\\'}
     */
    char C_BACKSLASH = CharPool.BACKSLASH;

    /**
     * 字符常量：回车符 {@code '\r'}
     */
    char C_CR = CharPool.CR;

    /**
     * 字符常量：换行符 {@code '\n'}
     */
    char C_LF = CharPool.LF;

    /**
     * 字符常量：下划线 {@code '_'}
     */
    char C_UNDERLINE = CharPool.UNDERLINE;

    /**
     * 字符常量：逗号 {@code ','}
     */
    char C_COMMA = CharPool.COMMA;

    /**
     * 字符常量：花括号（左） <code>'{'</code>
     */
    char C_DELIM_START = CharPool.DELIM_START;

    /**
     * 字符常量：花括号（右） <code>'}'</code>
     */
    char C_DELIM_END = CharPool.DELIM_END;

    /**
     * 字符常量：中括号（左） {@code '['}
     */
    char C_BRACKET_START = CharPool.BRACKET_START;

    /**
     * 字符常量：中括号（右） {@code ']'}
     */
    char C_BRACKET_END = CharPool.BRACKET_END;

    /**
     * 字符常量：冒号 {@code ':'}
     */
    char C_COLON = CharPool.COLON;

    /**
     * 字符常量：艾特 {@code '@'}
     */
    char C_AT = CharPool.AT;

    /**
     * 字符串常量：制表符 {@code "\t"}
     */
    String TAB = "	";

    /**
     * 字符串常量：点 {@code "."}
     */
    String DOT = ".";

    /**
     * 字符串常量：双点 {@code ".."} <br>
     * 用途：作为指向上级文件夹的路径，如：{@code "../path"}
     */
    String DOUBLE_DOT = "..";

    /**
     * 字符串常量：斜杠 {@code "/"}
     */
    String SLASH = "/";

    /**
     * 字符串常量：反斜杠 {@code "\\"}
     */
    String BACKSLASH = "\\";

    /**
     * 字符串常量：回车符 {@code "\r"} <br>
     * 解释：该字符常用于表示 Linux 系统和 MacOS 系统下的文本换行
     */
    String CR = "\r";

    /**
     * 字符串常量：换行符 {@code "\n"}
     */
    String LF = "\n";

    /**
     * 字符串常量：Windows 换行 {@code "\r\n"} <br>
     * 解释：该字符串常用于表示 Windows 系统下的文本换行
     */
    String CRLF = "\r\n";

    /**
     * 字符串常量：下划线 {@code "_"}
     */
    String UNDERLINE = "_";

    /**
     * 字符串常量：减号（连接符） {@code "-"}
     */
    String DASHED = "-";

    /**
     * 字符串常量：逗号 {@code ","}
     */
    String COMMA = ",";

    /**
     * 字符串常量：花括号（左） <code>"{"</code>
     */
    String DELIM_START = "{";

    /**
     * 字符串常量：花括号（右） <code>"}"</code>
     */
    String DELIM_END = "}";

    /**
     * 字符串常量：中括号（左） {@code "["}
     */
    String BRACKET_START = "[";

    /**
     * 字符串常量：中括号（右） {@code "]"}
     */
    String BRACKET_END = "]";

    /**
     * 字符串常量：冒号 {@code ":"}
     */
    String COLON = ":";

    /**
     * 字符串常量：艾特 {@code "@"}
     */
    String AT = "@";


    /**
     * 字符串常量：HTML 空格转义 {@code "&nbsp;" -> " "}
     */
    String HTML_NBSP = "&nbsp;";

    /**
     * 字符串常量：HTML And 符转义 {@code "&amp;" -> "&"}
     */
    String HTML_AMP = "&amp;";

    /**
     * 字符串常量：HTML 双引号转义 {@code "&quot;" -> "\""}
     */
    String HTML_QUOTE = "&quot;";

    /**
     * 字符串常量：HTML 单引号转义 {@code "&apos" -> "'"}
     */
    String HTML_APOS = "&apos;";

    /**
     * 字符串常量：HTML 小于号转义 {@code "&lt;" -> "<"}
     */
    String HTML_LT = "&lt;";

    /**
     * 字符串常量：HTML 大于号转义 {@code "&gt;" -> ">"}
     */
    String HTML_GT = "&gt;";

    /**
     * 字符串常量：空 JSON {@code "{}"}
     */
    String EMPTY_JSON = "{}";

    /**
     * .java文件扩展名
     */
    public static final String EXT_JAVA = ".java";
    /**
     * .class文件扩展名
     */
    public static final String EXT_CLASS = ".class";
    /**
     * .jar文件扩展名
     */
    public static final String EXT_JAR = ".jar";

    /**
     * 针对ClassPath路径的伪协议前缀（兼容Spring）: "classpath:"
     */
    public static final String CLASSPATH_URL_PREFIX = "classpath:";
    /**
     * URL 前缀表示文件: "file:"
     */
    public static final String FILE_URL_PREFIX = "file:";
    /**
     * URL 前缀表示jar: "jar:"
     */
    public static final String JAR_URL_PREFIX = "jar:";
    /**
     * URL 前缀表示war: "war:"
     */
    public static final String WAR_URL_PREFIX = "war:";
    /**
     * URL 协议表示文件: "file"
     */
    public static final String URL_PROTOCOL_FILE = "file";
    /**
     * URL 协议表示Jar文件: "jar"
     */
    public static final String URL_PROTOCOL_JAR = "jar";
    /**
     * URL 协议表示zip文件: "zip"
     */
    public static final String URL_PROTOCOL_ZIP = "zip";
    /**
     * URL 协议表示WebSphere文件: "wsjar"
     */
    public static final String URL_PROTOCOL_WSJAR = "wsjar";
    /**
     * URL 协议表示JBoss zip文件: "vfszip"
     */
    public static final String URL_PROTOCOL_VFSZIP = "vfszip";
    /**
     * URL 协议表示JBoss文件: "vfsfile"
     */
    public static final String URL_PROTOCOL_VFSFILE = "vfsfile";
    /**
     * URL 协议表示JBoss VFS资源: "vfs"
     */
    public static final String URL_PROTOCOL_VFS = "vfs";
    /**
     * Jar路径以及内部文件路径的分界符: "!/"
     */
    public static final String JAR_URL_SEPARATOR = "!/";
    /**
     * WAR路径及内部文件路径分界符
     */
    public static final String WAR_URL_SEPARATOR = "*/";
}
