package cn.inphase.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class TestForkJoin {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// MyForkJoin myForkJoin = new MyForkJoin(1, 10);
		// Long result = new ForkJoinPool().invoke(myForkJoin);
		// System.out.println(result);

		String[] strArray = new String[] { "a", "b", "c", "d", "e", "f", "g" };
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			// try {
			// Thread.sleep(500);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			list.add(strArray[new Random().nextInt(strArray.length)]);
		}

		MyForkJoinString myForkJoinString = new MyForkJoinString(list);
		// try {
		// // invoke时抛异常
		// String result = new ForkJoinPool().invoke(myForkJoinString);
		// System.out.println(result);
		// } catch (Exception e) {
		// }

		new ForkJoinPool().submit(myForkJoinString);
		try {
			// get时抛异常
			System.out.println(myForkJoinString.get());
		} catch (Exception e) {
		}

		if (myForkJoinString.isCompletedAbnormally()) {
			System.out.println("异常");
		}
	}
}

class MyForkJoin extends RecursiveTask<Long> {

	int start;
	int end;

	private static final int max = 5;

	public MyForkJoin(int s, int e) {
		System.out.println("一个对象 start:" + s + " end:" + e);
		this.start = s;
		this.end = e;
	}

	@Override
	protected Long compute() {
		Long sum = 0L;
		if ((end - start) <= max) {
			for (int i = start; i <= end; i++) {
				sum += i;
			}
		} else {
			int middle = (end + start) / 2;
			MyForkJoin myForkJoin1 = new MyForkJoin(start, middle);
			MyForkJoin myForkJoin2 = new MyForkJoin(middle + 1, end);

			// 异步执行任务，但是这种方法会浪费线程
			// 类似于把自己的任务交给另外两个人，自己却不干事了，每次拆分任务都会浪费一个线程
			// 当然也可以一次性把任务全分完，只会浪费一个线程
			myForkJoin1.fork();
			myForkJoin2.fork();

			sum = myForkJoin1.join() + myForkJoin2.join();
		}

		return sum;
	}

}

// 合并字符串数组
class MyForkJoinString extends RecursiveTask<String> {
	int start;
	int end;
	private static final int max = 10;
	private List<String> list = null;

	public MyForkJoinString(List<String> l) {
		this.start = 0;
		this.end = l.size();
		this.list = l;
	}

	@Override
	protected String compute() {
		StringBuffer sb = new StringBuffer();
		if (new Random().nextInt(10) % 2 == 0) {
			// throw new RuntimeException("test exception");
		}
		if ((end - start) <= max) {
			for (int i = start; i < end; i++) {
				sb = sb.append(list.get(i)).append("=");
			}
		} else {
			int middle = (end + start) / 2;
			List<String> list1 = list.subList(0, middle);
			List<String> list2 = list.subList(middle, end - 1);
			MyForkJoinString myForkJoinString1 = new MyForkJoinString(list1);
			MyForkJoinString myForkJoinString2 = new MyForkJoinString(list2);

			// invokeAll()方法来执行一个主任务所创建的多个子任务。
			// 这是一个同步调用，这个任务将等待子任务完成，然后继续执行（也可能是结束）。
			// 当一个主任务等待它的子任务时，执行这个主任务的工作者线程接收另一个等待执行的任务并开始执行(任务窃取)
			invokeAll(myForkJoinString1, myForkJoinString2);
			try {
				int i = 1 / 0;
			} catch (Exception e) {
			}
			sb.append(myForkJoinString1.join()).append(myForkJoinString2.join());
		}
		return sb.toString();
	}

}
