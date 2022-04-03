package IOC;


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
    
    public ClassPathXmlApplicationContext() {
        this("applicationContext.xml");
    }
    
    // 解析 .xml 文件，将内容存放到 beanMap 容器中
    public ClassPathXmlApplicationContext(String path) {
        
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
            
            // 1、创建 DocumenetBuilderFactory 实例
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // 2、创建 DocumentBuilder 实例
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            // 3、创建 Document 对象，传入字节流
            Document document = documentBuilder.parse(inputStream);
            
            // 4、获取文件中所有的 bean 标签，放如List集合
            NodeList beanNodeList = document.getElementsByTagName("bean");

            // 通过标签中的id和class参数取出全类名，利用反射创建实例对象,然后 id 映射于 实例对象
            for (int i = 0; i < beanNodeList.getLength(); ++i) {
                Node beanNode = beanNodeList.item(i);  // 从 List 中取出一个元素
                
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element)beanNode;  
                    String beanId = beanElement.getAttribute("id");  // 取得标签中 id 的内容
                    
                    String className = beanElement.getAttribute("class");  // 取得标签中的 class 内容，也就是全类名
                    
                    Class beanClass = Class.forName(className);  // 传入全类名利用反射，给类创建 Class 对象
                    
                    // 利用 Class 对象创建实例 (调用的是类提供的默认构造函数)
                    Object beanObj = beanClass.newInstance();
                    // 将 bean 实例对象和标签中取出的 id 保存到 map 容器中， id 映射 beanObj
                    beanMap.put(beanId, beanObj); 
                    // 到此处需要注意的是，bean和bean之间的依赖关系还没有被设置
                }
            }
            
            // 5、组装bean之间的依赖关系; 也就是给有 property=??? 子标签的类实例的字段 赋值
            for (int i = 0; i < beanNodeList.getLength(); ++i) {
                Node beanNode = beanNodeList.item(i);  // 从List中取出一个元素
                
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element)beanNode;  
                    String beanId = beanElement.getAttribute("id");  // 取得标签中的 id 的内容
                    
                    NodeList beanChildNodeList = beanElement.getChildNodes();  // 取出 bean 标签中的 property 子标签 放入 List 集合中
                    for (int j = 0; j < beanChildNodeList.getLength(); ++j) {
                        Node beanChildNode = beanChildNodeList.item(j);  // 取出 property 子标签集合中的一个元素
                        
                        // 判断子标签是否名为 property
                        if (beanChildNode.getNodeType() == Node.ELEMENT_NODE 
                                && "property".equals(beanChildNode.getNodeName())) {
                            Element propertyElement = (Element)beanChildNode;
                            
                            String propertyName = propertyElement.getAttribute("name");  // 取出字段名
                            String propertyRef = propertyElement.getAttribute("ref"); // 取出 ref 映射的 id 的标签
                            
                            // 1、找到 PropertyRef 对应的实例，ref 映射的 id ; 在上一个 for 循环中已经创建了实例，并放入了 map 容器中
                            Object refObj = beanMap.get(propertyRef);  // 如：Service 中的字段是 DAO 的实例
                            
                            // 2、将 refObj 设置到当前 bean 对应的实例 property 字段上
                            Object beanObj = beanMap.get(beanId);  // 取出有 property 子标签的实例
                            Class beanClazz = beanObj.getClass();  // 取得需要设置字段的实例的 Class 对象

                            Field propertyField = beanClazz.getDeclaredField(propertyName);  // 传入需要赋值的字段名
                            propertyField.setAccessible(true);  // 暴破
                            propertyField.set(beanObj, refObj);  // 给字段赋值
                        }
                    }
                }
            }
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    @Override
    public Object getBean(String id) {
        return beanMap.get(id);
    }
    
    
}
