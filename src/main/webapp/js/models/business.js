var allBusinessList;
var allTypeList;

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
                    window.location.href = "../error.jsp"
                    return
                }
                var result = JSON.parse(xhr.responseText);
                if (result.ifSuccess) {
                    allBusinessList = result.bean.businesses;
                    allTypeList = result.bean.type;
                    handleBusinesses(result.bean.businesses, result.bean.type);
                    handleTypes(result.bean.types);
                } else {
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(filterBusiness));
}

function handleBusinesses(businessList, typeList) {
    var businesses = document.getElementById("buisnesses");
    businesses.innerHTML = '';
    for (var i = 0; i < businessList.length; i++) {
        businesses.innerHTML += '\n' +
            '    <tr>\n' +
            '        <td class="bottomTd1">' + businessList[i].code + '</td>\n' +
            '        <td class="bottomTd2">' + businessList[i].name + '</td>\n' +
            '        <td class="bottomTd3">' + businessList[i].address + '</td>\n' +
            '        <td class="bottomTd4">' + businessList[i].phone + '</td>\n' +
            '        <td class="bottomTd5">' + businessList[i].businessName + '</td>\n' +
            '        <td class="bottomTd6">' + typeList[i].name + '</td>\n' +
            '    </tr>';
    }
}

function handleTypes(typeList) {
    var createTypes = document.getElementById("createUserType");
    createTypes.innerHTML = '';
    for (var i = 0; i < typeList.length; i++) {
        var option = document.createElement("option");
        option.value = JSON.stringify(typeList[i]);
        option.text = typeList[i].name;
        createTypes.add(option);
    }
    var modifyTypes = document.getElementById("modifyUserType");
    modifyTypes.innerHTML = '';
    for (var i = 0; i < typeList.length; i++) {
        var option = document.createElement("option");
        option.value = JSON.stringify(typeList[i]);
        option.text = typeList[i].name;
        modifyTypes.add(option);
    }
}

function create() {
    var name = document.getElementById("createName").value;
    var address = document.getElementById("createAddress").value;
    var phone = document.getElementById("createPhone").value;
    var businessName = document.getElementById("createBusinessName").value;
    var logoSrc = document.getElementById("createLogo").value;
    var userType = document.getElementById("createUserType").value;
    var macAddress = document.getElementById("createMacAddress").value;
    var macAddress2 = document.getElementById("createMacAddress2").value;
    if (!check(name, address, phone, businessName, macAddress)) {
        return
    }
    if (logoSrc == "" || logoSrc == null) {
        alert('商标必须选择');
    }
    var createBusiness = {
        name: name,
        address: address,
        phone: phone,
        businessName: businessName,
        logoSrc: logoSrc,
        userType: JSON.parse(userType).code,
        macAddress: macAddress,
        macAddress2: macAddress2
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
                    window.location.href = "../error.jsp"
                    return
                }
                var result = JSON.parse(xhr.responseText);
                if (result.ifSuccess) {
                    allBusinessList = result.bean.businesses;
                    allTypeList = result.bean.type;
                    handleBusinesses(result.bean.businesses, result.bean.type);
                    document.getElementById("createName").value = '';
                    document.getElementById("createAddress").value = '';
                    document.getElementById("createPhone").value = '';
                    document.getElementById("createBusinessName").value = '';
                    document.getElementById("createLogo").value = '';
                    document.getElementById("createUserType").options[0].selected = true;
                    document.getElementById("createMacAddress").value = '';
                    document.getElementById("createMacAddress2").value = '';
                    alert(result.info);
                } else {
                    alert(result.errorInfo);
                }
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
                    window.location.href = "../error.jsp"
                    return
                }
                var result = JSON.parse(xhr.responseText);
                if (result.ifSuccess) {
                    allBusinessList = result.bean.businesses;
                    allTypeList = result.bean.type;
                    var modifyBusinesses = document.getElementById("modifyBusinesses");
                    modifyBusinesses.innerHTML = '';
                    for (var i = 0; i < result.bean.businesses.length; i++) {
                        var option = document.createElement("option");
                        option.value = JSON.stringify(result.bean.businesses[i]);
                        option.text = result.bean.businesses[i].code;
                        modifyBusinesses.add(option);
                    }
                    if (result.bean.businesses.length > 0) {
                        modifyBusinessCode("modifyBusinesses");
                    }
                    alert(result.info);
                } else {
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(modifyFilterBusiness));
}

function modifyBusinessCode(id) {
    var index = JSON.parse(document.getElementById(id).selectedIndex);
    document.getElementById("modifyName").value = allBusinessList[index].name;
    document.getElementById("modifyAddress").value = allBusinessList[index].address;
    document.getElementById("modifyPhone").value = allBusinessList[index].phone;
    document.getElementById("modifyBusinessName").value = allBusinessList[index].businessName;
    var length = document.getElementById("modifyUserType").options.length;
    var typeIndex = 0;
    for (var i = 0; i < length; i++) {
        var userType = JSON.parse(document.getElementById("modifyUserType").options[i].value);
        if (userType.name == allTypeList[index].name) {
            typeIndex = i;
            break;
        }
    }
    document.getElementById("modifyUserType").options[typeIndex].selected = true;
    document.getElementById("modifyMacAddress").value = allTypeList[index].macAddress;
    document.getElementById("modifyMacAddress2").value = allTypeList[index].macAddress2;
}

function update() {
    var business = document.getElementById("modifyBusinesses").value;
    var name = document.getElementById("modifyName").value;
    var address = document.getElementById("modifyAddress").value;
    var phone = document.getElementById("modifyPhone").value;
    var businessName = document.getElementById("modifyBusinessName").value;
    var logoSrc = document.getElementById("modifyLogo").value;
    var userType = document.getElementById("modifyUserType").value;
    var macAddress = document.getElementById("modifyMacAddress").value;
    var macAddress2 = document.getElementById("modifyMacAddress2").value;
    if (!check(name, address, phone, businessName, macAddress)) {
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
        logoSrc: logoSrc,
        userType: JSON.parse(userType).code,
        macAddress: macAddress,
        macAddress2: macAddress2
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
                    window.location.href = "../error.jsp"
                    return
                }
                var result = JSON.parse(xhr.responseText);
                if (result.ifSuccess) {
                    allBusinessList = result.bean.businesses;
                    allTypeList = result.bean.type;
                    handleBusinesses(result.bean.businesses, result.bean.type);
                    document.getElementById("modifyBusinesses").innerHTML = '';
                    document.getElementById("modifyName").value = '';
                    document.getElementById("modifyAddress").value = '';
                    document.getElementById("modifyPhone").value = '';
                    document.getElementById("modifyBusinessName").value = '';
                    document.getElementById("modifyLogo").value = '';
                    document.getElementById("modifyUserType").options[0].selected = true;
                    document.getElementById("modifyMacAddress").value = '';
                    document.getElementById("modifyMacAddress2").value = '';
                    alert(result.info);
                } else {
                    alert(result.errorInfo);
                }
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
                    window.location.href = "../error.jsp"
                    return
                }
                var result = JSON.parse(xhr.responseText);
                if (result.ifSuccess) {
                    allBusinessList = result.bean.businesses;
                    allTypeList = result.bean.type;
                    var deleteBusinesses = document.getElementById("deleteBusinesses");
                    deleteBusinesses.innerHTML = '';
                    for (var i = 0; i < result.bean.businesses.length; i++) {
                        var option = document.createElement("option");
                        option.value = JSON.stringify(result.bean.businesses[i]);
                        option.text = result.bean.businesses[i].code;
                        deleteBusinesses.add(option);
                    }
                    if (result.bean.businesses.length > 0) {
                        deleteBusinessCode("deleteBusinesses")
                    }
                    alert(result.info);
                } else {
                    alert(result.errorInfo);
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
                    window.location.href = "../error.jsp"
                    return
                }
                var result = JSON.parse(xhr.responseText);
                if (result.ifSuccess) {
                    allBusinessList = result.bean.businesses;
                    allTypeList = result.bean.type;
                    handleBusinesses(result.bean.businesses, result.bean.type);
                    document.getElementById("deleteBusinesses").innerHTML = '';
                    document.getElementById("deleteName").innerHTML = '';
                    document.getElementById("deleteAddress").innerHTML = '';
                    document.getElementById("deletePhone").innerHTML = '';
                    document.getElementById("deleteBusinessName").innerHTML = '';
                    alert(result.info);
                } else {
                    alert(result.errorInfo);
                }
            }
        }
    }
    xhr.send(JSON.stringify(deleteBusiness));
}

function check(name, address, phone, businessName, macAddress) {
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
    if (macAddress == "" || macAddress == null) {
        checkStr += '物理地址必须填写 '
    }
    if (checkStr != '') {
        alert(checkStr);
        return false;
    }
    return true;
}