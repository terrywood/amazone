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
                orderId: <input type="text"name="orderId" id="orderId" value="${orderId}">
                orderName: <input type="text" name="orderName" id="orderName" value="${orderName}">

               status: <input type="text" name="status" id="status" value="${status}">
                productId: <input type="text" name="productId" id="productId" value="${productId}">

            </p>

            <div class="button_row">
                <button class="button" type="submit" >Search</button>
             <%--   <button class="button" type="button" onclick="back();">Cancel</button>--%>
            </div>
        </div>


    </form>
</div>

<div class="listview">
    <table id="list_table">
        <thead>
        <tr style="font-size: 100">
            <th width="100">id</th>
            <th width="100">name</th>
            <th width="100">orderTime</th>
            <th width="100">#</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach var="obj" items="${pageList.data}">
            <tr>
                <td>${obj.id}</td>
                <td>${obj.orderName}</td>
                <td><fmt:formatDate value="${obj.orderTime}" pattern="yyyy-MM-dd"/> </td>
                <td>detail</td>

                </td>
            </tr>
        </c:forEach>
        </tbody>

      <tfoot>
        <tr>
            <td colspan="4">

            </td>
        </tr>
        </tfoot>

    </table>

   <div>
    <pg:pager url="orderList.do" maxPageItems="20" items="${pageList.totalItems}" export="currentPageNumber=pageNumber">
        <pg:first>
            <a href="${pageUrl}">首页</a>
        </pg:first>
        <pg:prev>
            <a href="${pageUrl }">上一页</a>
        </pg:prev>
        <pg:pages>
            <c:choose>
                <c:when test="${currentPageNumber eq pageNumber}">
                    <font color="red">${pageNumber }</font>
                </c:when>
                <c:otherwise>
                    <a href="${pageUrl }">${pageNumber }</a>
                </c:otherwise>
            </c:choose>
        </pg:pages>
        <pg:next>
            <a href="${pageUrl }">下一页</a>
        </pg:next>
        <pg:last>
            <a href="${pageUrl }">尾页</a>
        </pg:last>
    </pg:pager>
   </div>
</div>

    <div id="footer"><span>Powered By</span></div>
</div>


</html>