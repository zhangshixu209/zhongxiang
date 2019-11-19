package com.chmei.nzbcommon.calc;


import java.math.BigDecimal;

/**
 * 计算类 提供常用的运算
 * @ClassName:  Calc   
 * @author: liuzl
 * @date:   2018年12月6日 上午11:49:03   
 * @since JDK 1.7
 */
public class Calc {
	
	//默认保留2位小数
    private static final int DEF_SCALE = 4;

    //这个类不能实例化 
    private Calc() {
    }

    /** 
     * 提供精确的加法运算。 
     * @param v1 被加数 
     * @param v2 加数 
     * @return 两个参数的和 
     */
    public static String add(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).toString();
    }
    
    /**
     * 多个参数加法运算 参数如：v1,v2,v3,......
     * @param args
     * @return
     */
    public static String add(String... args) {
    	String result = "0";
    	for (String arg : args) {
    		result = add(arg, result);
    	}
    	return result;
    }

    /** 
     * 提供精确的减法运算。 
     * @param v1 被减数 
     * @param v2 减数 
     * @return 两个参数的差 
     */
    public static String sub(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);

        BigDecimal b2 = new BigDecimal(v2);

        return b1.subtract(b2).toString();
    }

    /** 
     * 提供精确的乘法运算 默认保留两位小数
     * @param v1 被乘数 
     * @param v2 乘数 
     * @return 两个参数的积 
     */
    public static String mul(String v1, String v2) {
        return mul(v1, v2, DEF_SCALE);
    }
    
    /**
     * 乘法运算 自定义小数位
     * @param v1 被乘数
     * @param v2 乘数
     * @param scale 保留小数位
     * @return
     */
    public static String mul(String v1,String v2,int scale) {
    	BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
    	return round(b1.multiply(b2).toString(), scale);
    }
    
    /**
     * 多个参数相乘 默认保留两位小数 参数如：v1,v2,v3,......
     * @param args
     * @return
     */
    public static String mul(String... args) {
    	String result = "1";
    	for (String arg : args) {
    		result = mul(arg, result);
    	}
    	return result;
    }

    /** 
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 
     * 小数点以后2位，以后的数字四舍五入。 
     * @param v1 被除数 
     * @param v2 除数 
     * @return 两个参数的商 
     */
    public static String div(String v1, String v2) {
        return div(v1, v2, DEF_SCALE);
    }

    /** 
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 
     * 定精度，以后的数字四舍五入。 
     * @param v1 被除数 
     * @param v2 除数 
     * @param scale 表示表示需要精确到小数点以后几位。 
     * @return 两个参数的商 
     */
    public static String div(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    /** 
     * 提供精确的小数位四舍五入处理。 
     * @param v 需要四舍五入的数字 
     * @param scale 小数点后保留几位 
     * @return 四舍五入后的结果 
     */
    public static String round(String v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString();
    }
    
    /**
     * 去除.00的数字
     * @param str
     * @return
     * @author liudong
     * @since 2018年12月7日下午5:47:43
     */
	public static String formatDecimal(String str) {
		if(str==null||str.isEmpty()){
			return "0";
		}
	    BigDecimal bg = new BigDecimal(str);
	    double num = bg.doubleValue();
	    if (Math.round(num) - num == 0) {
	        return String.valueOf((long) num);
	    }
	    return String.valueOf(num);
	}
    
}