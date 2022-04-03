
1、需求
  ① 用户登录
  ② 登录成功，显示主界面。左侧显示好友列表；上端显示欢迎词。如果不是自己的空间。显示超链接：返回自己的空间。下端显示日志列表
  ③ 查看日志详情：
    - 日志本身的信息(作者头像、昵称、日志标题、日志内容、日志的日期)
    - 回复列表(回复者的头像、昵称、回复内容、回复日期等)
    - 主人回复信息
  ④ 删除日志
  ⑤ 删除特定回复
  ⑥ 删除特定主人回复
  ⑦ 添加日志、添加回复、添加主人回复
  ⑧ 点击左侧好友链接，进入好友的空间
  
2、数据库设计
  ① 抽取实体 ： 用户(用户登录信息、用户详情信息)、日志、回帖、主人回复
  
  ② 分析其中的属性 ：
    - 用户登录信息 ： 账号、密码、头像、昵称
    - 用户详情信息 ： 真实姓名、星座、血型、邮箱、手机号...
    - 日志 ： 标题、内容、日期、作者
    - 回复 ： 内容、日期、作者、日志
    - 主人回复 ： 内容、日期、作者、回复
  
  ③ 分析实体之间的关系
    - 用户 : 日志       1:1 PK
    - 日志 : 回复       1:N
    - 回复 : 主人回复    1:1 UK
    - 用户 : 好友       M:N

3、数据库的范式：
  ① 第一范式：列不可再分
  ② 第二范式：一张表只表达一层含义(只描述一件事情)
  ③ 第三范式：表中的每一列和主键都是直接依赖关系，而不是间接依赖
  
4、数据库设计的范式和数据库的查询性能很多时候是相悖的，需要根据实际的业务情况做选择：
  - 查询频率不高的情况下，更倾向于提高数据库的设计方式，从而提高存储效率
  - 查询频率较高的情况下，更倾向于降低数据库设计的范式，允许特定的冗余，从而提高查询的性能


5、left.html页面没有样式，同时数据也不展示，因为：直接去请求静态页面资源，那么并没有执行super.processTemplate()，也就是Thmeleaf没有起作用，所以使用th表达式没有效果
    解决方法就是新增一个 PageController类，添加方法 page 方法
    public string page(String page) {
        return page;   //  frame/left
    }
    目的是执行 super.processTemplate() 方法，让thymeleaf渲染



4月1号：

    1、点击左侧的好友链接，进入好友的空间
        - 根据 UserBasic的id 获取指定 id(这是点击好友的id) 的 List<Topic> 列表
        然后将这个列表设置到 session 作用域中的 userBasic(当前登录用户实例中) 的 friendList 字段中的 topicList
        因为好友也是一个 UserBasic 对象实例，被保存在登录用户实例中的 friendList 字段里
  
        错误：跳转后，在左侧(left)中显示index页面
        - 解决：给超链接添加 target 属性： target="_top" 保证在顶层窗口显示整个 index 页面
        
        
    2、日志详情页面实现 
        - 已知topic的id，需要根据topic的id获取特定topic
        - 获取这个topic关联的所有回复
        - 如果这个回复有主人回复，需要查询出来
        - 获取到的topic中author和hostReply需要特别设置
        
        
    3、删除回复
        - 如果回复有关联的主人回复，需要先删除主人回复
        - 其次再删除回复
        - 删除之后，需要给客户端发一个重定向，让他重新请求，detail页面
    
    
    4、删除日志
        - 删除日志，首先需要考虑是否有关联的回复
        - 而删除回复首先需要考虑是否有关联的的主人回复
        - 最后才能删除日志
        - 如果不是自己的空间，则不能删除日志
        - 删除日志之后重定向到 main 页面，日志列表
    
    
    5、日期格式
        - thymeleaf中使用 th:text="${#dates.format(session.topic.topicDate, 'yyyy-MM-dd HH:mm:ss')}" 
        这个公共内置对象格式化日期
  
  
  
  
    6、基本流程
        ① 服务器启动后，访问的页面是： http://localhost:8080/QQ1.1/page.do?operate=page&page=login
            - 其中，page 映射 PageController 类，方法用 operate=page 调用此类的 page 方法，并且传入一个参数 login
            - 该 page 方法会返回 login 字符串给调度器(DispatcherServlet)由调度器调用父类的 processTemplate 方法渲染页面
        ② 进入登录页面后，填写表单，提交给 user.do ，并带上隐藏域 operate=login ，user 映射 UserControllet 类，调用此类的 login 方法
            - 这个 login 方法会判断用户是否存在于数据库，并且查询该用户是否有日志，如果有取出日志列表，然后返回给调度器 index ，让它渲染 index 页面
        ③ index页面是由 left.html(显示好友列表) top.html(显示顶部) main.html(日志列表) 三个html页面组成的
            - index 页面分别会向调度器发送请求 th:src="@{page.do?operate=page&page=frames/top}" 令调度器渲染三个页面
        
  
    7、DispatcherServlet中步骤大致分为：
        - 从application作用域中获取IOC容器
        - 解析servletPath，在IOC容器中寻找对应的Controller组件
        - 准备operate指定的方法所要求的参数
        - 调用operate指定的方法
        - 接收到执行operate指定的方法的返回值，对返回值进行处理 - 视图处理
        
    8、DispatcherServlet能够从application作用域获取到IOC容器的原因
        - 解析IOC的配置文件，创建一个一个组件，并完成组件之间字段的依赖关系的注入
        - 将IOC容器保存到application作用域中
        
  
  
  
  
  
  
  
  
  
  
  