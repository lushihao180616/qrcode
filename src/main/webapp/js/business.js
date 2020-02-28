function init() {
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
                var result = JSON.parse(xhr.responseText);
                var businesses = document.getElementById("buisnesses");
                businesses.innerHTML = '';
                for (var i = 0; i < result.bean.length; i++) {
                    businesses.innerHTML += '\n' +
                        '    <tr>\n' +
                        '        <td class="bottomTd1">' + result.bean[i].code + '</td>\n' +
                        '        <td class="bottomTd2">' + result.bean[i].name + '</td>\n' +
                        '        <td class="bottomTd3">' + result.bean[i].address + '</td>\n' +
                        '        <td class="bottomTd4">' + result.bean[i].phone + '</td>\n' +
                        '        <td class="bottomTd5">' + result.bean[i].businessName + '</td>\n' +
                        '    </tr>';
                }
            }
        }
    }
    xhr.send(JSON.stringify(filterBusiness));
}

function create() {
    var name = document.getElementById("createName").value;
    var address = document.getElementById("createAddress").value;
    var phone = document.getElementById("createPhone").value;
    var businessName = document.getElementById("createBusinessName").value;
    var logoSrc = document.getElementById("createLogo").value;
    if (!check(name, address, phone, businessName, logoSrc)) {
        return
    }
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
                var result = JSON.parse(xhr.responseText);
                init();
                document.getElementById("createName").value = '';
                document.getElementById("createAddress").value = '';
                document.getElementById("createPhone").value = '';
                document.getElementById("createBusinessName").value = '';
                document.getElementById("createLogo").value = '';
                alert(result.info);
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
                var result = JSON.parse(xhr.responseText);
                var modifyBusinesses = document.getElementById("modifyBusinesses");
                modifyBusinesses.innerHTML = '';
                for (var i = 0; i < result.bean.length; i++) {
                    var option = document.createElement("option");
                    option.value = JSON.stringify(result.bean[i]);
                    option.text = result.bean[i].code;
                    modifyBusinesses.add(option);
                }
                if (result.bean.length > 0) {
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
    var business = document.getElementById("modifyBusinesses").value;
    var name = document.getElementById("modifyName").value;
    var address = document.getElementById("modifyAddress").value;
    var phone = document.getElementById("modifyPhone").value;
    var businessName = document.getElementById("modifyBusinessName").value;
    var logoSrc = document.getElementById("modifyLogo").value;
    if (!check(name, address, phone, businessName, logoSrc)) {
        return;
    }
    if (business == null) {
        alert("商家必须选择");
        return;
    }
    var modifyBusiness = {
        code: JSON.parse(business).code,
        name: name,
        address: address,
        phone: phone,
        businessName: businessName,
        logoSrc: logoSrc
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
                var result = JSON.parse(xhr.responseText);
                init();
                document.getElementById("modifyBusinesses").innerHTML = '';
                document.getElementById("modifyName").value = '';
                document.getElementById("modifyAddress").value = '';
                document.getElementById("modifyPhone").value = '';
                document.getElementById("modifyBusinessName").value = '';
                document.getElementById("modifyLogo").value = '';
                alert(result.info);
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
    var business = document.getElementById("deleteBusinesses").value;
    if (business == null) {
        alert("商家必须选择")
        return;
    }
    var deleteBusiness = {
        code: JSON.parse(business).code
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
                var result = JSON.parse(xhr.responseText);
                init();
                document.getElementById("deleteBusinesses").innerHTML = '';
                document.getElementById("deleteName").innerHTML = '';
                document.getElementById("deleteAddress").innerHTML = '';
                document.getElementById("deletePhone").innerHTML = '';
                document.getElementById("deleteBusinessName").innerHTML = '';
                alert(result.info);
            }
        }
    }
    xhr.send(JSON.stringify(deleteBusiness));
}

function check(name, address, phone, businessName, logoSrc) {
    var checkStr = '';
    if (name == "" || name == null) {
        checkStr += '名称必须填写 '
    }
    if (address == "" || address == null) {
        checkStr += '地址必须填写 '
    }
    if (phone == "" || phone == null) {
        checkStr += '手机号必须填写 '
    }
    if (businessName == "" || businessName == null) {
        checkStr += '联系人必须填写 '
    }
    if (logoSrc == "" || logoSrc == null) {
        checkStr += '商标必须选择 '
    }
    if (checkStr != '') {
        alert(checkStr);
        return false;
    }
    return true;
}