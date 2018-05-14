/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htc.services.airtime.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Admin
 */
public class PhoneValid {
//                Số Viettel
//            '086'  =>'Viettel';
//            '096'  =>'Viettel';
//            '097'  =>'Viettel';
//            '098'  =>'Viettel';
//            '0161' =>'Viettel';
//            '0162' =>'Viettel';
//            '0163' =>'Viettel';
//            '0164' =>'Viettel';
//            '0165' =>'Viettel';
//            '0166' =>'Viettel';
//            '0167' =>'Viettel';
//            '0168' =>'Viettel';
//            '0169' =>'Viettel';

//    public static void main(String[] args) {
//        String phone = "84964933669";
//        String phone = "841668010046";
//        System.out.println("phone : "+phone);
//        System.out.println("VALID : "+validPhoneViettel84(phone));
//
//    }

    public static boolean validPhoneViettel84(String phone) {
//        String regex = "^(\\+849\\d{8})|(849\\d{8})|(09\\d{8})|(\\+841\\d{9})|(841\\d{9})|(01\\d{9})|(\\+848\\d{8})|(848\\d{8})|(08\\d{8})$";
        String regex = "^(\\"
                + "(8486\\d{7})|"
                + "(8496\\d{7})|"
                + "(8497\\d{7})|"
                + "(8498\\d{7})|"
                + "(84161\\d{7})|"
                + "(84162\\d{7})|"
                + "(84163\\d{7})|"
                + "(84164\\d{7})|"
                + "(84165\\d{7})|"
                + "(84166\\d{7})|"
                + "(84167\\d{7})|"
                + "(84168\\d{7})|"
                + "(84169\\d{7})"
                + "$";
        // Create a Pattern object
        Pattern pattern = Pattern.compile(regex);
        // Now create matcher object.
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static String PhoneTo09(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.equals("")) {
            return "";
        }
        try {
            if (phoneNumber.startsWith("84") && phoneNumber.length() > 2) {
                phoneNumber = "0" + phoneNumber.substring(2).toString();
            } else if (phoneNumber.startsWith("+84") && phoneNumber.length() > 3) {
                phoneNumber = "0" + phoneNumber.substring(3).toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return phoneNumber;
    }

    public static String PhoneTo84(String number) {
        if (number == null) {
            number = "";
            return number;
        }
        number = number.replaceAll("o", "0");
        if (number.startsWith("84")) {
            return number;
        } else if (number.startsWith("0")) {
            number = "84" + number.substring(1);
        } else if (number.startsWith("+84")) {
            number = number.substring(1);
        } else {
            number = "84" + number;
        }
        return number;
    }

    public static boolean checkVietel(String userId) {
        return userId.startsWith("+8498") || userId.startsWith("8498") || userId.startsWith("098") || userId.startsWith("98")
                || userId.startsWith("+8497") || userId.startsWith("8497") || userId.startsWith("097") || userId.startsWith("97")
                || userId.startsWith("+84160") || userId.startsWith("84160") || userId.startsWith("0160") || userId.startsWith("160")
                || userId.startsWith("+84161") || userId.startsWith("84161") || userId.startsWith("0161") || userId.startsWith("161")
                || userId.startsWith("+84162") || userId.startsWith("84162") || userId.startsWith("0162") || userId.startsWith("162")
                || userId.startsWith("+84163") || userId.startsWith("84163") || userId.startsWith("0163") || userId.startsWith("163")
                || userId.startsWith("+84164") || userId.startsWith("84164") || userId.startsWith("0164") || userId.startsWith("164")
                || userId.startsWith("+84165") || userId.startsWith("84165") || userId.startsWith("0165") || userId.startsWith("165")
                || userId.startsWith("+84166") || userId.startsWith("84166") || userId.startsWith("0166") || userId.startsWith("166")
                || userId.startsWith("+84167") || userId.startsWith("84167") || userId.startsWith("0167") || userId.startsWith("167")
                || userId.startsWith("+84168") || userId.startsWith("84168") || userId.startsWith("0168") || userId.startsWith("168")
                || userId.startsWith("+84169") || userId.startsWith("84169") || userId.startsWith("0169") || userId.startsWith("169")
                || userId.startsWith("+8486") || userId.startsWith("8486") || userId.startsWith("086") || userId.startsWith("86") // NEW
                ;
    }
}
