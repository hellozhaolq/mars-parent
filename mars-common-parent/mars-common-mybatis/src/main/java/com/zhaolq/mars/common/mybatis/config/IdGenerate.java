package com.zhaolq.mars.common.mybatis.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.generator.SnowflakeGenerator;
import cn.hutool.core.util.IdUtil;
import io.mybatis.provider.EntityColumn;
import io.mybatis.provider.EntityTable;
import io.mybatis.provider.keysql.GenId;

/**
 * ID生成器
 *
 * @Author zhaolq
 * @Date 2024/6/16 20:01
 */
public class IdGenerate implements GenId {
    @Override
    public Object genId(EntityTable table, EntityColumn column) {
        return IdUtil.getSnowflake().nextIdStr();
    }
}
