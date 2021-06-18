package com.zhaolq.mars.common.log;

import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.Set;

/**
 * 更改日志级别处理单元
 *
 * https://tech.meituan.com/2017/02/17/change-log-level.html
 *
 * @author zhaolq
 * @date 2021/6/18 17:13
 */
@Component
public class ChangeLogLevelProcessUnit {

    @PostConstruct
    public void init() {

    }

    private final static void bind() {
        try {
            // 查找classpath下所有的StaticLoggerBinder类。
            Set<URL> staticLoggerBinderPathSet = findPossibleStaticLoggerBinderPathSet();
            reportMultipleBindingAmbiguity(staticLoggerBinderPathSet);
            // 每一个slf4j桥接包中都有一个org.slf4j.impl.StaticLoggerBinder类，该类实现了LoggerFactoryBinder接口。
            // the next line does the binding
            StaticLoggerBinder.getSingleton();
            INITIALIZATION_STATE = SUCCESSFUL_INITIALIZATION;
            reportActualBinding(staticLoggerBinderPathSet);
            fixSubstitutedLoggers();
        }
    }

    /**
     * 查找当前classpath下所有的org.slf4j.impl.StaticLoggerBinder类
     *
     * @param
     * @return java.util.Set<java.net.URL>
     */
    private Set<URL> findPossibleStaticLoggerBinderPathSet(){


        return null;
    }
}
