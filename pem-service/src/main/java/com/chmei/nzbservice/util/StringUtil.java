package com.chmei.nzbservice.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * String工具类
 */
public final class StringUtil {
	/** Private Constructor **/
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
	 * @param str
	 * @return True为数字类
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
	public static String unescapeHtml4(String value){
		return StringEscapeUtils.unescapeHtml4(value);
	}
	/**
	 * 脱敏样本手机号
	 * desensitivePhone:
	 * @author xzq  
	 * @param Phone
	 * @return
	 */
	public static String desensitivePhone(String phone) {
		if(isNotEmpty(phone)) {
			return phone.substring(0, 3)+"****"+phone.substring(phone.length()-4,phone.length());
		}
		return "";
	}
	/**
	 * 脱敏样本姓名
	 * desensitiveName:
	 * @author xzq  
	 * @param name
	 * @return
	 */
	public static String desensitiveName(String name) {
		if(isNotEmpty(name)) {
			return name.substring(0, 1)+"**";
		}
		return "";
	}
	public static String phoneEncrypt(String phone) throws NzbServiceException{
		String phoneNew ;
		if(StringUtil.isEmpty(phone)){
			throw new NzbServiceException("电话号码为空！");
		}else{
			if(phone.length()!=11){
				throw new NzbServiceException("电话号码不是11位！");
			}else{
				String frout = phone.substring(0,3);
				String back = phone.substring(8,11);
				String mid = phone.substring(3,8);
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
				phoneNew = back+mid+frout;
			}
		}
		return phoneNew;
	}
	
	public static String phoneDecrypt(String phone)throws NzbServiceException{
		String phoneNew;
		if(StringUtil.isEmpty(phone)){
			throw new NzbServiceException("电话号码为空！");
		}else{
			if(phone.length()!=11){
				throw new NzbServiceException("电话号码不是11位！");
			}else{
				String frout = phone.substring(0,3);
				String back = phone.substring(8,11);
				String mid = phone.substring(3,8);
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
				phoneNew = back+mid+frout;
			}
		}
		return phoneNew;
	}
	/**
	 * 隐藏电话号码中间4位
	 * @param calledNum
	 * @return
	 */
	public static String maskPhone(String calledNum){
		String front = calledNum.substring(0,3);
		String back = calledNum.substring(7,11);
		String calledNumNew = new StringBuilder(front).append("****").append(back).toString();
		return calledNumNew;
	}
	
	
	/**
     * 脱敏规则
     * @Description: 
     * @param str(需脱敏的字符串) 
     * @param n(脱敏开始位)
     * @param newChar(脱敏掩码)
     * @return
     * @ReturnType String
     * @author: ZhangShiXu
     * @Created 2019年8月6日 10点41分
     */
    public static String replace (String str,int n,String newChar){ 
        String s1=""; 
        String s2=""; 
        try{
            s1=str.substring(0,n-1); 
            s2=str.substring(n-1+newChar.length(),str.length());
            return s1+newChar+s2;
        }catch(Exception ex){ 
            return str;
        } 
    }
	
	/**
     * 脱敏规则——身份证(18／20位：隐藏7—14位)
     * @Description: 
     * @param credNum
     * @return
     * @ReturnType String
     * @author: ZhangShiXu
     * @Created 2019年8月6日 10点41分
     */
    public static String desensitizationCredNum(String credNum){
        String rtnStr = credNum;
        if(isEmpty(rtnStr)||rtnStr.trim().length()<15){
            return rtnStr;
        }else{
            return replace(rtnStr.trim(), 7, "********");
        }
    }
}


