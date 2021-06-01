package com.zhaolq.mars.common.mybatis.pagination;

import cn.hutool.core.text.CharPool;
import cn.hutool.core.text.StrSpliter;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 分页转换工具
 *
 * @author zhaolq
 * @date 2021/6/1 13:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PageConvert<T> {

    /**
     * 每页显示条数，默认 10
     */
    protected long size = 10;

    /**
     * 当前页
     */
    protected long current = 1;

    /**
     * 正序列，格式：,,column1,column2,column3,,
     */
    protected String ascColumns;

    /**
     * 反序列，格式：,,column1,column2,column3,,
     */
    protected String descColumns;

    public Page<T> getPage() {
        Page<T> page = new Page<>();
        page.setSize(getSize());
        page.setCurrent(getCurrent());

        String[] ascColumns = StrSpliter.splitToArray(getAscColumns(), CharPool.COMMA, 0, true, true);
        List<OrderItem> ascList = OrderItem.ascs(ascColumns);
        page.addOrder(ascList);

        String[] descColumns = StrSpliter.splitToArray(getDescColumns(), CharPool.COMMA, 0, true, true);
        List<OrderItem> descList = OrderItem.descs(descColumns);
        page.addOrder(descList);

        return page;
    }

}
