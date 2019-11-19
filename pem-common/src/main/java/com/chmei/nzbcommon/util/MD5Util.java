package com.chmei.nzbcommon.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MD5工具类
 * @author Administrator
 *
 */
public class MD5Util {
	
	private static final Logger log = LoggerFactory.getLogger(MD5Util.class);
	private static final String SALT="V7GX9CW7CAQIG6IO";
	
	private MD5Util(){}
	/**
	 * 对字符串进行MD5加密
	 * @param str 待加密字符串
	 * @return
	 */
	public static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] encode = md.digest();
			return HexUtil.bytes2Hex(encode);
		} catch (NoSuchAlgorithmException e) {
			log.error("md5", "计算字符串MD5失败", e);
		}
		return null;
	}
	
	/**
	 * 对字符串进行MD5加密
	 * @param str 待加密字符串
	 * @return
	 */
	public static String md5Hex(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] encode = md.digest();
			return HexUtil.bytes2Hex(encode);
		} catch (NoSuchAlgorithmException e) {
			log.error("md5", "计算字符串MD5失败", e);
		}
		return null;
	}
	   /**
     * 生成含有随机盐的密码
     */
    public static String generate(String password) {
            password = md5Hex(password + SALT);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = SALT.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }
    /**
     * 校验密码是否正确
     */
    public static boolean verify(String password, String md5) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5.charAt(i);
            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
            cs2[i / 3] = md5.charAt(i + 1);
        }
        String salt = new String(cs2);
        return md5Hex(password + salt).equals(new String(cs1));
    }
    
	
	/**
	 * 对文件流进行MD5加密
	 * @param fileInputStream
	 * @return
	 */
	public static String md5(FileInputStream fileInputStream) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] buf = new byte[1024];
			int len;
			while((len = fileInputStream.read(buf)) != -1) {
				md.update(buf, 0, len);
			}
			byte[] encode = md.digest();
			return HexUtil.bytes2Hex(encode);
		} catch (NoSuchAlgorithmException e) {
			log.error("md5", "计算文件流MD5失败", e);
		} catch (IOException e) {
			log.error("md5", "IO异常", e);
		}
		return null;
	}
	
	public static void main(String[] args) {
		/*String phone = "15538807500";
		for(int i=0;i<100;i++){
			System.out.println(md5(phone));
		}*/
		String generate = generate("#jxh2019");
		System.err.println(generate);
	}
	
}
