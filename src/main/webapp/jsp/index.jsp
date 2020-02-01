<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <script type="text/javascript">
        var qrcode = {
            message: '',
            templeCode: '',
            businessCode: '',
            fileName: ''
        };

        function init(businessCode,templeCode, name, address, phone, businessName) {
            changeBusiness(businessCode, name, address, phone, businessName);
            changeQRCodeTempleCode(templeCode);
        }

        function changeQRCodeMessage(id) {
            qrcode.message = document.getElementById(id).value;
        }

        function changeQRCodeTempleCode(code) {
            qrcode.templeCode = code;
        }

        function changeBusiness(code, name, address, phone, businessName) {
            document.getElementById("nowBusiness_name").innerHTML = name;
            document.getElementById("nowBusiness_address").innerHTML = address;
            document.getElementById("nowBusiness_phone").innerHTML = phone;
            document.getElementById("nowBusiness_businessName").innerHTML = businessName;
            qrcode.businessCode = code;
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
<body onload="init('${requestScope.business[0].code}','${requestScope.temple[0].code}','${requestScope.business[0].name}','${requestScope.business[0].address}','${requestScope.business[0].phone}','${requestScope.business[0].businessName}')">

<p>商家信息: </p>
<p>名&emsp;&emsp;称: <i id="nowBusiness_name"></i></p>
<p>地&emsp;&emsp;址: <i id="nowBusiness_address"></i></p>
<p>电&emsp;&emsp;话: <i id="nowBusiness_phone"></i></p>
<p>联&ensp;系&ensp;人: <i id="nowBusiness_businessName"></i></p>

<hr>

<p>二维码信息: </p>
<p>信&emsp;&emsp;息:
    <input type="text" id="message" style="width:200px" onchange="changeQRCodeMessage(this.id)"/></p>
<p>模&emsp;&emsp;板:
    <select style="width:204px;height: 25px">
        <c:forEach var="templeItem" items="${requestScope.temple}">
            <option value="${templeItem.code}"
                    onclick="changeQRCodeTempleCode('${templeItem.code}')">${templeItem.code}</option>
        </c:forEach>
    </select>
</p>
<p>商&emsp;&emsp;家:
    <select style="width:204px;height: 25px">
        <c:forEach var="businessItem" items="${requestScope.business}">
            <option value="${businessItem.code}"
                    onclick="changeBusiness('${businessItem.code}','${businessItem.name}','${businessItem.address}','${businessItem.phone}','${businessItem.businessName}')">${businessItem.code}</option>
        </c:forEach>
    </select>
</p>
<p>文&ensp;件&ensp;名:
    <input type="text" id="fileName" style="width:200px" onchange="changeQRCodeFileName(this.id)"/>
</p>
<input type="submit" value="创建" onclick="post()"/>

<hr>

</body>
</html>