package com.zhaolq.mars.service.admin.runner;

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

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * 执行时机：参考org.springframework.boot.SpringApplication.run()方法的源码
 *
 * @Author zhaolq
 * @Date 2021/5/20 16:34
 */
@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TestRunner implements ApplicationRunner {
    @Resource(name = "baseDataSource")
    private HikariDataSource baseDataSource;
    @Resource(name = "baseDataSourceProperties")
    private DataSourceProperties baseDataSourceProperties;

    @Override
    public void run(ApplicationArguments args) {
        example();
    }

    /**
     * https://zh.wikipedia.org/wiki/Java数据库连接
     */
    private void example() {
        String sql = "select t.* from T_BASE_USER t where t.id = ? and t.account = ?";
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
        Class.forName(baseDataSourceProperties.getDriverClassName());
        String url = baseDataSourceProperties.getUrl();
        String username = baseDataSourceProperties.getUsername();
        String password = baseDataSourceProperties.getPassword();
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    private Connection getConnection2() throws IOException, SQLException {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("database.properties"), StandardOpenOption.READ)) {
            props.load(in);
        }

        String driver = props.getProperty("jdbc.driver");
        if (driver != null) {
            //  DriverManager类将尝试加载“jdbc.driver”系统属性中引用的驱动程序类，详见DriverManager类注释。不必担心多数据源情况，因为它总能找到合适的驱动程序。
            System.setProperty("jdbc.driver", driver);
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
