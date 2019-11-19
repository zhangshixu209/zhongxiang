package com.chmei.nzbcommon.util;



import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chmei.nzbcommon.exception.NzbCommonException;

public class TicketDesUtil{
	
	private static final Logger log = LoggerFactory.getLogger(TicketDesUtil.class);
    
    private static final String Algorithm = "DESede"; //定义加密算法,可用 DES,DESede,Blowfish  

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp;
        for (int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n < b.length - 1)
                hs = hs;
        }
        return hs.toLowerCase();
    }

    public static byte[] hexStr2Bytes(String src) {
        int m ;
        int n ;
        int l = src.length() / 2;

        byte[] ret = new byte[l];
        for (int i = 0; i < l; ++i) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
        }
        return ret;
    }

    public static byte uniteBytes(String src0, String src1) {
        byte b0 = Byte.decode("0x" + src0).byteValue();
        b0 = (byte) (b0 << 4);
        byte b1 = Byte.decode("0x" + src1).byteValue();
        byte ret = (byte) (b0 | b1);
        return ret;
    }

    public static String encrypt(String source, String key) throws NzbCommonException {
        if(key==null||key.length()<24){
        	throw new NzbCommonException("key length must be 24 !");
        } 
        byte[] b = encrypt(source.getBytes(), key.substring(0, 24).getBytes());
        return byte2hex(b);
    }

    public static String decrypt(String ciphertext, String key) throws NzbCommonException {
        if(key==null||key.length()<24) {
        	throw new NzbCommonException("key length must be 24 !");
        }
        	
        byte[] b = decrypt(hexStr2Bytes(ciphertext), key.substring(0, 24).getBytes());
        if(null != b){
            return new String(b);
        }
        return null;
    }
  
    public static byte[] encrypt(byte[] src,byte[] keybyte){  
         try {  
            //生成密钥  
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);  
            //加密  
            Cipher c1 = Cipher.getInstance(Algorithm);  
            c1.init(Cipher.ENCRYPT_MODE, deskey);  
            return c1.doFinal(src);//在单一方面的加密或解密  
        } catch (java.security.NoSuchAlgorithmException e1) { 
        	log.error("NoSuchAlgorithmException"+e1);
        }catch(javax.crypto.NoSuchPaddingException e2){  
        	log.error("NoSuchPaddingException"+e2);
        }catch(java.lang.Exception e3){
        	log.error("Exception"+e3);
        }  
        return null;  
    }  
      

    public static byte[] decrypt(byte[] src,byte[] keybyte){  
        try {  
            //生成密钥  
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);  
            //解密  
            Cipher c1 = Cipher.getInstance(Algorithm);  
            c1.init(Cipher.DECRYPT_MODE, deskey);  
            return c1.doFinal(src);  
        } catch (java.security.NoSuchAlgorithmException e1) {  
        	log.error("NoSuchAlgorithmException"+e1);
        }catch(javax.crypto.NoSuchPaddingException e2){  
        	log.error("NoSuchPaddingException"+e2);
        }catch(java.lang.Exception e3){  
        	log.error("Exception"+e3); 
        }  
        return null;          
    }  
}
