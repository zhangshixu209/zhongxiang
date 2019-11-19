package com.chmei.nzbcommon.phone;

/**
 * <p>手机号变换器
 * <p>将手机号混乱、还原
 * @author lixinjie
 * @since 2018-04-17
 */
public final class PhoneInverter {

	/**
	 * 混乱手机号（相当于加密）
	 */
	public static String messPhone(String phone) {
		char[] chs = phone.toCharArray();
		int len = chs.length;
		char[] mchs = new char[len];
		int half = len / 2;
		int index = 0;
		for (int i = 0; i < half; i++) {
			if (i % 2 == 0) {
				mchs[index++] = chs[len - i - 1];
				mchs[index++] = chs[i];
			} else {
				mchs[index++] = chs[i];
				mchs[index++] = chs[len - i - 1];
			}
		}
		if (len % 2 == 1) {
			mchs[index] = chs[half];
		}
		return new String(mchs);
	}
	
	/**
	 * 还原手机号（相当于解密）
	 */
	public static String recoverPhone(String messPhone) {
		char[] mchs = messPhone.toCharArray();
		int len = mchs.length;
		char[] chs = new char[len];
		int half = len / 2;
		int index = 0;
		for (int i = 0; i < half; i++) {
			if (i % 2 == 0) {
				chs[len - i - 1] = mchs[index++];
				chs[i] = mchs[index++];
			} else {
				chs[i] = mchs[index++];
				chs[len - i - 1] = mchs[index++];
			}
		}
		if (len % 2 == 1) {
			chs[half] = mchs[index];
		}
		return new String(chs);
	}
	
}
