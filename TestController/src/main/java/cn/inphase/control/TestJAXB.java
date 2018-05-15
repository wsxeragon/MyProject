package cn.inphase.control;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import cn.inphase.domain.Student;

public class TestJAXB {
	public static void main(String[] args)
			throws JAXBException, ParserConfigurationException, SAXException, IOException {
		Student student = new Student("tom", 19, 1);
		student.setSubjects(Arrays.asList(new String[] { "微积分", "大物", "模电" }));
		System.out.println(ObjectToXmlString(student));

		///
		String path = TestJAXB.class.getClassLoader().getResource("student.xml").getFile();
		System.out.println(XmlFileToObject(new File(path), new Student()));
	}

	@Test
	private void test1() throws JAXBException {
		Student student = new Student("tom", 19, 1);
		student.setSubjects(Arrays.asList(new String[] { "微积分", "大物", "模电" }));
		System.out.println(ObjectToXmlString(student));
	}

	// 实体类转xml字符串
	public static String ObjectToXmlString(Object obj) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
		Marshaller marshaller = jaxbContext.createMarshaller();
		// 设置序列化的编码格式
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		// 设置格式化输出
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		StringWriter writer = new StringWriter();
		marshaller.marshal(obj, writer);

		return writer.toString();
	}

	// xml文件转实体类
	public static Object XmlFileToObject(File file, Object obj)
			throws JAXBException, ParserConfigurationException, SAXException, IOException {
		// 将xml文件转为document对象
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);

		// 解析document对象
		JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		obj = unmarshaller.unmarshal(document);

		return obj;

	}
}
