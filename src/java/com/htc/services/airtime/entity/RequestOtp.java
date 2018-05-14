/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htc.services.airtime.entity;

import com.htc.services.airtime.dao.AirTime;
import com.htc.services.airtime.thread.ReqOtpCacheManager;
import java.util.HashMap;

/**
 *
 * @author Admin
 */
public class RequestOtp {

//    private static final HashMap<String, RequestOtp> CACHE_REQUEST_OTP = new HashMap<>();
    int count;
    long requestTime;

    public RequestOtp() {
        
    }

    public static void createCacheReqOtp(String keycacheReqOtp) {
        ReqOtpCacheManager reqOtpCacheManager = new ReqOtpCacheManager();
        RequestOtp checkReq = reqOtpCacheManager.get(keycacheReqOtp);
        int count = 1;
        long timeReq = System.currentTimeMillis();
        if (checkReq != null) {
           count = checkReq.getCount() + 1;
        }
        RequestOtp checkRequest = new RequestOtp();
        checkRequest.setCount(count);
        checkRequest.setRequestTime(timeReq);
        reqOtpCacheManager.put(keycacheReqOtp, checkRequest);
    }
    public static void removeCacheReqOtp(String keycacheReqOtp) {
        ReqOtpCacheManager reqOtpCacheManager = new ReqOtpCacheManager();
        reqOtpCacheManager.remove(keycacheReqOtp);
    }

    public static int checkReqOtp(String keycacheReqOtp, String msisdn) {
        boolean rs = true;
        ReqOtpCacheManager reqOtpCacheManager = new ReqOtpCacheManager();
        RequestOtp checkNumReq = reqOtpCacheManager.get(keycacheReqOtp);
        int count = 0;
        if (checkNumReq == null) {
            //KIEM TRA DU LIEU TRONG DB
            AirTime dt = AirTime.getByMsisdn(msisdn);
            if (dt != null) {
//                Timestamp timeRequest = dt.getREQUEST_TIME();
                count = AirTime.countRequestOtpPerDay(dt);
            } else {
                count = AirTime.countRequestOtp(msisdn);
            }
        } else {
            count = checkNumReq.getCount();
        }
        System.out.println("SO LAN LUU CACHE REQ OTP MA BAN DANG TON DONG:"+count);
        return count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
    }

}
