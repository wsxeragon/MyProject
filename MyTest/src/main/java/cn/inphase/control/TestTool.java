package cn.inphase.control;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class TestTool {
	static List<String> initlist = new LinkedList<>();
	// 初始化
	{
		for (int j = 0; j < 1000; j++) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < 15; i++) {
				float ran = new Random().nextFloat();
				if (ran > 0.5) {
					sb.append("1");
				} else {
					sb.append("0");
				}
			}
			initlist.add(sb.toString());
		}
	}

	// 淘汰劣势的，小于平均值的40%全部剔除
	public List<String> eliminate(List<String> strs) {
		double ave = getAverage(strs);
		for (int i = 0; i < strs.size(); i++) {
			if (Long.valueOf(strs.get(i), 2) < ave * 0.5) {
				strs.remove(i);
				i--;
			}
		}
		return strs;
	}

	// 交配产生后代
	// 本质就是基因交换，每个个体与任意一个个体交换基因的概率为4/n
	public List<String> mate(List<String> strs) {
		for (int i = 0; i < strs.size() / 2; i++) {
			for (int j = strs.size() / 2; j < strs.size(); j++) {
				int ran = new Random().nextInt((int) (strs.size() / 4));
				if (ran == 1) {
					char[] male = strs.get(i).toCharArray();
					char[] female = strs.get(j).toCharArray();
					// 记录变异位置
					List<Integer> location = new ArrayList<>();
					for (int k = 0; k < 15; k++) {
						// 每个位置的基因交换的概率为0.3
						float ran1 = new Random().nextFloat();
						if (ran1 <= 0.3) {
							location.add(k);
						}
					}
					for (int l : location) {
						char temp = male[l];
						male[l] = female[l];
						female[l] = temp;
					}
					StringBuffer male1 = new StringBuffer();
					StringBuffer female1 = new StringBuffer();
					for (int l = 0; l < 15; l++) {
						male1.append("" + male[l]);
						female1.append("" + female[l]);
					}
					strs.set(i, male1.toString());
					strs.set(j, female1.toString());
				}
			}
		}
		return strs;
	}

	// 变异
	// 每个个体变异概率为0.03，每个基因变异概率为1/15
	public List<String> variation(List<String> strs) {
		for (int i = 0; i < strs.size(); i++) {
			if (new Random().nextDouble() < 0.03) {
				char[] str = strs.get(i).toCharArray();
				for (int j = 0; j < str.length; j++) {
					if (new Random().nextInt(15) == 1) {
						str[j] = (char) (str[j] == '1' ? '0' : '1');
					}
				}
				StringBuffer sb = new StringBuffer();
				for (int l = 0; l < str.length; l++) {
					sb.append("" + str[l]);
				}
				strs.set(i, sb.toString());
			}
		}
		return strs;
	}

	@Test
	public void test() {
		System.out.println(initlist.size());
		System.out.println(getAverage(initlist));
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 交配
			list = mate(initlist);
			System.out.println("交配");
			System.out.println(list.size());
			System.out.println(getAverage(list));
			// 变异
			list = variation(list);
			System.out.println("变异");
			System.out.println(list.size());
			System.out.println(getAverage(list));
			// 淘汰
			list = eliminate(list);
			System.out.println("淘汰");
			System.out.println(list.size());
			System.out.println(getAverage(list));

		}

	}

	public double getAverage(List<String> list) {
		long count = 0;
		for (String s : list) {
			long temp = Long.valueOf(s, 2);
			count += temp;
		}
		double ave = count / list.size();
		return ave;
	}
}
