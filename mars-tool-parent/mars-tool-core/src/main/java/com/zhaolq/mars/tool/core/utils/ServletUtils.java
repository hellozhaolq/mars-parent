// package com.zhaolq.;
//
// import com.sun.xml.internal.ws.util.UtilException;
//
// import org.apache.commons.lang3.StringUtils;
// import org.apache.tomcat.util.buf.CharsetUtil;
//
// import java.io.BufferedInputStream;
// import java.io.BufferedReader;
// import java.io.ByteArrayOutputStream;
// import java.io.Closeable;
// import java.io.DataInputStream;
// import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileNotFoundException;
// import java.io.IOException;
// import java.io.InputStream;
// import java.io.PrintWriter;
// import java.io.Writer;
// import java.lang.reflect.Array;
// import java.net.URLConnection;
// import java.net.URLDecoder;
// import java.nio.CharBuffer;
// import java.nio.charset.Charset;
// import java.nio.charset.StandardCharsets;
// import java.nio.charset.UnsupportedCharsetException;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.util.Arrays;
// import java.util.Collections;
// import java.util.Date;
// import java.util.Enumeration;
// import java.util.HashMap;
// import java.util.Locale;
// import java.util.Map;
// import ch.qos.logback.core.util.FileUtil;
// import javax.servlet.ServletOutputStream;
// import javax.servlet.ServletRequest;
// import javax.servlet.http.Cookie;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
//
// /**
//  * Servlet相关工具类封装
//  *
//  * @author zWX1085453
//  * @since 2021/11/2 9:24
//  */
// public class ServletUtils {
//     private static final String METHOD_DELETE = "DELETE";
//     private static final String METHOD_HEAD = "HEAD";
//     private static final String METHOD_GET = "GET";
//     private static final String METHOD_OPTIONS = "OPTIONS";
//     private static final String METHOD_POST = "POST";
//     private static final String METHOD_PUT = "PUT";
//     private static final String METHOD_TRACE = "TRACE";
//
//     /**
//      * 字符串常量：逗号 {@code ","}
//      */
//     private static final String COMMA = ",";
//
//     /**
//      * 默认缓存大小 8192 (8K)
//      */
//     private static final int DEFAULT_BUFFER_SIZE = 2 << 12;
//
//     /**
//      * 默认中等缓存大小 16384
//      */
//     private static final int DEFAULT_MIDDLE_BUFFER_SIZE = 2 << 13;
//
//     /**
//      * 默认大缓存大小 32768
//      */
//     private static final int DEFAULT_LARGE_BUFFER_SIZE = 2 << 14;
//
//     /**
//      * 数据流末尾
//      */
//     private static final int EOF = -1;
//
//     // --------------------------------------------------------- getParam start
//
//     /**
//      * 获得所有请求参数
//      *
//      * @param request 请求对象{@link ServletRequest}
//      * @return Map
//      */
//     public static Map<String, String[]> getParams(ServletRequest request) {
//         final Map<String, String[]> map = request.getParameterMap();
//         return Collections.unmodifiableMap(map);
//     }
//
//     /**
//      * 获得所有请求参数
//      *
//      * @param request 请求对象{@link ServletRequest}
//      * @return Map
//      */
//     public static Map<String, String> getParamMap(ServletRequest request) {
//         Map<String, String> params = new HashMap<>();
//         for (Map.Entry<String, String[]> entry : getParams(request).entrySet()) {
//             params.put(entry.getKey(), Arrays.deepToString(entry.getValue()));
//         }
//         return params;
//     }
//
//     /**
//      * 获取请求体<br>
//      * 调用该方法后，getParam方法将失效
//      *
//      * @param request {@link ServletRequest}
//      * @return 获得请求体
//      */
//     public static String getBody(ServletRequest request) {
//         try (final BufferedReader reader = request.getReader()) {
//             final StringBuilder builder = new StringBuilder();
//             final CharBuffer buffer = CharBuffer.allocate(DEFAULT_BUFFER_SIZE);
//             while (EOF != reader.read(buffer)) {
//                 builder.append(buffer.flip());
//             }
//             return URLDecoder.decode(builder.toString(), "UTF-8");
//         } catch (IOException e) {
//             throw new IORuntimeException(e);
//         }
//     }
//
//     /**
//      * 获取请求体byte[]<br>
//      * 调用该方法后，getParam方法将失效
//      *
//      * @param request {@link ServletRequest}
//      * @return 获得请求体byte[]
//      */
//     public static byte[] getBodyBytes(ServletRequest request) {
//         try (
//                 final DataInputStream inputStream = new DataInputStream(request.getInputStream()); // 获取请求内容，转成数据操作流
//                 final ByteArrayOutputStream outputStream = new ByteArrayOutputStream() // 内存操作流
//         ) {
//             byte[] bys = new byte[DEFAULT_BUFFER_SIZE];
//             int len = 0;
//             while ((len = inputStream.read(bys)) != -1) {
//                 outputStream.write(bys, 0, len);
//             }
//             byte[] res = outputStream.toByteArray();
//             if (res == null) {
//                 return new byte[0];
//             }
//             return URLDecoder.decode(new String(res, StandardCharsets.UTF_8), StandardCharsets.UTF_8.toString()).getBytes(StandardCharsets.UTF_8);
//         } catch (IOException e) {
//             throw new IORuntimeException(e);
//         }
//     }
//
//     // --------------------------------------------------------- getParam end
//
//     /**
//      * 获取客户端IP
//      *
//      * <p>
//      * 默认检测的Header:
//      *
//      * <pre>
//      * 1、X-Forwarded-For
//      * 2、X-Real-IP
//      * 3、Proxy-Client-IP
//      * 4、WL-Proxy-Client-IP
//      * </pre>
//      *
//      * <p>
//      * otherHeaderNames参数用于自定义检测的Header<br>
//      * 需要注意的是，使用此方法获取的客户IP地址必须在Http服务器（例如Nginx）中配置头信息，否则容易造成IP伪造。
//      * </p>
//      *
//      * @param request 请求对象{@link HttpServletRequest}
//      * @param otherHeaderNames 其他自定义头文件，通常在Http服务器（例如Nginx）中配置
//      * @return IP地址
//      */
//     public static String getClientIP(HttpServletRequest request, String... otherHeaderNames) {
//         String[] headers = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP",
//                 "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
//         if ((otherHeaderNames != null && otherHeaderNames.length != 0)) {
//             headers = addAll(headers, otherHeaderNames);
//         }
//         return getClientIPByHeader(request, headers);
//     }
//
//     /**
//      * 获取客户端IP
//      *
//      * <p>
//      * headerNames参数用于自定义检测的Header<br>
//      * 需要注意的是，使用此方法获取的客户IP地址必须在Http服务器（例如Nginx）中配置头信息，否则容易造成IP伪造。
//      * </p>
//      *
//      * @param request 请求对象{@link HttpServletRequest}
//      * @param headerNames 自定义头，通常在Http服务器（例如Nginx）中配置
//      * @return IP地址
//      */
//     private static String getClientIPByHeader(HttpServletRequest request, String... headerNames) {
//         String ip;
//         for (String header : headerNames) {
//             ip = request.getHeader(header);
//             if (isUnknown(ip) == false) {
//                 return getMultistageReverseProxyIp(ip);
//             }
//         }
//
//         ip = request.getRemoteAddr();
//         return getMultistageReverseProxyIp(ip);
//     }
//
//     /**
//      * 从多级反向代理中获得第一个非unknown IP地址
//      *
//      * @param ip 获得的IP地址
//      * @return 第一个非unknown IP地址
//      */
//     private static String getMultistageReverseProxyIp(String ip) {
//         // 多级反向代理检测
//         if (ip != null && ip.indexOf(",") > 0) {
//             final String[] ips = ip.trim().split(",");
//             for (String subIp : ips) {
//                 if (isUnknown(subIp) == false) {
//                     ip = subIp;
//                     break;
//                 }
//             }
//         }
//         return ip;
//     }
//
//     /**
//      * 检测给定字符串是否为未知，多用于检测HTTP请求相关<br>
//      *
//      * @param checkString 被检测的字符串
//      * @return 是否未知
//      */
//     private static boolean isUnknown(String checkString) {
//         return StringUtils.isBlank(checkString) || "unknown".equalsIgnoreCase(checkString);
//     }
//
//     /**
//      * 将多个数组合并在一起<br>
//      * 忽略null的数组
//      *
//      * @param <T> 数组元素类型
//      * @param arrays 数组集合
//      * @return 合并后的数组
//      */
//     private static <T> T[] addAll(T[]... arrays) {
//         if (arrays.length == 1) {
//             return arrays[0];
//         }
//
//         int length = 0;
//         for (T[] array : arrays) {
//             if (null != array) {
//                 length += array.length;
//             }
//         }
//         T[] result = newArray(arrays.getClass().getComponentType().getComponentType(), length);
//
//         length = 0;
//         for (T[] array : arrays) {
//             if (null != array) {
//                 System.arraycopy(array, 0, result, length, array.length);
//                 length += array.length;
//             }
//         }
//         return result;
//     }
//
//     /**
//      * 新建一个空数组
//      *
//      * @param <T> 数组元素类型
//      * @param componentType 元素类型
//      * @param newSize 大小
//      * @return 空数组
//      */
//     private static <T> T[] newArray(Class<?> componentType, int newSize) {
//         return (T[]) Array.newInstance(componentType, newSize);
//     }
//
//     // --------------------------------------------------------- Header start
//
//     /**
//      * 获取请求所有的头（header）信息
//      *
//      * @param request 请求对象{@link HttpServletRequest}
//      * @return header值
//      */
//     public static Map<String, String> getHeaderMap(HttpServletRequest request) {
//         final Map<String, String> headerMap = new HashMap<>();
//
//         final Enumeration<String> names = request.getHeaderNames();
//         String name;
//         while (names.hasMoreElements()) {
//             name = names.nextElement();
//             headerMap.put(name, request.getHeader(name));
//         }
//
//         return headerMap;
//     }
//
//     /**
//      * 忽略大小写获得请求header中的信息
//      *
//      * @param request 请求对象{@link HttpServletRequest}
//      * @param nameIgnoreCase 忽略大小写头信息的KEY
//      * @return header值
//      */
//     public static String getHeaderIgnoreCase(HttpServletRequest request, String nameIgnoreCase) {
//         final Enumeration<String> names = request.getHeaderNames();
//         String name;
//         while (names.hasMoreElements()) {
//             name = names.nextElement();
//             if (name != null && name.equalsIgnoreCase(nameIgnoreCase)) {
//                 return request.getHeader(name);
//             }
//         }
//
//         return null;
//     }
//
//     /**
//      * 获得请求header中的信息
//      *
//      * @param request 请求对象{@link HttpServletRequest}
//      * @param name 头信息的KEY
//      * @param charsetName 字符集
//      * @return header值
//      */
//     public static String getHeader(HttpServletRequest request, String name, String charsetName) {
//         return getHeader(request, name, charset(charsetName));
//     }
//
//     /**
//      * 获得请求header中的信息
//      *
//      * @param request 请求对象{@link HttpServletRequest}
//      * @param name 头信息的KEY
//      * @param charset 字符集
//      * @return header值
//      */
//     public static String getHeader(HttpServletRequest request, String name, Charset charset) {
//         final String header = request.getHeader(name);
//         if (null != header) {
//             return convert(header, StandardCharsets.ISO_8859_1, charset);
//         }
//         return null;
//     }
//
//     /**
//      * 转换为Charset对象
//      *
//      * @param charsetName 字符集，为空则返回默认字符集
//      * @return Charset
//      * @throws UnsupportedCharsetException 编码不支持
//      */
//     private static Charset charset(String charsetName) throws UnsupportedCharsetException {
//         return StringUtils.isBlank(charsetName) ? Charset.defaultCharset() : Charset.forName(charsetName);
//     }
//
//     /**
//      * 转换字符串的字符集编码
//      *
//      * @param source 字符串
//      * @param srcCharset 源字符集，默认ISO-8859-1
//      * @param destCharset 目标字符集，默认UTF-8
//      * @return 转换后的字符集
//      */
//     private static String convert(String source, String srcCharset, String destCharset) {
//         return convert(source, Charset.forName(srcCharset), Charset.forName(destCharset));
//     }
//
//     /**
//      * 转换字符串的字符集编码<br>
//      * 当以错误的编码读取为字符串时，打印字符串将出现乱码。<br>
//      * 此方法用于纠正因读取使用编码错误导致的乱码问题。<br>
//      * 例如，在Servlet请求中客户端用GBK编码了请求参数，我们使用UTF-8读取到的是乱码，此时，使用此方法即可还原原编码的内容
//      * <pre>
//      * 客户端 -》 GBK编码 -》 Servlet容器 -》 UTF-8解码 -》 乱码
//      * 乱码 -》 UTF-8编码 -》 GBK解码 -》 正确内容
//      * </pre>
//      *
//      * @param source 字符串
//      * @param srcCharset 源字符集，默认ISO-8859-1
//      * @param destCharset 目标字符集，默认UTF-8
//      * @return 转换后的字符集
//      */
//     private static String convert(String source, Charset srcCharset, Charset destCharset) {
//         if (null == srcCharset) {
//             srcCharset = StandardCharsets.ISO_8859_1;
//         }
//
//         if (null == destCharset) {
//             destCharset = StandardCharsets.UTF_8;
//         }
//
//         if (StringUtils.isBlank(source) || srcCharset.equals(destCharset)) {
//             return source;
//         }
//         return new String(source.getBytes(srcCharset), destCharset);
//     }
//
//     /**
//      * 客户浏览器是否为IE
//      *
//      * @param request 请求对象{@link HttpServletRequest}
//      * @return 客户浏览器是否为IE
//      */
//     public static boolean isIE(HttpServletRequest request) {
//         String userAgent = getHeaderIgnoreCase(request, "User-Agent");
//         if (StringUtils.isNotBlank(userAgent)) {
//             userAgent = userAgent.toUpperCase(Locale.getDefault());
//             return userAgent.contains("MSIE") || userAgent.contains("TRIDENT");
//         }
//         return false;
//     }
//
//     /**
//      * 是否为GET请求
//      *
//      * @param request 请求对象{@link HttpServletRequest}
//      * @return 是否为GET请求
//      */
//     public static boolean isGetMethod(HttpServletRequest request) {
//         return METHOD_GET.equalsIgnoreCase(request.getMethod());
//     }
//
//     /**
//      * 是否为POST请求
//      *
//      * @param request 请求对象{@link HttpServletRequest}
//      * @return 是否为POST请求
//      */
//     public static boolean isPostMethod(HttpServletRequest request) {
//         return METHOD_POST.equalsIgnoreCase(request.getMethod());
//     }
//
//     /**
//      * 是否为Multipart类型表单，此类型表单用于文件上传
//      *
//      * @param request 请求对象{@link HttpServletRequest}
//      * @return 是否为Multipart类型表单，此类型表单用于文件上传
//      */
//     public static boolean isMultipart(HttpServletRequest request) {
//         if (false == isPostMethod(request)) {
//             return false;
//         }
//
//         String contentType = request.getContentType();
//         if (StringUtils.isBlank(contentType)) {
//             return false;
//         }
//         return contentType.toLowerCase(Locale.getDefault()).startsWith("multipart/");
//     }
//
//     // --------------------------------------------------------- Header end
//
//     // --------------------------------------------------------- Cookie start
//
//     /**
//      * 获得指定的Cookie
//      *
//      * @param httpServletRequest {@link HttpServletRequest}
//      * @param name cookie名
//      * @return Cookie对象
//      */
//     public static Cookie getCookie(HttpServletRequest httpServletRequest, String name) {
//         return readCookieMap(httpServletRequest).get(name);
//     }
//
//     /**
//      * 将cookie封装到Map里面
//      *
//      * @param httpServletRequest {@link HttpServletRequest}
//      * @return Cookie map
//      */
//     public static Map<String, Cookie> readCookieMap(HttpServletRequest httpServletRequest) {
//         final Cookie[] cookies = httpServletRequest.getCookies();
//         if (cookies == null || cookies.length == 0) {
//             return Collections.emptyMap();
//         }
//
//         final Map<String, Cookie> cookieMap = new HashMap<>();
//         for (Cookie cookie : cookies) {
//             cookieMap.put(cookie.getName(), cookie);
//         }
//         return cookieMap;
//     }
//
//     /**
//      * 设定返回给客户端的Cookie
//      *
//      * @param response 响应对象{@link HttpServletResponse}
//      * @param cookie Servlet Cookie对象
//      */
//     public static void addCookie(HttpServletResponse response, Cookie cookie) {
//         response.addCookie(cookie);
//     }
//
//     /**
//      * 设定返回给客户端的Cookie
//      *
//      * @param response 响应对象{@link HttpServletResponse}
//      * @param name Cookie名
//      * @param value Cookie值
//      */
//     public static void addCookie(HttpServletResponse response, String name, String value) {
//         response.addCookie(new Cookie(name, value));
//     }
//
//     /**
//      * 设定返回给客户端的Cookie<br>
//      * Path: "/"<br>
//      * No Domain
//      *
//      * @param response 响应对象{@link HttpServletResponse}
//      * @param name cookie名
//      * @param value cookie值
//      * @param maxAgeInSeconds -1: 关闭浏览器清除Cookie. 0: 立即清除Cookie. &gt;0 : Cookie存在的秒数.
//      */
//     public static void addCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds) {
//         addCookie(response, name, value, maxAgeInSeconds, "/", null);
//     }
//
//     /**
//      * 设定返回给客户端的Cookie
//      *
//      * @param response 响应对象{@link HttpServletResponse}
//      * @param name cookie名
//      * @param value cookie值
//      * @param maxAgeInSeconds -1: 关闭浏览器清除Cookie. 0: 立即清除Cookie. &gt;0 : Cookie存在的秒数.
//      * @param path Cookie的有效路径
//      * @param domain the domain name within which this cookie is visible; form is according to RFC 2109
//      */
//     public static void addCookie(
//             HttpServletResponse response,
//             String name,
//             String value,
//             int maxAgeInSeconds,
//             String path,
//             String domain) {
//         Cookie cookie = new Cookie(name, value);
//         if (domain != null) {
//             cookie.setDomain(domain);
//         }
//         cookie.setMaxAge(maxAgeInSeconds);
//         cookie.setPath(path);
//         addCookie(response, cookie);
//     }
//
//     // --------------------------------------------------------- Cookie end
//
//     // --------------------------------------------------------- Response start
//
//     /**
//      * 获得PrintWriter
//      *
//      * @param response 响应对象{@link HttpServletResponse}
//      * @return 获得PrintWriter
//      * @throws IORuntimeException IO异常
//      */
//     public static PrintWriter getWriter(HttpServletResponse response) throws IORuntimeException {
//         try {
//             return response.getWriter();
//         } catch (IOException e) {
//             throw new IORuntimeException(e);
//         }
//     }
//
//     /**
//      * 返回数据给客户端
//      *
//      * @param response 响应对象{@link HttpServletResponse}
//      * @param text 返回的内容
//      * @param contentType 返回的类型
//      */
//     public static void write(HttpServletResponse response, String text, String contentType) {
//         response.setContentType(contentType);
//         try (final Writer writer = response.getWriter();) {
//             writer.write(text);
//             writer.flush();
//         } catch (IOException e) {
//             throw new UtilException(e);
//         }
//     }
//
//     /**
//      * 返回文件给客户端
//      *
//      * @param response 响应对象{@link HttpServletResponse}
//      * @param file 写出的文件对象
//      * @since 4.1.15
//      */
//     public static void write(HttpServletResponse response, File file) {
//         final String fileName = file.getName();
//         final String contentType = defaultIfNull(getMimeType(fileName), "application/octet-stream");
//         BufferedInputStream in = null;
//         try {
//             in = new BufferedInputStream(new FileInputStream(file));
//             write(response, in, contentType, fileName);
//         } catch (FileNotFoundException e) {
//             throw new IORuntimeException(e);
//         } finally {
//             if (in != null) {
//                 try {
//                     in.close();
//                 } catch (IOException e) {
//                     throw new IORuntimeException(e);
//                 }
//             }
//         }
//     }
//     /**
//      * 根据文件扩展名获得MimeType
//      *
//      * @param filePath 文件路径或文件名
//      * @return MimeType
//      * @since 4.1.15
//      */
//     private static String getMimeType(String filePath) {
//         String contentType = URLConnection.getFileNameMap().getContentTypeFor(filePath);
//         if (null == contentType) {
//             // 补充一些常用的mimeType
//             if (filePath.endsWith(".css")) {
//                 contentType = "text/css";
//             } else if (filePath.endsWith(".js")) {
//                 contentType = "application/x-javascript";
//             }
//         }
//
//         // 补充
//         if (null == contentType) {
//             contentType = getMimeType(Paths.get(filePath));
//         }
//
//         return contentType;
//     }
//
//     /**
//      * 获得文件的MimeType
//      *
//      * @param file 文件
//      * @return MimeType
//      * @see Files#probeContentType(Path)
//      * @since 5.5.5
//      */
//     private static String getMimeType(Path file) {
//         try {
//             return Files.probeContentType(file);
//         } catch (IOException e) {
//             throw new IORuntimeException(e);
//         }
//     }
//
//     /**
//      * 如果给定对象为{@code null}返回默认值
//      *
//      * <pre>
//      * ObjectUtil.defaultIfNull(null, null)      = null
//      * ObjectUtil.defaultIfNull(null, "")        = ""
//      * ObjectUtil.defaultIfNull(null, "zz")      = "zz"
//      * ObjectUtil.defaultIfNull("abc", *)        = "abc"
//      * ObjectUtil.defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
//      * </pre>
//      *
//      * @param <T> 对象类型
//      * @param object 被检查对象，可能为{@code null}
//      * @param defaultValue 被检查对象为{@code null}返回的默认值，可以为{@code null}
//      * @return 被检查对象为{@code null}返回默认值，否则返回原值
//      * @since 3.0.7
//      */
//     private static <T> T defaultIfNull(final T object, final T defaultValue) {
//         return (null != object) ? object : defaultValue;
//     }
//
//     /**
//      * 返回数据给客户端
//      *
//      * @param response 响应对象{@link HttpServletResponse}
//      * @param in 需要返回客户端的内容
//      * @param contentType 返回的类型
//      * 如：
//      * 1、application/pdf、
//      * 2、application/vnd.ms-excel、
//      * 3、application/msword、
//      * 4、application/vnd.ms-powerpoint
//      * docx、xlsx 这种 office 2007 格式 设置 MIME;网页里面docx 文件是没问题，但是下载下来了之后就变成doc格式了
//      * https://blog.csdn.net/cyh2260629/article/details/73824760
//      * 5、MIME_EXCELX_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
//      * 6、MIME_PPTX_TYPE = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
//      * 7、MIME_WORDX_TYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
//      * 8、MIME_STREAM_TYPE = "application/octet-stream;charset=utf-8"; #原始字节流
//      * @param fileName 文件名
//      * @since 4.1.15
//      */
//     public static void write(HttpServletResponse response, InputStream in, String contentType, String fileName) {
//         final String charset = defaultIfNull(response.getCharacterEncoding(), "UTF-8");
//         response.setHeader("Content-Disposition", String.format(Locale.getDefault(),"attachment;filename=%s",
//                 URLUtil.encode(fileName, CharsetUtil.charset(charset))));
//         response.setContentType(contentType);
//         write(response, in);
//     }
//
//     /**
//      * 返回数据给客户端
//      *
//      * @param response 响应对象{@link HttpServletResponse}
//      * @param in 需要返回客户端的内容
//      * @param contentType 返回的类型
//      */
//     public static void write(HttpServletResponse response, InputStream in, String contentType) {
//         response.setContentType(contentType);
//         write(response, in);
//     }
//
//     /**
//      * 返回数据给客户端
//      *
//      * @param response 响应对象{@link HttpServletResponse}
//      * @param in 需要返回客户端的内容
//      */
//     public static void write(HttpServletResponse response, InputStream in) {
//         write(response, in, IoUtil.DEFAULT_BUFFER_SIZE);
//     }
//
//     /**
//      * 返回数据给客户端
//      *
//      * @param response 响应对象{@link HttpServletResponse}
//      * @param in 需要返回客户端的内容
//      * @param bufferSize 缓存大小
//      */
//     public static void write(HttpServletResponse response, InputStream in, int bufferSize) {
//         ServletOutputStream out = null;
//         try {
//             out = response.getOutputStream();
//             IoUtil.copy(in, out, bufferSize);
//         } catch (IOException e) {
//             throw new UtilException(e);
//         } finally {
//             IoUtil.close(out);
//             IoUtil.close(in);
//         }
//     }
//
//     /**
//      * 设置响应的Header
//      *
//      * @param response 响应对象{@link HttpServletResponse}
//      * @param name 名
//      * @param value 值，可以是String，Date， int
//      */
//     public static void setHeader(HttpServletResponse response, String name, Object value) {
//         if (value instanceof String) {
//             response.setHeader(name, (String) value);
//         } else if (Date.class.isAssignableFrom(value.getClass())) {
//             response.setDateHeader(name, ((Date) value).getTime());
//         } else if (value instanceof Integer || "int".equalsIgnoreCase(value.getClass().getSimpleName())) {
//             response.setIntHeader(name, (int) value);
//         } else {
//             response.setHeader(name, value.toString());
//         }
//     }
//     // --------------------------------------------------------- Response end
//
// }
