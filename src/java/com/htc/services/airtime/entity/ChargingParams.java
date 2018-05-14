/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htc.services.airtime.entity;

import com.htc.services.airtime.common.Tool;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
public class ChargingParams {

    static final Logger logger = Logger.getLogger(ChargingParams.class);
    String cpCode;
    String cpGame;
    String user;
    String cpRequestId;
    String msisdn;
    String price;
    String otp;
    String accesskey;
    String secretkey;
    String signature;

    String otherInfo;

    String sysRequestId;
    String command;
    String category;
    String params;
    String chargetime;
    String subService;
    String content; // CAI NAY SAU Y/C app gửi theo noi dung len
    String ip;
    String data;
    long startTime;

    public ChargingParams() {
    }

    public void debugValue() {
        try {
            Class objClass = this.getClass();
            // Get the public methods associated with this class.
            Method[] methods = objClass.getMethods();
            for (Method method : methods) {
                String name = method.getName();
                if (name.startsWith("get") || name.startsWith("is")) {
                    String fieldName = getFieldName(method);
                    if (!Tool.checkNull(fieldName)) {
                        if (fieldName.equals("brand") || fieldName.equals("class")) {
                            continue;
                        }
                        System.out.print(fieldName + ":" + method.invoke(this) + "\n");
                    }
                }
            }
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.error(Tool.getLogMessage(e));
        }
    }

    private static String getFieldName(Method method) {
        try {
            Class<?> clazz = method.getDeclaringClass();
            BeanInfo info = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] props = info.getPropertyDescriptors();
            for (PropertyDescriptor pd : props) {
                if (method.equals(pd.getWriteMethod()) || method.equals(pd.getReadMethod())) {
                    return pd.getName();
                }
            }
        } catch (Exception e) {
            logger.error(Tool.getLogMessage(e));
        }
        return null;
    }

    public static ChargingParams str2Obj(String request) {
        ChargingParams result = null;
        try {
            JSONObject jobj = JSONObject.fromObject(request);
            result = (ChargingParams) JSONObject.toBean(jobj, ChargingParams.class);
        } catch (Exception e) {
            logger.error(Tool.getLogMessage(e));
            System.out.println("KHONG PASS OBJECT");
        }
        return result;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getSecretkey() {
        return secretkey;
    }

    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey;
    }

   
    public String getCpCode() {
        return cpCode;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode;
    }

    public String getCpGame() {
        return cpGame;
    }

    public void setCpGame(String cpGame) {
        this.cpGame = cpGame;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCpRequestId() {
        return cpRequestId;
    }

    public void setCpRequestId(String cpRequestId) {
        this.cpRequestId = cpRequestId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getAccesskey() {
        return accesskey;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getSysRequestId() {
        return sysRequestId;
    }

    public void setSysRequestId(String sysRequestId) {
        this.sysRequestId = sysRequestId;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getChargetime() {
        return chargetime;
    }

    public void setChargetime(String chargetime) {
        this.chargetime = chargetime;
    }

    public String getSubService() {
        return subService;
    }

    public void setSubService(String subService) {
        this.subService = subService;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
