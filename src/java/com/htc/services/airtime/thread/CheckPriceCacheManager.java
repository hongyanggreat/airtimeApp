/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htc.services.airtime.thread;

import com.htc.services.airtime.common.DateProc;
import com.htc.services.airtime.common.Tool;
import com.htc.services.airtime.config.MyContext;
import com.htc.services.airtime.entity.CheckPrice;
import java.util.HashMap;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
public class CheckPriceCacheManager extends Thread {

    static final Logger logger = Logger.getLogger(CheckPriceCacheManager.class);
    private static final HashMap<String, CheckPrice> CACHE_PRICE_REQUEST = new HashMap<>();
    protected static Object monitor;
    private static boolean CLEANER = false;

    @SuppressWarnings("LeakingThisInConstructor")
    public CheckPriceCacheManager() {
        this.setName("CheckPriceCacheManager:" + DateProc.createTimestamp());
        monitor = this;
    }

    private HashMap<String, CheckPrice> cloneHash() {
        synchronized (monitor) {
            HashMap<String, CheckPrice> result = (HashMap<String, CheckPrice>) CACHE_PRICE_REQUEST.clone();
            return result;
        }
    }

    public static CheckPrice get(String key) {
        synchronized (monitor) {
            return CACHE_PRICE_REQUEST.get(key);
        }
    }

    public static void put(String key, CheckPrice mt) {
//        System.out.println("HAM ADD CACHE");
        synchronized (monitor) {
            CACHE_PRICE_REQUEST.put(key, mt);
            monitor.notifyAll();
        }
    }

    public static void remove(String key) {
        synchronized (monitor) {
//            logger.debug("ConcatLongMT.removeLongMT:" + key);
            CACHE_PRICE_REQUEST.remove(key);
        }
    }

    @Override
    public void run() {
        System.out.println("CheckPriceCacheManager Started:" + DateProc.createTimestamp());
        double hm = DateProc.getTimer();
        while (MyContext.running) {
            try {
                if (hm <= 0.01) {
                    CLEANER = false;
                } else if (hm > 0.01 && !CLEANER) {
//                    System.out.println("LAM GI DAY");
//                System.out.println("DANG CHAY NGAM XOA CACHE TOTAL_AMOUNT KHI HET NGAY");
                    HashMap<String, CheckPrice> resultCheckPrice = cloneHash();
//                System.out.println("resultCheckPrice:" + resultCheckPrice);
                    Set<String> keySets = resultCheckPrice.keySet();
//                System.out.println("time: " + time);
                    for (String oneKey : keySets) {
                        remove(oneKey);
                    }
                    logger.info("Clean Cache CheckPriceCacheManager: " + DateProc.createTimestamp());
                    CLEANER = true;
                    Thread.sleep((2 * 1000));// 0.5s
                }
                hm = DateProc.getTimer();
            } catch (InterruptedException ex) {
                System.out.println("BI LOI ROI CheckPriceCacheManager");
                logger.error(Tool.getLogMessage(ex));
            }
        }
    }

    public void stopThread() {
        this.interrupt();
    }
}
