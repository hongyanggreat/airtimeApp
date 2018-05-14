/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htc.services.airtime.common;

import com.gk.rsa.RSA;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;

/**
 *
 * @author Company
 */
public class EncriptData {

    public static String encryptData2MPS(String sub_service, String command, String cp_request_id) throws UnsupportedEncodingException, IOException, GeneralSecurityException {
        String dataAndSign = "";
        try {
            //        sub_service = "sub_service";
//        command = "DOWNLOAD";
//        cp_request_id = "cp_request_id";
            // B1: Input dữ liệu gửi từ CP sang VT có dạng sau: 
            String input = "SUB_SERVICE=" + sub_service + "&COMMAND=" + command + "&CP_REQUEST_ID=" + cp_request_id;

            String keyAES = "dsadsd343432cfdsfd";
//            System.out.println("========================================");
//            System.out.println("B1: " + input);
            try {
                keyAES = RSA.AESKeyGen();
            } catch (Exception e) {
            }
//            System.out.println("keyAES : " + keyAES);
//             B2: CP mã hóa dữ liệu input theo thuật toán AES – key AES theo từng phiên giao dịch
            String input_encrypt = "value=" + RSA.encryptAES(input, keyAES) + "&key=" + keyAES;
//            System.out.println("End B2+3: " + input_encrypt);
//        try {
//            System.out.println("Giaima: " + EncryptManager.decryptAES(EncryptManager.encryptAES(input, keyAES), keyAES));
//        } catch (Exception ex) {
//            System.out.println("LOI GIAI MA");
//            ex.printStackTrace();
//        }
            // B4: CP mã hóa dữ liệu bằng PubVT
            String dataEncrypt = RSA.encrypt(input_encrypt, RSA.PUBLIC_KEY_VT);
            System.out.println("End B4: " + dataEncrypt);
//             B5: CP tạo chữ ký trên dữ liệu mã hóa bằng khóa PrivCP
            String sign = RSA.sign(RSA.PRIVATE_KEY_CP, dataEncrypt);
            System.out.println("End B5: " + sign);
            //dataAndSign =  dataEncrypt + "SIG=" + URLEncoder.encode(sign, "UTF-8");//nhu the moi dung tai lieu chuVang
            dataAndSign = dataEncrypt + "SIG=" + URLEncoder.encode(sign, "UTF-8");
            System.out.println("B6: " + dataAndSign);
            //dataAndSign =  URLEncoder.encode(dataEncrypt, "UTF-8") + URLEncoder.encode(sign, "UTF-8");
            boolean verify = RSA.verify(RSA.PUBLIC_KEY_CP, dataEncrypt, sign);
            System.out.println("B7.Verify == : " + verify);
            System.out.println("========================================");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataAndSign;
    }

}
