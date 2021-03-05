package com.zhaolq.mars.tool.core.result;

import com.zhaolq.mars.tool.core.result.IResultCode;
import org.springframework.lang.Nullable;

/**
 * HTTP状态码枚举
 *
 * 参考：
 *      {@code org.springframework.http.HttpStatus}
 *      {@code javax.servlet.http.HttpServletResponse}
 *      {@code cn.hutool.http.HttpStatus}
 * @author zhaolq
 * @date 2020/10/16 10:34
 */
public enum HttpStatus implements IResultCode {

    // 1xx Informational

    CONTINUE(100, "Continue", ""),
    SWITCHING_PROTOCOLS(101, "Switching Protocols", ""),
    PROCESSING(102, "Processing", ""),
    CHECKPOINT(103, "Checkpoint", ""),

    // 2xx Success

    OK(200, "OK", "请求成功"),
    CREATED(201, "Created", ""),
    ACCEPTED(202, "Accepted", ""),
    NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information", ""),
    NO_CONTENT(204, "No Content", "请求成功，无任何内容"),
    RESET_CONTENT(205, "Reset Content", ""),
    PARTIAL_CONTENT(206, "Partial Content", ""),
    MULTI_STATUS(207, "Multi-Status", ""),
    ALREADY_REPORTED(208, "Already Reported", ""),
    IM_USED(226, "IM Used", ""),

    // 3xx Redirection

    MULTIPLE_CHOICES(300, "Multiple Choices", ""),
    MOVED_PERMANENTLY(301, "Moved Permanently", ""),
    FOUND(302, "Found", ""),
    SEE_OTHER(303, "See Other", ""),
    NOT_MODIFIED(304, "Not Modified", ""),
    TEMPORARY_REDIRECT(307, "Temporary Redirect", ""),
    PERMANENT_REDIRECT(308, "Permanent Redirect", ""),

    // 4xx Client Error

    BAD_REQUEST(400, "Bad Request", "客户端错误"),
    UNAUTHORIZED(401, "Unauthorized", "未经授权"),
    PAYMENT_REQUIRED(402, "Payment Required", ""),
    FORBIDDEN(403, "Forbidden", "拒绝请求"),
    NOT_FOUND(404, "Not Found", "资源不存在"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed", "不支持的请求方法"),
    NOT_ACCEPTABLE(406, "Not Acceptable", ""),
    PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required", ""),
    REQUEST_TIMEOUT(408, "Request Timeout", ""),
    CONFLICT(409, "Conflict", ""),
    GONE(410, "Gone", ""),
    LENGTH_REQUIRED(411, "Length Required", ""),
    PRECONDITION_FAILED(412, "Precondition Failed", ""),
    PAYLOAD_TOO_LARGE(413, "Payload Too Large", ""),
    URI_TOO_LONG(414, "URI Too Long", ""),
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type", "不支持的媒体类型"),
    REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested range not satisfiable", ""),
    EXPECTATION_FAILED(417, "Expectation Failed", ""),
    I_AM_A_TEAPOT(418, "I'm a teapot", ""),
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity", ""),
    LOCKED(423, "Locked", ""),
    FAILED_DEPENDENCY(424, "Failed Dependency", ""),
    TOO_EARLY(425, "Too Early", ""),
    UPGRADE_REQUIRED(426, "Upgrade Required", ""),
    PRECONDITION_REQUIRED(428, "Precondition Required", ""),
    TOO_MANY_REQUESTS(429, "Too Many Requests", ""),
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Request Header Fields Too Large", ""),
    UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons", ""),

    // 5xx Server Error

    INTERNAL_SERVER_ERROR(500, "Internal Server Error", "内部服务器错误"),
    NOT_IMPLEMENTED(501, "Not Implemented", ""),
    BAD_GATEWAY(502, "Bad Gateway", ""),
    SERVICE_UNAVAILABLE(503, "Service Unavailable", ""),
    GATEWAY_TIMEOUT(504, "Gateway Timeout", ""),
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version not supported", ""),
    VARIANT_ALSO_NEGOTIATES(506, "Variant Also Negotiates", ""),
    INSUFFICIENT_STORAGE(507, "Insufficient Storage", ""),
    LOOP_DETECTED(508, "Loop Detected", ""),
    BANDWIDTH_LIMIT_EXCEEDED(509, "Bandwidth Limit Exceeded", ""),
    NOT_EXTENDED(510, "Not Extended", ""),
    NETWORK_AUTHENTICATION_REQUIRED(511, "Network Authentication Required", ""),

    ;

    /**
     * 状态码
     */
    private final int code;
    /**
     * 英文信息描述
     */
    private final String descEn;
    /**
     * 中文信息描述
     */
    private final String descCh;


    HttpStatus(int code, String descEn, String descCh) {
        this.code = code;
        this.descEn = descEn;
        this.descCh = descCh;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDescEn() {
        return descEn;
    }

    @Override
    public String getDescCh() {
        return descCh;
    }

    @Override
    public Boolean isSuccess() {
        return this.code == HttpStatus.OK.getCode();
    }

    /**
     * 根据状态码返回 {@link HttpStatus 类型的}枚举常量。
     * @param statusCode 状态码
     * @return 指定状态码的枚举常量
     * @throws IllegalArgumentException 若没有找到与状态码匹配的枚举常量，将抛出异常。
     */
    public static HttpStatus valueOf(int statusCode) {
        HttpStatus status = resolve(statusCode);
        if (status == null) {
            throw new IllegalArgumentException("没有匹配的状态码 [" + statusCode + "]");
        }
        return status;
    }

    /**
     * 解析状态码为{@link HttpStatus}。
     * @param statusCode HTTP状态代码（可能是非标准的）
     * @return 相应的{@link HttpStatus}，如果找不到，则为{@code null}
     */
    @Nullable
    public static HttpStatus resolve(int statusCode) {
        for (HttpStatus status : values()) {
            if (status.code == statusCode) {
                return status;
            }
        }
        return null;
    }

    /**
     * 返回此状态代码的HTTP状态系列。
     * @see Series
     */
    public Series series() {
        return Series.valueOf(this);
    }

    /**
     * 此状态代码是否在HTTP系列中
     */
    public boolean is1xxInformational() {
        return (series() == Series.INFORMATIONAL);
    }

    /**
     * 此状态代码是否在HTTP系列中
     */
    public boolean is2xxSuccessful() {
        return (series() == Series.SUCCESSFUL);
    }

    /**
     * 此状态代码是否在HTTP系列中
     */
    public boolean is3xxRedirection() {
        return (series() == Series.REDIRECTION);
    }

    /**
     * 此状态代码是否在HTTP系列中
     */
    public boolean is4xxClientError() {
        return (series() == Series.CLIENT_ERROR);
    }

    /**
     * 此状态代码是否在HTTP系列中
     */
    public boolean is5xxServerError() {
        return (series() == Series.SERVER_ERROR);
    }

    /**
     * 此状态代码是否在HTTP系列中
     */
    public boolean isError() {
        return (is4xxClientError() || is5xxServerError());
    }

    /**
     * 返回此状态代码的字符串表示形式。
     */
    @Override
    public String toString() {
        return this.code + " " + name();
    }

    /**
     * HTTP状态系列的枚举。通过 {@link HttpStatus#series()} 检索.
     */
    public enum Series {

        /**
         * 信息，服务器收到请求，需要请求者继续执行操作
         */
        INFORMATIONAL(1),
        /**
         * 成功，操作被成功接收并处理
         */
        SUCCESSFUL(2),
        /**
         * 重定向，需要进一步的操作以完成请求
         */
        REDIRECTION(3),
        /**
         * 客户端错误，请求包含语法错误或无法完成请求
         */
        CLIENT_ERROR(4),
        /**
         * 服务器错误，服务器在处理请求的过程中发生了错误
         */
        SERVER_ERROR(5);

        private final int code;

        Series(int code) {
            this.code = code;
        }

        /**
         * 返回此状态系列的整数值。范围从1到5。
         */
        public int code() {
            return this.code;
        }

        /**
         * 返回带有相应系列的{@link Series 类型的}枚举常量。
         * @param status 标准的HTTP状态枚举值
         * @return 此类型的枚举常量及其对应的系列
         * @throws IllegalArgumentException 如果此枚举没有相应的常量
         */
        public static Series valueOf(HttpStatus status) {
            return valueOf(status.code);
        }

        /**
         * 返回带有相应系列的{@link Series 类型的}枚举常量。
         * @param statusCode HTTP状态代码（可能是非标准的）
         * @return 此类型的枚举常量及其对应的系列
         * @throws IllegalArgumentException 如果此枚举没有相应的常量
         */
        public static Series valueOf(int statusCode) {
            Series series = resolve(statusCode);
            if (series == null) {
                throw new IllegalArgumentException("没有匹配的状态码 [" + statusCode + "]");
            }
            return series;
        }

        /**
         * 如果可能，将给定的状态代码解析为{@link Series}。
         * @param statusCode HTTP状态代码（可能是非标准的）
         * @return 相应的{@code系列}，如果找不到，则为{@code null}
         */
        @Nullable
        public static Series resolve(int statusCode) {
            int seriesCode = statusCode / 100;
            for (Series series : values()) {
                if (series.code == seriesCode) {
                    return series;
                }
            }
            return null;
        }
    }

}
