<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>创建二维码</title>
    <link rel="stylesheet" type="text/css" href="../css/public.css">
    <link rel="stylesheet" type="text/css" href="../css/qrcode.css">
    <script src="../js/public.js"></script>
    <script src="../js/qrcode.js"></script>
</head>
<body onload="init()">

<a class="toIndex" onclick="navigate('index.jsp')" onMouseOver="makevisible(this,0)"
   onMouseOut="makevisible(this,1)">首页</a>
<a class="toIndex" href="http://www.fhdq.net/">特殊符号</a>

<p class="topTitle">
    创建二维码
</p>
<div class="top">
    <div class="topItem1">
        <br>
        <span class="topItemTitle">二维码信息：</span><br><br><br>
        <span class="itemName">模&emsp;&emsp;板：</span><input class="topItemFilter" id="filterTemple"/><input
            class="topItemSearch"
            type="button"
            value="搜索"
            onclick="getTemple()"/><br><br>
        <span class="itemName">商&emsp;&emsp;家：</span><input class="topItemFilter" id="filterBusiness"/><input
            class="topItemSearch" type="button"
            value="搜索"
            onclick="getBusiness()"/><br><br>
        <span class="itemName">模&emsp;&emsp;板：</span><select class="topItemSelect" id="temples"
                                                             onchange="getTempleCode(this.id)"></select><br><br>
        <span class="itemName">商&emsp;&emsp;家：</span><select class="topItemSelect" id="businesses"
                                                             onchange="getBusinessCode(this.id)"></select><br><br>
        <span class="itemName">文&ensp;件&ensp;名：</span><input class="topItemInput" type="text" id="fileName"/><br><br>
        <span class="itemName2">信&emsp;&emsp;息：</span><textarea style="resize:none" class="topItemInput" type="text"
                                                                id="message"></textarea><br><br>
        <span id="backGroundLabel" class="itemName">背景图片：</span><input class="topItemSelect" id="backGround" type="file"
                                                                       name="uploadFile"/><br><br>
        <span id="shortLengthLabel" class="itemName">较短边长：</span><input class="topItemInput" type="text"
                                                                        id="shortLength" value="1950"/><br><br>
        <span id="xLabel" class="itemName">x&ensp;偏移量：</span><input class="topItemInput" type="text" id="x" value="0"/><span
            class="unit">%</span><br><br>
        <span id="yLabel" class="itemName">y&ensp;偏移量：</span><input class="topItemInput" type="text" id="y" value="0"/><span
            class="unit">%</span><br><br>
        <hr>
        <br>
        <span class="itemName">预&ensp;览&ensp;图：</span><input class="topItemInput" readonly="readonly" type="text"
                                                             id="createTest"/><br><br>

        <input class="topItemButton2" type="button" value="预览" onclick="test()"/>
        <input class="topItemButton" type="button" value="创建" onclick="create()"/>
    </div>
    <div class="topItem2">
        <br>
        <span class="topItemTitle">商家信息：</span><br><br><br>

        <div class="split"></div>
        <div class="topItemSelect" id="nowBusiness_name"></div>
        <br>
        <div class="topItemSelect" id="nowBusiness_address"></div>
        <br>
        <div class="topItemSelect" id="nowBusiness_phone"></div>
        <br>
        <div class="topItemSelect" id="nowBusiness_businessName"></div>
        <div class="split"></div>
    </div>
    <div class="topItem3">
        <br>
        <span class="topItemTitle">模板信息：</span><br><br><br>

        <div class="split"></div>
        <div class="topItemSelect" id="nowTemple_price"></div>
        <br>
        <div class="topItemSelect" id="nowTemple_ifOnly"></div>
        <br>
        <div class="topItemSelect" id="nowTemple_ifShowLogo"></div>
        <br>
        <div class="topItemSelect" id="nowTemple_ifSelfBg"></div>
        <br>
        <div class="topItemSelect" id="nowTemple_arti"></div>
        <br>
        <div class="topItemSelect" id="nowTemple_width_height"></div>
        <br>
        <div class="topItemSelect" id="nowTemple_x_y"></div>
        <br>
        <div class="topItemSelect" id="nowTemple_path"></div>
        <div class="split"></div>
    </div>
</div>

<p class="middleTitle">
    二维码查询
</p>

<div class="bottom">
    <div class="topItem4">
        <div class="itemSearch">
            <span class="itemName">商&emsp;家：</span><input class="bottomItemFilter" id="filterRecordBusiness"/>
        </div>
        <div class="itemSearch">
            <span class="itemName">模&emsp;板：</span><input class="bottomItemFilter" id="filterRecordTemple"/>
        </div>
        <div class="itemSearch">
            <span class="itemName">文件名：</span><input class="bottomItemFilter" id="filterFileName"/>
        </div>
        <input class="topItemSearch bottomItemSearch" type="button" value="搜索" onclick="getRecord()"/><br><br>

        <table class="bottomItemTable1">
            <tr>
                <th class="bottomTh1">商家</th>
                <th class="bottomTh2">模板</th>
                <th class="bottomTh3">文件名</th>
                <th class="bottomTh4">位置</th>
                <th class="bottomTh5">价格</th>
                <th class="bottomTh6">创建时间</th>
            </tr>
        </table>
        <div class="tableItems">
            <table class="bottomItemTable2" id="records">
            </table>
        </div>
    </div>
</div>

</body>
</html>