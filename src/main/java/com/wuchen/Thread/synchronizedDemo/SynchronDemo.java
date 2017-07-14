package com.wuchen.Thread.synchronizedDemo;

public class SynchronDemo implements Runnable {

	private synchronized static void count() {
		for (int i = 0; i < 5; i++) {
			System.out.println(Thread.currentThread().getName() + ",当前："
					+ (i + 1));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		count();
	}

	public static void main(String[] args) {
		SynchronDemo sync1 = new SynchronDemo();
		SynchronDemo sync2 = new SynchronDemo();
		Thread a = new Thread(sync1, "A");
		Thread b = new Thread(sync2, "B");
		a.start();
		b.start();
	}
}
