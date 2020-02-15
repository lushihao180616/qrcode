<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>商家管理</title>
    <link rel="stylesheet" type="text/css" href="../css/business.css">
    <script src="../js/business.js"></script>
</head>
<body onload="init()">

<a style="margin: 10px;padding: 10px;background-color: cadetblue" href="index.jsp">首页</a>

<p style="background-color: cadetblue;margin: 20px 10px 10px;padding: 10px;text-align: center;font-weight: bolder;color: white">
    商家变动
</p>
<div class="top">
    <div class="topItem1">
        <br>
        <span class="topItemTitle">创建商家：</span><br><br><br>
        名&emsp;&emsp;称：<input class="topItemInput" type="text" id="createName"/><br><br>
        地&emsp;&emsp;址：<input class="topItemInput" type="text" id="createAddress"/><br><br>
        手&ensp;机&ensp;号：<input class="topItemInput" type="text" id="createPhone"/><br><br>
        联&ensp;系&ensp;人：<input class="topItemInput" type="text" id="createBusinessName"/><br><br>
        商&emsp;&emsp;标：<input class="topItemSelect" id="createLogo" type="file" name="uploadFile"/><br><br>

        <input class="topItemButton" type="button" value="创建" onclick="create()"/>
    </div>

    <div class="topItem2">
        <br>
        <span class="topItemTitle">修改商家：</span><br><br><br>
        编&emsp;&emsp;号：<input class="topItemFilter" id="modifyCode"/><input class="topItemSearch" type="button"
                                                                            value="搜索"
                                                                            onclick="modifySearch()"/><br><br>
        编&emsp;&emsp;号：<select class="topItemSelect" id="modifyBusinesses"
                               onchange="modifyBusinessCode(this.id)"></select><br><br>
        名&emsp;&emsp;称：<input class="topItemInput" type="text" id="modifyName"/><br><br>
        地&emsp;&emsp;址：<input class="topItemInput" type="text" id="modifyAddress"/><br><br>
        手&ensp;机&ensp;号：<input class="topItemInput" type="text" id="modifyPhone"/><br><br>
        联&ensp;系&ensp;人：<input class="topItemInput" type="text" id="modifyBusinessName"/><br><br>
        商&emsp;&emsp;标：<input class="topItemSelect" id="modifyLogo" type="file" name="uploadFile"/><br><br>

        <input class="topItemButton" type="button" value="更新" onclick="update()"/>
    </div>

    <div class="topItem3">
        <br>
        <span class="topItemTitle">删除商家：</span><br><br><br>
        编&emsp;&emsp;号：<input class="topItemFilter" id="deleteCode"/><input class="topItemSearch" type="button"
                                                                            value="搜索"
                                                                            onclick="deleteSearch()"/><br><br>
        编&emsp;&emsp;号：<select class="topItemSelect" id="deleteBusinesses"
                               onchange="deleteBusinessCode(this.id)"></select><br><br>
        <div style="height: 10px"></div>
        <div class="topItemSelect" id="deleteName"></div>
        <br>
        <div class="topItemSelect" id="deleteAddress"></div>
        <br>
        <div class="topItemSelect" id="deletePhone"></div>
        <br>
        <div class="topItemSelect" id="deleteBusinessName"></div>
        <br>

        <input class="topItemButton" type="submit" value="删除" onclick="deleteOne()"/>
    </div>
</div>

<p style="background-color: cadetblue;margin: 100px 10px 10px;padding: 10px;text-align: center;font-weight: bolder;color: white">
    商家查询
</p>

<div class="bottom">
    <div class="topItem4">
        编号：<input class="bottomItemFilter" id="filterCode"/>
        名称：<input class="bottomItemFilter" id="filterName"/>
        地址：<input class="bottomItemFilter" id="filterAddress"/>
        电话：<input class="bottomItemFilter" id="filterPhone"/>
        联系人：<input class="bottomItemFilter" id="filterBusinessName"/>
        <input class="topItemSearch" type="button" value="搜索" onclick="init()"/><br><br>

        <table class="bottomItemTable" id="buisnesses"></table>
    </div>
</div>

</body>
</html>