package com.chmei.nzbcommon.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RandomUtil {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	/**
	 * 
	 * @param length :产生的随机数的长度。
	 * @return 返回随机数的字符串
	 */
	public static String genRandomNumber(Integer length){
		String number;
		if (length>=1&&length<=19) {
			long i =(long) (Math.pow(10,length-1)+Math.random()*Math.pow(10,length));
			number=Long.toString(i);
			if(number.length()>length){
				return number.substring(0, number.length()-1);
			}
		}else{
			return "请输入范围1~19的数字";
		}
		return number;
	}
	
	/**
	 * 
	 * @param length :产生的随机时间字符串。
	 * @return 返回随机数的messageId
	 */
	
	public static String genMessageId(){
		String dateTime = sdf.format(new Date());
		Integer randNum = (new Double(Math.random()*10000)).intValue();
		return dateTime+randNum;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s =genRandomNumber(5);
		System.out.println(s);
		
		for(int i=0;i<100;i++){
			System.out.println(genRandomNumber(7));
		}
	}

}
