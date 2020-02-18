<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>视频合并</title>
    <link rel="stylesheet" type="text/css" href="../css/public.css">
    <link rel="stylesheet" type="text/css" href="../css/video.css">
    <script src="../js/video.js"></script>
</head>
<body onload="init()">

<a class="toIndex" href="index.jsp">首页</a>

<p class="topTitle">
    视频处理
</p>
<div class="top">
    <div class="topItem1">
        <br>
        <span class="topItemTitle">拼接视频：</span></span><br><br><br>
        <span class="itemName">商家搜索：</span><input class="topItemFilter" id="createCode"/><input class="topItemSearch"
                                                                                                type="button"
                                                                                                value="搜索"
                                                                                                onclick="init()"/><br><br>
        <span class="itemName">商家选择：</span><select class="topItemSelect" id="createBusinesses"></select><br><br>
        <span class="itemName">原&ensp;视&ensp;频：</span><input class="topItemSelect" type="file" id="createPath"/><br><br>

        <input class="topItemButton" type="button" value="创建" onclick="create()"/>
    </div>
    <div class="topItem2">
        <br>
        <span class="topItemTitle">添加记录：</span><span
            style="color: #999;font-size: 12px;margin-left: 130px;line-height: 42px;font-weight: bold">仅用于预览，关闭页面消失</span><br><br><br>
        <table class="bottomItemTable1" id="tableTitle" style="visibility: hidden">
            <tr>
                <th class="bottomTh1">原视频</th>
                <th class="bottomTh2">生成视频</th>
            </tr>
        </table>
        <div class="tableItems">
            <table class="bottomItemTable2" id="videos">
            </table>
        </div>
    </div>
</div>

</body>
</html>