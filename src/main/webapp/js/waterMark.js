var tableRow = [];

function init() {
    var filterBusinessCode = {
        code: document.getElementById("createCode").value
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/business/filter", false);
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
                var businesses = document.getElementById("createBusinesses");
                businesses.innerHTML = '';
                for (var i = 0; i < result.bean.length; i++) {
                    var option = document.createElement("option");
                    option.value = JSON.stringify(result.bean[i]);
                    option.text = result.bean[i].code;
                    businesses.add(option);
                }
            }
        }
    }
    xhr.send(JSON.stringify(filterBusinessCode));
}

function create() {
    var business = document.getElementById("createBusinesses").value;
    var alpha = document.getElementById("createAlpha").value;
    var height = document.getElementById("createHeight").value;
    var x = document.getElementById("createX").value;
    var y = document.getElementById("createY").value;
    var path = document.getElementById("createPath").value;
    if (!check(business, alpha, height, x, y, path)) {
        return
    }
    var createWaterMark = {
        businessCode: JSON.parse(business).code,
        alpha: parseInt(alpha),
        height: parseInt(height),
        x: parseInt(x),
        y: parseInt(y),
        path: path
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/waterMark/add", false);
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
                if (result.info.substring(0, 4) == '添加成功') {
                    document.getElementById("tableTitle").style.visibility = "visible";
                    var waterMarks = document.getElementById("waterMarks");
                    var url = document.getElementById('createPath').value.substring(0, document.getElementById('createPath').value.lastIndexOf(".")) + '_waterMark.jpg';
                    var ifHave = false;
                    for (var i = 0; i < tableRow.length; i++) {
                        if (tableRow[i] == url) {
                            ifHave = true;
                        }
                    }
                    if (!ifHave) {
                        tableRow.push(url);
                        waterMarks.innerHTML += '\n' +
                            '    <tr>\n' +
                            '        <td class="bottomTd1">' + document.getElementById('createPath').value + '</td>\n' +
                            '        <td class="bottomTd2">' + url + '</td>\n' +
                            '    </tr>';
                    }
                    document.getElementById('createPath').value = '';
                    document.getElementById("createTest").value = '';
                }
                document.getElementById("createAlpha").value = '50';
                document.getElementById("createHeight").value = '10';
                document.getElementById("createX").value = '0';
                document.getElementById("createY").value = '100';
                alert(result.info);
            }
        }
    }
    xhr.send(JSON.stringify(createWaterMark));
}

function test() {
    var business = document.getElementById("createBusinesses").value;
    var alpha = document.getElementById("createAlpha").value;
    var height = document.getElementById("createHeight").value;
    var x = document.getElementById("createX").value;
    var y = document.getElementById("createY").value;
    var path = document.getElementById("createPath").value;
    if (!check(business, alpha, height, x, y, path)) {
        return
    }
    var createWaterMark = {
        businessCode: JSON.parse(business).code,
        alpha: parseInt(alpha),
        height: parseInt(height),
        x: parseInt(x),
        y: parseInt(y),
        path: path
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/waterMark/test", false);
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
                if (result.info.substring(0, 4) == '添加成功') {
                    document.getElementById("createTest").value = document.getElementById('createPath').value.substring(0, document.getElementById('createPath').value.lastIndexOf(".")) + '_test.jpg';
                } else {
                    document.getElementById("createTest").value = '';
                }
                alert(result.info);
            }
        }
    }
    xhr.send(JSON.stringify(createWaterMark));
}

function check(business, alpha, height, x, y, path) {
    var checkStr = '';
    if (business == null) {
        checkStr += '商家必须选择 ';
    }
    if (alpha == '' || isNaN(alpha) || parseInt(alpha) > 100 || parseInt(alpha) < 0) {
        checkStr += '请填写透明度（0-100） ';
    }
    if (height == '' || isNaN(height) || parseInt(height) > 25 || parseInt(height) < 0) {
        checkStr += '请填写高度（0-25，建议填写15以下） ';
    }
    if (x == '' || isNaN(x) || parseInt(x) > 100 || parseInt(x) < 0) {
        checkStr += '请填写x偏移量（0-100） ';
    }
    if (y == '' || isNaN(y) || parseInt(y) > 100 || parseInt(y) < 0) {
        checkStr += '请填写y偏移量（0-100） ';
    }
    if (path == null || path == '') {
        checkStr += '原图必须选择 ';
    }
    if (checkStr != '') {
        alert(checkStr);
        return false;
    }
    return true;
}