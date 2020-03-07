var tableRow = [];

function create() {
    var width = document.getElementById("createWidth").value;
    var height = document.getElementById("createHeight").value;
    var x = document.getElementById("createX").value;
    var y = document.getElementById("createY").value;
    var path = document.getElementById("createPath").value;
    var alpha = document.getElementById("createAlpha").value;
    if (!check(width, height, x, y, path, alpha)) {
        return
    }
    var imageCut = {
        width: parseInt(width),
        height: parseInt(height),
        x: parseInt(x),
        y: parseInt(y),
        path: path,
        alpha: parseInt(alpha)
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
                    document.getElementById("createWidth").value = '100';
                    document.getElementById("createHeight").value = '100';
                    document.getElementById("createX").value = '0';
                    document.getElementById("createY").value = '0';
                    document.getElementById('createPath').value = '';
                    document.getElementById("createAlpha").value = '0';
                    document.getElementById("createTest").value = '';
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
    var width = document.getElementById("createWidth").value;
    var height = document.getElementById("createHeight").value;
    var x = document.getElementById("createX").value;
    var y = document.getElementById("createY").value;
    var path = document.getElementById("createPath").value;
    var alpha = document.getElementById("createAlpha").value;
    if (!check(width, height, x, y, path, alpha)) {
        return
    }
    var createCut = {
        width: parseInt(width),
        height: parseInt(height),
        x: parseInt(x),
        y: parseInt(y),
        path: path,
        alpha: parseInt(alpha)
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

function check(width, height, x, y, path, alpha) {
    var checkStr = '';
    if (width == '' || isNaN(width) || parseInt(width) > 100 || parseInt(width) < 0) {
        checkStr += '请填写宽度（0-100） ';
    }
    if (height == '' || isNaN(height) || parseInt(height) > 100 || parseInt(height) < 0) {
        checkStr += '请填写高度（0-100） ';
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
    if (alpha == '' || isNaN(alpha) || parseInt(alpha) > 50 || parseInt(alpha) < 0) {
        checkStr += '请填写透明度（0-50） ';
    }
    if (checkStr != '') {
        alert(checkStr);
        return false;
    }
    return true;
}