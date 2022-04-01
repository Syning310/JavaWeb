package com.myspringmvc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;




// 中央控制器 
public class DispatcherServlet extends HttpServlet {

    
    private Map<String, Object> beanMap = new HashMap<>();
    

    // 解析 .xml 文件，将内容存放到 beanMap 中
    public void init() {
 
        try {
            // 获得类加载器，读取 .xml 文件
            InputStream input =
                    getClass().getClassLoader().getResourceAsStream("applicationContext.xml");


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
                    
                    Method setServletContextMethod = controllerBeanClass.getDeclaredMethod("setServletContext", ServletContext.class);
                    setServletContextMethod.invoke(beanObj, this.getServletContext());
                    
//                    Field servleContextField = controllerBeanClass.getDeclaredField("servletContext");
//                    servleContextField.setAccessible(true);
//                    servleContextField.set(beanObj, this.getServletContext());

                    beanMap.put(beanId, beanObj);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    
    // （"*.do"），得到所有的请求
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置编码
        req.setCharacterEncoding("UTF-8");

        
        //  /假设url是：http://localhost:8080/fruit_1.9/hello.do
        // 那么servletPath是：  /hello.do   或者得到： /fruit.do
        // 那么把它截取得剩 hello  或 fruit
        // 思路：1、 /hello -> hello   2、 hello -> HelloController
        
        
        String servletPath = req.getServletPath();
        System.out.println(servletPath);    //  /fruit.do
        servletPath = servletPath.substring(1);
        int lastDotInder = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0, lastDotInder);

        System.out.println("servletPath = " + servletPath);
        
        Object controllerBeanObj = beanMap.get(servletPath);


        
        
        String operation = req.getParameter("operation");

        if (operation == null) {
            operation = "index";
        }

        try {
            // 获取所有的方法再遍历效率太慢，直接传入 方法名, 参数1, 参数2
            Method method = controllerBeanObj.getClass().getDeclaredMethod(operation, HttpServletRequest.class, HttpServletResponse.class);
            if (method != null) {
                method.setAccessible(true);
                method.invoke(controllerBeanObj, req, resp);
            } else {
                throw new RuntimeException("operator值非法!");
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
   
        
        // 获取所有的方法，效率太慢
//        Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();
//        for (Method m : methods) {
//            // 获取方法名
//            String methodName = m.getName();
//            if (operation.equals(methodName)) {
//                try {
//                    // 找到和 operating 的值，相同的方法，通过反射技术调用它
//                    m.invoke(controllerBeanObj, req, resp);
//                    return;
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
        //// 如果没有找到和 operating 值相同的 方法名，那么值非法
        //throw new RuntimeException("operator值非法!");
    }
}
