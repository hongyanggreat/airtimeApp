<%-- 
    Document   : login
    Created on : Oct 31, 2017, 11:32:08 AM
    Author     : Admin
--%>

<%@page import="java.net.NetworkInterface"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.net.InetAddress"%>
<%@page import="com.htc.services.airtime.common.Tool"%>
<%@page import="com.htc.services.airtime.dao.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            String ip = Tool.getClientIpAddr(request);
            out.println("IP:"+ip+"<br/>");
            out.println("IP:"+request.getRemoteAddr()+"<br/>");
            out.println(InetAddress.getLocalHost().getHostAddress()+"<br/>");
            
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while(e.hasMoreElements())
            {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements())
                {
                    InetAddress i = (InetAddress) ee.nextElement();
                    out.println(i.getHostAddress()+"<br/>");
                }
            }
            
            String  url = "http://duongnh.com/test";
            String domainName = Tool.getDomainName(url);
            out.println("domainName:"+domainName+"<br/>");
            
            Account acc = Account.checkLogin("tkcon3", "7c6bew7sxnzZaU4j3x","kqxs");
            System.out.println(acc);
            if (acc != null) {
                out.println("TEN cua ban:" + acc.getUserName());
                System.out.println("TEN cua ban:" + acc.getUserName());
            } else {
                out.println("Khong ton tai tai khoan");
            }
        %>
    </body>
</html>
