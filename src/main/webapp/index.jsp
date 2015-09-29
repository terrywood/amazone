<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gt.bmf.service.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
    GfQueryLogService gfQueryLogService = (GfQueryLogService)wac.getBean("gfQueryLogService") ;

   out.println( request.getRemoteHost());
%>
<html>
<meta http-equiv="refresh"content="5;url=index.jsp">
<body>
<div>Lock Status: <%=gfQueryLogService.isLockBuyAction()%>  <button onclick="window.location=('action.jsp?a=1')">Un Lock Buy Action</button></div>
<br/>
<div>Lock Status: <%=gfQueryLogService.isLockSaleAction()%> <button onclick="window.location=('action.jsp?a=2')">Un Lock Sale Action</button></div>

</body>

</html>