package com.zhaolq.mars.service.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaolq.mars.service.sys.entity.MenuEntity;
import com.zhaolq.mars.service.sys.entity.RoleEntity;
import com.zhaolq.mars.service.sys.entity.UserEntity;
import com.zhaolq.mars.service.sys.mapper.UserMapper;
import com.zhaolq.mars.service.sys.service.IUserService;
import com.zhaolq.mars.tool.core.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

/**
 * <p>
 * 用户管理 服务实现类
 * </p>
 *
 * @author zhaolq
 * @since 2020-10-29
 */
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService {

    UserMapper userMapper;

    @Override
    public UserEntity getWithRole(UserEntity userEntity, RoleEntity roleEntity) {
        return userMapper.selectWithRole(userEntity, roleEntity);
    }

    @Override
    public UserEntity getWithRoleNestedSelectTest(UserEntity userEntity) {
        return userMapper.selectWithRoleNestedSelectTest(userEntity);
    }

    @Override
    public List<UserEntity> listWithRole(UserEntity userEntity, RoleEntity roleEntity) {
        return userMapper.selectListWithRole(userEntity, roleEntity);
    }

    @Override
    public IPage<UserEntity> pageWithRole(IPage<UserEntity> page, UserEntity userEntity, RoleEntity roleEntity) {
        return userMapper.selectPageWithRole(page, userEntity, roleEntity);
    }

    @Override
    public List<MenuEntity> getAuthorityMenuTree(UserEntity userEntity) {
        List<MenuEntity> list = userMapper.selectAuthorityMenu(userEntity);
        // 对list去重并排序
        TreeSet treeSet = CollUtil.toTreeSet(list, new Comparator<MenuEntity>() {
            @Override
            public int compare(MenuEntity o1, MenuEntity o2) {
                // 如果Id相同，则为重复项，应返回0
                if (o1.getId().compareTo(o2.getId()) == 0) {
                    return 0;
                }
                // 如果ParentId不相等，返回比较结果
                if (!StringUtils.equals(o1.getParentId(), o2.getParentId())) {
                    return Integer.valueOf(o1.getParentId()).compareTo(Integer.valueOf(o2.getParentId()));
                }
                // 如果OrderNum不相等，返回比较结果
                if (o1.getOrderNum().compareTo(o2.getOrderNum()) != 0) {
                    return o1.getOrderNum().compareTo(o2.getOrderNum());
                }
                // 当Id不同，ParentId和OrderNum都相等，返回Id的比较结果
                return o1.getId().compareTo(o2.getId());
            }
        });
        List<MenuEntity> sourceList = CollUtil.newArrayList(treeSet);

        List<MenuEntity> menuTreeList = new ArrayList<>();
        sourceList.removeIf(e -> {
            if (StrUtil.equals(e.getParentId(), "0")) {
                // 所有ParentId=0的一级menu添加到menuTreeList，不关心menu的类型（目录、菜单、按钮）
                menuTreeList.add(e);
                return true;
            }
            return false;
        });
        setChildren(menuTreeList, sourceList);

        // 校验树状结构菜单总数是否与改变结构前的菜单数量相同
        if (!NumberUtil.equals(getMenuTreeNum(menuTreeList), treeSet.size())) {
            log.error("树状结构菜单总数与期望值不符！");
            return null;
        }
        return menuTreeList;
    }

    private List<MenuEntity> setChildren(final List<MenuEntity> rootList, final List<MenuEntity> sourceList) {
        for (MenuEntity element : rootList) {
            sourceList.removeIf(e -> {
                if (StrUtil.equals(e.getParentId(), element.getId())) {
                    element.getChildren().add(e);
                    return true;
                }
                return false;
            });
            if (CollUtil.isNotEmpty(element.getChildren())) {
                setChildren(element.getChildren(), sourceList);
            }
        }
        return rootList;
    }

    /**
     * 获取树状结构菜单的菜单总数
     *
     * @param menuTreeList
     * @return int
     */
    private int getMenuTreeNum(final List<MenuEntity> menuTreeList) {
        if (menuTreeList == null || menuTreeList.size() == 0) {
            return 0;
        }
        int num = CollUtil.size(menuTreeList);
        for (MenuEntity e : menuTreeList) {
            if (e.getChildren().size() > 0) {
                num = num + getMenuTreeNum(e.getChildren());
            }
        }
        return num;
    }


}
