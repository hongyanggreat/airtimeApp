package com.htc.services.airtime;

import com.htc.services.airtime.entity.ChargingParams;
import com.htc.services.airtime.common.SubServices;
import com.htc.services.airtime.common.EncriptData;
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
import org.apache.commons.lang.math.NumberUtils;
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
public class AirTimeChargeRequest {

    //KHAI BAO BIEN DE TEST
//    private static final HashMap<String, ChargingParams> ReqCacheManager = new HashMap<>();
    static final Logger logger = Logger.getLogger(AirTimeChargeRequest.class);
    public static final String COMMAND = "DOWNLOAD";
    public static final String IP = "?";
    public static final String PARAM = "?";
    public static final String CATEGORY = " ";
    public static final String CONTENT = "?";

    public static enum ERR_CODE {

        SUCCESS_CHARGING("00", "Charging Success"),
        REQUIRE_OTP("04", "Require OTP"),
        DATA_JSON_NOT_VALID("99", "Da ta khong dung dinh danh Json!"),
        GET_RESULT_FAILD("98", "Không lấy được kết quả từ MPS"),
        OTP_NOTNULL("97", "OTP not empty"),
        OTP_NOTVALID("96", "Không lấy được kết quả từ MPS"),
        DATA_INPUT_NOT_VALID("95", "Không lấy được kết quả từ MPS"),
        AUTHEN_FAIL("94", "Sai user pass"),
        REQUESTID_EMPTY("93", "Reqeuest ID is empty"),
        PHONE_NOT_VALID("92", "Phone not valid"),
        PRICE_NOT_VALID("91", "Price not valid"),
        IP_NOT_ALLOW("90", "IP khong cho phep"),
        REQUEST_EXIST("89", "Request Exist"),
        UNKNOW_SERVICE("88", "Unknow Service"),
        REPEAT_OTP("87", "Yêu cầu Lấy mã OTP bị giới hạn"),
        TOTAL_AMOUNT_LIMIT("86", "Số tiền charging trong ngày <= 500k"),
        DISCONNECT_MPS("85", "Yêu cầu bị mất kết nối"),
        UNKNOW_EXCEPTION("84", "System Busy"),
        CHARGE_NOT_VALID_OR_EXPRIRE("83", "Request Exprire | Request Charging invalid CprequestId"),
        //NEW DEFINE
        AMOUNT_LIMIT("82", "Thực hiện giao dịch quá 100k/lần"),
        CP_REQ_NOT_VALID("81", "CpReq không hợp lệ"),
        PHONE_NOTNULL("80", "Phone not null"),
        PRICE_NOTNULL("79", "Price not null"),
        MAP_ERROR_MPS("555", "Không lấy được OTP"),;
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

    public static String changing(String request) {

        ReqCacheManager reqCacheManager = new ReqCacheManager();

        System.out.println("CODE DUOC THUC THI O SV1");
//        System.out.println("CODE DUOC THUC THI O SV2");
//        System.out.println("CODE DUOC THUC THI O LOCAL");

        ChargingParams chParam = ChargingParams.str2Obj(request);

        String result_mps = "-1";

        if (chParam != null) {
            //Kiem tra nếu các tham số valid thì thực hiện gọi sang MPS
            try {
                //GAN CAC GIA TRI MOI VAO
                chParam.setCommand(COMMAND);
                chParam.setCategory(CATEGORY);
                chParam.setParams(PARAM);
                chParam.setContent(CONTENT);
                chParam.setIp(IP);
                chParam.setStartTime(System.currentTimeMillis());
                chParam.setChargetime(DateProc.getyyyyMMddhh24missNoSpace());
                Account acc = Account.checkLogin(chParam.getUser(),chParam.getAccesskey(), chParam.getCpGame());
                if (acc == null) {
                    System.out.println("GHI LOG FILE : TH TAI KHOAN CP not valid");
                    doAddErrOtpReq(request, chParam, result_mps, ERR_CODE.AUTHEN_FAIL.getJson());
                    return ERR_CODE.AUTHEN_FAIL.getJson();
                }
                String ipRequest = "";
                if (!acc.validIP(ipRequest)) {
                    System.out.println("GHI LOG FILE : TH ipRequest not valid");
                    doAddErrOtpReq(request, chParam, result_mps, ERR_CODE.IP_NOT_ALLOW.getJson());
                    return ERR_CODE.IP_NOT_ALLOW.getJson();
                }
                if (Tool.checkNull(chParam.getCpRequestId())) {
                    System.out.println("GHI LOG FILE : TH getCpRequestId null");
                    doAddErrOtpReq(request, chParam, result_mps, ERR_CODE.REQUESTID_EMPTY.getJson());
                    return ERR_CODE.REQUESTID_EMPTY.getJson();
                }
                if (Tool.checkNull(chParam.getMsisdn())) {
                    System.out.println("GHI LOG FILE : TH getMsisdn null");
                    doAddErrOtpReq(request, chParam, result_mps, ERR_CODE.PHONE_NOTNULL.getJson());
                    return ERR_CODE.PHONE_NOTNULL.getJson();
                }
                
                if (!PhoneValid.validPhoneViettel84(chParam.getMsisdn())) {
                    System.out.println("GHI LOG FILE : TH PHONE NOT VALID");
                    doAddErrOtpReq(request, chParam, result_mps, ERR_CODE.PHONE_NOT_VALID.getJson());
                    return ERR_CODE.PHONE_NOT_VALID.getJson();
                    // Valid phone 
                }
                if (Tool.checkNull(chParam.getPrice())) {
                    System.out.println("GHI LOG FILE : TH getMsisdn null");
                    doAddErrOtpReq(request, chParam, result_mps, ERR_CODE.PRICE_NOTNULL.getJson());
                    return ERR_CODE.PRICE_NOTNULL.getJson();
                }
                if (NumberUtils.toInt(chParam.getPrice()) <= 0) {
                    System.out.println("GHI LOG FILE : TH SO TIEN KHONG DUNG DINH DANG");
                    doAddErrOtpReq(request, chParam, result_mps, ERR_CODE.PRICE_NOT_VALID.getJson());
                    return ERR_CODE.PRICE_NOT_VALID.getJson();
                }
                String keycache = chParam.getMsisdn();
                System.out.println("keycache" + keycache);
                String keycachePrice = "price_" + chParam.getMsisdn();
                String keycacheReqOtp = "reqOtp_" + chParam.getMsisdn();
//                TAO CACHE REQUEST OTP
                System.out.println("-----------------------------");
                RequestOtp.createCacheReqOtp(keycacheReqOtp);
                System.out.println("+++++++++++++++++++++++++++++");
                if ("".equals(chParam.getOtp())) {
                    //LUU LOG FILE
                    doAddErrOtpReq(request, chParam, result_mps, ERR_CODE.OTP_NOTNULL.getJson());
                    return ERR_CODE.OTP_NOTNULL.getJson();
                } else if ("1".equals(chParam.getOtp())) {
                    String sysRequestId = Tool.getRandomString(18);
                    chParam.setSysRequestId(sysRequestId);

                    if (ReqCacheManager.get(keycache) != null) {
                        System.out.println("GHI LOG FILE : TH keycache Ton tai");
                        doAddErrOtpReq(request, chParam, result_mps, ERR_CODE.REQUEST_EXIST.getJson());
                        return ERR_CODE.REQUEST_EXIST.getJson();
                    }

//                    CHECK REQUEST LAP LAI NHIEU LAN
                    int totalReqOtp = RequestOtp.checkReqOtp(keycachePrice, chParam.getMsisdn());
                    if (totalReqOtp >= 5) {
                        //LUU LOG FILE
                        System.out.println("GHI LOG FILE +DB LOGS : TH totalReqOtp >5 req khong hop le");
                        doAddErrOtpReq(request, chParam, result_mps, ERR_CODE.REPEAT_OTP.getJson());
                        return ERR_CODE.REPEAT_OTP.getJson();
                    }

                    String subService = SubServices.getSubService(chParam.getPrice());
                    chParam.setSubService(subService);
//                    SAU NAY CHECK PRICE HOP LE THI MOI LAY SUBSERVICE
                    if (Tool.checkNull(subService)) {
                        System.out.println("GHI LOG FILE : TH subService Null");
                        doAddErrOtpReq(request, chParam, result_mps, ERR_CODE.UNKNOW_SERVICE.getJson());
                        return ERR_CODE.UNKNOW_SERVICE.getJson();
                    }

//                    CHECK SO TIEN /LAN <= 100k
                    boolean checkPrice = CheckPrice.checkPrice(chParam.getPrice());
                    if (!checkPrice) {
                        System.out.println("GHI LOG FILE : TH Price Amount > 100k/lan");
                        doAddErrOtpReq(request, chParam, result_mps, ERR_CODE.AMOUNT_LIMIT.getJson());
                        return ERR_CODE.AMOUNT_LIMIT.getJson();
                    }
//                    CHECK SO DIEN THOAI NAP QUA 500k
                    boolean checkTotalAmount = CheckPrice.checkTotalAmount(keycachePrice, chParam.getMsisdn(), chParam.getPrice());
                    if (!checkTotalAmount) {
                        System.out.println("GHI LOG FILE : TH Total Amount > 500k/ngay");
                        doAddErrOtpReq(request, chParam, result_mps, ERR_CODE.TOTAL_AMOUNT_LIMIT.getJson());
                        return ERR_CODE.TOTAL_AMOUNT_LIMIT.getJson();
                    }
//            GIA TRI MAC DINH KHI CHUA CO THAM SO TRUYEN VAO TU PHIA CP
//                    String cpCode = "";
//                    String cpGame = "";
//                    String cpUser = "";
//                    String accesskey = "";
//                    String signature = "";
//                    String otherInfo = "";
//
//                    if (chParam.getAccesskey() != null) {
//                        accesskey = chParam.getAccesskey();
//                    }
//                    if (chParam.getSignature() != null) {
//                        signature = chParam.getSignature();
//                    }
//                    if (chParam.getOtherInfo() != null) {
//                        otherInfo = chParam.getOtherInfo();
//                    }
                    String data = EncriptData.encryptData2MPS(subService, COMMAND, sysRequestId);
                    chParam.setData(data);

                    //NEU KQT QUA TRA VE MA OTP CHO KH THI MINH MOI CACHE
                    chParam.debugValue();
                    //LAY MA OTP
//                    result_mps = HttpRequest.sendGET();
                    result_mps = doMpschargeRequest(
                            chParam.getSysRequestId(), // sysRequestId
                            chParam.getMsisdn(), // msisdn
                            COMMAND, // Command 
                            CATEGORY, // category
                            PARAM, // params
                            chParam.getChargetime(), // chargetime,
                            chParam.getSubService(),//subService, 
                            chParam.getPrice(), // Price
                            CONTENT,// content
                            IP,// IP
                            chParam.getData(),// data, 
                            chParam.getOtp());

//                    LUU DB O DAY TRUOC KHI RETURN
                    if (result_mps.equals("-1")) {

                        System.out.println("GHI LOG FILE : TH KHONG LAY DUOC KET QUA TU MPS");
                        doAddErrOtpReq(request, chParam, result_mps, ERR_CODE.DISCONNECT_MPS.getJson());
                        return ERR_CODE.DISCONNECT_MPS.getJson();
                    }

//                    Check KET QUA 
                    if (result_mps.equals("4")) {
                        System.out.println("GHI LOG FILE & DB : TH MPS TRA OTP CHO NGUOI DUNG");
                        ReqCacheManager.put(keycache, chParam);
                        doAddOtpRequest(request, chParam, result_mps, ERR_CODE.REQUIRE_OTP.getJson());
                        return ERR_CODE.REQUIRE_OTP.getJson();
                    } else {
                        System.out.println("GHI LOG FILE & DB : TH LOI TRA VE TU MPS");
                        String mapErrorMps = ErrorMps.mapErrorMps(result_mps);
                        doAddErrOtpReq(request, chParam, result_mps, mapErrorMps);
                        return mapErrorMps;
                    }
                } else {
                    System.out.println("++++++++++++++++++++++++++++++++++");
                    System.out.println("OTP != 1");
                    System.out.println("NO TRU $$$ THAT DAY. DUNG CO DUA!");
                    System.out.println("++++++++++++++++++++++++++++++++++");
                    System.out.println("keycache ============ :" + keycache);
                    ChargingParams chParamCache = ReqCacheManager.get(keycache);
                    if (chParamCache == null) {
                        // 1. Request Exprire
                        // 2. Request Charging invalid CprequestId
                        System.out.println("GHI LOG FILE  TH:KHONG TON TAI CACHE");
                        doAddErrChargeReq(request, chParam, result_mps, ERR_CODE.CHARGE_NOT_VALID_OR_EXPRIRE.getJson());
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
                            System.out.println("HAI CHUOI KHAC NHAU");
                            doAddErrChargeReq(request, chParamCache, result_mps, ERR_CODE.CP_REQ_NOT_VALID.getJson());
                            return ERR_CODE.CP_REQ_NOT_VALID.getJson();
                        } else {
                            System.out.println("HAI CHUOI GIONG NHAU");
                        }
                        // YEU CAU CHARGE
//                        result_mps = HttpRequest.sendPOST();
                        result_mps = doMpschargeRequest(
                                chParamCache.getSysRequestId(), // sysRequestId
                                chParamCache.getMsisdn(), // msisdn
                                COMMAND, // Command 
                                CATEGORY, // category
                                PARAM, // params
                                chParamCache.getChargetime(), // chargetime,
                                chParamCache.getSubService(),//subService, 
                                chParamCache.getPrice(), // Price
                                CONTENT,// content
                                IP,// IP
                                chParamCache.getData(),// data, 
                                chParamCache.getOtp());

                        ReqCacheManager.remove(keycache);
                        if (result_mps.equals("0")) {
//                             XOA CACHE REQ_OTP NEU GOI DEN MPS CHARGE THANH CONG
                            RequestOtp.removeCacheReqOtp(keycacheReqOtp);

                            System.out.println("CHARG THANH CONG THI CACHE PRICE");
                            CheckPrice.createCacheTotalAmount(keycachePrice, chParamCache.getMsisdn(), chParamCache.getPrice());
                            System.out.println("GHI LOG FILE & DB : TH MPS CHARGE THANH CONG");
                            doAddChargeRequest(request, chParamCache, result_mps, ERR_CODE.SUCCESS_CHARGING.getJson());
                            return ERR_CODE.SUCCESS_CHARGING.getJson();
                        } else {
                            System.out.println("GHI LOG FILE  : TH MPS CHARGE THAT BAI");
                            String mapErrorMps = ErrorMps.mapErrorMps(result_mps);
                            doAddErrChargeReq(request, chParamCache, result_mps, mapErrorMps);
                            return mapErrorMps;
                        }
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

    public static boolean doAddOtpRequest(String request, ChargingParams chParam, String rs_mps, String rs_ahp) {
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

        rs = airTimeRequest.addOtpRequest(airTimeRequest);

        if (rs) {
            System.out.println("LUU THANH CONG");
            rs = true;
        } else {
            System.out.println("LUU THAT BAI");
        }
        return rs;
    }

    public static boolean doAddChargeRequest(String request, ChargingParams chParam, String rs_mps, String rs_ahp) {
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

        rs = airTimeRequest.addChargeRequest(airTimeRequest);

        if (rs) {
            System.out.println("LUU THANH CONG");
            rs = true;
        } else {
            System.out.println("LUU THAT BAI");
        }
        return rs;
    }

    public static boolean doAddErrOtpReq(String request, ChargingParams chParam, String rs_mps, String rs_ahp) {
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

        rs = airTimeRequest.addErrOtpReq(airTimeRequest);

        if (rs) {
            System.out.println("LUU THANH CONG");
            rs = true;
        } else {
            System.out.println("LUU THAT BAI");
        }
        return rs;
    }

    public static boolean doAddErrChargeReq(String request, ChargingParams chParam, String rs_mps, String rs_ahp) {
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

        rs = airTimeRequest.addErrChargeReq(airTimeRequest);

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
