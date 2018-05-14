/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htc.services.airtime.thread;

import com.htc.services.airtime.common.DateProc;
import com.htc.services.airtime.common.Tool;
import com.htc.services.airtime.config.MyContext;
import com.htc.services.airtime.dao.Account;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
public class AccountCacheManager extends Thread {

    static final Logger logger = Logger.getLogger(AccountCacheManager.class);
    private static final HashMap<String, Account> CACHE_ACCOUT_SERVICE = new HashMap<>();
    protected static Object monitor;

    @SuppressWarnings("LeakingThisInConstructor")
    public AccountCacheManager() {
        this.setName("AccoutCacheManager:" + DateProc.createTimestamp());
        monitor = this;
    }

    private HashMap<String, Account> cloneHash() {
        synchronized (monitor) {
            HashMap<String, Account> result = (HashMap<String, Account>) CACHE_ACCOUT_SERVICE.clone();
            return result;
        }
    }

    public static Account get(String key) {
        synchronized (monitor) {
            return CACHE_ACCOUT_SERVICE.get(key);
        }
    }

    public static void put(String key, Account mt) {
//        System.out.println("HAM ADD CACHE");
        synchronized (monitor) {
            CACHE_ACCOUT_SERVICE.put(key, mt);
            monitor.notifyAll();
        }
    }

    public static void remove(String key) {
        synchronized (monitor) {
//            logger.debug("ConcatLongMT.removeLongMT:" + key);
            CACHE_ACCOUT_SERVICE.remove(key);
        }
    }

    @Override
    public void run() {
        System.out.println("AccoutCacheManager Started:" + DateProc.createTimestamp());
//        while (MyContext.running) {
//            try {
//                System.out.println("THUC HIEN TAO CACH LAN DAU CHO ACC_SERVICE");
//                int serviceType = 4;
//                ArrayList list = Account.getUserService(serviceType);
//                Thread.sleep(5000);
//            } catch (Exception ex) {
//                System.out.println("BI LOI ROI AccoutCacheManager");
//                logger.error(Tool.getLogMessage(ex));
//            }
//        }

    }

    public void stopThread() {
        this.interrupt();
    }
}
