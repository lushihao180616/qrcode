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
    var path = document.getElementById("createPath").value;
    if (!check(business, path)) {
        return
    }
    var createWaterMark = {
        businessCode: JSON.parse(business).code,
        path: path
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/video/add", false);
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
                if (data.substring(0, 4) == '生成成功') {
                    document.getElementById("tableTitle").style.visibility = "visible";
                    var videos = document.getElementById("videos");
                    videos.innerHTML += '\n' +
                        '    <tr>\n' +
                        '        <td class="bottomTd1">' + document.getElementById('createPath').value + '</td>\n' +
                        '        <td class="bottomTd2">' + document.getElementById('createPath').value.substring(0, document.getElementById('createPath').value.indexOf(".mp4")) + '_new.mp4' + '</td>\n' +
                        '    </tr>';
                }
                document.getElementById('createPath').value = '';
                alert(data);
            }
        }
    }
    xhr.send(JSON.stringify(createWaterMark));
}

function check(business, path) {
    var checkStr = '';
    if (business == null) {
        checkStr += '商家必须选择 ';
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