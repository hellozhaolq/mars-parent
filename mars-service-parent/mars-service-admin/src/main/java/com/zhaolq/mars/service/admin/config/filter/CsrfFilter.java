package com.zhaolq.mars.service.admin.config.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 跨站请求伪造过滤器
 *
 * @Author zhaolq
 * @Date 2023/8/16 14:08:29
 */
@Slf4j
@Order(100)
@Component
public class CsrfFilter {
}
