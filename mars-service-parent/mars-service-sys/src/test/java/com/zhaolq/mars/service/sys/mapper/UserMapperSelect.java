package com.zhaolq.mars.service.sys.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhaolq.mars.service.sys.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author zhaolq
 * @date 2020/10/21 21:58
 */
@Slf4j
@SpringBootTest
public class UserMapperSelect {

    @Resource
    private UserMapper userMapper;

    @Test
    public void selectById() {
        UserEntity userEntity = userMapper.selectById(1);
        System.out.println(userEntity);
        // 断言
        Assertions.assertEquals(1, userEntity.getId());
        assert Integer.valueOf(userEntity.getId()) == 1;
    }

    @Test
    public void selectBatchIds() {
        List<Long> idList = (List<Long>) Arrays.<Long>asList(1L, 2L, 3L);
        List<UserEntity> userList = userMapper.selectBatchIds(idList);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByMap() {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("ACCOUNT", "admin");
        columnMap.put("NAME", "超管");
        List<UserEntity> userList = userMapper.selectByMap(columnMap);
        userList.forEach(System.out::println);
    }

    /***************************** 其他查询方法 *****************************/
    /**
     * selectMaps：返回List<Map<String, Object>>
     * 应用场景：
     *  1、当表字段很多且只需要查询几列时，没必要返回泛型为实体的list。若泛型为实体类则大部分字段都是null，这样做不优雅
     *  2、返回统计结果时常用
     */
    @Test
    public void selectByWrapperMaps() {
        // 场景1
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("ID", "NAME", "AGE").like("NICK_NAME", "超").lt("AGE", 30);
        List<Map<String, Object>> userList = userMapper.selectMaps(queryWrapper);
        userList.forEach(System.out::println);

        // 场景2
        QueryWrapper<UserEntity> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.select("DEPT_ID", "avg(age)", "min(age)", "max(age)")
                .groupBy("DEPT_ID").having("avg(age) < {0}", 30);
        List<Map<String, Object>> userList2 = userMapper.selectMaps(queryWrapper2);
        userList2.forEach(System.out::println);
    }

    /**
     * selectObjs：注意，只返回第一个字段的值，返回List<Object>
     * 应用场景：只返回一列
     */
    @Test
    public void selectByWrapperObjs() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("NAME", "AGE").like("NICK_NAME", "超").lt("AGE", 30);
        List<Object> userList = userMapper.selectObjs(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * select count(1) from tableName
     */
    @Test
    public void selectByWrapperCount() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("AGE", 30);
        int count = userMapper.selectCount(queryWrapper);
        System.out.println(count);
    }

    /**
     * selectOne
     */
    @Test
    public void selectByWrapperOne() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("NAME", "超管").lt("AGE", 30);
        UserEntity userEntity = userMapper.selectOne(queryWrapper);
        System.out.println(userEntity);
    }
    /***************************** 其他查询方法 *****************************/

}
