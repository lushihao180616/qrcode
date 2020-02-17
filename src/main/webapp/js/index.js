function checkComputer() {
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