/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gk.rsa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.misc.BASE64Encoder;

public class RSA {

    protected final static Logger logger = Logger.getLogger(RSA.class);
    public static PublicKey PUBLIC_KEY_VT;
//    public static PrivateKey PRIVATE_KEY_VT;
    public static PublicKey PUBLIC_KEY_CP;
    public static PrivateKey PRIVATE_KEY_CP;

    public final static String RESOURCES_DIR = "/work/webroot/AirTimeServices/META-INF/keyRSA/";
//    public final static String RESOURCES_DIR = "E:\\JAVA\\AirTimeServices\\web\\META-INF\\keyRSA\\";
//    public final static String RESOURCES_DIR = "../config";   

    static {
        loadRSA();
    }

    public static void loadRSA() {
        try {
            System.out.println("+++++++++++++++DANG LAY LINK KEYRSA o: "+RESOURCES_DIR);
            Security.addProvider(new BouncyCastleProvider());
            logger.info("BouncyCastle provider added.");
            KeyFactory factory = KeyFactory.getInstance("RSA", "BC");
            //--
            PUBLIC_KEY_VT = generatePublicKey(factory, RESOURCES_DIR + "/PublicKeyVT.pem");
            logger.info(String.format("Instantiated public key: %s", PUBLIC_KEY_VT));
            //--
            PUBLIC_KEY_CP = generatePublicKey(factory, RESOURCES_DIR + "/PublicKeyCP.pem");
            logger.info(String.format("Instantiated private key: %s", PUBLIC_KEY_CP));
            PRIVATE_KEY_CP = generatePrivateKey(factory, RESOURCES_DIR + "/PrivateKeyCP.pem");
            logger.info(String.format("Instantiated private key: %s", PRIVATE_KEY_CP));
            //--
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static PrivateKey generatePrivateKey(KeyFactory factory, String filename) throws InvalidKeySpecException, FileNotFoundException, IOException {
        PemFile pemFile = new PemFile(filename);
        byte[] content = pemFile.getPemObject().getContent();
        PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
        return factory.generatePrivate(privKeySpec);
    }

    private static PublicKey generatePublicKey(KeyFactory factory, String filename) throws InvalidKeySpecException, FileNotFoundException, IOException {
        PemFile pemFile = new PemFile(filename);
        byte[] content = pemFile.getPemObject().getContent();
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(content);
        return factory.generatePublic(pubKeySpec);
    }

    public static String sign(PrivateKey privateKey, String message) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
        Signature sign = Signature.getInstance("SHA1withRSA");
        sign.initSign(privateKey);
        sign.update(message.getBytes("UTF-8"));
        return new String(Base64.encodeBase64(sign.sign()), "UTF-8");
    }

    public static boolean verify(PublicKey publicKey, String message, String signature) throws SignatureException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Signature sign = Signature.getInstance("SHA1withRSA");
        sign.initVerify(publicKey);
        sign.update(message.getBytes("UTF-8"));
        return sign.verify(Base64.decodeBase64(signature.getBytes("UTF-8")));
    }

    public static String encrypt(String rawText, PublicKey publicKey) throws IOException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.encodeBase64String(cipher.doFinal(rawText.getBytes("UTF-8")));
    }

    public static String decrypt(String cipherText, PrivateKey privateKey) throws IOException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(Base64.decodeBase64(cipherText)), "UTF-8");
    }

    public static String AESKeyGen() throws NoSuchAlgorithmException {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128, new SecureRandom());
            SecretKey secretKey = keyGen.generateKey();
            return byteToHex(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException noSuchAlgo) {
            logger.error(noSuchAlgo.getMessage());
        }
        return null;
    }

    public static String encryptAES(String data, String key) {
        String dataEncrypted = new String();
        try {
            Cipher aesCipher = Cipher.getInstance("AES");
            byte[] raw = hexToBytes(key);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            aesCipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] byteDataToEncrypt = data.getBytes();
            byte[] byteCipherText = aesCipher.doFinal(byteDataToEncrypt);
            dataEncrypted = new BASE64Encoder().encode(byteCipherText);
            return dataEncrypted;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return dataEncrypted;
    }

    public static byte[] hexToBytes(String hex) {
        return hexToBytes(hex.toCharArray());
    }

    public static byte[] hexToBytes(char[] hex) {
        int length = hex.length / 2;
        byte[] raw = new byte[length];
        for (int i = 0; i < length; i++) {
            int high = Character.digit(hex[i * 2], 16);
            int low = Character.digit(hex[i * 2 + 1], 16);
            int value = (high << 4) | low;
            if (value > 127) {
                value -= 256;
            }
            raw[i] = (byte) value;
        }
        return raw;
    }

    public static String byteToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;

            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }

        return buf.toString();
    }
}
