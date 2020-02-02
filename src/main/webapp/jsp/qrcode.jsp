<%@ page import="com.lushihao.qrcode.entity.business.Business" %>
<%@ page import="java.util.List" %>
<%@ page import="com.lushihao.qrcode.entity.qrcode.QRCodeTemple" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>创建二维码</title>
    <script type="text/javascript">
        var qrcode = {
            message: '',
            templeCode: '',
            businessCode: '',
            fileName: ''
        };

        var filterTempleCode = {
            code: null
        };

        var filterBusinessCode = {
            code: null
        };

        function init() {
            getTemple();
            getBusiness();
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

        function filterTemple(id) {
            filterTempleCode.code = document.getElementById(id).value;
        }

        function filterBusiness(id) {
            filterBusinessCode.code = document.getElementById(id).value;
        }

        function changeQRCodeMessage(id) {
            qrcode.message = document.getElementById(id).value;
        }

        function changeQRCodeTempleCode(id) {
            qrcode.templeCode = JSON.parse(document.getElementById(id).value).code;
        }

        function changeBusinessCode(id) {
            var budiness = JSON.parse(document.getElementById(id).value);
            document.getElementById("nowBusiness_name").innerHTML = budiness.name;
            document.getElementById("nowBusiness_address").innerHTML = budiness.address;
            document.getElementById("nowBusiness_phone").innerHTML = budiness.phone;
            document.getElementById("nowBusiness_businessName").innerHTML = budiness.businessName;
            qrcode.businessCode = budiness.code;
        }

        function changeQRCodeFileName(id) {
            qrcode.fileName = document.getElementById(id).value;
        }

        function post() {
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
                    }
                }
            }
            xhr.send(JSON.stringify(qrcode));
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
    <input id="filterTemple" style="width:66px" onchange="filterTemple(this.id)"/>
    商家:
    <input id="filterBusiness" style="width:66px" onchange="filterBusiness(this.id)"/>
</p>
<p>信&emsp;&emsp;息:
    <textarea type="text" id="message" style="width:200px;height: 80px"
              onchange="changeQRCodeMessage(this.id)"></textarea>
</p>
<p>模&emsp;&emsp;板:
    <select id="temples" style="width:205px;height: 25px" onchange="changeQRCodeTempleCode(this.id)">
    </select>
</p>
<p>商&emsp;&emsp;家:
    <select id="businesses" style="width:205px;height: 25px" onchange="changeBusinessCode(this.id)">
    </select>
</p>
<p>文&ensp;件&ensp;名:
    <input type="text" id="fileName" style="width:200px" onchange="changeQRCodeFileName(this.id)"/>
</p>
<input type="button" value="创建" onclick="post()"/>

<hr>

</body>
</html>