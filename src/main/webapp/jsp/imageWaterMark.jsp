<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加水印</title>
    <link rel="stylesheet" type="text/css" href="../css/public.css">
    <link rel="stylesheet" type="text/css" href="../css/imageWaterMark.css">
    <script src="../js/public.js"></script>
    <script src="../js/imageWaterMark.js"></script>
</head>
<body onload="init()">

<a class="toIndex" onclick="navigate('imageManage.jsp')" onMouseOver="makevisible(this,0)"
   onMouseOut="makevisible(this,1)">上一页</a>

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
                                                                                                onclick="init()"/><br><br>
        <span class="itemName">商家选择：</span><select class="topItemSelect" id="createBusinesses"></select><br><br>
        <span class="itemName">原&ensp;图&ensp;片：</span><input class="topItemSelect" type="file" id="createPath"
                                                             accept="image/jpeg, image/jpg, image/png"/><br><br>
        <span class="itemName">水印高度：</span><input class="topItemInput" type="text"
                                                  value="10"
                                                  id="createHeight"/><span class="unit">%</span><br><br>
        <span class="itemName">x&ensp;偏移量：</span><input class="topItemInput" type="text" value="0"
                                                        id="createX"/><span class="unit">%</span><br><br>
        <span class="itemName">y&ensp;偏移量：</span><input class="topItemInput" type="text" value="100"
                                                        id="createY"/><span class="unit">%</span><br><br>
        <span class="itemName">透&ensp;明&ensp;度：</span><input class="topItemInput" type="text" value="50"
                                                             id="createAlpha"/><span class="unit">%</span><br><br>
        <hr>
        <br>
        <span class="itemName">预&ensp;览&ensp;图：</span><input class="topItemInput" readonly="readonly" type="text"
                                                             id="createTest"/><br><br>

        <input class="topItemButton2" type="button" value="预览" onclick="test()"/>
        <input class="topItemButton" type="button" value="创建" onclick="create()"/>
    </div>
    <div class="topItem2">
        <br>
        <span class="topItemTitle">添加记录：</span><span
            style="color: #999;font-size: 12px;margin-left: 130px;line-height: 42px;font-weight: bold">仅用于预览，关闭页面消失</span><br><br><br>
        <table class="bottomItemTable1" id="tableTitle" style="visibility: hidden">
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