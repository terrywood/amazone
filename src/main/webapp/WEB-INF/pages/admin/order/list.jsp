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
<%--<div style="overflow: auto;">
    <div style="padding: 0px 0px 10px 5px; font-size: large; font-weight: bolder; float: left;">
       Order List
    </div>
    <div style="padding: 0px 5px 10px 0px; float: right;">
        <input value="Add" class="button" style="cursor: pointer;" type="button" onclick="toAddParent()"/>
    </div>
</div>--%>
<div class="list-div">
    <form action="orderList.do" method="post" id="list_form">

        <div class="pics">
            <p>
                orderId: <input type="text"name="orderId" id="orderId" value="${params.orderId}">
                orderName: <input type="text" name="orderName" id="orderName" value="${params.orderName}">
<%--

               status: <input type="text" name="status" id="status" value="${status}">
                productId: <input type="text" name="productId" id="productId" value="${productId}">
--%>
                <button class="button" type="submit" >Search</button>
            </p>
<%--
            <div class="button_row">
              <button class="button" type="button" onclick="back();">Cancel</button>
            </div>--%>
        </div>


    </form>
</div>

<div class="listview">
    <table id="list_table">
        <thead>
        <tr style="font-size: 100">
            <th ></th>
            <th >ID</th>
            <th >Product</th>
            <th >点左/下载左/总数</th>
            <th >Name</th>
            <th >DeliveryDate</th>
            <th >OrderTime</th>
            <th >#</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="obj" items="${pageList.data}" varStatus="vs">
            <tr>
                <td><input type="checkbox"
                 <c:if test="${obj.clickedCount>0}">checked="checked" </c:if>
                 <c:if test="${obj.clickedCount!=obj.totalItem}">disabled="true" </c:if>
                 <c:if test="${obj.clickedCount==obj.totalItem  && obj.totalItem == fn:length(obj.orderItems)}">readonly="readonly" </c:if>
                  /></td>
                <td>${obj.id}</td>
                <td>${obj.orderItems[0].product.aliasName}</td>
                <td> ${obj.clickedCount}/${fn:length(obj.orderItems)}/${obj.totalItem}</td>
                <td>${obj.orderName}</td>
                <td><fmt:formatDate value="${obj.orderItems[0].deliveryDate}" pattern="yyyy-MM-dd"/> </td>
                <td><fmt:formatDate value="${obj.orderTime}" pattern="yyyy-MM-dd"/> </td>
                <td><a href="orderDetail.do?orderId=${obj.id}&searchOrderId=${param.orderId}&searchOrderName=${param.orderName}">Show Detail</a>
      <%--              &nbsp;&nbsp;&nbsp;
                    <a target="_blank" href="genScript.do?orderId=${obj.id}&searchOrderId=${param.orderId}&searchOrderName=${param.orderName}">General Script</a></td>
--%>            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div id="paginationDiv">
        <pg:pager url="orderList.do"
                  items="${pageList.totalItems}"
                  maxPageItems="20"
                  maxIndexPages="10"
                  export="pagerOffset=pageOffset,currentPageNumber=pageNumber"
                  index="center">
            <pg:param name="orderName" value="${param.orderName}"/>
            <pg:param name="orderId"  value="${param.orderId}"/>
            <pg:index >
                <ul id="pagination">
                    <pg:prev ifnull="true"><c:choose><c:when test="${not empty pageUrl}"><li class="previous"><a href="${pageUrl}">&#171; Previous</a></li></c:when><c:otherwise><li class="previousOff">&#171; Previous</li></c:otherwise></c:choose></pg:prev><pg:pages><c:choose><c:when test="${pageNumber == currentPageNumber}"><li class="active">${pageNumber}</li></c:when><c:otherwise><li><a href="${pageUrl}">${pageNumber}</a></li></c:otherwise></c:choose></pg:pages><pg:next ifnull="true"><c:choose><c:when test="${not empty pageUrl}"><li class="next"><a href="${pageUrl}">Next &#187;</a></li></c:when><c:otherwise><li class="nextOff">Next &#187;</li></c:otherwise></c:choose></pg:next>
                </ul>
            </pg:index>
        </pg:pager>
    </div>


</div>

    <div id="footer"><span>Powered By</span></div>
</div>


</html>