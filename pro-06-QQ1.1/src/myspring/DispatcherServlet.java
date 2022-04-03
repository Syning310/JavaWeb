package myspring;



import IOC.BeanFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

// *.do
public class DispatcherServlet extends ViewBaseServlet {
    
    private BeanFactory beanFactory;
    
    
    public void init() throws ServletException {
        super.init();
        
        // 从 application 作用域去获取 IOC 容器
        ServletContext application = getServletContext();
        Object beanFactoryObj = application.getAttribute("beanFactory");
        
        if (beanFactoryObj != null) {
            beanFactory = (BeanFactory)beanFactoryObj;
        } else {
            throw new RuntimeException("IOC容器获取失败!");
        }
    }
    

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        // 取得 url 
        String servletPath = request.getServletPath();  //  user.do
        
        servletPath = servletPath.substring(1);  // 截取第一个字符  /
        int lastDoIndex = servletPath.lastIndexOf(".do");  // 取得 .do 的起始下标
        servletPath = servletPath.substring(0, lastDoIndex);  
        
        // 从 beanFactory 中取出对象
        Object controllerBeanObj = beanFactory.getBean(servletPath);
        
        String operate = request.getParameter("operate");
        
        if (operate == null) {
            operate = "index";
        }
        
        Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();
        
        
        try {
            for (Method method : methods) {
                if (operate.equals(method.getName())) {  // 如果取出的方法和 operate 相等，说明是我们想要的
                    
                    // 1、统一获取请求参数

                    // 1.1、获取当前方法的参数，返回参数数组
                    Parameter[] parameters = method.getParameters();
                    // 1.2、Object parameterValues 用来承载参数的值
                    Object[] parameterValues = new Object[parameters.length];

                    for (int i = 0; i < parameters.length; ++i) {
                        Parameter parameter = parameters[i];
                        String parameterName = parameter.getName(); // 取得参数名

                        // 如果参数名是 request、response、session 那么就不是通过请求中获取参数
                        if ("request".equals(parameterName)) {
                            parameterValues[i] = request;
                        } else if ("response".equals(parameterName)) {
                            parameterValues[i] = response;
                        } else if ("session".equals(parameterName)) {
                            parameterValues[i] = request.getSession();
                        } else {
                            // 从请求中获取参数
                            String requestParameterValue = request.getParameter(parameterName);
                            String typeName = parameter.getType().getName();  // 获得参数的全类名

                            Object parameterObj = requestParameterValue;   // Obj 指向从请求中取出来的值
                            if (requestParameterValue != null) {
                                // 如果方法需要的参数类型是 Integer ; 那么将从 request 请求中取出来的参数转换成 Integer
                                if ("java.lang.Integer".equals(typeName)) {
                                    parameterObj = Integer.parseInt(requestParameterValue);
                                }
                            }

                            parameterValues[i] = parameterObj;
                        }
                    }

                    
                    // 2、controller 组件中的方法调用
                    method.setAccessible(true);
                    Object returnObj = method.invoke(controllerBeanObj, parameterValues);  // 传入需要调用的对象实例，和参数数组


                    // 3、视图处理，将controller对象方法的返回值进行类型转换
                    String methodReturnStr = (String)returnObj;

                    // 如果以重定向开头的字符串，就执行客户端重定向
                    if (methodReturnStr.startsWith("redirect:")) {
                        String redirectStr = methodReturnStr.substring("redirect:".length());
                        response.sendRedirect(redirectStr);
                    } else {
                        super.processTemplate(methodReturnStr, request, response);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
