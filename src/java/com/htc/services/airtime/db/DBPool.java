package com.htc.services.airtime.db;

import com.htc.services.airtime.common.Tool;
import java.sql.*;
import org.apache.log4j.Logger;
import snaq.db.ConnectionPool;

public class DBPool {

    static Logger logger = Logger.getLogger(DBPool.class);
    //=============================Connection Pool------------------------------------------
    private static final String DB_POOL_NAME = "air_time";
    static ConnectionPool dbpool;

    private static void CreatePool() {
        dbpool = PoolMng.getConnPool(DB_POOL_NAME);
        if (dbpool == null) {
            Tool.debug("=============> [" + DB_POOL_NAME + "] IS NULL ?????");
        }
    }

    static {
        CreatePool();
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = dbpool.getConnection();
        } catch (Exception e) {
            System.out.println("---------------->>> get connection error !  <<<------------- ");
            e.printStackTrace();
        }
        return conn;
    }

    public static void release() {
        dbpool.release();
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
        }
    }

    public static int size() {
        return dbpool.getSize();
    }
}
