package cn.inphase.control;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.junit.Test;

public class TestFunction {
	private static Map<Character, Integer> map0 = new HashMap<>();
	static {
		map0.put('0', 0);
		map0.put('1', 1);
		map0.put('2', 2);
		map0.put('3', 3);
		map0.put('4', 4);
		map0.put('5', 5);
		map0.put('6', 6);
		map0.put('7', 7);
		map0.put('8', 8);
		map0.put('9', 9);
	}

	@Test
	public void test() {

		Function<String, Integer> s2i = new Function<String, Integer>() {

			@Override
			public Integer apply(String t) {
				char[] arr = t.toCharArray();
				int sum = 0;
				for (char c : arr) {
					if (map0.get(c) != null) {
						sum *= 10;
						sum += (map0.get(c));
					} else {
						sum = 0;
						break;
					}
				}
				return sum;
			}
		};

		System.out.println(s2i.apply("123") + 2);
	}

	@Test
	public void test5() {
		TestFunction testFunction = new TestFunction();

		List<String> list = Arrays.asList(new String[] { "王明", "王红", "李明", "王强", "周明", "赵明" });

		testFunction.test4(list, new Checker() {
			@Override
			public boolean check(String str) {
				return str.startsWith("王");
			}
		}, new Caller() {
			@Override
			public String call(String str) {
				return str;
			}
		});

		testFunction.test4(list, str -> {
			return str.startsWith("王");
		}, str -> {
			return str;
		});

	}

	public static void main(String[] args) {
		TestFunction testFunction = new TestFunction();
		testFunction.test2(() -> {
			System.out.println("hello");
		});
		testFunction.test3(() -> {
			System.out.println("hello");
		});

		testFunction.test2(new MyFunction() {

			@Override
			public void hello() {
				System.out.println("hello");
			}
		});
		testFunction.test3(new MyInterface() {

			@Override
			public void hello() {
				System.out.println("hello");
			}
		});

	}

	private void test2(MyFunction myFunction) {
		myFunction.hello();
	}

	private void test3(MyInterface myInterface) {
		myInterface.hello();
	}

	private void test4(List<String> list, Checker checker, Caller caller) {
		for (String str : list) {
			if (checker.check(str)) {
				System.out.println(caller.call(str));
			}
		}
	}
}

// 普通接口
interface MyInterface {
	void hello();

	// 1.8新特性，所有接口都可以定义default 实现方法
	default public void say1() {
		System.out.println("1");
	}

}

// 函数式接口
@FunctionalInterface
interface MyFunction {

	void hello();

	// 函数式接口只能定义一个抽象方法
	// void hello1();

	// 但是Object类的固有方法不被数量限制
	String toString();

	boolean equals(Object o);

	// 1.8新特性，所有接口都可以定义default 实现方法
	default public void say1() {
		System.out.println("1");
	}

}

interface Checker<T extends String> {
	boolean check(String str);
}

interface Caller<T extends String> {
	String call(String str);
}
