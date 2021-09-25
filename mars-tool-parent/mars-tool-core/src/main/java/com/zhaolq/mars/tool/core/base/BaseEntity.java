package com.zhaolq.mars.tool.core.base;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 实体基类
 *
 * @author zhaolq
 * @since 2021/6/1 14:04
 */
public abstract class BaseEntity {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static <T extends BaseEntity> Map<String, T> idEntityMap(Collection<T> list) {
        Map<String, T> map = new HashMap<>();
        if (list == null || list.size() == 0) {
            return map;
        }
        for (T entity : list) {
            map.put(entity.getId(), entity);

        }
        return map;
    }
}
