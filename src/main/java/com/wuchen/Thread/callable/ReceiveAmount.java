package com.wuchen.Thread.callable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ReceiveAmount extends Amount {

	@Override
	public Map<String, Object> getAmount() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("3000", new BigDecimal(3000));
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> call() throws Exception {
		return getAmount();
	}

}
