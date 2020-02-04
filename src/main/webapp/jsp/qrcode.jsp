<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>创建二维码</title>
    <link rel="stylesheet" type="text/css" href="../css/qrcode.css">
    <script src="../js/qrcode.js"></script>
</head>
<body onload="init()">

<a style="margin: 10px;padding: 10px;background-color: lightcoral" href="index.jsp">首页</a>

<p style="background-color: lightcoral;margin: 20px 10px 10px;padding: 10px;text-align: center;font-weight: bolder;color: white">
    二维码创建
</p>
<div class="top">
    <div class="topItem1">
        <br>
        <span class="topItemTitle">二维码信息：</span><br><br><br>
        模&emsp;&emsp;板：<input class="topItemFilter" id="filterTemple" style="margin-right: 25px"/>商&emsp;&emsp;家：<input class="topItemFilter" id="filterBusiness"/><input class="topItemSearch" type="button"
                                                                            value="搜索"
                                                                            onclick="init()"/><br><br>
        信&emsp;&emsp;息：<input class="topItemInput" type="text" id="message"/><br><br>
        模&emsp;&emsp;板：<select class="topItemSelect" id="temples"></select><br><br>
        商&emsp;&emsp;家：<select class="topItemSelect" id="businesses"
                               onchange="updateBusinessCode(this.id)"></select><br><br>
        文&ensp;件&ensp;名：<input class="topItemInput" type="text" id="fileName"/><br><br>

        <input class="topItemButton" type="button" value="创建" onclick="create()"/>
    </div>
    <div class="topItem2">
        <br>
        <span class="topItemTitle">商家信息：</span><br><br><br>

        <div style="height: 10px"></div>
        <div class="topItemSelect" id="nowBusiness_name"></div>
        <br>
        <div class="topItemSelect" id="nowBusiness_address"></div>
        <br>
        <div class="topItemSelect" id="nowBusiness_phone"></div>
        <br>
        <div class="topItemSelect" id="nowBusiness_businessName"></div>
    </div>
    <div class="topItem3">
        <br>
        <span class="topItemTitle">模板信息：</span><br><br><br>
    </div>
</div>

<p style="background-color: lightcoral;margin: 100px 10px 10px;padding: 10px;text-align: center;font-weight: bolder;color: white">
    二维码查询
</p>

<div class="bottom">
    <div class="topItem4">
        商家：<input class="bottomItemFilter" id="filterRecordBusiness"/>
        模板：<input class="bottomItemFilter" id="filterRecordTemple"/>
        文件名：<input class="bottomItemFilter" id="filterFileName"/>
        <input class="topItemSearch" type="button" value="搜索" onclick="getRecord()"/><br><br>

        <table class="bottomItemTable" id="records"></table>
    </div>
</div>

</body>
</html>