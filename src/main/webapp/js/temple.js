function init() {
    getTemple();
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
                    '        <th class="bottomTh5">算法选择</th>\n' +
                    '        <th class="bottomTh6">背景宽度</th>\n' +
                    '        <th class="bottomTh7">背景高度</th>\n' +
                    '        <th class="bottomTh8">X偏移量</th>\n' +
                    '        <th class="bottomTh9">Y偏移量</th>\n' +
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
                    var transparent = ''
                    if (templeList[i].transparent) {
                        transparent += "是"
                    } else {
                        transparent += "否"
                    }
                    var arti = ''
                    if (templeList[i].arti == '0') {
                        arti += "热门算法"
                    } else if (templeList[i].arti == '1') {
                        arti += "最初算法"
                    } else if (templeList[i].arti == '2') {
                        arti += "三角算法"
                    }
                    temples.innerHTML += '\n' +
                        '    <tr>\n' +
                        '        <td class="bottomTd1">' + templeList[i].code + '</td>\n' +
                        '        <td class="bottomTd2">' + templeList[i].money + '</td>\n' +
                        '        <td class="bottomTd3">' + ifOnly + '</td>\n' +
                        '        <td class="bottomTd4">' + ifShowLogo + '</td>\n' +
                        '        <td class="bottomTd5">' + arti + '</td>\n' +
                        '        <td class="bottomTd6">' + templeList[i].width + '</td>\n' +
                        '        <td class="bottomTd7">' + templeList[i].height + '</td>\n' +
                        '        <td class="bottomTd8">' + templeList[i].x + '</td>\n' +
                        '        <td class="bottomTd9">' + templeList[i].y + '</td>\n' +
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
        ifOnly: Boolean(parseInt(document.getElementById("createIfOnly").value)),
        ifShowLogo: Boolean(parseInt(document.getElementById("createIfShowLogo").value)),
        arti: document.getElementById("createArti").value,
        width: parseInt(document.getElementById("createWidth").value),
        height: parseInt(document.getElementById("createHeight").value),
        x: parseInt(document.getElementById("createX").value),
        y: parseInt(document.getElementById("createY").value),
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
                document.getElementById("createIfOnly").options[0].selected = true;
                document.getElementById("createIfShowLogo").options[0].selected = true;
                document.getElementById("createArti").options[0].selected = true;
                document.getElementById("createWidth").value = '975';
                document.getElementById("createHeight").value = '975';
                document.getElementById("createX").value = '0';
                document.getElementById("createY").value = '0';
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
    if (temple.ifOnly) {
        document.getElementById("updateIfOnly").options[0].selected = true;
    } else {
        document.getElementById("updateIfOnly").options[1].selected = true;
    }
    if (temple.ifShowLogo) {
        document.getElementById("updateIfShowLogo").options[0].selected = true;
    } else {
        document.getElementById("updateIfShowLogo").options[1].selected = true;
    }
    if (temple.arti == '0') {
        document.getElementById("updateArti").options[0].selected = true;
    } else {
        document.getElementById("updateArti").options[1].selected = true;
    }
    document.getElementById("updateWidth").value = temple.width;
    document.getElementById("updateHeight").value = temple.height;
    document.getElementById("updateX").value = temple.x;
    document.getElementById("updateY").value = temple.y;
}

function update() {
    var updateTemple = {
        code: JSON.parse(document.getElementById("updateTemples").value).code,
        money: document.getElementById("updateMoney").value,
        ifOnly: Boolean(parseInt(document.getElementById("updateIfOnly").value)),
        ifShowLogo: Boolean(parseInt(document.getElementById("updateIfShowLogo").value)),
        arti: document.getElementById("updateArti").value,
        width: parseInt(document.getElementById("updateWidth").value),
        height: parseInt(document.getElementById("updateHeight").value),
        x: parseInt(document.getElementById("updateX").value),
        y: parseInt(document.getElementById("updateY").value),
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
                document.getElementById("updateIfOnly").options[0].selected = true;
                document.getElementById("updateIfShowLogo").options[0].selected = true;
                document.getElementById("updateArti").options[0].selected = true;
                document.getElementById("updateWidth").value = '';
                document.getElementById("updateHeight").value = '';
                document.getElementById("updateX").value = '';
                document.getElementById("updateY").value = '';
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
    if (temple.ifOnly) {
        document.getElementById("deleteIfOnly").innerText = '仅二维码：是';
    } else {
        document.getElementById("deleteIfOnly").innerText = '仅二维码：否';
    }
    if (temple.ifShowLogo) {
        document.getElementById("deleteIfShowLogo").innerText = '显示商标：是';
    } else {
        document.getElementById("deleteIfShowLogo").innerText = '显示商标：否';
    }
    if (temple.arti == '0') {
        document.getElementById("deleteArti").innerText = '算法选择：热门算法';
    } else {
        document.getElementById("deleteArti").innerText = '算法选择：最初算法';
    }
    document.getElementById("deleteWidth").innerText = '背景宽度：' + temple.width;
    document.getElementById("deleteHeight").innerText = '背景高度：' + temple.height;
    document.getElementById("deleteX").innerText = 'x  偏移量：' + temple.x;
    document.getElementById("deleteY").innerText = 'y  偏移量：' + temple.y;
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
                document.getElementById("deleteIfOnly").innerText = '';
                document.getElementById("deleteIfShowLogo").innerText = '';
                document.getElementById("deleteArti").innerText = '';
                document.getElementById("deleteWidth").innerText = '';
                document.getElementById("deleteHeight").innerText = '';
                document.getElementById("deleteX").innerText = '';
                document.getElementById("deleteY").innerText = '';
            }
        }
    }
    xhr.send(JSON.stringify(deleteTemple));
}