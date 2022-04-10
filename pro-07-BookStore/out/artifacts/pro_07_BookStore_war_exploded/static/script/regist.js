
function $(id) {
    return document.getElementById(id);
}


function ckRegist() {
    // 用户名不能为空，而且是 6 ~ 16 位数字和字母组成
    var nameTxt = $("nameTxt");
    var nameReg = /[0-9a-zA-Z]+/;
    var name = nameTxt.value;
    var nameSpan = $("nameSpan");
    if (!nameReg.test(name)) {
        nameSpan.style.visibility="visible";
        return false;
    } else {
        nameSpan.style.visibility="hidden";
    }
    
    
    // 密码长度至少8位
    var pwd = $("pwdTxt").value;
    var pwdReg = /[\w]{8,}/;  ///^(?![0-9+&])(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$/;
    var pwdSpan = $("pwdSpan");
    if (!pwdReg.test(pwd)) {
        pwdSpan.style.visibility="visible";
        return false;
    } else {
        pwdSpan.style.visibility="hidden";
    }
    
    
    // 两次密码输入不一致
    if ($("pwdAgain").value==$("pwdTxt").value) {
        $("pwdAgainSpan").style.visibility="visible";
        return false;
    } else {
        $("pwdAgainSpan").style.visibility="hidden";
    }
    
    
    
    // 正确的邮箱格式
    var email = $("emailTxt").value;
    var emailSpan = $("emailSpan");
    var emailReg = /^[a-zA-Z0-9_\.-]+@([a-zA-Z0-9-]+[\.]{1})+[a-zA-Z]+$/;
    
    if (!emailReg.test(email)) {
        emailSpan.style.visibility="visible";
        return false;
    } else {
        emailSpan.style.visibility="hidden";
    }
    
    return true;
}


// 如果想要发送异步请求，我们需要一个关键的对象 XMLHttpRequest
var xmlHttpRequest;

function createXMLHttpRequest() {
    if (window.XMLHttpRequest) {
        // 符合 DOM2 标准的浏览器，XMLHttpRequst的创建方式
        xmlHttpRequest = new XMLHttpRequest();
    } else if(window.ActiveXObject) {
        // IE 游览器
        try {
            xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
        } catch(e) {
            xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
        }

    }
}

function ckName(name) {
    createXMLHttpRequest();
    
    var url = "user.do?operate=ckName&name="+name;
    xmlHttpRequest.open("GET", url, true);
    
    // 设置回调函数
    xmlHttpRequest.onreadystatechange = ckNameCB;
    
    // 发送请求
    xmlHttpRequest.send();
    
}


function ckNameCB() {
    
    
    if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200){
        
        // xmlHttpRequest.responseText 表示服务器端响应给我的文本内容
        //alert(xmlHttpRequest.responseText); 
        
        var responstText = xmlHttpRequest.responseText;
        // {'name':'1'}
        
        if (responstText=="{'name':'1'}") {
            alert("用户名已经被注册!");
        } else {
            //alert("允许注册!");
        }
        
    }
    
    
}




