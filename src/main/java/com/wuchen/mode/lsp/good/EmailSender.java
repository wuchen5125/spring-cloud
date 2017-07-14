package com.wuchen.mode.lsp.good;

import com.wuchen.mode.lsp.good.po.Customer;

public class EmailSender {

	public void send(Customer c){
		System.out.println("send to:"+c.getName()+"; address:"+c.getEmail());
	}
}
