/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htc.services.airtime.entity;

/**
 *
 * @author Admin
 */
public class ErrorMps {

    public static enum ERR_CODE {
        MPS_CHARGE_FAILED("01", "Charge Failed"),
        MPS_VALID_USER_PASS("02", "Invalid username or password"),
        MPS_SEND_OTP_FALSE("03", "Send otp false"),
        MPS_VALID_OTP_FALSE("05", "OTP validate false"),
        MPS_VALID_FALSE("06", "Validate false"),
        MPS_CALL_ERRORS("07", "Error when call ws MPS"),
        MPS_CALL_CHARGE_FALSE("08", "Call charge false"),
        MPS_USER_ALREADY_REG("12", "User already registered service"),
        MPS_REG_CONFLICT("13", "Register conflict with other services"),
        MPS_USER_NOT_REG("15", "User does not register services"),
        MPS_PENDING("16", "User already in pending status"),
        MPS_AUTH_FALURE("201", "AUTHENTICATION FAILURE"),
        MPS_PARAM_INVALID("300", "Invalid Parameter(s"),
        MPS_SERVER_BUSY("302", "Server too busy"),
        MPS_MOBILE_INVALID("410", "Invalid number mobile"),
        MPS_USSD_CONFIRM_FAILSE("417", "USSD confirm false or timeout"),
        NOT_CALL_MPS("111", "CO LOI GOI MPS"),
        ;
        public String val;
        public String mess;

        public String getJson() {
            return new ChargeResponse(val, mess).toJson();
        }

        private ERR_CODE(String val, String mess) {
            this.val = val;
            this.mess = mess;
        }
    }

    public static void main(String[] args) {
        System.out.println(ERR_CODE.MPS_USSD_CONFIRM_FAILSE.getJson());
    }

    public static String mapErrorMps(String result) {
        String rs = ERR_CODE.NOT_CALL_MPS.getJson();
        switch (result) {
           
            case "1":
                rs = ERR_CODE.MPS_CHARGE_FAILED.getJson();
                break;
            case "2":
                rs = ERR_CODE.MPS_VALID_USER_PASS.getJson();
                break;
            case "3":
                rs = ERR_CODE.MPS_SEND_OTP_FALSE.getJson();
                break;
            case "5":
                rs = ERR_CODE.MPS_VALID_OTP_FALSE.getJson();
                break;
            case "6":
                rs = ERR_CODE.MPS_VALID_FALSE.getJson();
                break;
            case "7":
                rs = ERR_CODE.MPS_CALL_ERRORS.getJson();
                break;
            case "8":
                rs = ERR_CODE.MPS_CALL_CHARGE_FALSE.getJson();
                break;
            case "12":
                rs = ERR_CODE.MPS_USER_ALREADY_REG.getJson();
                break;
            case "13":
                rs = ERR_CODE.MPS_REG_CONFLICT.getJson();
                break;
            case "15":
                rs = ERR_CODE.MPS_USER_NOT_REG.getJson();
                break;
            case "16":
                rs = ERR_CODE.MPS_PENDING.getJson();
                break;
            case "201":
                rs = ERR_CODE.MPS_AUTH_FALURE.getJson();
                break;
            case "300":
                rs = ERR_CODE.MPS_PARAM_INVALID.getJson();
                break;
            case "302":
                rs = ERR_CODE.MPS_SERVER_BUSY.getJson();
                break;
            case "410":
                rs = ERR_CODE.MPS_MOBILE_INVALID.getJson();
                break;
            case "417":
                rs = ERR_CODE.MPS_USSD_CONFIRM_FAILSE.getJson();
                break;
        }
        System.out.println("rs JSON"+rs);
        return rs;
    }
}
