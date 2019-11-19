package com.chmei.nzbcommon.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chmei.nzbcommon.exception.NzbCommonException;

public class Rsa {
	private static final  Logger LOGGER = LoggerFactory.getLogger(Rsa.class);
    public static class Keys {
        private String privateKey;
        private String publicKey;

        public Keys(String privateKey, String publicKey) {
            this.privateKey = privateKey;
            this.publicKey = publicKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }
    }

    public static class Generator {
        public static Keys generate() throws NzbCommonException{
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            try {
                KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
                generator.initialize(2048, new SecureRandom());
                KeyPair pair = generator.generateKeyPair();
                PublicKey publicKey = pair.getPublic();
                PrivateKey privateKey = pair.getPrivate();
                return new Keys(new String(Base64.encode(privateKey.getEncoded())), new String(Base64.encode(publicKey.getEncoded())));
            } catch (Exception e) {
            	LOGGER.error("", e.getMessage(), e);
    			throw new NzbCommonException(e.getMessage());
            }
        }
    }

    public static class Encoder {
        private PrivateKey mPrivateKey;
        private Cipher cipher;

        public Encoder(String privateKey) throws NzbCommonException{
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            PKCS8EncodedKeySpec privatePKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey.getBytes()));
            try {
                KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
                mPrivateKey = keyFactory.generatePrivate(privatePKCS8);
                cipher = Cipher.getInstance("RSA", "BC");
            } catch (Exception e) {
            	LOGGER.error("", e.getMessage(), e);
    			throw new NzbCommonException(e.getMessage());
            }
        }

        public String encode(String source) throws NzbCommonException{
            try {
                cipher.init(Cipher.ENCRYPT_MODE, mPrivateKey);
                byte[] cipherText = cipher.doFinal(source.getBytes("utf-8"));
                return new String(Base64.encode(cipherText));
            } catch (Exception e) {
            	LOGGER.error("", e.getMessage(), e);
    			throw new NzbCommonException(e.getMessage());
            }
        }
    }

    public static class Decoder {
        private PublicKey mPublicKey;
        private Cipher cipher;

        public Decoder(String publicKey) throws NzbCommonException{
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            X509EncodedKeySpec publicX509 = new X509EncodedKeySpec(Base64.decode(publicKey.getBytes()));
            try {
                KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
                mPublicKey = keyFactory.generatePublic(publicX509);
                cipher = Cipher.getInstance("RSA", "BC");
            } catch (Exception e) {
            	LOGGER.error("", e.getMessage(), e);
    			throw new NzbCommonException(e.getMessage());
            }
        }

        public String decode(String source) throws NzbCommonException{
            try {
                cipher.init(Cipher.DECRYPT_MODE, mPublicKey);
                byte[] output = cipher.doFinal(Base64.decode(source.getBytes()));
                return new String(output, "utf-8");
            } catch (Exception e) {
            	LOGGER.error("", e.getMessage(), e);
    			throw new NzbCommonException(e.getMessage());
            }
        }
    }
    
}
