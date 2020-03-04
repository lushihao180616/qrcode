<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>商家管理</title>
    <link rel="stylesheet" type="text/css" href="../css/public.css">
    <link rel="stylesheet" type="text/css" href="../css/business.css">
    <script src="../js/public.js"></script>
    <script src="../js/business.js"></script>
</head>
<body onload="init()">

<a class="toIndex" onclick="navigate('index.jsp')" onMouseOver="makevisible(this,0)"
   onMouseOut="makevisible(this,1)">上一页</a>

<p class="topTitle">
    商家变动
</p>
<div class="top">
    <div class="topItem1">
        <br>
        <span class="topItemTitle">创建商家：</span><br><br><br>
        <span class="itemName">名&emsp;&emsp;称：</span><input class="topItemInput" type="text" id="createName"/><br><br>
        <span class="itemName">地&emsp;&emsp;址：</span><input class="topItemInput" type="text"
                                                            id="createAddress"/><br><br>
        <span class="itemName">手&ensp;机&ensp;号：</span><input class="topItemInput" type="text" id="createPhone"/><br><br>
        <span class="itemName">联&ensp;系&ensp;人：</span><input class="topItemInput" type="text"
                                                             id="createBusinessName"/><br><br>
        <span class="itemName">商&emsp;&emsp;标：</span><input class="topItemSelect" id="createLogo" type="file"
                                                            name="uploadFile"
                                                            accept="image/jpeg, image/jpg, image/png"/><br><br>

        <input class="topItemButton" type="button" value="创建" onclick="create()"/>
    </div>

    <div class="topItem2">
        <br>
        <span class="topItemTitle">修改商家：</span><br><br><br>
        <span class="itemName">编&emsp;&emsp;号：</span><input class="topItemFilter" id="modifyCode"/><input
            class="topItemSearch" type="button"
            value="搜索"
            onclick="modifySearch()"/><br><br>
        <span class="itemName">编&emsp;&emsp;号：</span><select class="topItemSelect" id="modifyBusinesses"
                                                             onchange="modifyBusinessCode(this.id)"></select><br><br>
        <span class="itemName">名&emsp;&emsp;称：</span><input class="topItemInput" type="text" id="modifyName"/><br><br>
        <span class="itemName">地&emsp;&emsp;址：</span><input class="topItemInput" type="text"
                                                            id="modifyAddress"/><br><br>
        <span class="itemName">手&ensp;机&ensp;号：</span><input class="topItemInput" type="text" id="modifyPhone"/><br><br>
        <span class="itemName">联&ensp;系&ensp;人：</span><input class="topItemInput" type="text"
                                                             id="modifyBusinessName"/><br><br>
        <span class="itemName">商&emsp;&emsp;标：</span><input class="topItemSelect" id="modifyLogo" type="file"
                                                            name="uploadFile"
                                                            accept="image/jpeg, image/jpg, image/png"/><br><br>

        <input class="topItemButton" type="button" value="更新" onclick="update()"/>
    </div>

    <div class="topItem3">
        <br>
        <span class="topItemTitle">删除商家：</span><br><br><br>
        <span class="itemName">编&emsp;&emsp;号：</span><input class="topItemFilter" id="deleteCode"/><input
            class="topItemSearch" type="button"
            value="搜索"
            onclick="deleteSearch()"/><br><br>
        <span class="itemName">编&emsp;&emsp;号：</span><select class="topItemSelect" id="deleteBusinesses"
                                                             onchange="deleteBusinessCode(this.id)"></select><br><br>
        <div class="split"></div>
        <div class="topItemSelect itemName" id="deleteName"></div>
        <br>
        <div class="topItemSelect itemName" id="deleteAddress"></div>
        <br>
        <div class="topItemSelect itemName" id="deletePhone"></div>
        <br>
        <div class="topItemSelect itemName" id="deleteBusinessName"></div>
        <div class="split"></div>

        <input class="topItemButton" type="submit" value="删除" onclick="deleteOne()"/>
    </div>
</div>

<p class="middleTitle">
    商家查询
</p>

<div class="bottom">
    <div class="topItem4">
        <div class="itemSearch">
            <span class="itemName">编&emsp;号：</span><input class="bottomItemFilter" id="filterCode"/>
        </div>
        <div class="itemSearch">
            <span class="itemName">名&emsp;称：</span><input class="bottomItemFilter" id="filterName"/>
        </div>
        <div class="itemSearch">
            <span class="itemName">地&emsp;址：</span><input class="bottomItemFilter" id="filterAddress"/>
        </div>
        <div class="itemSearch">
            <span class="itemName">电&emsp;话：</span><input class="bottomItemFilter" id="filterPhone"/>
        </div>
        <div class="itemSearch">
            <span class="itemName">联系人：</span><input class="bottomItemFilter" id="filterBusinessName"/>
        </div>
        <input class="topItemSearch bottomItemSearch" type="button" value="搜索" onclick="init()"/><br><br>

        <table class="bottomItemTable1">
            <tr>
                <th class="bottomTh1">编号</th>
                <th class="bottomTh2">名称</th>
                <th class="bottomTh3">地址</th>
                <th class="bottomTh4">电话</th>
                <th class="bottomTh5">联系人</th>
            </tr>
        </table>
        <div class="tableItems">
            <table class="bottomItemTable2" id="buisnesses">
            </table>
        </div>
    </div>
</div>

</body>
</html>