package com.zhaolq.mars.common.spring.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 获取bean
 * <p>
 * 单例模式
 *
 * @Author zhaolq
 * @Date 2021/6/17 15:45
 */
@Slf4j
@Component
public class SpringContext implements ApplicationContextAware {

    private volatile static SpringContext springContext;
    private ApplicationContext applicationContext;

    private SpringContext() {
    }

    /**
     * 登记式/静态内部类 --- 推荐使用
     * <p>
     * 是否 Lazy 初始化：是
     * 是否多线程安全：是
     * 实现难度：一般
     * 描述：这种方式能达到双检锁方式一样的功效，但实现更简单。对静态域使用延迟初始化，应使用这种方式而不是双检锁方式。
     * 这种方式只适用于静态域的情况，双检锁方式可在实例域需要延迟初始化时使用。
     */
    public static SpringContext getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 双检锁/双重校验锁（DCL，即 double-checked locking）
     * <p>
     * 是否 Lazy 初始化：是
     * 是否多线程安全：是
     * 实现难度：较复杂
     * 描述：这种方式采用双锁机制，安全且在多线程情况下能保持高性能。
     * getInstance() 的性能对应用程序很关键。
     */
    protected static SpringContext getSingleton() {
        if (springContext == null) {
            synchronized (SpringContext.class) {
                if (springContext == null) {
                    springContext = new SpringContext();
                }
            }
        }
        return springContext;
    }

    public <T> T getBean(String beanName) {
        AtomicReference<T> bean = new AtomicReference<>();
        if (applicationContext == null) {
            log.error("applicationContext is null");
            return bean.get();
        }

        try {
            bean.set((T) applicationContext.getBean(beanName));
        } catch (NoSuchBeanDefinitionException e) {
            log.error(e.getMessage(), e);
        } catch (BeansException e) {
            log.error(e.getMessage(), e);
        }
        return bean.get();
    }

    public <T> T getBean(String beanName, Class<T> requiredType) {
        AtomicReference<T> bean = new AtomicReference<>();
        if (applicationContext == null) {
            log.error("applicationContext is null");
            return bean.get();
        }

        try {
            bean.set(applicationContext.getBean(beanName, requiredType));
        } catch (NoSuchBeanDefinitionException e) {
            log.error(e.getMessage(), e);
        } catch (BeanNotOfRequiredTypeException e) {
            log.error(e.getMessage(), e);
        } catch (BeansException e) {
            log.error(e.getMessage(), e);
        }
        return bean.get();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private static class SingletonHolder {
        private static final SpringContext INSTANCE = new SpringContext();
    }

}
