package com.chmei.nzbcommon.util;

import java.io.UnsupportedEncodingException;

/**
 * 16进制与String/Byte之间的转换工具类
 * @author fengwei
 */
public class HexUtil {
	public static final String  CODE="0123456789ABCDEF";
	private HexUtil(){}

	/**
	 * 字符串转换成16进制字符串
	 * @param str 待转换的ASCII字符串
	 * @return 转换后的16进制字符串，Byte之间无分隔符，如：[616C6B]
	 */
	public static String str2Hex(String str) {
		char[] hexChars = CODE.toCharArray();
		StringBuilder sb = new StringBuilder();
		byte[] bs = str.getBytes();
		int bit;
		for(int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0xf0) >> 4;
			sb.append(hexChars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(hexChars[bit]);
		}
		return sb.toString().trim();
	}
	
	/**
	 * 字符串转换成16进制字符串,字符串按制定字符集解码
	 * @param str 待转换的ASCII字符串
	 * @param encode
	 * @return 转换后的16进制字符串，Byte之间无分隔符，如：[616C6B]
	 * @throws UnsupportedEncodingException 
	 */
	public static String str2Hex(String str, String encode) 
			throws UnsupportedEncodingException {
		char[] hexChars = CODE.toCharArray();
		StringBuilder sb = new StringBuilder();
		byte[] bs = str.getBytes(encode);
		int bit;
		for(int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0xf0) >> 4;
			sb.append(hexChars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(hexChars[bit]);
		}
		return sb.toString().trim();
	}
	
	/**
	 * 16进制字符串转换为字符串
	 * @param hexStr 待转换的16进制字符串，Byte之间无分隔符，如[616C6B]
	 * @return 转换后的字符串
	 */
	public static String hex2Str(String hexStr) {
		char[] hexChars = hexStr.toCharArray();
		byte[] bs = new byte[hexStr.length() / 2];
		int n;
		for(int i = 0; i < bs.length; i++) {
			n = CODE.indexOf(hexChars[2 * i]) << 4;
			n += CODE.indexOf(hexChars[2 * i + 1]);
			bs[i] = (byte)(n & 0xff);
		}
		return new String(bs);
	}
	
	/**
	 * Byte数组转换为16进制字符串,
	 * 每interval个字节之间以split分隔,
	 * 每行lineSize个字节
	 * @param bs 待转换Byte数组
	 * @param split 分隔符
	 * @param interval 字节间隔数
	 * @param lineSize 多少个字节换行
	 * @return 转换后的16进制字符串
	 */
	public static String bytes2Hex(byte[] bs, String split, int interval, int lineSize) {
		String temp ;
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < bs.length; i++) {
			temp = Integer.toHexString(bs[i] & 0xff);
			sb.append((temp.length() == 1) ? "0" + temp : temp);
			if(i != bs.length - 1 && i % interval == 0) {
				sb.append(split);
			}
			if(lineSize != 0 && (i + 1) % lineSize == 0) {
				sb.append("\n");
			}
		}
		return sb.toString().toUpperCase().trim();
	}

	/**
	 * Byte数组转换为16进制字符串,字节间无分隔符
	 * @param bs 待转换Byte数组
	 * @return 转换后的16进制字符串
	 */
	public static String bytes2Hex(byte[] bs) {
		return bytes2Hex(bs, "", 1, 0);
	}
	
	/**
	 * Byte数组转换为16进制字符串,每个字节间以split分隔
	 * @param bs 待转换Byte数组
	 * @param split 分隔符
	 * @return 转换后的16进制字符串
	 */
	public static String bytes2Hex(byte[] bs, String split) {
		return bytes2Hex(bs, split, 1, 0);
	}
	
	/**
	 * 16进制字符串转换为Byte数组
	 * @param hexStr 待转换的16进制字符串，Byte之间没有分隔符
	 * @return 转换后的Byte数组
	 */
	public static byte[] hex2Bytes(String hexStr) {
		byte[] bs = new byte[hexStr.length() / 2];
		for(int i = 0; i < bs.length; i++) {
			bs[i] = Byte.decode("0x" + 
			hexStr.substring(i * 2, i * 2 + 1) + hexStr.substring(i * 2 + 1, (i +1) * 2 ));
		}
		return bs;
	}

}
