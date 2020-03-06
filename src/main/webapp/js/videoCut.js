var tableRow = [];

function create() {
    var path = document.getElementById("createPath").value;
    var start = document.getElementById("createStart").value;
    var end = document.getElementById("createEnd").value;
    if (!check(path, start, end)) {
        return
    }
    var videoCut = {
        path: path,
        start: parseFloat(start),
        end: parseFloat(end)
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/video/addCut", false);
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
                    var imageCuts = document.getElementById("videoCuts");
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
                    document.getElementById("createStart").value = '0';
                    document.getElementById("createEnd").value = '1';
                    document.getElementById('createPath').value = '';
                    alert(result.info);
                } else {
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(videoCut));
}

function test() {
    var path = document.getElementById("createPath").value;
    var start = document.getElementById("createStart").value;
    var end = document.getElementById("createEnd").value;
    if (!check(path, start, end)) {
        return
    }
    var videoCut = {
        path: path,
        start: parseFloat(start),
        end: parseFloat(end)
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/video/testCut", false);
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
                    alert(result.info);
                } else {
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(videoCut));
}

function check(path, start, end) {
    var checkStr = '';
    if (path == null || path == '') {
        checkStr += '原视频必须选择 ';
    }
    if (start == '' || isNaN(start) || parseInt(start) < 0) {
        checkStr += '请填写开始时间（≥0） ';
    }
    if (end == '' || isNaN(end) || parseInt(end) < 0) {
        checkStr += '请填写结束时间（≥0） ';
    }
    if (checkStr != '') {
        alert(checkStr);
        return false;
    }
    return true;
}