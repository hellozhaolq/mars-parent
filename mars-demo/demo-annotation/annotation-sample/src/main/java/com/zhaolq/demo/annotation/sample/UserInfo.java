package com.zhaolq.demo.annotation.sample;

import com.zhaolq.demo.annotation.Constraints;
import com.zhaolq.demo.annotation.DBTable;
import com.zhaolq.demo.annotation.SQLInteger;
import com.zhaolq.demo.annotation.SQLString;

/**
 * 数据库表成员对应实例类bean
 *
 * @author zhaolq
 * @date 2020/7/10 11:51
 */
@DBTable(name = "USER_INFO")
public class UserInfo {
    /**
     * 主键ID
     */
    @SQLString(name = "ID", value = 50, constraint = @Constraints(primaryKey = true))
    private String id;

    @SQLString(name = "NAME", value = 30)
    private String name;

    @SQLInteger(name = "AGE")
    private int age;

    /**
     * 个人描述
     */
    @SQLString(name = "DESCRIPTION", value = 150, constraint = @Constraints(allowNull = true))
    private String description;

    //省略set get.....
}
