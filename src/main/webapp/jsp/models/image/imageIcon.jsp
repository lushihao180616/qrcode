<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加图标</title>
    <link rel="stylesheet" type="text/css" href="../../../css/public.css">
    <link rel="stylesheet" type="text/css" href="../../../css/models/image/imageIcon.css">
    <script src="../../../js/public.js"></script>
    <script src="../../../js/models/image/imageIcon.js"></script>
</head>
<body>

<a class="toIndex" onclick="navigate('../imageManage.jsp')" onMouseOver="makevisible(this,0)"
   onMouseOut="makevisible(this,1)">上一页</a>

<p class="topTitle">
    添加图标
</p>

<div class="top">
    <div class="topItem1">
        <br>
        <span class="topItemTitle">添加图标：</span></span><br><br><br>
        <span class="itemName">原&ensp;图&ensp;片：</span><input class="topItemSelect" type="file" id="createPath"
                                                             accept="image/jpeg, image/jpg, image/png"/><br><br>
        <span class="itemName">添加图标：</span><input class="topItemSelect" type="file" id="createIcon"
                                                  accept="image/jpeg, image/jpg, image/png"/><br><br>
        <span class="itemName">图标宽度：</span><input class="topItemInput" type="text"
                                                  value="10"
                                                  id="createWidth"/><span class="unit">%</span><br><br>
        <span class="itemName">图标高度：</span><input class="topItemInput" type="text"
                                                  value="10"
                                                  id="createHeight"/><span class="unit">%</span><br><br>
        <span class="itemName">x&ensp;偏移量：</span><input class="topItemInput" type="text"
                                                        value="50"
                                                        id="createX"/><span class="unit">%</span><br><br>
        <span class="itemName">y&ensp;偏移量：</span><input class="topItemInput" type="text"
                                                        value="50"
                                                        id="createY"/><span class="unit">%</span><br><br>
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
                <th class="bottomTh1">添加前</th>
                <th class="bottomTh2">添加后</th>
            </tr>
        </table>
        <div class="tableItems">
            <table class="bottomItemTable2" id="imageIcons">
            </table>
        </div>
    </div>
</div>

</body>
</html>