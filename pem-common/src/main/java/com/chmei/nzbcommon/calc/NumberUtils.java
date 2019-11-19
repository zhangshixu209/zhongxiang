package com.chmei.nzbcommon.calc;

import java.math.BigDecimal;

/**
 * @author lixinjie
 * @since 2019-03-22
 */
public class NumberUtils {

	/**
	 * 四舍五入
	 * @param number
	 * @param scale 小数位数
	 * @return
	 */
	public static double roundOff(double number, int scale) {
		return new BigDecimal(number).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 保留四位小数
	 * @param number
	 */
	public static double roundOff4(double number) {
		return roundOff(number, 4);
	}
	
	/**
	 * 保留两位小数
	 * @param number
	 */
	public static double roundOff2(double number) {
		return roundOff(number, 2);
	}
	
	
}
