<%@ page import="com.lushihao.qrcode.entity.business.Business" %>
<%@ page import="java.util.List" %>
<%@ page import="com.lushihao.qrcode.entity.temple.QRCodeTemple" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>创建二维码</title>
    <script type="text/javascript">
        function init() {
            getTemple();
            getBusiness();
            getRecord();
        }

        function getTemple() {
            var filterTempleCode = {
                code: document.getElementById("filterTemple").value
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
                        temples.innerHTML = '';
                        for (var i = 0; i < templeList.length; i++) {
                            var option = document.createElement("option");
                            option.value = JSON.stringify(templeList[i]);
                            option.text = templeList[i].code;
                            temples.add(option);
                        }
                    }
                }
            }
            xhr.send(JSON.stringify(filterTempleCode));
        }

        function getBusiness() {
            var filterBusinessCode = {
                code: document.getElementById("filterBusiness").value
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
                        var businesses = document.getElementById("businesses");
                        businesses.innerHTML = '';
                        for (var i = 0; i < businessList.length; i++) {
                            var option = document.createElement("option");
                            option.value = JSON.stringify(businessList[i]);
                            option.text = businessList[i].code;
                            businesses.add(option);
                        }
                        if(businessList.length > 0){
                            document.getElementById("nowBusiness_name").innerHTML = businessList[0].name;
                            document.getElementById("nowBusiness_address").innerHTML = businessList[0].address;
                            document.getElementById("nowBusiness_phone").innerHTML = businessList[0].phone;
                            document.getElementById("nowBusiness_businessName").innerHTML = businessList[0].businessName;
                        }
                    }
                }
            }
            xhr.send(JSON.stringify(filterBusinessCode));
        }

        function getRecord() {
            var filterRecord = {
                templeCode: document.getElementById("filterRecordTemple").value,
                businessCode: document.getElementById("filterRecordBusiness").value,
                fileName: document.getElementById("filterFileName").value
            };
            var xhr = new XMLHttpRequest();
            xhr.open('POST', "http://localhost:8090/qrcode/qrcode/selectRecord", false);
            // 添加http头，发送信息至服务器时内容编码类型
            xhr.setRequestHeader('Content-type', 'application/json');
            xhr.setRequestHeader('dataType', 'json');
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200 || xhr.status == 304) {
                        var recordList = JSON.parse(xhr.responseText);
                        var records = document.getElementById("records");
                        records.innerHTML = '\n' +
                            '    <tr>\n' +
                            '        <th style="width: 100px">商家</th>\n' +
                            '        <th style="width: 100px">模板</th>\n' +
                            '        <th style="width: 100px">文件名</th>\n' +
                            '        <th style="width: 400px">位置</th>\n' +
                            '        <th style="width: 80px">价格</th>\n' +
                            '        <th style="width: 100px">创建时间</th>\n' +
                            '    </tr>';

                        for (var i = 0; i < recordList.length; i++) {
                            records.innerHTML += '\n' +
                                '    <tr>\n' +
                                '        <td style="width: 100px;border: 1px solid red">' + recordList[i].businessCode + '</td>\n' +
                                '        <td style="width: 100px;border: 1px solid black">' + recordList[i].templeCode + '</td>\n' +
                                '        <td style="width: 100px;border: 1px solid purple">' + recordList[i].fileName + '</td>\n' +
                                '        <td style="width: 400px;border: 1px solid blue">' + recordList[i].url + '</td>\n' +
                                '        <td style="width: 80px;border: 1px solid brown">' + recordList[i].money + '</td>\n' +
                                '        <td style="width: 100px;border: 1px solid green">' + recordList[i].saveTime + '</td>\n' +
                                '    </tr>';
                        }
                    }
                }
            }
            xhr.send(JSON.stringify(filterRecord));
        }

        function changeBusinessCode(id) {
            var budiness = JSON.parse(document.getElementById(id).value);
            document.getElementById("nowBusiness_name").innerHTML = budiness.name;
            document.getElementById("nowBusiness_address").innerHTML = budiness.address;
            document.getElementById("nowBusiness_phone").innerHTML = budiness.phone;
            document.getElementById("nowBusiness_businessName").innerHTML = budiness.businessName;
        }

        function create() {
            var createQRCode = {
                message: document.getElementById("message").value,
                templeCode: JSON.parse(document.getElementById("temples").value).code,
                businessCode: JSON.parse(document.getElementById("businesses").value).code,
                fileName: document.getElementById("fileName").value
            };
            var xhr = new XMLHttpRequest();
            xhr.open('POST', "http://localhost:8090/qrcode/qrcode/create", false);
            // 添加http头，发送信息至服务器时内容编码类型
            xhr.setRequestHeader('Content-type', 'application/json');
            xhr.setRequestHeader('dataType', 'json');
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200 || xhr.status == 304) {
                        var data = xhr.responseText;
                        alert(data);
                        init();
                        document.getElementById("message").value = '';
                        document.getElementById("temples").options[0].selected = true;
                        document.getElementById("businesses").options[0].selected = true;
                        document.getElementById("fileName").value = '';
                    }
                }
            }
            xhr.send(JSON.stringify(createQRCode));
        }
    </script>
</head>
<body onload="init()">
<hr>

<p>商家信息: </p>
<p>名&emsp;&emsp;称: <i id="nowBusiness_name"></i></p>
<p>地&emsp;&emsp;址: <i id="nowBusiness_address"></i></p>
<p>电&emsp;&emsp;话: <i id="nowBusiness_phone"></i></p>
<p>联&ensp;系&ensp;人: <i id="nowBusiness_businessName"></i></p>

<hr>

<p>二维码信息:</p>
<p>
    <input type="button" value="搜索" onclick="init()"/>
    模板:
    <input id="filterTemple" style="width:66px"/>
    商家:
    <input id="filterBusiness" style="width:66px"/>
</p>
<p>信&emsp;&emsp;息:
    <textarea type="text" id="message" style="width:200px;height: 80px"></textarea>
</p>
<p>模&emsp;&emsp;板:
    <select id="temples" style="width:205px;height: 25px">
    </select>
</p>
<p>商&emsp;&emsp;家:
    <select id="businesses" style="width:205px;height: 25px">
    </select>
</p>
<p>文&ensp;件&ensp;名:
    <input type="text" id="fileName" style="width:200px"/>
</p>
<input type="button" value="创建" onclick="create()"/>

<hr>
<p>创建记录:</p>
<p>
    <input type="button" value="搜索" onclick="getRecord()"/>
    商家:
    <input id="filterRecordBusiness" style="width:66px"/>
    模板:
    <input id="filterRecordTemple" style="width:66px"/>
    文件名:
    <input id="filterFileName" style="width:66px"/>
</p>
<table id="records">
</table>

</body>
</html>