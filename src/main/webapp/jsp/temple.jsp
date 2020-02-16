<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>模板管理</title>
    <link rel="stylesheet" type="text/css" href="../css/public.css">
    <link rel="stylesheet" type="text/css" href="../css/temple.css">
    <script src="../js/temple.js"></script>
</head>
<body onload="init()">

<a style="margin: 10px;padding: 10px;background-color: burlywood" href="index.jsp">首页</a>
<div style="float: right;margin-top: -5px;margin-right: 10px">
    <label>更新模板二维码：</label><input class="topItemSelect_up" type="file" id="downLoadTemple"/><input
        class="topItemSearch_up"
        type="button" value="确定"
        onclick="downLoadTemple()">
</div>

<p style="background-color: burlywood;margin: 20px 10px 10px;padding: 10px;text-align: center;font-weight: bolder;color: white">
    模板变动
</p>
<div class="top">
    <div class="topItem1">
        <br>
        <span class="topItemTitle">创建模板：</span><br><br><br>
        <span class="itemName">编&emsp;&emsp;号：</span><input class="topItemInput" type="text" id="createCode"/><br><br>
        <span class="itemName">价&emsp;&emsp;格：</span><input class="topItemInput" type="text" id="createMoney"/><br><br>
        <span class="itemName">背景宽度：</span><input class="topItemInput" type="text" value="1950"
                                                  id="createWidth"/><br><br>
        <span class="itemName">背景高度：</span><input class="topItemInput" type="text" value="1950"
                                                  id="createHeight"/><br><br>
        <span class="itemName">子图数量：</span><input class="topItemInput" type="text" value="1"
                                                  id="createIconNum"/><br><br>
        <span class="itemName">x&ensp;偏移量：</span><input class="topItemInput" type="text" value="0"
                                                        id="createX"/><br><br>
        <span class="itemName">y&ensp;偏移量：</span><input class="topItemInput" type="text" value="0"
                                                        id="createY"/><br><br>
        <span class="itemName">旋转角度：</span><input class="topItemInput" type="text" value="0" id="createAngle"/><br><br>
        <span class="itemName">缩放倍数：</span><input class="topItemInput" type="text" value="1"
                                                  id="createMultiple"/><br><br>
        <span class="itemName">帧&ensp;管&ensp;理：</span><input class="topItemInput" type="text" value="0/0/0"
                                                             id="createFrame"/><br><br>
        <span class="itemName">模板图标：</span><input class="topItemSelect" type="file" id="createTempleItemsPath"
                                                  name="uploadFile"/><br><br>

        <input class="topItemButton" type="button" value="创建" onclick="create()"/>
    </div>

    <div class="topItem2">
        <br>
        <span class="topItemTitle">更新模板：</span><br><br><br>
        <span class="itemName">编&emsp;&emsp;号：</span><input class="topItemFilter" id="updateCode"/><input
            class="topItemSearch" type="button"
            value="搜索"
            onclick="updateSearch()"/><br><br>
        <span class="itemName">编&emsp;&emsp;号：</span><select class="topItemSelect" id="updateTemples"
                                                             onchange="updateTempleCode(this.id)"></select><br><br>
        <span class="itemName">价&emsp;&emsp;格：</span><input class="topItemInput" type="text" id="updateMoney"/><br><br>
        <span class="itemName">背景宽度：</span><input class="topItemInput" type="text" id="updateWidth"/><br><br>
        <span class="itemName">背景高度：</span><input class="topItemInput" type="text" id="updateHeight"/><br><br>
        <span class="itemName">子图数量：</span><input class="topItemInput" type="text" id="updateIconNum"/><br><br>
        <span class="itemName">x&ensp;偏移量：</span><input class="topItemInput" type="text" id="updateX"/><br><br>
        <span class="itemName">y&ensp;偏移量：</span><input class="topItemInput" type="text" id="updateY"/><br><br>
        <span class="itemName">旋转角度：</span><input class="topItemInput" type="text" id="updateAngle"/><br><br>
        <span class="itemName">缩放倍数：</span><input class="topItemInput" type="text" value="1"
                                                  id="updateMultiple"/><br><br>
        <span class="itemName">帧&ensp;管&ensp;理：</span><input class="topItemInput" type="text" value="0/0/0"
                                                             id="updateFrame"/><br><br>
        <span class="itemName">模板图标：</span><input class="topItemSelect" id="updateTempleItemsPath" type="file"
                                                  name="uploadFile"/><br><br>

        <input class="topItemButton" type="button" value="更新" onclick="update()"/>
    </div>

    <div class="topItem3">
        <br>
        <span class="topItemTitle">删除模板：</span><br><br><br>
        <span class="itemName">编&emsp;&emsp;号：</span><input class="topItemFilter" id="deleteCode"/><input
            class="topItemSearch" type="button"
            value="搜索"
            onclick="deleteSearch()"/><br><br>
        <span class="itemName">编&emsp;&emsp;号：</span><select class="topItemSelect" id="deleteTemples"
                                                             onchange="deleteTempleCode(this.id)"></select><br><br>
        <div style="height: 10px"></div>
        <div class="topItemSelect" id="deleteMoney"></div>
        <br>
        <div class="topItemSelect" id="deleteWidth"></div>
        <br>
        <div class="topItemSelect" id="deleteHeight"></div>
        <br>
        <div class="topItemSelect" id="deleteIconNum"></div>
        <br>
        <div class="topItemSelect" id="deleteX"></div>
        <br>
        <div class="topItemSelect" id="deleteY"></div>
        <br>
        <div class="topItemSelect" id="deleteAngle"></div>
        <br>
        <div class="topItemSelect" id="deleteMultiple"></div>
        <br>

        <input class="topItemButton" type="button" value="删除" onclick="deleteOne()"/>
    </div>
</div>

<p style="background-color: burlywood;margin: 100px 10px 10px;padding: 10px;text-align: center;font-weight: bolder;color: white">
    模板查询
</p>

<div class="bottom">
    <div class="topItem4">
        <div class="itemSearch">
            <span class="itemName">编&emsp;号：</span><input class="bottomItemFilter" id="filterCode"/>
        </div>
        <input class="topItemSearch bottomItemSearch" type="button" value="搜索" onclick="init()"/><br><br>

        <table class="bottomItemTable1">
            <tr>
                <th class="bottomTh1">编号</th>
                <th class="bottomTh2">价格</th>
                <th class="bottomTh3">仅二维码</th>
                <th class="bottomTh4">显示商标</th>
                <th class="bottomTh5">自定背景</th>
                <th class="bottomTh6">算法选择</th>
                <th class="bottomTh7">背景宽度</th>
                <th class="bottomTh8">背景高度</th>
                <th class="bottomTh9">子图数量</th>
                <th class="bottomTh10">X偏移量</th>
                <th class="bottomTh11">Y偏移量</th>
                <th class="bottomTh12">旋转角度</th>
                <th class="bottomTh13">缩放倍数</th>
                <th class="bottomTh14">帧管理</th>
                <th class="bottomTh15">模板样例</th>
            </tr>
        </table>
        <div class="tableItems">
            <table class="bottomItemTable2" id="temples">
            </table>
        </div>
    </div>
</div>

</body>
</html>