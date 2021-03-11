package com.zhaolq.mars.service.sys.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhaolq.mars.service.sys.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * MyBatis分页：使用RowBounds实现逻辑分页，或叫内存分页。
 *  原理：查出所有符合条件的数据到内存，然后返回某页数据。如果符合条件的数据多达上百万，后果会很严重
 *  两个严重问题：
 *      1、消耗内存
 *      2、查询数速度慢
 *
 * mp分页插件：实现物理分页
 *
 * @author zhaolq
 * @date 2020/10/21 22:05
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperPage {

    @Resource
    private UserMapper userMapper;

    /**
     * selectPage
     */
    @Test
    public void selectPage() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("AGE", "26");
        IPage<UserEntity> page = new Page<>(1, 2);
        IPage<UserEntity> iPage = userMapper.selectPage(page, queryWrapper);

        System.out.println("总记录数：" + iPage.getTotal());
        System.out.println("总页数：" + iPage.getPages());
        System.out.println("当前页：" + iPage.getCurrent());
        System.out.println("每页大小：" + iPage.getSize());
        List<UserEntity> userList = iPage.getRecords();
        userList.forEach(System.out::println);
    }

    /**
     * selectMapsPage
     */
    @Test
    public void selectMapsPage() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("AGE", "26");
        IPage<Map<String, Object>> page = new Page<>(1, 2);
        IPage<Map<String, Object>> iPage = userMapper.selectMapsPage(page, queryWrapper);

        System.out.println("总记录数：" + iPage.getTotal());
        System.out.println("总页数：" + iPage.getPages());
        System.out.println("当前页：" + iPage.getCurrent());
        System.out.println("每页大小：" + iPage.getSize());
        List<Map<String, Object>> userList = iPage.getRecords();
        userList.forEach(System.out::println);
    }

    /**
     * 不进行 count 查询
     * 对应场景：app上拉加载时不需要查询总记录数
     */
    @Test
    public void selectIgnoreTotal() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("AGE", "26");
        IPage<Map<String, Object>> page = new Page<>(1, 2, false);
        IPage<Map<String, Object>> iPage = userMapper.selectMapsPage(page, queryWrapper);

        System.out.println("总记录数：" + iPage.getTotal());
        System.out.println("总页数：" + iPage.getPages());
        System.out.println("当前页：" + iPage.getCurrent());
        System.out.println("每页大小：" + iPage.getSize());
        List<Map<String, Object>> userList = iPage.getRecords();
        userList.forEach(System.out::println);
    }


}
