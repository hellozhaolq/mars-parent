package com.zhaolq.mars.common.mybatis.pagination;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.zhaolq.mars.common.core.constant.StringPool;
import com.zhaolq.mars.common.core.db.PageUtils;

/**
 * 封装分页模型：
 * 新增排序参数
 * 添加分页彩虹
 *
 * @author zhaolq
 * @date 2021/5/31 17:33
 */
public class PagePlus<T> extends Page<T> {

    /**
     * 正序列，格式：,,column1,column2,column3,,
     */
    protected String ascColumns;

    /**
     * 反序列，格式：,,column1,column2,column3,,
     */
    protected String descColumns;

    /**
     * 分页彩虹
     */
    protected int[] rainbow;

    public PagePlus() {
    }

    public PagePlus(long current, long size) {
        super(current, size, 0);
    }

    public PagePlus(long current, long size, long total) {
        super(current, size, total, true);
    }

    public PagePlus(long current, long size, boolean isSearchCount) {
        super(current, size, 0, isSearchCount);
    }

    public PagePlus(long current, long size, long total, boolean isSearchCount) {
        super(current, size, total, isSearchCount);
    }

    public String getAscColumns() {
        return ascColumns;
    }

    public void setAscColumns(String ascColumns) {
        String[] columns = StringUtils.split(ascColumns, StringPool.COMMA, 0);
        List<OrderItem> list = OrderItem.ascs(columns);
        addOrder(list);
        this.ascColumns = Arrays.toString(columns);
    }

    public String getDescColumns() {
        return descColumns;
    }

    public void setDescColumns(String descColumns) {
        String[] columns = StringUtils.split(descColumns, StringPool.COMMA, 0);
        List<OrderItem> list = OrderItem.descs(columns);
        addOrder(list);
        this.descColumns = Arrays.toString(columns);
    }

    public int[] getRainbow() {
        return rainbow;
    }

    public void setRainbow(int[] rainbow) {
        this.rainbow = rainbow;
    }

    @Override
    public Page<T> setTotal(long total) {
        this.total = total;
        // 总页数
        int totalPage = PageUtils.totalPage((int) total, (int) getSize());
        this.rainbow = PageUtils.rainbow((int) getCurrent(), totalPage, 7);
        return this;
    }

}
