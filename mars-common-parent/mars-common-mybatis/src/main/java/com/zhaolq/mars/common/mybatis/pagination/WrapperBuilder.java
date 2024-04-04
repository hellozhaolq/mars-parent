package com.zhaolq.mars.common.mybatis.pagination;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;

/**
 * 创建各种 Wrapper 对象
 *
 * https://baomidou.com/guide/wrapper.html
 *
 * @Author zhaolq
 * @Date 2021/6/1 17:07
 */
public class WrapperBuilder {

    /* QueryWrapper */

    public static <T> QueryWrapper<T> getQueryWrapper() {
        return new QueryWrapper<>();
    }

    public static <T> QueryWrapper<T> getQueryWrapper(T entity) {
        return new QueryWrapper<>(entity);
    }

    public static <T> QueryWrapper<T> getQueryWrapper(T entity, String... columns) {
        return new QueryWrapper<>(entity, columns);
    }

    /* UpdateWrapper */

    public static <T> UpdateWrapper<T> getUpdateWrapper() {
        return new UpdateWrapper<>();
    }

    public static <T> UpdateWrapper<T> getUpdateWrapper(T entity) {
        return new UpdateWrapper<>(entity);
    }

    /* LambdaQueryWrapper */

    public static <T> LambdaQueryWrapper<T> getLambdaQueryWrapper() {
        return Wrappers.lambdaQuery();
    }

    public static <T> LambdaQueryWrapper<T> getLambdaQueryWrapper(T entity) {
        return Wrappers.lambdaQuery(entity);
    }

    public static <T> LambdaQueryWrapper<T> getLambdaQueryWrapper(Class<T> entityClass) {
        return Wrappers.lambdaQuery(entityClass);
    }

    /* LambdaUpdateWrapper */

    public static <T> LambdaUpdateWrapper<T> getLambdaUpdateWrapper() {
        return Wrappers.lambdaUpdate();
    }

    public static <T> LambdaUpdateWrapper<T> getLambdaUpdateWrapper(T entity) {
        return Wrappers.lambdaUpdate(entity);
    }

    public static <T> LambdaUpdateWrapper<T> getLambdaUpdateWrapper(Class<T> entityClass) {
        return Wrappers.lambdaUpdate(entityClass);
    }

    /* QueryChainWrapper */

    public static <T> QueryChainWrapper<T> getQueryChainWrapper(BaseMapper<T> baseMapper) {
        return new QueryChainWrapper<>(baseMapper);
    }

    /* UpdateChainWrapper */

    public static <T> UpdateChainWrapper<T> getUpdateChainWrapper(BaseMapper<T> baseMapper) {
        return new UpdateChainWrapper<>(baseMapper);
    }

    /* LambdaQueryChainWrapper */

    public static <T> LambdaQueryChainWrapper<T> getLambdaQueryChainWrapper(BaseMapper<T> baseMapper) {
        return new LambdaQueryChainWrapper<>(baseMapper);
    }

    /* LambdaUpdateChainWrapper */

    public static <T> LambdaUpdateChainWrapper<T> getLambdaUpdateChainWrapper(BaseMapper<T> baseMapper) {
        return new LambdaUpdateChainWrapper<>(baseMapper);
    }

}
