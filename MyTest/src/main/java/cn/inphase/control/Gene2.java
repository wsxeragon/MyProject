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

public class Gene2 {
	// 有特殊安排的时段 将该时段排除，单独安排
	public static String[] times_am = new String[] { "11", "12", "13", "14", "21", "22", "23", "24", "31", "32", "33",
			"34", "41", "42", "43", "44", "51", "52", "53", "54" };
	// 15 班会课， 28 自习，37和38 选修课
	public static String[] times_pm = new String[] { "16", "17", "18", "25", "26", "27", "35", "36", "45", "46", "47",
			"48", "55", "56", "57", "58" };

	public static List<String> allocationTime(List<String> initList) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < initList.size(); i++) {
			String str = initList.get(i);
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
			// 信息技术每周两节且连堂
			if (str.contains("techInfo")) {
				String timePart = "";
				while (true) {
					timePart = randomTime(str, Arrays.asList(times_am), Arrays.asList(times_pm));
					if (!timeSb.toString().contains(timePart)) {
						timeSb.append(timePart).append("_");
						break;
					}
				}
				if (Arrays.asList(times_am).contains(timePart)) {
					if (Arrays.asList(times_am).indexOf(timePart) == (times_am.length - 1)) {
						timeSb.append(times_am[Arrays.asList(times_am).indexOf(timePart) - 1]).append("_");
					} else {
						timeSb.append(times_am[Arrays.asList(times_am).indexOf(timePart) + 1]).append("_");
					}
				} else {
					if (Arrays.asList(times_pm).indexOf(timePart) == (times_pm.length - 1)) {
						timeSb.append(times_pm[Arrays.asList(times_pm).indexOf(timePart) - 1]).append("_");
					} else {
						timeSb.append(times_pm[Arrays.asList(times_pm).indexOf(timePart) + 1]).append("_");
					}
				}
			}
			timeSb.delete(timeSb.length() - 1, timeSb.length());
			// 拼装
			sb.append(str).append("-").append(timeSb);
			initList.add(sb.toString());
			sb.delete(0, sb.length());
		}
		return initList;
	}

	public static List<String> reduceConflict(List<String> list) {
		List<String> selectedList1 = new LinkedList<>();
		List<String> unSelectedList1 = new LinkedList<>();
		Map<String, Set<String>> teacherTime = new HashMap<>();
		Map<String, Set<String>> roomTime = new HashMap<>();
		Map<String, Set<String>> classTime = new HashMap<>();
		selectedList1.add(list.get(0));
		for (int i = 1; i < list.size(); i++) {
			// 获取待比较基因的各个片段
			String str1 = list.get(i);
			String[] strs1 = str1.split("-");
			// String class1 = strs1[0].split("_")[0];
			String teacher1 = strs1[0].split("_")[2];
			String subject1 = strs1[0].split("_")[1];
			String room1 = strs1[1];
			String[] times1 = strs1[2].split("_");
			// 循环完成排课的基因，获取到与待排序基因相关的（相同的班级，相同的老师，相同的教室）时间集合，此时间集合就是待排序基因不能重复出现的
			// 不满足特殊条件的时间集合
			Set<String> specialtimeSet = new HashSet<>();
			// 语文周二上午，周四下午不排课
			if (subject1.equals("chinese")) {
				specialtimeSet.add("25");
				specialtimeSet.add("26");
				specialtimeSet.add("27");
				specialtimeSet.add("28");
				specialtimeSet.add("41");
				specialtimeSet.add("42");
				specialtimeSet.add("43");
				specialtimeSet.add("44");
			}
			for (int j = 0; j < selectedList1.size(); j++) {
				// 获取已完成的基因的各个片段
				Set<String> roomtimeSet = new HashSet<>();
				Set<String> teachertimeSet = new HashSet<>();
				// Set<String> classtimeSet = new HashSet<>();
				String str2 = selectedList1.get(j);
				String[] strs2 = str2.split("-");
				// String class2 = strs2[0].split("_")[0];
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
				// if (class1.equals(class2)) {
				// for (String t : times2) {
				// classtimeSet.add(t);
				// }
				// if (classTime.get(class2) == null) {
				// classTime.put(class2, classtimeSet);
				// } else {
				// classTime.get(class2).addAll(classtimeSet);
				// }
				// }
			}
			// 已被使用的时间集合获取完毕后，遍历待排序基因的时间，进行对比，如出现重复，从可用时间中随机分配一个，若无时间可分配则加入无法分配列表
			Set<String> teacherRoomClassTimes = new HashSet<>();
			if (teacherTime.get(teacher1) != null) {
				teacherRoomClassTimes.addAll(teacherTime.get(teacher1));
			}
			if (roomTime.get(room1) != null) {
				teacherRoomClassTimes.addAll(roomTime.get(room1));
			}
			// if (classTime.get(class1) != null) {
			// teacherRoomClassTimes.addAll(classTime.get(class1));
			// }
			teacherRoomClassTimes.addAll(specialtimeSet);
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
							flag = false;
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
		if (unSelectedList1.size() == 0) {
			return selectedList1;
		} else {
			return unSelectedList1;
		}
	}

	// 主修课选上午的概率为70% 选修选下午的概率为70%
	public static String randomTime(String simple, List<String> temptimes0, List<String> temptimes1) {
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
}
