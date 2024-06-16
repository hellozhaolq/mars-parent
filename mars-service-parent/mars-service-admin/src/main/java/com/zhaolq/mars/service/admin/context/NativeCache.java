package com.zhaolq.mars.service.admin.context;

import org.springframework.stereotype.Service;

/**
 * 本地缓存
 * 当前登录的用户信息，有哪些权限：角色、菜单
 *
 * 正式环境不可使用：
 * 分布式集群情况下，每个服务都有一份缓存，而每个请求只会打到一个服务上，这个服务的内容会被修改（增删改），当同一用户的
 * 下一次请求被分配到其他服务时，找不到缓存或缓存未更新，造成状态不一致。除非是永不改变的信息。
 *
 * @Author zhaolq
 * @Date 2021/6/9 22:12
 */
@Service
public class NativeCache {

}
