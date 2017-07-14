package com.nc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class PayServiceTest {
	static final int TASK_COUNT = 1;

	public static void main(String[] args) throws IOException,
			InterruptedException {
		CustomerizedThreadPool pool = new CustomerizedThreadPool(
				"submitPaymentsPool", 3, 3, 5000, 1000000);

		final StringBuffer template = getTemplate();

		List<RunLog> records = new ArrayList<RunLog>();
		CountDownLatch cl = new CountDownLatch(TASK_COUNT);

		//final String testPushUrl = "http://10.0.6.15:80/service/XChangeServlet?account=001&receiver=01";
		final String testPushUrl = "http://zhikuan.zhaogangrentest.com/service/XChangeServlet?account=001&receiver=01";
		//final String uatPushUrl = "http://zhikuan.zhaogangrenuat.com/service/XChangeServlet?account=001&receiver=01";
		System.out.println(new Date() + "开始处理" + TASK_COUNT + "条到款通知单开始...");

		for (int i = 0; i < TASK_COUNT; i++) {
			int currentIndex = 30000 + i;
			final RunLog rec = new RunLog();
			final String paymentNoPrefix = "6122-20170706" + currentIndex;
			final String zhaiyao = "摘要" + currentIndex;
			rec.setPaymentNo(paymentNoPrefix);
			rec.setSubmitTime(Calendar.getInstance().getTimeInMillis());
			pool.submitTask(new Runnable() {
				public void run() {
					rec.setStartTime(Calendar.getInstance().getTimeInMillis());
					NCHttpClientUtil.sendNCxmlData(
							testPushUrl,
							template.toString()
									.replaceAll("6121-2017039788968",
											paymentNoPrefix)
									.replaceAll("摘要", zhaiyao));
					rec.setFinishTime(Calendar.getInstance().getTimeInMillis());
				}
			}, cl);

			records.add(rec);
		}

		cl.await();
		System.out.println(new Date() + "完成处理!");

		for (RunLog log : records) {
			System.out.println(log);
		}
	}

	private static StringBuffer getTemplate() throws FileNotFoundException,
			IOException {
		FileReader fr = new FileReader(new File(
				"D:\\uat-success.xml"));
		BufferedReader br = new BufferedReader(fr);
		final StringBuffer template = new StringBuffer();

		String s;
		while ((s = br.readLine()) != null) {
			template.append(s);
		}
		fr.close();
		return template;
	}

	public static class RunLog {
		private String paymentNo;
		private Long submitTime;
		private Long startTime;
		private Long finishTime;

		public String getPaymentNo() {
			return paymentNo;
		}

		public void setPaymentNo(String paymentNo) {
			this.paymentNo = paymentNo;
		}

		public Long getSubmitTime() {
			return submitTime;
		}

		public void setSubmitTime(Long submitTime) {
			this.submitTime = submitTime;
		}

		public Long getStartTime() {
			return startTime;
		}

		public void setStartTime(Long startTime) {
			this.startTime = startTime;
		}

		public Long getFinishTime() {
			return finishTime;
		}

		public void setFinishTime(Long finishTime) {
			this.finishTime = finishTime;
		}

		@Override
		public String toString() {
			return "paymentNo=" + paymentNo 
					+ "总体时间=" + ((finishTime - submitTime)) + "ms" 
					+ "接口响应时间=" + ((finishTime - startTime)) + "ms";
		}
	}
}
