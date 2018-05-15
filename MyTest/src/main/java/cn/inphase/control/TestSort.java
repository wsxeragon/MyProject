package cn.inphase.control;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TestSort {

	// 插入排序
	@Test
	public void test1() {

		Integer[] intArr = new Integer[] { 12, 2, 32, 14, 25, 36, 7, 8 };

		for (int i = 1; i < intArr.length; i++) {
			int temp = intArr[i];
			int j = i - 1;
			while (j >= 0 && temp < intArr[j]) {
				intArr[j + 1] = intArr[j];
				j--;
			}
			intArr[j + 1] = temp;

		}
		// 基本类型的数组是不能asList()成列表的，泛型不接受基本类型，需要使用包装类数组
		List<Integer> list = Arrays.asList(intArr);
		System.out.println(list);
	}

	@Test
	public void test2() {
		MyNode root = new MyNode(10, null, null);

		root.insert(root, 9);
		root.insert(root, 11);
		root.insert(root, 12);

		MyNode.preOrder(root);
		System.out.println("");
		MyNode.midOrder(root);
		System.out.println("");
		MyNode.postOrder(root);
	}

}

class MyNode {

	int data;

	MyNode right;

	MyNode left;

	public MyNode(int data, MyNode right, MyNode left) {
		super();
		this.data = data;
		this.right = right;
		this.left = left;
	}

	// 插入数据
	public void insert(MyNode root, int data) {
		if (data >= root.data) {
			if (root.right == null) {
				root.right = new MyNode(data, null, null);
			} else {
				insert(root.right, data);
			}

		} else {
			if (root.left == null) {
				root.left = new MyNode(data, null, null);
			} else {
				insert(root.left, data);
			}
		}
	}

	@Override
	public String toString() {
		return "MyNode [data=" + data + ", right=" + right + ", left=" + left + "]";
	}

	public static void preOrder(MyNode root) {
		if (root != null) {
			System.out.print(root.data + "-");
			preOrder(root.left);
			preOrder(root.right);
		}
	}

	public static void midOrder(MyNode root) {
		if (root != null) {
			midOrder(root.left);
			System.out.print(root.data + "-");
			midOrder(root.right);
		}
	}

	public static void postOrder(MyNode root) {
		if (root != null) {
			postOrder(root.left);
			postOrder(root.right);
			System.out.print(root.data + "-");
		}
	}

}