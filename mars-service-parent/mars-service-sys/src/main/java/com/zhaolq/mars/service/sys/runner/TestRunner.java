package com.zhaolq.mars.service.sys.runner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.hutool.db.Db;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import lombok.extern.slf4j.Slf4j;

/**
 * 推荐：https://bbs.huaweicloud.com/blogs/184825
 * <p>
 * 执行时机：参考org.springframework.boot.SpringApplication.run()方法的源码
 *
 * @author zhaolq
 * @date 2021/5/20 16:34
 */
@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TestRunner implements ApplicationRunner {
    @Resource
    private DataSource dataSource;
    @Resource
    private DataSourceProperties dataSourceProperties;

    @Override
    public void run(ApplicationArguments args) throws SQLException {
        log.trace(">>>>>>>> TestRunner 开始 <<<<<<<<");
        conn();
        jdbc1();
        jdbc2();
        log.trace(">>>>>>>> TestRunner 结束 <<<<<<<<");
    }

    private void conn() throws SQLException {
        Connection conn1 = Db.use(dataSource).getConnection();
        Connection conn2 = Db.use(dataSource).getConnection();
        log.trace("\t ThreadLocal同一线程中多次获取的数据库连接相同: {}", conn1 == conn2);
        DbUtil.close(conn1, conn2);
    }

    private void jdbc1() throws SQLException {
        String id = "1";
        Entity entity = Db.use(dataSource).queryOne("select t.* from MARS_SYS_USER t where t.id = ?", id);
        log.trace("\t id为1的account={}", entity.get("account"));
    }

    /**
     * https://zh.wikipedia.org/wiki/Java%E6%95%B0%E6%8D%AE%E5%BA%93%E8%BF%9E%E6%8E%A5
     */
    private void jdbc2() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement("select t.* from MARS_SYS_USER t where t.id = ?");
            ps.setString(1, "1");
            rs = ps.executeQuery();
            while (rs.next()) {
                String account = rs.getString("account");
                log.trace("\t id为1的account={}", account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                close(rs, ps, conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获得数据库连接对象
     *
     * @return 返回java.sql.Connection接口类型
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private Connection getConnection() throws ClassNotFoundException,
            SQLException {
        Class.forName(dataSourceProperties.getDriverClassName());
        Connection conn = DriverManager.getConnection(dataSourceProperties.getUrl(), dataSourceProperties.getUsername(),
                dataSourceProperties.getPassword());
        return conn;
    }

    private void close(ResultSet rs, PreparedStatement ps, Connection conn)
            throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}
