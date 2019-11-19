package com.chmei.nzbcommon.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.scheduling.support.CronSequenceGenerator;

/**
 * @author lixinjie
 * @since 2018-01-17
 */
public class DateUtils {
	
	private static final SimpleDateFormat sdf14 = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final SimpleDateFormat sdf16 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
	private static final SimpleDateFormat sdf19 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat sdf10 = new SimpleDateFormat("yyyy-MM-dd");
	
	public static String getDateStr10() {
		return getDateStr10(new Date());
	}
	public static String getDateStr10(Date date) {
		return sdf10.format(date);
	}
	public static String getDateStr14() {
		return getDateStr14(new Date());
	}
	
	public static String getDateStr14(Date date) {
		return sdf14.format(date);
	}
	
	public static String getDateStr16() {
		return getDateStr16(new Date());
	}
	
	public static String getDateStr16(Date date) {
		return sdf16.format(date);
	}
	
	public static String getDateStr19() {
		return getDateStr19(new Date());
	}
	
	public static String getDateStr19(Date date) {
		return sdf19.format(date);
	}
	
	public static boolean greatThan(Date time) {
		return greatThan(time, new Date());
	}
	
	public static boolean greatThan(Date time1, Date time2) {
		return time1.after(time2);
	}
	
	public static boolean lessThan(Date time) {
		return lessThan(time, new Date());
	}
	
	public static boolean lessThan(Date time1, Date time2) {
		return time1.before(time2);
	}

	public static Date nextCronDate(Date baseTime, String cronExpression) {
		return new CronSequenceGenerator(cronExpression).next(baseTime);
	}
	
	public static Date addInterval(long interval, IntervalUnit unit) {
		return addInterval(new Date(), interval, unit);
	}
	
	public static Date addInterval(Date baseTime, long interval, IntervalUnit unit) {
		Calendar c = Calendar.getInstance();
		c.setTime(baseTime);
		switch (unit) {
			case Second: c.set(Calendar.SECOND, (int)(c.get(Calendar.SECOND) + interval)); return c.getTime();
			case Minute: c.set(Calendar.MINUTE, (int)(c.get(Calendar.MINUTE) + interval)); return c.getTime();
			case Hour: c.set(Calendar.HOUR_OF_DAY, (int)(c.get(Calendar.HOUR_OF_DAY) + interval)); return c.getTime();
			case Day: c.set(Calendar.DAY_OF_MONTH, (int)(c.get(Calendar.DAY_OF_MONTH) + interval)); return c.getTime();
			case Week: c.set(Calendar.WEEK_OF_YEAR, (int)(c.get(Calendar.WEEK_OF_YEAR) + interval)); return c.getTime();
			case Month: c.set(Calendar.MONTH, (int)(c.get(Calendar.MONTH) + interval)); return c.getTime();
			case Quarter: c.set(Calendar.MONTH, (int)(c.get(Calendar.MONTH) + interval * 3)); return c.getTime();
			case Year: c.set(Calendar.YEAR, (int)(c.get(Calendar.YEAR) + interval)); return c.getTime();
			default : return c.getTime();
		}
	}
	
	public static Date subInterval(long interval, IntervalUnit unit) {
		return subInterval(new Date(), interval, unit);
	}
	
	public static Date subInterval(Date baseTime, long interval, IntervalUnit unit) {
		return addInterval(baseTime, -interval, unit);
	}
	
	public static long dateDiff(Date time, IntervalUnit unit) {
		return dateDiff(new Date(), time, unit);
	}
	
	public static long dateDiff(Date baseTime, Date time, IntervalUnit unit) {
		switch (unit) {
			case Second: return dateDiffBySecond(baseTime, time);
			case Minute: return dateDiffByMinute(baseTime, time);
			case Hour: return dateDiffByHour(baseTime, time);
			case Day: return dateDiffByDay(baseTime, time);
			case Week: return dateDiffByWeek(baseTime, time);
			case Month: return dateDiffByMonth(baseTime, time);
			case Quarter: return dateDiffByQuarter(baseTime, time);
			case Year: return dateDiffByYear(baseTime, time);
			default : return 0;
		}
	}
	
	public static long dateDiffByQuarter(Date time) {
		return dateDiffByQuarter(new Date(), time);
	}
	
	public static long dateDiffByQuarter(Date baseTime, Date time) {
		Calendar bc = Calendar.getInstance();
		bc.setTime(baseTime);
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		long years = c.get(Calendar.YEAR) - bc.get(Calendar.YEAR);
		long month1 = c.get(Calendar.MONTH);
		long month2 = bc.get(Calendar.MONTH);
		month1 = (month1 >= 0 && month1 <= 2) ? 0 : (month1 >= 3 && month1 <= 5) ? 3 : (month1 >= 6 && month1 <= 8) ? 6 : 9;
		month2 = (month2 >= 0 && month2 <= 2) ? 0 : (month2 >= 3 && month2 <= 5) ? 3 : (month2 >= 6 && month2 <= 8) ? 6 : 9;
		long months = month1 - month2;
		return (years * 12 + months) / 3;
	}
	
	public static long dateDiffByWeek(Date time) {
		return dateDiffByWeek(new Date(), time);
	}
	
	public static long dateDiffByWeek(Date baseTime, Date time) {
		Calendar bc = Calendar.getInstance();
		bc.setTime(baseTime);
		bc.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		bc.set(Calendar.HOUR_OF_DAY, 0);
		bc.set(Calendar.MINUTE, 0);
		bc.set(Calendar.SECOND, 0);
		bc.set(Calendar.MILLISECOND, 0);
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return (c.getTimeInMillis() - bc.getTimeInMillis()) / (7 * 24 * 60 * 60 * 1000);
	}
	
	public static long dateDiffBySecond(Date time) {
		return dateDiffBySecond(new Date(), time);
	}
	
	public static long dateDiffBySecond(Date baseTime, Date time) {
		Calendar bc = Calendar.getInstance();
		bc.setTime(baseTime);
		bc.set(Calendar.MILLISECOND, 0);
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.set(Calendar.MILLISECOND, 0);
		return (c.getTimeInMillis() - bc.getTimeInMillis()) / (1000);
	}
	
	public static long dateDiffByMinute(Date time) {
		return dateDiffByMinute(new Date(), time);
	}
	
	public static long dateDiffByMinute(Date baseTime, Date time) {
		Calendar bc = Calendar.getInstance();
		bc.setTime(baseTime);
		bc.set(Calendar.SECOND, 0);
		bc.set(Calendar.MILLISECOND, 0);
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return (c.getTimeInMillis() - bc.getTimeInMillis()) / (60 * 1000);
	}
	
	public static long dateDiffByHour(Date time) {
		return dateDiffByHour(new Date(), time);
	}
	
	public static long dateDiffByHour(Date baseTime, Date time) {
		Calendar bc = Calendar.getInstance();
		bc.setTime(baseTime);
		bc.set(Calendar.MINUTE, 0);
		bc.set(Calendar.SECOND, 0);
		bc.set(Calendar.MILLISECOND, 0);
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return (c.getTimeInMillis() - bc.getTimeInMillis()) / (60 * 60 * 1000);
	}
	
	public static long dateDiffByDay(Date time) {
		return dateDiffByDay(new Date(), time);
	}
	
	public static long dateDiffByDay(Date baseTime, Date time) {
		Calendar bc = Calendar.getInstance();
		bc.setTime(baseTime);
		bc.set(Calendar.HOUR_OF_DAY, 0);
		bc.set(Calendar.MINUTE, 0);
		bc.set(Calendar.SECOND, 0);
		bc.set(Calendar.MILLISECOND, 0);
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return (c.getTimeInMillis() - bc.getTimeInMillis()) / (24 * 60 * 60 * 1000);
	}
	
	public static long dateDiffByMonth(Date time) {
		return dateDiffByMonth(new Date(), time);
	}
	
	public static long dateDiffByMonth(Date baseTime, Date time) {
		Calendar bc = Calendar.getInstance();
		bc.setTime(baseTime);
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		long years = c.get(Calendar.YEAR) - bc.get(Calendar.YEAR);
		long months = c.get(Calendar.MONTH) - bc.get(Calendar.MONTH);
		return years * 12 + months;
	}
	
	public static long dateDiffByYear(Date time) {
		return dateDiffByYear(new Date(), time);
	}
	
	public static long dateDiffByYear(Date baseTime, Date time) {
		Calendar bc = Calendar.getInstance();
		bc.setTime(baseTime);
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		return c.get(Calendar.YEAR) - bc.get(Calendar.YEAR);
	}
	
	 /**
     * 通过身份证号码获取出生日期、性别、年龄
     * @param certificateNo
     * @return 返回的出生日期格式：1990-01-01   性别格式：F-女，M-男
     */
    public static Map<String, String> getBirAgeSex(String certificateNo) {
        String birthday = "";
        String age = "";
        String sexCode = "";

        int year = Calendar.getInstance().get(Calendar.YEAR);
        char[] number = certificateNo.toCharArray();
        boolean flag = true;
        if (number.length == 15) {
            for (int x = 0; x < number.length; x++) {
                if (!flag) return new HashMap<String, String>();
                flag = Character.isDigit(number[x]);
            }
        } else if (number.length == 18) {
            for (int x = 0; x < number.length - 1; x++) {
                if (!flag) return new HashMap<String, String>();
                flag = Character.isDigit(number[x]);
            }
        }
        if (flag && certificateNo.length() == 15) {
            birthday = "19" + certificateNo.substring(6, 8) + "-"
                    + certificateNo.substring(8, 10) + "-"
                    + certificateNo.substring(10, 12);
            sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 3, certificateNo.length())) % 2 == 0 ? "F" : "M";
            age = (year - Integer.parseInt("19" + certificateNo.substring(6, 8))) + "";
        } else if (flag && certificateNo.length() == 18) {
            birthday = certificateNo.substring(6, 10) + "-"
                    + certificateNo.substring(10, 12) + "-"
                    + certificateNo.substring(12, 14);
            sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 4, certificateNo.length() - 1)) % 2 == 0 ? "F" : "M";
            age = (year - Integer.parseInt(certificateNo.substring(6, 10))) + "";
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("birthday", birthday);
        map.put("age", age);
        map.put("sexCode", sexCode);
        return map;
    }
	
    /**
     * 获取时间月的第一天
     * @param date
     * @return
     */
    public static Date firstDayOfMonth(Date date){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.MONTH, 0);
    	cal.set(Calendar.DAY_OF_MONTH, 1);
    	return cal.getTime();
    }
    /**
     * 获取上月的第一天
     * @param date
     * @return
     */
    public static Date firstDayOfLastMonth(){
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.MONTH, -1);
    	cal.set(Calendar.DAY_OF_MONTH, 1);
    	return cal.getTime();
    }
    
    /**
     * 获取时间月的最后一天
     * @param date
     * @return
     */
    public static Date lastDayOfMonth(Date date){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.MONTH, 1);
    	cal.set(Calendar.DAY_OF_MONTH, 0);
    	return cal.getTime();
    }
    
    /**
     * 得到指定时间的小时
     * @param date
     * @return
     */
    public static int getHour(Date date) {
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR);
    }
    
    /**
     * 得到指定时间的偏移天数的时间,date指定的时间, num偏移天数
     * @param date  
     * @param num
     * @return
     */
    public static Date getAppointDate(Date date,int num) {
    	Calendar cal=Calendar.getInstance();
    	cal.add(Calendar.DATE,num);
    	return cal.getTime();
    }
    
    /**
     * 字符串转换成指定sdf格式的日期
     * @param strDate
     * @param sdf
     * @return
     * @throws Exception
     */
    public static Date str2Date(String strDate,SimpleDateFormat sdf){
    	Date date = new Date();
    	try {
			date = sdf.parse(strDate);
		} catch (Exception e) {
		}
    	return date;
    }
    /**
     * 将字任串转换成日期,格式:yyyy-MM-dd HH:mm:ss
     * @param strDate
     * @return
     * @throws Exception
     */
    public static Date str2Date(String strDate){
    	return str2Date(strDate,sdf19);
    }
}
