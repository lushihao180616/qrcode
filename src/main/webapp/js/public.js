//当前模式花费金豆
var beanCostCount = 0;

function navigate(url) {
    window.location.href = url;
}

function makevisible(cur, which) {
    if (which == 0)
        cur.style.opacity = 0.6;
    else
        cur.style.opacity = 1;
}

function getBeanCost(type) {
    var beanCost = {
        type: type
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/beanCost/filter", false);
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
                    beanCostCount = result.bean;
                } else {
                    window.location.href = "../error.jsp"
                    return
                }
            }
        }
    }
    xhr.send(JSON.stringify(beanCost));
}