
package com.chmei.nzbcommon.util;

/**
 * ClassName:SystemUtil Function: TODO ADD FUNCTION. Date: 2018年8月28日 下午8:44:50
 * 
 * @author Lvcrosstime
 * @version
 * @since JDK 1.7
 */
public class SystemUtil {

	public static boolean isWindows() {
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {
			return true;
		}else {
			return false;
		}
	}

}
