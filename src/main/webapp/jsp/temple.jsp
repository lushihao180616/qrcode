<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>模板管理</title>
    <script type="text/javascript">
        var createTemple = {
            code: "",
            money: "0",
            ifOnly: true,
            ifShowLogo: true,
            arti: "0",
            transparent: true,
            x: 0,
            y: 0,
            templeItemsPath: ""
        };

        var filterTemple = {
            code: ""
        };

        function init() {
            getTemple();
        }

        function getTemple() {
            var xhr = new XMLHttpRequest();
            xhr.open('POST', "http://localhost:8090/qrcode/temple/filter", false);
            // 添加http头，发送信息至服务器时内容编码类型
            xhr.setRequestHeader('Content-type', 'application/json');
            xhr.setRequestHeader('dataType', 'json');
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200 || xhr.status == 304) {
                        var templeList = JSON.parse(xhr.responseText);
                        var temples = document.getElementById("temples");
                        temples.innerHTML = '\n' +
                            '    <tr>\n' +
                            '        <th style="width: 100px">编号</th>\n' +
                            '        <th style="width: 80px">价格</th>\n' +
                            '        <th style="width: 80px">仅二维码</th>\n' +
                            '        <th style="width: 80px">显示商标</th>\n' +
                            '        <th style="width: 120px">算法选择</th>\n' +
                            '        <th style="width: 80px">背景透明</th>\n' +
                            '        <th style="width: 80px">X偏移量</th>\n' +
                            '        <th style="width: 80px">Y偏移量</th>\n' +
                            '    </tr>';

                        for (var i = 0; i < templeList.length; i++) {
                            var ifOnly = ''
                            if (templeList[i].ifOnly) {
                                ifOnly += "是"
                            } else {
                                ifOnly += "否"
                            }
                            var ifShowLogo = ''
                            if (templeList[i].ifShowLogo) {
                                ifShowLogo += "是"
                            } else {
                                ifShowLogo += "否"
                            }
                            var transparent = ''
                            if (templeList[i].transparent) {
                                transparent += "是"
                            } else {
                                transparent += "否"
                            }
                            var arti = ''
                            if (templeList[i].arti == '0') {
                                arti += "热门算法"
                            } else if (templeList[i].arti == '1') {
                                arti += "最初算法"
                            } else if (templeList[i].arti == '2') {
                                arti += "三角算法"
                            }
                            temples.innerHTML += '\n' +
                                '    <tr>\n' +
                                '        <td style="width: 100px;border: 1px solid red">' + templeList[i].code + '</td>\n' +
                                '        <td style="width: 80px;border: 1px solid black">' + templeList[i].money + '</td>\n' +
                                '        <td style="width: 80px;border: 1px solid blue">' + ifOnly + '</td>\n' +
                                '        <td style="width: 80px;border: 1px solid purple">' + ifShowLogo + '</td>\n' +
                                '        <td style="width: 120px;border: 1px solid green">' + arti + '</td>\n' +
                                '        <td style="width: 80px;border: 1px solid crimson">' + transparent + '</td>\n' +
                                '        <td style="width: 80px;border: 1px solid midnightblue">' + templeList[i].x + '</td>\n' +
                                '        <td style="width: 80px;border: 1px solid darkolivegreen">' + templeList[i].y + '</td>\n' +
                                '    </tr>';
                        }
                    }
                }
            }
            xhr.send(JSON.stringify(filterTemple));
        }

        function changeCode(id) {
            createTemple.code = document.getElementById(id).value;
        }

        function changeMoney(id) {
            createTemple.money = document.getElementById(id).value;
        }

        function changeIfOnly(id) {
            createTemple.ifOnly = Boolean(parseInt(document.getElementById(id).value));
        }

        function changeIfShowLogo(id) {
            createTemple.ifShowLogo = Boolean(parseInt(document.getElementById(id).value));
        }

        function changeArti(id) {
            createTemple.arti = document.getElementById(id).value;
        }

        function changeTransparent(id) {
            createTemple.transparent = Boolean(parseInt(document.getElementById(id).value));
        }

        function changeX(id) {
            createTemple.x = parseInt(document.getElementById(id).value);
        }

        function changeY(id) {
            createTemple.y = parseInt(document.getElementById(id).value);
        }

        function post() {
            createTemple.templeItemsPath = document.getElementById('templeItemsPath').value;
            var xhr = new XMLHttpRequest();
            xhr.open('POST', "http://localhost:8090/qrcode/temple/create", false);
            // 添加http头，发送信息至服务器时内容编码类型
            xhr.setRequestHeader('Content-type', 'application/json');
            xhr.setRequestHeader('dataType', 'json');
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200 || xhr.status == 304) {
                        var data = xhr.responseText;
                        alert(data);
                    }
                }
            }
            xhr.send(JSON.stringify(createTemple));
        }

        function filterCode(id) {
            filterTemple.code = document.getElementById(id).value;
        }

    </script>
</head>
<body onload="init()">
<hr>

<p>创建模板: </p>
<p>编&emsp;&emsp;号:
    <input type="text" id="code" style="width:200px" onchange="changeCode(this.id)"/></p>
<p>价&emsp;&emsp;格:
    <input type="text" id="money" style="width:200px" onchange="changeMoney(this.id)"/></p>
</p>
<p>仅二维码:
    <select id="ifOnly" style="width:205px;height: 25px" onchange="changeIfOnly(this.id)">
        <option value="1">是</option>
        <option value="0">否</option>
    </select>
</p>
<p>显示商标:
    <select id="ifShowLogo" style="width:205px;height: 25px" onchange="changeIfShowLogo(this.id)">
        <option value="1">是</option>
        <option value="0">否</option>
    </select>
</p>
<p>算法选择:
    <select id="arti" style="width:205px;height: 25px" onchange="changeArti(this.id)">
        <option value="0">热门算法</option>
        <option value="1">最初算法</option>
        <option value="2">三角算法</option>
    </select>
</p>
<p>背景透明:
    <select id="transparent" style="width:205px;height: 25px" onchange="changeTransparent(this.id)">
        <option value="1">是</option>
        <option value="0">否</option>
    </select>
</p>
<p>X&ensp;偏移量:
    <input type="text" id="x" style="width:200px" onchange="changeX(this.id)"/>
</p>
<p>Y&ensp;偏移量:
    <input type="text" id="y" style="width:200px" onchange="changeY(this.id)"/>
</p>
<p>文&ensp;件&ensp;夹:
    <input id="templeItemsPath" type="file" name="uploadFile" style="width: 204px;height: 25px"/>
</p>

<input type="submit" value="创建" onclick="post()"/>

<hr>

<p>模板列表:</p>
<p>
    <input type="button" value="搜索" onclick="init()"/>
    编号:
    <input id="filterCode" style="width:66px" onchange="filterCode(this.id)"/>
</p>
<table id="temples">
</table>

<hr>
</body>
</html>