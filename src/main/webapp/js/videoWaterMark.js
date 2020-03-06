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
    var path = document.getElementById("createPath").value;
    var x = document.getElementById("createX").value;
    var y = document.getElementById("createY").value;
    var fontSize = document.getElementById("createFontSize").value;
    var fontColor = document.getElementById("createFontColor").options[document.getElementById("createFontColor").selectedIndex].value;
    var fontShadow = document.getElementById("createFontShadow").options[document.getElementById("createFontShadow").selectedIndex].value;
    if (!check(business, path, x, y, fontSize)) {
        return
    }
    var createWaterMark = {
        businessCode: JSON.parse(business).code,
        path: path,
        x: parseInt(x),
        y: parseInt(y),
        fontSize: parseInt(fontSize),
        fontColor: fontColor,
        fontShadow: fontShadow
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/video/addWaterMark", false);
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
                    var videos = document.getElementById("videos");
                    var url = document.getElementById('createPath').value.substring(0, document.getElementById('createPath').value.lastIndexOf(".")) + '_new.mp4';
                    var ifHave = false;
                    for (var i = 0; i < tableRow.length; i++) {
                        if (tableRow[i] == url) {
                            ifHave = true;
                        }
                    }
                    if (!ifHave) {
                        tableRow.push(url);
                        videos.innerHTML += '\n' +
                            '    <tr>\n' +
                            '        <td class="bottomTd1">' + document.getElementById('createPath').value + '</td>\n' +
                            '        <td class="bottomTd2">' + url + '</td>\n' +
                            '    </tr>';
                        document.getElementById('createPath').value = '';
                        document.getElementById("createTest").value = '';
                    }
                    alert(result.info);
                } else {
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(createWaterMark));
}

function test() {
    var business = document.getElementById("createBusinesses").value;
    var path = document.getElementById("createPath").value;
    var x = document.getElementById("createX").value;
    var y = document.getElementById("createY").value;
    var fontSize = document.getElementById("createFontSize").value;
    var fontColor = document.getElementById("createFontColor").options[document.getElementById("createFontColor").selectedIndex].value;
    var fontShadow = document.getElementById("createFontShadow").options[document.getElementById("createFontShadow").selectedIndex].value;
    if (!check(business, path, x, y, fontSize)) {
        return
    }
    var createWaterMark = {
        businessCode: JSON.parse(business).code,
        path: path,
        x: parseInt(x),
        y: parseInt(y),
        fontSize: parseInt(fontSize),
        fontColor: fontColor,
        fontShadow: fontShadow
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/video/testWaterMark", false);
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
    xhr.send(JSON.stringify(createWaterMark));
}

function check(business, path, x, y, fontSize) {
    var checkStr = '';
    if (business == null) {
        checkStr += '商家必须选择 ';
    }
    if (path == null || path == '') {
        checkStr += '图片位置必须选择 ';
    }
    if (x == '' || isNaN(x) || parseInt(x) > 100 || parseInt(x) < 0) {
        checkStr += '请填写x偏移量（0-100） ';
    }
    if (y == '' || isNaN(y) || parseInt(y) > 100 || parseInt(y) < 0) {
        checkStr += '请填写y偏移量（0-100） ';
    }
    if (fontSize == '' || isNaN(fontSize)) {
        checkStr += '请填写字体大小 ';
    }
    if (checkStr != '') {
        alert(checkStr);
        return false;
    }
    return true;
}