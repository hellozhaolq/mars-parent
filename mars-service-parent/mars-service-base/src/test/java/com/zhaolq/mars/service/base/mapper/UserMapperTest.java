package com.zhaolq.mars.service.base.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.zhaolq.mars.api.sys.entity.RoleEntity;
import com.zhaolq.mars.api.sys.entity.UserEntity;
import com.zhaolq.mars.common.mybatis.pagination.PagePlus;
import com.zhaolq.mars.service.base.mapper.base.UserMapper;
import com.zhaolq.mars.tool.core.utils.ObjectUtils;
import com.zhaolq.mars.tool.core.utils.StringUtils;

import lombok.extern.slf4j.Slf4j;

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
        Long count = userMapper.selectCount(queryWrapper);
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
    public void selectList01() {
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
    public void selectList02() {
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
    public void selectList03() {
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
    public void selectList04() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        // 存在sql注入的风险，例如：日期参数为 "'2020-01-01 10:00:00' or 1=1"，sql语句中并没有使用占位符。
        // queryWrapper.apply("to_char(create_time, 'yyyy-mm-dd hh24:mi:ss') = " + "'2020-01-01 10:00:00' or 1=1").inSql("DEPT_ID", "select id from
        // tab_earth_dept where name like '蜀%'");
        queryWrapper.apply("to_char(create_time, 'yyyy-mm-dd hh24:mi:ss') = {0}", "2020-01-01 10:00:00").inSql("DEPT_ID", "select id from " +
                "mars_sys_dept where name like '蜀%'");
        List<UserEntity> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 名字为赵姓并且 (年龄小于40或邮箱不为空)
     *  where name like '赵%' and (age < 40 or email is not null)
     */
    @Test
    public void selectList05() {
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
    public void selectList06() {
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
    public void selectList07() {
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
    public void selectList08() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("AGE", Arrays.asList(50, 51, 52));
        List<UserEntity> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 只返回满足条件的其中一条语句即可
     *  where age in (30, 31, 34, 35) and rownum = 1
     * last只能调用一次,多次调用以最后一次为准 有sql注入的风险，请谨慎使用。https://baomidou.com/guide/wrapper.html#last
     */
    @Test
    public void selectList09() {
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
    public void selectList10() {
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
    public void selectList11() {
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
    public void selectListByEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("赵");
        userEntity.setDeptId("13");

        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>(userEntity);
        // 等同于以下方式
        queryWrapper.like("NAME", "赵").lt("AGE", "30").eq("AGE", "13");

        List<UserEntity> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * AllEq
     */
    @Test
    public void selectListAllEq() {
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

    /**
     * 4种Lambda条件构造器。
     * Lambda条件构造器的好处：防止列名误写
     */
    @Test
    public void selectListLambda() {
        // LambdaQueryWrapper<UserEntity> lambdaQueryWrapper1 = new QueryWrapper<UserEntity>().lambda();
        // LambdaQueryWrapper<UserEntity> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<UserEntity> lambdaQueryWrapper3 = Wrappers.lambdaQuery();
        lambdaQueryWrapper3.like(UserEntity::getName, "赵").lt(UserEntity::getAge, 30);
        List<UserEntity> userList = userMapper.selectList(lambdaQueryWrapper3);
        userList.forEach(System.out::println);

        // 第4种比较特殊
        List<UserEntity> userList4 = new LambdaQueryChainWrapper<UserEntity>(userMapper)
                .like(UserEntity::getName, "赵").ge(UserEntity::getAge, 30).list();
        userList4.forEach(System.out::println);
    }

    /**
     * 使用 Wrapper 自定义SQL
     */
    @Test
    public void selectByWrapperCustom() {
        LambdaQueryWrapper<UserEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.like(UserEntity::getName, "赵婉君").lt(UserEntity::getAge, 29);
        // 使用lambda的或普通构造器都可以
        List<UserEntity> userList = userMapper.selectByWrapperCustom(lambdaQueryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * MyBatis分页：使用RowBounds实现逻辑分页，或叫内存分页。
     *   原理：查出所有符合条件的数据到内存，然后返回某页数据。如果符合条件的数据多达上百万，后果会很严重
     *   两个严重问题：
     *       1、消耗内存
     *       2、查询数速度慢
     *
     *  mp分页插件：实现物理分页
     */

    /**
     * selectPage
     */
    @Test
    public void selectPage() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("AGE", "26");
        // 根据AGE排序
        queryWrapper.orderBy(true, false, "AGE");
        queryWrapper.orderByDesc("AGE");

        IPage<UserEntity> page = new Page<>(1, 2);
        IPage<UserEntity> iPage = userMapper.selectPage(page, queryWrapper);

        System.out.println("总记录数：" + iPage.getTotal());
        System.out.println("总页数：" + iPage.getPages());
        System.out.println("当前页：" + iPage.getCurrent());
        System.out.println("每页大小：" + iPage.getSize());
        System.out.println("排序信息：" + page.orders());
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
        // 根据AGE排序
        queryWrapper.orderBy(true, false, "AGE");
        queryWrapper.orderByDesc("AGE");

        IPage<Map<String, Object>> page = new Page<>(1, 2);
        IPage<Map<String, Object>> iPage = userMapper.selectMapsPage(page, queryWrapper);

        System.out.println("总记录数：" + iPage.getTotal());
        System.out.println("总页数：" + iPage.getPages());
        System.out.println("当前页：" + iPage.getCurrent());
        System.out.println("每页大小：" + iPage.getSize());
        System.out.println("排序信息：" + page.orders());
        List<Map<String, Object>> userList = iPage.getRecords();
        userList.forEach(System.out::println);
    }

    /**
     * 不进行 count 查询
     * 对应场景：app上拉加载时不需要查询总记录数
     */
    @Test
    public void selectMapsPageIgnoreCount() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("AGE", "26");
        // 根据AGE排序
        queryWrapper.orderBy(true, false, "AGE");
        queryWrapper.orderByDesc("AGE");

        IPage<Map<String, Object>> page = new Page<>(1, 2, false);
        IPage<Map<String, Object>> iPage = userMapper.selectMapsPage(page, queryWrapper);

        System.out.println("总记录数：" + iPage.getTotal());
        System.out.println("总页数：" + iPage.getPages());
        System.out.println("当前页：" + iPage.getCurrent());
        System.out.println("每页大小：" + iPage.getSize());
        List<Map<String, Object>> userList = iPage.getRecords();
        userList.forEach(System.out::println);
    }

    /**
     * 使用 Wrapper 自定义分页
     */
    @Test
    public void selectPageByWrapperCustom() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("AGE", "28");
        // 同样可以不进行 count 查询
        Page<UserEntity> page = new Page<>(1, 3);
        IPage<UserEntity> iPage = userMapper.selectPageByWrapperCustom(page, queryWrapper);

        System.out.println("总记录数：" + iPage.getTotal());
        System.out.println("总页数：" + iPage.getPages());
        System.out.println("当前页：" + iPage.getCurrent());
        System.out.println("每页大小：" + iPage.getSize());
        List<UserEntity> userList = iPage.getRecords();
        userList.forEach(System.out::println);
    }

    /**
     * 列表查询，携带角色列表
     */
    @Test
    public void selectWithRoleCustom() {
        UserEntity userEntity = new UserEntity();
        userEntity.setAccount("admin");
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setStatus(Byte.valueOf("1"));

        List<UserEntity> userList = userMapper.selectListWithRole(userEntity, roleEntity);

        System.out.println("userList.size(): " + userList.size());
        userList.forEach(System.out::println);
        userList.forEach(user -> System.out.println(user.getRole()));

        log.debug("检索嵌套域对象: ");

        Map<String, UserEntity> map = new HashMap<>();
        UserEntity user;
        if (ObjectUtils.isNotEmpty(userList)) {
            for (UserEntity userTemp : userList) {
                String account = userTemp.getAccount();
                // map中没有此user，则以userId为键，user对象为值放入HashMap/TreeMap
                user = map.get(account);
                if (user == null) {
                    user = userTemp;
                    map.put(account, user);
                }

                // 如果此user有所属角色，则将角色添加到user对象的roleList中
                RoleEntity role = userTemp.getRole();
                if (role != null) {
                    List<RoleEntity> roleList = user.getRoleList();
                    if (roleList == null) {
                        roleList = new ArrayList<>();
                        user.setRoleList(roleList);
                    }
                    roleList.add(role);
                }
            }
        }
        ArrayList<UserEntity> list = new ArrayList<>(map.values());
        // roleId转integer排序
        for (UserEntity u : list) {
            Collections.sort(u.getRoleList(), (r1, r2) -> Integer.valueOf(r1.getId()).compareTo(Integer.valueOf(r2.getId())));
        }
        System.out.println("list.size(): " + list.size());
        list.forEach(System.out::println);
        list.forEach(u -> u.getRoleList().forEach(System.out::println));

    }

    /**
     * 自定义分页，携带角色列表，连表查询，多个参数
     */
    @Test
    public void selectWithRolePageCustom() {
        UserEntity userEntity = new UserEntity();
        userEntity.setAccount("admin");
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setStatus(Byte.valueOf("1"));

        // 同样可以不进行 count 查询
        Page<UserEntity> page = new PagePlus<>(1, 3);
        IPage<UserEntity> iPage = userMapper.selectPageWithRole(page, userEntity, roleEntity);

        System.out.println("总记录数：" + iPage.getTotal());
        System.out.println("总页数：" + iPage.getPages());
        System.out.println("当前页：" + iPage.getCurrent());
        System.out.println("每页大小：" + iPage.getSize());
        List<UserEntity> userList = iPage.getRecords();

        // roleId转integer排序
        for (UserEntity u : userList) {
            Collections.sort(u.getRoleList(), (r1, r2) -> Integer.valueOf(r1.getId()).compareTo(Integer.valueOf(r2.getId())));
        }

        System.out.println("userList.size(): " + userList.size());
        userList.forEach(System.out::println);
        userList.forEach(user -> user.getRoleList().forEach(System.out::println));
    }


}
