package com.wuchen.Thread.synchronizedDemo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DateFormatTest {

	// DateFormat 非线程同步,并发场景下会出现异常
	private final static DateFormat dateformatter = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10000; i++) {
			service.execute(new Runnable() {
				@Override
				public void run() {
					// 直接new new SimpleDateFormat 可以解决并发出现的问题
					toDate();
				}
			});
		}
	}

	private synchronized static void toDate() {
		try {
			System.out.println(Thread.currentThread().getName() + ";"
					+ dateformatter.parse("2017-05-01 12:00:00"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
