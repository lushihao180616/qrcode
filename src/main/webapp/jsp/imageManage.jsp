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
    <link rel="stylesheet" type="text/css" href="../css/imageManage.css">
    <script src="../js/public.js"></script>
    <script src="../js/imageManage.js"></script>
</head>
<body>

<a class="toIndex" onclick="navigate('index.jsp')" onMouseOver="makevisible(this,0)"
   onMouseOut="makevisible(this,1)">上一页</a>

<p class="topTitle">
    基础信息管理模块
</p>

<div class="linkItems">
    <div class="lineItem1" onclick="navigate('image.jsp')" onMouseOver="makevisible(this,0)"
         onMouseOut="makevisible(this,1)">
        <div>图&emsp;片&emsp;截&emsp;取</div>
    </div>
    <div class="lineItem2" onclick="navigate('imageWaterMark.jsp')" onMouseOver="makevisible(this,0)"
         onMouseOut="makevisible(this,1)">
        <div>图&emsp;片&emsp;水&emsp;印</div>
    </div>
</div>

</body>
</html>