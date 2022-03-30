package com.myspringmvc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;




// 中央控制器 
public class DispatcherServlet extends ViewBaseServlet {

    
    private Map<String, Object> beanMap = new HashMap<>();
    

    // 解析 .xml 文件，将内容存放到 beanMap 中
    public void init() throws ServletException {
        super.init();   // 调用
        try {
            // 获得类加载器，读取 .xml 文件
            InputStream input =
                    DispatcherServlet.class.getClassLoader().getResourceAsStream("applicationContext.xml");
            

            // 1、创建DocumentBuilderFactory 实例
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // 2、创建DocumentBuilder 实例
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            // 3、创建Document对象
            Document document = documentBuilder.parse(input);

            // 获取文件中所有的 bean 标签
            NodeList beanNodeList = document.getElementsByTagName("bean");

            for (int i = 0; i < beanNodeList.getLength(); ++i) {
                // 获取里面的每个元素
                Node beanNode = beanNodeList.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element)beanNode;
                    String beanId = beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");
                    Class<?> controllerBeanClass = Class.forName(className);
                    Object beanObj = controllerBeanClass.newInstance();
                    
                    beanMap.put(beanId, beanObj);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    
    // （"*.do"），得到所有的请求
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 设置编码
        //req.setCharacterEncoding("UTF-8");

        
        //  /假设url是：http://localhost:8080/fruit_1.9/hello.do
        // 那么servletPath是：  /hello.do   或者得到： /fruit.do
        // 那么把它截取得剩 hello  或 fruit
        // 思路：1、 /hello -> hello   2、 hello -> HelloController
        
        
        String servletPath = req.getServletPath();
        servletPath = servletPath.substring(1);
        int lastDotInder = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0, lastDotInder);

        //System.out.println("servletPath = " + servletPath);
        
        Object controllerBeanObj = beanMap.get(servletPath);


        
        
        String operation = req.getParameter("operation");

        if (operation == null) {
            operation = "index";
        }


        try {
            // 因为所有的方法签名都不同了，所以需要取出所有的方法，然后遍历
            Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (operation.equals(method.getName())) {
                    // 1、统一获取请求参数

                    // 1.1、获取当前方法的参数，返回参数数组
                    Parameter[] parameters = method.getParameters();
                    // 1.2、创建一个Object数组 parsmeterValues用来承载参数的值
                    Object[] parsmeterValues = new Object[parameters.length];
                    
                    // 1.3、循环取出各个参数请求而来时，带的 value
                    for (int i = 0; i < parameters.length; ++i) {
                        Parameter parameter = parameters[i];
                        
                        //1.4、如果是request、response、session 那么就不是通过请求中获取 value 的方式了
                        String parameterName = parameter.getName();
                        if ("req".equals(parameterName)) {
                            parsmeterValues[i] = req;
                        } else if ("resp".equals(parameterName)) {
                            parsmeterValues[i] = resp;
                        } else if ("session".equals(parameterName)) {
                            parsmeterValues[i] = req.getSession();
                        } else {
                            
                            // 1.5、取出客户端请求时带来的值
                            String parameterValue = req.getParameter(parameterName);
                            
                            // 1.6、如果参数的类型是Integer或Double，应该进行转换，然后存入参数数组
                            Object tmp = parameterValue;
                            if (parameterValue != null) {
                                if ("java.lang.Integer".equals(parameter.getType().getName())) {
                                    tmp = Integer.parseInt(parameterValue);
                                } else if ("java.lang.Double".equals(parameter.getType().getName())) {
                                    tmp = Double.parseDouble(parameterValue);
                                }
                            }
                            
                            parsmeterValues[i] = tmp;
                        }
                    }
                    

                    // 2、controller 组件中的方法调用
                    method.setAccessible(true);
                    Object retValue = method.invoke(controllerBeanObj, parsmeterValues);
                    
                    String methodRetValue = (String)retValue;
                    // 3、视图处理
                    if (methodRetValue.startsWith("redirect:")) {  // 比如：redirect:fruit.do
                        String redirectStr = methodRetValue.substring("redirect:".length());  // 只剩后面的 fruit.do
                        resp.sendRedirect(redirectStr);
                    } else {
                        super.processTemplate(methodRetValue, req, resp);  // 比如返回：edit，那就渲染edit.html页面
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}
