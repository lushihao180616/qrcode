<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>模板管理</title>
    <link rel="stylesheet" type="text/css" href="../css/temple.css">
    <script src="../js/temple.js"></script>
</head>
<body onload="init()">

<a style="margin: 10px;padding: 10px;background-color: burlywood" href="index.jsp">首页</a>

<p style="background-color: burlywood;margin: 20px 10px 10px;padding: 10px;text-align: center;font-weight: bolder;color: white">
    模板变动
</p>
<div class="top">
    <div class="topItem1">
        <br>
        <span class="topItemTitle">创建模板：</span><br><br><br>
        编&emsp;&emsp;号：<input class="topItemInput" type="text" id="createCode"/><br><br>
        价&emsp;&emsp;格：<input class="topItemInput" type="text" id="createMoney"/><br><br>
        仅二维码：<select class="topItemSelect" id="createIfOnly">
        <option value="1">是</option>
        <option value="0">否</option>
    </select><br><br>
        显示商标：<select class="topItemSelect" id="createIfShowLogo">
        <option value="1">是</option>
        <option value="0">否</option>
    </select><br><br>
        算法选择：<select class="topItemSelect" id="createArti">
        <option value="0">热门算法</option>
        <option value="1">最初算法</option>
    </select><br><br>
        背景透明：<select class="topItemSelect" id="createTransparent">
        <option value="1">是</option>
        <option value="0">否</option>
    </select><br><br>
        x&ensp;偏移量：<input class="topItemInput" type="text" id="createX"/><br><br>
        y&ensp;偏移量：<input class="topItemInput" type="text" id="createY"/><br><br>
        文&ensp;件&ensp;夹：<input class="topItemSelect" type="file" id="createTempleItemsPath" name="uploadFile"/><br><br>

        <input class="topItemButton" type="button" value="创建" onclick="create()"/>
    </div>

    <div class="topItem2">
        <br>
        <span class="topItemTitle">更新模板：</span><br><br><br>
        编&emsp;&emsp;号：<input class="topItemFilter" id="updateCode"/><input class="topItemSearch" type="button"
                                                                            value="搜索"
                                                                            onclick="updateSearch()"/><br><br>
        编&emsp;&emsp;号：<select class="topItemSelect" id="updateTemples"
                               onchange="updateTempleCode(this.id)"></select><br><br>
        价&emsp;&emsp;格：<input class="topItemInput" type="text" id="updateMoney"/><br><br>
        仅二维码：<select class="topItemSelect" id="updateIfOnly">
        <option value="1">是</option>
        <option value="0">否</option>
    </select><br><br>
        显示商标：<select class="topItemSelect" id="updateIfShowLogo">
        <option value="1">是</option>
        <option value="0">否</option>
    </select><br><br>
        算法选择：<select class="topItemSelect" id="updateArti">
        <option value="0">热门算法</option>
        <option value="1">最初算法</option>
    </select><br><br>
        背景透明：<select class="topItemSelect" id="updateTransparent">
        <option value="1">是</option>
        <option value="0">否</option>
    </select><br><br>
        x&ensp;偏移量：<input class="topItemInput" type="text" id="updateX"/><br><br>
        y&ensp;偏移量：<input class="topItemInput" type="text" id="updateY"/><br><br>
        文&ensp;件&ensp;夹：<input class="topItemSelect" id="updateTempleItemsPath" type="file" name="uploadFile"/><br><br>

        <input class="topItemButton" type="button" value="更新" onclick="update()"/>
    </div>

    <div class="topItem3">
        <br>
        <span class="topItemTitle">删除模板：</span><br><br><br>
        编&emsp;&emsp;号：<input class="topItemFilter" id="deleteCode"/><input class="topItemSearch" type="button"
                                                                            value="搜索"
                                                                            onclick="deleteSearch()"/><br><br>
        编&emsp;&emsp;号：<select class="topItemSelect" id="deleteTemples"
                               onchange="deleteTempleCode(this.id)"></select><br><br>
        <div style="height: 10px"></div>
        <div class="topItemSelect" id="deleteMoney"></div>
        <br>
        <div class="topItemSelect" id="deleteIfOnly"></div>
        <br>
        <div class="topItemSelect" id="deleteIfShowLogo"></div>
        <br>
        <div class="topItemSelect" id="deleteArti"></div>
        <br>
        <div class="topItemSelect" id="deleteTransparent"></div>
        <br>
        <div class="topItemSelect" id="deleteX"></div>
        <br>
        <div class="topItemSelect" id="deleteY"></div>
        <br>

        <input class="topItemButton" type="button" value="删除" onclick="deleteOne()"/>
    </div>
</div>

<p style="background-color: burlywood;margin: 100px 10px 10px;padding: 10px;text-align: center;font-weight: bolder;color: white">
    模板查询
</p>

<div class="bottom">
    <div class="topItem4">
        编号：<input class="bottomItemFilter" id="filterCode"/>
        <input class="topItemSearch" type="button" value="搜索" onclick="init()"/><br><br>

        <table class="bottomItemTable" id="temples"></table>
    </div>
</div>

</body>
</html>