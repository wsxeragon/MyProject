package cn.inphase.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class TestTransaction {
    /**
     * @param args
     */
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // 动态导入数据库的驱动
            Class.forName("com.mysql.jdbc.Driver");

            // 获取数据库链接
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true",
                    "root", "root");

            // 开启事务
            // 不把其设置为true之前都是一个当作一个事务来处理
            conn.setAutoCommit(false);

            // 创造SQL语句
            String sql = "INSERT INTO user ( id,name,age ) VALUES ( 1,'wsx',25 )";
            // int i = 1 / 0;
            // 执行SQL语句
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            // 提交事务
            conn.commit();

            System.out.println("OK!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("有错误！");

            try {
                // 回滚事务
                // 撤销上面对事务的所有操作哈！
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception e2) {
            }
        } finally {
            // 关闭Statement
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
            }
            // 关闭Connection
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            }
        }
    }
}
