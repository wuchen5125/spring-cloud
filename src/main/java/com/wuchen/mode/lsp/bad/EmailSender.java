package com.wuchen.mode.lsp.bad;

import com.wuchen.mode.lsp.bad.po.CommonCustomer;
import com.wuchen.mode.lsp.bad.po.VipCustomer;


public class EmailSender {

	public void send(CommonCustomer c){
		System.out.println("send to:"+c.getName()+"; address:"+c.getEmail());
	}
	
	public void send(VipCustomer c){
		System.out.println("send to:"+c.getName()+"; address:"+c.getEmail());
	}
}
