function init() {
    getBusiness();
}

function getBusiness() {
    var filterBusiness = {
        code: document.getElementById("filterCode").value,
        name: document.getElementById("filterName").value,
        address: document.getElementById("filterAddress").value,
        phone: document.getElementById("filterPhone").value,
        businessName: document.getElementById("filterBusinessName").value
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
                var businesses = document.getElementById("buisnesses");
                businesses.innerHTML = '\n' +
                    '    <tr>\n' +
                    '        <th class="bottomTh1">编号</th>\n' +
                    '        <th class="bottomTh2">名称</th>\n' +
                    '        <th class="bottomTh3">地址</th>\n' +
                    '        <th class="bottomTh4">电话</th>\n' +
                    '        <th class="bottomTh5">联系人</th>\n' +
                    '    </tr>';

                for (var i = 0; i < businessList.length; i++) {
                    businesses.innerHTML += '\n' +
                        '    <tr>\n' +
                        '        <td class="bottomTd1">' + businessList[i].code + '</td>\n' +
                        '        <td class="bottomTd2">' + businessList[i].name + '</td>\n' +
                        '        <td class="bottomTd3">' + businessList[i].address + '</td>\n' +
                        '        <td class="bottomTd4">' + businessList[i].phone + '</td>\n' +
                        '        <td class="bottomTd5">' + businessList[i].businessName + '</td>\n' +
                        '    </tr>';
                }
            }
        }
    }
    xhr.send(JSON.stringify(filterBusiness));
}

function create() {
    var createBusiness = {
        name: document.getElementById("createName").value,
        address: document.getElementById("createAddress").value,
        phone: document.getElementById("createPhone").value,
        businessName: document.getElementById("createBusinessName").value,
        logoSrc: document.getElementById("createLogo").value
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/business/create", false);
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
                init();
                document.getElementById("createName").value = '',
                    document.getElementById("createAddress").value = '',
                    document.getElementById("createPhone").value= '',
                    document.getElementById("createBusinessName").value= '',
                    document.getElementById("createLogo").value= ''
            }
        }
    }
    xhr.send(JSON.stringify(createBusiness));
}

function modifySearch() {
    var modifyFilterBusiness = {
        code: document.getElementById('modifyCode').value
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
                var modifyBusinesses = document.getElementById("modifyBusinesses");
                modifyBusinesses.innerHTML = '';
                for (var i = 0; i < businessList.length; i++) {
                    var option = document.createElement("option");
                    option.value = JSON.stringify(businessList[i]);
                    option.text = businessList[i].code;
                    modifyBusinesses.add(option);
                }
                if (businessList.length > 0) {
                    modifyBusinessCode("modifyBusinesses")
                }
            }
        }
    }
    xhr.send(JSON.stringify(modifyFilterBusiness));
}

function modifyBusinessCode(id) {
    var budiness = JSON.parse(document.getElementById(id).value);
    document.getElementById("modifyName").value = budiness.name;
    document.getElementById("modifyAddress").value = budiness.address;
    document.getElementById("modifyPhone").value = budiness.phone;
    document.getElementById("modifyBusinessName").value = budiness.businessName;
}

function update() {
    var modifyBusiness = {
        code: JSON.parse(document.getElementById("modifyBusinesses").value).code,
        name: document.getElementById("modifyName").value,
        address: document.getElementById("modifyAddress").value,
        phone: document.getElementById("modifyPhone").value,
        businessName: document.getElementById("modifyBusinessName").value,
        logoSrc: document.getElementById("modifyLogo").value
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/business/update", false);
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
                init();
                document.getElementById("modifyBusinesses").innerHTML = '';
                document.getElementById("modifyName").value = '';
                document.getElementById("modifyAddress").value = '';
                document.getElementById("modifyPhone").value = '';
                document.getElementById("modifyBusinessName").value = '';
                document.getElementById("modifyLogo").value = '';
            }
        }
    }
    xhr.send(JSON.stringify(modifyBusiness));
}

function deleteSearch() {
    var deleteFilterBusiness = {
        code: document.getElementById('deleteCode').value
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
                var deleteBusinesses = document.getElementById("deleteBusinesses");
                deleteBusinesses.innerHTML = '';
                for (var i = 0; i < businessList.length; i++) {
                    var option = document.createElement("option");
                    option.value = JSON.stringify(businessList[i]);
                    option.text = businessList[i].code;
                    deleteBusinesses.add(option);
                }
                if (businessList.length > 0) {
                    deleteBusinessCode("deleteBusinesses")
                }
            }
        }
    }
    xhr.send(JSON.stringify(deleteFilterBusiness));
}

function deleteBusinessCode(id) {
    var budiness = JSON.parse(document.getElementById(id).value);
    document.getElementById("deleteName").innerText = "名        称：" + budiness.name;
    document.getElementById("deleteAddress").innerText = "地        址：" + budiness.address;
    document.getElementById("deletePhone").innerText = "电        话：" + budiness.phone;
    document.getElementById("deleteBusinessName").innerText = "联  系  人：" + budiness.businessName;
}

function deleteOne() {
    var deleteBusiness = {
        code: JSON.parse(document.getElementById("deleteBusinesses").value).code,
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8090/qrcode/business/delete", false);
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
                init();
                document.getElementById("deleteBusinesses").innerHTML = '';
                document.getElementById("deleteName").innerHTML = '';
                document.getElementById("deleteAddress").innerHTML = '';
                document.getElementById("deletePhone").innerHTML = '';
                document.getElementById("deleteBusinessName").innerHTML = '';
            }
        }
    }
    xhr.send(JSON.stringify(deleteBusiness));
}