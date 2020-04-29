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
    <link rel="stylesheet" type="text/css" href="../css/modifyInfo.css">
    <script src="../js/public.js"></script>
    <script src="../js/modifyInfo.js"></script>
</head>
<body onload="init()">

<a class="toIndex" onclick="navigate('index.jsp')" onMouseOver="makevisible(this,0)"
   onMouseOut="makevisible(this,1)">上一页</a>

<div class="top">
    <div class="topItem1">
        <br>
        <span class="topItemTitle">修改信息：</span><br><br><br>
        <span class="itemName" id="nameSpan">商&emsp;&emsp;家：</span><input class="topItemInput" type="text" id="name"/><br><br>
        <span class="itemName">姓&emsp;&emsp;名：</span><input class="topItemInput" type="text" id="businessName"/><br><br>
        <span class="itemName">手&ensp;机&ensp;号：</span><input class="topItemInput" type="text" id="phone"/><br><br>
        <span class="itemName">地&emsp;&emsp;址：</span><input class="topItemInput" type="text"
                                                            id="address"/><br><br>
        <span class="itemName">商&emsp;&emsp;标：</span><input class="topItemSelect" id="logo" type="file"
                                                            name="uploadFile"
                                                            accept="image/jpeg, image/jpg, image/png"/><br><br>

        <input class="topItemButton" type="button" value="修改" onclick="modify()"/>
    </div>
</div>
</body>
</html>