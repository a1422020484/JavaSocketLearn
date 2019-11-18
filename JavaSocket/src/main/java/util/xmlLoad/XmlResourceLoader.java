package util.xmlLoad;

import java.beans.Beans;
import java.net.URL;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlResourceLoader {
	public static void main(String[] args) throws DocumentException {
		String configxml = "./src/main/resources/xml/student.xml";
		// 创建SAXReader对象
		SAXReader reader = new SAXReader();
		// xml文件的位置
		URL sources = Beans.class.getClassLoader().getResource(configxml);
		// 创建document对象,并读取xml文件 （解析xml文件）
		Document document = reader.read(sources);
		// 读取元素 getRootElement() --》 获取父节点 elements() --> 所有节点
		List<Element> elements = document.getRootElement().elements();

		for (Element element : elements) {
			// <beans id="cat" class="com.oukele.Readerxml.animal.Cat" ></beans>
			// id 是一个属性 可以自定义
//			String id = element.attributeValue("id");// 获取id属性
//			String clazz = element.attributeValue("class");// 获取class属性
		}
	}
}
