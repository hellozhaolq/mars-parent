package com.zhaolq.mars.service.admin.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaolq.mars.api.admin.entity.MenuEntity;
import com.zhaolq.mars.api.admin.entity.RoleEntity;
import com.zhaolq.mars.api.admin.entity.UserEntity;
import com.zhaolq.mars.service.admin.dao.base.UserMapper;
import com.zhaolq.mars.service.admin.service.IUserService;
import com.zhaolq.mars.tool.core.collection.CollectionUtils;
import com.zhaolq.mars.tool.core.utils.NumberUtils;
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
 * @date 2020-10-29
 */
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService {

    private UserMapper userMapper;

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

    @Deprecated
    @Override
    public IPage<UserEntity> pageWithRole(IPage<UserEntity> page, UserEntity userEntity, RoleEntity roleEntity) {
        return userMapper.selectPageWithRole(page, userEntity, roleEntity);
    }

    @Override
    public IPage<UserEntity> pageWithRoleNestedSelectTest(IPage<UserEntity> page, UserEntity userEntity) {
        return userMapper.selectPageWithRoleNestedSelectTest(page, userEntity);
    }

    @Override
    public List<MenuEntity> getAuthorityMenuTree(UserEntity userEntity) {
        List<MenuEntity> list = userMapper.selectAuthorityMenu(userEntity);
        // 对list去重并排序
        TreeSet treeSet = CollectionUtils.toTreeSet(list, new Comparator<MenuEntity>() {
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
        List<MenuEntity> sourceList = CollectionUtils.newArrayList(treeSet);

        List<MenuEntity> menuTreeList = new ArrayList<>();
        sourceList.removeIf(e -> {
            if (StringUtils.equals(e.getParentId(), "0")) {
                // 所有ParentId=0的一级menu添加到menuTreeList，不关心menu的类型（目录、菜单、按钮）
                menuTreeList.add(e);
                return true;
            }
            return false;
        });
        setChildren(menuTreeList, sourceList);

        // 校验：树状结构菜单总数是否与改变结构前的菜单数量相同
        if (!NumberUtils.equals(getMenuTreeNum(menuTreeList), treeSet.size())) {
            log.error("树状结构菜单总数与期望值不符，证明代码有误！");
            return null;
        }
        return menuTreeList;
    }

    private List<MenuEntity> setChildren(final List<MenuEntity> rootList, final List<MenuEntity> sourceList) {
        for (MenuEntity element : rootList) {
            sourceList.removeIf(e -> {
                if (StringUtils.equals(e.getParentId(), element.getId())) {
                    element.getChildren().add(e);
                    return true;
                }
                return false;
            });
            if (CollectionUtils.isNotEmpty(element.getChildren())) {
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
        int num = CollectionUtils.size(menuTreeList);
        for (MenuEntity e : menuTreeList) {
            if (e.getChildren().size() > 0) {
                num = num + getMenuTreeNum(e.getChildren());
            }
        }
        return num;
    }

    @Override
    public List<Tree<String>> getAuthorityMenuTree2(UserEntity userEntity) {
        // 查询所有权限菜单。包括已拥有角色的全部菜单，所以有重复数据。
        List<MenuEntity> allAuthorityMenuList = userMapper.selectAuthorityMenu(userEntity);
        // 去重集合
        List<MenuEntity> menuListDistinct = CollectionUtils.distinct(allAuthorityMenuList);

        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setIdKey("id");
        treeNodeConfig.setParentIdKey("parentId");
        treeNodeConfig.setWeightKey("orderNum");
        treeNodeConfig.setNameKey("name");
        treeNodeConfig.setChildrenKey("children");
        // 最大递归深度
        treeNodeConfig.setDeep(3);

        // 0表示最顶层的id是0
        List<Tree<String>> treeList = TreeUtil.build(menuListDistinct, "0", treeNodeConfig,
                (menuEntity, tree) -> {
                    tree.setId(menuEntity.getId());
                    tree.setParentId(menuEntity.getParentId());
                    tree.setName(menuEntity.getName());
                    tree.setWeight(menuEntity.getOrderNum());
                    // 扩展属性 ...
                    tree.putExtra("code", menuEntity.getCode());
                    tree.putExtra("perms", menuEntity.getPerms());
                    tree.putExtra("type", menuEntity.getType());
                    tree.putExtra("urlType", menuEntity.getUrlType());
                    tree.putExtra("url", menuEntity.getUrl());
                    tree.putExtra("scheme", menuEntity.getScheme());
                    tree.putExtra("path", menuEntity.getPath());
                    tree.putExtra("target", menuEntity.getTarget());
                    tree.putExtra("icon", menuEntity.getIcon());
                    tree.putExtra("status", menuEntity.getStatus());
                    tree.putExtra("delFlag", menuEntity.getDelFlag());
                    tree.putExtra("remark", menuEntity.getRemark());
                    tree.putExtra("createBy", menuEntity.getCreateBy());
                    tree.putExtra("createTime", menuEntity.getCreateTime());
                    tree.putExtra("lastUpdateBy", menuEntity.getLastUpdateBy());
                    tree.putExtra("lastUpdateTime", menuEntity.getLastUpdateTime());
                });

        return treeList;
    }


}