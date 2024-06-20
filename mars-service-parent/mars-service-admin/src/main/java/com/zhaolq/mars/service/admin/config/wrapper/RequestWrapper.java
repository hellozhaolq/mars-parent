package com.zhaolq.mars.service.admin.config.wrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.util.StreamUtils;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用装饰器HttpServletRequestWrapper解决流只能读取一次的问题: https://blog.csdn.net/qq_43437874/article/details/122102362
 *
 * @Author zhaolq
 * @Date 2024/6/14 14:38:49
 */
@Slf4j
public class RequestWrapper extends HttpServletRequestWrapper {
    // 缓存流，存储body数据的容器
    private byte[] body;

    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 初始化缓存
        if (body == null) {
            body = StreamUtils.copyToByteArray(super.getInputStream());
        }

        // 从缓存中返回流
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
