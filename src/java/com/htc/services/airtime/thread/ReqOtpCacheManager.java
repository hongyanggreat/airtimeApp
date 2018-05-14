/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htc.services.airtime.thread;

import com.htc.services.airtime.common.DateProc;
import com.htc.services.airtime.common.Tool;
import com.htc.services.airtime.config.MyContext;
import com.htc.services.airtime.entity.RequestOtp;
import java.util.HashMap;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
public class ReqOtpCacheManager extends Thread {

    static final Logger logger = Logger.getLogger(ReqOtpCacheManager.class);
    private static final HashMap<String, RequestOtp> CACHE_REQUEST_OTP = new HashMap<>();
    protected static Object monitor;

    @SuppressWarnings("LeakingThisInConstructor")
    public ReqOtpCacheManager() {
        this.setName("ReqOtpCacheManager:" + DateProc.createTimestamp());
        monitor = this;
    }

    private HashMap<String, RequestOtp> cloneHash() {
        synchronized (monitor) {
            HashMap<String, RequestOtp> result = (HashMap<String, RequestOtp>) CACHE_REQUEST_OTP.clone();
            return result;
        }
    }

    public static RequestOtp get(String key) {
        synchronized (monitor) {
            return CACHE_REQUEST_OTP.get(key);
        }
    }

    public static void put(String key, RequestOtp mt) {
//        System.out.println("HAM ADD CACHE");
        synchronized (monitor) {
            CACHE_REQUEST_OTP.put(key, mt);
            monitor.notifyAll();
        }
    }

    public static void remove(String key) {
        synchronized (monitor) {
//            logger.debug("ConcatLongMT.removeLongMT:" + key);
            CACHE_REQUEST_OTP.remove(key);
        }
    }

    @Override
    public void run() {
        System.out.println("ReqOtpCacheManager Started:" + DateProc.createTimestamp());
        while (MyContext.running) {
            try {
//                System.out.println("LAM GI DAY");
//                System.out.println("DANG CHAY NGAM XOA CACHE REQUEST OTP KHI QUA THOI GIAN 15 PHUT");
                HashMap<String, RequestOtp> resultRequestOtp = cloneHash();
//                System.out.println("resultRequestOtp:" + resultRequestOtp);
                Set<String> keySets = resultRequestOtp.keySet();
//                if(DateProc.createDate("yyyy-MM-dd")){
//                    
//                }
                for (String oneKey : keySets) {
//                    System.out.println("oneKey:" + oneKey);
                    RequestOtp one = resultRequestOtp.get(oneKey);
                    long timeReq = System.currentTimeMillis() - one.getRequestTime();
//                    System.out.println("getRequestTime : " + one.getRequestTime());
//                    System.out.println("timeReq : " + timeReq);
                    if (timeReq > (((15 * 60) + 1) * 1000)) {
                        // do somthing
//                        System.out.println("XOA KEY CACHE : " + oneKey);
                        remove(oneKey);
                    } else {
//                        System.out.println("KHONG LAM GI CA");
                    }
                }
                Thread.sleep((1 * 1000));//1s
            } catch (InterruptedException ex) {
                System.out.println("BI LOI ROI ReqOtpCacheManager");
                logger.error(Tool.getLogMessage(ex));
            }
        }
    }

    public void stopThread() {
        this.interrupt();
    }
}
