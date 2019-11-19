package com.chmei.nzbcommon.util;

import java.util.Date;

/**
 * 星级评定工具类
 * 
 * @author zhanghm
 *
 */
public class StarUtil {
	
	private StarUtil() {
	    // 
    }
	
	/**
	 * ：注册时间5个月以上为满分10分，5个月以内，每少一个月扣减2分
	 * 
	 * @param date
	 * @return
	 */
	public static int agentRegisterTimeScore(Date registerDate) {
		int num = DateUtil.getMonth(registerDate, new Date());
		int score = num * 2;
		if (score > 10) {
			score = 10;
		}

		return score;
	}

	/**
	 * 日均工作时长评分 日均工作时间：日均通话时间为100分钟为满分30分，每减少5分钟扣减1分，低于1分钟为0分。
	 * 
	 * @param minute
	 * @return
	 */
	public static int avgDailyCommunicateTimeScore(double minute) {

		int score ;
		if (minute >= 100) {
			score = 30;
		} else if (minute >= 1) {
			double minus = Math.ceil((100 - minute) / 5);
			score = (int) Math.round(30 - minus);
		} else {
			score = 0;
		}

		return score;
	}

	/**
	 * 
	 * 日均成功拨打评分 日均成功拨40通为满分30分，每减少两通扣减1分。
	 * 
	 * @param callNum
	 * @return
	 */
	public static int avgDailyCallNumScore(double callNum) {

		int score ;
		if (callNum >= 40) {
			score = 30;
		} else {
			double minus = Math.ceil((40 - callNum) / 2);
			score = (int) Math.round(30 - minus);
		}
		return score;
	}

	/**
	 * 营销成功率：成功率30%为30分满分，按照每低1%，扣减1分为标准。 日均成功营销量12单为满分，每低0.4，扣减1分为标准。
	 * 
	 * @param successedNum
	 * @return
	 */
	public static int marketingSuccessScore(double successedNum) {

		int score ;
		if (successedNum >= 12) {
			score = 30;
		} else {
			double minus = Math.ceil((12 - successedNum) / 0.4);
			score = (int) Math.round(30 - minus);
		}
		return score;
	}

	/**
	 * （1）90分以上为5星级。 （2）80分-90分为4星级。 （3）70分-80分为3星级。 （4）60分-70分为2星级。
	 * （5）60分以下为1星级。
	 * 
	 * @param score
	 */

	public static int getStartLevel(int score) {
		int startLevel;
		if (score >= 90) {
			startLevel = 5;
		} else if (score >= 80) {
			startLevel = 4;
		} else if (score >= 70) {
			startLevel = 3;
		} else if (score >= 60) {
			startLevel = 2;
		} else {
			startLevel = 1;
		}
		return startLevel;
	}

}
