package listener;

import ioc.BeanFactory;
import ioc.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoalerListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        
        // 1、获取 ServletContext 对象
        ServletContext application = servletContextEvent.getServletContext();
        
        // 2、获取上下文的初始化参数
        String path = application.getInitParameter("contextConfigLocation");
        
        // 3、创建 IOC 容器
        BeanFactory beanFactory = new ClassPathXmlApplicationContext();
        
        // 4、将 IOC 容器保存到 application 作用域中
        application.setAttribute("beanFactory", beanFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
