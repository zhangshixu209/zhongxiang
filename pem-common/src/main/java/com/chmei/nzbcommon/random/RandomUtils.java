package com.chmei.nzbcommon.random;

import java.util.Random;

/**
 * <p>随机数
 * @author lixinjie
 * @since 2018-01-22
 */
public class RandomUtils {
	
	/**
	 * 随机整数
	 */
	public static final int randomInt(int lowerBound, int upperBound) {
		return new Random().nextInt(upperBound - lowerBound) + lowerBound;
		
	}
	
	/**
	 * 随机数字字符串
	 */
	public static final String randomDigit(int len) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
	
	/**
	 * <p>随机字母字符串
	 * <p>upperLowerCase, true is uppercase, false is lowercase, null is mixed
	 */
	public static final String randomLetter(int len, Boolean upperLowerCase) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		if (upperLowerCase == null) {
			for (int i = 0; i < len; i++) {
				sb.append(MIXEDCASE_ALPHABET[random.nextInt(52)]);
			}
		} else if (upperLowerCase) {
			for (int i = 0; i < len; i++) {
				sb.append(UPPERCASE_ALPHABET[random.nextInt(26)]);
			}
		} else {
			for (int i = 0; i < len; i++) {
				sb.append(LOWERCASE_ALPHABET[random.nextInt(26)]);
			}
		}
		return sb.toString();
	}
	
	/**
	 * <p>随机数字字母字符串
	 * <p>upperLowerCase, true is uppercase, false is lowercase, null is mixed
	 */
	public static final String randomString(int len, Boolean upperLowerCase) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		if (upperLowerCase == null) {
			for (int i = 0; i < len; i++) {
				sb.append(DIGIT_MIXEDCASE_CHARS[random.nextInt(62)]);
			}
		} else if (upperLowerCase) {
			for (int i = 0; i < len; i++) {
				sb.append(DIGIT_UPPERCASE_CHARS[random.nextInt(36)]);
			}
		} else {
			for (int i = 0; i < len; i++) {
				sb.append(DIGIT_LOWERCASE_CHARS[random.nextInt(36)]);
			}
		}
		return sb.toString();
	}

	private static final char[] LOWERCASE_ALPHABET = {
			'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n',
			'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z'};
	
	private static final char[] UPPERCASE_ALPHABET = {
			'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N',
			'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z'};
	
	private static final char[] MIXEDCASE_ALPHABET = {
			'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n',
			'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z',
			'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N',
			'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z'};
	
	private static final char[] DIGIT_LOWERCASE_CHARS = {
			'0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n',
			'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z'};
	
	private static final char[] DIGIT_UPPERCASE_CHARS = {
			'0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N',
			'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z'};
	
	private static final char[] DIGIT_MIXEDCASE_CHARS = {
			'0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n',
			'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z',
			'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N',
			'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z'};
}
