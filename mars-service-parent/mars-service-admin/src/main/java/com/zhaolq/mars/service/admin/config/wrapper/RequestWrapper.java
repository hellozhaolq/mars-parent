package com.zhaolq.mars.service.admin.config.wrapper;

import com.zhaolq.mars.common.core.exception.BaseRuntimeException;
import com.zhaolq.mars.common.core.result.ErrorEnum;
import com.zhaolq.mars.common.core.util.ServletUtil;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * request.getInputStream()输入流只能读取一次问题: https://blog.csdn.net/qq_16159433/article/details/120922952
 * 使用HTTP提供的装饰器 HttpServletRequestWrapper 来包装原生 SerlvetRequest 对象
 *
 * @Author zhaolq
 * @Date 2024/6/14 14:38:49
 */
@Slf4j
public class RequestWrapper extends HttpServletRequestWrapper {
    // 存储body数据的容器
    private final byte[] body;

    public RequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            // 将body数据存起来，也是使用 CharsetUtil.defaultCharset()
            body = ServletUtil.getBody(request).getBytes(StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new BaseRuntimeException(e, ErrorEnum.FAILURE);
        }
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 每次都从这个容器中读数据。这样request的输入流就可以重复读了。
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }
        };
    }
}
