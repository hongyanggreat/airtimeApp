/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htc.services.airtime.entity;

import com.htc.services.airtime.dao.AirTime;
import com.htc.services.airtime.thread.CheckPriceCacheManager;
import java.util.HashMap;

/**
 *
 * @author Admin
 */
public class CheckPrice {

//    private static final HashMap<String, CheckPrice> CACHE_PRICE_REQUEST = new HashMap<>();
    //KHAI BAO THUOC TINH
    int totalAmount;

    public CheckPrice() {

    }

    public static void createCacheTotalAmount(String keycachePrice, String msisdn, String priceReq) {
        
        CheckPriceCacheManager checkPriceCacheManager = new CheckPriceCacheManager();
        
        CheckPrice checkReq = checkPriceCacheManager.get(keycachePrice);
        int price = 0;
        price = Integer.parseInt(priceReq);
        int totalAmount = 0;
        int count = 0;
        int requestTime = 0;
        if (checkReq == null) {
            totalAmount = AirTime.totalAmount(msisdn);
        } else {
            totalAmount = checkReq.getTotalAmount() + price;
        }
        CheckPrice checkRequest = new CheckPrice();

        checkRequest.setTotalAmount(totalAmount);

        checkPriceCacheManager.put(keycachePrice, checkRequest);
    }

    public static boolean checkTotalAmount(String keycachePrice, String msisdn, String priceReq) {
        boolean rs = true;
        CheckPriceCacheManager checkPriceCacheManager = new CheckPriceCacheManager();
        CheckPrice checkReq = checkPriceCacheManager.get(keycachePrice);
        int price = 0;
        int totalAmount;
        price = Integer.parseInt(priceReq);
        if (checkReq == null) {
            //KIEM TRA DU LIEU TRONG DB
            totalAmount = AirTime.totalAmount(msisdn);
            System.out.println("totalAmount :" + (totalAmount));
            System.out.println("price:" + (price));
            System.out.println("totalAmount + price:" + (totalAmount + price));
            if ((totalAmount + price) > (500 * 1000)) {
                rs = false;
            } else {
                //LUU GIA TRI VAO CACHE TRUOC KHI ADD CACHE
                CheckPrice checkRequest = new CheckPrice();
                checkRequest.setTotalAmount(totalAmount);
                checkPriceCacheManager.put(keycachePrice, checkRequest);
            }
        } else {
            totalAmount = checkReq.getTotalAmount();
            System.out.println("CtotalAmount :" + (totalAmount));
            System.out.println("Cprice:" + (price));
            System.out.println("CtotalAmount + price:" + (totalAmount + price));
            if ((totalAmount + price) > (500 * 1000)) {
                rs = false;
            }
        }
        return rs;
    }

    public static boolean checkPrice(String priceReq) {
        boolean rs = true;
        int price = 0;
        price = Integer.parseInt(priceReq);
        if (price > (100 * 1000)) {
            rs = false;
        }
        return rs;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

}
