/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htc.services.airtime.entity;

import net.sf.json.JSONObject;

/**
 *
 * @author Admin
 */
public class ChargeResponse {

    String code;
    String message;

    public ChargeResponse(String code, String mess) {
        this.code = code;
        this.message = mess;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toJson() {
//        return "chuoi json tra ve";
        JSONObject obj = JSONObject.fromObject(this);
        return obj.toString();
    }
}
