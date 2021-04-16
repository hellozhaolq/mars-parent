package com.zhaolq.mars.service.sys.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhaolq.mars.service.sys.entity.UserEntity;
import com.zhaolq.mars.service.sys.entity.RoleEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * 自定义分页
 *
 * @author zhaolq
 * @date 2020/10/22 21:29
 */
@Slf4j
@SpringBootTest
public class UserMapperPage_Custom {

    @Resource
    private UserMapper userMapper;

    /**
     * 使用 Wrapper 自定义分页
     */
    @Test
    public void customSelectUserPage() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("AGE", "28");
        // 同样可以不进行 count 查询
        IPage<UserEntity> page = new Page<>(1, 3);
        IPage<UserEntity> iPage = userMapper.customSelectUserPageByWrapper(page, queryWrapper);

        System.out.println("总记录数：" + iPage.getTotal());
        System.out.println("总页数：" + iPage.getPages());
        System.out.println("当前页：" + iPage.getCurrent());
        System.out.println("每页大小：" + iPage.getSize());
        List<UserEntity> userList = iPage.getRecords();
        userList.forEach(System.out::println);
    }

    /**
     * 自定义分页，连表查询，多个参数
     */
    @Test
    public void customSelectUserAndRolePage() {
        UserEntity userEntity = new UserEntity();
        userEntity.setSex(Byte.valueOf("1"));
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName("mng");

        // 同样可以不进行 count 查询
        IPage<UserEntity> page = new Page<>(1, 3);
        IPage<UserEntity> iPage = userMapper.customSelectUserAndRolePage(page, userEntity, roleEntity);

        System.out.println("总记录数：" + iPage.getTotal());
        System.out.println("总页数：" + iPage.getPages());
        System.out.println("当前页：" + iPage.getCurrent());
        System.out.println("每页大小：" + iPage.getSize());
        List<UserEntity> userList = iPage.getRecords();
        userList.forEach(System.out::println);
    }

}
