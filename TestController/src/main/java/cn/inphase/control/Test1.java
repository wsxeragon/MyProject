package cn.inphase.control;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.mysql.jdbc.Connection;

import cn.inphase.domain.User2;
import cn.inphase.tool.DateUtils;
import cn.inphase.tool.PropertiesUtil;

public class Test1 {
	@Test
	public void test0() {

		String a = getMD5(new File("C:\\Users\\WSX\\Desktop\\test\\线条3.mp4"));

		System.out.println("a.txt的摘要值为：" + a);

	}

	static char hexdigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/** 
	 
	 * @funcion 对文件全文生成MD5摘要  
	
	 * @param file:要加密的文件 
	
	 * @return MD5摘要码 
	
	 */

	public static String getMD5(File file) {

		FileInputStream fis = null;

		try {

			MessageDigest md = MessageDigest.getInstance("MD5");

			fis = new FileInputStream(file);

			byte[] buffer = new byte[2048];

			int length = -1;

			while ((length = fis.read(buffer)) != -1) {

				md.update(buffer, 0, length);

			}

			byte[] b = md.digest();

			return byteToHexString(b);

		} catch (Exception e) {

			e.printStackTrace();

			return null;

		} finally {

			try {

				fis.close();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

	}

	/** 
	
	 * @function 把byte[]数组转换成十六进制字符串表示形式 
	
	 * @param tmp  要转换的byte[] 
	
	 * @return 十六进制字符串表示形式 
	
	 */

	private static String byteToHexString(byte[] tmp) {

		String s;

		// 用字节表示就是 16 个字节

		// 每个字节用 16 进制表示的话，使用两个字符，所以表示成 16 进制需要 32 个字符

		// 比如一个字节为01011011，用十六进制字符来表示就是“5b”

		char str[] = new char[16 * 2];

		int k = 0; // 表示转换结果中对应的字符位置

		for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节转换成 16 进制字符的转换

			byte byte0 = tmp[i]; // 取第 i 个字节

			str[k++] = hexdigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换, >>>
														// 为逻辑右移，将符号位一起右移

			str[k++] = hexdigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换

		}

		s = new String(str); // 换后的结果转换为字符串

		return s;

	}

	@Test
	public void test3() {
		Double a = 5.0;
		int b = (int) (double) a;
		// float f = 3.4;
		int i = 2;
		// i = 2+4.0;
		Integer f1 = 100, f2 = 100, f3 = 150, f4 = 150;
		Integer f5 = new Integer(100);
		Integer f7 = new Integer(100);
		Integer f6 = new Integer(150);
		int f8 = 100;
		System.out.println(f1 == f2);// 自动装箱时，-128到127间的数，不new新对象，直接引用常量池里的Integer缓存对象
		System.out.println(f1 == f8);// 对象和基本类型比较，会自动拆箱成基本类型
		System.out.println(f3 == f4);// 超出范围，所以new了两个新对象
		System.out.println(f1 == f5);// 一个指向常量池中的对象，一个指向堆中new出的新对象
		System.out.println(f3 == f6);// 一个是超出范围，new的新对象，一个是手动new的新对象
		System.out.println(f5 == f7);// 直接在堆中new了两个新对象
		System.out.println(2 << 4);
		System.out.println(7 >> 1);

		String s1 = "Programming";
		String s2 = new String("Programming");
		String s3 = "Program" + "ming";
		System.out.println(s1 == s2);// s1直接指向字符串常量池中的“Programming”
										// s2指向堆中的对象，堆中的对象再指向字符串常量池中的“Programming”
		System.out.println(s1 == s3);// String拼接会弃用掉常量池中原来的字符串，创建一个新的，
										// 而新的已经存在于常量池中于是直接指向已存在的“Programming”
										// 所以频繁操作字符串，使用stringbuffer(线程安全，但效率低，与之相反的是stringbuilder)
		System.out.println(s1 == s1.intern());// intern方法会得到字符串对象在常量池中对应的版本的引用
												// （如果常量池中有一个字符串与String对象的equals结果是true），
												// 如果常量池中没有对应的字符串，则该字符串将被添加到常量池中，然后返回常量池中字符串的引用。

		B b0 = new B();
		/**
		 *父静态代码块
		 *子静态代码块
		 *父代码块
		 *父构造器
		 *子代码块
		 *子构造器
		*/

	}

	// break 加 标识符
	@Test
	public void test4() {
		for (int x = 0; x < 3; x++) {
			System.out.println("X:" + x);
			A: for (int y = 0; y < 3; y++) {
				if (y == 1) {
					break A;// 不要理解成直接跳到A处开始循环，而是直接跳出A包含的循环！！！
				}
				System.out.println("Y:" + y);
				for (int z = 0; z < 3; z++) {
					System.out.println("Z:" + z);
				}
			}

		}
	}

	@Test
	public void test5() throws UnsupportedEncodingException {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		System.out.println(calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DAY_OF_MONTH));

		MyCall myCall = new MyCall(10);
		FutureTask<Boolean> future = new FutureTask<>(myCall);

		new Thread(future).start();

		try {
			boolean r = future.get(100, TimeUnit.SECONDS);
			System.out.println(r);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int j = 3;
		System.out.println(j++);
		System.out.println(++j);
		Map<String, Object> map0 = new HashMap<>();
		Map<String, Object> map1 = Collections.synchronizedMap(map0);

		Set<String> set0 = new HashSet<>();
		Set<String> set01 = Collections.synchronizedSet(set0);

		System.out.println(1 << 30);
		System.out.println(Math.pow(2, 30));
		BigDecimal bigDecimal = new BigDecimal(Math.pow(2, 30));
		System.out.println(bigDecimal.toString());

		int i2 = 129;
		byte b0 = (byte) i2;
		System.out.println(b0);

		A a2 = new A();
		B b2 = new B();

		A ab2 = (A) b2;// 子类可强转为父类
		// B ba2 = (B) a2 通常父类不能强转为子类 classCastException
		A a3 = new B();
		B ba3 = (B) a3;// 声明为父类，实际为子类的对象 是可以实现父类强转为子类的

		short s0 = 1;
		short s1 = 2;
		// s1 =s0 + s1; a+b0 会提升为int操作
		s1 += s0;// 自带类型转换
		System.out.println(3 * 0.1 == 0.3);// false
		System.out.println(1 * 0.3 == 0.3);// true
		System.out.println(3 * 0.2 == 2 * 0.3);// false

		String str1 = "hello";
		String str2 = "he" + new String("llo");
		System.out.println(str1 == str2);// f
		str2 = "he" + "llo";
		System.out.println(str1 == str2);// t
	}

	private static String Name = PropertiesUtil.get("name");

	@Test
	public void testProperties() {

		System.out.println(Name);
	}

	@Test
	public void testSQL() {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=utf-8", "root", "");
			String sql = "select id,name0 from test1 where type0=?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, "2");
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("id") + " " + rs.getString("name0"));
			}

			// 批处理
			sql = "insert into test1 (name0,age0,type0,type1) values (?,?,?,?)";
			ps = connection.prepareStatement(sql);
			for (int i = 0; i < 10; i++) {
				ps.setString(1, "JOKER" + i);
				ps.setString(2, "" + (18 + i));
				ps.setString(3, "" + i);
				ps.setString(4, "E");

				ps.addBatch();
			}
			int[] result = ps.executeBatch();
			for (int s : result) {
				System.out.println(s);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	/**
	 * 
	 (?=a)b  意思是a后面必须有个b才能匹配
	(?=.*[0-9])
	任意字符串后有一数字
	(?=.*[a-z])
	任意字符串后有一小写字母
	(?=.*[A-Z])
	任意字符串后有一大写字母
	.{6,10}
	6－10位任意字符
	整个正则表示6－10位字符，必须同时包含数字，小写字母，大写字母。
	 * */
	@Test
	public void testReg() {
		Pattern p = Pattern.compile("^(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*).{6,20}$");
		Matcher m = p.matcher("Wa112334");
		if (m.matches()) {
			System.out.println(true);
		} else {
			System.out.println(false);
		}

		User2 u = new User2();

		System.out.println(DateUtils.getNowDate());

		System.out.println("123".substring(1, 2));

	}

	// 递归反转字符串
	public String reverse(String str1) {
		if (str1 == null || str1.length() <= 1) {
			return str1;
		} else {
			return reverse(str1.substring(1)) + str1.charAt(0);
		}

	}

	// 实现Cloneable接口并重写Object类中的clone()方法
	@Test
	public void testClone() throws CloneNotSupportedException {
		Info info1 = new Info();
		Name name = new Name();
		name.setFirst("f");
		name.setLast("l");
		info1.setName(name);
		info1.setAge(18);
		Info info2 = (Info) info1.clone();
		System.out.println(info1.getName() == info2.getName());
	}

	// 实现Serializable接口，通过对象的序列化和反序列化实现克隆，可以实现真正的深度克隆
	public static <T> T myClone(T obj) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bout);
		oos.writeObject(obj);

		ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bin);
		return (T) ois.readObject();
	}

}

class A {
	static {
		System.out.println("父静态代码块");
	}

	{
		System.out.println("父代码块");
	}

	public A() {
		System.out.println("父构造器");
	}

}

class B extends A {
	static {
		System.out.println("子静态代码块");
	}

	{
		System.out.println("子代码块");
	}

	public B() {
		System.out.println("子构造器");
	}

}

class MyCall implements Callable<Boolean> {

	int i = 0;

	public MyCall(int i) {
		this.i = i;
	}

	@Override
	public Boolean call() throws Exception {
		System.out.println(i++);
		return true;
	}

}

class Name implements Cloneable {
	private String first;
	private String last;

	public String getFirst() {
		return first;
	}

	public String getLast() {
		return last;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public void setLast(String last) {
		this.last = last;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}

class Info implements Cloneable {
	private Name name;
	private int age;

	public int getAge() {
		return age;
	}

	public Name getName() {
		return name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setName(Name name) {
		this.name = name;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		Info info1 = (Info) super.clone();
		info1.setName((Name) name.clone());// 不加这句话clone出的新对象的name属性和老对象的name属性指向同一个Name对象，属于浅克隆
											// 加这句话就是深度克隆，clone出的新对象的name属性和老对象的name属性指向不同的Name对象
		return info1;
	}
}
