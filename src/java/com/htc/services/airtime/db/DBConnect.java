/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htc.services.airtime.db;

import com.htc.services.airtime.common.Tool;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class DBConnect {

//    static String db_name = "air_time";
//    static String username = "air_time";
//    static String password = "air_time13456789";
//    static String localhost = "127.0.0.1:3306";
    
    static String db_name = "htc_payment";
    static String username = "htcpay";
    static String password = "Adsd)No3#kd";
    static String localhost = "10.58.98.53:3307";
    
    static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DBPool.class);

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://"+localhost+"/" + db_name + "?autoReconnect=true&useSSL=false&useUnicode=true&characterEncoding=utf-8";
//            username = "db_shop_java";
//            password = "db_shop_java13456789";
            conn = DriverManager.getConnection(connectionUrl, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
//       finally{
//            conn = DriverManager.close();
//        }
        return conn;
    }

    public static void freeConn(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error(Tool.getLogMessage(e));
        }
    }

    public static void freeConn(ResultSet rs, PreparedStatement pstm, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstm != null) {
                pstm.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(Tool.getLogMessage(e));
        }
    }

    public static void freeConn(PreparedStatement pstm, Connection conn) {
        try {

            if (pstm != null) {
                pstm.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(Tool.getLogMessage(e));
        }
    }

    public static void releadRsPstm(ResultSet rs, PreparedStatement pstm) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstm != null) {
                pstm.close();
            }
        } catch (Exception e) {
            logger.error(Tool.getLogMessage(e));
        }
    }

    public static void main(String[] args) {
        System.out.println("Day la phan ket noi DB");
        System.out.println(getConnection());
    }
}
