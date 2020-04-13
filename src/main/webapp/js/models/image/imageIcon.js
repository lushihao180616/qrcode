var tableRow = [];

function create() {
    var path = document.getElementById("createPath").value;
    var icon = document.getElementById("createIcon").value;
    var width = document.getElementById("createWidth").value;
    var height = document.getElementById("createHeight").value;
    var x = document.getElementById("createX").value;
    var y = document.getElementById("createY").value;
    if (!check(path, icon, width, height, x, y)) {
        return
    }
    if (confirm("本操作将消耗1枚金豆") == false) {
        return;
    }
    var createIcon = {
        path: path,
        icon: icon,
        width: parseInt(width),
        height: parseInt(height),
        x: parseInt(x),
        y: parseInt(y)
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/image/addIcon", false);
    // 添加http头，发送信息至服务器时内容编码类型
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.setRequestHeader('dataType', 'json');
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            if (xhr.status == 200 || xhr.status == 304) {
                if (xhr.responseText == null || xhr.responseText == '') {
                    window.location.href = "../../error.jsp"
                    return
                }
                var result = JSON.parse(xhr.responseText);
                if (result.ifSuccess) {
                    document.getElementById("tableTitle").style.visibility = "visible";
                    var imageIcons = document.getElementById("imageIcons");
                    var url = result.bean;
                    var ifHave = false;
                    for (var i = 0; i < tableRow.length; i++) {
                        if (tableRow[i] == url) {
                            ifHave = true;
                        }
                    }
                    if (!ifHave) {
                        tableRow.push(url);
                        imageIcons.innerHTML += '\n' +
                            '    <tr>\n' +
                            '        <td class="bottomTd1">' + document.getElementById('createPath').value + '</td>\n' +
                            '        <td class="bottomTd2">' + url + '</td>\n' +
                            '    </tr>';
                    }
                    document.getElementById('createPath').value = '';
                    document.getElementById("createIcon").value = '';
                    document.getElementById("createTest").value = '';
                    document.getElementById("createWidth").value = '10';
                    document.getElementById("createHeight").value = '10';
                    document.getElementById("createX").value = '50';
                    document.getElementById("createY").value = '50';
                    alert(result.info);
                } else {
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(createIcon));
}

function test() {
    var path = document.getElementById("createPath").value;
    var icon = document.getElementById("createIcon").value;
    var width = document.getElementById("createWidth").value;
    var height = document.getElementById("createHeight").value;
    var x = document.getElementById("createX").value;
    var y = document.getElementById("createY").value;
    if (!check(path, icon, width, height, x, y)) {
        return
    }
    var createIcon = {
        path: path,
        icon: icon,
        width: parseInt(width),
        height: parseInt(height),
        x: parseInt(x),
        y: parseInt(y)
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/image/testIcon", false);
    // 添加http头，发送信息至服务器时内容编码类型
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.setRequestHeader('dataType', 'json');
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            if (xhr.status == 200 || xhr.status == 304) {
                if (xhr.responseText == null || xhr.responseText == '') {
                    window.location.href = "../../error.jsp"
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
    xhr.send(JSON.stringify(createIcon));
}

function check(path, icon, width, height, x, y) {
    var checkStr = '';
    if (path == null || path == '') {
        checkStr += '原图必须选择 ';
    }
    if (icon == null || icon == '') {
        checkStr += '添加图标必须选择 ';
    }
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
    if (checkStr != '') {
        alert(checkStr);
        return false;
    }
    return true;
}