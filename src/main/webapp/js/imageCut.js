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
                if (result.ifSuccess) {
                    var businesses = document.getElementById("createBusinesses");
                    businesses.innerHTML = '';
                    for (var i = 0; i < result.bean.length; i++) {
                        var option = document.createElement("option");
                        option.value = JSON.stringify(result.bean[i]);
                        option.text = result.bean[i].code;
                        businesses.add(option);
                    }
                } else {
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(filterBusinessCode));
}

function create() {
    var business = document.getElementById("createBusinesses").value;
    var width = document.getElementById("createWidth").value;
    var height = document.getElementById("createHeight").value;
    var path = document.getElementById("createPath").value;
    var alpha = document.getElementById("createAlpha").value;
    if (!check(business, width, height, path, alpha)) {
        return
    }
    var imageCut = {
        businessCode: JSON.parse(business).code,
        width: parseInt(width),
        height: parseInt(height),
        path: path
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/image/addCut", false);
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
                    document.getElementById("tableTitle").style.visibility = "visible";
                    var imageCuts = document.getElementById("imageCuts");
                    var url = result.bean;
                    var ifHave = false;
                    for (var i = 0; i < tableRow.length; i++) {
                        if (tableRow[i] == url) {
                            ifHave = true;
                        }
                    }
                    if (!ifHave) {
                        tableRow.push(url);
                        imageCuts.innerHTML += '\n' +
                            '    <tr>\n' +
                            '        <td class="bottomTd1">' + document.getElementById('createPath').value + '</td>\n' +
                            '        <td class="bottomTd2">' + url + '</td>\n' +
                            '    </tr>';
                    }
                    document.getElementById('createPath').value = '';
                    document.getElementById("createTest").value = '';
                    document.getElementById("createWidth").value = '100';
                    document.getElementById("createHeight").value = '100';
                    alert(result.info);
                } else {
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(imageCut));
}

function test() {
    var business = document.getElementById("createBusinesses").value;
    var width = document.getElementById("createWidth").value;
    var height = document.getElementById("createHeight").value;
    var path = document.getElementById("createPath").value;
    var alpha = document.getElementById("createAlpha").value;
    if (!check(business, width, height, path, alpha)) {
        return
    }
    var createCut = {
        businessCode: JSON.parse(business).code,
        width: parseInt(width),
        height: parseInt(height),
        path: path
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/image/testCut", false);
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
                    document.getElementById("createTest").value = result.bean;
                    alert(result.info);
                } else {
                    document.getElementById("createTest").value = '';
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(createCut));
}

function check(business, width, height, path, alpha) {
    var checkStr = '';
    if (business == null) {
        checkStr += '商家必须选择 ';
    }
    if (width == '' || isNaN(width) || parseInt(width) > 100 || parseInt(width) < 0) {
        checkStr += '请填写宽度（0-100） ';
    }
    if (height == '' || isNaN(height) || parseInt(height) > 100 || parseInt(height) < 0) {
        checkStr += '请填写高度（0-100） ';
    }
    if (path == null || path == '') {
        checkStr += '原图必须选择 ';
    }
    if (alpha == '' || isNaN(alpha) || parseInt(alpha) > 50 || parseInt(alpha) < 0) {
        checkStr += '请填写透明度（0-50） ';
    }
    if (checkStr != '') {
        alert(checkStr);
        return false;
    }
    return true;
}