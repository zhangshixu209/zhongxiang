package com.chmei.nzbcommon.date;

/**
 * 普通循环的时间间隔
 * @author lixinjie
 * @since 2017-12-27
 */
public enum IntervalUnit {

	Second(1, "秒"),
	Minute(2, "分"),
	Hour(3, "时"),
	Day(4, "天"),
	Week(5, "周"),
	Month(6, "月"),
	Quarter(7, "季"),
	Year(8, "年");
	
	private int unit;
	private String desc;
	
	private IntervalUnit(int unit, String desc) {
		this.unit = unit;
		this.desc = desc;
	}

	public int getUnit() {
		return unit;
	}

	public String getDesc() {
		return desc;
	}
	
	public static IntervalUnit parse(int unit) {
		for (IntervalUnit iu : values()) {
			if (iu.unit == unit) {
				return iu;
			}
		}
		return null;
	}
}
