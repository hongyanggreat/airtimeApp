/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htc.services.airtime.config;

import com.htc.services.airtime.common.DateProc;
import com.htc.services.airtime.common.Tool;
import org.apache.log4j.Logger;

/**
 *
 * @author TUANPLA
 */
public class Monitor extends Thread {

    static final Logger logger = Logger.getLogger(Monitor.class);
    private static boolean NOFIRY = false;

    @Override
    public void run() {
        System.out.println("=====> Send Notify Statistic SMS BRAND Myname.vn Starting");
        double hm = 0;
        while (MyContext.running) {
//            String mailList = AppConfig.getConfigValueCache("LIST_MAIL_ERROR_QUANLITY");
//            ArrayList<String> toEmail = SendMail.parseListMail(mailList);
            hm = DateProc.getTimer();
            try {
//                if (hm < 8) {
//                    NOFIRY = false;
//                }
//                if (hm > 8 && !NOFIRY && hm < 8.5) {
//                    String subject = "![SMS BRAND HTC REPORT " + DateProc.createDDMMYYYY() + "]";
//                    String conntent = CDRStatistic.buildContentCDR();
//                    SendMail.sendMail(subject, conntent, toEmail, "[HTC BRAND] REPORT");
//                    NOFIRY = true;
//                }
                Thread.sleep(5 * 60 * 1000);
                Tool.debug("Report SMS BRAND HTC is live...NOFIRY:[" + NOFIRY + "]");
            } catch (Exception e) {
                logger.error(Tool.getLogMessage(e));
            }
        }
    }

    public void stopThread() {
        this.interrupt();
    }
}
