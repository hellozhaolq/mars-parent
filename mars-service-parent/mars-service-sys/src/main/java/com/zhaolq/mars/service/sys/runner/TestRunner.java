package com.zhaolq.mars.service.sys.runner;

import cn.hutool.db.Db;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.json.JSONObject;
import com.zhaolq.mars.tool.core.setting.YamlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

/**
 * 推荐：https://bbs.huaweicloud.com/blogs/184825
 *
 * 执行时机：参考org.springframework.boot.SpringApplication.run()方法的源码
 *
 * @author zhaolq
 * @date 2021/5/20 16:34
 */
@Component
@Slf4j
@Order(1)
public class TestRunner implements ApplicationRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(ApplicationArguments args) throws SQLException {
        conn();
        log.info(">>>>>>>> TestRunner 开始 ...");
        jdbc1();
        jdbc2();
        log.info(">>>>>>>> TestRunner 结束 ...");
    }

    private void conn() throws SQLException {
        log.info(">>>>>>>> 多次获取数据库连接");
        Connection conn1 = Db.use(dataSource).getConnection();
        Connection conn2 = Db.use(dataSource).getConnection();
        log.info("\t ThreadLocal同一线程中多次获取的数据库连接相同");
        log.info("\t conn1：{}", conn1);
        log.info("\t conn2：{}", conn2);
        log.info("\t conn1==conn2：{}", conn1 == conn2);
        DbUtil.close(conn1);
        DbUtil.close(conn2);
    }

    private void jdbc1() throws SQLException {
        String id = "1";
        Entity entity = Db.use(dataSource).queryOne("select t.* from MARS_SYS_USER t where t.id = ?", id);
        log.info("\t id为1的account={}", entity.get("account"));
    }

    /**
     * https://zh.wikipedia.org/wiki/Java%E6%95%B0%E6%8D%AE%E5%BA%93%E8%BF%9E%E6%8E%A5
     */
    private void jdbc2() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = TestRunner.DbUtilCustom.getConnection();
            ps = conn.prepareStatement("select t.* from MARS_SYS_USER t where t.id = ?");
            ps.setString(1, "1");
            rs = ps.executeQuery();
            while (rs.next()) {
                String account = rs.getString("account");
                log.info("\t id为1的account={}", account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                TestRunner.DbUtilCustom.close(rs, ps, conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 数据库工具类
     */
    public static class DbUtilCustom {

        /**
         * 获得数据库连接对象
         *
         * @return 返回java.sql.Connection接口类型
         * @throws ClassNotFoundException
         * @throws SQLException
         */
        public static Connection getConnection() throws ClassNotFoundException,
                SQLException {

            // Props props = PropsUtils.get("db.properties", CharsetUtil.CHARSET_UTF_8);
            JSONObject jsonObject = YamlUtils.get("application.yml");
            String className = YamlUtils.getValueFromKey("spring.datasource.driver-class-name", jsonObject);
            String url = YamlUtils.getValueFromKey("spring.datasource.url", jsonObject);
            String username = YamlUtils.getValueFromKey("spring.datasource.username", jsonObject);
            String password = YamlUtils.getValueFromKey("spring.datasource.password", jsonObject);

            Class.forName(className);
            Connection conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(false);
            return conn;
        }

        /**
         * 关闭与数据库相关的对象
         *
         * @param rs
         *            结果集
         * @param ps
         *            准备语句对象
         * @param conn
         *            连接对象
         * @throws SQLException
         */
        public static void close(ResultSet rs, PreparedStatement ps, Connection conn)
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

}
