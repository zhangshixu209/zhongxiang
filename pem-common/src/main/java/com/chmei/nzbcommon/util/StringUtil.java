package com.chmei.nzbcommon.util;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;


/**
 */
public final class StringUtil {
	/** Private Constructor **/
	
	
	/**
	 * 判断字符串是否非null && 非空字符 
	 * 
	 * @param param
	 * @return
	 */
	public static boolean isNotEmpty(String param) {
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
	 * 判断是否为数字类�?
	 * @param str
	 * @return True为数字类�?
	 */
	public static boolean isNum(String str) {
		String regex = "^(-?\\d+)(\\.\\d+)?$";
		return matchRegex(str, regex);
	}
	/**
	 * 生成uuid
	 * @return
	 */
	public static String getSquence() {
		String rtVal = null;
		rtVal = UUID.randomUUID().toString();
		rtVal = rtVal.replaceAll("-", "");
		return rtVal;
	}
				
	private static boolean matchRegex(String value, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
	public static String unescapeHtml4(String value){
		return StringEscapeUtils.unescapeHtml4(value);
	}
	
	/**
	 * 去除字符串前后空白
	 * @author lianziyu  
	 * @param cs
	 * @return
	 */
	public static String trim(CharSequence cs) {
		if (null == cs)
			return null;
		if (cs instanceof String)
			return ((String) cs).trim();
		int length = cs.length();
		if (length == 0)
			return cs.toString();
		int l = 0;
		int last = length - 1;
		int r = last;
		for (; l < length; l++) {
			if (!Character.isWhitespace(cs.charAt(l)))
				break;
		}
		for (; r > l; r--) {
			if (!Character.isWhitespace(cs.charAt(r)))
				break;
		}
		if (l > r)
			return "";
		else if (l == 0 && r == last)
			return cs.toString();
		return cs.subSequence(l, r + 1).toString();
	}
	/*public static void main(String[] args) {
		System.out.println(StringUtil.getSquence());
	}*/
}
