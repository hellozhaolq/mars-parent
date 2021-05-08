package com.zhaolq.mars.service.sys.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
 * @date 2020/10/20 21:12
 */
@Slf4j
@SpringBootTest
public class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void selectById() {
        UserEntity userEntity = userMapper.selectById(1);
        log.debug(userEntity.toString());
        // 断言
        Assertions.assertEquals("1", userEntity.getId());
        assert "1".equals(userEntity.getId());
        assert Integer.valueOf(userEntity.getId()) == 1;
    }

    @Test
    public void selectBatchIds() {
        List<String> idList = Arrays.asList("1", "2", "3");
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

    /***************************** 使用QueryWrapper查询 *****************************/

    /**
     * selectMaps：返回List<Map<String, Object>>
     * 应用场景：
     *  1、当表字段很多且只需要查询几列时，没必要返回泛型为实体的list。若泛型为实体类则大部分字段都是null，这样做不优雅
     *  2、返回统计结果时常用
     */
    @Test
    public void selectMaps() {
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
    public void selectObjs() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("NAME", "AGE").like("NICK_NAME", "超").lt("AGE", 30);
        List<Object> userList = userMapper.selectObjs(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * select count(1) from tableName
     */
    @Test
    public void selectCount() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("AGE", 30);
        int count = userMapper.selectCount(queryWrapper);
        System.out.println(count);
    }

    /**
     * selectOne
     */
    @Test
    public void selectOne() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("NAME", "超管").lt("AGE", 30);
        UserEntity userEntity = userMapper.selectOne(queryWrapper);
        System.out.println(userEntity);
    }

    /**
     * 昵称中包含超，并且年龄小于30
     *  where nick_name like '%超%' and age < 30
     */
    @Test
    public void selectList1() {
        QueryWrapper<UserEntity> query = Wrappers.<UserEntity>query();
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("NICK_NAME", "超").lt("AGE", 30);
        List<UserEntity> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 昵称中包含超，并且年龄大于等于20且小于等于30，并且邮箱不为空
     *  where nick_name like '%超%' and age between 20 and 30 and email is not null
     */
    @Test
    public void selectList2() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("NICK_NAME", "超").between("AGE", 20, 30).isNotNull("EMAIL");
        List<UserEntity> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 名字为赵姓或者年龄大于等于40，按照年龄降序排序，年龄相同按照id升序
     *  where name like '赵%' or age >= 40 order by age desc, id asc
     */
    @Test
    public void selectList3() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("NAME", "赵").or().ge("AGE", "40").orderByDesc("AGE").orderByAsc("ID");
        List<UserEntity> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 创建日期为2020年1月1日并且机构名称为蜀开头
     *  where to_char(create_time, 'yyyy-mm-dd hh24:mi:ss') = '2020-01-01 10:00:00'
     *    and dept_id in (select id from tab_earth_dept where name like '蜀%')
     */
    @Test
    public void selectList4() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        // 存在sql注入的风险，例如：日期参数为 "'2020-01-01 10:00:00' or 1=1"，sql语句中并没有使用占位符。
        // queryWrapper.apply("to_char(create_time, 'yyyy-mm-dd hh24:mi:ss') = " + "'2020-01-01 10:00:00' or 1=1").inSql("DEPT_ID", "select id from tab_earth_dept where name like '蜀%'");
        queryWrapper.apply("to_char(create_time, 'yyyy-mm-dd hh24:mi:ss') = {0}", "2020-01-01 10:00:00").inSql("DEPT_ID", "select id from mars_sys_dept where name like '蜀%'");
        List<UserEntity> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 名字为赵姓并且 (年龄小于40或邮箱不为空)
     *  where name like '赵%' and (age < 40 or email is not null)
     */
    @Test
    public void selectList5() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("NAME", "赵").and(qw -> qw.lt("AGE", "40").or().isNotNull("EMAIL"));
        List<UserEntity> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

}
