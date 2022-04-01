package listener;


import IOC.BeanFactory;
import IOC.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoalerListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        
        // 1、获取ServletContext对象
        ServletContext application = servletContextEvent.getServletContext();
        
        // 2、获取上下文的初始化参数
        String path = application.getInitParameter("contextConfigLocation");
        
        // 3、创建IOC容器
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(path);
        
        // 4、将IOC容器保存到application作用域
        application.setAttribute("beanFactory", beanFactory);
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
