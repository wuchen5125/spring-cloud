package com.wuchen.Thread.callable;

import java.util.Map;
import java.util.concurrent.Callable;

public abstract class Amount implements Callable<Map<String, Object>> {

	public abstract Map<String, Object> getAmount();
}
