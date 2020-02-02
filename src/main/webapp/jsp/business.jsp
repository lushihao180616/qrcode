<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>商家管理</title>
    <script type="text/javascript">
        function init() {
            getBusiness();
        }

        function getBusiness() {
            var filterBusiness = {
                code: document.getElementById("filterCode").value,
                name: document.getElementById("filterName").value,
                address: document.getElementById("filterAddress").value,
                phone: document.getElementById("filterPhone").value,
                businessName: document.getElementById("filterBusinessName").value
            };
            var xhr = new XMLHttpRequest();
            xhr.open('POST', "http://localhost:8090/qrcode/business/filter", false);
            // 添加http头，发送信息至服务器时内容编码类型
            xhr.setRequestHeader('Content-type', 'application/json');
            xhr.setRequestHeader('dataType', 'json');
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200 || xhr.status == 304) {
                        var businessList = JSON.parse(xhr.responseText);
                        var businesses = document.getElementById("buisnesses");
                        businesses.innerHTML = '\n' +
                            '    <tr>\n' +
                            '        <th style="width: 100px">编号</th>\n' +
                            '        <th style="width: 200px">名称</th>\n' +
                            '        <th style="width: 300px">地址</th>\n' +
                            '        <th style="width: 100px">电话</th>\n' +
                            '        <th style="width: 100px">联系人</th>\n' +
                            '    </tr>';

                        for (var i = 0; i < businessList.length; i++) {
                            businesses.innerHTML += '\n' +
                                '    <tr>\n' +
                                '        <td style="width: 100px;border: 1px solid red">' + businessList[i].code + '</td>\n' +
                                '        <td style="width: 200px;border: 1px solid black">' + businessList[i].name + '</td>\n' +
                                '        <td style="width: 300px;border: 1px solid purple">' + businessList[i].address + '</td>\n' +
                                '        <td style="width: 100px;border: 1px solid blue">' + businessList[i].phone + '</td>\n' +
                                '        <td style="width: 100px;border: 1px solid green">' + businessList[i].businessName + '</td>\n' +
                                '    </tr>';
                        }
                    }
                }
            }
            xhr.send(JSON.stringify(filterBusiness));
        }

        function create() {
            var createBusiness = {
                name: document.getElementById("createName").value,
                address: document.getElementById("createAddress").value,
                phone: document.getElementById("createPhone").value,
                businessName: document.getElementById("createBusinessName").value,
                logoSrc: document.getElementById("createLogo").value
            };
            var xhr = new XMLHttpRequest();
            xhr.open('POST', "http://localhost:8090/qrcode/business/create", false);
            // 添加http头，发送信息至服务器时内容编码类型
            xhr.setRequestHeader('Content-type', 'application/json');
            xhr.setRequestHeader('dataType', 'json');
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200 || xhr.status == 304) {
                        var data = xhr.responseText;
                        alert(data);
                        init();
                    }
                }
            }
            xhr.send(JSON.stringify(createBusiness));
        }

        function modifySearch() {
            var modifyFilterBusiness = {
                code: document.getElementById('modifyCode').value
            };
            var xhr = new XMLHttpRequest();
            xhr.open('POST', "http://localhost:8090/qrcode/business/filter", false);
            // 添加http头，发送信息至服务器时内容编码类型
            xhr.setRequestHeader('Content-type', 'application/json');
            xhr.setRequestHeader('dataType', 'json');
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200 || xhr.status == 304) {
                        var businessList = JSON.parse(xhr.responseText);
                        var modifyBusinesses = document.getElementById("modifyBusinesses");
                        modifyBusinesses.innerHTML = '';
                        for (var i = 0; i < businessList.length; i++) {
                            var option = document.createElement("option");
                            option.value = JSON.stringify(businessList[i]);
                            option.text = businessList[i].code;
                            modifyBusinesses.add(option);
                        }
                        if(businessList.length > 0){
                            modifyBusinessCode("modifyBusinesses")
                        }
                    }
                }
            }
            xhr.send(JSON.stringify(modifyFilterBusiness));
        }

        function modifyBusinessCode(id) {
            var budiness = JSON.parse(document.getElementById(id).value);
            document.getElementById("modifyName").value = budiness.name;
            document.getElementById("modifyAddress").value = budiness.address;
            document.getElementById("modifyPhone").value = budiness.phone;
            document.getElementById("modifyBusinessName").value = budiness.businessName;
        }

        function update() {
            var modifyBusiness = {
                code: JSON.parse(document.getElementById("modifyBusinesses").value).code,
                name: document.getElementById("modifyName").value,
                address: document.getElementById("modifyAddress").value,
                phone: document.getElementById("modifyPhone").value,
                businessName: document.getElementById("modifyBusinessName").value,
                logoSrc: document.getElementById("modifyLogo").value
            };
            var xhr = new XMLHttpRequest();
            xhr.open('POST', "http://localhost:8090/qrcode/business/update", false);
            // 添加http头，发送信息至服务器时内容编码类型
            xhr.setRequestHeader('Content-type', 'application/json');
            xhr.setRequestHeader('dataType', 'json');
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200 || xhr.status == 304) {
                        var data = xhr.responseText;
                        alert(data);
                        init();
                        document.getElementById("modifyBusinesses").innerHTML = '';
                        document.getElementById("modifyName").value = '';
                        document.getElementById("modifyAddress").value = '';
                        document.getElementById("modifyPhone").value = '';
                        document.getElementById("modifyBusinessName").value = '';
                        document.getElementById("modifyLogo").value = '';
                    }
                }
            }
            xhr.send(JSON.stringify(modifyBusiness));
        }

        function deleteSearch() {
            var deleteFilterBusiness = {
                code: document.getElementById('deleteCode').value
            };
            var xhr = new XMLHttpRequest();
            xhr.open('POST', "http://localhost:8090/qrcode/business/filter", false);
            // 添加http头，发送信息至服务器时内容编码类型
            xhr.setRequestHeader('Content-type', 'application/json');
            xhr.setRequestHeader('dataType', 'json');
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200 || xhr.status == 304) {
                        var businessList = JSON.parse(xhr.responseText);
                        var deleteBusinesses = document.getElementById("deleteBusinesses");
                        deleteBusinesses.innerHTML = '';
                        for (var i = 0; i < businessList.length; i++) {
                            var option = document.createElement("option");
                            option.value = JSON.stringify(businessList[i]);
                            option.text = businessList[i].code;
                            deleteBusinesses.add(option);
                        }
                        if(businessList.length > 0){
                            deleteBusinessCode("deleteBusinesses")
                        }
                    }
                }
            }
            xhr.send(JSON.stringify(deleteFilterBusiness));
        }

        function deleteBusinessCode(id) {
            var budiness = JSON.parse(document.getElementById(id).value);
            document.getElementById("deleteName").innerText = budiness.name;
            document.getElementById("deleteAddress").innerText = budiness.address;
            document.getElementById("deletePhone").innerText = budiness.phone;
            document.getElementById("deleteBusinessName").innerText = budiness.businessName;
        }

        function deleteOne() {
            var deleteBusiness = {
                code: JSON.parse(document.getElementById("deleteBusinesses").value).code,
            };
            var xhr = new XMLHttpRequest();
            xhr.open('POST', "http://localhost:8090/qrcode/business/delete", false);
            // 添加http头，发送信息至服务器时内容编码类型
            xhr.setRequestHeader('Content-type', 'application/json');
            xhr.setRequestHeader('dataType', 'json');
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200 || xhr.status == 304) {
                        var data = xhr.responseText;
                        alert(data);
                        init();
                        document.getElementById("deleteBusinesses").innerHTML = '';
                        document.getElementById("deleteName").innerHTML = '';
                        document.getElementById("deleteAddress").innerHTML = '';
                        document.getElementById("deletePhone").innerHTML = '';
                        document.getElementById("deleteBusinessName").innerHTML = '';
                    }
                }
            }
            xhr.send(JSON.stringify(deleteBusiness));
        }
    </script>
</head>
<body onload="init()">
<hr>

<p>创建商家: </p>
<p>名&emsp;&emsp;称:
    <input type="text" id="createName" style="width:200px"/></p>
<p>地&emsp;&emsp;址:
    <input type="text" id="createAddress" style="width:200px"/></p>
</p>
<p>手&ensp;机&ensp;号:
    <input type="text" id="createPhone" style="width:200px"/></p>
</p>
<p>联&ensp;系&ensp;人:
    <input type="text" id="createBusinessName" style="width:200px"/>
</p>
<p>商&emsp;&emsp;标:
    <input id="createLogo" type="file" name="uploadFile" style="width: 204px;height: 25px"/>
</p>

<input type="submit" value="创建" onclick="create()"/>

<hr>

<p>修改商家: </p>
<p>编号:
    <input type="button" value="搜索" onclick="modifySearch()"/>
    <input id="modifyCode" style="width:66px"/>
</p>
<p>编&emsp;&emsp;号:
    <select id="modifyBusinesses" style="width:205px;height: 25px" onchange="modifyBusinessCode(this.id)">
    </select>
</p>
<p>名&emsp;&emsp;称:
    <input type="text" id="modifyName" style="width:200px"/></p>
<p>地&emsp;&emsp;址:
    <input type="text" id="modifyAddress" style="width:200px"/></p>
</p>
<p>手&ensp;机&ensp;号:
    <input type="text" id="modifyPhone" style="width:200px"/></p>
</p>
<p>联&ensp;系&ensp;人:
    <input type="text" id="modifyBusinessName" style="width:200px"/>
</p>
<p>商&emsp;&emsp;标:
    <input id="modifyLogo" type="file" name="uploadFile" style="width: 204px;height: 25px"/>
</p>

<input type="submit" value="更新" onclick="update()"/>

<hr>

<p>删除商家: </p>

<p>编号:
    <input type="button" value="搜索" onclick="deleteSearch()"/>
    <input id="deleteCode" style="width:66px"/>
</p>
<p>编&emsp;&emsp;号:
    <select id="deleteBusinesses" style="width:205px;height: 25px" onchange="deleteBusinessCode(this.id)">
    </select>
</p>
<p>名&emsp;&emsp;称:
    <span id="deleteName" style="width:200px"></span></p>
<p>地&emsp;&emsp;址:
    <span id="deleteAddress" style="width:200px"></span></p>
</p>
<p>手&ensp;机&ensp;号:
    <span id="deletePhone" style="width:200px"></span></p>
</p>
<p>联&ensp;系&ensp;人:
    <span id="deleteBusinessName" style="width:200px"></span>
</p>

<input type="submit" value="删除" onclick="deleteOne()"/>

<hr>

<p>商家列表:</p>
<p>
    <input type="button" value="搜索" onclick="init()"/>
    编号:
    <input id="filterCode" style="width:66px"/>
    名称:
    <input id="filterName" style="width:66px"/>
    地址:
    <input id="filterAddress" style="width:66px"/>
    电话:
    <input id="filterPhone" style="width:66px"/>
    联系人:
    <input id="filterBusinessName" style="width:66px"/>
</p>
<table id="buisnesses">
</table>

<hr>
</body>
</html>