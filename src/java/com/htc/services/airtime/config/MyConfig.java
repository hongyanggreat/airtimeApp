/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htc.services.airtime.config;

import org.apache.log4j.Logger;
import org.jconfig.Configuration;

/**
 *
 * @author Admin
 */
public class MyConfig {

    //***********
    //-------Define ------------
//    public static final String USERNAME = "AHP_GOLD";
    public static final String USERNAME = "AHPGOLD";
    public static final String PASSWORD = "GoldAhp123456";

    public static final String COMMAND = "DOWNLOAD";
    public static final String IP = "?";
    public static final String PARAM = "?";
    public static final String CATEGORY = " ";
    public static final String CONTENT = "?";
//    public static final String PASSWORD = "Gold_Ahp@!@#456";
    public static Configuration config;
    static Logger logger = Logger.getLogger(MyConfig.class);

    public static int getInt(String properties, int defaultVal, String categoryName) {
        try {
            return Integer.parseInt(config.getProperty(properties, defaultVal + "", categoryName));
        } catch (NumberFormatException e) {
            logger.error(e);
            return defaultVal;
        }
    }

    public static long getLong(String properties, long defaultVal, String categoryName) {
        try {
            return Long.parseLong(config.getProperty(properties, defaultVal + "", categoryName));
        } catch (NumberFormatException e) {
            logger.error(e);
            return defaultVal;
        }
    }

    public static Double getDouble(String properties, Double defaultVal, String categoryName) {
        try {
            return Double.parseDouble(config.getProperty(properties, defaultVal + "", categoryName));
        } catch (NumberFormatException e) {
            logger.error(e);
            return defaultVal;
        }
    }

    public static String getString(String properties, String defaultVal, String categoryName) {
        try {
            return config.getProperty(properties, defaultVal, categoryName);
        } catch (Exception e) {
            logger.error(e);
            return defaultVal;
        }
    }

    public static boolean getBoolean(String properties, boolean defaultVal, String categoryName) {
        try {
            return config.getProperty(properties, "0", categoryName).equals("1");
        } catch (Exception e) {
            logger.error(e);
            return defaultVal;
        }
    }
}
