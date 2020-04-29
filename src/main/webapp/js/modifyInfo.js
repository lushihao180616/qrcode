//当前用户信息
var nowUserInfo = null;

function init() {
    userInfo();
    if(nowUserInfo != null){
        if (nowUserInfo.userType.type == "0") {
            document.getElementById("nameSpan").style.display = "none";
            document.getElementById("name").style.display = "none";
            document.getElementById("businessName").value = nowUserInfo.manager.name;
            document.getElementById("phone").value = nowUserInfo.manager.phone;
            document.getElementById("address").value = nowUserInfo.manager.address;
        } else if (nowUserInfo.userType.type == "1") {
            document.getElementById("name").value = nowUserInfo.business.name;
            document.getElementById("businessName").value = nowUserInfo.business.businessName;
            document.getElementById("phone").value = nowUserInfo.business.phone;
            document.getElementById("address").value = nowUserInfo.business.address;
        }
    }
}

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
                var result = JSON.parse(xhr.responseText);
                if (result.ifSuccess) {
                    nowUserInfo = result.bean;
                } else {
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(data));
}