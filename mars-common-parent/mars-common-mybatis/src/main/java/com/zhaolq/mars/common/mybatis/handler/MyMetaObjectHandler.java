package com.zhaolq.mars.common.mybatis.handler;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author zhaolq
 * @Date 2020/10/18 15:23
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = this.getFieldValByName("createTime", metaObject);
        if (createTime == null) {
            LocalDateTime localDateTime = LocalDateTime.now();
            this.setFieldValByName("createTime", localDateTime, metaObject);
            this.setFieldValByName("lastUpdateTime", localDateTime, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object lastUpdateTime = this.getFieldValByName("lastUpdateTime", metaObject);
        if (lastUpdateTime == null) {
            this.setFieldValByName("lastUpdateTime", LocalDateTime.now(), metaObject);
        }
    }
}
