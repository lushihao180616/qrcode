function init() {
    var filter = {
        templeCode: document.getElementById("filterTemple").value
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/qrcode/qrcodeInit", false);
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
                    var recordList = result.bean.record;
                    var templeList = result.bean.temple;
                    var businessList = result.bean.business;

                    handleRecord(recordList);
                    handleTemples(templeList);
                    handleBusiness(businessList);
                } else {
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(filter));
}

function handleRecord(recordList) {
    var records = document.getElementById("records");
    records.innerHTML = '';
    for (var i = 0; i < recordList.length; i++) {
        records.innerHTML += '\n' +
            '    <tr>\n' +
            '        <td class="bottomTd1">' + recordList[i].businessCode + '</td>\n' +
            '        <td class="bottomTd2">' + recordList[i].templeCode + '</td>\n' +
            '        <td class="bottomTd3">' + recordList[i].fileName + '</td>\n' +
            '        <td class="bottomTd4">' + recordList[i].url + '</td>\n' +
            '        <td class="bottomTd5">' + recordList[i].money + '</td>\n' +
            '        <td class="bottomTd6">' + recordList[i].saveTime + '</td>\n' +
            '    </tr>';
    }
}

function handleTemples(templeList) {
    var temples = document.getElementById("temples");
    temples.innerHTML = '';
    for (var i = 0; i < templeList.length; i++) {
        var option = document.createElement("option");
        option.value = JSON.stringify(templeList[i]);
        option.text = templeList[i].code;
        temples.add(option);
    }
    if (templeList.length > 0) {
        getTempleCode("temples");
    }
}

function handleBusiness(businessList) {
    var businesses = document.getElementById("businesses");
    businesses.innerHTML = '';
    for (var i = 0; i < businessList.length; i++) {
        var option = document.createElement("option");
        option.value = JSON.stringify(businessList[i]);
        option.text = businessList[i].code;
        businesses.add(option);
    }
    if (businessList.length > 0) {
        getBusinessCode("businesses");
    }
}

function getTemple() {
    var filterTempleCode = {
        code: document.getElementById("filterTemple").value
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
                    window.location.href = "error.jsp"
                    return
                }
                var result = JSON.parse(xhr.responseText);
                if (result.ifSuccess) {
                    handleTemples(result.bean);
                    alert(result.info);
                } else {
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(filterTempleCode));
}

function getBusiness() {
    var filterBusinessCode = {
        code: document.getElementById("filterBusiness").value
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
                    var businesses = document.getElementById("businesses");
                    businesses.innerHTML = '';
                    for (var i = 0; i < result.bean.length; i++) {
                        var option = document.createElement("option");
                        option.value = JSON.stringify(result.bean[i]);
                        option.text = result.bean[i].code;
                        businesses.add(option);
                    }
                    if (result.bean.length > 0) {
                        getBusinessCode("businesses");
                    }
                    alert(result.info);
                } else {
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(filterBusinessCode));
}

function changeType(id) {
    var type = document.getElementById(id).value;
    if (type == "text") {
        document.getElementById("textInfo").style.display = "";
    } else {
        document.getElementById("textInfo").style.display = "none";
    }
    if (type == "image") {
        document.getElementById("imageInfo").style.display = "";
    } else {
        document.getElementById("imageInfo").style.display = "none";
    }
    if (type == "video") {
        document.getElementById("videoInfo").style.display = "";
    } else {
        document.getElementById("videoInfo").style.display = "none";
    }
}

function getTempleCode(id) {
    var temple = JSON.parse(document.getElementById(id).value);
    var ifOnly = '';
    if (temple.ifOnly) {
        ifOnly += "是";
    } else {
        ifOnly += "否";
    }
    var ifShowLogo = '';
    if (temple.ifShowLogo) {
        ifShowLogo += "是";
    } else {
        ifShowLogo += "否";
    }
    var ifSelfBg = '';
    if (temple.ifSelfBg) {
        ifSelfBg += "是";
        document.getElementById("backGround").style.visibility = "visible";
        document.getElementById("backGroundLabel").style.visibility = "visible";
        document.getElementById("shortLength").style.visibility = "visible";
        document.getElementById("shortLengthLabel").style.visibility = "visible";
        document.getElementById("x").style.visibility = "visible";
        document.getElementById("xLabel").style.visibility = "visible";
        document.getElementById("y").style.visibility = "visible";
        document.getElementById("yLabel").style.visibility = "visible";
        document.getElementById("angle").style.visibility = "visible";
        document.getElementById("angleLabel").style.visibility = "visible";
        document.getElementsByClassName("unit")[1].style.visibility = "visible";
        document.getElementsByClassName("unit")[2].style.visibility = "visible";
        document.getElementsByClassName("unit")[3].style.visibility = "visible";
    } else {
        ifSelfBg += "否";
        document.getElementById("backGround").style.visibility = "hidden";
        document.getElementById("backGroundLabel").style.visibility = "hidden";
        document.getElementById("shortLength").style.visibility = "hidden";
        document.getElementById("shortLengthLabel").style.visibility = "hidden";
        document.getElementById("x").style.visibility = "hidden";
        document.getElementById("xLabel").style.visibility = "hidden";
        document.getElementById("y").style.visibility = "hidden";
        document.getElementById("yLabel").style.visibility = "hidden";
        document.getElementById("angle").style.visibility = "hidden";
        document.getElementById("angleLabel").style.visibility = "hidden";
        document.getElementsByClassName("unit")[1].style.visibility = "hidden";
        document.getElementsByClassName("unit")[2].style.visibility = "hidden";
        document.getElementsByClassName("unit")[3].style.visibility = "hidden";
    }
    document.getElementById("nowTemple_price").innerText = '价        格：' + temple.money;
    document.getElementById("nowTemple_ifOnly").innerText = '仅二维码：' + ifOnly;
    document.getElementById("nowTemple_ifShowLogo").innerText = '显示商标：' + ifShowLogo;
    document.getElementById("nowTemple_ifSelfBg").innerText = '自定背景：' + ifSelfBg;
    document.getElementById("nowTemple_path").innerText = '模板样例：' + temple.path;
}

function getBusinessCode(id) {
    var budiness = JSON.parse(document.getElementById(id).value);
    document.getElementById("nowBusiness_name").innerText = '名        称：' + budiness.name;
    document.getElementById("nowBusiness_address").innerText = '地        址：' + budiness.address;
    document.getElementById("nowBusiness_phone").innerText = '电        话：' + budiness.phone;
    document.getElementById("nowBusiness_businessName").innerText = '联  系  人：' + budiness.businessName;
}

function getRecord() {
    var filterRecord = {
        templeCode: document.getElementById("filterRecordTemple").value,
        businessCode: document.getElementById("filterRecordBusiness").value,
        fileName: document.getElementById("filterFileName").value
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/qrcode/selectRecord", false);
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
                    var records = document.getElementById("records");
                    records.innerHTML = '';
                    for (var i = 0; i < result.bean.length; i++) {
                        records.innerHTML += '\n' +
                            '    <tr>\n' +
                            '        <td class="bottomTd1">' + result.bean[i].businessCode + '</td>\n' +
                            '        <td class="bottomTd2">' + result.bean[i].templeCode + '</td>\n' +
                            '        <td class="bottomTd3">' + result.bean[i].fileName + '</td>\n' +
                            '        <td class="bottomTd4">' + result.bean[i].url + '</td>\n' +
                            '        <td class="bottomTd5">' + result.bean[i].money + '</td>\n' +
                            '        <td class="bottomTd6">' + result.bean[i].saveTime + '</td>\n' +
                            '    </tr>';
                    }
                    alert(result.info);
                } else {
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(filterRecord));
}

function create() {
    var message = getMessage();
    var temple = document.getElementById("temples").value;
    var business = document.getElementById("businesses").value;
    var fileName = document.getElementById("fileName").value;
    var backGround = document.getElementById("backGround").value;
    var shortLength = document.getElementById("shortLength").value;
    var x = document.getElementById("x").value;
    var y = document.getElementById("y").value;
    var alpha = document.getElementById("alpha").value;
    var angle = document.getElementById("angle").value;
    if (!check(message, temple, business, fileName, backGround, shortLength, x, y, alpha, angle)) {
        return
    }
    var createQRCode = {
        message: message,
        templeCode: JSON.parse(temple).code,
        businessCode: JSON.parse(business).code,
        fileName: fileName,
        backGround: backGround,
        shortLength: parseInt(shortLength),
        x: parseInt(x),
        y: parseInt(y),
        alpha: parseInt(alpha),
        angle: parseInt(angle)
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/qrcode/create", false);
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
                    handleRecord(result.bean.record);
                    document.getElementById("textMessage").value = '';
                    document.getElementById("temples").options[0].selected = true;
                    document.getElementById("businesses").options[0].selected = true;
                    document.getElementById("fileName").value = '';
                    document.getElementById("backGround").value = '';
                    document.getElementById("createTest").value = '';
                    alert(result.info);
                } else {
                    alert(result.errorInfo)
                }
            }
        }
    }
    xhr.send(JSON.stringify(createQRCode));
}

function test() {
    var message = getMessage();
    var temple = document.getElementById("temples").value;
    var business = document.getElementById("businesses").value;
    var fileName = document.getElementById("fileName").value;
    var backGround = document.getElementById("backGround").value;
    var shortLength = document.getElementById("shortLength").value;
    var x = document.getElementById("x").value;
    var y = document.getElementById("y").value;
    var alpha = document.getElementById("alpha").value;
    var angle = document.getElementById("angle").value;
    if (!check(message, temple, business, fileName, backGround, shortLength, x, y, alpha, angle)) {
        return
    }
    var createQRCode = {
        message: message,
        templeCode: JSON.parse(temple).code,
        businessCode: JSON.parse(business).code,
        fileName: fileName,
        backGround: backGround,
        shortLength: parseInt(shortLength),
        x: parseInt(x),
        y: parseInt(y),
        alpha: parseInt(alpha),
        angle: parseInt(angle)
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/qrcode/test", false);
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
                    document.getElementById("createTest").value = result.bean.filePath;
                    alert(result.info);
                } else {
                    document.getElementById("createTest").value = '';
                    alert(result.errorInfo)
                }
            }
        }
    }
    xhr.send(JSON.stringify(createQRCode));
}

function check(message, temple, business, fileName, backGround, shortLength, x, y, alpha, angle) {
    var checkStr = '';
    if (message == '' || message == null) {
        checkStr += '二维码信息必须填写 ';
    }
    if (temple == null) {
        checkStr += '模板必须选择 ';
    } else {
        if (JSON.parse(temple).code.toString().charAt(4) == '1') {
            if (backGround == '' || backGround == null) {
                checkStr += '背景图片必须选择 ';
            }
            if (shortLength == '' || shortLength == null || parseInt(shortLength) < 1950) {
                checkStr += '较短边长必须填写（1950+） ';
            }
            if (x == '' || x == null || parseInt(x) > 100 || parseInt(y) < 0) {
                checkStr += 'x偏移量必须填写（0-100） ';
            }
            if (y == '' || y == null || parseInt(y) > 100 || parseInt(y) < 0) {
                checkStr += 'y偏移量必须填写（0-100） ';
            }
            if (angle == '' || angle == null || parseInt(angle) > 359 || parseInt(angle) < 0) {
                checkStr += '旋转角度必须填写（0-359） ';
            }
        }
    }
    if (business == null) {
        checkStr += '商家必须选择 ';
    }
    if (fileName == '' || fileName == null) {
        checkStr += '文件名必须填写 ';
    }
    if (alpha == '' || alpha == null || parseInt(alpha) > 50 || parseInt(alpha) < 0) {
        checkStr += '码透明度必须填写（0-50） ';
    }
    if (checkStr != '') {
        alert(checkStr);
        return false;
    }
    return true;
}

function getMessage() {
    var message;
    var type = document.getElementById(id).value;
    if (type == "text") {
        message = document.getElementById("textMessage").value;
    } else if (type == "image") {
        message = document.getElementById("imageMessage").value;
    } else if (type == "video") {
        message = document.getElementById("videoMessage").value;
    } else {
        message = "";
    }
    return message;
}