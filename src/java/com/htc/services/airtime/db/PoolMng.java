/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htc.services.airtime.db;

import com.htc.services.airtime.common.Tool;
import com.htc.services.airtime.config.MyContext;
import java.io.File;
import org.apache.log4j.Logger;
import snaq.db.ConnectionPool;
import snaq.db.ConnectionPoolManager;

/**
 *
 * @author TUANPLA
 */
public class PoolMng {

    static final Logger logger = Logger.getLogger(PoolMng.class);
    static ConnectionPoolManager connPoolMng = null;

    public static void CreatePool() {
        try {
            File fcf = new File(MyContext.configDir + "/dbpool.properties");
            Tool.debug(MyContext.configDir + "/dbpool.properties");
            connPoolMng = ConnectionPoolManager.getInstance(fcf, "UTF-8");
            connPoolMng.registerShutdownHook();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(Tool.getLogMessage(e));
        }
    }

    public static ConnectionPool getConnPool(String poolName) {
        return connPoolMng.getPool(poolName);
    }

    public static void release() {
        connPoolMng.release();
    }
}
