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
                    if (result.bean.userType.type == "0") {
                        document.getElementById("code").innerText = result.bean.manager.code;
                        document.getElementById("businessItem").style.display = "none";
                        document.getElementById("businessName").innerText = result.bean.manager.name;
                        document.getElementById("phone").innerText = result.bean.manager.phone;
                        document.getElementById("address").innerText = result.bean.manager.address;
                    } else if (result.bean.userType.type == "1") {
                        document.getElementById("code").innerText = result.bean.business.code;
                        document.getElementById("name").innerText = result.bean.business.name;
                        document.getElementById("businessName").innerText = result.bean.business.businessName;
                        document.getElementById("phone").innerText = result.bean.business.phone;
                        document.getElementById("address").innerText = result.bean.business.address;
                        document.getElementById("businessModel").style.display = "none";
                        document.getElementById("templeModel").style.display = "none";
                    }
                    document.getElementById("type").innerText = result.bean.userType.name;
                    if (result.bean.count == -1) {
                        document.getElementById("count").innerText = "无限";
                    } else {
                        document.getElementById("count").innerText = result.bean.count;
                    }
                } else {
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(data));
}