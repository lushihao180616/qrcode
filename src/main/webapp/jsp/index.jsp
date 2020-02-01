<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <script type="text/javascript">
        function changeBusiness(name, address, phone, businessName) {
            document.getElementById("nowBusiness_name").innerHTML = name;
            document.getElementById("nowBusiness_address").innerHTML = address;
            document.getElementById("nowBusiness_phone").innerHTML = phone;
            document.getElementById("nowBusiness_businessName").innerHTML = businessName;
        }
    </script>
</head>
<body onload="changeBusiness('${requestScope.business[0].name}','${requestScope.business[0].address}','${requestScope.business[0].phone}','${requestScope.business[0].businessName}')">
<p>商家信息: </p>
<p>名&emsp;&emsp;称: <i id="nowBusiness_name"></i></p>
<p>地&emsp;&emsp;址: <i id="nowBusiness_address"></i></p>
<p>电&emsp;&emsp;话: <i id="nowBusiness_phone"></i></p>
<p>联&ensp;系&ensp;人: <i id="nowBusiness_businessName"></i></p>
<hr>
<p>二维码信息: </p>
<form action="create" method="post">
    <p>信&emsp;&emsp;息: <input type="text" name="message" style="width:200px"/></p>
    <p>模&emsp;&emsp;板:
        <select name="templeCode" style="width:204px">
            <c:forEach var="templeItem" items="${requestScope.temple}">
                <option value="${templeItem.code}">${templeItem.code}</option>
            </c:forEach>
        </select>
    </p>
    <p>商&emsp;&emsp;家:
        <select name="businessCode" style="width:204px">
            <c:forEach var="businessItem" items="${requestScope.business}">
                <option value="${businessItem.code}"
                        onclick="changeBusiness('${businessItem.name}','${businessItem.address}','${businessItem.phone}','${businessItem.businessName}')">${businessItem.code}</option>
            </c:forEach>
        </select>
    </p>
    <p>文&ensp;件&ensp;名: <input type="text" name="fileName" style="width:200px"/></p>
    <input type="submit" value="Submit"/>
</form>
<hr>
</body>
</html>