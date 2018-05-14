/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htc.services.airtime.thread;

import com.htc.services.airtime.common.DateProc;
import com.htc.services.airtime.common.Tool;
import com.htc.services.airtime.config.Monitor;
import com.htc.services.airtime.config.MyContext;
import com.htc.services.airtime.entity.ChargingParams;
import java.util.HashMap;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author Company
 */
public class ReqCacheManager extends Thread {

    static final Logger logger = Logger.getLogger(ReqCacheManager.class);
    private static final HashMap<String, ChargingParams> CACHE_REQUEST = new HashMap<>();

    protected static Object monitor;

    @SuppressWarnings("LeakingThisInConstructor")
    public ReqCacheManager() {
        this.setName("ReqCacheManager:" + DateProc.createTimestamp());
        monitor = this;
    }

    private HashMap<String, ChargingParams> cloneHash() {
        synchronized (monitor) {
            HashMap<String, ChargingParams> result = (HashMap<String, ChargingParams>) CACHE_REQUEST.clone();
            return result;
        }
    }

    public static ChargingParams get(String key) {
        synchronized (monitor) {
            return CACHE_REQUEST.get(key);
        }
    }

    public static void put(String key, ChargingParams mt) {
//        System.out.println("HAM ADD CACHE");
        synchronized (monitor) {
            CACHE_REQUEST.put(key, mt);
            monitor.notifyAll();
        }
    }

    public static void remove(String key) {
        synchronized (monitor) {
//            logger.debug("ConcatLongMT.removeLongMT:" + key);
            CACHE_REQUEST.remove(key);
        }
    }

    @Override
    public void run() {
        System.out.println("ReqCacheManager Start:" + DateProc.createTimestamp());
        while (MyContext.running) {
            try {
//                System.out.println("LAM GI DAY");
                HashMap<String, ChargingParams> resultChargingParams = cloneHash();
//                System.out.println("resultChargingParams:" + resultChargingParams);
                Set<String> keySets = resultChargingParams.keySet();
                for (String oneKey : keySets) {
//                    System.out.println("oneKey:" + oneKey);

                    ChargingParams one = resultChargingParams.get(oneKey);
                    long timeReq = System.currentTimeMillis() - one.getStartTime();
//                    System.out.println("currentTimeMillis : " + System.currentTimeMillis());
//                    System.out.println("getStartTime : " + one.getStartTime());
//                    System.out.println("timeReq : " + timeReq);
                    if (timeReq > (((2 * 60) + 1) * 1000)) {
                        // do somthing
//                        System.out.println("XOA KEY CACHE : " + oneKey);
                        remove(oneKey);
                    } else {
//                        System.out.println("KHONG LAM GI CA");
                    }
                }
                Thread.sleep((1 * 1000));//1s
            } catch (InterruptedException ex) {
                System.out.println("BI LOI ROI ReqCacheManager");
                logger.error(Tool.getLogMessage(ex));
            }
        }
    }

    public void stopThread() {
        this.interrupt();
    }

    public void test() {
        System.out.println("A:" + DateProc.createTimestamp());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            logger.error(Tool.getLogMessage(ex));
        }
    }
}
