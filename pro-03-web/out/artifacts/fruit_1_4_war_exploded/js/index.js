function delFruit(name) {
    if (confirm('确认是否删除？')) {
        window.location.href='del.do?name=' + name;
        /* wind表示的是当前窗口(网页)  local指的是地址栏   href是地址栏的属性    
        = 等号后面的意思是给href赋值，给del.do发请求，同时把name值带过去  */
    }
}