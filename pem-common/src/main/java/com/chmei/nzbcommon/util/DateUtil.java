package com.chmei.nzbcommon.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * 
 */
public final class DateUtil {
	public static final int SEC_UNIT=60;
	public static final int SEC_PER_MIN=SEC_UNIT;
	public static final int SEC_PER_HOUR=SEC_UNIT*SEC_PER_MIN;
	public static final int SEC_PER_DAY=24*SEC_PER_HOUR;
	public static final int MILLI_PER_SEC=1000;
	public enum TimeUnit {
		MILLI,SEC,MIN,HOUR,DAY
	}
	/** Private Constructor **/
	private DateUtil() {
	}

	/** 日期格式 **/
	public interface DATE_PATTERN {
		String HHMMSS = "HHmmss";
		String HH_MM_SS = "HH:mm:ss";
		String HH_MM = "HH:mm";
		String YYYYMM = "yyyyMM";
		String YYYYMMDD = "yyyyMMdd";
		String YYYY_MM_DD = "yyyy-MM-dd";
		String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
		String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
		String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	}
	/**
	 * 将Date类型转换成String类型
	 * 
	 * @param date
	 *            Date对象
	 * @return 形如:"yyyy-MM-dd HH:mm:ss"
	 */
	public static String date2String(Date date) {
		return date2String(date, DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 将Date按格式转化成String
	 * 
	 * @param date
	 *            Date对象
	 * @param pattern
	 *            日期类型
	 * @return String
	 */
	public static String date2String(Date date, String pattern) {
		if (date == null || pattern == null){
			return null;
		}
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 将String类型转换成Date类型
	 * 
	 * @param date
	 *            Date对象
	 * @return
	 */
	public static Date string2Date(String date) {
		SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	public static Date string2Date(String date, String pattern)
	{
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	/***
	 * 获取时间差额
	 * @param startDate
	 * @param endDate
	 * @param timeUnit 默认millisecond
	 * @return
	 */
	public static Long diffTimeUnit(Date startDate,Date endDate,TimeUnit timeUnit){
		Long diffTime=endDate.getTime()-startDate.getTime();
		
		switch (timeUnit) {
		case MILLI:{
			return diffTime;
		}
		case SEC:{
			return diffTime/DateUtil.MILLI_PER_SEC;
		}
		case MIN:{
			return diffTime/DateUtil.MILLI_PER_SEC/DateUtil.SEC_PER_MIN;
		}
		case HOUR:{
			return diffTime/DateUtil.MILLI_PER_SEC/DateUtil.SEC_PER_HOUR;
		}
		case DAY:{
			return diffTime/DateUtil.MILLI_PER_SEC/DateUtil.SEC_PER_DAY;
		}
		default:
			return diffTime;
		}
	}
	/**
	 * 获得当前系统时间
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurDate(){
		return date2String(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 时间比较大小
	 */
	public static int compareDate(Date param1,Date param2){
		if(param1.getTime() > param2.getTime()){
			return 1;
		}
		if(param1.getTime() < param2.getTime()){
			return -1;
		}
		return 0;
	}
	
	/**
	 * 时间比较大小
	 */
	public static boolean dateCompare(String DATE1, String DATE2){
		boolean flag = false;
        Date dt1 = string2Date(DATE1, DATE_PATTERN.YYYYMMDD);
        Date dt2 = string2Date(DATE2, DATE_PATTERN.YYYYMMDD);
		if(dt1.getTime() > dt2.getTime()){
			return false;
		}
		if(dt1.getTime() <= dt2.getTime()){
			return true;
		}
		return flag;
	}
	
	/**
	 * 判断某一时间是否在一个区间内
	 * 
	 * @param sourceTime
	 *            时间区间,半闭合,如[10:00-20:00)
	 * @param curTime
	 *            需要判断的时间 如10:00
	 * @return 
	 * @throws IllegalArgumentException
	 */
	public static boolean isInTime(String sourceTime, String curTime) {
	    if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {
	        throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
	    }
	    if (curTime == null || !curTime.contains(":")) {
	        throw new IllegalArgumentException("Illegal Argument arg:" + curTime);
	    }
	    String[] args = sourceTime.split("-");
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	    try {
	        long now = sdf.parse(curTime).getTime();
	        long start = sdf.parse(args[0]).getTime();
	        long end = sdf.parse(args[1]).getTime();
	        if (args[1].equals("00:00")) {
	            args[1] = "24:00";
	        }
	        if (end < start) {
	            if (now >= end && now < start) {
	                return false;
	            } else {
	                return true;
	            }
	        } 
	        else {
	        	if (now >= start && now < end) {
	                return true;
	            } else {
	                return false;
	            }
	        }
	    } catch (ParseException e) {
	        e.printStackTrace();
	        throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
	    }

	}
	
	public static String secToTime(int time) {  
        String timeStr = null;  
        int hour = 0;  
        int minute = 0;  
        if (time <= 0)  
            return "00:00";  
        else {  
                hour = time / 60;  
                if (hour > 99)  
                    return "99:59:59";  
                minute = time - hour * 60;  
                timeStr = unitFormat(hour) + ":" + unitFormat(minute);  
            }  
        return timeStr;  
    }  
  
    public static String unitFormat(int i) {  
        String retStr = null;  
        if (i >= 0 && i < 10)  
            retStr = "0" + Integer.toString(i);  
        else  
            retStr = "" + i;  
        return retStr;  
    }  
    
    //获得上个月一号
    public static Date preMonthFirstDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }
    
	//获得下个月一号
    public static Date nextMonthFirstDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }
    
    //获得下个月一号零点
    public static Date nextMonthFirstDateZero() {
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.DAY_OF_MONTH, 1);
    	calendar.add(Calendar.MONTH, 1);
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
 		calendar.set(Calendar.MINUTE, 0);
 		calendar.set(Calendar.SECOND, 0);
 		calendar.set(Calendar. MILLISECOND, 0);
    	return calendar.getTime();
    }
    
    //获得当月一号零点
    public static Date currMonthFirstDateZero() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar. MILLISECOND, 0);
        return calendar.getTime();
    }
    
    public static Date currMonthsOfDate(int day){
    	Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar. MILLISECOND, 0);
        return calendar.getTime();
    }
    
    /**
	 * 获得指定日期当天0点0分0秒
	 */
	public static Date getDayZeroByDate(Date date){
		
		if(date == null)
			return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar. MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	/**
	 * 获取相邻月份
	 */
	public static String getNextMonth(String pattern,int sub){
		SimpleDateFormat sd= new SimpleDateFormat(pattern);
        Date currdate = new Date();
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(currdate);
        //calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)-sub);
        calendar.add(Calendar.MONTH, -sub);
	    return sd.format(calendar.getTime());
	}
	
	/**
	 * 月份计算
	 * @param pattern 返回的日期格式
	 * @param sub 加/减的月份数量
	 * @return 
	 * @user wangya
	 * @date 2019年3月30日
	 */
	public static String addMonth(String pattern,int sub){
        SimpleDateFormat sd= new SimpleDateFormat(pattern);
        Date currdate = new Date();
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(currdate);
        calendar.add(Calendar.MONTH, sub);
        Date m3 = calendar.getTime();
        return sd.format(calendar.getTime());
    }
	
	/**
	 * 获得指定日期下一天0点0分0秒
	 */
	public static Date getNextDayZeroByDate(Date date){
		
		if(date == null)
			return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar. MILLISECOND, 0);
		calendar.add(Calendar. DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	
	public static int getMonth(Date d1, Date d2) {

		int result ;

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(d1);
		c2.setTime(d2);

		int y = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		int m = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
		int d = c2.get(Calendar.DAY_OF_MONTH)+1 - c1.get(Calendar.DAY_OF_MONTH);
		result=y*12+m;
		if (d < 0) {
			result = result -1;
		}

		return result;
	}
	
	
}
