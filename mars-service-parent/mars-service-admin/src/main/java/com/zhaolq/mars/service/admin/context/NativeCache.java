package com.zhaolq.mars.service.admin.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.zhaolq.mars.api.sys.entity.MenuEntity;
import com.zhaolq.mars.api.sys.entity.RoleEntity;
import com.zhaolq.mars.service.admin.service.IMenuService;

/**
 * 本地缓存
 * 当前登录的用户信息，有哪些权限：角色、菜单
 *
 * 正式环境不可使用：
 * 分布式集群情况下，每个服务都有一份缓存，而每个请求只会打到一个服务上，这个服务的内容会被修改（增删改），当同一用户的
 * 下一次请求被分配到其他服务时，找不到缓存或缓存未更新，造成状态不一致。除非是永不改变的信息。
 *
 * @author zhaolq
 * @date 2021/6/9 22:12
 */
@Service
public class NativeCache {

    /**
     * key:菜单id；value:菜单对象
     */
    private Map<String, MenuEntity> menuMap = new HashMap<>();
    /**
     * key:用户id；value:用户对应的角色列表
     */
    private Map<String, List<RoleEntity>> userRoleMap = new HashMap<>();

    private final IMenuService menuService;

    public NativeCache(IMenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 创建此对象时的初始化方法，对应PreDestroy。
     */
    @PostConstruct
    public void init() {
        List<MenuEntity> menuList = menuService.list();
        menuList.forEach(menuEntity -> menuMap.put(menuEntity.getId(), menuEntity));
    }

    public List<MenuEntity> getMenus() {
        if (menuMap.isEmpty()) {
            init();
        }
        return new ArrayList<>(menuMap.values());
    }

    /***** 当对菜单进行修改时，改数据库同时需要修改缓存菜单 *****/

    public void saveMenu(MenuEntity menuEntity) {
        menuMap.put(menuEntity.getId(), menuEntity);
    }

    public void removeMenu(String menuId) {
        if (menuMap.containsKey(menuId)) {
            menuMap.remove(menuId);
        }
    }

    public void updateMenu(MenuEntity menuEntity) {
        if (menuMap.containsKey(menuEntity.getId())) {
            removeMenu(menuEntity.getId());
            menuMap.put(menuEntity.getId(), menuEntity);
        }
    }

    public MenuEntity getMenu(String menuId) {
        return menuMap.get(menuId);
    }


    public List<RoleEntity> getUserRole(String userId) {
        return userRoleMap.get(userId);
    }

    public void setUserRole(String userId, List<RoleEntity> roleList) {
        userRoleMap.put(userId, roleList);
    }
}
