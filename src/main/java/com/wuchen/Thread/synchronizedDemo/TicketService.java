package com.wuchen.Thread.synchronizedDemo;

public class TicketService {

	/**
	 * 票名
	 */
	private String ticketName;

	/*
	 * 总票数
	 */
	private Integer total;

	/**
	 * 剩余票数
	 */
	private Integer remaining;

	public TicketService(String ticketName, Integer total) {
		this.ticketName = ticketName;
		this.total = total;
		this.remaining = total;
	}

	public synchronized  Integer saleTiket(Integer ticktNum) {
		if (remaining > 0) {
			remaining -= ticktNum;
			if (remaining >= 0) {
				return remaining;
			} else {
				remaining += ticktNum;
				return -1;
			}
		}
		return -1;
	}

	public String getTicketName() {
		return ticketName;
	}

	public synchronized  Integer getRemaining() {
		return remaining;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}

	public void setRemaining(Integer remaining) {
		this.remaining = remaining;
	}
	
}
