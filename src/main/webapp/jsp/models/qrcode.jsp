<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>创建二维码</title>
    <link rel="stylesheet" type="text/css" href="../../css/public.css">
    <link rel="stylesheet" type="text/css" href="../../css/models/qrcode.css">
    <script src="../../js/public.js"></script>
    <script src="../../js/models/qrcode.js"></script>
</head>
<body onload="init()">

<a class="toIndex" onclick="navigate('../index.jsp')" onMouseOver="makevisible(this,0)"
   onMouseOut="makevisible(this,1)">上一页</a>

<a class="toIndex" href="http://www.fhdq.net/">特殊符号</a>

<p class="topTitle">
    创建二维码
</p>
<div class="top">
    <div class="topItem1">
        <br>
        <span class="topItemTitle">类型及信息：</span><br><br><br>
        <span class="itemName">类&emsp;&emsp;型：</span><select class="topItemSelect" id="createLayout"
                                                             onchange="changeType(this.id)">
        <option value="text">文本</option>
        <option value="image">图片</option>
        <option value="video">视频</option>
        <option value="beautify">二维码美化</option>
    </select><br><br>
        <div id="textInfo">
            <span class="itemName2">信&emsp;&emsp;息：</span><textarea style="resize:none"
                                                                    class="topItemInput" type="text"
                                                                    id="textMessage"></textarea><br><br>
        </div>
        <div id="imageInfo" style="display: none">
            <span class="itemName">图&emsp;&emsp;片：</span><input class="topItemSelect" type="file" id="imageMessage"
                                                                accept="image/jpeg, image/jpg, image/png"/><br><br>
        </div>
        <div id="videoInfo" style="display: none">
            <span class="itemName">视&emsp;&emsp;频：</span><input class="topItemSelect" type="file" id="videoMessage"
                                                                accept="video/*"/><br><br>
        </div>
        <div id="beautifyInfo" style="display: none">
            <span class="itemName">原二维码：</span><input class="topItemSelect" type="file" id="beautifyMessage"
                                                      accept="image/jpeg, image/jpg, image/png"/><br><br>
        </div>
    </div>
    <div class="topItem2">
        <br>
        <span class="topItemTitle">其他：</span><br><br><br>
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
        <span class="itemName">码透明度：</span><input class="topItemInput" type="text" id="alpha"
                                                  value="0"/><span
            class="unit">%</span><br><br>
        <span id="backGroundLabel" class="itemName">背景图片：</span><input class="topItemSelect" id="backGround" type="file"
                                                                       name="uploadFile"
                                                                       accept="image/gif, image/jpeg, image/jpg, image/png"/><br><br>
        <span id="shortLengthLabel" class="itemName">较短边长：</span><input class="topItemInput" type="text"
                                                                        id="shortLength" value="1950"/><br><br>
        <span id="xLabel" class="itemName">x&ensp;偏移量：</span><input class="topItemInput" type="text" id="x"
                                                                    value="50"/><span
            class="unit">%</span><br><br>
        <span id="yLabel" class="itemName">y&ensp;偏移量：</span><input class="topItemInput" type="text" id="y"
                                                                    value="50"/><span
            class="unit">%</span><br><br>
        <span id="angleLabel" class="itemName">旋转角度：</span><input class="topItemInput" type="text" id="angle"
                                                                  value="0"/><span
            class="unit">°</span><br><br>
        <hr>
        <br>
        <span class="itemName">预&ensp;览&ensp;图：</span><input class="topItemInput" readonly="readonly" type="text"
                                                             id="createTest"/><br><br>

        <input class="topItemButton2" type="button" value="预览" onclick="test()"/>
        <input class="topItemButton" type="button" value="创建" onclick="create()"/>
    </div>
    <div class="topItem3">
        <br>
        <span class="topItemTitle">商家信息：</span><br><br><br>
        <br>
        <div class="topItemSelect" id="nowBusiness_name"></div>
        <br>
        <div class="topItemSelect" id="nowBusiness_address"></div>
        <br>
        <div class="topItemSelect" id="nowBusiness_phone"></div>
        <br>
        <div class="topItemSelect" id="nowBusiness_businessName"></div>
        <br>
        <span class="topItemTitle">模板信息：</span><br><br><br>
        <br>
        <div class="topItemSelect" id="nowTemple_price"></div>
        <br>
        <div class="topItemSelect" id="nowTemple_ifOnly"></div>
        <br>
        <div class="topItemSelect" id="nowTemple_ifShowLogo"></div>
        <br>
        <div class="topItemSelect" id="nowTemple_ifSelfBg"></div>
        <br>
        <div class="topItemSelect" id="nowTemple_path"></div>
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