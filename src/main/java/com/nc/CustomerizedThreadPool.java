package com.nc;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程池
 * 
 * @author gang.yuan
 *
 */
public class CustomerizedThreadPool {
	private ThreadPoolExecutor executor;
	// 线程池最大值
	private int maxThreadPoolSize;
	// 线程队列，若任务数大于coreSize，任务先放入队列，队列满再加线程，直到最大值，再添加丢弃
	private int queueSize;
	// 线程池名称
	private String threadPoolName;
	// 缓冲任务尺
	private BlockingQueue<Runnable> queue;
	// 线程工厂方法
	ThreadFactory threadFactory;
	// 单位秒，任务执行超时时间（等待和执行都算），超时将会取消，但对于执行中的线程，取消执行不一定能中断
	private long timeOut;

	private long keepAliveTime = 60l;

	// 正在工作线程数
	private AtomicInteger workCount = new AtomicInteger(0);

	private ExecutorService executorService = Executors.newCachedThreadPool();

	/**
	 * 
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param threadPoolName
	 *            线程池名称
	 * @param coreThreadNum
	 *            池初始化值
	 * @param maxThreadPoolSize
	 *            池最大值
	 * @param queueSize
	 *            队列长度
	 * @param timeOut
	 *            任务过期时间（含队列中等待时间）
	 */
	public CustomerizedThreadPool(String threadPoolName, int coreThreadNum, int maxThreadPoolSize, int queueSize, int timeOut) {
		this.maxThreadPoolSize = maxThreadPoolSize;
		this.queueSize = queueSize;
		this.timeOut = timeOut;
		this.threadPoolName = threadPoolName;
		initThreadFactory();
		queue = new LinkedBlockingQueue<Runnable>(queueSize);

		executor = new ThreadPoolExecutor(coreThreadNum, maxThreadPoolSize, keepAliveTime,TimeUnit.SECONDS, queue, threadFactory,
				new ThreadPoolExecutor.DiscardOldestPolicy());
		Runtime.getRuntime().addShutdownHook(new Thread(getDistoryTask()));
	}

	private void initThreadFactory() {
		threadFactory = new ThreadFactory() {
			private int i = 1;
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r, threadPoolName + "-" + i);
				i++;
				t.setDaemon(true);
				return t;
			}
		};
	}

	public void submitTask(Runnable run,final CountDownLatch cl) {
		final java.util.concurrent.Future<?> future = executor.submit(run);
		workCount.getAndIncrement();
		executorService.submit(new Runnable() {
			public void run() {
				try {
					future.get(timeOut, TimeUnit.SECONDS);
				} catch (TimeoutException e) {
					future.cancel(true);
				} catch (ExecutionException e) {
					e.printStackTrace();
					System.out.println("线程池" + threadPoolName + "woker执行异常");
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.out.println("线程池" + threadPoolName + "woker执行异常");
				} finally {
					cl.countDown();
					System.out.println("当前还剩余" + cl.getCount() + "个待推送付款单！");
					workCount.decrementAndGet();
				}
			}
		});
	}

	public Boolean isFull() {
		return (workCount.get() >= (maxThreadPoolSize + queueSize));
	}
	
	public void destroy() {
		try {
			if (null != executor) {
				executor.shutdown();
				executor.awaitTermination(1L, TimeUnit.MINUTES);
				executorService.shutdown();
				executorService.awaitTermination(1L, TimeUnit.MINUTES);
			}
		} catch (Exception e) {
		}
	}

	private Runnable getDistoryTask() {
		return new Runnable() {
			public void run() {
				destroy();
				return;
			}

		};
	}
}
