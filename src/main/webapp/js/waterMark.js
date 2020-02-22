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
                var businessList = JSON.parse(xhr.responseText);
                var businesses = document.getElementById("createBusinesses");
                businesses.innerHTML = '';
                for (var i = 0; i < businessList.length; i++) {
                    var option = document.createElement("option");
                    option.value = JSON.stringify(businessList[i]);
                    option.text = businessList[i].code;
                    businesses.add(option);
                }
            }
        }
    }
    xhr.send(JSON.stringify(filterBusinessCode));
}

function create() {
    var business = document.getElementById("createBusinesses").value;
    var alpha = document.getElementById("createAlpha").value;
    var height = document.getElementById("createHeight").value;
    var x = document.getElementById("createX").value;
    var y = document.getElementById("createY").value;
    var path = document.getElementById("createPath").value;
    if (!check(business, alpha, height, x, y, path)) {
        return
    }
    var createWaterMark = {
        businessCode: JSON.parse(business).code,
        alpha: parseInt(alpha),
        height: parseInt(height),
        x: parseInt(x),
        y: parseInt(y),
        path: path
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/waterMark/add", false);
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
                var data = xhr.responseText;
                alert(data);
                document.getElementById("tableTitle").style.visibility = "visible";
                if (data.substring(0, 4) == '添加成功') {
                    var waterMarks = document.getElementById("waterMarks");
                    waterMarks.innerHTML += '\n' +
                        '    <tr>\n' +
                        '        <td class="bottomTd1">' + document.getElementById('createPath').value + '</td>\n' +
                        '        <td class="bottomTd2">' + document.getElementById('createPath').value.substring(0, document.getElementById('createPath').value.indexOf(".jpg")) + '_waterMark.jpg' + '</td>\n' +
                        '    </tr>';
                }
                document.getElementById("createAlpha").value = '50';
                document.getElementById("createHeight").value = '10';
                document.getElementById("createX").value = '0';
                document.getElementById("createY").value = '100';
                document.getElementById('createPath').value = '';
            }
        }
    }
    xhr.send(JSON.stringify(createWaterMark));
}

function check(business, alpha, height, x, y, path) {
    var checkStr = '';
    if (business == null) {
        checkStr += '商家必须选择 ';
    }
    if (alpha == '' || isNaN(alpha) || alpha > 100 || alpha < 0) {
        checkStr += '请填写透明度（0-100） ';
    }
    if (height == '' || isNaN(height) || height > 25 || height < 0) {
        checkStr += '请填写高度（0-25，建议填写15以下） ';
    }
    if (x == '' || isNaN(x) || x > 100 || x < 0) {
        checkStr += '请填写x偏移量（0-100） ';
    }
    if (y == '' || isNaN(y) || x > 100 || x < 0) {
        checkStr += '请填写y偏移量（0-100） ';
    }
    if (path == null || path == '') {
        checkStr += '图片位置必须选择 ';
    }
    if (checkStr != '') {
        alert(checkStr);
        return false;
    }
    return true;
}