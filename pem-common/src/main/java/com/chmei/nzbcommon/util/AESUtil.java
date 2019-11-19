package com.chmei.nzbcommon.util;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;


/**
 * AES工具类
 * 使用base64AndaesEncrypt加密
 * 使用base64AndaesDecrypt解密
 */
public class AESUtil {

	/**
	 * 测试方法
	 * @param args
	 * @throws Exception 抛出异常
	 */
	/*public static void main(String[] args) throws Exception {
		String str = "zhaoyan";
		String encry = base64AndEncrypt(str,"zhaoyan");
		System.out.println(encry);
		System.out.println(base64AndDecrypt(encry,"zhaoyan"));
		
	}*/
	/**
	 * AES加密
	 * 
	 * @param content
	 *            待加密的内容
	 * @param encryptKey
	 *            加密密钥
	 * @return 加密后的byte[]
	 */
	public static byte[] EncryptToBytes(String content, String encryptKey) {
		return EncryptToBytes(content.getBytes(),encryptKey);
	}

	/**
	 * AES加密
	 * 
	 * @param encryptContent
	 *            待加密的内容
	 * @param encryptKey
	 *            加密密钥
	 * @return 加密后的byte[]
	 */
	public static byte[] EncryptToBytes(byte[] encryptContent, String encryptKey){
		try {
			return aesByMode(encryptContent,encryptKey, Cipher.ENCRYPT_MODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * AES加密为base 64 code
	 * 
	 * @param content
	 *            待加密的内容
	 * @param encryptKey
	 *            加密密钥
	 * @return 加密后的base 64 code
	 */
	public static String base64AndEncrypt(String content, String encryptKey){
		return Base64.encodeBase64URLSafeString(EncryptToBytes(content, encryptKey));
	}
	/**
	 * AES解密
	 * 
	 * @param encryptBytes
	 *            待解密的byte[]
	 * @param decryptKey
	 *            解密密钥
	 * @return 解密后的String
	 */
	public static String DecryptByBytes(byte[] encryptBytes, String decryptKey){
		try {
			byte[] decryptBytes = aesByMode(encryptBytes,decryptKey,Cipher.DECRYPT_MODE);
			return new String(decryptBytes, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据mode选择解密或者加密
	 * @param content 要操作的内容
	 * @param key 密钥
	 * @param MODE 选择模型 Cipher里面的常量
	 * @return 操作后的串
	 */
	private static byte[] aesByMode(byte[] content, String key,Integer MODE) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(key.getBytes());
		//生成128位的key
		kgen.init(128, secureRandom);
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
		return cipher.doFinal(content);
	}

	/**
	 * AES解密
	 * @param normalStr 带解密的串
	 * @param decryptKey 密钥
	 * @return 解密后的string
	 */
	public static String DecryptByStr(String normalStr, String decryptKey){
		return (normalStr==null || "".equals(normalStr)) ? null : DecryptByBytes(normalStr.getBytes(), decryptKey);
	}

	/**
	 * 将base 64 code AES解密
	 * 
	 * @param encryptStr
	 *            待解密的base 64 code
	 * @param decryptKey
	 *            解密密钥
	 * @return 解密后的string
	 */
	public static String base64AndDecrypt(String encryptStr, String decryptKey){
		return (encryptStr==null || "".equals(encryptStr)) ? null : DecryptByBytes(Base64.decodeBase64(encryptStr), decryptKey);
	}
	
	
	
	
	
	
	
	
	
	
	
	///////////////////////文档上内容//////////////////////////////
	public static String entrypt(String sensitiveInformation, String key)
			throws NoSuchAlgorithmException, UnsupportedEncodingException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		String strPassword = key;
		if (null == sensitiveInformation || sensitiveInformation.trim().length() < 1) {
			return "";
		}
		strPassword = generateKey(strPassword);
		byte[] raw = strPassword.getBytes("UTF8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(sensitiveInformation.getBytes());
		String result = Base64.encodeBase64String(encrypted);
		return result; 
	}
	
	/**
	 * BASE64编码
	 * 
	 * @param str
	 * @return
	 */
	private static String generateKey(String str)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (null == str) {
//			str = "defaultpassword";
			str = "";
		} else if (str.length() < 1) {
			str = "emptypassword";
		}
		java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
		md.update(str.getBytes("UTF8"));
		String strret = Base64.encodeBase64String(md.digest());
		while (strret.length() < 16) {
			strret += "%";
		}
		if (strret.length() > 16) {
			int nbegin = (strret.length() - 16) / 2;
			strret = strret.substring(nbegin, nbegin + 16);
		}
		return strret;
	}
	
	
	public static String dentrypt(String detryptContent, String key)
			throws NoSuchAlgorithmException, UnsupportedEncodingException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		String strPassword = key;
		if (null == detryptContent || detryptContent.trim().length() < 1) {
			return null;
		}
		strPassword = generateKey(strPassword);
		byte[] raw = strPassword.getBytes("UTF8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
//		byte[] encrypted1 = new sun.misc.BASE64Decoder()
//				.decodeBuffer(detryptContent);
		byte[] encrypted1 = org.apache.commons.codec.binary.Base64.decodeBase64(detryptContent);
		byte[] original = cipher.doFinal(encrypted1);
		String originalString = new String(original);
		return originalString;
	}

}
