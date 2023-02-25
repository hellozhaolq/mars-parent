package com.zhaolq.mars.service.sys.runner;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

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
    public void run(ApplicationArguments args) {
        log.trace(">>>>>>>> TestRunner 开始 <<<<<<<<");
        conn();
        example1();
        example2();
        log.trace(">>>>>>>> TestRunner 结束 <<<<<<<<");
    }

    private void conn() {
        Connection conn1 = null;
        Connection conn2 = null;
        try {
            conn1 = Db.use(dataSource).getConnection();
            conn2 = Db.use(dataSource).getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        log.trace("\t ThreadLocal同一线程中多次获取的数据库连接相同: {}", conn1 == conn2);
        DbUtil.close(conn1, conn2);
    }

    private void example1() {
        String sql = "select t.* from MARS_SYS_USER t where t.id = ? and t.account = ?";
        int id = 1;
        String account = "admin";

        Entity entity = null;
        try {
            entity = Db.use(dataSource).queryOne(sql, id, account);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        log.trace("\t id为1的account={}", entity.get("account"));
    }

    /**
     * https://zh.wikipedia.org/wiki/Java%E6%95%B0%E6%8D%AE%E5%BA%93%E8%BF%9E%E6%8E%A5
     */
    private void example2() {
        String sql = "select t.* from MARS_SYS_USER t where t.id = ? and t.account = ?";
        int id = 1;
        String account = "admin";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, account);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id_ = rs.getInt("id");
                String account_ = rs.getString("account");
                log.trace("\t id={}; account={}", id_, account);
            }
        } catch (Exception e) {
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
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(dataSourceProperties.getDriverClassName());
        String url = dataSourceProperties.getUrl();
        String username = dataSourceProperties.getUsername();
        String password = dataSourceProperties.getPassword();
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    private Connection getConnection2() throws IOException, SQLException {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("database.properties"), StandardOpenOption.READ)) {
            props.load(in);
        }

        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null) {
            //  DriverManager类将尝试加载“jdbc.drivers”系统属性中引用的驱动程序类，详见DriverManager类注释。不必担心多数据源情况，因为它总能找到合适的驱动程序。
            System.setProperty("jdbc.drivers", drivers);
        }
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        return DriverManager.getConnection(url, username, password);
    }

    private void close(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException {
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
