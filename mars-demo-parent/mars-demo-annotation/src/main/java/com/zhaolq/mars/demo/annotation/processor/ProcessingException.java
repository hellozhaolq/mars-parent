package com.zhaolq.mars.demo.annotation.processor;

import com.zhaolq.mars.demo.annotation.annotation.Factory;
import javax.lang.model.element.Element;

/**
 * {@link Factory}注解处理器处理异常
 *
 * @Author zhaolq
 * @Date 2020/7/10 10:53
 */
public class ProcessingException extends Exception {

    Element element;

    public ProcessingException(Element element, String msg, Object... args) {
        super(String.format(msg, args));
        this.element = element;
    }

    public Element getElement() {
        return element;
    }
}