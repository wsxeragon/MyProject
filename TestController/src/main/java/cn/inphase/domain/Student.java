package cn.inphase.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "student")
@XmlType(propOrder = { "name", "gender", "age", "subjects" }) 
// 指定属性的在xml中的顺序
// 1.对于@XmlElementWrapper标注的属性，不能出现在@XmlType的propOrder列表中。
// 2.对于所有@XmlElement标注过的属性，必须出现在@XmlType的propOrder列表中。
public class Student {

	private String name;
	private Integer age;
	private int gender;
	private List<String> subjects;

	public Student() {
		super();
	}

	public Student(String name, Integer age, int gender) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
	}

	@XmlElement(name = "name")
	// 只能写在get/set上，不写的话会自动生成同名的节点
	// 如果写在属性定义上会出现重复节点的问题，因为get/set方法不写注解也会自动生成节点
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "age0")
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	// 不写注解，会自动生成同名节点
	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	@XmlElementWrapper(name = "subjects")
	@XmlElement(name = "subject")
	public List<String> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + ", gender=" + gender + ", subjects=" + subjects + "]";
	}

}
