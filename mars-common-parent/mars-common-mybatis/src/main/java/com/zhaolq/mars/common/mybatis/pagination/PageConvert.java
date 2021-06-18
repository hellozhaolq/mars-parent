package com.zhaolq.mars.common.mybatis.pagination;

import cn.hutool.core.text.StrSpliter;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhaolq.mars.tool.core.constant.StringPool;
import com.zhaolq.mars.tool.core.utils.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 分页转换
 *
 * @author zhaolq
 * @date 2021/6/1 13:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PageConvert<T> {

    public static final long ZERO = 0L;
    public static final long DEFAULT_SIZE = 10L;
    public static final long DEFAULT_CURRENT = 1L;

    /**
     * 每页显示条数，默认 10
     */
    protected long size = DEFAULT_SIZE;

    /**
     * 当前页
     */
    protected long current = DEFAULT_CURRENT;

    /**
     * 正序列，格式：,,column1,column2,column3,,
     */
    protected String ascColumns;

    /**
     * 反序列，格式：,,column1,column2,column3,,
     */
    protected String descColumns;

    public Page<T> getPage() {
        return getPage(getSize(), getCurrent(), getAscColumns(), getDescColumns());
    }

    public static <T> Page<T> getPage(HttpServletRequest request) {
        long size = NumberUtil.parseLong(request.getParameter("size"));
        long current = NumberUtil.parseLong(request.getParameter("current"));
        String ascColumns = request.getParameter("ascColumns");
        String descColumns = request.getParameter("descColumns");
        return getPage(size, current, ascColumns, descColumns);
    }

    public static <T> Page<T> getPage(long size, long current) {
        return getPage(size, current, null, null);
    }

    public static <T> Page<T> getPage(long size, long current, String ascColumns, String descColumns) {
        Page<T> page = new Page<>();
        page.setSize(size == ZERO ? DEFAULT_SIZE : size);
        page.setCurrent(current == ZERO ? DEFAULT_CURRENT : current);

        if (StringUtils.isNotBlank(ascColumns)) {
            String[] ascColumnArray = StrSpliter.splitToArray(ascColumns, StringPool.C_COMMA, 0, true, true);
            List<OrderItem> ascList = OrderItem.ascs(ascColumnArray);
            page.addOrder(ascList);
        }

        if (StringUtils.isNotBlank(descColumns)) {
            String[] descColumnArray = StrSpliter.splitToArray(descColumns, StringPool.C_COMMA, 0, true, true);
            List<OrderItem> descList = OrderItem.descs(descColumnArray);
            page.addOrder(descList);
        }

        return page;
    }

    public PagePlus<T> getPagePlus() {
        return getPagePlus(getSize(), getCurrent(), getAscColumns(), getDescColumns());
    }

    public static <T> PagePlus<T> getPagePlus(HttpServletRequest request) {
        long size = NumberUtil.parseLong(request.getParameter("size"));
        long current = NumberUtil.parseLong(request.getParameter("current"));
        String ascColumns = request.getParameter("ascColumns");
        String descColumns = request.getParameter("descColumns");
        return getPagePlus(size, current, ascColumns, descColumns);
    }

    public static <T> PagePlus<T> getPagePlus(long size, long current) {
        return getPagePlus(size, current, null, null);
    }

    public static <T> PagePlus<T> getPagePlus(long size, long current, String ascColumns, String descColumns) {
        PagePlus<T> pagePlus = new PagePlus<>();
        pagePlus.setSize(size == ZERO ? DEFAULT_SIZE : size);
        pagePlus.setCurrent(current == ZERO ? DEFAULT_CURRENT : current);
        pagePlus.setAscColumns(ascColumns);
        pagePlus.setDescColumns(descColumns);
        return pagePlus;
    }

}
