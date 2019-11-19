package org.eop.common.random;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author lixinjie
 * @since 2018-07-20
 */
public class RandomTest {

	static void test1() {
		System.out.println(new Random().nextInt(1000));
		System.out.println(new Random().nextInt(1000));
		System.out.println(new Random().nextInt(1000));
		System.out.println(new Random().nextInt(1000));
	}
	
	static void test2() {
		System.out.println(ThreadLocalRandom.current().nextInt(1000));
		System.out.println(ThreadLocalRandom.current().nextInt(1000));
		System.out.println(ThreadLocalRandom.current().nextInt(1000));
		System.out.println(ThreadLocalRandom.current().nextInt(1000));
	}
	
	public static void main(String[] args) {
		test1();
		test2();
	}

}
