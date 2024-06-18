package com.zhaolq.mars.common.core.console;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 控制台打印表格工具
 *
 * @Author zhaolq
 * @Date 2023/6/9 15:36:28
 */
public class ConsoleTable extends FillContent {
    /**
     * 表格头信息
     */
    private final List<List<String>> headerList = new ArrayList<>();
    /**
     * 表格体信息
     */
    private final List<List<String>> bodyList = new ArrayList<>();
    /**
     * 每列最大字符个数
     */
    private List<Integer> columnCharNumber;

    /**
     * 半角字符：占用一个字节。half-width character。single-byte character(SBC)
     * 全角字符：占用两个字节。full-width character。double-byte character(DBC)
     */
    private boolean isDBCMode = false;

    /**
     * 创建ConsoleTable对象
     *
     * @return ConsoleTable
     */
    public static ConsoleTable create() {
        return new ConsoleTable();
    }

    public static void main(String[] args) throws IOException {
        ConsoleTable consoleTable = ConsoleTable.create()
                .setDBCMode(false)
                .addHeader("column1", "column2", "column3", "column4", "column5");
        for (int i = 0; i < 10; i++) {
            String column1 = RandomStringUtils.randomNumeric(i + 1);
            String column2 = RandomStringUtils.randomGraph(i + 1);
            String column3 = RandomStringUtils.randomAscii(i + 1);
            String column4 = RandomStringUtils.randomAlphabetic(i + 1);
            String column5 = RandomStringUtils.randomAlphanumeric(i + 1);
            consoleTable.addBody(column1, column2, column3, column4, column5);
        }
        consoleTable.print(true);
    }

    /**
     * 设置是否使用全角模式<br>
     * 当包含中文字符时，输出的表格可能无法对齐，因此当设置为全角模式时，全部字符转为全角。
     *
     * @param isDBCMode 是否全角模式
     * @return this
     */
    public ConsoleTable setDBCMode(boolean isDBCMode) {
        this.isDBCMode = isDBCMode;
        return this;
    }

    /**
     * 添加头信息
     *
     * @param columnNames 列名
     * @return 自身对象
     */
    public ConsoleTable addHeader(String... columnNames) {
        if (columnCharNumber == null) {
            columnCharNumber = new ArrayList<>(Collections.nCopies(columnNames.length, 0));
        }
        List<String> l = new ArrayList<>();
        fillColumns(l, columnNames, columnCharNumber, isDBCMode);
        headerList.add(l);
        return this;
    }

    /**
     * 添加体信息
     *
     * @param values 列值
     * @return 自身对象
     */
    public ConsoleTable addBody(String... values) {
        if (columnCharNumber == null) {
            columnCharNumber = new ArrayList<>(Collections.nCopies(values.length, 0));
        }
        List<String> l = new ArrayList<>();
        bodyList.add(l);
        fillColumns(l, values, columnCharNumber, isDBCMode);
        return this;
    }

    /**
     * 打印到控制台
     *
     * @param isSort 是否排序
     * @return void
     */
    public void print(Boolean isSort) {
        if (isSort) {
            Collections.sort(bodyList, (o1, o2) -> {
                int result = 0;
                int i = 0;
                while (i < o1.size() && result == 0) {
                    result = o1.get(i).compareToIgnoreCase(o2.get(i));
                    i++;
                }
                return result;
            });
        }
        System.out.println(this);
    }

    /**
     * 获取表格字符串
     *
     * @return 表格字符串
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        fillBorder(sb, columnCharNumber, isDBCMode);
        if (!(headerList == null || headerList.isEmpty())) {
            fillRows(sb, headerList, columnCharNumber, isDBCMode);
            fillBorder(sb, columnCharNumber, isDBCMode);
        }
        fillRows(sb, bodyList, columnCharNumber, isDBCMode);
        fillBorder(sb, columnCharNumber, isDBCMode);
        return sb.toString();
    }
}
