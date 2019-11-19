package com.chmei.nzbcommon.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * RSA非对称加解密工具
 * @author 翟超锋
 * @since 1.8 jdk
 *
 */
public class RSAUtilSQL {


    public final static String ALGORITHM = "RSA";
    public final static String SIGNATURE_ALGORITHM = "MD5withRSA";
    
    /***公钥redis名称*/
    public final static String SQL_PUBLIC_KEY = "report:sql:public_key";
    /***私钥redis名称*/
    public final static String SQL_PRIVATE_Key = "report:sql:private_key";

    
    
    
    /**
     * 获取公钥密钥对
     * @return
     * @throws Exception
     */
    public static KeyPair getKey() throws Exception{
        KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM);
        return generator.generateKeyPair();
    }
    
    /**
     * 生成公钥
     * @param key
     * @return
     * @throws Exception
     */
    private static Key getPublicKey(String key)throws Exception{
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(key));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        Key k = keyFactory.generatePublic(keySpec);
        return k;
    }
    
    /**
     * 生成私钥
     * @param key
     * @return
     * @throws Exception
     */
    private static Key getPrivateKey(String key)throws Exception{
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(key));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        Key k = keyFactory.generatePrivate(keySpec);
        return k;
    }
    
    /**
     * 使用公钥进行加密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String data,String key)throws Exception{
        Key k = getPublicKey(key);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k);
        byte[] bytes = cipher.doFinal(data.getBytes("UTF-8"));
        return Base64.encode(bytes);
    }
    
   
    
    /**
     * 使用密钥进行解密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data,String key)throws Exception{
        Key k = getPrivateKey(key);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k);
        byte[] bytes = cipher.doFinal(Base64.decode(data));
        return new String(bytes,"UTF-8");
    }
    
    
    /**
     * 生成RSA非对称密钥对
     * @return
     */
    public static Map<String,String> getPrivateKey(){
    	KeyPair keyPair = null;
		try {
			keyPair = getKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
    	RSAPublicKey  publicKey = (RSAPublicKey)keyPair.getPublic();
    	
    	String privateKeyStr = Base64.encode(privateKey.getEncoded());
    	String publicKeyStr = Base64.encode(publicKey.getEncoded());
    	Map<String, String> map = new HashMap<>();
    	map.put(SQL_PUBLIC_KEY, publicKeyStr);
    	map.put(SQL_PRIVATE_Key, privateKeyStr);
    	return map;
    }
    
    
    /**
     * test
     */
   /*public static void main(String[] args) throws Exception{
	    KeyPair keyPair = getKey();
	    RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
    	RSAPublicKey  publicKey = (RSAPublicKey)keyPair.getPublic();
    	String privateKeyStr = Base64.encode(privateKey.getEncoded());
    	String publicKeyStr = Base64.encode(publicKey.getEncoded());
    	
        System.out.println("私钥：" + privateKeyStr);
        System.out.println("公钥：" + publicKeyStr);
        
        String data = "select * from tables_name";
        System.out.println("---------------公钥加密，私钥解密-----------------");
        String encryptedData = encryptByPublicKey(data,publicKeyStr);
        System.out.println("加密后：" + encryptedData);
        
        String decryptedData = decryptByPrivateKey(encryptedData, privateKeyStr);
        System.out.println("解密后：" + decryptedData);
        System.out.println("---------------私钥加密，公钥解密-----------------");
        
    }*/
    
}
