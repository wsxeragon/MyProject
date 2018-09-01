package cn.inphase.control;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class TestThreadPool {

	// 核心线程数 cpu数*2
	private int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;

	// 总线程数 总线程-核心线程=外包线程
	// 优先核心线程处理，超出处理能力，塞到队列，队列塞满，外包出马，外包也承受不了，拒接接任务
	private int maximumPoolSize = 30;

	// 外包存活时间
	private long keepAliveTime = 6000;

	// 存放任务的队列
	// 如果是要求高吞吐量的，可以使用 SynchronousQueue 队列；不存储元素的阻塞队列 每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态
	// 如果对执行顺序有要求，可以使用 PriorityBlockingQueue；
	// 如果最大积攒的待做任务有上限，可以使用 LinkedBlockingQueue。
	private BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(128);

	// 线程创建工程，可以设置名字，优先级等
	private ThreadFactory threadFactory = new ThreadFactory() {

		@Override
		public Thread newThread(Runnable arg0) {
			Thread thread = new Thread(arg0, "test");
			thread.setPriority(Thread.NORM_PRIORITY);
			return thread;
		}
	};

	// 拒绝接收策略
	// CallerRunsPolicy：只要线程池没关闭，就直接用调用者所在线程来运行任务
	// AbortPolicy：直接抛出 RejectedExecutionException 异常
	// DiscardPolicy：悄悄把任务放生，不做了
	// DiscardOldestPolicy：把队列里待最久的那个任务扔了，然后再调用 execute() 试试看能行不
	// 也可以实现自己的 RejectedExecutionHandler 接口自定义策略，比如如记录日志什么的
	private RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardOldestPolicy();

	ThreadPoolExecutor myThreadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
			TimeUnit.MILLISECONDS, workQueue, threadFactory, handler);

	@Test
	public void test1() {
	}

}
