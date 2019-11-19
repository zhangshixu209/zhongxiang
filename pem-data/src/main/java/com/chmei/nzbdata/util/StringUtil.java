package com.chmei.nzbdata.util;

import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;

import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * String工具类
 */
public final class StringUtil {
	/**
	 * Private Constructor
	 */
	private StringUtil() {
	}

	/**
	 * 
	 * 常量编码
	 *
	 */
	public interface ENCODING {
		String UTF_8 = "UTF-8";
		String GBK = "GBK";
		String ISO_8858_1 = "ISO-8859-1";
		String GB2312 = "GB2312";
	}

	/**
	 * 判断字符串是否非null && 非空字符
	 * 
	 * @param param
	 * @return
	 */
	public static boolean isNotEmpty(String param) {
		StringBuilder ss = new StringBuilder();
		return param != null && !"".equals(param.trim());
	}

	/**
	 * 判断字符串是否为null || 空字符串
	 * 
	 * @param param
	 * @return
	 */
	public static boolean isEmpty(String param) {
		return param == null || "".equals(param.trim());
	}

	/**
	 * 判断是否为数字类
	 * 
	 * @param str
	 * @return True为数字类
	 */
	public static boolean isNum(String str) {
		String regex = "^(-?\\d+)(\\.\\d+)?$";
		return matchRegex(str, regex);
	}

	/**
	 * 生成uuid
	 * 
	 * @return
	 */
	public static String getSquence() {
		return UUID.randomUUID().toString();
	}

	private static boolean matchRegex(String value, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static String unescapeHtml4(String value) {
		return StringEscapeUtils.unescapeHtml4(value);
	}

	public static String phoneEncrypt(String phone) throws NzbDataException {
		String phoneNew;
		if (StringUtil.isEmpty(phone)) {
			throw new NzbDataException("电话号码为空！");
		} else {
			if (phone.length() != 11) {
				throw new NzbDataException("电话号码不是11位！");
			} else {
				String frout = phone.substring(0, 3);
				String back = phone.substring(8, 11);
				String mid = phone.substring(3, 8);
				mid = mid.replace('0', 'q');
				mid = mid.replace('1', 'a');
				mid = mid.replace('2', 'z');
				mid = mid.replace('3', 'w');
				mid = mid.replace('4', 's');
				mid = mid.replace('5', 'x');
				mid = mid.replace('6', 'e');
				mid = mid.replace('7', 'd');
				mid = mid.replace('8', 'c');
				mid = mid.replace('9', 'r');
				phoneNew = back + mid + frout;
			}
		}
		return phoneNew;
	}

	public static String phoneDecrypt(String phone) throws NzbDataException {
		String phoneNew;
		if (StringUtil.isEmpty(phone)) {
			throw new NzbDataException("电话号码为空！");
		} else {
			if (phone.length() != 11) {
				throw new NzbDataException("电话号码不是11位！");
			} else {
				String frout = phone.substring(0, 3);
				String back = phone.substring(8, 11);
				String mid = phone.substring(3, 8);
				mid = mid.replace('q', '0');
				mid = mid.replace('a', '1');
				mid = mid.replace('z', '2');
				mid = mid.replace('w', '3');
				mid = mid.replace('s', '4');
				mid = mid.replace('x', '5');
				mid = mid.replace('e', '6');
				mid = mid.replace('d', '7');
				mid = mid.replace('c', '8');
				mid = mid.replace('r', '9');
				phoneNew = back + mid + frout;
			}
		}
		return phoneNew;
	}

	/**
	 * 隐藏电话号码中间4位
	 * 
	 * @param calledNum
	 * @return
	 */
	public static String maskPhone(String calledNum) {
		if (calledNum.length() >= 11) {
			// 11位手机号码脱敏
			String front = calledNum.substring(0, 3);
			String back = calledNum.substring(calledNum.length() - 4, calledNum.length());
			String calledNumNew = new StringBuilder(front).append("****").append(back).toString();
			return calledNumNew;
		} else {
			return calledNum;
		}
	}

	/**
	 * 获取随机的数字串
	 */
	public static String getRondomNumbers(int length) {
		if (length <= 0) {
			return "";
		}
		char[] codeSequence = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };// 随机数
		Random random = new Random();
		StringBuffer randomCode = new StringBuffer();
		// 随机产生codeCount数字的验证码。
		for (int i = 0; i < length; i++) {
			// 得到随机产生的验证码数字。
			String code = String.valueOf(codeSequence[random.nextInt(10)]);
			randomCode.append(code);
		}
		return randomCode.toString();
	}
}
