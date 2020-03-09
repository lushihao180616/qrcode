function init() {
    var filterTemple = {
        code: document.getElementById("filterCode").value
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/temple/filter", false);
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
                    handleTemples(result.bean);
                } else {
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(filterTemple));
}

function handleTemples(templeList) {
    var temples = document.getElementById("temples");
    temples.innerHTML = '';
    for (var i = 0; i < templeList.length; i++) {
        var ifOnly = ''
        if (templeList[i].ifOnly) {
            ifOnly += "是"
        } else {
            ifOnly += "否"
        }
        var ifShowLogo = ''
        if (templeList[i].ifShowLogo) {
            ifShowLogo += "是"
        } else {
            ifShowLogo += "否"
        }
        var ifSelfBg = ''
        if (templeList[i].ifSelfBg) {
            ifSelfBg += "是"
        } else {
            ifSelfBg += "否"
        }
        temples.innerHTML += '\n' +
            '    <tr >\n' +
            '        <td class="bottomTd1">' + templeList[i].code + '</td>\n' +
            '        <td class="bottomTd2">' + templeList[i].money + '</td>\n' +
            '        <td class="bottomTd3">' + ifOnly + '</td>\n' +
            '        <td class="bottomTd4">' + ifShowLogo + '</td>\n' +
            '        <td class="bottomTd5">' + ifSelfBg + '</td>\n' +
            '        <td class="bottomTd6">' + templeList[i].arti + '</td>\n' +
            '        <td class="bottomTd7">' + templeList[i].width + '</td>\n' +
            '        <td class="bottomTd8">' + templeList[i].height + '</td>\n' +
            '        <td class="bottomTd9">' + templeList[i].iconNum + '</td>\n' +
            '        <td class="bottomTd10">' + templeList[i].x + '</td>\n' +
            '        <td class="bottomTd11">' + templeList[i].y + '</td>\n' +
            '        <td class="bottomTd12">' + templeList[i].angle + '</td>\n' +
            '        <td class="bottomTd13">' + templeList[i].multiple + '</td>\n' +
            '        <td class="bottomTd14">' + templeList[i].frame + '</td>\n' +
            '        <td class="bottomTd15">' + templeList[i].path + '</td>\n' +
            '    </tr>';
    }
}

function create() {
    var code = document.getElementById("createCode").value;
    var money = document.getElementById("createMoney").value;
    var width = document.getElementById("createWidth").value;
    var height = document.getElementById("createHeight").value;
    var iconNum = document.getElementById("createIconNum").value;
    var x = document.getElementById("createX").value;
    var y = document.getElementById("createY").value;
    var angle = document.getElementById("createAngle").value;
    var multiple = document.getElementById("createMultiple").value;
    var frame = document.getElementById("createFrame").value;
    var templeItemsPath = document.getElementById("createTempleItemsPath").value;
    if (!check(code, money, width, height, iconNum, x, y, angle, multiple, frame, templeItemsPath)) {
        return
    }
    var createTemple = {
        code: code,
        money: parseFloat(money),
        width: parseInt(width),
        height: parseInt(height),
        iconNum: parseInt(iconNum),
        x: parseInt(x),
        y: parseInt(y),
        angle: parseInt(angle),
        multiple: parseInt(multiple),
        frame: frame,
        templeItemsPath: templeItemsPath
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/temple/create", false);
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
                    handleTemples(result.bean);
                    document.getElementById("createCode").value = '';
                    document.getElementById("createMoney").value = '';
                    document.getElementById("createWidth").value = '1950';
                    document.getElementById("createHeight").value = '1950';
                    document.getElementById("createIconNum").value = '1';
                    document.getElementById("createX").value = '0';
                    document.getElementById("createY").value = '0';
                    document.getElementById("createAngle").value = '0';
                    document.getElementById("createMultiple").value = '1';
                    document.getElementById("createFrame").value = '0/0/0';
                    document.getElementById('createTempleItemsPath').value = '';
                    alert(result.info);
                } else {
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(createTemple));
}

function updateSearch() {
    var updateFilterBusiness = {
        code: document.getElementById('updateCode').value
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/temple/filter", false);
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
                    var updateTemples = document.getElementById("updateTemples");
                    updateTemples.innerHTML = '';
                    for (var i = 0; i < result.bean.length; i++) {
                        var option = document.createElement("option");
                        option.value = JSON.stringify(result.bean[i]);
                        option.text = result.bean[i].code;
                        updateTemples.add(option);
                    }
                    if (result.bean.length > 0) {
                        updateTempleCode("updateTemples")
                    }
                    alert(result.info);
                } else {
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(updateFilterBusiness));
}

function updateTempleCode(id) {
    var temple = JSON.parse(document.getElementById(id).value);
    document.getElementById("updateMoney").value = temple.money;
    document.getElementById("updateWidth").value = temple.width;
    document.getElementById("updateHeight").value = temple.height;
    document.getElementById("updateIconNum").value = temple.iconNum;
    document.getElementById("updateX").value = temple.x;
    document.getElementById("updateY").value = temple.y;
    document.getElementById("updateAngle").value = temple.angle;
    document.getElementById("updateMultiple").value = temple.multiple;
    document.getElementById("updateFrame").value = temple.frame;
}

function update() {
    var code = JSON.parse(document.getElementById("updateTemples").value).code;
    var money = document.getElementById("updateMoney").value;
    var width = document.getElementById("updateWidth").value;
    var height = document.getElementById("updateHeight").value;
    var iconNum = document.getElementById("updateIconNum").value;
    var x = document.getElementById("updateX").value;
    var y = document.getElementById("updateY").value;
    var angle = document.getElementById("updateAngle").value;
    var multiple = document.getElementById("updateMultiple").value;
    var frame = document.getElementById("updateFrame").value;
    var templeItemsPath = document.getElementById("updateTempleItemsPath").value;
    if (!check(code, money, width, height, iconNum, x, y, angle, multiple, frame, "1")) {
        return
    }
    var updateTemple = {
        code: code,
        money: money,
        width: parseInt(width),
        height: parseInt(height),
        iconNum: parseInt(iconNum),
        x: parseInt(x),
        y: parseInt(y),
        angle: parseInt(angle),
        multiple: parseInt(multiple),
        frame: frame,
        templeItemsPath: templeItemsPath
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/temple/update", false);
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
                    handleTemples(result.bean);
                    document.getElementById("updateTemples").innerHTML = '';
                    document.getElementById("updateMoney").value = '';
                    document.getElementById("updateWidth").value = '';
                    document.getElementById("updateHeight").value = '';
                    document.getElementById("updateIconNum").value = '';
                    document.getElementById("updateX").value = '';
                    document.getElementById("updateY").value = '';
                    document.getElementById("updateAngle").value = '';
                    document.getElementById("updateMultiple").value = '';
                    document.getElementById("updateFrame").value = '';
                    document.getElementById('updateTempleItemsPath').value = '';
                    alert(result.info);
                } else {
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(updateTemple));
}

function deleteSearch() {
    var deleteFilterBusiness = {
        code: document.getElementById('deleteCode').value
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/temple/filter", false);
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
                    var deleteTemples = document.getElementById("deleteTemples");
                    deleteTemples.innerHTML = '';
                    for (var i = 0; i < result.bean.length; i++) {
                        var option = document.createElement("option");
                        option.value = JSON.stringify(result.bean[i]);
                        option.text = result.bean[i].code;
                        deleteTemples.add(option);
                    }
                    if (result.bean.length > 0) {
                        deleteTempleCode("deleteTemples")
                    }
                    alert(result.info);
                } else {
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(deleteFilterBusiness));
}

function deleteTempleCode(id) {
    var temple = JSON.parse(document.getElementById(id).value);
    document.getElementById("deleteMoney").innerText = '价        格：' + temple.money;
    document.getElementById("deleteWidth").innerText = '背景宽度：' + temple.width;
    document.getElementById("deleteHeight").innerText = '背景高度：' + temple.height;
    document.getElementById("deleteIconNum").innerText = '子图数量：' + temple.iconNum;
    document.getElementById("deleteX").innerText = 'x  偏移量：' + temple.x;
    document.getElementById("deleteY").innerText = 'y  偏移量：' + temple.y;
    document.getElementById("deleteAngle").innerText = '旋转角度：' + temple.angle;
    document.getElementById("deleteMultiple").innerText = '缩放倍数：' + temple.multiple;
}

function deleteOne() {
    var deleteTemple = {
        code: JSON.parse(document.getElementById("deleteTemples").value).code,
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/temple/delete", false);
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
                    handleTemples(result.bean);
                    document.getElementById("deleteTemples").innerHTML = '';
                    document.getElementById("deleteMoney").innerText = '';
                    document.getElementById("deleteWidth").innerText = '';
                    document.getElementById("deleteHeight").innerText = '';
                    document.getElementById("deleteIconNum").innerText = '';
                    document.getElementById("deleteX").innerText = '';
                    document.getElementById("deleteY").innerText = '';
                    document.getElementById("deleteAngle").innerText = '';
                    document.getElementById("deleteMultiple").innerText = '';
                    alert(result.info);
                } else {
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(deleteTemple));
}

function check(code, money, width, height, iconNum, x, y, angle, multiple, frame, templeItemsPath) {
    var checkStr = '';
    if (code == "" || code == null) {
        checkStr += '编号必须填写 '
    }
    if (money == '' || isNaN(money)) {
        checkStr += '价格必须填写（数字） '
    }
    if (width == '' || isNaN(width)) {
        checkStr += '宽度必须填写（数字） '
    }
    if (height == '' || isNaN(height)) {
        checkStr += '高度必须填写（数字） '
    }
    if (iconNum == '' || isNaN(iconNum)) {
        checkStr += '子图数量必须填写（数字） '
    }
    if (x == '' || isNaN(x)) {
        checkStr += 'x偏移量必须填写（数字） '
    }
    if (y == '' || isNaN(y)) {
        checkStr += 'y偏移量必须填写（数字） '
    }
    if (angle == '' || isNaN(angle)) {
        checkStr += '角度必须填写（数字） '
    }
    if (multiple == '' || isNaN(multiple)) {
        checkStr += '缩放倍数必须填写（数字） '
    }
    if (frame == '' || frame == null) {
        checkStr += '帧管理必须填写（每秒多少帧/从第几帧开始加二维码/从第几帧停止加二维码） '
    }
    if (templeItemsPath == '' || templeItemsPath == null) {
        checkStr += '模板图标必须选择 '
    }
    if (checkStr != '') {
        alert(checkStr);
        return false;
    }
    return true;
}