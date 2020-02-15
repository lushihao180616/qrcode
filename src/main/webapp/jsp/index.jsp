<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>首页</title>
    <script type="text/javascript">
        function check() {
            var xhr = new XMLHttpRequest();
            xhr.open('POST', "http://localhost:8090/qrcode/check/check", false);
            // 添加http头，发送信息至服务器时内容编码类型
            xhr.setRequestHeader('Content-type', 'application/json');
            xhr.setRequestHeader('dataType', 'json');
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200 || xhr.status == 304) {
                        if (xhr.responseText == '0') {
                            window.location.href = "error.jsp"
                        }
                    }
                }
            }
            xhr.send();
        }
    </script>
</head>
<body onload="check()">

<p style="background-color: black;margin: 20px 10px 10px;padding: 10px;text-align: center;font-weight: bolder;color: white">
    页面信息
</p>
<p>
    <a style="margin: 10px;padding: 10px;background-color: lightcoral"
       href="qrcode.jsp">二&ensp;维&ensp;码&ensp;管&ensp;理</a>
</p>
<p>
    <a style="margin: 10px;padding: 10px;background-color: cadetblue" href="business.jsp">商&emsp;家&emsp;管&emsp;理</a>
</p>
<p>
    <a style="margin: 10px;padding: 10px;background-color: burlywood" href="temple.jsp">模&emsp;板&emsp;管&emsp;理</a>
</p>
<p>
    <a style="margin: 10px;padding: 10px;background-color: turquoise" href="waterMark.jsp">水&emsp;印&emsp;管&emsp;理</a>
</p>

<hr>

</body>
</html>