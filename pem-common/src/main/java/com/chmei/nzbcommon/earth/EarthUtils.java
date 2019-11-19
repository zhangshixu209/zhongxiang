package com.chmei.nzbcommon.earth;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author lixinjie
 * @since 2018-05-30
 */
public class EarthUtils {

	public static final double RADIUS = 6370996.81;
	public static final NumberFormat NF = new DecimalFormat("#.##");
	
	/**
	 * <p>经度，纬度
	 * <p>计算两个经度纬度间的距离
	 * <p>经度纬度获取，http://api.map.baidu.com/lbsapi/getpoint/index.html
	 */
	public static final double computeDistance(double longitude1, double latitude1, double longitude2, double latitude2) {  
        double lat1 = (Math.PI / 180) * latitude1;
        double lon1 = (Math.PI / 180) * longitude1;
  
        double lat2 = (Math.PI / 180) * latitude2;
        double lon2 = (Math.PI / 180) * longitude2;
  
        return RADIUS * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1));  
    }
	
	/**
	 * 展示距离，米的单位，千米的单位
	 * <p>小于1000米显示为米（取整数）
	 * <p>大于1000米显示为千米（保留两位小数）
	 */
	public static final String displayDistance(double distance, String mUnit, String kmUnit) {
		if (distance < 1000) {
			return NF.format((long)distance) + mUnit;
		}
		return NF.format(distance / 1000) + kmUnit;
	}
}
