<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>水印管理</title>
    <link rel="stylesheet" type="text/css" href="../css/waterMark.css">
    <script src="../js/waterMark.js"></script>
</head>
<body onload="getBusiness()">

<a style="margin: 10px;padding: 10px;background-color: turquoise" href="index.jsp">首页</a>

<p style="background-color: turquoise;margin: 20px 10px 10px;padding: 10px;text-align: center;font-weight: bolder;color: white">
    添加水印
</p>
<div class="top">
    <div class="topItem1">
        <br>
        <span class="topItemTitle">添加水印：</span><br><br><br>
        商家搜索：<input class="topItemFilter" id="createCode"/><input class="topItemSearch" type="button"
                                                                  value="搜索"
                                                                  onclick="getBusiness()"/><br><br>
        商家选择：<select class="topItemSelect" id="createBusinesses"></select><br><br>
        商&emsp;&emsp;标：<select class="topItemSelect" id="createLogo">
        <option value="1">有</option>
        <option value="0">无</option>
    </select><br><br>
        文&emsp;&emsp;字：<select class="topItemSelect" id="createFont">
        <option value="1">有</option>
        <option value="0">无</option>
    </select><br><br>
        商标宽度：<input class="topItemInput" type="text" value="100" id="createWidth"/><br><br>
        商标高度：<input class="topItemInput" type="text" value="100" id="createHeight"/><br><br>
        x&ensp;偏移量：<input class="topItemInput" type="text" value="50" id="createX"/><br><br>
        y&ensp;偏移量：<input class="topItemInput" type="text" value="50" id="createY"/><br><br>
        图片位置：<input class="topItemSelect" type="file" id="createPath"/><br><br>

        <input class="topItemButton" type="button" value="创建" onclick="create()"/>
    </div>
</div>

</body>
</html>