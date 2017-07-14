package com.wuchen.Thread.callable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Test {

	public static void main(String[] args) {
		Long start = System.currentTimeMillis();
		add2();
		Long end = System.currentTimeMillis();
		System.out.println((end - start) / 1000);
	}

	// public static void add1() {
	// PayAmount a = new PayAmount();
	// BigDecimal a1 = a.getAmount();
	// ReceiveAmount b = new ReceiveAmount();
	// BigDecimal b1 = b.getAmount();
	// System.out.println(a1.add(b1));
	// }

	public static void add2() {
		ExecutorService service = Executors.newCachedThreadPool();
		FutureTask<Map<String, Object>> f1 = new FutureTask<Map<String, Object>>(
				new FinancingAmount());
		FutureTask<Map<String, Object>> f2 = new FutureTask<Map<String, Object>>(
				new PayAmount());
		FutureTask<Map<String, Object>> f3 = new FutureTask<Map<String, Object>>(
				new ReceiveAmount());
		FutureTask<Map<String, Object>> f4 = new FutureTask<Map<String, Object>>(
				new ReuqestAmount());
		service.submit(f1);
		service.submit(f2);
		service.submit(f3);
		service.submit(f4);
		try {
			count(f1.get(), f2.get(), f3.get(), f4.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void count(Map<String, Object> ownMap,
			Map<String, Object> requestMap, Map<String, Object> financingMap,
			Map<String, Object> payMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.putAll(ownMap);
		map.putAll(requestMap);
		map.putAll(financingMap);
		map.putAll(payMap);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			System.out.println("key:" + entry.getKey() + "value："
					+ entry.getValue());
		}
	}
}
