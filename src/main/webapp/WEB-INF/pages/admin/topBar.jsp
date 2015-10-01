<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="topBar">
    <ul>
        <li class="title">AMAZON CMS </li>
        <%--<li class="title"> <iframe height="15" src="http://1111.ip138.com/ic.asp"></iframe> </li>--%>
        <li><a href="${pageContext.request.contextPath}/admin/orderList.do">Order List</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/checkout.do">Check Out</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/myip.do">My IP</a></li>
<%--        <li class="logout"><a href="${appPath}/logout.do">Logout</a></li>--%>
        <li class="adminName">Admin</li>
    </ul>
    <div style="clear: both;">

    </div>
</div>