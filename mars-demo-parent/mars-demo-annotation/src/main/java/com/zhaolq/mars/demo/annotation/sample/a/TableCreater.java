package com.zhaolq.mars.demo.annotation.sample.a;

import com.zhaolq.mars.demo.annotation.annotation.Constraints;
import com.zhaolq.mars.demo.annotation.annotation.DBTable;
import com.zhaolq.mars.demo.annotation.annotation.SQLInteger;
import com.zhaolq.mars.demo.annotation.annotation.SQLString;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 表生成器
 *
 * @author zhaolq
 * @since 2020/7/10 11:51
 */
public class TableCreater {

    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println("begin");
        System.out.println(createTableSql("com.zhaolq.mars.demo.annotation.sample.a.UserInfo"));
        System.out.println("end");
    }

    private static String createTableSql(String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        DBTable dbTable = clazz.getAnnotation(DBTable.class);

        if (dbTable == null) {
            System.out.println("no dbtable annotations in class" + className);
            return null;
        }

        String tableName = dbTable.name();
        if (tableName.length() < 1) {
            tableName = clazz.getSimpleName().toUpperCase();
        }

        List<String> columnDefs = new ArrayList<String>();

        Field[] filedArr = clazz.getDeclaredFields();
        for (Field field : filedArr) {
            String columnName = null;
            Annotation[] anns = field.getAnnotations();
            if (anns.length < 1) {
                continue;
            }
            if (anns[0] instanceof SQLInteger) {
                SQLInteger sInt = (SQLInteger) anns[0];
                if (sInt.name().length() < 1) {
                    columnName = field.getName().toUpperCase();
                } else {
                    columnName = sInt.name();
                }
                columnDefs.add(columnName + " INT" + getConstraints(sInt.constaint()));
            }

            if (anns[0] instanceof SQLString) {
                SQLString sString = (SQLString) anns[0];
                if (sString.name().length() < 1) {
                    columnName = field.getName().toUpperCase();
                } else {
                    columnName = sString.name();
                }
                columnDefs.add(columnName + " VARCHAR(" + sString.value() + ")" + getConstraints(sString.constraint()));
            }
        }
        StringBuilder createCommand = new StringBuilder("CREATE TABLE " + tableName + "(");

        for (String columnDef : columnDefs) {
            createCommand.append("\n    " + columnDef + ",");
        }

        String tableCreate = createCommand.substring(0, createCommand.length() - 1) + ");";

        return tableCreate;
    }

    private static String getConstraints(Constraints constaint) {
        StringBuilder sb = new StringBuilder();
        if (!constaint.allowNull()) {
            sb.append(" not null");
        }
        if (constaint.primaryKey()) {
            sb.append(" primary key");
        }
        if (constaint.unique()) {
            sb.append(" unique");
        }
        return sb.toString();
    }

}
