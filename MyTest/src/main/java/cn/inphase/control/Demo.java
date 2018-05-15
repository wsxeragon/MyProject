package cn.inphase.control;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.junit.Test;

import com.google.gson.Gson;

public class Demo {
	public static String[] classNo0 = new String[] { "101", "102", "103", "104", "105" };
	public static String[] classNo = new String[classNo0.length];
	public static String[] classRoom0 = new String[] { "101", "102", "103", "201", "202", "203" };
	public static String[] classRoom = new String[classRoom0.length];
	public static String[] teacherNo0 = new String[] { "0001", "0002", "0003", "0004", "0005", "0006", "0007", "0008" };
	public static String[] teacherNo = new String[teacherNo0.length];
	public static String[] subject0 = new String[] { "chinese", "math", "english", "physica", "biology", "history",
			"geography" };
	public static String[] subject = new String[subject0.length];
	public static String[] times0 = new String[] { "11", "12", "13", "14", "21", "22", "23", "24", "31", "32", "33",
			"34", "41", "42", "43", "44", "51", "52", "53", "54" };
	public static String[] times = new String[times0.length];
	public static List<Double> weights = new LinkedList<>();
	// 初始化样本
	public static List<String> initlist = new LinkedList<>();
	{
		for (int i = 0; i < classNo.length; i++) {
			while (true) {
				int ran = new Random().nextInt(classNo.length);
				if (classNo[ran] == null) {
					classNo[ran] = classNo0[i];
					break;
				}
			}
		}
		for (String s : classNo) {
			System.out.print(s + ",");
		}
		System.out.println();
		for (int i = 0; i < classRoom.length; i++) {
			while (true) {
				int ran = new Random().nextInt(classRoom.length);
				if (classRoom[ran] == null) {
					classRoom[ran] = classRoom0[i];
					break;
				}
			}
		}
		for (String s : classRoom) {
			System.out.print(s + ",");
		}
		System.out.println();
		for (int i = 0; i < teacherNo.length; i++) {
			while (true) {
				int ran = new Random().nextInt(teacherNo.length);
				if (teacherNo[ran] == null) {
					teacherNo[ran] = teacherNo0[i];
					break;
				}
			}
		}
		for (String s : teacherNo) {
			System.out.print(s + ",");
		}
		System.out.println();
		for (int i = 0; i < subject.length; i++) {
			while (true) {
				int ran = new Random().nextInt(subject.length);
				if (subject[ran] == null) {
					subject[ran] = subject0[i];
					break;
				}
			}
		}
		for (String s : subject) {
			System.out.print(s + ",");
		}
		System.out.println();
		for (int i = 0; i < times.length; i++) {
			while (true) {
				int ran = new Random().nextInt(times.length);
				if (times[ran] == null) {
					times[ran] = times0[i];
					break;
				}
			}
		}
		for (String s : times) {
			System.out.print(s + ",");
		}
		System.out.println();
		StringBuffer sb = new StringBuffer();
		for (int a = 0; a < classNo.length; a++) {
			for (int b = 0; b < classRoom.length; b++) {
				for (int c = 0; c < teacherNo.length; c++) {
					for (int d = 0; d < subject.length; d++) {
						for (int e = 0; e < times.length; e++) {
							sb.append(classNo[a]).append("_").append(classRoom[b]).append("_").append(teacherNo[c])
									.append("_").append(subject[d]).append("_").append(times[e]);
							initlist.add(sb.toString());
							sb.delete(0, sb.length());
						}
					}
				}
			}
		}
	}

	// 根据权重优化
	public List<String> eliminateByWeight(List<String> list) {
		weights.clear();
		for (int i = 0; i < list.size(); i++) {
			double point = 0;
			String[] strings = list.get(i).split("_");
			// 课程配时间
			if (strings[3].equals("chinese") || strings[3].equals("math") || strings[3].equals("english")) {
				// 上午
				if (strings[4].toCharArray()[1] == '1') {
					point += 40;
				} else {
					point += 20;
				}
			} else {
				// 上午
				if (strings[4].toCharArray()[1] == '1') {
					point += 20;
				} else {
					point += 30;
				}
			}
			// 教室因素
			if (strings[1].toCharArray()[0] == '1') {
				point += 30;
			} else {
				point += 15;
			}
			// 老师因素
			// if (strings[2].toCharArray()[3] == '1' ||
			// strings[2].toCharArray()[3] == '2'
			// || strings[2].toCharArray()[3] == '3') {
			// point += 30;
			// } else {
			// point += 25;
			// }
			if (point < 0) {
				list.remove(i);
				i--;
			} else {
				weights.add(point);
			}
		}
		return list;
	}

	// 排序，权重大的排在前面，增加存活率
	private static void fastSort(List<Double> arr, List<String> simpleList, int left, int right) {
		if (left < right) {
			System.out.println(left);
			double s = arr.get(left);
			String temp1 = simpleList.get(left);
			int i = left;
			int j = right + 1;
			while (true) {
				// 向右找大于s的元素的索引
				while (i + 1 < arr.size() && arr.get(++i) > s)
					;
				// 向左找小于s的元素的索引
				while (j - 1 > -1 && arr.get(--j) < s)
					;
				// 如果i >= j 推出循环
				if (i >= j) {
					break;
				} else {
					// 交换i和j位置的元素
					double t = arr.get(i);
					arr.set(i, arr.get(j));
					arr.set(j, t);
					String temp = simpleList.get(i);
					simpleList.set(i, simpleList.get(j));
					simpleList.set(j, temp);
				}
			}
			arr.set(left, arr.get(j));
			arr.set(j, s);
			simpleList.set(left, simpleList.get(j));
			simpleList.set(j, temp1);
			// 对左面进行递归
			fastSort(arr, simpleList, left, j - 1);
			// 对右面进行递归
			fastSort(arr, simpleList, j + 1, right);
		}
	}

	// 淘汰 选修出现次数少于主修
	public List<String> eliminate2(List<String> list) {
		int flag = 0;
		for (int i = 0; i < list.size(); i++) {
			// 101|***|****|****|****|11
			String[] simple = list.get(i).split("_");
			String subject = simple[3];
			if (flag < 5000 && subject.equals("biology") && new Random().nextInt(100) == 1) {
				flag++;
			} else if (subject.equals("biology")) {
				list.remove(i);
				i--;
			}

		}
		return list;
	}

	public List<String> eliminate3(List<String> list) {
		int flag = 0;
		for (int i = 0; i < list.size(); i++) {
			// 101|***|****|****|****|11
			String[] simple = list.get(i).split("_");
			String subject = simple[3];
			if (flag < 5000 && subject.equals("physica") && new Random().nextInt(100) == 1) {
				flag++;
			} else if (subject.equals("physica")) {
				list.remove(i);
				i--;
			}

		}
		return list;
	}

	// 淘汰 选修出现次数少于主修
	public List<String> eliminate4(List<String> list) {
		int flag = 0;
		for (int i = 0; i < list.size(); i++) {
			// 101|***|****|****|****|11
			String[] simple = list.get(i).split("_");
			String subject = simple[3];
			if (flag < 5000 && subject.equals("history") && new Random().nextInt(100) == 1) {
				flag++;
			} else if (subject.equals("history")) {
				list.remove(i);
				i--;
			}

		}
		return list;
	}

	// 淘汰 选修出现次数少于主修
	public List<String> eliminate5(List<String> list) {
		int flag = 0;
		for (int i = 0; i < list.size(); i++) {
			// 101|***|****|****|****|11
			String[] simple = list.get(i).split("_");
			String subject = simple[3];
			if (flag < 5000 && subject.equals("geography") && new Random().nextInt(100) == 1) {
				flag++;
			} else if (subject.equals("geography")) {
				list.remove(i);
				i--;
			}

		}
		return list;
	}

	// 淘汰不符合硬约束的
	public List<String> eliminate0(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			// 101|***|****|****|****|11
			String[] simple = list.get(i).split("_");
			String classno = simple[0];
			String time = simple[4];
			String pattern1 = classno + "_([\\w]*)_" + time;
			System.out.println(pattern1);
			for (int j = i + 1; j < list.size(); j++) {
				// 同一班级在同一时间只能安排一门课程
				String Str1 = list.get(j);
				if (Pattern.matches(pattern1, Str1)) {
					list.remove(j);
					j--;
				}

			}
		}
		return list;
	}

	// 淘汰不符合硬约束的
	public List<String> eliminate1(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			// 101|***|****|****|****|11
			String[] simple = list.get(i).split("_");
			String teacherno = simple[2];
			String time = simple[4];
			String pattern1 = "([\\d]*)_([\\d]*)_" + teacherno + "_([a-z]*)_" + time;
			System.out.println(pattern1);
			for (int j = i + 1; j < list.size(); j++) {
				// 同一教师在同一时间只能安排一门课程
				String Str1 = list.get(j);
				if (Pattern.matches(pattern1, Str1)) {
					list.remove(j);
					j--;
				}
			}
		}
		return list;
	}

	@Test
	public void test() {
		System.out.println(initlist.size());
		List<String> list0 = initlist;
		list0 = eliminateByWeight(initlist);
		System.out.println(list0.size());
		System.out.println(weights.size());
		fastSort(weights, list0, 0, weights.size() - 1);
		System.out.println("排序完成");
		// 软约束
		list0 = eliminate2(list0);
		list0 = eliminate3(list0);
		list0 = eliminate4(list0);
		list0 = eliminate5(list0);
		System.out.println(list0);
		// 硬约束
		list0 = eliminate0(list0);
		System.out.println(list0.size());
		System.out.println(new Gson().toJson(list0));
		list0 = eliminate1(list0);
		System.out.println(list0.size());
		System.out.println(new Gson().toJson(list0));

		// List<Integer> list12 = new LinkedList<>();
		// list12.add(1);
		// list12.add(1);
		// list12.add(2);
		// list12.add(3);
		// list12.add(3);
		// list12.add(2);
		// list12.add(1);
		// list12.add(4);
		// list12.add(2);
		// list12.add(3);
		// list12.add(3);
		// list12.add(4);
		// list12.add(1);
		// list12.add(5);
		// list12.add(4);
		// list12.add(4);
		// list12.add(5);
		// list12.add(4);
		// list12.add(3);
		// list12.add(1);
		// list12.add(5);
		// for (int i = 0; i < list12.size(); i++) {
		// int temp = list12.get(i);
		// for (int j = i + 1; j < list12.size(); j++) {
		// System.out.println(j);
		// if (list12.get(j) == temp) {
		// list12.remove(j);
		// j--;
		// }
		// }
		// }
		// System.out.println(list12);
	}
}
