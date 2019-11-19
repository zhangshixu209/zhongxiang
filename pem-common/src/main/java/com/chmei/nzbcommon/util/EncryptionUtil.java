package com.chmei.nzbcommon.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加密工具类
 *
 */
public class EncryptionUtil {
	private EncryptionUtil(){}
	private static final String ALGORITHM = "DES";

	private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionUtil.class);

	/**
	 * Get Des Key
	 * @throws NoSuchAlgorithmException 
	 */
	public static byte[] getKey() throws NoSuchAlgorithmException {
		KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM);
		SecretKey deskey = keygen.generateKey();
		return deskey.getEncoded();
	}

	/**
	 * 加密
	 * @param input
	 * @param key
	 * @return
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 */
	public static byte[] encode(byte[] input, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException  {
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, ALGORITHM);
		Cipher c1 = Cipher.getInstance(ALGORITHM);
		c1.init(Cipher.ENCRYPT_MODE, deskey);
		return c1.doFinal(input);
	}

	/**
	 * 解密
	 * @param input
	 * @param key
	 * @return
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws Exception
	 */
	public static byte[] decode(byte[] input, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException  {
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, ALGORITHM);
		Cipher c1 = Cipher.getInstance(ALGORITHM);
		c1.init(Cipher.DECRYPT_MODE, deskey);
		return c1.doFinal(input);
	}

	/**
	 * 
	 * @param input
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public static byte[] md5(byte[] input) throws NoSuchAlgorithmException  {
		java.security.MessageDigest alg = java.security.MessageDigest
				.getInstance("MD5"); // or "SHA-1"
		alg.update(input);
		return alg.digest();
	}

	/**
	 * Convert byte[] to String
	 * @param byte[] b
	 * @return String
	 */
	public static String byte2hex(byte[] b) {
		StringBuilder hs =new StringBuilder();
		String stmp ;
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1) {
				hs.append("0" + stmp) ;
			} else {
				hs.append( stmp) ;
			}
		}
		return hs.toString().toUpperCase();
	}
	
	/**
	 * Convert String to byte[]
	 * @param hex
	 * @return byte[]
	 */
	public static byte[] hex2byte(String hex)  {
		if (hex.length() % 2 != 0) {
			throw new IllegalArgumentException();
		}
		char[] arr = hex.toCharArray();
		byte[] b = new byte[hex.length() / 2];
		for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
			String swap =  Character.toString(arr[i++]) + arr[i];
			int byteint = Integer.parseInt(swap, 16) & 0xFF;
			Integer obj = new Integer(byteint);
			b[j] = obj.byteValue();
		}
		return b;
	}
}
