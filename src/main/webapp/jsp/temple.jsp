<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>模板管理</title>
    <script type="text/javascript">
        function init() {
            getTemple();
        }

        function getTemple() {
            var filterTemple = {
                code: document.getElementById("filterCode").value
            };
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

        function create() {
            var createTemple = {
                code: document.getElementById("createCode").value,
                money: document.getElementById("createMoney").value,
                ifOnly: Boolean(parseInt(document.getElementById("createIfOnly").value)),
                ifShowLogo: Boolean(parseInt(document.getElementById("createIfShowLogo").value)),
                arti: document.getElementById("createArti").value,
                transparent: Boolean(parseInt(document.getElementById("createTransparent").value)),
                x: parseInt(document.getElementById("createX").value),
                y: parseInt(document.getElementById("createY").value),
                templeItemsPath: document.getElementById('createTempleItemsPath').value
            };
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
                        init();
                        document.getElementById("createCode").value = '';
                        document.getElementById("createMoney").value = '';
                        document.getElementById("createIfOnly").options[0].selected = true;
                        document.getElementById("createIfShowLogo").options[0].selected = true;
                        document.getElementById("createArti").options[0].selected = true;
                        document.getElementById("createTransparent").options[0].selected = true;
                        document.getElementById("createX").value = '';
                        document.getElementById("createY").value = '';
                        document.getElementById('createTempleItemsPath').value = '';
                    }
                }
            }
            xhr.send(JSON.stringify(createTemple));
        }

        function updateSearch() {
            var updateFilterBusiness = {
                code: document.getElementById('updateCode').value
            };
            var xhr = new XMLHttpRequest();
            xhr.open('POST', "http://localhost:8090/qrcode/temple/filter", false);
            // 添加http头，发送信息至服务器时内容编码类型
            xhr.setRequestHeader('Content-type', 'application/json');
            xhr.setRequestHeader('dataType', 'json');
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200 || xhr.status == 304) {
                        var templeList = JSON.parse(xhr.responseText);
                        var updateTemples = document.getElementById("updateTemples");
                        updateTemples.innerHTML = '';
                        for (var i = 0; i < templeList.length; i++) {
                            var option = document.createElement("option");
                            option.value = JSON.stringify(templeList[i]);
                            option.text = templeList[i].code;
                            updateTemples.add(option);
                        }
                        if (templeList.length > 0) {
                            updateTempleCode("updateTemples")
                        }
                    }
                }
            }
            xhr.send(JSON.stringify(updateFilterBusiness));
        }
        
        function updateTempleCode(id) {
            var temple = JSON.parse(document.getElementById(id).value);
            document.getElementById("updateMoney").value = temple.money;
            if(temple.ifOnly){
                document.getElementById("updateIfOnly").options[0].selected = true;
            }else{
                document.getElementById("updateIfOnly").options[1].selected = true;
            }
            if(temple.ifShowLogo){
                document.getElementById("updateIfShowLogo").options[0].selected = true;
            }else{
                document.getElementById("updateIfShowLogo").options[1].selected = true;
            }
            if(temple.arti == '0'){
                document.getElementById("updateArti").options[0].selected = true;
            }else{
                document.getElementById("updateArti").options[1].selected = true;
            }
            if(temple.transparent){
                document.getElementById("updateTransparent").options[0].selected = true;
            }else{
                document.getElementById("updateTransparent").options[1].selected = true;
            }
            document.getElementById("updateX").value = temple.x;
            document.getElementById("updateY").value = temple.y;
        }

        function update() {
            var updateTemple = {
                code: JSON.parse(document.getElementById("updateTemples").value).code,
                money: document.getElementById("updateMoney").value,
                ifOnly: Boolean(parseInt(document.getElementById("updateIfOnly").value)),
                ifShowLogo: Boolean(parseInt(document.getElementById("updateIfShowLogo").value)),
                arti: document.getElementById("updateArti").value,
                transparent: Boolean(parseInt(document.getElementById("updateTransparent").value)),
                x: parseInt(document.getElementById("updateX").value),
                y: parseInt(document.getElementById("updateY").value),
                templeItemsPath: document.getElementById('updateTempleItemsPath').value
            };
            var xhr = new XMLHttpRequest();
            xhr.open('POST', "http://localhost:8090/qrcode/temple/update", false);
            // 添加http头，发送信息至服务器时内容编码类型
            xhr.setRequestHeader('Content-type', 'application/json');
            xhr.setRequestHeader('dataType', 'json');
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200 || xhr.status == 304) {
                        var data = xhr.responseText;
                        alert(data);
                        init();
                        document.getElementById("updateTemples").innerHTML = '';
                        document.getElementById("updateMoney").value = '';
                        document.getElementById("updateIfOnly").options[0].selected = true;
                        document.getElementById("updateIfShowLogo").options[0].selected = true;
                        document.getElementById("updateArti").options[0].selected = true;
                        document.getElementById("updateTransparent").options[0].selected = true;
                        document.getElementById("updateX").value = '';
                        document.getElementById("updateY").value = '';
                        document.getElementById('updateTempleItemsPath').value = '';
                    }
                }
            }
            xhr.send(JSON.stringify(updateTemple ));
        }

        function deleteSearch() {
            var deleteFilterBusiness = {
                code: document.getElementById('deleteCode').value
            };
            var xhr = new XMLHttpRequest();
            xhr.open('POST', "http://localhost:8090/qrcode/temple/filter", false);
            // 添加http头，发送信息至服务器时内容编码类型
            xhr.setRequestHeader('Content-type', 'application/json');
            xhr.setRequestHeader('dataType', 'json');
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200 || xhr.status == 304) {
                        var templeList = JSON.parse(xhr.responseText);
                        var deleteTemples = document.getElementById("deleteTemples");
                        deleteTemples.innerHTML = '';
                        for (var i = 0; i < templeList.length; i++) {
                            var option = document.createElement("option");
                            option.value = JSON.stringify(templeList[i]);
                            option.text = templeList[i].code;
                            deleteTemples.add(option);
                        }
                        if (templeList.length > 0) {
                            deleteTempleCode("deleteTemples")
                        }
                    }
                }
            }
            xhr.send(JSON.stringify(deleteFilterBusiness));
        }

        function deleteTempleCode(id) {
            var temple = JSON.parse(document.getElementById(id).value);
            document.getElementById("deleteMoney").innerText = temple.money;
            if(temple.ifOnly){
                document.getElementById("deleteIfOnly").innerText = '是';
            }else{
                document.getElementById("deleteIfOnly").innerText = '否';
            }
            if(temple.ifShowLogo){
                document.getElementById("deleteIfShowLogo").innerText = '是';
            }else{
                document.getElementById("deleteIfShowLogo").innerText = '否';
            }
            if(temple.arti == '0'){
                document.getElementById("deleteArti").innerText = '热门算法';
            }else{
                document.getElementById("deleteArti").innerText = '最初算法';
            }
            if(temple.transparent){
                document.getElementById("deleteTransparent").innerText = '是';
            }else{
                document.getElementById("deleteTransparent").innerText = '否';
            }
            document.getElementById("deleteX").innerText = temple.x;
            document.getElementById("deleteY").innerText = temple.y;
        }

        function deleteOne() {
            var deleteTemple = {
                code: JSON.parse(document.getElementById("deleteTemples").value).code,
            };
            var xhr = new XMLHttpRequest();
            xhr.open('POST', "http://localhost:8090/qrcode/temple/delete", false);
            // 添加http头，发送信息至服务器时内容编码类型
            xhr.setRequestHeader('Content-type', 'application/json');
            xhr.setRequestHeader('dataType', 'json');
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200 || xhr.status == 304) {
                        var data = xhr.responseText;
                        alert(data);
                        init();
                        document.getElementById("deleteTemples").innerHTML = '';
                        document.getElementById("deleteMoney").innerText = '';
                        document.getElementById("deleteIfOnly").innerText = '';
                        document.getElementById("deleteIfShowLogo").innerText = '';
                        document.getElementById("deleteArti").innerText = '';
                        document.getElementById("deleteTransparent").innerText = '';
                        document.getElementById("deleteX").innerText = '';
                        document.getElementById("deleteY").innerText = '';
                    }
                }
            }
            xhr.send(JSON.stringify(deleteTemple ));
        }
    </script>
</head>
<body onload="init()">
<hr>

<p>创建模板: </p>
<p>编&emsp;&emsp;号:
    <input type="text" id="createCode" style="width:200px"/></p>
<p>价&emsp;&emsp;格:
    <input type="text" id="createMoney" style="width:200px"/></p>
</p>
<p>仅二维码:
    <select id="createIfOnly" style="width:205px;height: 25px">
        <option value="1">是</option>
        <option value="0">否</option>
    </select>
</p>
<p>显示商标:
    <select id="createIfShowLogo" style="width:205px;height: 25px">
        <option value="1">是</option>
        <option value="0">否</option>
    </select>
</p>
<p>算法选择:
    <select id="createArti" style="width:205px;height: 25px">
        <option value="0">热门算法</option>
        <option value="1">最初算法</option>
        <%--<option value="2">三角算法</option>--%>
    </select>
</p>
<p>背景透明:
    <select id="createTransparent" style="width:205px;height: 25px">
        <option value="1">是</option>
        <option value="0">否</option>
    </select>
</p>
<p>X&ensp;偏移量:
    <input type="text" id="createX" style="width:200px"/>
</p>
<p>Y&ensp;偏移量:
    <input type="text" id="createY" style="width:200px"/>
</p>
<p>文&ensp;件&ensp;夹:
    <input id="createTempleItemsPath" type="file" name="uploadFile" style="width: 204px;height: 25px"/>
</p>

<input type="submit" value="创建" onclick="create()"/>

<hr>

<p>更新模板: </p>
<p>
    <input type="button" value="搜索" onclick="updateSearch()"/>
    编号:
    <input id="updateCode" style="width:66px"/>
</p>
<p>编&emsp;&emsp;号:
    <select id="updateTemples" style="width:205px;height: 25px" onchange="updateTempleCode(this.id)">
    </select>
</p>
<p>价&emsp;&emsp;格:
    <input type="text" id="updateMoney" style="width:200px"/></p>
</p>
<p>仅二维码:
    <select id="updateIfOnly" style="width:205px;height: 25px">
        <option value="1">是</option>
        <option value="0">否</option>
    </select>
</p>
<p>显示商标:
    <select id="updateIfShowLogo" style="width:205px;height: 25px">
        <option value="1">是</option>
        <option value="0">否</option>
    </select>
</p>
<p>算法选择:
    <select id="updateArti" style="width:205px;height: 25px">
        <option value="0">热门算法</option>
        <option value="1">最初算法</option>
        <%--<option value="2">三角算法</option>--%>
    </select>
</p>
<p>背景透明:
    <select id="updateTransparent" style="width:205px;height: 25px">
        <option value="1">是</option>
        <option value="0">否</option>
    </select>
</p>
<p>X&ensp;偏移量:
    <input type="text" id="updateX" style="width:200px"/>
</p>
<p>Y&ensp;偏移量:
    <input type="text" id="updateY" style="width:200px"/>
</p>
<p>文&ensp;件&ensp;夹:
    <input id="updateTempleItemsPath" type="file" name="uploadFile" style="width: 204px;height: 25px"/>
</p>

<input type="button" value="更新" onclick="update()"/>

<hr>

<p>删除模板: </p>
<p>
    <input type="button" value="搜索" onclick="deleteSearch()"/>
    编号:
    <input id="deleteCode" style="width:66px"/>
</p>
<p>编&emsp;&emsp;号:
    <select id="deleteTemples" style="width:205px;height: 25px" onchange="deleteTempleCode(this.id)">
    </select>
</p>
<p>价&emsp;&emsp;格:
    <span id="deleteMoney" style="width:200px"></span></p>
</p>
<p>仅二维码:
    <span id="deleteIfOnly" style="width:200px"></span></p>
</p>
<p>显示商标:
    <span id="deleteIfShowLogo" style="width:200px"></span></p>
</p>
<p>算法选择:
    <span id="deleteArti" style="width:200px"></span></p>
</p>
<p>背景透明:
    <span id="deleteTransparent" style="width:200px"></span></p>
</p>
<p>X&ensp;偏移量:
    <span id="deleteX" style="width:200px"></span></p>
</p>
<p>Y&ensp;偏移量:
    <span id="deleteY" style="width:200px"></span></p>
</p>

<input type="button" value="删除" onclick="deleteOne()"/>

<hr>

<p>模板列表:</p>
<p>
    <input type="button" value="搜索" onclick="init()"/>
    编号:
    <input id="filterCode" style="width:66px"/>
</p>
<table id="temples">
</table>

<hr>
</body>
</html>