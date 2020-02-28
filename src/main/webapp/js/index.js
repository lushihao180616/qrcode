function userInfo() {
    var data = {};
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/user/filter", false);
    // 添加http头，发送信息至服务器时内容编码类型
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.setRequestHeader('dataType', 'json');
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            if (xhr.status == 200 || xhr.status == 304) {
                if (xhr.responseText == null || xhr.responseText == '') {
                    window.location.href = "error.jsp"
                    return
                }
                var userinfo = JSON.parse(xhr.responseText);
                document.getElementById("code").innerText = userinfo.business.code;
                document.getElementById("name").innerText = userinfo.business.name;
                document.getElementById("businessName").innerText = userinfo.business.businessName;
                document.getElementById("phone").innerText = userinfo.business.phone;
                document.getElementById("address").innerText = userinfo.business.address;
                document.getElementById("type").innerText = userinfo.userType.name;
                if (userinfo.count == -1) {
                    document.getElementById("count").innerText = "无限";
                } else {
                    document.getElementById("count").innerText = userinfo.count;
                }
            }
        }
    }
    xhr.send(JSON.stringify(data));
}