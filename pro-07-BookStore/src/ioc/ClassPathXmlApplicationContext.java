package ioc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ClassPathXmlApplicationContext implements BeanFactory {
    
    private Map<String, Object> beanMap = new HashMap<>();
    
    private String path = "applicationContext.xml";
    
    // 解析 .xml 文件，将内容放到 beanMap 容器中
    public ClassPathXmlApplicationContext() {
        
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
            
            // 1、创建 DocumentBuilderFactory 实例
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            
            // 2、创建 DocumentBuilder 实例
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            
            // 3、传入字节流，创建 Document 对象实例
            Document document = documentBuilder.parse(inputStream);
            
            // 4、获取文件中所有 bean 标签，放入 List 集合中
            NodeList beanNodeList = document.getElementsByTagName("bean");
            
            // 通过标签中的 id 和 class 参数，取出全类名，利用反射创建实例对象，然后放入 HashMap 容器中。key 为 id   实例 为 value
            for (int i = 0; i < beanNodeList.getLength(); ++i) {
                Node beanNode = beanNodeList.item(i);  // 从 List 中取出第一个元素
                
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element)beanNode;
                    
                    String beanId = beanElement.getAttribute("id");  // 取得标签中 id 的内容
                    
                    String className = beanElement.getAttribute("class");  // 取得标签中 class 的内容，也就是全类名
                    
                    Class beanClass = Class.forName(className);  // 传入全类名，利用反射，给类创建 Class 对象
                    
                    // 利用 Class 对象创建实例 (调用的是类提供的默认无参构造函数)
                    Object beanObj = beanClass.newInstance();
                    
                    // 将 beanObj 实例对象和标签中取出的 id 保存到 map 容器中， id 映射于 beanObj
                    beanMap.put(beanId, beanObj);
                    
                    // 注意: bean 与 bean 之间的依赖关系还没有被设置，也就是 字段 还未赋值
                }
            }

            // 5、组装 bean 之间的依赖关系，也就是给有 <property> 子标签的类实例的 字段 赋值
            for (int i = 0; i < beanNodeList.getLength(); ++i) {
                Node beanNode = beanNodeList.item(i);
                
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element)beanNode;
                    
                    String beanId = beanElement.getAttribute("id");  // 取得标签中的 id 内容
                    
                    NodeList beanChildNodeList = beanElement.getChildNodes();  // 取出 bean 标签中的 property 子标签，放入 List 集合中
                    
                    for (int j = 0; j < beanChildNodeList.getLength(); ++j) {
                        Node beanChildNode = beanChildNodeList.item(j);
                        
                        // 判断子标签是否名为 property 
                        if (beanChildNode.getNodeType() == Node.ELEMENT_NODE 
                                && "property".equals(beanChildNode.getNodeName())) {
                            
                            Element propertyElement = (Element)beanChildNode;
                            
                            String propertyName = propertyElement.getAttribute("name");  // 取出 name 的内容，也就是字段名，需要赋值的字段
                            // 取出 ref 映射的 id 的标签，而 id 映射的对象实例在上一个 for 循环中已经创建
                            String propertyRef = propertyElement.getAttribute("ref");  
                            
                            // 1、找到 propertyRef 对应的实例，ref 映射的 id， 在上一个 for 循环中已经创建了实例，放入了 map 容器中
                            Object refObj = beanMap.get(propertyRef);  
                            
                            // 2、将 refObj 设置到当前 beanId(就是有property标签的bean) 对应的实例的 字段上
                            
                            Object beanObj = beanMap.get(beanId);  // 取出 beanId 映射的实例，也就是有 property 标签的 bean
                            Class beanClazz = beanObj.getClass();  // 取得需要设置字段的实例的 Class 对象

                            Field propertyField = beanClazz.getDeclaredField(propertyName);   // 取出需要赋值的字段
                            propertyField.setAccessible(true); // 暴破
                            propertyField.set(beanObj, refObj);
                        }
                    }
                }
                
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    
    @Override
    public Object getBean(String id) {
        return beanMap.get(id);
    }
    
    
    
    
    
    
}
