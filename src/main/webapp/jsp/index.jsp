<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="referrer" content="no-referrer">
    <title>首页</title>
    <link rel="stylesheet" type="text/css" href="../css/public.css">
    <link rel="stylesheet" type="text/css" href="../css/index.css">
    <script src="../js/public.js"></script>
    <script src="../js/index.js"></script>
</head>
<body onload="userInfo()">

<p class="topTitle">
    用户信息
</p>

<div class="top">
    <div class="userInfo">
        <div class="leftDiv">
            <div class="userInfoRow">
                <span class="userInfoName">标识</span><span class="userInfoValue" id="code"></span>
            </div>
            <div class="userInfoRow">
                <span class="userInfoName">商家</span><span class="userInfoValue" id="name"></span>
            </div>
            <div class="userInfoRow">
                <span class="userInfoName">联系人</span><span class="userInfoValue" id="businessName"></span>
            </div>
            <div class="userInfoRow">
                <span class="userInfoName">手机号</span><span class="userInfoValue" id="phone"></span>
            </div>
            <div class="userInfoRow">
                <span class="userInfoName">地址</span><span class="userInfoValue" id="address"></span>
            </div>
        </div>
        <div class="logoDiv">
            <img class="logo"
                 src="../image/logo.jpg">
        </div>
        <div class="rigthDiv">
            <div class="userInfoRow">
                <span class="userInfoName">类型</span><span class="userInfoValue" id="type"></span>
            </div>
            <div class="userInfoRow">
                <span class="userInfoName">金豆</span><span class="userInfoValue" id="count"></span>
            </div>
        </div>
    </div>
</div>

<p class="middleTitle">
    页面信息
</p>

<div class="linkItems">
    <div class="lineItem1" onclick="navigate('qrcode.jsp')" onMouseOver="makevisible(this,0)"
         onMouseOut="makevisible(this,1)">
        <div>二&ensp;维&ensp;码&ensp;管&ensp;理</div>
    </div>
    <div class="lineItem2" onclick="navigate('business.jsp')" onMouseOver="makevisible(this,0)"
         onMouseOut="makevisible(this,1)">
        <div>商&emsp;家&emsp;管&emsp;理</div>
    </div>
    <div class="lineItem3" onclick="navigate('temple.jsp')" onMouseOver="makevisible(this,0)"
         onMouseOut="makevisible(this,1)">
        <div>模&emsp;板&emsp;管&emsp;理</div>
    </div>
    <div class="lineItem4" onclick="navigate('waterMark.jsp')" onMouseOver="makevisible(this,0)"
         onMouseOut="makevisible(this,1)">
        <div>水&emsp;印&emsp;管&emsp;理</div>
    </div>
    <div class="lineItem5" onclick="navigate('video.jsp')" onMouseOver="makevisible(this,0)"
         onMouseOut="makevisible(this,1)">
        <div>视&emsp;频&emsp;管&emsp;理</div>
    </div>
</div>

</body>
</html>