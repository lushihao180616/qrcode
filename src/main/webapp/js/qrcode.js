function init() {
    getTemple();
    getBusiness();
    getRecord();
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
                var templeList = JSON.parse(xhr.responseText);
                var temples = document.getElementById("temples");
                temples.innerHTML = '';
                for (var i = 0; i < templeList.length; i++) {
                    var option = document.createElement("option");
                    option.value = JSON.stringify(templeList[i]);
                    option.text = templeList[i].code;
                    temples.add(option);
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
                var businessList = JSON.parse(xhr.responseText);
                var businesses = document.getElementById("businesses");
                businesses.innerHTML = '';
                for (var i = 0; i < businessList.length; i++) {
                    var option = document.createElement("option");
                    option.value = JSON.stringify(businessList[i]);
                    option.text = businessList[i].code;
                    businesses.add(option);
                }
                if(businessList.length > 0){
                    document.getElementById("nowBusiness_name").innerText = '名        称：' + businessList[0].name;
                    document.getElementById("nowBusiness_address").innerText = '地        址：' + businessList[0].address;
                    document.getElementById("nowBusiness_phone").innerText = '电        话：' + businessList[0].phone;
                    document.getElementById("nowBusiness_businessName").innerText = '联  系  人：' + businessList[0].businessName;
                }
            }
        }
    }
    xhr.send(JSON.stringify(filterBusinessCode));
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
                var recordList = JSON.parse(xhr.responseText);
                var records = document.getElementById("records");
                records.innerHTML = '\n' +
                    '    <tr>\n' +
                    '        <th class="bottomTh1">商家</th>\n' +
                    '        <th class="bottomTh2">模板</th>\n' +
                    '        <th class="bottomTh3">文件名</th>\n' +
                    '        <th class="bottomTh4">位置</th>\n' +
                    '        <th class="bottomTh5">价格</th>\n' +
                    '        <th class="bottomTh6">创建时间</th>\n' +
                    '    </tr>';

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
        }
    }
    xhr.send(JSON.stringify(filterRecord));
}

function create() {
    var createQRCode = {
        message: document.getElementById("message").value,
        templeCode: JSON.parse(document.getElementById("temples").value).code,
        businessCode: JSON.parse(document.getElementById("businesses").value).code,
        fileName: document.getElementById("fileName").value
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/qrcode/create", false);
    // 添加http头，发送信息至服务器时内容编码类型
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.setRequestHeader('dataType', 'json');
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            if (xhr.status == 200 || xhr.status == 304) {
                var data = xhr.responseText;
                alert(data);
                init();
                document.getElementById("message").value = '';
                document.getElementById("temples").options[0].selected = true;
                document.getElementById("businesses").options[0].selected = true;
                document.getElementById("fileName").value = '';
            }
        }
    }
    xhr.send(JSON.stringify(createQRCode));
}