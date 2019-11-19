package com.chmei.nzbcommon.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/***
 * AES加密工具类
 * */
public class EncryptionAES {
	
	public static String entrypt(String sensitiveInformation, String key)
			throws NoSuchAlgorithmException, UnsupportedEncodingException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		String strPassword = key;
		if (null == sensitiveInformation || sensitiveInformation.trim().length() < 1) {
			return null;
		}
		strPassword = generateKey(strPassword);
		byte[] raw = strPassword.getBytes("UTF8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(sensitiveInformation.getBytes());
		return Base64.encodeBase64String(encrypted);
	}

	/**
	 * BASE64编码
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("restriction")
	private static String generateKey(String str)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (null == str) {
			str = "defaultpassword";
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

	
	
	

	/**
	 * 把数据进行加密并返回加密后的数据
	 * */
    public static String sign(String dataToSign,File privateKeyFile) {
        byte[] sign=null;
        try {
            byte[] data = dataToSign.getBytes("UTF-8");
            Signature signature = Signature.getInstance("SHA1withRSA" );
            PrivateKey privateKey = getPrivateKey(privateKeyFile);
            if(privateKey == null ){
                privateKey = getPrivateKey(null);
            }
            if(privateKey ==  null) {
                throw new Exception();
            }
            // 初始化签名，由私钥构建
            signature.initSign(privateKey);
            signature.update(data);
            sign =  signature.sign();
	   } catch (Exception e) {
            e.printStackTrace();
	   }
	   try {
            return byteArr2HexStr(sign);
	   } catch (Exception e) {
            e.printStackTrace();
	   }
	   return null;
    }

	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	
	private static PrivateKey getPrivateKey(File priKeyFile){
		File file = priKeyFile;    
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = br.readLine();
			String str = "";
			s = br.readLine();
			while (s.charAt(0) != '-'){
				str += s + "\r";
				s = br.readLine();
			}
			byte[] b = Base64.decodeBase64(str);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(b);
			RSAPrivateKey priKey = (RSAPrivateKey)keyFactory.generatePrivate(privateKeySpec);
			return priKey;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
