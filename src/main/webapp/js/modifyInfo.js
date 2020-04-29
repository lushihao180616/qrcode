//当前用户信息
var nowUserInfo = null;

function init() {
    userInfo();
    if (nowUserInfo != null) {
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

function modify() {
    if (nowUserInfo == null) {
        alert("获取用户信息错误，无法修改");
        return;
    }
    var name = document.getElementById("name").value;
    var businessName = document.getElementById("businessName").value;
    var phone = document.getElementById("phone").value;
    var address = document.getElementById("address").value;
    var logo = document.getElementById("logo").value;
    var code = "";
    if (nowUserInfo.userType.type == "0") {
        code = nowUserInfo.manager.code;
    } else if (nowUserInfo.userType.type == "1") {
        code = nowUserInfo.business.code;
    }
    if (!check(name, businessName, phone, address)) {
        return;
    }
    var modifyInfo = {
        code: code,
        name: name,
        businessName: businessName,
        phone: phone,
        address: address,
        logo: logo,
        type: nowUserInfo.userType.type
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/user/modify", false);
    // 添加http头，发送信息至服务器时内容编码类型
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.setRequestHeader('dataType', 'json');
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            if (xhr.status == 200 || xhr.status == 304) {
                if (xhr.responseText == null || xhr.responseText == '') {
                    window.location.href = "../error.jsp"
                    return
                }
                var result = JSON.parse(xhr.responseText);
                if (result.ifSuccess) {
                    alert(result.info);
                } else {
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(modifyInfo));
}

function check(name, businessName, phone, address) {
    var checkStr = '';
    if (nowUserInfo.userType.type == "1") {
        if (name == "" || name == null) {
            checkStr += '商家必须填写 '
        }
    }
    if (businessName == "" || businessName == null) {
        checkStr += '姓名必须填写 '
    }
    if (phone == '' || phone == null) {
        checkStr += '手机号必须填写 '
    }
    if (address == '' || address == null) {
        checkStr += '地址必须填写 '
    }
    if (checkStr != '') {
        alert(checkStr);
        return false;
    }
    return true;
}