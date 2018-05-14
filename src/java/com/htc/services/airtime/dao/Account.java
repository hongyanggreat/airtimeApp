/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htc.services.airtime.dao;

import com.htc.services.airtime.common.Tool;
import com.htc.services.airtime.db.DBPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
public class Account {

    static final Logger logger = Logger.getLogger(Account.class);

    String cpCode;
    String gameName;
    String gameCode;
    String userName;
    String servicePin;
    String accessKey;
    String secretKey;
    String serviceType;
    String ipAllow;
    int status;

    public Account() {

    }

    public static ArrayList<Account> getUserService(int serviceType) throws SQLException {
        Account dt = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        ArrayList list = new ArrayList();
        try {
            conn = DBPool.getConnection();
            String sql = "SELECT * FROM tbl_user_service  WHERE SERVICE_TYPE = ? ";
            pstm = conn.prepareStatement(sql);
            int i = 1;
            pstm.setInt(i++, serviceType);
            rs = pstm.executeQuery();
            while (rs.next()) {
                dt = new Account();
                dt.setCpCode(rs.getString("CP_CODE"));
                dt.setGameName(rs.getString("GAME_NAME"));
                dt.setGameCode(rs.getString("CODE_GAME"));
                dt.setUserName(rs.getString("USERNAME"));
                dt.setServicePin(rs.getString("SERVICE_PIN"));
                dt.setAccessKey(rs.getString("ACCESS_KEY"));
                dt.setSecretKey(rs.getString("SECRET_KEY"));
                dt.setServiceType(rs.getString("SERVICE_TYPE"));
                dt.setIpAllow(rs.getString("IP_ALLOW"));
                dt.setStatus(rs.getInt("STATUS"));

                list.add(dt);
            }

        } catch (Exception ex) {
            System.out.println("CO LOI O TRY : getListSearch");
            System.out.println("ex" + ex);
            logger.error(Tool.getLogMessage(ex));
        } finally {
            DBPool.freeConn(rs, pstm, conn);
        }
        return list;
    }
    public static Account checkLogin(String username,String password,String codeGame) throws SQLException {
        Account dt = null;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT CP_CODE,USERNAME,IP_ALLOW FROM tbl_user_service  WHERE USERNAME = ?  AND ACCESS_KEY = ? AND CODE_GAME = ? AND STATUS = 1";
        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            int i = 1;
            pstm.setString(i++, username);
            pstm.setString(i++, password);
            pstm.setString(i++, codeGame);
            rs = pstm.executeQuery();
            if (rs.next()) {
                dt = new Account();
                dt.setCpCode(rs.getString("CP_CODE"));
                dt.setUserName(rs.getString("USERNAME"));
                dt.setIpAllow(rs.getString("IP_ALLOW"));
            }
        } catch (Exception ex) {
            System.out.println("CO LOI O TRY : getListSearch");
            System.out.println("ex" + ex);
            logger.error(Tool.getLogMessage(ex));
        } finally {
            DBPool.freeConn(rs, pstm, conn);
        }
        return dt;
    }
    public boolean validIP(String ipAddress, String ipAllow) {
        Boolean validIP = false;
        if (!Tool.checkNull(ipAllow) && !Tool.checkNull(ipAddress)) {
            String[] arrIpAllow = ipAllow.split(",");
            validIP = Arrays.asList(arrIpAllow).contains(ipAddress);
        }
        return validIP;
    }
    public boolean validIP(String ipRequest) {
        return true;
    }

    public String getCpCode() {
        return cpCode;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
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

    public String getServicePin() {
        return servicePin;
    }

    public void setServicePin(String servicePin) {
        this.servicePin = servicePin;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getIpAllow() {
        return ipAllow;
    }

    public void setIpAllow(String ipAllow) {
        this.ipAllow = ipAllow;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
