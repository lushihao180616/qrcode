<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>图片截取</title>
    <link rel="stylesheet" type="text/css" href="../css/public.css">
    <link rel="stylesheet" type="text/css" href="../css/imageCut.css">
    <script src="../js/public.js"></script>
    <script src="../js/imageCut.js"></script>
</head>
<body>

<a class="toIndex" onclick="navigate('imageManage.jsp')" onMouseOver="makevisible(this,0)"
   onMouseOut="makevisible(this,1)">上一页</a>

<p class="topTitle">
    图片截取
</p>

<div class="top">
    <div class="topItem1">
        <br>
        <span class="topItemTitle">图片截取：</span></span><br><br><br>
        <span class="itemName">原&ensp;图&ensp;片：</span><input class="topItemSelect" type="file" id="createPath"
                                                             accept="image/jpeg, image/jpg, image/png"/><br><br>
        <span class="itemName">截取宽度：</span><input class="topItemInput" type="text"
                                                  value="100"
                                                  id="createWidth"/><span class="unit">%</span><br><br>
        <span class="itemName">截取高度：</span><input class="topItemInput" type="text"
                                                  value="100"
                                                  id="createHeight"/><span class="unit">%</span><br><br>
        <span class="itemName">x&ensp;偏移量：</span><input class="topItemInput" type="text"
                                                  value="0"
                                                  id="createX"/><span class="unit">%</span><br><br>
        <span class="itemName">y&ensp;偏移量：</span><input class="topItemInput" type="text"
                                                  value="0"
                                                  id="createY"/><span class="unit">%</span><br><br>
        <span class="itemName">透&ensp;明&ensp;度：</span><input class="topItemInput" type="text" value="0"
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
        <span class="topItemTitle">截取记录：</span><span
            style="color: #999;font-size: 12px;margin-left: 130px;line-height: 42px;font-weight: bold">仅用于预览，关闭页面消失</span><br><br><br>
        <table class="bottomItemTable1" id="tableTitle" style="visibility: hidden">
            <tr>
                <th class="bottomTh1">截取前</th>
                <th class="bottomTh2">截取后</th>
            </tr>
        </table>
        <div class="tableItems">
            <table class="bottomItemTable2" id="imageCuts">
            </table>
        </div>
    </div>
</div>

</body>
</html>