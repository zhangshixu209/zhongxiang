package com.chmei.nzbcommon.util;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 *SHA1
 */
public class SHA1 {

	private static final Logger LOGGER = LoggerFactory.getLogger(SHA1.class);

	 private final int[] abcde;
	  private int[] digestInt;
	  private int[] tmpData;

	  /**SHA1
	   * public constructor.
	   */
	  public SHA1()
	  {
	    this.abcde = new int[] { 1732584193, -271733879, -1732584194, 271733878, -1009589776 };
	    this.digestInt = new int[5]; 
	    this.tmpData = new int[80];
	  }

	  private int process_input_bytes(byte[] bytedata)
	  {
	    System.arraycopy(this.abcde, 0, this.digestInt, 0, this.abcde.length);

	    byte[] newbyte = byteArrayFormatData(bytedata);

	    int MCount = newbyte.length / 64;

	    for (int pos = 0; pos < MCount; ++pos)
	    {
	      for (int j = 0; j < 16; ++j) {
	        this.tmpData[j] = byteArrayToInt(newbyte, pos * 64 + j * 4);
	      }

	      encrypt();
	    }
	    return 20;
	  }

	  private byte[] byteArrayFormatData(byte[] bytedata)
	  {
	    int zeros ;

	    int size ;

	    int n = bytedata.length;

	    int m = n % 64;

	    if (m < 56) {
	      zeros = 55 - m;
	      size = n - m + 64;
	    } else if (m == 56) {
	      zeros = 63;
	      size = n + 8 + 64;
	    } else {
	      zeros = 63 - m + 56;
	      size = n + 64 - m + 64;
	    }

	    byte[] newbyte = new byte[size];

	    System.arraycopy(bytedata, 0, newbyte, 0, n);

	   
	    long N = n * 8L;
	    newbyte[n++] = -128;

	    for (int i = 0; i < zeros; ++i) {
	      newbyte[n++] = 0;
	    }

	 
	    byte h8 = (byte)(int)(N & 0xFF);
	    byte h7 = (byte)(int)(N >> 8 & 0xFF);
	    byte h6 = (byte)(int)(N >> 16 & 0xFF);
	    byte h5 = (byte)(int)(N >> 24 & 0xFF);
	    byte h4 = (byte)(int)(N >> 32 & 0xFF);
	    byte h3 = (byte)(int)(N >> 40 & 0xFF);
	    byte h2 = (byte)(int)(N >> 48 & 0xFF);
	    byte h1 = (byte)(int)(N >> 56);
	    newbyte[n++] = h1;
	    newbyte[n++] = h2;
	    newbyte[n++] = h3;
	    newbyte[n++] = h4;
	    newbyte[n++] = h5;
	    newbyte[n++] = h6;
	    newbyte[n++] = h7;
	    newbyte[n] = h8;
	    return newbyte;
	  }

	  private int f1(int x, int y, int z) {
	    return (x & y | (x ^ 0xFFFFFFFF) & z);
	  }

	  private int f2(int x, int y, int z) {
	    return x ^ y ^ z;
	  }

	  private int f3(int x, int y, int z) {
	    return x & y | x & z | y & z;
	  }

	  private int f4(int x, int y) {
	    return x << y | x >>> 32 - y;
	  }

	  private void encrypt()
	  {
	    for (int i = 16; i <= 79; ++i) {
	      this.tmpData[i] = 
	        f4(this.tmpData[i - 3] ^ this.tmpData[i - 8] ^ this.tmpData[i - 14] ^ 
	        this.tmpData[i - 16], 1);
	    }
	    int[] tmpabcde = new int[5];
	    for (int i1 = 0; i1 < tmpabcde.length; ++i1)
	      tmpabcde[i1] = this.digestInt[i1];
	    int tmp;
	    for (int j = 0; j <= 19; ++j) {
	      tmp = f4(tmpabcde[0], 5) + 
	        f1(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + 
	        tmpabcde[4] + 
	        this.tmpData[j] + 
	        1518500249;
	      tmpabcde[4] = tmpabcde[3];
	      tmpabcde[3] = tmpabcde[2];
	      tmpabcde[2] = f4(tmpabcde[1], 30);
	      tmpabcde[1] = tmpabcde[0];
	      tmpabcde[0] = tmp;
	    }
	    for (int k = 20; k <= 39; ++k) {
	      tmp = f4(tmpabcde[0], 5) + 
	        f2(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + 
	        tmpabcde[4] + 
	        this.tmpData[k] + 
	        1859775393;
	      tmpabcde[4] = tmpabcde[3];
	      tmpabcde[3] = tmpabcde[2];
	      tmpabcde[2] = f4(tmpabcde[1], 30);
	      tmpabcde[1] = tmpabcde[0];
	      tmpabcde[0] = tmp;
	    }
	    for (int l = 40; l <= 59; ++l) {
	      tmp = f4(tmpabcde[0], 5) + 
	        f3(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + 
	        tmpabcde[4] + 
	        this.tmpData[l] + 
	        -1894007588;
	      tmpabcde[4] = tmpabcde[3];
	      tmpabcde[3] = tmpabcde[2];
	      tmpabcde[2] = f4(tmpabcde[1], 30);
	      tmpabcde[1] = tmpabcde[0];
	      tmpabcde[0] = tmp;
	    }
	    for (int m = 60; m <= 79; ++m) {
	      tmp = f4(tmpabcde[0], 5) + 
	        f2(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + 
	        tmpabcde[4] + 
	        this.tmpData[m] + 
	        -899497514;
	      tmpabcde[4] = tmpabcde[3];
	      tmpabcde[3] = tmpabcde[2];
	      tmpabcde[2] = f4(tmpabcde[1], 30);
	      tmpabcde[1] = tmpabcde[0];
	      tmpabcde[0] = tmp;
	    }
	    for (int i2 = 0; i2 < tmpabcde.length; ++i2) {
	      this.digestInt[i2] += tmpabcde[i2];
	    }
	    for (int n = 0; n < this.tmpData.length; ++n)
	      this.tmpData[n] = 0;
	  }

	  private int byteArrayToInt(byte[] bytedata, int i)
	  {
	    return (bytedata[i] & 0xFF) << 24 | (bytedata[i + 1] & 0xFF) << 16 | 
	      (bytedata[i + 2] & 0xFF) << 8 | 
	      bytedata[i + 3] & 0xFF;
	  }

	  private void intToByteArray(int intValue, byte[] byteData, int i)
	  {
	    byteData[i] = (byte)(intValue >>> 24);
	    byteData[i + 1] = (byte)(intValue >>> 16);
	    byteData[i + 2] = (byte)(intValue >>> 8);
	    byteData[i + 3] = (byte)intValue;
	  }

	  private static String byteToHexString(byte ib)
	  {
	    char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 
	      'B', 'C', 'D', 'E', 'F' };
	    char[] ob = new char[2];
	    ob[0] = digit[ib >>> 4 & 0xF];
	    ob[1] = digit[ib & 0xF];
	    return new String(ob);
	  }

	  private static String byteArrayToHexString(byte[] bytearray)
	  {
	    StringBuilder strDigest = new StringBuilder();
	    for (int i = 0; i < bytearray.length; ++i) {
	    	strDigest.append(byteToHexString(bytearray[i])) ;
	    }
	    return strDigest.toString();
	  }

	  /**
	   * 
	   * @param byteData
	   * @return
	   */
	  public byte[] getDigestOfBytes(byte[] byteData)
	  {
	    process_input_bytes(byteData);
	    byte[] digest = new byte[20];
	    for (int i = 0; i < this.digestInt.length; ++i) {
	      intToByteArray(this.digestInt[i], digest, i * 4);
	    }
	    return digest;
	  }

	  /**
	   * 
	   * @param byteData
	   * @return
	   */
	  public String getDigestOfString(byte[] byteData)
	  {
	    return byteArrayToHexString(getDigestOfBytes(byteData));
	  }

	  /**
	   * 
	   * @param data
	   * @return
	   */
	  public String digest(String data)
	  {
	    return getDigestOfString(data.getBytes());
	  }

	  public String digest(String data, String encode)
	  {
	    try
	    {
	      return getDigestOfString(data.getBytes(encode)); } catch (UnsupportedEncodingException e) {
	    	  LOGGER.error("digest", e.getMessage(), e);
	    }
	    return digest(data);
	  }

	  /**
	   * 
	   * @param text
	   * @return
	   */
	  public static String SHA1Digest(String text)
	  {
	    String str="" ;
	    try {
	      MessageDigest md = MessageDigest.getInstance("SHA1");
	      md.update(text.getBytes());
	      str = new BigInteger(1, md.digest()).toString(16);
	    } catch (NoSuchAlgorithmException e) {
	    	LOGGER.error("SHA1Digest", e.getMessage(), e);
	    }
	    return str.toUpperCase();
	  }


}