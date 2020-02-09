function init() {
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
                } else {
                    getTemple();
                }
            }
        }
    }
    xhr.send();
}

function getTemple() {
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
                var templeList = JSON.parse(xhr.responseText);
                var temples = document.getElementById("temples");
                temples.innerHTML = '\n' +
                    '    <tr>\n' +
                    '        <th class="bottomTh1">编号</th>\n' +
                    '        <th class="bottomTh2">价格</th>\n' +
                    '        <th class="bottomTh3">仅二维码</th>\n' +
                    '        <th class="bottomTh4">显示商标</th>\n' +
                    '        <th class="bottomTh5">自定背景</th>\n' +
                    '        <th class="bottomTh6">算法选择</th>\n' +
                    '        <th class="bottomTh7">背景宽度</th>\n' +
                    '        <th class="bottomTh8">背景高度</th>\n' +
                    '        <th class="bottomTh9">子图数量</th>\n' +
                    '        <th class="bottomTh10">X偏移量</th>\n' +
                    '        <th class="bottomTh11">Y偏移量</th>\n' +
                    '        <th class="bottomTh12">旋转角度</th>\n' +
                    '        <th class="bottomTh13">缩放倍数</th>\n' +
                    '        <th class="bottomTh14">帧管理</th>\n' +
                    '        <th class="bottomTh15">模板样例</th>\n' +
                    '    </tr>';

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
                        '    <tr>\n' +
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
        }
    }
    xhr.send(JSON.stringify(filterTemple));
}

function create() {
    var createTemple = {
        code: document.getElementById("createCode").value,
        money: document.getElementById("createMoney").value,
        width: parseInt(document.getElementById("createWidth").value),
        height: parseInt(document.getElementById("createHeight").value),
        iconNum: parseInt(document.getElementById("createIconNum").value),
        x: parseInt(document.getElementById("createX").value),
        y: parseInt(document.getElementById("createY").value),
        angle: parseInt(document.getElementById("createAngle").value),
        multiple: parseInt(document.getElementById("createMultiple").value),
        frame: document.getElementById("createFrame").value,
        templeItemsPath: document.getElementById('createTempleItemsPath').value
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/temple/create", false);
    // 添加http头，发送信息至服务器时内容编码类型
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.setRequestHeader('dataType', 'json');
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            if (xhr.status == 200 || xhr.status == 304) {
                var data = xhr.responseText;
                alert(data);
                init();
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
                var templeList = JSON.parse(xhr.responseText);
                var updateTemples = document.getElementById("updateTemples");
                updateTemples.innerHTML = '';
                for (var i = 0; i < templeList.length; i++) {
                    var option = document.createElement("option");
                    option.value = JSON.stringify(templeList[i]);
                    option.text = templeList[i].code;
                    updateTemples.add(option);
                }
                if (templeList.length > 0) {
                    updateTempleCode("updateTemples")
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
    var updateTemple = {
        code: JSON.parse(document.getElementById("updateTemples").value).code,
        money: document.getElementById("updateMoney").value,
        width: parseInt(document.getElementById("updateWidth").value),
        height: parseInt(document.getElementById("updateHeight").value),
        iconNum: parseInt(document.getElementById("updateIconNum").value),
        x: parseInt(document.getElementById("updateX").value),
        y: parseInt(document.getElementById("updateY").value),
        angle: parseInt(document.getElementById("updateAngle").value),
        multiple: parseInt(document.getElementById("updateMultiple").value),
        frame: document.getElementById("updateFrame").value,
        templeItemsPath: document.getElementById('updateTempleItemsPath').value
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/temple/update", false);
    // 添加http头，发送信息至服务器时内容编码类型
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.setRequestHeader('dataType', 'json');
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            if (xhr.status == 200 || xhr.status == 304) {
                var data = xhr.responseText;
                alert(data);
                init();
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
                var templeList = JSON.parse(xhr.responseText);
                var deleteTemples = document.getElementById("deleteTemples");
                deleteTemples.innerHTML = '';
                for (var i = 0; i < templeList.length; i++) {
                    var option = document.createElement("option");
                    option.value = JSON.stringify(templeList[i]);
                    option.text = templeList[i].code;
                    deleteTemples.add(option);
                }
                if (templeList.length > 0) {
                    deleteTempleCode("deleteTemples")
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
                var data = xhr.responseText;
                alert(data);
                init();
                document.getElementById("deleteTemples").innerHTML = '';
                document.getElementById("deleteMoney").innerText = '';
                document.getElementById("deleteWidth").innerText = '';
                document.getElementById("deleteHeight").innerText = '';
                document.getElementById("deleteIconNum").innerText = '';
                document.getElementById("deleteX").innerText = '';
                document.getElementById("deleteY").innerText = '';
                document.getElementById("deleteAngle").innerText = '';
                document.getElementById("deleteMultiple").innerText = '';
            }
        }
    }
    xhr.send(JSON.stringify(deleteTemple));
}