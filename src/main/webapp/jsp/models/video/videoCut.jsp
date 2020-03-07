<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>视频截取</title>
    <link rel="stylesheet" type="text/css" href="../../../css/public.css">
    <link rel="stylesheet" type="text/css" href="../../../css/models/video/videoCut.css">
    <script src="../../../js/public.js"></script>
    <script src="../../../js/models/video/videoCut.js"></script>
</head>
<body>

<a class="toIndex" onclick="navigate('../videoManage.jsp')" onMouseOver="makevisible(this,0)"
   onMouseOut="makevisible(this,1)">上一页</a>

<p class="topTitle">
    视频截取
</p>

<div class="top">
    <div class="topItem1">
        <br>
        <span class="topItemTitle">视频截取：</span></span><br><br><br>
        <span class="itemName">原&ensp;视&ensp;频：</span><input class="topItemSelect" type="file" id="createPath"
                                                             accept="video/*"/><br><br>
        <span class="itemName">开始时间：</span><input class="topItemInput" type="text"
                                                  value="0"
                                                  id="createStart"/><span class="unit">秒</span><br><br>
        <span class="itemName">结束时间：</span><input class="topItemInput" type="text"
                                                  value="1"
                                                  id="createEnd"/><span class="unit">秒</span><br><br>

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
            <table class="bottomItemTable2" id="videoCuts">
            </table>
        </div>
    </div>
</div>

</body>
</html>