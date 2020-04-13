var tableRow = [];

function create() {
    var message = document.getElementById("createMessage").value;
    var x = document.getElementById("createX").value;
    var y = document.getElementById("createY").value;
    var path = document.getElementById("createPath").value;
    var size = document.getElementById("createSize").value;
    var layout = document.getElementById("createLayout").options[document.getElementById("createLayout").selectedIndex].value;
    var color = document.getElementById("createColor").options[document.getElementById("createColor").selectedIndex].value;
    if (!check(message, x, y, path, size, layout, color)) {
        return
    }
    if (confirm("本操作将消耗1枚金豆") == false) {
        return;
    }
    var createFont = {
        message: message,
        x: parseInt(x),
        y: parseInt(y),
        path: path,
        layout: layout,
        size: parseInt(size),
        color: color
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/video/addFont", false);
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
                    var videoCuts = document.getElementById("videoFonts");
                    var url = result.bean;
                    var ifHave = false;
                    for (var i = 0; i < tableRow.length; i++) {
                        if (tableRow[i] == url) {
                            ifHave = true;
                        }
                    }
                    if (!ifHave) {
                        tableRow.push(url);
                        videoCuts.innerHTML += '\n' +
                            '    <tr>\n' +
                            '        <td class="bottomTd1">' + document.getElementById('createPath').value + '</td>\n' +
                            '        <td class="bottomTd2">' + url + '</td>\n' +
                            '    </tr>';
                    }
                    document.getElementById("createMessage").value = '';
                    document.getElementById("createX").value = '0';
                    document.getElementById("createY").value = '0';
                    document.getElementById("createSize").value = '24';
                    document.getElementById('createPath').value = '';
                    document.getElementById("createTest").value = '';
                    document.getElementById("createLayout").options[0].selected = true;
                    document.getElementById("createColor").options[0].selected = true;
                    alert(result.info);
                } else {
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(createFont));
}

function test() {
    var message = document.getElementById("createMessage").value;
    var x = document.getElementById("createX").value;
    var y = document.getElementById("createY").value;
    var path = document.getElementById("createPath").value;
    var size = document.getElementById("createSize").value;
    var layout = document.getElementById("createLayout").options[document.getElementById("createLayout").selectedIndex].value;
    var color = document.getElementById("createColor").options[document.getElementById("createColor").selectedIndex].value;
    if (!check(message, x, y, path, size, layout, color)) {
        return
    }
    var createFont = {
        message: message,
        x: parseInt(x),
        y: parseInt(y),
        path: path,
        layout: layout,
        size: parseInt(size),
        color: color
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/video/testFont", false);
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
    xhr.send(JSON.stringify(createFont));
}

function check(message, x, y, path, size, layout) {
    var checkStr = '';
    if (message == '' || message == null) {
        checkStr += '请填写信息 ';
    }
    if (x == '' || isNaN(x) || parseInt(x) > 100 || parseInt(x) < 0) {
        checkStr += '请填写x偏移量（0-100） ';
    }
    if (y == '' || isNaN(y) || parseInt(y) > 100 || parseInt(y) < 0) {
        checkStr += '请填写y偏移量（0-100） ';
    }
    if (size == '' || isNaN(size) || parseInt(size) < 0) {
        checkStr += '请填写字体大小（>0） ';
    }
    if (path == null || path == '') {
        checkStr += '原视频必须选择 ';
    }
    if (layout == '' || layout == null) {
        checkStr += '请选择布局 ';
    }
    if (checkStr != '') {
        alert(checkStr);
        return false;
    }
    return true;
}