package com.zhaolq.mars.common.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.zhaolq.mars.common.core.constant.StringPool;
import com.zhaolq.mars.common.core.exception.IORuntimeException;
import com.zhaolq.mars.common.core.exception.UtilException;
import com.zhaolq.mars.common.core.io.IoUtil;
import com.zhaolq.mars.common.core.io.file.FileUtil;
import com.zhaolq.mars.common.core.net.NetUtil;

/**
 * Servlet相关工具类封装
 *
 * @Author zhaolq
 * @Date 2023/6/13 14:16:56
 */
public class ServletUtil {
    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_OPTIONS = "OPTIONS";
    public static final String METHOD_HEAD = "HEAD";
    public static final String METHOD_TRACE = "TRACE";

    // --------------------------------------------------------- getParam start

    /**
     * 获得所有请求参数
     *
     * @param request 请求对象{@link ServletRequest}
     * @return Map
     */
    public static Map<String, String[]> getParams(ServletRequest request) {
        final Map<String, String[]> map = request.getParameterMap();
        return Collections.unmodifiableMap(map);
    }

    /**
     * 获得所有请求参数
     *
     * @param request 请求对象{@link ServletRequest}
     * @return Map
     */
    public static Map<String, String> getParamMap(ServletRequest request) {
        Map<String, String> params = new HashMap<>();
        for (Map.Entry<String, String[]> entry : getParams(request).entrySet()) {
            params.put(entry.getKey(), StringUtils.join(entry.getValue(), StringPool.COMMA));
        }
        return params;
    }

    /**
     * 获取请求体<br>
     * 调用该方法后，getParam方法将失效
     *
     * @param request {@link ServletRequest}
     * @return 获得请求体
     */
    public static String getBody(ServletRequest request) {
        try (final BufferedReader reader = request.getReader()) {
            return IoUtil.read(reader, true);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 获取请求体byte[]<br>
     * 调用该方法后，getParam方法将失效
     *
     * @param request {@link ServletRequest}
     * @return 获得请求体byte[]
     */
    public static byte[] getBodyBytes(ServletRequest request) {
        try {
            return IoUtil.readBytes(request.getInputStream(), true);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    // --------------------------------------------------------- getParam end

    /**
     * 获取客户端IP
     *
     * <p>
     * 默认检测的Header:
     *
     * <pre>
     * 1、X-Forwarded-For
     * 2、X-Real-IP
     * 3、Proxy-Client-IP
     * 4、WL-Proxy-Client-IP
     * </pre>
     *
     * <p>
     * otherHeaderNames参数用于自定义检测的Header<br>
     * 需要注意的是，使用此方法获取的客户IP地址必须在Http服务器（例如Nginx）中配置头信息，否则容易造成IP伪造。
     * </p>
     *
     * @param request 请求对象{@link HttpServletRequest}
     * @param otherHeaderNames 其他自定义头文件，通常在Http服务器（例如Nginx）中配置
     * @return IP地址
     */
    public static String getClientIP(HttpServletRequest request, String... otherHeaderNames) {
        String[] headers = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        if (ArrayUtil.isNotEmpty(otherHeaderNames)) {
            headers = ArrayUtils.addAll(headers, otherHeaderNames);
        }

        return getClientIPByHeader(request, headers);
    }

    /**
     * 获取客户端IP
     *
     * <p>
     * headerNames参数用于自定义检测的Header<br>
     * 需要注意的是，使用此方法获取的客户IP地址必须在Http服务器（例如Nginx）中配置头信息，否则容易造成IP伪造。
     * </p>
     *
     * @param request 请求对象{@link HttpServletRequest}
     * @param headerNames 自定义头，通常在Http服务器（例如Nginx）中配置
     * @return IP地址
     */
    public static String getClientIPByHeader(HttpServletRequest request, String... headerNames) {
        String ip;
        for (String header : headerNames) {
            ip = request.getHeader(header);
            if (false == NetUtil.isUnknown(ip)) {
                return NetUtil.getMultistageReverseProxyIp(ip);
            }
        }

        ip = request.getRemoteAddr();
        return NetUtil.getMultistageReverseProxyIp(ip);
    }

    // --------------------------------------------------------- Header start

    /**
     * 获取请求所有的头（header）信息
     *
     * @param request 请求对象{@link HttpServletRequest}
     * @return header值
     */
    public static Map<String, String> getHeaderMap(HttpServletRequest request) {
        final Map<String, String> headerMap = new HashMap<>();
        final Enumeration<String> names = request.getHeaderNames();
        String name;
        while (names.hasMoreElements()) {
            name = names.nextElement();
            headerMap.put(name, request.getHeader(name));
        }
        return headerMap;
    }


    /**
     * 忽略大小写获得请求header中的信息
     *
     * @param request 请求对象{@link HttpServletRequest}
     * @param nameIgnoreCase 忽略大小写头信息的KEY
     * @return header值
     */
    public static String getHeaderIgnoreCase(HttpServletRequest request, String nameIgnoreCase) {
        final Enumeration<String> names = request.getHeaderNames();
        String name;
        while (names.hasMoreElements()) {
            name = names.nextElement();
            if (name != null && name.equalsIgnoreCase(nameIgnoreCase)) {
                return request.getHeader(name);
            }
        }

        return null;
    }

    /**
     * 获得请求header中的信息
     *
     * @param request 请求对象{@link HttpServletRequest}
     * @param name 头信息的KEY
     * @param charsetName 字符集
     * @return header值
     */
    public static String getHeader(HttpServletRequest request, String name, String charsetName) {
        return getHeader(request, name, CharsetUtil.charset(charsetName));
    }

    /**
     * 获得请求header中的信息
     *
     * @param request 请求对象{@link HttpServletRequest}
     * @param name 头信息的KEY
     * @param charset 字符集
     * @return header值
     */
    public static String getHeader(HttpServletRequest request, String name, Charset charset) {
        final String header = request.getHeader(name);
        if (null != header) {
            return CharsetUtil.convert(header, CharsetUtil.CHARSET_ISO_8859_1, charset);
        }
        return null;
    }

    /**
     * 客户浏览器是否为IE
     *
     * @param request 请求对象{@link HttpServletRequest}
     * @return 客户浏览器是否为IE
     */
    public static boolean isIE(HttpServletRequest request) {
        String userAgent = getHeaderIgnoreCase(request, "User-Agent");
        if (StringUtils.isNotBlank(userAgent)) {
            //noinspection ConstantConditions
            userAgent = userAgent.toUpperCase();
            return userAgent.contains("MSIE") || userAgent.contains("TRIDENT");
        }
        return false;
    }

    /**
     * 是否为GET请求
     *
     * @param request 请求对象{@link HttpServletRequest}
     * @return 是否为GET请求
     */
    public static boolean isGetMethod(HttpServletRequest request) {
        return METHOD_GET.equalsIgnoreCase(request.getMethod());
    }

    /**
     * 是否为POST请求
     *
     * @param request 请求对象{@link HttpServletRequest}
     * @return 是否为POST请求
     */
    public static boolean isPostMethod(HttpServletRequest request) {
        return METHOD_POST.equalsIgnoreCase(request.getMethod());
    }

    /**
     * 是否为Multipart类型表单，此类型表单用于文件上传
     *
     * @param request 请求对象{@link HttpServletRequest}
     * @return 是否为Multipart类型表单，此类型表单用于文件上传
     */
    public static boolean isMultipart(HttpServletRequest request) {
        if (false == isPostMethod(request)) {
            return false;
        }

        String contentType = request.getContentType();
        if (StringUtils.isBlank(contentType)) {
            return false;
        }
        return contentType.toLowerCase().startsWith("multipart/");
    }

    // --------------------------------------------------------- Header end

    // --------------------------------------------------------- Cookie start

    /**
     * 获得指定的Cookie
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @param name cookie名
     * @return Cookie对象
     */
    public static Cookie getCookie(HttpServletRequest httpServletRequest, String name) {
        return readCookieMap(httpServletRequest).get(name);
    }

    /**
     * 将cookie封装到Map里面
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @return Cookie map
     */
    public static Map<String, Cookie> readCookieMap(HttpServletRequest httpServletRequest) {
        final Cookie[] cookies = httpServletRequest.getCookies();
        if (ArrayUtil.isEmpty(cookies)) {
            return MapUtil.empty();
        }
        // 键不区分大小写的map
        Map<String, Cookie> cookieMap = new CaseInsensitiveMap<>();
        for (Cookie cookie : cookies) {
            cookieMap.put(cookie.getName(), cookie);
        }
        return cookieMap;
    }

    /**
     * 设定返回给客户端的Cookie
     *
     * @param response 响应对象{@link HttpServletResponse}
     * @param cookie Servlet Cookie对象
     */
    public static void addCookie(HttpServletResponse response, Cookie cookie) {
        response.addCookie(cookie);
    }

    /**
     * 设定返回给客户端的Cookie
     *
     * @param response 响应对象{@link HttpServletResponse}
     * @param name Cookie名
     * @param value Cookie值
     */
    public static void addCookie(HttpServletResponse response, String name, String value) {
        response.addCookie(new Cookie(name, value));
    }

    /**
     * 设定返回给客户端的Cookie<br>
     * Path: "/"<br>
     * No Domain
     *
     * @param response 响应对象{@link HttpServletResponse}
     * @param name cookie名
     * @param value cookie值
     * @param maxAgeInSeconds -1: 关闭浏览器清除Cookie. 0: 立即清除Cookie. &gt;0 : Cookie存在的秒数.
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds) {
        addCookie(response, name, value, maxAgeInSeconds, "/", null);
    }

    /**
     * 设定返回给客户端的Cookie
     *
     * @param response 响应对象{@link HttpServletResponse}
     * @param name cookie名
     * @param value cookie值
     * @param maxAgeInSeconds -1: 关闭浏览器清除Cookie. 0: 立即清除Cookie. >0 : Cookie存在的秒数.
     * @param path Cookie的有效路径
     * @param domain the domain name within which this cookie is visible; form is according to RFC 2109
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds, String path, String domain) {
        Cookie cookie = new Cookie(name, value);
        if (domain != null) {
            cookie.setDomain(domain);
        }
        cookie.setMaxAge(maxAgeInSeconds);
        cookie.setPath(path);
        addCookie(response, cookie);
    }

    // --------------------------------------------------------- Cookie end

    // --------------------------------------------------------- Response start

    /**
     * 获得PrintWriter
     *
     * @param response 响应对象{@link HttpServletResponse}
     * @return 获得PrintWriter
     * @throws IORuntimeException IO异常
     */
    public static PrintWriter getWriter(HttpServletResponse response) throws IORuntimeException {
        try {
            return response.getWriter();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 返回数据给客户端
     *
     * @param response 响应对象{@link HttpServletResponse}
     * @param text 返回的内容
     * @param contentType 返回的类型
     */
    public static void write(HttpServletResponse response, String text, String contentType) {
        response.setContentType(contentType);
        Writer writer = null;
        try {
            writer = response.getWriter();
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            throw new UtilException(e);
        } finally {
            IoUtil.close(writer);
        }
    }

    /**
     * 返回文件给客户端
     *
     * @param response 响应对象{@link HttpServletResponse}
     * @param file 写出的文件对象
     */
    public static void write(HttpServletResponse response, File file) {
        final String contentType = ObjectUtils.defaultIfNull(FileUtil.getMimeType(file), "application/octet-stream");
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            write(response, in, contentType, file.getName());
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 返回数据给客户端
     *
     * @param response 响应对象{@link HttpServletResponse}
     * @param in 需要返回客户端的内容
     * @param contentType 返回的类型，可以使用{@link FileUtil#getMimeType(File)}获取对应扩展名的MIME信息
     * <ul>
     *   <li>application/pdf</li>
     *   <li>application/vnd.ms-excel</li>
     *   <li>application/msword</li>
     *   <li>application/vnd.ms-powerpoint</li>
     * </ul>
     * docx、xlsx 这种 office 2007 格式 设置 MIME;网页里面docx 文件是没问题，但是下载下来了之后就变成doc格式了
     * 参考：<a href="https://my.oschina.net/shixiaobao17145/blog/32489">https://my.oschina.net/shixiaobao17145/blog/32489</a>
     * <ul>
     *   <li>MIME_EXCELX_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";</li>
     *   <li>MIME_PPTX_TYPE = "application/vnd.openxmlformats-officedocument.presentationml.presentation";</li>
     *   <li>MIME_WORDX_TYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";</li>
     *   <li>MIME_STREAM_TYPE = "application/octet-stream;charset=utf-8"; #原始字节流</li>
     * </ul>
     * @param fileName 文件名，自动添加双引号
     */
    public static void write(HttpServletResponse response, InputStream in, String contentType, String fileName) {
        final String charset = ObjectUtils.defaultIfNull(response.getCharacterEncoding(), CharsetUtil.UTF_8);
        URLEncoder.encode(fileName, CharsetUtil.charset(charset));
        response.setHeader("Content-Disposition",
                String.format("attachment;filename=\"%s\"", URLEncoder.encode(fileName, CharsetUtil.charset(charset))));
        response.setContentType(contentType);
        write(response, in);
    }

    /**
     * 返回数据给客户端
     *
     * @param response 响应对象{@link HttpServletResponse}
     * @param in 需要返回客户端的内容
     * @param contentType 返回的类型
     */
    public static void write(HttpServletResponse response, InputStream in, String contentType) {
        response.setContentType(contentType);
        write(response, in);
    }

    /**
     * 返回数据给客户端
     *
     * @param response 响应对象{@link HttpServletResponse}
     * @param in 需要返回客户端的内容
     */
    public static void write(HttpServletResponse response, InputStream in) {
        write(response, in, IoUtil.DEFAULT_BUFFER_SIZE);
    }

    /**
     * 返回数据给客户端
     *
     * @param response 响应对象{@link HttpServletResponse}
     * @param in 需要返回客户端的内容
     * @param bufferSize 缓存大小
     */
    public static void write(HttpServletResponse response, InputStream in, int bufferSize) {
        ServletOutputStream out = null;
        try {
            byte[] bys = new byte[128];
            int len = 0;
            while ((len = in.read(bys)) != -1) {
                out.write(bys, 0, len);
            }
        } catch (IOException e) {
            throw new IORuntimeException(e);
        } finally {
            IoUtil.close(out);
            IoUtil.close(in);
        }
    }

    /**
     * 设置响应的Header
     *
     * @param response 响应对象{@link HttpServletResponse}
     * @param name 名
     * @param value 值，可以是String，Date， int
     */
    public static void setHeader(HttpServletResponse response, String name, Object value) {
        if (value instanceof String) {
            response.setHeader(name, (String) value);
        } else if (Date.class.isAssignableFrom(value.getClass())) {
            response.setDateHeader(name, ((Date) value).getTime());
        } else if (value instanceof Integer || "int".equalsIgnoreCase(value.getClass().getSimpleName())) {
            response.setIntHeader(name, (int) value);
        } else {
            response.setHeader(name, value.toString());
        }
    }

    // --------------------------------------------------------- Response end
}
