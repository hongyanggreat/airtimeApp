/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htc.services.airtime.client;

import com.htc.services.airtime.common.*;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import javax.xml.soap.*;
import org.apache.log4j.Logger;
import org.w3c.dom.NodeList;

/**
 *
 * @author TUANPLA
 */
public class SOAPClientService {

    static final Logger logger = Logger.getLogger(SOAPClientService.class);
//    public static final String URL = "http://10.58.128.105:9000/process/MPScharge?wsdl";//TEST
//    public static final String URL = "http://10.60.105.203:8174/process/MPScharge?wsdl";//REAL
    public static final String DOMAIN = "http://10.60.105.203:8174";//REAL
    public static final String PATH = "/process/MPScharge?wsdl";//REAL

    public static String mpschargeRequest(String cpRequestId,
            String msisdn,
            String username,
            String password,
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
        String result = "-1";
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            // Send SOAP Message to SOAP Server
            long startime = System.currentTimeMillis();
            URL endpoint
                    = new URL(new URL(DOMAIN),
                            PATH,
                            new URLStreamHandler() {
                        @Override
                        protected URLConnection openConnection(URL url) throws IOException {
                            URL target = new URL(url.toString());
                            URLConnection connection = target.openConnection();
                            // Connection settings
                            connection.setConnectTimeout(5000); // 10 sec
//                            connection.setReadTimeout(5000); // 10 sec
                            connection.setReadTimeout(60000); // 1 min
                            return (connection);
                        }
                    });
//            String url = URL;
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(cpRequestId, msisdn, username,
                    password, command, category, params, chargetime, subService, price, content, ip, data, otp),
                    endpoint);
//                    URL);
            // Process the SOAP Response
            result = getResponse(soapResponse);
            soapConnection.close();
            long entime = System.currentTimeMillis();
            System.out.println("Time out: " + (entime - startime));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error occurred while sending SOAP Request to Server 01");
            logger.error(Tool.getLogMessage(e));
        }
        return result;
    }

    private static SOAPMessage createSOAPRequest(String cpRequestId,
            String msisdn,
            String username,
            String password,
            String command,
            String category,
            String params,
            String chargetime,
            String subService,
            String price,
            String content,
            String ip,
            String data,
            String otp) throws SOAPException, IOException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        String serverURI = "http://mpschargews/xsd";
//        String serverURI = "http://tempuri.org/";
        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("xsd", serverURI);
        /*
         <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://mpschargews/xsd">
            <soapenv:Header/>
            <soapenv:Body>
               <xsd:mpschargeRequest>
                  <!--Optional:-->
                  <xsd:cp_request_id>009170518103356829</xsd:cp_request_id>
                  <!--Optional:-->
                  <xsd:msisdn>983970940</xsd:msisdn>
                  <!--Optional:-->
                  <xsd:username>xxxxx</xsd:username>
                  <!--Optional:-->
                  <xsd:password>xxxxx</xsd:password>
                  <!--Optional:-->
                  <xsd:command>DOWNLOAD</xsd:command>
                  <!--Optional:-->
                  <xsd:category>GAME50K</xsd:category>
                  <!--Optional:-->
                 <xsd:params>?</xsd:params>
                  <!--Optional:-->
                  <xsd:chargetime>20170518103356</xsd:chargetime>
                  <!--Optional:-->
                  <xsd:sub_service>SOHAGAME</xsd:sub_service>
                  <!--Optional:-->
                  <xsd:price>50000</xsd:price>
                  <!--Optional:-->
                  <xsd:content>?</xsd:content>
                  <!--Optional:-->
                  <xsd:ip>?</xsd:ip>
                  <!--Optional:-->
                  <xsd:data>FF5Vu6rpNPY/uzYhSoBISzuq35mXQ2m0BCT/yG7JDASfhb+jP0ZazJMwFd2z39tqhpVYug4jMBkv4g+JN/3i1fnbC8R6du94h5/95lj8E/DRmv/C0EQ9QF+oEVjVWSXwjEGzu5tt5lMrTkKsVaMZWK1EokDrPcjfhtT9MkHbRZCpTPXAqqgW28e8jyqTULwemLkgzbYsUbPnzlfPhlR5djoJhNw+bWDAQyHEMBb71wu/sMiRYxx++T4/T/0Q6DVQaZ2Pw5rzFO/C2bEr0+2w5EgxDhnqXGnmjdWCdSkYTZ4RGtoQmnSRTksMrbLROzkSVGqv2giudcCRdLyJ/lxnAc73olMkvrF/nFe50hTHvJ0hCLJFrnm1U3FDxoFCPqz8LblwCk3sRZa7bNemqD2XsmhCu4xe2a6zQF1QwocmkIHR5TiHTLsB96px7C1/RunelUbsIQZWNyN+ND5FUS4CFYQFekV7HfJSfqFYrcJ3hthdknzPJxBkSNDWPKUji7Q7l+Ke+RZEBFukUwYfwmijwdSn/2ho/NqhlN/MMmYqz7f+LgXBDysDeuvxyIqpPzCioBsbglZRDLFuWWkEbSHDv2ZmkRjASyXv6rFm/nSWkrJwV7aUp/LIIhG6OnyYAVkkFYCUIzYKiStuqAJ+gvXOJl7wBga545tM1VHHYv7RK78=SIG=ORgGCawCpAdiWt4gd%2F0nXbmMohOGN23%2FnkEZAMNvJ0KVTMDF4eXLOXDsdYOsjrvHEPVG8fO8eRI4VUYDlsDb0pcuyh47n5pWNTdQDRehchi0kgfnGBBk7vKyRUKAqEH27yiMrKKKRG%2FV3bT24WP8kpRNeaz0s8iOKo5rStdwLdXbzRPFohbLFxxyoR8qeLjIQt%2BvUfxsWp4VJ%2Fi7kmhQ8qygCyjTGbufcZ1wKRCpmWXtQVwU5nKsXn9NrrTNtUoy8Hls5wyFE080T%2B4E6gawHn%2BORAAmXt7mF1UOd%2FO%2Bp%2F2qyby9hDLNd6lDojO5kMHbqjmOd8IrR6QG3YADFqIPHfIKVCzJwRxiBbdcoh4v3t4lPyJZKl4GykumGpVpWACpTMHkMM%2BQ9P9MGOzpjegg6rSr1Sd%2By%2Fj6dvxIXGIaGmC6QOU9TwomDVwiEu9njgcqei5meRXi1a%2B7ef7FmlS7jjGL0rtpPcDAm14%2FiaVA6VjOOdEZcWAZuzAhgBVh2Dbc0v5pBH2Z9SkHunU2WjqcGaifPSs%2B9PZW0yt0lVPh0GdWgd5e%2BWeLJ3BOzf%2B9wkvHtqdSDZmAt2otTdCS2Du168h%2B0a4rbScMFHqo1OL4y9H3WxfjIoAtDzPt7QVGqrtFO2bb%2Bu1HV4EsoinYOlm2kCjLZIoXqRB5YjImChvawwc%3D</xsd:data>
                  <!--Optional:-->
                  <xsd:otp>1</xsd:otp>
               </xsd:mpschargeRequest>
            </soapenv:Body>
         </soapenv:Envelope>
         */
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("mpschargeRequest", "xsd");
        SOAPElement cp_request_id = soapBodyElem.addChildElement("cp_request_id", "xsd");
        cp_request_id.addTextNode(cpRequestId);
        SOAPElement msisdnElm = soapBodyElem.addChildElement("msisdn", "xsd");
        msisdnElm.addTextNode(msisdn);
        SOAPElement usernameElm = soapBodyElem.addChildElement("username", "xsd");
        usernameElm.addTextNode(username);
        SOAPElement passwordElm = soapBodyElem.addChildElement("password", "xsd");
        passwordElm.addTextNode(password);
        SOAPElement commandElm = soapBodyElem.addChildElement("command", "xsd");
        commandElm.addTextNode(command);
        SOAPElement categoryElm = soapBodyElem.addChildElement("category", "xsd");
        categoryElm.addTextNode(category);
        SOAPElement paramsElm = soapBodyElem.addChildElement("params", "xsd");
        paramsElm.addTextNode(params);
        SOAPElement chargetimeElm = soapBodyElem.addChildElement("chargetime", "xsd");
        chargetimeElm.addTextNode(chargetime);
        SOAPElement sub_serviceElm = soapBodyElem.addChildElement("sub_service", "xsd");
        sub_serviceElm.addTextNode(subService);
        SOAPElement priceElm = soapBodyElem.addChildElement("price", "xsd");
        priceElm.addTextNode(price);
        SOAPElement contentElm = soapBodyElem.addChildElement("content", "xsd");
        contentElm.addTextNode(content);
        SOAPElement ipElm = soapBodyElem.addChildElement("ip", "xsd");
        ipElm.addTextNode(ip);
        SOAPElement dataElm = soapBodyElem.addChildElement("data", "xsd");
        dataElm.addTextNode(data);
        SOAPElement otpElm = soapBodyElem.addChildElement("otp", "xsd");
        otpElm.addTextNode(otp);
//        Tool.debug("message: " + message);
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI + "mpschargeRequest");
        soapMessage.saveChanges();
        /* Print the request message */
//        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
//        Tool.debug();
        return soapMessage;
    }

    /**
     * Method used to print the SOAP Response
     */
    private static String getResponse(SOAPMessage soapResponse) throws Exception {
        String result = "-1";
        if (soapResponse != null) {
            SOAPBody body = soapResponse.getSOAPBody();
            soapResponse.writeTo(System.out);
            NodeList returnList = body.getElementsByTagName("ns:mpschargeResponse");
            if (returnList != null) {
                NodeList noteResult = returnList.item(0).getChildNodes();
                if (noteResult != null && noteResult.item(0).getNodeName().equalsIgnoreCase("ns:return")) {
                    result = noteResult.item(0).getTextContent();
                }
            }
        }
        return result;
    }
}
