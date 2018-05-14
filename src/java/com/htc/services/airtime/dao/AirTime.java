/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htc.services.airtime.dao;

import com.htc.services.airtime.common.DateProc;
import com.htc.services.airtime.common.Tool;
import com.htc.services.airtime.db.DBPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
public class AirTime {

    static final Logger logger = Logger.getLogger(AirTime.class);

    private int id;
    private String sysRequestId;
    private String cpRequestId;
    private String cpCode;
    private String gameCode;
    private String userName;
    private String msisdn;
    private int price;
    private String rsAhp;
    private String rsMps;
    private String otp;
    private String chargeTime;
    private String subService;
    private String otherInfo;
    private Timestamp requestTime;
    private Timestamp submitTime;

    public AirTime() {
    }

    public static void main(String[] args) {
        totalAmount("0964933669");
    }
    public static boolean addRequestOtp(AirTime dt) {
//    public static boolean addNew() {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "insert into artime_request_otp (SYS_REQUEST_ID,CP_REQUEST_ID,CP_CODE,GAME_CODE,USER_NAME,MSISDN,PRICE,RS_AHP,RS_MPS,OTP,CHARGER_TIME,SUB_SERVICE,OTHER_INFO,REQUEST_TIME,SUBMIT_TIME) "
                + "     values        (                 ?          ,?            ,?      ,?        ,?        ,?     ,?    ,?     ,?     ,?  ,?           ,?          ,?         ,NOW()           ,NOW()          ) ";
//        System.out.println("sql:" + sql);
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            int i = 1;
            pstm.setString(i++, dt.getSysRequestId());
            pstm.setString(i++, dt.getCpRequestId());
            pstm.setString(i++, dt.getCpCode());
            pstm.setString(i++, dt.getGameCode());
            pstm.setString(i++, dt.getUserName());
            pstm.setString(i++, dt.getMsisdn());
            pstm.setInt(i++, dt.getPrice());
            pstm.setString(i++, dt.getRsAhp());
            pstm.setString(i++, dt.getRsMps());
            pstm.setString(i++, dt.getOtp());
            pstm.setString(i++, dt.getChargeTime());
            pstm.setString(i++, dt.getSubService());
            pstm.setString(i++, dt.getOtherInfo());
            if (pstm.executeUpdate() == 1) {
                flag = true;
            }

        } catch (SQLException ex) {
            System.out.println("CO LOI KHI LUU CSDL addRequest");
            ex.printStackTrace();
            logger.error(Tool.getLogMessage(ex));
        } finally {
            System.out.println("GIAI PHONG BO NHO");
            DBPool.freeConn(rs, pstm, conn);
        }

        return flag;
    }
    public static boolean addRequestCharge(AirTime dt) {
//    public static boolean addNew() {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "insert into artime_request_charge (SYS_REQUEST_ID,CP_REQUEST_ID,CP_CODE,GAME_CODE,USER_NAME,MSISDN,PRICE,RS_AHP,RS_MPS,OTP,CHARGER_TIME,SUB_SERVICE,OTHER_INFO,REQUEST_TIME,SUBMIT_TIME) "
                + "     values        (                 ?          ,?            ,?      ,?        ,?        ,?     ,?    ,?     ,?     ,?  ,?           ,?          ,?         ,NOW()           ,NOW()          ) ";
//        System.out.println("sql:" + sql);
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            int i = 1;
            pstm.setString(i++, dt.getSysRequestId());
            pstm.setString(i++, dt.getCpRequestId());
            pstm.setString(i++, dt.getCpCode());
            pstm.setString(i++, dt.getGameCode());
            pstm.setString(i++, dt.getUserName());
            pstm.setString(i++, dt.getMsisdn());
            pstm.setInt(i++, dt.getPrice());
            pstm.setString(i++, dt.getRsAhp());
            pstm.setString(i++, dt.getRsMps());
            pstm.setString(i++, dt.getOtp());
            pstm.setString(i++, dt.getChargeTime());
            pstm.setString(i++, dt.getSubService());
            pstm.setString(i++, dt.getOtherInfo());
            if (pstm.executeUpdate() == 1) {
                flag = true;
            }

        } catch (SQLException ex) {
            System.out.println("CO LOI KHI LUU CSDL addRequest");
            ex.printStackTrace();
            logger.error(Tool.getLogMessage(ex));
        } finally {
            System.out.println("GIAI PHONG BO NHO");
            DBPool.freeConn(rs, pstm, conn);
        }

        return flag;
    }
    public static boolean addRequest(AirTime dt) {
//    public static boolean addNew() {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "insert into artime_request (SYS_REQUEST_ID,CP_REQUEST_ID,CP_CODE,GAME_CODE,USER_NAME,MSISDN,PRICE,RS_AHP,RS_MPS,OTP,CHARGER_TIME,SUB_SERVICE,OTHER_INFO,REQUEST_TIME,SUBMIT_TIME) "
                + "     values        (                 ?          ,?            ,?      ,?        ,?        ,?     ,?    ,?     ,?     ,?  ,?           ,?          ,?         ,NOW()           ,NOW()          ) ";
//        System.out.println("sql:" + sql);
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            int i = 1;
            pstm.setString(i++, dt.getSysRequestId());
            pstm.setString(i++, dt.getCpRequestId());
            pstm.setString(i++, dt.getCpCode());
            pstm.setString(i++, dt.getGameCode());
            pstm.setString(i++, dt.getUserName());
            pstm.setString(i++, dt.getMsisdn());
            pstm.setInt(i++, dt.getPrice());
            pstm.setString(i++, dt.getRsAhp());
            pstm.setString(i++, dt.getRsMps());
            pstm.setString(i++, dt.getOtp());
            pstm.setString(i++, dt.getChargeTime());
            pstm.setString(i++, dt.getSubService());
            pstm.setString(i++, dt.getOtherInfo());
            if (pstm.executeUpdate() == 1) {
                flag = true;
            }

        } catch (SQLException ex) {
            System.out.println("CO LOI KHI LUU CSDL addRequest");
            ex.printStackTrace();
            logger.error(Tool.getLogMessage(ex));
        } finally {
            System.out.println("GIAI PHONG BO NHO");
            DBPool.freeConn(rs, pstm, conn);
        }

        return flag;
    }
    public static boolean addResponse(AirTime dt) {
//    public static boolean addNew() {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "insert into artime_response (SYS_REQUEST_ID,CP_REQUEST_ID,CP_CODE,GAME_CODE,USER_NAME,MSISDN,PRICE,RS_AHP,RS_MPS,OTP,CHARGER_TIME,SUB_SERVICE,OTHER_INFO,REQUEST_TIME,SUBMIT_TIME) "
                + "     values        (                 ?          ,?            ,?      ,?        ,?        ,?     ,?    ,?     ,?     ,?  ,?           ,?          ,?         ,NOW()           ,NOW()          ) ";
//        System.out.println("sql:" + sql);
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            int i = 1;
            pstm.setString(i++, dt.getSysRequestId());
            pstm.setString(i++, dt.getCpRequestId());
            pstm.setString(i++, dt.getCpCode());
            pstm.setString(i++, dt.getGameCode());
            pstm.setString(i++, dt.getUserName());
            pstm.setString(i++, dt.getMsisdn());
            pstm.setInt(i++, dt.getPrice());
            pstm.setString(i++, dt.getRsAhp());
            pstm.setString(i++, dt.getRsMps());
            pstm.setString(i++, dt.getOtp());
            pstm.setString(i++, dt.getChargeTime());
            pstm.setString(i++, dt.getSubService());
            pstm.setString(i++, dt.getOtherInfo());
            if (pstm.executeUpdate() == 1) {
                flag = true;
            }

        } catch (SQLException ex) {
            System.out.println("CO LOI KHI LUU CSDL addRequest");
            ex.printStackTrace();
            logger.error(Tool.getLogMessage(ex));
        } finally {
            System.out.println("GIAI PHONG BO NHO");
            DBPool.freeConn(rs, pstm, conn);
        }

        return flag;
    }
    public static boolean addOtpRequest(AirTime dt) {
//    public static boolean addNew() {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "insert into tbl_airtime_otp_request (SYS_REQUEST_ID,CP_REQUEST_ID,CP_CODE,GAME_CODE,USER_NAME,MSISDN,PRICE,RS_AHP,RS_MPS,OTP,CHARGER_TIME,SUB_SERVICE,OTHER_INFO,REQUEST_TIME,SUBMIT_TIME) "
                + "     values        (                 ?          ,?            ,?      ,?        ,?        ,?     ,?    ,?     ,?     ,?  ,?           ,?          ,?         ,NOW()           ,NOW()          ) ";
//        System.out.println("sql:" + sql);
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            int i = 1;
            pstm.setString(i++, dt.getSysRequestId());
            pstm.setString(i++, dt.getCpRequestId());
            pstm.setString(i++, dt.getCpCode());
            pstm.setString(i++, dt.getGameCode());
            pstm.setString(i++, dt.getUserName());
            pstm.setString(i++, dt.getMsisdn());
            pstm.setInt(i++, dt.getPrice());
            pstm.setString(i++, dt.getRsAhp());
            pstm.setString(i++, dt.getRsMps());
            pstm.setString(i++, dt.getOtp());
            pstm.setString(i++, dt.getChargeTime());
            pstm.setString(i++, dt.getSubService());
            pstm.setString(i++, dt.getOtherInfo());
            if (pstm.executeUpdate() == 1) {
                flag = true;
            }

        } catch (SQLException ex) {
            System.out.println("CO LOI KHI LUU CSDL");
            ex.printStackTrace();
            logger.error(Tool.getLogMessage(ex));
        } finally {
            System.out.println("GIAI PHONG BO NHO");
            DBPool.freeConn(rs, pstm, conn);
        }

        return flag;
    }

    public static boolean addChargeRequest(AirTime dt) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "insert into tbl_airtime_charge_resquest(SYS_REQUEST_ID,CP_REQUEST_ID,CP_CODE,GAME_CODE,USER_NAME,MSISDN,PRICE,RS_AHP,RS_MPS,OTP,CHARGER_TIME,SUB_SERVICE,OTHER_INFO,REQUEST_TIME,SUBMIT_TIME) "
                + "     values        (                 ?          ,?            ,?      ,?        ,?        ,?     ,?    ,?     ,?     ,?  ,?           ,?          ,?         ,NOW()           ,NOW()          ) ";
//        System.out.println("sql:" + sql);
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            int i = 1;
            pstm.setString(i++, dt.getSysRequestId());
            pstm.setString(i++, dt.getCpRequestId());
            pstm.setString(i++, dt.getCpCode());
            pstm.setString(i++, dt.getGameCode());
            pstm.setString(i++, dt.getUserName());
            pstm.setString(i++, dt.getMsisdn());
            pstm.setInt(i++, dt.getPrice());
            pstm.setString(i++, dt.getRsAhp());
            pstm.setString(i++, dt.getRsMps());
            pstm.setString(i++, dt.getOtp());
            pstm.setString(i++, dt.getChargeTime());
            pstm.setString(i++, dt.getSubService());
            pstm.setString(i++, dt.getOtherInfo());
            if (pstm.executeUpdate() == 1) {
                flag = true;
            }

        } catch (SQLException ex) {
            System.out.println("CO LOI KHI LUU CSDL");
            ex.printStackTrace();
            logger.error(Tool.getLogMessage(ex));
        } finally {
            System.out.println("GIAI PHONG BO NHO");
            DBPool.freeConn(rs, pstm, conn);
        }

        return flag;
    }

    public static boolean addErrOtpReq(AirTime dt) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "insert into tbl_airtime_err_otp_req(SYS_REQUEST_ID,CP_REQUEST_ID,CP_CODE,GAME_CODE,USER_NAME,MSISDN,PRICE,RS_AHP,RS_MPS,OTP,CHARGER_TIME,SUB_SERVICE,OTHER_INFO,REQUEST_TIME,SUBMIT_TIME) "
                + "     values        (                 ?          ,?            ,?      ,?        ,?        ,?     ,?    ,?     ,?     ,?  ,?           ,?          ,?         ,NOW()           ,NOW()          ) ";
//        System.out.println("sql:" + sql);
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            int i = 1;
            pstm.setString(i++, dt.getSysRequestId());
            pstm.setString(i++, dt.getCpRequestId());
            pstm.setString(i++, dt.getCpCode());
            pstm.setString(i++, dt.getGameCode());
            pstm.setString(i++, dt.getUserName());
            pstm.setString(i++, dt.getMsisdn());
            pstm.setInt(i++, dt.getPrice());
            pstm.setString(i++, dt.getRsAhp());
            pstm.setString(i++, dt.getRsMps());
            pstm.setString(i++, dt.getOtp());
            pstm.setString(i++, dt.getChargeTime());
            pstm.setString(i++, dt.getSubService());
            pstm.setString(i++, dt.getOtherInfo());
            if (pstm.executeUpdate() == 1) {
                flag = true;
            }

        } catch (SQLException ex) {
            System.out.println("CO LOI KHI LUU CSDL");
            System.out.println(ex.getMessage());
            logger.error(Tool.getLogMessage(ex));
        } finally {
            System.out.println("GIAI PHONG BO NHO");
            DBPool.freeConn(rs, pstm, conn);
        }

        return flag;
    }
    public static boolean addErrChargeReq(AirTime dt) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "insert into tbl_airtime_err_charge_req(SYS_REQUEST_ID,CP_REQUEST_ID,CP_CODE,GAME_CODE,USER_NAME,MSISDN,PRICE,RS_AHP,RS_MPS,OTP,CHARGER_TIME,SUB_SERVICE,OTHER_INFO,REQUEST_TIME,SUBMIT_TIME) "
                + "     values        (                 ?          ,?            ,?      ,?        ,?        ,?     ,?    ,?     ,?     ,?  ,?           ,?          ,?         ,NOW()           ,NOW()          ) ";
//        System.out.println("sql:" + sql);
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            int i = 1;
            pstm.setString(i++, dt.getSysRequestId());
            pstm.setString(i++, dt.getCpRequestId());
            pstm.setString(i++, dt.getCpCode());
            pstm.setString(i++, dt.getGameCode());
            pstm.setString(i++, dt.getUserName());
            pstm.setString(i++, dt.getMsisdn());
            pstm.setInt(i++, dt.getPrice());
            pstm.setString(i++, dt.getRsAhp());
            pstm.setString(i++, dt.getRsMps());
            pstm.setString(i++, dt.getOtp());
            pstm.setString(i++, dt.getChargeTime());
            pstm.setString(i++, dt.getSubService());
            pstm.setString(i++, dt.getOtherInfo());
            if (pstm.executeUpdate() == 1) {
                flag = true;
            }

        } catch (SQLException ex) {
            System.out.println("CO LOI KHI LUU CSDL");
            logger.error(Tool.getLogMessage(ex));
        } finally {
            System.out.println("GIAI PHONG BO NHO");
            DBPool.freeConn(rs, pstm, conn);
        }

        return flag;
    }

    public static AirTime getByMsisdn(String msisdn) {
        AirTime dt = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String time = DateProc.createDate("yyyy-MM-dd") + " 00:00:00";
        String sql = "SELECT * FROM tbl_airtime_charge_resquest  WHERE MSISDN = ? AND REQUEST_TIME > ?  ORDER BY ID DESC LIMIT 1";
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            int i = 1;
            pstm.setString(i++, msisdn);
            pstm.setString(i++, time);
            rs = pstm.executeQuery();
            if (rs.next()) {
                dt = new AirTime();
                dt.setMsisdn(rs.getString("MSISDN"));
                dt.setChargeTime(rs.getString("CHARGER_TIME"));
                dt.setRequestTime(rs.getTimestamp("REQUEST_TIME"));
            }
        } catch (SQLException ex) {
            logger.error(Tool.getLogMessage(ex));
        } finally {
            DBPool.freeConn(rs, pstm, conn);
        }
        return dt;
    }

    public static int totalAmount(String msisdn) {
        int result = 0;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT SUM(PRICE) TOTAL_AMOUNT FROM tbl_airtime_charge_resquest WHERE MSISDN = ? AND REQUEST_TIME > ? AND RS_MPS = 0";
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            int i = 1;
            String time = DateProc.createDate("yyyy-MM-dd") + " 00:00:00";
            System.out.println("time" + time);
            pstm.setString(i++, msisdn);
            pstm.setString(i++, time);
            System.out.println("CAU LENH SQL" + pstm.toString());
            rs = pstm.executeQuery();
            if (rs.next()) {
//                result = rs.getInt(1);
                result = rs.getInt("TOTAL_AMOUNT");
            }
        } catch (SQLException ex) {
            System.out.println("CO LOI O TRY");
            logger.error(Tool.getLogMessage(ex));
        } finally {
            System.out.println("KET QUA:" + result);
            DBPool.freeConn(rs, pstm, conn);
        }
        return result;
    }

    public static int countRequestOtpPerDay(AirTime dt) {
        int result = 0;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String time = DateProc.createDate("yyyy-MM-dd") + " 00:00:00";

        String sql = "SELECT count(*) TOTAL_REQUEST FROM tbl_airtime_otp_request WHERE MSISDN = ? AND REQUEST_TIME > ?  ORDER BY ID DESC";

        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            int i = 1;
            pstm.setString(i++, dt.getMsisdn());
            pstm.setString(i++, time);
            pstm.setTimestamp(i++, dt.getRequestTime());
            System.out.println("CAU LENH SQL" + pstm.toString());
            rs = pstm.executeQuery();
            if (rs.next()) {
//                result = rs.getInt(1);
                result = rs.getInt("TOTAL_REQUEST");
            }
        } catch (SQLException ex) {
            logger.error(Tool.getLogMessage(ex));
        } finally {
            DBPool.freeConn(rs, pstm, conn);
        }
        return result;
    }

    public static int countRequestOtp(String msisdn) {
        int result = 0;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT count(*) TOTAL_REQUEST FROM tbl_airtime_otp_request WHERE MSISDN = ? AND REQUEST_TIME > ? ORDER BY ID DESC";

        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            int i = 1;
            pstm.setString(i++, msisdn);
            System.out.println("CAU LENH SQL" + pstm.toString());
            rs = pstm.executeQuery();
            if (rs.next()) {
//                result = rs.getInt(1);
                result = rs.getInt("TOTAL_REQUEST");
            }
        } catch (SQLException ex) {
            logger.error(Tool.getLogMessage(ex));
        } finally {
            DBPool.freeConn(rs, pstm, conn);
        }
        return result;
    }

    public AirTime(int ID, String SYS_REQUEST_ID, String CP_REQUEST_ID, String CP_CODE, String GAME_CODE,
            String USER_NAME, String MSISDN, int PRICE, String RS_AHP, String RS_MPS, String OTP,
            String CHARGER_TIME, String SUB_SERVICE, String OTHER_INFO,
            Timestamp REQUEST_TIME, Timestamp SUBMIT_TIME
    ) {
        this.id = ID;
        this.sysRequestId = SYS_REQUEST_ID;
        this.cpRequestId = CP_REQUEST_ID;
        this.cpCode = CP_CODE;
        this.gameCode = GAME_CODE;
        this.userName = USER_NAME;
        this.msisdn = MSISDN;
        this.price = PRICE;
        this.rsAhp = RS_AHP;
        this.rsMps = RS_MPS;
        this.otp = OTP;
        this.chargeTime = CHARGER_TIME;
        this.subService = SUB_SERVICE;
        this.otherInfo = OTHER_INFO;
        this.requestTime = REQUEST_TIME;
        this.submitTime = SUBMIT_TIME;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSysRequestId() {
        return sysRequestId;
    }

    public void setSysRequestId(String sysRequestId) {
        this.sysRequestId = sysRequestId;
    }

    public String getCpRequestId() {
        return cpRequestId;
    }

    public void setCpRequestId(String cpRequestId) {
        this.cpRequestId = cpRequestId;
    }

    public String getCpCode() {
        return cpCode;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRsAhp() {
        return rsAhp;
    }

    public void setRsAhp(String rsAhp) {
        this.rsAhp = rsAhp;
    }

    public String getRsMps() {
        return rsMps;
    }

    public void setRsMps(String rsMps) {
        this.rsMps = rsMps;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(String chargeTime) {
        this.chargeTime = chargeTime;
    }

    public String getSubService() {
        return subService;
    }

    public void setSubService(String subService) {
        this.subService = subService;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public Timestamp getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Timestamp requestTime) {
        this.requestTime = requestTime;
    }

    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }
}
