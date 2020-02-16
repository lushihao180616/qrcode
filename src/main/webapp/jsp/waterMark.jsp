<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>水印管理</title>
    <link rel="stylesheet" type="text/css" href="../css/public.css">
    <link rel="stylesheet" type="text/css" href="../css/waterMark.css">
    <script src="../js/waterMark.js"></script>
</head>
<body onload="getBusiness()">

<a class="toIndex" href="index.jsp">首页</a>

<p class="topTitle">
    添加水印
</p>
<div class="top">
    <div class="topItem1">
        <br>
        <span class="topItemTitle">添加水印：</span></span><br><br><br>
        <span class="itemName">商家搜索：</span><input class="topItemFilter" id="createCode"/><input class="topItemSearch"
                                                                                                type="button"
                                                                                                value="搜索"
                                                                                                onclick="getBusiness()"/><br><br>
        <span class="itemName">商家选择：</span><select class="topItemSelect" id="createBusinesses"></select><br><br>
        <span class="itemName">商&emsp;&emsp;标：</span><select class="topItemSelect" id="createLogo"
                                                             onchange="showSomeThing()">
        <option value="1">有</option>
        <option value="0">无</option>
    </select><br><br>
        <span class="itemName">文&emsp;&emsp;字：</span><select class="topItemSelect" id="createFont">
        <option value="1">有</option>
        <option value="0">无</option>
    </select><br><br>
        <span class="itemName">图片位置：</span><input class="topItemSelect" type="file" id="createPath"/><br><br>
        <span class="itemName" id="createWidthSpan">商标宽度：</span><input class="topItemInput" type="text" value="100"
                                                                       id="createWidth"/><br><br>
        <span class="itemName" id="createHeightSpan">商标高度：</span><input class="topItemInput" type="text" value="100"
                                                                        id="createHeight"/><br><br>
        <span class="itemName" id="createXSpan">x&ensp;偏移量：</span><input class="topItemInput" type="text" value="50"
                                                                         id="createX"/><br><br>
        <span class="itemName" id="createYSpan">y&ensp;偏移量：</span><input class="topItemInput" type="text" value="50"
                                                                         id="createY"/><br><br>

        <input class="topItemButton" type="button" value="创建" onclick="create()"/>
    </div>
    <div class="topItem2">
        <br>
        <span class="topItemTitle">添加记录：</span><span
            style="color: #999;font-size: 12px;margin-left: 130px;line-height: 42px;font-weight: bold">仅用于预览，关闭页面消失</span><br><br><br>
        <table class="bottomItemTable1">
            <tr>
                <th class="bottomTh1">加水印前</th>
                <th class="bottomTh2">加水印后</th>
            </tr>
        </table>
        <div class="tableItems">
            <table class="bottomItemTable2" id="waterMarks">
            </table>
        </div>
    </div>
</div>

</body>
</html>