package org.eop.common.xml;

import com.chmei.nzbcommon.xml.XmlBuilder;

/**
 * @author lixinjie
 * @since 2018-07-18
 */
public class XmlBuilderTest {

	static void test1() {
		XmlBuilder xb = new XmlBuilder();
		xb.root("xml")
			.text("string", "公众号：编程新说")
			.text("int", 33)
			.text("boolean", true)
			.text("double", 3.14)
			.text("null", null)
			.element("sub1")
				.text("string", "公众号：编程新说")
				.text("int", 33)
				.text("boolean", true)
				.text("double", 3.14)
				.text("null", null)
			.end()
			.element("sub2")
				.text("string", "公众号：编程新说")
				.text("int", 33)
				.text("boolean", true)
				.text("double", 3.14)
				.text("null", null)
			.end()
			.element("sub2")
				.text("string", "公众号：编程新说")
				.text("int", 33)
				.text("boolean", true)
				.text("double", 3.14)
				.text("null", null)
			.end()
			.texts("sub3", new Object[] {"公众号：编程新说", 33, true, 3.14, null});
		System.out.println(xb.toXml());
		System.out.println(xb.toXml(true));
	}
	
	public static void main(String[] args) {
		test1();
	}

}
