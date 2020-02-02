<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>商家管理</title>
    <script type="text/javascript">
        var createBusiness = {
            name: '',
            address: '',
            phone: '',
            businessName: '',
            logoSrc: ''
        };

        var filterBusiness = {
            code: '',
            name: '',
            address: '',
            phone: '',
            businessName: ''
        };

        function init() {
            getBusiness();
        }

        function getBusiness() {
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

        function changeName(id) {
            createBusiness.name = document.getElementById(id).value;
        }

        function changeAddress(id) {
            createBusiness.address = document.getElementById(id).value;
        }

        function changePhone(id) {
            createBusiness.phone = document.getElementById(id).value;
        }

        function changeBusinessName(id) {
            createBusiness.businessName = document.getElementById(id).value;
        }

        function post() {
            createBusiness.logoSrc = document.getElementById('logo').value;
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
                    }
                }
            }
            xhr.send(JSON.stringify(createBusiness));
        }

        function filterCode(id) {
            filterBusiness.code = document.getElementById(id).value;
        }

        function filterName(id) {
            filterBusiness.name = document.getElementById(id).value;
        }

        function filterAddress(id) {
            filterBusiness.address = document.getElementById(id).value;
        }

        function filterPhone(id) {
            filterBusiness.phone = document.getElementById(id).value;
        }

        function filterBusinessName(id) {
            filterBusiness.businessName = document.getElementById(id).value;
        }
    </script>
</head>
<body onload="init()">
<hr>

<p>创建商家: </p>
<p>名&emsp;&emsp;称:
    <input type="text" id="name" style="width:200px" onchange="changeName(this.id)"/></p>
<p>地&emsp;&emsp;址:
    <input type="text" id="address" style="width:200px" onchange="changeAddress(this.id)"/></p>
</p>
<p>手&ensp;机&ensp;号:
    <input type="text" id="phone" style="width:200px" onchange="changePhone(this.id)"/></p>
</p>
<p>联&ensp;系&ensp;人:
    <input type="text" id="businessName" style="width:200px" onchange="changeBusinessName(this.id)"/>
</p>
<p>商&emsp;&emsp;标:
    <input id="logo" type="file" name="uploadFile" style="width: 204px;height: 25px"/>
</p>

<input type="submit" value="创建" onclick="post()"/>

<hr>

<p>商家列表:</p>
<p>
    <input type="button" value="搜索" onclick="init()"/>
    编号:
    <input id="filterCode" style="width:66px" onchange="filterCode(this.id)"/>
    名称:
    <input id="filterName" style="width:66px" onchange="filterName(this.id)"/>
    地址:
    <input id="filterAddress" style="width:66px" onchange="filterAddress(this.id)"/>
    电话:
    <input id="filterPhone" style="width:66px" onchange="filterPhone(this.id)"/>
    联系人:
    <input id="filterBusinessName" style="width:66px" onchange="filterBusinessName(this.id)"/>
</p>
<table id="buisnesses">
</table>

<hr>
</body>
</html>