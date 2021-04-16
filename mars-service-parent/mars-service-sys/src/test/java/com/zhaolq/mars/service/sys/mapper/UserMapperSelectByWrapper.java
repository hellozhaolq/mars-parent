package com.zhaolq.mars.service.sys.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhaolq.mars.service.sys.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author zhaolq
 * @date 2020/10/21 21:55
 */
@Slf4j
@SpringBootTest
public class UserMapperSelectByWrapper {

    @Resource
    private UserMapper userMapper;

    /**
     * 昵称中包含超，并且年龄小于30
     *  where nick_name like '%超%' and age < 30
     */
    @Test
    public void selectByWrapper1() {
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
    public void selectByWrapper2() {
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
    public void selectByWrapper3() {
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
    public void selectByWrapper4() {
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
    public void selectByWrapper5() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("NAME", "赵").and(qw -> qw.lt("AGE", "40").or().isNotNull("EMAIL"));
        List<UserEntity> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 名字为赵姓或者 (年龄小于40并且年龄大于20并且邮箱不为空)
     *   where name like '赵%' or (age < 30 and age > 25 and email is not null)
     */
    @Test
    public void selectByWrapper6() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("NAME", "赵").or(qw -> qw.lt("AGE", "30").gt("AGE", "25").isNotNull("EMAIL"));
        List<UserEntity> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * (年龄小于40或邮箱不为空) 并且名字为孙姓
     *  where (age < 40 or email is not null) and name like '孙%'
     */
    @Test
    public void selectByWrapper7() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.nested(qw -> qw.lt("AGE", "40").or().isNotNull("EMAIL")).likeRight("NAME", "孙");
        List<UserEntity> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 年龄为30、31、34、35
     *  where age in (30, 31, 34, 35)
     */
    @Test
    public void selectByWrapper8() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("AGE", Arrays.asList(30, 31, 34, 35));
        List<UserEntity> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 只返回满足条件的其中一条语句即可
     *  where age in (30, 31, 34, 35) and rownum = 1
     * last只能调用一次,多次调用以最后一次为准 有sql注入的风险,请谨慎使用。https://baomidou.com/guide/wrapper.html#last
     */
    @Test
    public void selectByWrapper9() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("AGE", Arrays.asList(30, 31, 34, 35)).last("and rownum = 1");
        List<UserEntity> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 不查询全部字段
     * 应用场景：排除大字段
     */
    @Test
    public void selectByWrapper10() {
        QueryWrapper<UserEntity> queryWrapperSuper1 = new QueryWrapper<>();
        queryWrapperSuper1.select("ID", "NAME", "NICK_NAME");
        queryWrapperSuper1.like("NICK_NAME", "超").lt("AGE", 30);
        List<UserEntity> userList1 = userMapper.selectList(queryWrapperSuper1);
        userList1.forEach(System.out::println);

        QueryWrapper<UserEntity> queryWrapperSuper2 = new QueryWrapper<>();
        queryWrapperSuper2.select(UserEntity.class, info -> !info.getColumn().equals("PASSWORD") && !info.getColumn().equals("SALT"));
        queryWrapperSuper2.like("NICK_NAME", "超").lt("AGE", 30);
        List<UserEntity> userList2 = userMapper.selectList(queryWrapperSuper2);
        userList2.forEach(System.out::println);
    }

    /**
     * condition作用：该条件是否加入最后生成的sql中
     * 应用场景：列表查询时，请求参数为空的字段不做为sql查询条件
     */
    @Test
    public void selectByWrapper11() {
        String name = "赵";
        String email = "";
        condition(name, email);
    }

    private void condition(String name, String email) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), "NAME", name).like(StringUtils.isNotBlank(email), "EMAIL", email);
        List<UserEntity> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * entity做为查询条件，默认不为null的属性将做为where条件，默认使用等值比较符
     * 与条件构造器不冲突
     */
    @Test
    public void selectByWrapperEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("赵");
        userEntity.setDeptId(BigDecimal.valueOf(13));

        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>(userEntity);
        // queryWrapper.like("NAME", "赵").lt("AGE", "30");

        List<UserEntity> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * AllEq
     */
    @Test
    public void selectByWrapperAllEq() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        Map<String, Object> params = new HashMap<>();
        params.put("NAME", "赵云");
        params.put("AGE", "28");
        params.put("NICK_NAME", null);
        // 参数为null时，不执行 isNull 方法
        queryWrapper.allEq(params, false);
        // 参数 key 为 NAME 才加入条件
        queryWrapper.allEq((k, v) -> k.equals("NAME"), params);

        List<UserEntity> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

}
