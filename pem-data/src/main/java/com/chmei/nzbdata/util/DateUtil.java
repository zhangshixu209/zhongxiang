package com.chmei.nzbdata.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import com.chmei.nzbdata.common.exception.NzbDataException;


/**
 * 日期工具类
 */
public final class DateUtil {
	/** Private Constructor **/
	private DateUtil() {
	}

	/** 日期格式 **/
	public interface DATE_PATTERN {
		String HH_MM = "HH:mm";
		String HHMMSS = "HHmmss";
		String HH_MM_SS = "HH:mm:ss";
		String YYYYMM = "yyyyMM";
		String YYYYMMDD = "yyyyMMdd";
		String YYYY_MM = "yyyy-MM";
		String YYYY_MM_DD = "yyyy-MM-dd";
		String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
		String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
		String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
		String YYYY_MM_DD_HH_MM_SSSSS = "yyyy-MM-dd HH:mm:ssSSS";
		String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
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
	 * 将Date按格式转化成String
	 * 
	 * @param date
	 *            Date对象
	 * @param pattern
	 *            日期类型
	 * @return String
	 */
	public static String Ts2String(Timestamp date, String pattern) {
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
	/**
	 * 字符串转日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date string2Date(String date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 验证码有效时间
	 * @param effectiveTime
	 * @return
	 */
	// 验证码有效时间，系统发送验证码时自动根据当前时间加上有效时间（暂定为30分钟，可配置）生成。
	public static Date getEffectiveTime(int effectiveTime) {
		Date now = new Date();
		return DateUtil.addMiniutes(now, effectiveTime);
	}

	/**
	 * 
	 * @param value
	 * @param minutes
	 * @return
	 */
	public static Date addMiniutes(Date value, Integer minutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(value);
		return new Date(calendar.getTimeInMillis() + minutes * 60 * 1000);
	}
	
	
	
	/**
	 * //获取身份证上的生日
	 * @param idCard
	 * @return
	 */
	
	public static Date getBirthdayByIdCard(String idCard) {
		String yearText = idCard.substring(6, 10);
		String monthText = idCard.substring(10, 12);
		String dayText = idCard.substring(12, 14);

		String dateText = yearText + "-" + monthText + "-" + dayText + " 00:00:00";

		return DateUtil.string2Date(dateText);
	}
	
	/**
	 * 
	 * @param idCard
	 * @return
	 */
	public static Integer getAgeIdCard(String idCard) {
		Integer yearText = Integer.valueOf(idCard.substring(6, 10));
		String dat=date2String(new Date());
		Integer newTime =Integer.valueOf(dat.substring(0, 4));
		return newTime-yearText;
	}
	/**
	 * 判断给定的日期是否在时间段内
	 * @param begin
	 * @param end
	 * @param date
	 * @return
	 */
	public static boolean timeRange(String begin, String end, Date date) {
		String beginDate;
		String endDate;
		if(begin.length()!=5){
			beginDate="0"+begin;
		}else{
			beginDate = begin;
		}
		if(end.length()!=5){
			endDate="0"+end;
		}else{
			endDate = end;
		}
		Date beginTime = string2Date(beginDate, DATE_PATTERN.HH_MM);
		Date endTime = string2Date(endDate, DATE_PATTERN.HH_MM);
		Date dateTime=string2Date(date2String(date,  DATE_PATTERN.HH_MM), DATE_PATTERN.HH_MM);
		Calendar bgnCalendar = Calendar.getInstance();
		bgnCalendar.setTime(beginTime);
		Calendar dateCalendar = Calendar.getInstance();
		dateCalendar.setTime(dateTime);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endTime);
		boolean flag;
		if (bgnCalendar.getTimeInMillis() <= dateCalendar.getTimeInMillis() && endCalendar.getTimeInMillis() >= dateCalendar.getTimeInMillis())
			flag= true;
		else
			flag=false;
		return flag;
	}
	
	/**
	 * 结束日期加一天
	 * @param date
	 * @return endTime
	 * @throws ParseException
	 */
	public static Date addEndTime(String date) throws ParseException{
		Date endTime = string2Date(date, DATE_PATTERN.YYYY_MM_DD);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endTime);
		calendar.add(Calendar.DAY_OF_MONTH, +1);//+1时间加.天
		endTime = calendar.getTime();
		return endTime;
	}
	
	/**
	 * 判断是否在指定日期之前
	 */
	public static boolean compareDateBefore(String startTime,String staticTime){
		String time = staticTime ;
		if(time == null || time.isEmpty()) {
			time ="2017-10-01 00:00:00";
		}
		Date compStiTime = string2Date(time);
		Date compStaTime = string2Date(startTime);
		boolean flag;
		if(compStaTime.before(compStiTime)) {
			flag = true;
		}else {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 判断两个日期的大小
	 * @param startTime
	 * @param staticTime
	 * @return
	 */
	public static boolean compareDateBefores(String start,String end){
		Date compstart = string2Date(start,DATE_PATTERN.YYYY_MM_DD);
		Date compend = string2Date(end,DATE_PATTERN.YYYY_MM_DD);
		boolean flag = true;
		if(compstart.before(compend) || compstart.equals(compend)) {
			flag = true;
		}else{
			flag = false;
		}
		return flag;
	}
	
	
	/**
	 * 获取日,周,月的第一时刻
	 * 默认获取当前日的第一时刻
	 * @param type
	 * @return time
	 * @throws ParseException
	 */
	public static Date getTimeType(String type) throws ParseException{
		Date time = new Date();
		Calendar calendar = Calendar.getInstance();
		if("week".equals(type)){
			calendar.setTime(time);
			int dayOfWeek=calendar.get(Calendar.DAY_OF_WEEK)-2;//当前日期距离当前星期一有几天
			if(dayOfWeek<0){
				dayOfWeek = dayOfWeek+7;
			}
			calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek);//获取当前星期的第一时刻
			time = calendar.getTime();
		}else if("month".equals(type)){
			calendar.setTime(time);
			int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH)-1;//当前日期距离当前月份1号有几天
			calendar.add(Calendar.DAY_OF_MONTH, -dayOfMonth);//获取当前月份的第一时刻
			time = calendar.getTime();
		}
		
		String dateStr = date2String(time, DATE_PATTERN.YYYY_MM_DD);
		time = string2Date(dateStr, DATE_PATTERN.YYYY_MM_DD);
		
		return time;
	}
	
	/**
	 * 返回分钟数
	 * @param crtTime
	 * @param modfTime
	 * @return
	 */
	public static long getMin(String crtTime,String modfTime) throws NzbDataException{
		SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
		long day = 0;
		long min = 3000;
		try {
			long nd = 86400000;
			long nh = 3600000;
			long nm = 60000;// 一分钟的毫秒数
			Date cdate = format.parse(crtTime);
			Date mdate = format.parse(modfTime);
			Calendar cCalendar = Calendar.getInstance();
			cCalendar.setTime(cdate);
			Calendar mCalendar = Calendar.getInstance();
			mCalendar.setTime(mdate);
		    long diff = mCalendar.getTimeInMillis() - cCalendar.getTimeInMillis();
		    day = diff / nd;//计算差多少天 
            min = diff % nd % nh / nm + day * 24 * 60;
		} catch (Exception e) {
			throw new NzbDataException(e);
		}
		return min;
	}
	
	/**
	 * 返回天数
	 * @param crtTime
	 * @param modfTime
	 * @return
	 */
	public static long getDay(String crtTime,String modfTime) throws NzbDataException{
		SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
		long day = 0;
		try {
			long nd = 86400000;
			Date cdate = format.parse(crtTime);
			Date mdate = format.parse(modfTime);
			Calendar cCalendar = Calendar.getInstance();
			cCalendar.setTime(cdate);
			Calendar mCalendar = Calendar.getInstance();
			mCalendar.setTime(mdate);
		    long diff = mCalendar.getTimeInMillis() - cCalendar.getTimeInMillis();
		    day = diff / nd;//计算差多少天 
		} catch (Exception e) {
			throw new NzbDataException(e);
		}
		return day;
	}
	
	/**
	 * 获取某日期N天后的日期
	 * @param datestr
	 * @param day
	 * @return
	 */
	public static Date getBeforeAfterDate(String datestr, int day) throws NzbDataException {  
        SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);  
        java.sql.Date olddate = null;  
        try {  
            df.setLenient(false);  
            Date date = df.parse(datestr);
            Calendar cCalendar = Calendar.getInstance();
			cCalendar.setTime(date);
            olddate = new java.sql.Date(cCalendar.getTimeInMillis()); 
        } catch (ParseException e) {  
            throw new NzbDataException("日期转换错误");  
        }  
        Calendar cal = new GregorianCalendar();  
        cal.setTime(olddate);  
  
        int Year = cal.get(Calendar.YEAR);  
        int Month = cal.get(Calendar.MONTH);  
        int Day = cal.get(Calendar.DAY_OF_MONTH);  
  
        int NewDay = Day + day;  
  
        cal.set(Calendar.YEAR, Year);  
        cal.set(Calendar.MONTH, Month);  
        cal.set(Calendar.DAY_OF_MONTH, NewDay);  
  
        return new Date(cal.getTimeInMillis());  
    }  
	
	public static boolean compareDate(String DATE1, String DATE2){
		boolean flag = false;
        Date dt1 = string2Date(DATE1, DATE_PATTERN.YYYY_MM_DD);
        Date dt2 = string2Date(DATE2, DATE_PATTERN.YYYY_MM_DD);
        if (dt1.before(dt2)) 
        	flag = true;//dt2时间点，之前方式
        else if (dt2.before(dt1)) 
        	flag = false;
        return flag;
    }
	
	public static boolean dateCompare(String DATE1, String DATE2){
		String beforeDate;
		if("".equals(DATE1)){
			beforeDate = date2String(new Date(),DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
        }else{
        	beforeDate = DATE1;
        }
        Date dt1 = string2Date(beforeDate, DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
        Date dt2 = string2Date(DATE2, DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
        boolean flag = false;
        if (dt1.before(dt2)) 
        	flag = true;//dt2时间点，之前方式
        else if (dt2.before(dt1)) 
        	flag = false;
        return flag;
    }
	
	/**
	 * 判断当前时间是否在外呼时间段
	 * @param timeSection
	 * @return
	 */
	public static boolean isTimeInPeriod(String timeSection) {
		boolean flag = false;
		int indexNum = timeSection.indexOf("|");
		if (indexNum > 0) {
			// 上午，下午时间段
			String[] timeArr = timeSection.split("\\|");
			String timeSec1 =timeArr[0];
			String timeSec2 =  timeArr[1];
			String[] timeSec1Arr = timeSec1.split("-");
			String[] timeSec2Arr = timeSec2.split("-");
			if (DateUtil.timeRange(timeSec1Arr[0], timeSec1Arr[1],
					new Date())
					|| DateUtil.timeRange(timeSec2Arr[0],
							timeSec2Arr[1], new Date()) ){
				flag = true;
			}

		} else {
			// 按天 时间段
			String[] stre = timeSection.split("-");
			if (DateUtil.timeRange(stre[0], stre[1], new Date())) {
				flag = true;
			}

		}
		return flag;
	}
	
	
	public static Date addDate(Date date ,int field ,int amount){
		Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(field,amount);
        return rightNow.getTime();
	}
	
	public static Date addDate(Date date,long second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		long time = calendar.getTimeInMillis(); // 得到指定日期的毫秒数
		long millisecond = second*1000; // 要加上的秒转换成毫秒数
		time+=millisecond; // 相加得到新的毫秒数
		return new Date(time); // 将毫秒数转换成日期
	}
	
	public static int getMonthByDate(Date d){
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(c.MONTH)+1;
		
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
	
	/**
	 * 
	 * @Title:        isEqualDate 
	 * @Description:  比较两个日期截止到日是否相等
	 * @param:        @param startTime
	 * @param:        @param endTime
	 * @param:        @return    
	 * @return:       boolean    
	 * @author        冯高星
	 * @Date          2017年9月11日 下午5:08:26
	 */
	public static boolean isEqualDate(String startTime, String endTime) {
		Date startDate = string2Date(startTime, DATE_PATTERN.YYYY_MM_DD);
		Date endDate = string2Date(endTime, DATE_PATTERN.YYYY_MM_DD);
		Calendar startDateCalendar = Calendar.getInstance();
		Calendar endDateCalendar = Calendar.getInstance();
		startDateCalendar.setTime(startDate);
		endDateCalendar.setTime(endDate);
		if(startDateCalendar.getTimeInMillis() == endDateCalendar.getTimeInMillis()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断当前时间是否在抢单时间段
	 * @param timeSection
	 * @return
	 */
	public static boolean isGrabTimeInPeriod(String timeSection) {
		boolean flag = false;
		int indexNum = timeSection.indexOf(",");
		if(indexNum!=-1){
			String[] str1 = timeSection.split(",");
			indexNum = str1.length-1;
		}
		if (indexNum == -1) {
			String[] timeArr = timeSection.split(",");
			String timeSec1 =timeArr[0];
			String[] timeSec1Arr = timeSec1.split("-");
			if (DateUtil.timeRange(timeSec1Arr[0], timeSec1Arr[1],new Date())){
				flag = true;
			}
		}else if(indexNum == 1){
			String[] timeArr = timeSection.split(",");
			String timeSec1 =timeArr[0];
			String timeSec2 =  timeArr[1];
			String[] timeSec1Arr = timeSec1.split("-");
			String[] timeSec2Arr = timeSec2.split("-");
			if (DateUtil.timeRange(timeSec1Arr[0], timeSec1Arr[1],new Date())
					|| DateUtil.timeRange(timeSec2Arr[0],timeSec2Arr[1], new Date())){
				flag = true;
			}
		}else if(indexNum == 2){
			// 上午，下午，晚上时间段
			String[] timeArr = timeSection.split(",");
			String timeSec1 =timeArr[0];
			String timeSec2 =  timeArr[1];
			String timeSec3 =  timeArr[2];
			String[] timeSec1Arr = timeSec1.split("-");
			String[] timeSec2Arr = timeSec2.split("-");
			String[] timeSec3Arr = timeSec3.split("-");
			if (DateUtil.timeRange(timeSec1Arr[0], timeSec1Arr[1],new Date())
					|| DateUtil.timeRange(timeSec2Arr[0],timeSec2Arr[1], new Date())
					|| DateUtil.timeRange(timeSec3Arr[0],timeSec3Arr[1], new Date())){
				flag = true;
			}
		}
		return flag;
	}
	
	
	/**
	 * 计算年月时间
	 * @param timeSection
	 * @return
	 */
	public static Map<String,Object> getAgentMonthTime(String timeSection) {
	    Map<String,Object> map = new HashMap<>();
		Date d = string2Date(timeSection,DATE_PATTERN.YYYY_MM_DD);
	    Calendar foretime = Calendar.getInstance();
	    foretime.setTime(d);
	    Calendar nowCalendar = Calendar.getInstance();
	    int day = nowCalendar.get(Calendar.DAY_OF_MONTH) - foretime.get(Calendar.DAY_OF_MONTH);
	    int month = nowCalendar.get(Calendar.MONTH) - foretime.get(Calendar.MONTH);
	    int year = nowCalendar.get(Calendar.YEAR) - foretime.get(Calendar.YEAR);
	    if(day < 0) {
	        month -= 1;
	        nowCalendar.add(Calendar.MONTH, -1);// 得到上一个月，用来得到上个月的天数。
	    }
	    if(month < 0) {
	        month = (month + 12) % 12;
	        year--;
	    }
	    map.put("year", year);
	    map.put("month", month);
	    return map;
	}
	
	
	
	/**
	 * 
	 * @Title:        isRankOrderRuleTime 
	 * @Description:  抢单规则配置时间段校验是否在当前时间
	 * @param:        @param param
	 * @param:        @return    
	 * @return:       boolean    
	 * @throws 
	 * @author        冯高星
	 * @Date          2017年8月15日 下午4:10:26
	 */
	public static boolean isRankOrderRuleTime(String param){
		boolean flag = false;
		String[] timeSec1Arr = param.split("-");
		String begin = timeSec1Arr[0];
		String end = timeSec1Arr[1];
		Date date = new Date();
		String beginDate;
		String endDate;
		if(begin.length()!=5){
			beginDate="0"+begin;
		}else{
			beginDate = begin;
		}
		if(end.length()!=5){
			endDate="0"+end;
		}else{
			endDate = end;
		}
		Date beginTime = string2Date(beginDate, DATE_PATTERN.HH_MM);
		Date endTime = string2Date(endDate, DATE_PATTERN.HH_MM);
		Date dateTime=string2Date(date2String(date,  DATE_PATTERN.HH_MM), DATE_PATTERN.HH_MM);
		Calendar bgnCalendar = Calendar.getInstance();
		bgnCalendar.setTime(beginTime);
		Calendar dateCalendar = Calendar.getInstance();
		dateCalendar.setTime(dateTime);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endTime);
		if (bgnCalendar.getTimeInMillis() <= dateCalendar.getTimeInMillis() && endCalendar.getTimeInMillis() > dateCalendar.getTimeInMillis()){
        	flag = true;
        }
		return flag;
	}
	/**
	 * 
	 * @Title:        getRobMonadTimeDot 
	 * @Description:  抢单时间顺延策略判断,判断不通过返回时间,通过返回""
	 * @param:        @param releaseTime
	 * @param:        @param robMonadTimeDotDetails
	 * @param:        @return
	 * @param:        @throws OcpCoreException    
	 * @return:       String    
	 * @throws 
	 * @author        冯高星
	 * @Date          2017年9月5日 上午10:00:25
	 */
	public static String getRobMonadTimeDot(String releaseTime, Map<String, String> robMonadTimeDotDetails) throws NzbDataException{
		//项目发布日期
		Date beforeTimeDate = DateUtil.string2Date(releaseTime, DateUtil.DATE_PATTERN.YYYY_MM_DD);
		Calendar beforeTimeDateCalendar = Calendar.getInstance();
		beforeTimeDateCalendar.setTime(beforeTimeDate);
		//项目发布时分
		Date beforeTime = DateUtil.string2Date(DateUtil.date2String(DateUtil.string2Date(releaseTime, "yyyy-MM-dd HH:mm"), DateUtil.DATE_PATTERN.HH_MM), DateUtil.DATE_PATTERN.HH_MM);
		Calendar beforeTimeCalendar = Calendar.getInstance();
		beforeTimeCalendar.setTime(beforeTime);
		//当前日期
		Date nowTimeDate = DateUtil.string2Date(DateUtil.date2String(new Date()), DateUtil.DATE_PATTERN.YYYY_MM_DD);
		Calendar nowTimeDateCalendar = Calendar.getInstance();
		nowTimeDateCalendar.setTime(nowTimeDate);
		//当前时分
		Date nowTime = DateUtil.string2Date(DateUtil.date2String(DateUtil.string2Date(DateUtil.date2String(new Date()), "yyyy-MM-dd HH:mm"), DateUtil.DATE_PATTERN.HH_MM), DateUtil.DATE_PATTERN.HH_MM);
		Calendar nowTimeCalendar = Calendar.getInstance();
		nowTimeCalendar.setTime(nowTime);
		
		long nd = 86400000;
		if(robMonadTimeDotDetails != null && !robMonadTimeDotDetails.isEmpty()){
			String[] strategyConfigureArray = robMonadTimeDotDetails.get("strategyConfigure").split(",");
			int i = strategyConfigureArray.length;
			for(int a = 0; a < i; a++){
				if(strategyConfigureArray[a].length() != 5){
					strategyConfigureArray[a] = "0" + strategyConfigureArray[a];
				}
			}
			//第一个时间点
			String strategyConfigureFirstTime = strategyConfigureArray[0];
			Date strategyConfigureFirstTimeDate = string2Date(strategyConfigureFirstTime, DATE_PATTERN.HH_MM);
			Calendar strategyConfigureFirstTimeDateCalendar = Calendar.getInstance();
			strategyConfigureFirstTimeDateCalendar.setTime(strategyConfigureFirstTimeDate);
			//最后一个时间点
			String strategyConfigureLastTime = strategyConfigureArray[i-1];
			Date strategyConfigureLastTimeDate = string2Date(strategyConfigureLastTime, DATE_PATTERN.HH_MM);
			Calendar strategyConfigureLastTimeDateCalendar = Calendar.getInstance();
			strategyConfigureLastTimeDateCalendar.setTime(strategyConfigureLastTimeDate);
			//如果是昨天发布的项目则判断是否是昨天最后一个时间点之后发布的
			
			if(beforeTimeDateCalendar.getTimeInMillis() + nd == nowTimeDateCalendar.getTimeInMillis() && 
					beforeTimeCalendar.getTimeInMillis() > strategyConfigureLastTimeDateCalendar.getTimeInMillis() && 
					nowTimeCalendar.getTimeInMillis() < strategyConfigureFirstTimeDateCalendar.getTimeInMillis()){
				return strategyConfigureFirstTime + " 开抢";
			}else 
			//如果是今天发布的项目,则判断是哪个时间段发布的
			if(beforeTimeDateCalendar.getTimeInMillis() == nowTimeDateCalendar.getTimeInMillis()){
				for(int b = 0; b < i; b++){
					Date strategyConfigureTime = string2Date(strategyConfigureArray[b], DATE_PATTERN.HH_MM);
					Calendar strategyConfigureTimeCalendar = Calendar.getInstance();
					strategyConfigureTimeCalendar.setTime(strategyConfigureTime);
					if(beforeTimeCalendar.getTimeInMillis() < strategyConfigureTimeCalendar.getTimeInMillis() && 
							nowTimeCalendar.getTimeInMillis() < strategyConfigureTimeCalendar.getTimeInMillis()){
						return strategyConfigureArray[b] + " 开抢";
					}else if(beforeTimeCalendar.getTimeInMillis() <= strategyConfigureTimeCalendar.getTimeInMillis() && 
							nowTimeCalendar.getTimeInMillis() >= strategyConfigureTimeCalendar.getTimeInMillis()){
						return "";
					}
				}
				if(beforeTimeCalendar.getTimeInMillis() > strategyConfigureLastTimeDateCalendar.getTimeInMillis()){
					return "次日 " + strategyConfigureFirstTime + " 开抢";
				}
			}else if(beforeTimeDateCalendar.getTimeInMillis() > nowTimeDateCalendar.getTimeInMillis()){
				//明天开始的项目
				for(int b = 0; b < i; b++){
					Date strategyConfigureTime = string2Date(strategyConfigureArray[b], DATE_PATTERN.HH_MM);
					Calendar strategyConfigureTimeCalendar = Calendar.getInstance();
					strategyConfigureTimeCalendar.setTime(strategyConfigureTime);
					if(beforeTimeCalendar.getTimeInMillis() < strategyConfigureTimeCalendar.getTimeInMillis() ){
						return "次日 "+strategyConfigureArray[b] + " 开抢";
					}
				}
				if(beforeTimeCalendar.getTimeInMillis() > strategyConfigureLastTimeDateCalendar.getTimeInMillis()){
					return "后日" + strategyConfigureFirstTime + " 开抢";
				}
				
			}
		}
		return "";
	}
	/*
	 * 判断项目发布时间或开始时间在哪一个集中抢单时间点之前
	 */
	public static int isBeforeTimeComparisonNowTime(String releaseTime, Map<String, String> robMonadConcentrate, Map<String, String> robMonadRegulation){
		int n = 0;
		//判断缓存是否有值
		if(robMonadConcentrate != null && !robMonadConcentrate.isEmpty() && robMonadRegulation != null && !robMonadRegulation.isEmpty()){
			//项目发布日期
			Date beforeTimeDate = DateUtil.string2Date(releaseTime, DateUtil.DATE_PATTERN.YYYY_MM_DD);
			Calendar beforeTimeDateCalendar = Calendar.getInstance();
			beforeTimeDateCalendar.setTime(beforeTimeDate);
			//项目发布时分
			Date beforeTime = DateUtil.string2Date(DateUtil.date2String(DateUtil.string2Date(releaseTime, "yyyy-MM-dd HH:mm"), DateUtil.DATE_PATTERN.HH_MM), DateUtil.DATE_PATTERN.HH_MM);
			Calendar beforeTimeCalendar = Calendar.getInstance();
			beforeTimeCalendar.setTime(beforeTime);
			//当前日期
			Date nowTimeDate = DateUtil.string2Date(DateUtil.date2String(new Date()), DateUtil.DATE_PATTERN.YYYY_MM_DD);
			Calendar nowTimeDateCalendar = Calendar.getInstance();
			nowTimeDateCalendar.setTime(nowTimeDate);
			//集中抢单时间点
			String strategyConfigure = robMonadConcentrate.get("strategyConfigure");
			String[] strategyConfigureArray = strategyConfigure.split(",");
			int i = strategyConfigureArray.length;
			for(int a = 0; a < i; a++){
				if(strategyConfigureArray[a].length() != 5){
					strategyConfigureArray[a] = "0" + strategyConfigureArray[a];
				}
			}
			//最后一个时间点
			String strategyConfigureLastTime = strategyConfigureArray[i-1];
			Date strategyConfigureLastTimeDate = string2Date(strategyConfigureLastTime, DATE_PATTERN.HH_MM);
			Calendar strategyConfigureLastTimeDateCalendar = Calendar.getInstance();
			strategyConfigureLastTimeDateCalendar.setTime(strategyConfigureLastTimeDate);
			//1天的毫秒数
			long nd = 86400000;
			//判断项目发布日期和当前日期是否在同一天
			if(beforeTimeDateCalendar.getTimeInMillis() == nowTimeDateCalendar.getTimeInMillis()){
				//判断项目发布时分在哪个时间点之前
				for(int j=0; j<i; j++){
					Date strategyConfigureTime = string2Date(strategyConfigureArray[j], DATE_PATTERN.HH_MM);
					Calendar strategyConfigureTimeCalendar = Calendar.getInstance();
					strategyConfigureTimeCalendar.setTime(strategyConfigureTime);
					if(beforeTimeCalendar.getTimeInMillis() <= strategyConfigureTimeCalendar.getTimeInMillis()){
						n = j + 1;
						break;
					}else if(beforeTimeCalendar.getTimeInMillis() > strategyConfigureLastTimeDateCalendar.getTimeInMillis()){
						n = 1;
						break;
					}
				}
			}else if(beforeTimeDateCalendar.getTimeInMillis() + nd == nowTimeDateCalendar.getTimeInMillis() && 
					beforeTimeCalendar.getTimeInMillis() > strategyConfigureLastTimeDateCalendar.getTimeInMillis()){
				n = 1;
			}
		}
		return n;
	}
	/*
	 * 判断当前时间是否再外呼日程内(MrFeng)
	 */
	public static boolean isOutboundSchedule(Map<String, String> outboundScheduleMap){
		if(outboundScheduleMap != null && !outboundScheduleMap.isEmpty()){
			Date date = new Date();
			boolean result = false;
			if(outboundScheduleMap.containsKey("firstBeginTime") &&  StringUtil.isNotEmpty(outboundScheduleMap.get("firstBeginTime")) 
					&& outboundScheduleMap.containsKey("firstEndTime") &&  StringUtil.isNotEmpty(outboundScheduleMap.get("firstEndTime"))){
				String firstBeginTime = outboundScheduleMap.get("firstBeginTime");
				String firstEndTime = outboundScheduleMap.get("firstEndTime");
				result = DateUtil.timeRange(firstBeginTime, firstEndTime, date);
			}
			if(!result && outboundScheduleMap.containsKey("secondBeginTime") &&  StringUtil.isNotEmpty(outboundScheduleMap.get("secondBeginTime"))
					&& outboundScheduleMap.containsKey("secondEndTime") &&  StringUtil.isNotEmpty(outboundScheduleMap.get("secondEndTime"))){
				String secondBeginTime = outboundScheduleMap.get("secondBeginTime");
				String secondEndTime = outboundScheduleMap.get("secondEndTime");
				result = DateUtil.timeRange(secondBeginTime, secondEndTime, date);
			}
			if(!result && outboundScheduleMap.containsKey("thirdBeginTime") &&  StringUtil.isNotEmpty(outboundScheduleMap.get("thirdBeginTime"))
					&& outboundScheduleMap.containsKey("thirdEndTime") &&  StringUtil.isNotEmpty(outboundScheduleMap.get("thirdEndTime"))){
				String thirdBeginTime = outboundScheduleMap.get("thirdBeginTime");
				String thirdEndTime = outboundScheduleMap.get("thirdEndTime");
				result = DateUtil.timeRange(thirdBeginTime, thirdEndTime, date);
			}
			return result;
		}
		return true;
	}
	
	/**
	 * 日期减一天
	 * @param date
	 * @return endTime
	 * @throws ParseException
	 */
	public static String subtractEndTime(String date) throws ParseException{
		Date dateTime = string2Date(date, DATE_PATTERN.YYYY_MM_DD);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		dateTime = calendar.getTime();
		String time = date2String(dateTime, DATE_PATTERN.YYYYMMDD);
		return time;
	}
	
	/**
	 * 距离当晚23:59:59的秒数
	 */
	public static int getSeounds(){
		Date nowTimeDate = DateUtil.string2Date(DateUtil.date2String(new Date()), DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
		Calendar nowTimeDateCalendar = Calendar.getInstance();
		nowTimeDateCalendar.setTime(nowTimeDate);
		
		String afterTime = DateUtil.date2String(new Date(),DATE_PATTERN.YYYY_MM_DD) + " 23:59:59";
		Date afterTimeDate = DateUtil.string2Date(afterTime, DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
		Calendar afterTimeDateCalendar = Calendar.getInstance();
		afterTimeDateCalendar.setTime(afterTimeDate);
		
		int seounds = (int)(afterTimeDateCalendar.getTimeInMillis() - nowTimeDateCalendar.getTimeInMillis()) / 1000;
		
		return seounds;
	}
	public static String getBeforeHourTime(int ihour){  
	    String returnstr;  
	    Calendar calendar = Calendar.getInstance();  
	    calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - ihour);  
	    SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);  
	    returnstr = df.format(calendar.getTime());  
	    return returnstr;  
	}

	public static String subtractNowTime(Date dateTime) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		Date waitDateTime = calendar.getTime();
		String time = date2String(waitDateTime, DATE_PATTERN.YYYY_MM_DD);
		return time;
	}  
	
	/**
	 * 
	 * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
	 * 
	 * @param date
	 * @return
	 */
	public static int getSeason(Date date) {
		int season = 0;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		switch (month) {
		case Calendar.JANUARY:
		case Calendar.FEBRUARY:
		case Calendar.MARCH:
			season = 1;
			break;
		case Calendar.APRIL:
		case Calendar.MAY:
		case Calendar.JUNE:
			season = 2;
			break;
		case Calendar.JULY:
		case Calendar.AUGUST:
		case Calendar.SEPTEMBER:
			season = 3;
			break;
		case Calendar.OCTOBER:
		case Calendar.NOVEMBER:
		case Calendar.DECEMBER:
			season = 4;
			break;
		default:
			break;
		}
		return season;
	}
	
	/**
	 * 
	 * @Title:        getWeekDayByNowDate 
	 * @Description:  查询某一天是周几
	 * @param:        @return    
	 * @return:       int    
	 * @throws 
	 * @author        冯高星
	 * @Date          2018年8月16日 下午8:47:10
	 */
	public static int getWeekDayByNowDate(String strDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(string2Date(strDate, DateUtil.DATE_PATTERN.YYYY_MM_DD));
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 *  获取次日日期字符串类型
	 *  @param date
	 *  @return String
	 */
	public static String getNextDay(String date){
		Calendar   calendar   =   new   GregorianCalendar(); 
	    calendar.setTime(string2Date(date, DateUtil.DATE_PATTERN.YYYY_MM_DD) ); 
	     calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
	     return date2String(calendar.getTime(), DateUtil.DATE_PATTERN.YYYY_MM_DD);   //这个时间就是日期往后推一天的结果 
	}
	
	/**
	 *  获取当前月前或后第N个月的第一天
	 *  @param date
	 *  @return String
	 */
	public static String getNMonthWithNow(int n){
		Calendar calendar = new   GregorianCalendar(); 
		calendar.add(Calendar.MONTH, n);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return date2String(calendar.getTime(), DateUtil.DATE_PATTERN.YYYY_MM_DD);   //这个时间就是当前月前或后第N个月的第一天的结果 
	}

	
	
	/**
	 * 取得当月天数
	 * */
	public static int getCurrentMonthLastDay(){
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);//把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}
	
	
	/**日期格式化*/	
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
	//12小时制
	static SimpleDateFormat format12 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
	//24小时制
	static SimpleDateFormat format24 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	/**日期工具类*/
	static Calendar calen = null;  
	
	
	/**
	 * 根据参数获取字符串日期的n月n天
	 * @param curDate
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getLastMonthEnd(String curDate,int month,int day){
		Date date = null;
        try{
          date = format.parse(curDate);//初始日期
        }catch(Exception e){
        	e.printStackTrace();
        }
		calen = Calendar.getInstance();
		calen.setTime(date);
		calen.add(Calendar.MONTH, month);
		calen.set(Calendar.DAY_OF_MONTH, calen.getActualMinimum(Calendar.DAY_OF_MONTH)); 
		calen.add(Calendar.DATE, day);
        String first = format.format(calen.getTime());
        return first;
	}
	
	/**
	 * 当前时间减一天
	 * @param curDate
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getCurrentOne(int month,int day){
		calen = Calendar.getInstance();
		calen.setTime(new Date());
		calen.add(Calendar.MONTH, month);
		calen.add(Calendar.DATE, day);
        String first = format.format(calen.getTime());
        return first;
	}

	/**
	 * 返回上一个月份的值
	 * @return 返回yyyyMM格式
	 */
	public static String getPrevMonth(String datePattern){
		Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        String statMonth = new SimpleDateFormat(datePattern).format(calendar.getTime());
        return statMonth;
	}
	
	/**
	* 通过时间秒毫秒数判断两个时间的间隔
	* @param date1
	* @param date2
	* @return
	*/
	public static long differentDaysByMillisecond(Date startTime,Date endTime)	{
		long millisecond = endTime.getTime() - startTime.getTime();        
		return millisecond;
	}
	
	/*public static void main(String[] args) throws InterruptedException {
        Date startDate = new Date();
        Thread.sleep(3000);
        Date endDate = new Date();
		System.out.println(differentDaysByMillisecond(startDate,endDate));
	}*/
}
