package com.zhaolq.mars.common.core.console;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.zhaolq.mars.common.core.constant.CharPool;
import com.zhaolq.mars.common.core.util.ConvertUtil;

/**
 * 填充内容
 *
 * @Author zhaolq
 * @Date 2023/6/13 10:29:35
 */
public class FillContent {
    final char CORNER = '+'; // 最初是'+'
    final char ROW_LINE_SBC = '-';
    final char ROW_LINE_DBC = '－';
    final char COLUMN_LINE = CharPool.SPACE; // 最初是'|'
    final char SPACE_SBC = CharPool.SPACE;
    final char SPACE_DBC = '\u3000';
    final char LF = CharPool.LF;

    /**
     * 填充表格头或者体
     *
     * @param l 被填充列表
     * @param columns 填充内容
     * @param columnCharNumber 每列最大字符个数
     * @param isDBCMode 是否使用全角模式
     */
    void fillColumns(List<String> l, String[] columns, List<Integer> columnCharNumber, boolean isDBCMode) {
        for (int i = 0; i < columns.length; i++) {
            String column = columns[i];
            if (isDBCMode) {
                column = ConvertUtil.toDBC(column);
            }
            l.add(column);
            int width = column.length();
            if (width > columnCharNumber.get(i)) {
                columnCharNumber.set(i, width);
            }
        }
    }

    /**
     * 填充表头或者表体信息（多行）
     *
     * @param sb 内容
     * @param list 表头列表或者表体列表
     * @param columnCharNumber 每列最大字符个数
     * @param isDBCMode 是否使用全角模式
     */
    void fillRows(StringBuilder sb, List<List<String>> list, List<Integer> columnCharNumber, boolean isDBCMode) {
        for (List<String> row : list) {
            sb.append(COLUMN_LINE);
            if (isDBCMode) {
                fillRowDBC(sb, row, columnCharNumber);
            } else {
                fillRowSBC(sb, row, columnCharNumber);
            }
            sb.append(LF);
        }
    }

    /**
     * 填充一行数据，半角
     *
     * @param sb 内容
     * @param row 一行数据
     * @param columnCharNumber 每列最大字符个数
     */
    void fillRowSBC(StringBuilder sb, List<String> row, List<Integer> columnCharNumber) {
        final int size = row.size();
        for (int i = 0; i < size; i++) {
            sb.append(StringUtils.repeat(SPACE_SBC, 2));
            int length = columnCharNumber.get(i) + 2;
            sb.append(String.format("%-" + length + "s", row.get(i)));
            sb.append(COLUMN_LINE);
        }
    }

    /**
     * 填充一行数据，全角
     *
     * @param sb 内容
     * @param row 一行数据
     * @param columnCharNumber 每列最大字符个数
     */
    void fillRowDBC(StringBuilder sb, List<String> row, List<Integer> columnCharNumber) {
        final int size = row.size();
        String value;
        for (int i = 0; i < size; i++) {
            sb.append(SPACE_DBC);
            value = row.get(i);
            sb.append(value);
            final int length = value.length();
            final int sbcCount = sbcCount(value);
            if (sbcCount % 2 == 1) {
                sb.append(CharPool.SPACE);
            }
            sb.append(SPACE_DBC);
            int maxLength = columnCharNumber.get(i);
            for (int j = 0; j < (maxLength - length + (sbcCount / 2)); j++) {
                sb.append(SPACE_DBC);
            }
            sb.append(COLUMN_LINE);
        }
    }

    /**
     * 拼装边框
     *
     * @param sb StringBuilder
     * @param columnCharNumber 每列最大字符个数
     * @param isDBCMode 是否使用全角模式
     */
    void fillBorder(StringBuilder sb, List<Integer> columnCharNumber, boolean isDBCMode) {
        sb.append(CORNER);
        for (Integer width : columnCharNumber) {
            if (isDBCMode) {
                sb.append(StringUtils.repeat(ROW_LINE_DBC, width + 2));
            } else {
                sb.append(StringUtils.repeat(ROW_LINE_SBC, width + 4));
            }
            sb.append(CORNER);
        }
        sb.append(LF);
    }

    /**
     * 半角字符数量
     *
     * @param value 字符串
     * @return 填充空格数量
     */
    int sbcCount(String value) {
        int count = 0;
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) < '\177') {
                count++;
            }
        }
        return count;
    }
}
