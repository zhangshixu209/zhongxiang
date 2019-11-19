package com.chmei.nzbcommon.util;

import org.apache.log4j.Logger;

public class CheckUtil {

	private static final Logger logger = Logger.getLogger(CheckUtil.class);
	private static final String[] inj_stra = {
			"truncate", "insert", "select", "delete", "update",
			"declare", "onreadystatechange", "atestu",
			 "exec"};
	
	private CheckUtil() {
	}

	public static boolean checkSQLInject(String str, String url) {
		if (StringUtil.isEmpty(str)) {
			return false;// 如果传入空串则认为不存在非法字符
		}
		// 判断黑名单
		String strLower = str.toLowerCase(); // sql不区分大小写
		for (int i = 0; i < inj_stra.length; i++) {
			if (strLower.indexOf(inj_stra[i]) >= 0) {
				logger.info("xss防攻击拦截url:" + url + "，原因：特殊字符，传入str=" + strLower
						+ ",包含特殊字符：" + inj_stra[i]);
				return true;
			}
		}
		return false;
	}

	public static String xssEncode(String s) {
		if (s == null || s.isEmpty()) {
			return s;
		}
		StringBuilder sb = new StringBuilder(s.length() + 16);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case ';':
				sb.append('；');// 全角大于号
				break;
			case '>':
				sb.append('＞');// 全角大于号
				break;
			case '<':
				sb.append('＜');// 全角小于号
				break;
			case '\'':
				sb.append('‘');// 全角单引号
				break;
			case '\\':
				sb.append('＼');// 全角斜线
				break;
			case '#':
				sb.append('＃');// 全角井号
				break;
			case '&':
				sb.append('＆');// 全角
				break;
			case '(':
				sb.append('（');//
				break;
			case ')':
				sb.append('）');//
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}
	   

}
