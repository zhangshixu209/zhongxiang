package com.chmei.nzbcommon.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 签名工具类
 * @author hxh
 */
public class SignUtils {

	
    /**
     * 生成签名
     * @param clientId 客户端id
     * @param signKey 签名密钥
     * @param timestamp 时间戳
     * @return 签名
     */
    public static String getSign(String clientId,String signKey,long timestamp){
        String str = DigestUtils.md5Hex(DigestUtils.md5Hex(signKey + timestamp) + clientId) + timestamp;
        return Base64.encodeBase64String(str.getBytes());
    }
    
    /**
     * 培训地址加密
     * @param url
     * @return
     */
    public static String getBaseString(String url){
        return Base64.encodeBase64String(url.getBytes());
    }
    
}
