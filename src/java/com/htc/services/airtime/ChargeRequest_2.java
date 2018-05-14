package com.htc.services.airtime;

import com.htc.services.airtime.entity.ChargingParams;
import com.htc.services.airtime.client.SOAPClientService;
import com.htc.services.airtime.common.DateProc;
import com.htc.services.airtime.common.PhoneValid;
import com.htc.services.airtime.common.Tool;
import com.htc.services.airtime.config.MyConfig;
import com.htc.services.airtime.dao.AirTime;
import com.htc.services.airtime.dao.Account;
import com.htc.services.airtime.entity.ChargeResponse;
import com.htc.services.airtime.entity.RequestOtp;
import com.htc.services.airtime.entity.CheckPrice;
import com.htc.services.airtime.entity.ErrorMps;
import com.htc.services.airtime.entity.HttpRequest;
//import com.htc.services.airtime.entity.RequestOtp;
//import com.htc.services.airtime.entity.TotalRequestOtp;
import com.htc.services.airtime.thread.ReqCacheManager;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Admin
 */
public class ChargeRequest_2 {

    //KHAI BAO BIEN DE TEST
//    private static final HashMap<String, ChargingParams> ReqCacheManager = new HashMap<>();
    static final Logger logger = Logger.getLogger(ChargeRequest_2.class);

    public static enum ERR_CODE {

        SUCCESS_CHARGING("00", "Charging Success"),
        DATA_JSON_NOT_VALID("99", "Da ta khong dung dinh danh Json!"),
        OTP_NOTNULL("97", "OTP not empty"),
        AUTHEN_FAIL("94", "Sai user pass hoặc Tài khoản chưa kích hoạt"),
        REQUESTID_EMPTY("93", "Reqeuest ID is empty"),
        PHONE_NOT_VALID("92", "Phone not valid"),
        IP_NOT_ALLOW("90", "IP khong cho phep"),
        DISCONNECT_MPS("85", "Yêu cầu bị mất kết nối"),
        UNKNOW_EXCEPTION("84", "System Busy"),
        CHARGE_NOT_VALID_OR_EXPRIRE("83", "Request Exprire | Request Charging invalid CprequestId"),
        CP_REQ_NOT_VALID("81", "CpReq không hợp lệ"),
        PHONE_NOTNULL("80", "Phone not null"),
        PRICE_NOTNULL("79", "Price not null"),
        CP_CODE_FAIL("78", "Cp code không khớp với tài khoản"),;

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

    }

    public static String charge(HttpServletRequest requestData) {
        String request = requestData.getParameter("data");
        String ipAddress = "";
        ipAddress = Tool.getClientIpAddr(requestData);
        System.out.println("DIA CHI TRUY CAP VAO SERVICE : " + ipAddress);
        ReqCacheManager reqCacheManager = new ReqCacheManager();

//        System.out.println("CODE DUOC THUC THI O SV1");
//        System.out.println("CODE DUOC THUC THI O SV2");
        System.out.println("CODE DUOC THUC THI O LOCAL");

        ChargingParams chParam = ChargingParams.str2Obj(request);

        String result_mps = "-1";

        if (chParam != null) {
            //Kiem tra nếu các tham số valid thì thực hiện gọi sang MPS
            try {
                //GAN CAC GIA TRI MOI VAO
                chParam.setCommand(MyConfig.COMMAND);
                chParam.setCategory(MyConfig.CATEGORY);
                chParam.setParams(MyConfig.PARAM);
                chParam.setContent(MyConfig.CONTENT);
                chParam.setIp(MyConfig.IP);
                chParam.setStartTime(System.currentTimeMillis());
                chParam.setChargetime(DateProc.getyyyyMMddhh24missNoSpace());

                Account acc = Account.checkLogin(chParam.getUser(), chParam.getAccesskey(), chParam.getCpGame());

                if (acc == null) {
                    System.out.println("GHI LOG FILE : TH TAI KHOAN CP not valid");
                    doAddRequest(request, chParam, result_mps, ERR_CODE.AUTHEN_FAIL.getJson());
                    return ERR_CODE.AUTHEN_FAIL.getJson();
                }
                //CHECK CP_CODE
                String cpCode = "";
                if (!Tool.checkNull(acc.getCpCode())) {
                    cpCode = acc.getCpCode();
                }
                if (!cpCode.equals(chParam.getCpCode())) {
                    System.out.println("GHI LOG FILE : TH TAI KHOAN KHONG TON TAI CP_CODE");
                    doAddRequest(request, chParam, result_mps, ERR_CODE.CP_CODE_FAIL.getJson());
                    return ERR_CODE.CP_CODE_FAIL.getJson();
                }
                //CHECK DIA CHI IP
                ipAddress = Tool.getClientIpAddr(requestData).trim();

                String ipAllow = "";
                if (!Tool.checkNull(acc.getIpAllow())) {
                    ipAllow = acc.getIpAllow();
                }
                if (!acc.validIP(ipAddress, ipAllow)) {
                    System.out.println("GHI LOG FILE : TH ipRequest not valid");
                    doAddRequest(request, chParam, result_mps, ERR_CODE.IP_NOT_ALLOW.getJson());
                    return ERR_CODE.IP_NOT_ALLOW.getJson();
                }
                if (Tool.checkNull(chParam.getCpRequestId())) {
                    System.out.println("GHI LOG FILE : TH getCpRequestId null");
                    doAddRequest(request, chParam, result_mps, ERR_CODE.REQUESTID_EMPTY.getJson());
                    return ERR_CODE.REQUESTID_EMPTY.getJson();
                }
                if (Tool.checkNull(chParam.getMsisdn())) {
                    System.out.println("GHI LOG FILE : TH getMsisdn null");
                    doAddRequest(request, chParam, result_mps, ERR_CODE.PHONE_NOTNULL.getJson());
                    return ERR_CODE.PHONE_NOTNULL.getJson();
                }
                if (!PhoneValid.validPhoneViettel84(chParam.getMsisdn())) {
                    System.out.println("GHI LOG FILE : TH PHONE NOT VALID");
                    doAddRequest(request, chParam, result_mps, ERR_CODE.PHONE_NOT_VALID.getJson());
                    return ERR_CODE.PHONE_NOT_VALID.getJson();
                    // Valid phone 
                }
                if (Tool.checkNull(chParam.getOtp())) {
                    System.out.println("GHI LOG FILE : TH getOtp null");
                    doAddRequest(request, chParam, result_mps, ERR_CODE.OTP_NOTNULL.getJson());
                    return ERR_CODE.OTP_NOTNULL.getJson();
                }

                //TAO KEY CACHE 
                String keycache = chParam.getMsisdn();
                String keycachePrice = "price_" + chParam.getMsisdn();
                String keycacheReqOtp = "reqOtp_" + chParam.getMsisdn();
                String keycacheReqCharge = "reqCharge_" + chParam.getMsisdn();

                ChargingParams chParamCache = ReqCacheManager.get(keycache);
                if (chParamCache == null) {
                    // 1. Request Exprire
                    // 2. Request Charging invalid CprequestId
                    System.out.println("GHI LOG FILE  TH:KHONG TON TAI CACHE");
                    doAddRequest(request, chParam, result_mps, ERR_CODE.CHARGE_NOT_VALID_OR_EXPRIRE.getJson());
                    return ERR_CODE.CHARGE_NOT_VALID_OR_EXPRIRE.getJson();
                } else {
                    System.out.println("DA CO DU LIEU TU REQUEST TRUOC: chParamCache");
                    chParamCache.setOtp(chParam.getOtp());
                    //HAM DEBUG
                    chParamCache.debugValue();
                    //KIEM TA XEM CO TRUNG CP_REQ_ID khong
                    String cpRequestId = chParam.getCpRequestId();
                    String cpRequestIdCache = chParamCache.getCpRequestId();

                    if (!cpRequestId.equals(cpRequestIdCache)) {
                        System.out.println("cpRequestId KHONG HOP LE");
                        doAddRequest(request, chParamCache, result_mps, ERR_CODE.CP_REQ_NOT_VALID.getJson());
                        return ERR_CODE.CP_REQ_NOT_VALID.getJson();
                    }
                    // YEU CAU CHARGE
//                    result_mps = HttpRequest.sendPOST();

                    result_mps = doMpschargeRequest(
                            chParamCache.getSysRequestId(), // sysRequestId
                            chParamCache.getMsisdn(), // msisdn
                            MyConfig.COMMAND, // Command 
                            MyConfig.CATEGORY, // category
                            MyConfig.PARAM, // params
                            chParamCache.getChargetime(), // chargetime,
                            chParamCache.getSubService(),//subService, 
                            chParamCache.getPrice(), // Price
                            MyConfig.CONTENT,// content
                            MyConfig.IP,// IP
                            chParamCache.getData(),// data, 
                            chParamCache.getOtp());
                    
                    if (result_mps.equals("-1")) {

                        System.out.println("GHI LOG FILE : TH KHONG LAY DUOC KET QUA TU MPS");
                        doAddRequest(request, chParam, result_mps, ERR_CODE.DISCONNECT_MPS.getJson());
                        return ERR_CODE.DISCONNECT_MPS.getJson();
                    }
                    if (result_mps.equals("0")) {
                        ReqCacheManager.remove(keycache);
//                             XOA CACHE REQ_OTP NEU GOI DEN MPS CHARGE THANH CONG
                        RequestOtp.removeCacheReqOtp(keycacheReqOtp);
                        System.out.println("CHARG THANH CONG THI CACHE PRICE");
                        CheckPrice.createCacheTotalAmount(keycachePrice, chParamCache.getMsisdn(), chParamCache.getPrice());
                        System.out.println("GHI LOG FILE & DB : TH MPS CHARGE THANH CONG");
                        doAddResponse(request, chParamCache, result_mps, ERR_CODE.SUCCESS_CHARGING.getJson());
                        return ERR_CODE.SUCCESS_CHARGING.getJson();
                    } else {
                        System.out.println("GHI LOG FILE  : TH MPS CHARGE THAT BAI");
                        String mapErrorMps = ErrorMps.mapErrorMps(result_mps);
                        doAddResponse(request, chParamCache, result_mps, mapErrorMps);
                        return mapErrorMps;
                    }
                }

            } catch (Exception e) {
                System.out.println("CO LOI GI DAY ---------------------");
                System.out.println(e.getMessage());
                System.out.println("CO LOI GI DAY ---------------------");
                logger.error(Tool.getLogMessage(e));
                return ERR_CODE.UNKNOW_EXCEPTION.getJson();
            }
        } else {
            return ERR_CODE.DATA_JSON_NOT_VALID.getJson(); // Cho nay phai sua lai
        }
//        return result;
    }

    public static boolean doAddRequest(String request, ChargingParams chParam, String rs_mps, String rs_ahp) {
        boolean rs = false;
        AirTime airTimeRequest = new AirTime();

        airTimeRequest.setSysRequestId(chParam.getSysRequestId());
        airTimeRequest.setCpRequestId(chParam.getCpRequestId());
        airTimeRequest.setCpCode(chParam.getCpCode());
        airTimeRequest.setGameCode(chParam.getCpGame());
        airTimeRequest.setUserName(chParam.getUser());
        airTimeRequest.setMsisdn(chParam.getMsisdn());
        airTimeRequest.setPrice(Integer.parseInt(chParam.getPrice()));
        airTimeRequest.setRsAhp(rs_ahp);
        airTimeRequest.setRsMps(rs_mps);
        airTimeRequest.setOtp(chParam.getOtp());
        airTimeRequest.setChargeTime(chParam.getChargetime());
        airTimeRequest.setSubService(chParam.getSubService());
        airTimeRequest.setOtherInfo(request);

        rs = airTimeRequest.addRequest(airTimeRequest);

        if (rs) {
            System.out.println("LUU THANH CONG");
            rs = true;
        } else {
            System.out.println("LUU THAT BAI");
        }
        return rs;
    }

    public static boolean doAddResponse(String request, ChargingParams chParam, String rs_mps, String rs_ahp) {
        boolean rs = false;
        AirTime airTimeRequest = new AirTime();

        airTimeRequest.setSysRequestId(chParam.getSysRequestId());
        airTimeRequest.setCpRequestId(chParam.getCpRequestId());
        airTimeRequest.setCpCode(chParam.getCpCode());
        airTimeRequest.setGameCode(chParam.getCpGame());
        airTimeRequest.setUserName(chParam.getUser());
        airTimeRequest.setMsisdn(chParam.getMsisdn());
        airTimeRequest.setPrice(Integer.parseInt(chParam.getPrice()));
        airTimeRequest.setRsAhp(rs_ahp);
        airTimeRequest.setRsMps(rs_mps);
        airTimeRequest.setOtp(chParam.getOtp());
        airTimeRequest.setChargeTime(chParam.getChargetime());
        airTimeRequest.setSubService(chParam.getSubService());
        airTimeRequest.setOtherInfo(request);

        rs = airTimeRequest.addResponse(airTimeRequest);

        if (rs) {
            System.out.println("LUU THANH CONG");
            rs = true;
        } else {
            System.out.println("LUU THAT BAI");
        }
        return rs;
    }

    public static String doMpschargeRequest(
            String sysRequestId,
            String msisdn,
            String command,
            String category,
            String params,
            String chargetime,
            String subService,
            String price,
            String content,
            String ip,
            String data,
            String otp) {
        return SOAPClientService.mpschargeRequest(sysRequestId, msisdn, MyConfig.USERNAME, MyConfig.PASSWORD,
                command, category, params, chargetime, subService, price, content, ip, data, otp);
    }
}
