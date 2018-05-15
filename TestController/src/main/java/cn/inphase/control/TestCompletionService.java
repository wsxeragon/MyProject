package cn.inphase.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TestCompletionService {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// test1();
		// test2();
		// test3();
		test4();
	}

	public static void test1() throws InterruptedException, ExecutionException {

		myCallable myCallable1 = new myCallable(1000L);
		myCallable myCallable2 = new myCallable(1000L);

		FutureTask<String> futureTask1 = new FutureTask<>(myCallable1);
		FutureTask<String> futureTask2 = new FutureTask<>(myCallable2);

		ExecutorService executorService = Executors.newFixedThreadPool(2);

		executorService.execute(futureTask1);
		executorService.execute(futureTask2);

		while (true) {
			if (futureTask1.isDone() && futureTask2.isDone()) {
				System.out.println("ALL DONE");
				executorService.shutdown();
				return;
			}

			if (futureTask2.isDone()) {
				System.out.println("task2" + futureTask2.get());
			} else {
				System.err.println("NOT DONE task2   ");
			}

			if (futureTask1.isDone()) {
				System.out.println("Done task1   " + futureTask1.get());
			} else {
				System.err.println("NOT DONE task1   ");
			}

		}
	}

	public static void test2() throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		List<Future<String>> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Future<String> future = executorService.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					System.out.println(Thread.currentThread().getName() + "开始干活");
					Thread.sleep(1000);
					return Thread.currentThread().getName();
				}
			});
			list.add(future);
		}

		for (Future<String> future : list) {

			try {
				while (!future.isDone()) {
					// System.out.println("not done");
				}
				System.out.println(future.get());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				executorService.shutdown();
			}

		}
	}

	public static void test3() {
		ExecutorService executorService = Executors.newCachedThreadPool();

		CompletionService<String> completionService = new ExecutorCompletionService<>(executorService);

		for (int i = 0; i < 10; i++) {
			completionService.submit(new myCallable(1000));
		}

		for (int i = 0; i < 10; i++) {
			try {
				System.out.println(completionService.take().get());
			} catch (Exception e) {
				System.err.println(e);
			} finally {
				executorService.shutdown();
			}

		}
	}

	public static void test4() {
		ExecutorService executorService = Executors.newCachedThreadPool();

		BlockingQueue<String> queue = new LinkedBlockingQueue<>();

		for (int i = 0; i < 5; i++) {
			executorService.submit(new producer(queue));
		}

		executorService.submit(new comsumer(queue));

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executorService.shutdown();
	}

}

class myCallable implements Callable<String> {

	private long waitTime;

	public myCallable(long waitTime) {
		this.waitTime = waitTime;
	}

	@Override
	public String call() throws Exception {
		Thread.sleep(waitTime);
		return Thread.currentThread().getName();
	}

}

class producer implements Runnable {

	BlockingQueue<String> queue;

	public producer(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	Random r = new Random(100);

	@Override
	public void run() {
		int a = r.nextInt(100);
		System.out.println(Thread.currentThread().getName() + "开始生产" + a);
		System.out.println(Thread.currentThread().getName() + "往队列放入" + a);
		Boolean flag;
		try {
			flag = queue.offer("" + a, 2, TimeUnit.SECONDS);
			if (!flag) {
				System.err.println(Thread.currentThread().getName() + "放入" + a + "失败");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

class comsumer implements Runnable {

	BlockingQueue<String> queue;

	public comsumer(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + "开始获取商品");
		String a;
		try {
			while (true) {
				Thread.sleep(1000);
				a = queue.poll(2, TimeUnit.SECONDS);
				if (a != null) {
					System.out.println("拿到" + a);
				} else {
					System.err.println("获取失败");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}