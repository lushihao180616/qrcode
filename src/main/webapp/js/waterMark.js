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
    var ifShowLogo = document.getElementById("createLogo").value;
    var ifShowFont = document.getElementById("createFont").value;
    var width = document.getElementById("createWidth").value;
    var height = document.getElementById("createHeight").value;
    var x = document.getElementById("createX").value;
    var y = document.getElementById("createY").value;
    var path = document.getElementById("createPath").value;
    if (!check(business, ifShowLogo, ifShowFont, width, height, x, y, path)) {
        return
    }
    var createWaterMark = {
        businessCode: JSON.parse(business).code,
        ifShowLogo: parseInt(ifShowLogo),
        ifShowFont: parseInt(ifShowFont),
        width: parseInt(width),
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
                document.getElementById("createLogo")[0].selected = true;
                document.getElementById("createFont")[0].selected = true;
                document.getElementById("createWidth").value = '100';
                document.getElementById("createHeight").value = '100';
                document.getElementById("createX").value = '50';
                document.getElementById("createY").value = '50';
                document.getElementById('createPath').value = '';

                document.getElementById("createWidth").style.visibility = "visible";
                document.getElementById("createHeight").style.visibility = "visible";
                document.getElementById("createX").style.visibility = "visible";
                document.getElementById("createY").style.visibility = "visible";
                document.getElementById("createWidthSpan").style.visibility = "visible";
                document.getElementById("createHeightSpan").style.visibility = "visible";
                document.getElementById("createXSpan").style.visibility = "visible";
                document.getElementById("createYSpan").style.visibility = "visible";
            }
        }
    }
    xhr.send(JSON.stringify(createWaterMark));
}

function check(business, ifShowLogo, ifShowFont, width, height, x, y, path) {
    var checkStr = '';
    if (business == null) {
        checkStr += '商家必须选择 ';
    }
    if (isNaN(ifShowLogo)) {
        checkStr += '是否加商标必须选择 ';
    } else {
        if (width == '' || isNaN(width)) {
            checkStr += '请填写宽度（数字） ';
        }
        if (height == '' || isNaN(height)) {
            checkStr += '请填写高度（数字） ';
        }
        if (x == '' || isNaN(x)) {
            checkStr += '请填写x偏移量（数字） ';
        }
        if (y == '' || isNaN(y)) {
            checkStr += '请填写y偏移量（数字） ';
        }
    }
    if (isNaN(ifShowFont)) {
        checkStr += '是否加底部文字必须选择 ';
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

function showSomeThing() {
    if (document.getElementById("createLogo")[1].selected) {
        document.getElementById("createWidth").style.visibility = "hidden";
        document.getElementById("createHeight").style.visibility = "hidden";
        document.getElementById("createX").style.visibility = "hidden";
        document.getElementById("createY").style.visibility = "hidden";
        document.getElementById("createWidthSpan").style.visibility = "hidden";
        document.getElementById("createHeightSpan").style.visibility = "hidden";
        document.getElementById("createXSpan").style.visibility = "hidden";
        document.getElementById("createYSpan").style.visibility = "hidden";
    } else if (document.getElementById("createLogo")[0].selected) {
        document.getElementById("createWidth").style.visibility = "visible";
        document.getElementById("createHeight").style.visibility = "visible";
        document.getElementById("createX").style.visibility = "visible";
        document.getElementById("createY").style.visibility = "visible";
        document.getElementById("createWidthSpan").style.visibility = "visible";
        document.getElementById("createHeightSpan").style.visibility = "visible";
        document.getElementById("createXSpan").style.visibility = "visible";
        document.getElementById("createYSpan").style.visibility = "visible";
    }
}