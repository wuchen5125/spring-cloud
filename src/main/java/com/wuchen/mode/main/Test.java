package com.wuchen.mode.main;

import com.wuchen.mode.lsp.bad.EmailSender;
import com.wuchen.mode.lsp.bad.po.CommonCustomer;
import com.wuchen.mode.lsp.bad.po.VipCustomer;
import com.wuchen.mode.lsp.good.po.Customer;


public class Test {

	public static void main(String[] args) {
		EmailSender sender = new EmailSender();
		sender.send(new CommonCustomer("wuchen", "wuchen@china.zhaogang.com"));
		sender.send(new VipCustomer("VIP", "wuchenVIP@china.zhaogang.com"));
		
		com.wuchen.mode.lsp.good.EmailSender newSender = new com.wuchen.mode.lsp.good.EmailSender();
		Customer c = 
				new com.wuchen.mode.lsp.good.po.VipCustomer("VIP", "wuchenVIP@china.zhaogang.com");
		newSender.send(c);
	}
}
