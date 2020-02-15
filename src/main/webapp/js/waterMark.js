function getBusiness() {
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
    var createWaterMark = {
        businessCode: JSON.parse(document.getElementById("createBusinesses").value).code,
        ifShowLogo: parseInt(document.getElementById("createLogo").value),
        ifShowFont: parseInt(document.getElementById("createFont").value),
        width: parseInt(document.getElementById("createWidth").value),
        height: parseInt(document.getElementById("createHeight").value),
        x: parseInt(document.getElementById("createX").value),
        y: parseInt(document.getElementById("createY").value),
        path: document.getElementById('createPath').value
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

                if (data == '添加成功') {
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
            }
        }
    }
    xhr.send(JSON.stringify(createWaterMark));
}