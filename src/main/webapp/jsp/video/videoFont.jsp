<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加文字</title>
    <link rel="stylesheet" type="text/css" href="../../css/public.css">
    <link rel="stylesheet" type="text/css" href="../../css/videoFont.css">
    <script src="../../js/public.js"></script>
    <script src="../../js/videoFont.js"></script>
</head>
<body>

<a class="toIndex" onclick="navigate('../videoManage.jsp')" onMouseOver="makevisible(this,0)"
   onMouseOut="makevisible(this,1)">上一页</a>

<p class="topTitle">
    添加文字
</p>

<div class="top">
    <div class="topItem1">
        <br>
        <span class="topItemTitle">添加文字：</span></span><br><br><br>
        <span class="itemName">原&ensp;视&ensp;频：</span><input class="topItemSelect" type="file" id="createPath"
                                                             accept="video/*"/><br><br>
        <span class="itemName2">信&emsp;&emsp;息：</span><textarea style="resize:none" class="topItemInput" type="text"
                                                                id="createMessage"
                                                                placeholder="可使用回车来添加多行文字"></textarea><br><br>
        <span class="itemName">x&ensp;偏移量：</span><input class="topItemInput" type="text"
                                                        value="0"
                                                        id="createX"/><span class="unit">%</span><br><br>
        <span class="itemName">y&ensp;偏移量：</span><input class="topItemInput" type="text"
                                                        value="0"
                                                        id="createY"/><span class="unit">%</span><br><br>
        <span class="itemName">字体大小：</span><input class="topItemInput" type="text"
                                                  value="24"
                                                  id="createSize"/><br><br>
        <span class="itemName">字体颜色：</span><select class="topItemSelect" id="createColor">
        <option value="black">黑色</option>
        <option value="white">白色</option>
        <option value="blue">蓝色</option>
        <option value="green">绿色</option>
        <option value="red">红色</option>
        <option value="yellow">黄色</option>
        <option value="pink">粉色</option>
        <option value="cyan">青色</option>
        <option value="gray">灰色</option>
        <option value="orange">橙色</option>
    </select><br><br>
        <span class="itemName">布局方式：</span><select class="topItemSelect" id="createLayout">
        <option value="left">左对齐</option>
        <option value="center">居中对齐</option>
        <option value="right">右对齐</option>
    </select><br><br>
        <hr>
        <br>
        <span class="itemName">预览视频：</span><input class="topItemInput" readonly="readonly" type="text"
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
            <table class="bottomItemTable2" id="videoFonts">
            </table>
        </div>
    </div>
</div>

</body>
</html>