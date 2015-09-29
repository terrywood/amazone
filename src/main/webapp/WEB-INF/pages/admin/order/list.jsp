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
    <form action="" method="post" id="list_form">

        <div class="pics">
            <p>
                orderId: <input type="text"name="orderId" id="orderId" value="${orderId}">
                orderName: <input type="text" name="orderName" id="orderName" value="${orderName}">
                status: <input type="text" name="status" id="status" value="${status}">
                productId: <input type="text" name="productId" id="productId" value="${productId}">

            </p>

            <div class="button_row">
                <button class="button" type="button" onclick="save()">Search</button>
             <%--   <button class="button" type="button" onclick="back();">Cancel</button>--%>
            </div>
        </div>


    </form>
</div>

<div class="listview">
    <table id="list_table">
        <thead>
        <tr style="font-size: 100">
            <th width="100">menu id</th>
            <th width="100">name</th>
            <th width="100">selected</th>
            <th width="100">toggle</th>
            <th width="100">type</th>

        </tr>
        </thead>
        <tbody>
        <%--c:forEach var="parentScmpMenu" items="${parentScmpMenus}">
            <tr>
                <td>${parentScmpMenu.menuId}</td>
                <td>${parentScmpMenu.name}</td>
                <td>
                    <c:if test="${parentScmpMenu.selected==1}">true</c:if>
                    <c:if test="${parentScmpMenu.selected==0}">false</c:if>
                </td>
                <td>
                    <c:if test="${parentScmpMenu.toggle==1}">true</c:if>
                    <c:if test="${parentScmpMenu.toggle==0}">false</c:if>
                </td>
                <td>${parentScmpMenu.type}</td>
                <td>${parentScmpMenu.apiName}</td>
                <td>${parentScmpMenu.limit}</td>
                <td>${parentScmpMenu.sequence}</td>
                <td style="text-align: center">
                    <input value="Edit" class="button" style="cursor: pointer;" type="button"
                           onclick="modifyBtn(${parentScmpMenu.id});"/>
                    <input value="Sub Menu" class="button" style="cursor: pointer;" type="button"
                           onclick="modifySubMenuBtn(${parentScmpMenu.menuId});"/>
                    <input value="Delete" class="button" style="cursor: pointer;" type="button"
                           onclick="deleteBtn(${parentScmpMenu.id},${parentScmpMenu.menuId});"/>
                </td>
            </tr>

        </c:forEach>--%>

        </tbody>

     <%--   <tfoot>
        <tr>
            <td colspan="8"></td>
        </tr>
        </tfoot>--%>

    </table>

</div>

    <div id="footer"><span>Powered By</span></div>
</div>


</html>