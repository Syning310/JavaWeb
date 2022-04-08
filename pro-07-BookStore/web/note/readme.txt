

1、需求分析



2、数据库设计
    实体分析：
        - 图书        book
        - 用户        user
        - 订单        orderBean
        - 购物车项     cartItem
        - 订单详情     orderItem
    实体属性：
        - 图书：书名、作者、价格、销量、库存、封面、状态
        - 用户：用户名、密码、邮箱、
        - 订单：订单编号、订单日期、订单金额、订单数量、订单状态、用户
        - 订单详情： 图书、数量、所属订单
        - 购物车项(项不是购物车，是一条记录)： 图书、数量、所属用户






3、实现
    - 登录验证
    - 获取书单并显示
    - 显示右上角的欢迎词和购物车数量

    - th:text="${#numbers.formatDecimal(shoppingItem.book.price * shoppingItem.buyCount, 0, 2)}"
      thmeleaf 格式化数值，只剩两位小数；第二个参数 0 表示不限制整数位最大位数，如果为 7 表示限制整数位最大 7 位
     
4、结账
    - 订单表添加一条记录
    - 订单详情表添加记录 = 结账用户的购物车详情表
    - 清空结账用户的购物车列表
    ① 将结账用户的购物车取出变成一条条 订单详情 和一条 订单表
    ② 清空已经结账的用户的购物车表
    
5、添加请求过滤器    
    - 添加一个过滤器，只允许请求 index 页面和 login 页面
    - 其他任何情况都不放行
    
6、过滤器判断是否是合法用户：
    - 新建 SessionFilter ，判断 session 中是否保存了 currUser
    - 如果没有 currUser ，表示当前不是一个登录合法的用户，应该跳转到登录页面让其登录
    
    - 但是添加过滤器之后，会说重定向次数过多，或url打不开
    - 所以需要给该类配置参数
    
    
7、 注册表单验证
    - <form>有一个事件，onsubmit
    - onsubmit="return faluse"; 那么表单点击提交按钮也不会提交
    - onsubmit="return true";   那么表单点击提交时就会提交


8、前后端交互  Ajax
    第一步: 客户端发送异步请求，并绑定对结果处理的回调函数
        - <input type="text" name="name" onblur="ckName()"/>
        - 定义chName方法：
            1、创建 XMLHttpRequest对象
            2、XMLHttpRequest对象操作步骤：
                ① open("GET", url, true);    第三个参数 true : 表示是否是异步发送
                ② onreadyStateChange 设置回调
                ③ send() 发送请求
            3、在回调函数中判断，XMLHttpRequest对象的状态： readyState(0-4)，status(200)
            
    第二步: 服务器端做校验，然后将校验结果响应给客户端

    Ajax:异步的JavaScript and XML
        目的：用来发送异步请求，然后当服务器给我响应的时候再进行回调操作
        好处：提高用户体验；局部刷新：降低服务器负担，减轻浏览器压力，减轻网络带宽压力
    步骤:
        1、创建 XMLHttpRequest 对象
        2、调用 open 进行设置: xmlHttpRequest.open("GET", url, true);
        3、绑定状态改变时执行的回调函数 - onreadystatechange
        4、发送请求 - send();
        5、编写回调函数，在回调函数中，我们只对 XMLHttpRequest 的 readystate 为 4 时感兴趣
                                    只对 XMLHttpRequst 的 status 为 200 时感兴趣
    readystate
    0 -> (未初始化)，还没有调用send()方法
    1 -> (载入)，已调用send()方法，正在发送请求
    2 -> (载入完成)，send()方法执行完成，已经接收到全部响应内容
    3 -> (交互)，正在解析响应内容
    4 -> (完成)，响应内容解析完成，可以在客户端调用了
    
    
    
    
    
    
    
    
    


