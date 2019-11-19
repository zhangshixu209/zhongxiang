package org.eop.common.json;

import com.chmei.nzbcommon.json.JsonBuilder;

/**
 * @author lixinjie
 * @since 2018-07-10
 */
public class JsonBuilderTest {

	static void test1() {
		JsonBuilder jb = new JsonBuilder();
		jb.kv("string", "公众号：编程新说")
			.kv("int", 33)
			.kv("boolean", true)
			.kv("double", 3.14)
			.kv("null", null)
			.ko("sub1")
				.kv("string", "公众号：编程新说")
				.kv("int", 33)
				.kv("boolean", true)
				.kv("double", 3.14)
				.kv("null", null)
			.end()
			.ka("sub2")
				.io()
					.kv("string", "公众号：编程新说")
					.kv("int", 33)
					.kv("boolean", true)
					.kv("double", 3.14)
					.kv("null", null)
				.end()
				.io()
					.kv("string", "公众号：编程新说")
					.kv("int", 33)
					.kv("boolean", true)
					.kv("double", 3.14)
					.kv("null", null)
				.end()
			.end()
			.ka("sub3")
				.iv("公众号：编程新说")
				.iv(33)
				.iv(true)
				.iv(3.14)
				.iv(null)
			.end();
		System.out.println(jb.toJson());
		System.out.println(jb.toJson(true));
	}
	
	public static void main(String[] args) {
		test1();
	}

}
