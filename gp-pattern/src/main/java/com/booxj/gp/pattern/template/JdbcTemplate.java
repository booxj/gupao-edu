package com.booxj.gp.pattern.template;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * jdbc模板类
 */
public class JdbcTemplate {

    private static String url = "jdbc:mysql://47.110.42.93/test";
    private static String user = "root";
    private static String password = "123456";

    public JdbcTemplate() {
    }

    public List<?> executeQuery(String sql, RowMapper<?> rowMapper, Object[] values) throws Exception {
        //1、获取连接
        Connection conn = this.getConnection();

        //2、创建语句集
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //3、执行语句集，并且获得结果集
        ResultSet rs = this.executeQuery(pstmt, values);

        //4、解析语句集
        List<?> result = this.parseResultSet(rs, rowMapper);

        //5、关闭结果集
        this.closeResultSet(rs);

        //6、关闭语句集
        this.closeStatement(pstmt);

        //7、关闭连接
        this.closeConnection(conn);

        return result;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private ResultSet executeQuery(PreparedStatement pstmt, Object[] values) throws SQLException {
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                // parameterIndex 从1开始
                pstmt.setObject(i + 1, values[i]);
            }
        }

        return pstmt.executeQuery();
    }

    private List<?> parseResultSet(ResultSet rs, RowMapper<?> rowMapper) throws Exception {
        List<Object> list = new ArrayList<>();
        while (rs.next()) {
            list.add(rowMapper.mapRow(rs));
        }
        return list;
    }

    private void closeStatement(Statement stmt) throws Exception {
        stmt.close();
    }

    private void closeResultSet(ResultSet rs) throws Exception {
        rs.close();
    }

    private void closeConnection(Connection conn) throws Exception {
        conn.close();
    }

}
