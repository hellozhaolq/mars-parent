package com.zhaolq.mars.common.core.console;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 控制台打印键值对工具
 *
 * @Author zhaolq
 * @Date 2023/6/13 9:42:43
 */
public class ConsoleKeyValue extends FillContent {

    /**
     * 全部信息（标题和内容）
     */
    private final List<List<String>> allList = new ArrayList<>();

    /**
     * 每列最大字符个数
     */
    private List<Integer> columnCharNumber = new ArrayList<>(Collections.nCopies(3, 0));

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
    public static ConsoleKeyValue create() {
        return new ConsoleKeyValue();
    }

    public static void main(String[] args) {
        ConsoleKeyValue.create()
                .setDBCMode(false)
                .addTitle("ThreadName")
                .addKeyValue("ThreadName", "http-nio-60001-exec-1")
                .addTitle("RequestHeaders")
                .addKeyValue("content-type", "multipart/form-data; boundary=--------------------------592397919336676994762912")
                .print();
    }

    /**
     * 设置是否使用全角模式<br>
     * 当包含中文字符时，输出的表格可能无法对齐，因此当设置为全角模式时，全部字符转为全角。
     *
     * @param isDBCMode 是否全角模式
     * @return this
     */
    public ConsoleKeyValue setDBCMode(boolean isDBCMode) {
        this.isDBCMode = isDBCMode;
        return this;
    }

    /**
     * 添加标题信息
     *
     * @param title title
     * @return com.zhaolq.mars.common.core.console.ConsoleKeyValue
     */
    public ConsoleKeyValue addTitle(final String title) {
        String[] values = {title, "", ""};
        List<String> l = new ArrayList<>();
        fillColumns(l, values, columnCharNumber, isDBCMode);
        allList.add(l);
        return this;
    }

    /**
     * 添加键值信息
     *
     * @param key   key
     * @param value value
     * @return com.zhaolq.mars.common.core.console.ConsoleKeyValue
     */
    public ConsoleKeyValue addKeyValue(String key, String value) {
        String[] values = {"", key, value};
        List<String> l = new ArrayList<>();
        allList.add(l);
        fillColumns(l, values, columnCharNumber, isDBCMode);
        return this;
    }

    /**
     * 添加键值信息
     *
     * @param map map
     * @return com.zhaolq.mars.common.core.console.ConsoleKeyValue
     */
    public ConsoleKeyValue addKeyValues(Map<String, String> map) {
        map.forEach((k, v) -> {
            String[] values = {"", k, v};
            List<String> l = new ArrayList<>();
            allList.add(l);
            fillColumns(l, values, columnCharNumber, isDBCMode);
        });
        return this;
    }

    /**
     * 打印到控制台
     */
    public void print() {
        System.out.println(this);
    }

    /**
     * 获取键值对字符串
     *
     * @return 表格字符串
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        fillBorder(sb, columnCharNumber, isDBCMode);
        fillRows(sb, allList, columnCharNumber, isDBCMode);
        fillBorder(sb, columnCharNumber, isDBCMode);
        return sb.toString();
    }

    @Override
    void fillRowSBC(StringBuilder sb, List<String> row, List<Integer> columnCharNumber) {
        // 填充title列
        sb.append(StringUtils.repeat(SPACE_SBC, 2));
        int titleColumnWidth = columnCharNumber.get(0) + 2;
        sb.append(String.format("%-" + titleColumnWidth + "s", row.get(0)));
        sb.append(COLUMN_LINE);

        // 填充key列
        sb.append(StringUtils.repeat(SPACE_SBC, 2));
        int keyColumnWidth = columnCharNumber.get(1) + 1;
        sb.append(String.format("%" + keyColumnWidth + "s", row.get(1)));
        if (StringUtils.isNotEmpty(row.get(1))) {
            sb.append(":");
        } else {
            sb.append(SPACE_SBC);
        }
        sb.append(COLUMN_LINE);

        // 填充value列
        int valueColumnWidth = columnCharNumber.get(2) + 4;
        sb.append(String.format("%-" + valueColumnWidth + "s", row.get(2)));
        sb.append(COLUMN_LINE);
    }
}
