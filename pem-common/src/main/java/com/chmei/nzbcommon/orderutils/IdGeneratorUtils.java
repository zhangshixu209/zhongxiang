package com.chmei.nzbcommon.orderutils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 根据时间戳生产
 * 
 * @author zcf
 *
 */
public class IdGeneratorUtils {

	private final static long workerIdBits = 4L;
	public final static long maxWorkerId = -1L ^ -1L << workerIdBits;
	private final static long sequenceBits = 10L;
	private final static long timestampLeftShift = sequenceBits + workerIdBits;

	// 原子性操作递增id---保证高并发不重复
	private static AtomicInteger count = new AtomicInteger(0);

	/**
	 * 
	 * @return
	 */
	private static int increment() {
		int andIncrement = count.getAndIncrement();
		return andIncrement;
	}

	/**
	 * 获取递增序列号
	 * 
	 * @return
	 */
	private static synchronized long nextId() {
		long timestamp = (System.currentTimeMillis() + 1) / 1000;
		long nextId = (timestamp << timestampLeftShift) | increment();
		return nextId;
	}

	/**
	 * 根据自定义获取序列号
	 * @param str
	 * @return
	 */
	public static String nextId(String str) {
		String orderId = str + nextId();
		return orderId;
	}
	
	
	/**
	 * 生成时间戳
	 * @return
	 */
	public static synchronized String  getTimeStamp(){
		//格式化当前时间
		SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String strDate = sfDate.format(new Date());
		//得到17位时间如：20170411094039080
		return strDate;	
	}
	
	/*public static void main(String[] args) {
		System.err.println(nextId("GD"));
	}*/
	
}

