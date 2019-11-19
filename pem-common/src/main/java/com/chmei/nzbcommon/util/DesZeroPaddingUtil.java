package com.chmei.nzbcommon.util;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * DES加密工具类
 * 
 */
public class DesZeroPaddingUtil {

	private static BouncyCastleProvider bouncyCastleProvider = new org.bouncycastle.jce.provider.BouncyCastleProvider();
	private static final String strAlgorithm = "DES/ECB/ZeroBytePadding";
	private static final String KEY = "jetyduaa";

	public static String encrypt(String strDataToEncrypt) {
		byte[] key = KEY.getBytes();
		Security.addProvider(bouncyCastleProvider);
		SecretKeySpec keySpec = null;
		DESKeySpec deskey = null;
		String strResult = null;
		try {
			deskey = new DESKeySpec(key);
			keySpec = new SecretKeySpec(deskey.getKey(), "DES");
			Cipher cipher = Cipher.getInstance(strAlgorithm);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			byte[] enc = strDataToEncrypt.getBytes("UTF8");
			byte[] cliperEnc = cipher.doFinal(enc);
			strResult = Base64.encodeBase64String(cliperEnc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strResult;
	}

	/**
	 */
	public static String decrypt(String strDataToDecrypt) {
		byte[] key = KEY.getBytes();
		Security.addProvider(bouncyCastleProvider);
		SecretKeySpec keySpec = null;
		DESKeySpec deskey = null;
		String strResult = null;
		try {
			deskey = new DESKeySpec(key);
			keySpec = new SecretKeySpec(deskey.getKey(), "DES");
			Cipher cipher = Cipher.getInstance(strAlgorithm);
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			byte[] dec = Base64.decodeBase64(strDataToDecrypt);
			byte[] cliperDes = cipher.doFinal(dec);
			return new String(cliperDes, "UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strResult;
	}

	

}
