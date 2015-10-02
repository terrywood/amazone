<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="no-js" lang="en">
<head>
    <title>LKK</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="" />
    <%@ include file="/include.inc.jsp"%>
    <%@ include file="/include.js.jsp"%>
</head>
<%@ include file="../topBar.jsp"%>
<div id="bodyContainer">
<div style="overflow: auto;">
    <div style="padding: 0px 0px 10px 5px; font-size: large; font-weight: bolder; float: left;">
       Order ID : ${param.orderId}
    </div>
    <div style="padding: 0px 5px 10px 0px; float: right;">
        <input value="Add" class="button" style="cursor: pointer;" type="button" onclick="toAddParent()"/>
    </div>
</div>



<div class="listview">
    <table id="list_table">
        <thead>
        <tr style="font-size: 100">
            <th >trackId</th>
            <th >status</th>
            <th >deliveryDate</th>
            <th >product</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="obj" items="${list}">
            <tr>
                <td>${obj.trackId}</td>
                <td>${obj.status}</td>
                <td><fmt:formatDate value="${obj.deliveryDate}" pattern="yyyy-MM-dd"/> </td>
                <td>${obj.product.name}</td>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>



</div>

    <div id="footer"><span>Powered By</span></div>
</div>


</html>