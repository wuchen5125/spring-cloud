package com.wuchen.Thread.synchronizedDemo;

public class Sale implements Runnable {

	private String name;

	private TicketService ticketService;

	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	public Sale(String name, TicketService ticketService) {
		this.name = name;
		this.ticketService = ticketService;
	}

	@Override
	public void run() {
		while (ticketService.getRemaining() > 0) {
			synchronized (this) {
				System.out.println(Thread.currentThread().getName() + "出售第"
						+ ticketService.getRemaining() + "张票");
				int remaining = ticketService.saleTiket(1);
				if (remaining >= 0) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("出票成功！剩余:" + remaining + "张票");
				} else {
					System.out.println("出票失败");
				}
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
