package cn.inphase.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

public class Gene {
	private static final int BASE_INFO = 0;
	private static final int CLASSROOM_INDEX = 1;
	private static final int TIME_INDEX = 2;
	// 行政班
	public static String[] classNo = new String[] { "X101", "X102", "X103", "X104", "X105", "X106", "X107", "X108",
			"X109", "X110" };
	// 物理教学班
	public static String[] physica_classNo = new String[] { "P101", "P102", "P103", "P104", "P105", "P106", "P107",
			"P108", "P109", "P110", "P111", "P112", "P113" };
	// 生物教学班
	public static String[] biology_classNo = new String[] { "B101", "B102", "B103", "B104", "B105", "B106", "B107" };
	public static String[] classRoom = new String[] { "A101", "A102", "A103", "A201", "A202", "A203", "A301", "A302",
			"A303", "A401" };
	public static String[] teacherNo = new String[] { "0001C", "0002C", "0003C", "0004C", "0001M", "0002M", "0003M",
			"0004M", "0001B", "0002B", "0003B", "0001P", "0002P", "0003P", "0004P", "0005P", "0006P" };
	public static String[] subject = new String[] { "chinese", "math", "physica", "biology" };
	public static String[] times_am = new String[] { "11", "12", "13", "14", "21", "22", "23", "24", "31", "32", "33",
			"34", "41", "42", "43", "44", "51", "52", "53", "54" };
	public static String[] times_pm = new String[] { "15", "16", "17", "18", "25", "26", "27", "28", "35", "36", "37",
			"38", "45", "46", "47", "48", "55", "56", "57", "58" };
	private static List<String> simpleList = new LinkedList<>();
	private static List<String> initList = new LinkedList<>();
	{
		StringBuffer sb = new StringBuffer();
		simpleList.add(sb.append("X101").append("_").append("chinese").append("_").append("0001C").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("X102").append("_").append("chinese").append("_").append("0001C").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("X103").append("_").append("chinese").append("_").append("0001C").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("X104").append("_").append("chinese").append("_").append("0002C").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("X105").append("_").append("chinese").append("_").append("0002C").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("X106").append("_").append("chinese").append("_").append("0002C").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("X107").append("_").append("chinese").append("_").append("0003C").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("X108").append("_").append("chinese").append("_").append("0003C").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("X109").append("_").append("chinese").append("_").append("0004C").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("X110").append("_").append("chinese").append("_").append("0004C").toString());
		sb.delete(0, sb.length());

		simpleList.add(sb.append("X101").append("_").append("math").append("_").append("0001M").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("X102").append("_").append("math").append("_").append("0001M").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("X103").append("_").append("math").append("_").append("0002M").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("X104").append("_").append("math").append("_").append("0002M").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("X105").append("_").append("math").append("_").append("0002M").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("X106").append("_").append("math").append("_").append("0003M").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("X107").append("_").append("math").append("_").append("0003M").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("X108").append("_").append("math").append("_").append("0003M").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("X109").append("_").append("math").append("_").append("0004M").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("X110").append("_").append("math").append("_").append("0004M").toString());
		sb.delete(0, sb.length());

		simpleList.add(sb.append("P101").append("_").append("physica").append("_").append("0001P").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("P102").append("_").append("physica").append("_").append("0001P").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("P103").append("_").append("physica").append("_").append("0002P").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("P104").append("_").append("physica").append("_").append("0002P").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("P105").append("_").append("physica").append("_").append("0003P").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("P106").append("_").append("physica").append("_").append("0003P").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("P107").append("_").append("physica").append("_").append("0003P").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("P108").append("_").append("physica").append("_").append("0004P").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("P109").append("_").append("physica").append("_").append("0004P").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("P110").append("_").append("physica").append("_").append("0005P").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("P111").append("_").append("physica").append("_").append("0005P").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("P112").append("_").append("physica").append("_").append("0006P").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("P113").append("_").append("physica").append("_").append("0006P").toString());
		sb.delete(0, sb.length());

		simpleList.add(sb.append("B101").append("_").append("biology").append("_").append("0001B").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("B102").append("_").append("biology").append("_").append("0001B").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("B103").append("_").append("biology").append("_").append("0002B").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("B104").append("_").append("biology").append("_").append("0002B").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("B105").append("_").append("biology").append("_").append("0003B").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("B106").append("_").append("biology").append("_").append("0003B").toString());
		sb.delete(0, sb.length());
		simpleList.add(sb.append("B107").append("_").append("biology").append("_").append("0003B").toString());
		sb.delete(0, sb.length());
		for (int i = 0; i < simpleList.size(); i++) {
			String str = simpleList.get(i);
			// 选教室
			String classStr = classRoom[new Random().nextInt(classRoom.length)];

			// 选时间
			StringBuffer timeSb = new StringBuffer();
			// 语文数学教授八个课时
			if (str.contains("chinese") || str.contains("math")) {
				for (int j = 0; j < 8; j++) {
					while (true) {
						String timePart = randomTime(str, Arrays.asList(times_am), Arrays.asList(times_pm));
						if (!timeSb.toString().contains(timePart)) {
							timeSb.append(timePart).append("_");
							break;
						}
					}
				}
			}
			// 物理生物教授4个课时
			if (str.contains("physica") || str.contains("biology")) {
				for (int j = 0; j < 4; j++) {
					while (true) {
						String timePart = randomTime(str, Arrays.asList(times_am), Arrays.asList(times_pm));
						if (!timeSb.toString().contains(timePart)) {
							timeSb.append(timePart).append("_");
							break;
						}
					}
				}
			}
			timeSb.delete(timeSb.length() - 1, timeSb.length());
			// 拼装
			sb.append(str).append("-").append(classStr).append("-").append(timeSb);
			initList.add(sb.toString());
			sb.delete(0, sb.length());
		}

	}

	// 主修课选上午的概率为70% 选修选下午的概率为70%
	public String randomTime(String simple, List<String> temptimes0, List<String> temptimes1) {
		if (temptimes0.size() > 0 && temptimes1.size() > 0) {
			if (simple.contains("chinese") || simple.contains("math")) {
				if (new Random().nextInt(10) < 7) {
					return temptimes0.get(new Random().nextInt(temptimes0.size()));
				} else {
					return temptimes1.get(new Random().nextInt(temptimes1.size()));
				}
			} else {
				if (new Random().nextInt(10) < 7) {
					return temptimes1.get(new Random().nextInt(temptimes1.size()));
				} else {
					return temptimes0.get(new Random().nextInt(temptimes0.size()));
				}
			}
		} else {
			if (temptimes0.size() == 0) {
				// System.out.println("无上午时间可用");
				return temptimes1.get(new Random().nextInt(temptimes1.size()));
			} else {
				// System.out.println("无下午时间可用");
				return temptimes0.get(new Random().nextInt(temptimes0.size()));
			}
		}

	}

	// private int roulette() {
	// // "classRoom", "teacherNo", "times"
	// int[] a = { 1, 2, 3 };
	// int[] b = { 33, 33, 33 };
	// int randomNum = new Random().nextInt(99);
	// int sum = 0;
	// int result = 0;
	// for (int i = 0; i < b.length; i++) {
	// sum += b[i];
	// if (randomNum <= sum) {
	// result = a[i];
	// break;
	// }
	// }
	// return result;
	// }

	@Test
	public void test1() {
		for (String s : initList) {
			System.out.println(s);
		}
		System.out.println("============");
		List<String> selectedList1 = new LinkedList<>();
		List<String> unSelectedList1 = new LinkedList<>();
		Map<String, Set<String>> teacherTime = new HashMap<>();
		Map<String, Set<String>> roomTime = new HashMap<>();
		Map<String, Set<String>> classTime = new HashMap<>();
		selectedList1.add(initList.get(0));
		for (int i = 1; i < initList.size(); i++) {
			// 获取待比较基因的各个片段
			String str1 = initList.get(i);
			String[] strs1 = str1.split("-");
			String class1 = strs1[0].split("_")[0];
			String teacher1 = strs1[0].split("_")[2];
			String subject1 = strs1[0].split("_")[1];
			String room1 = strs1[1];
			String[] times1 = strs1[2].split("_");
			// 循环完成排课的基因，获取到与待排序基因相关的（相同的班级，相同的老师，相同的教室）时间集合，此时间集合就是待排序基因不能重复出现的
			for (int j = 0; j < selectedList1.size(); j++) {
				// 获取已完成的基因的各个片段
				Set<String> roomtimeSet = new HashSet<>();
				Set<String> teachertimeSet = new HashSet<>();
				Set<String> classtimeSet = new HashSet<>();
				String str2 = selectedList1.get(j);
				String[] strs2 = str2.split("-");
				String class2 = strs2[0].split("_")[0];
				String teacher2 = strs2[0].split("_")[2];
				String room2 = strs2[1];
				String[] times2 = strs2[2].split("_");
				if (teacher1.equals(teacher2)) {
					for (String t : times2) {
						teachertimeSet.add(t);
					}
					if (teacherTime.get(teacher2) == null) {
						teacherTime.put(teacher2, teachertimeSet);
					} else {
						teacherTime.get(teacher2).addAll(teachertimeSet);
					}
				}
				if (room1.equals(room2)) {
					for (String t : times2) {
						roomtimeSet.add(t);
					}
					if (roomTime.get(room2) == null) {
						roomTime.put(room2, roomtimeSet);
					} else {
						roomTime.get(room2).addAll(roomtimeSet);
					}
				}
				if (class1.equals(class2)) {
					for (String t : times2) {
						classtimeSet.add(t);
					}
					if (classTime.get(class2) == null) {
						classTime.put(class2, classtimeSet);
					} else {
						classTime.get(class2).addAll(classtimeSet);
					}
				}
			}
			// 已被使用的时间集合获取完毕后，遍历待排序基因的时间，进行对比，如出现重复，从可用时间中随机分配一个，若无时间可分配则加入无法分配列表
			Set<String> teacherRoomClassTimes = new HashSet<>();
			if (teacherTime.get(teacher1) != null) {
				teacherRoomClassTimes.addAll(teacherTime.get(teacher1));
			}
			if (roomTime.get(room1) != null) {
				teacherRoomClassTimes.addAll(roomTime.get(room1));
			}
			if (classTime.get(class1) != null) {
				teacherRoomClassTimes.addAll(classTime.get(class1));
			}
			StringBuffer sb = new StringBuffer();
			boolean flag = true;
			boolean flag1 = true;
			for (String t : times1) {
				while (true) {
					if (teacherRoomClassTimes.contains(t)) {
						List<String> temptimes0 = new ArrayList<>();
						List<String> temptimes1 = new ArrayList<>();
						for (String tam : times_am) {
							if (!teacherRoomClassTimes.contains(tam)) {
								temptimes0.add(tam);
							}
						}
						for (String tpm : times_pm) {
							if (!teacherRoomClassTimes.contains(tpm)) {
								temptimes0.add(tpm);
							}
						}
						// 上下午时间都用完
						if (temptimes0.size() == 0 && temptimes1.size() == 0) {
							// 主修直接放入无法排课列表
							if (subject1.equals("chinese") || subject1.equals("math")) {
								flag = false;
								// System.out.println("主修无法安排时间");
							} else {
								// 选修修改教室从新放入待排课列表，如果教室也用完了，放入无法排课列表
								Set<String> temprooms = new HashSet<>();
								for (String room : classRoom) {
									if (!roomTime.keySet().contains(room)) {
										temprooms.add(room);
									} else {
										if (roomTime.get(room).size() <= (40 - times1.length)) {
											temprooms.add(room);
										}
									}
								}
								if (temprooms.size() <= 0) {
									flag = false;
									// System.out.println("选修无法安排时间");
								} else {
									int ran = new Random().nextInt(temprooms.size());
									int abc = 0;
									String newRoom = "";
									for (String room : temprooms) {
										if (abc == ran) {
											newRoom = room;
											break;
										}
										abc++;
									}
									String newStr = strs1[0] + "-" + newRoom + "-" + strs1[2];
									flag1 = false;
									selectedList1.add(newStr);
									// System.out.println("选修修改教室");
								}
							}
							break;
						}
						t = randomTime(str1, temptimes0, temptimes1);
					} else {
						sb.append(t).append("_");
						teacherRoomClassTimes.add(t);
						break;
					}
				}
			}
			if (flag) {
				if (flag1) {
					String newStr = strs1[0] + "-" + strs1[1] + "-"
							+ sb.delete(sb.length() - 1, sb.length()).toString();
					selectedList1.add(newStr);
				}
				// 获取适应度，根据结果再次随机时间
				// while (true) {
				// if (1 == 1) {
				// break;
				// }
				// int[] indexs = new int[] { 5, 8, 10 };
				// times1 = newStr.split("-")[2].split("_");
				// for (int in : indexs) {
				// teacherroomTimes.remove(times1[in]);
				// }
				//
				// for (int in : indexs) {
				// while (true) {
				// // 随机时间
				// String temptime = "21";
				// if (!teacherroomTimes.contains(temptime)) {
				// {
				// times1[in] = temptime;
				// teacherroomTimes.add(temptime);
				// break;
				// }
				// }
				// }
				// }
				// }
			} else {
				unSelectedList1.add(str1);
			}
		}
		for (String s : selectedList1) {
			System.out.println(s);
		}
		System.out.println("未分配" + unSelectedList1);
	}

}
