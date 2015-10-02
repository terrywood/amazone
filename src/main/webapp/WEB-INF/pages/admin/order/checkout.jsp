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
        <div style="padding: 0px 0px 10px 5px; font-size: larger; font-weight: bolder; float: left;">
           Check out order list by cookie
        </div>
    </div>

    <form action="${pageContext.request.contextPath}/admin/checkout.do" method="post" style="margin-bottom: 30px;" onSubmit="return checkForm();" enctype="multipart/form-data">
        <input type="hidden" name="category" value="view_news" />
        <input type="hidden" name="sound" value="default" />
        <div class="formRow">
            <div class="label">Cookie</div>
            <div class="field">
                <textarea style="width: 600px" rows="10"  name="cookie"></textarea>
            </div>
        </div>
        <div class="formRow">
            <div class="label">Page</div>
            <div class="field">
                <select name="page">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select>
            </div>
        </div>

        <div class="formRow">
            <div class="label">&nbsp;</div>
            <div class="field"><input type="submit" value="Submit" /></div>
        </div>
    </form>
    <div id="footer"><span>Powered By</span></div>
</div>
</html>