package com.zhaolq.mars.tool.core.utils;

import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;
import java.util.*;

import static com.zhaolq.mars.tool.core.utils.JacksonUtils.*;

/**
 *
 *
 * @author zhaolq
 * @date 2021/4/23 13:36
 */
public class JacksonUtilsTest {

    private User user;

    private Role role;
    private List<Role> roleList;

    private String json;
    private Map<String, Object> map;
    private Map<String, User> userMap;
    private List<Object> list;
    private List<User> userList;

    static {
    }

    @BeforeEach
    public void before() {

        roleList = new ArrayList<>();
        Role roleTemp;
        for (int i = 1; i <= 5; i++) {
            roleTemp = new Role();
            roleTemp.setId(String.valueOf(i));
            roleTemp.setName("角色" + i);
            roleList.add(roleTemp);
        }

        role = new Role();
        role.setId("10000");
        role.setName("角色10000");

        user = new User();
        user.setId("9");
        user.setName("周润发");
        user.setSex(Byte.valueOf("1"));
        user.setBirthday(new Date());
        user.setAge(Integer.valueOf(18));
        user.setCreateTime(LocalDateTime.now());
        user.setRole(role);
        user.setRoleList(roleList);

        userMap = new HashMap<>();
        userMap.put(user.getId(), user);

        list = new ArrayList<>();
        list.add("周润发");
        list.add("刘德华");

        userList = new ArrayList<>();
        userList.add(user);
        userList.add(user);
    }

    @Test
    public void conversion() {

        StopWatch stopWatch = new StopWatch("测试");
        stopWatch.start("1");

        System.out.println("----------------- json和object互转 ----------------------------");

        json = objectToJson(user);
        System.out.println("1、object转json，忽略空值: \t\t\t\t" + json);

        user = jsonToObject(json, User.class);
        System.out.println("2、json转object: \t\t\t\t\t\t" + user);

        System.out.println();
        System.out.println("----------------- json和map<String, Object>互转 ----------------------------");

        map = jsonToMap(json);
        System.out.println("1、json转map<String, Object>: \t\t\t" + map);

        json = objectToJson(map);
        System.out.println("2、map<String, Object>转json: \t\t\t" + json);

        System.out.println();
        System.out.println("----------------- json和map<String, User>互转 ----------------------------");

        String userMapJson = objectToJson(userMap);
        System.out.println("1、map<String, User>转json: \t\t\t\t" + userMapJson);

        userMap = jsonToMap(userMapJson, String.class, User.class);
        System.out.println("2、json转map<String, User>: \t\t\t\t" + userMap);

        System.out.println();
        System.out.println("----------------- json和list<Object>互转 ----------------------------");

        String listJson = objectToJson(list);
        System.out.println("1、list<Object>转json: \t\t\t\t\t" + listJson);

        list = jsonToList(listJson);
        System.out.println("2、json转list<Object>: \t\t\t\t\t" + list);

        System.out.println();
        System.out.println("----------------- json和list<User>互转 ----------------------------");

        String userListJson = objectToJson(userList);
        System.out.println("1、list<User>转json: \t\t\t\t\t" + userListJson);

        userList = jsonToList(userListJson, User.class);
        System.out.println("2、json转list<User>: \t\t\t\t\t" + userList);

        System.out.println();
        System.out.println("----------------- map<String, Object>和object互转 ----------------------------");

        map = objectToObject(user, Map.class);
        System.out.println("object转map<String, Object>: \t\t" + map);

        User obj = objectToObject(map, User.class);
        System.out.println("map<String, Object>转object: \t\t" + obj);

        System.out.println();
        System.out.println("*****************************************************************************");

        stopWatch.stop();

        stopWatch.start("2");
        stopWatch.stop();

        System.out.println(stopWatch.getTotalTimeSeconds());
        System.out.println(stopWatch.prettyPrint());
    }

    @Data
    static class User {
        private String id;
        private String account;
        private String password;
        private String name;
        private Byte sex;
        private Integer age;
        private Date birthday;
        private LocalDateTime createTime;
        private Role role;
        private List<Role> roleList;
    }

    @Data
    static class Role {
        private String id;
        private String name;
    }

}
