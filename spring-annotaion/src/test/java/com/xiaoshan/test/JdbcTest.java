package com.xiaoshan.test;

import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author YushanZhao
 * @Date:2018/7/17
 */
public class JdbcTest {

    @Test
    public void testJdbc() throws ClassNotFoundException,SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql:///test", "root","root");
        PreparedStatement preparedStatement = null;

        //设置手动提交
        conn.setAutoCommit(false);
        try {
            String sql = "insert into product (product_name, product_inventory) values (?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "iphone X");
            preparedStatement.setInt(2, 2);

            preparedStatement.executeUpdate();

            conn.commit();
        } catch (SQLException e){
            conn.rollback();
        } finally {
            preparedStatement.close();
            conn.close();
        }

    }


    /*@Test
    public void testJdbc()throws Exception{

        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql:///test", "root","root");

        String sql = "insert into product (product_name, product_inventory) values (?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1,"iphone X");
        preparedStatement.setInt(2,2);

        preparedStatement.executeUpdate();

        preparedStatement.close();
        conn.close();
    }*/


}
