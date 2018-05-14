/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htc.services.airtime.common;

import java.util.LinkedHashMap;

/**
 *
 * @author Admin
 */
public class SubServices {

    public static String getSubService(String price) {
        String rs = "";
//        rs = price;
        if (Tool.checkNull(price)) {
            return rs;
        }
        LinkedHashMap<String, String> arrSubService = new LinkedHashMap<>();

        String[] prices = {"5", "15", "30", "50", "100", "200", "300", "500"};
        for (int i = 0; i < prices.length; i++) {
            arrSubService.put(prices[i]+"000", "GOLD_VLINE_" + prices[i] + "K");
//                        System.out.println("GIA TRI "+i+" Là :"+prices[i]);
        }
//        arrSubService.put("5000", "GOLD_VLINE_5K");
//        arrSubService.put("15000", "GOLD_VLINE_15K");
//        arrSubService.put("30000", "GOLD_VLINE_30K");
//        arrSubService.put("50000", "GOLD_VLINE_50K");
//        arrSubService.put("100000", "GOLD_VLINE_100K");
//        arrSubService.put("200000", "GOLD_VLINE_200K");
//        arrSubService.put("300000", "GOLD_VLINE_300K");
//        arrSubService.put("500000", "GOLD_VLINE_500K");
//        arrSubService.forEach((k,v) -> System.out.println("key: "+k+" =>  value:"+v));
        rs = arrSubService.get(price);
        return rs;
    }
}
