package com.wuchen.Thread.synchronizedDemo;

public class SaleTest {

	public static void main(String[] args) {
		TicketService service = new TicketService("广州--武汉", 100);
		Sale sale = new Sale("售票程序", service);
		Thread thread[] = new Thread[8];
		for (int i = 0; i < thread.length; i++) {
			thread[i] = new Thread(sale, "窗口" + (i + 1));
			System.out.println("窗口" + (i + 1) + "开始出售 "
					+ service.getTicketName() + " 的票...");
			thread[i].start();
		}
	}
}
