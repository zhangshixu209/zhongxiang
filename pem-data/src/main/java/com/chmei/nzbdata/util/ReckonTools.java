package com.chmei.nzbdata.util;

import java.math.BigDecimal;

/**
 * 计算工具常用类
 * 
 * @ClassName: ReckonTools
 * @Description: TODO
 * @author 翟超锋 mail - 357111361@qq.com
 * @since 2019年4月3日 下午3:49:08
 *
 */
public class ReckonTools {

    /**
     * @Title: calculateResult 
     * @Description: 两个Double想成并四舍五入
     * @param a 参数
     * @param b 参数
     * @return String 值
     */
    public static String calculateResult(Double a, Double b) {
        Double c = a * b * 0.01;
        BigDecimal bd = new BigDecimal(c);
        double f1 = bd.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(f1);
    }

    /**
     * @Title: addTo 
     * @Description: 两个Double相加结果->String 返回  
     * @param str 参数
     * @param str1 参数
     * @return String 值
     */
    public static String addTo(String str, String str1) {
        Double d = Double.valueOf(str);
        Double d1 = Double.valueOf(str1);
        Double d2 = d + d1;
        return String.valueOf(d2);
    }

    /**
     * @Title: str2Subtract 
     * @Description: 两个Double相减结果->String 返回  
     * @param str 参数
     * @param str1 参数
     * @return String 值
     */
    public static String str2Subtract(String str, String str1) {
        Double d = Double.valueOf(str);
        Double d1 = Double.valueOf(str1);
        Double d2 = d - d1;
        BigDecimal bg = new BigDecimal(d2);
        double f1 = bg.setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(f1);
    }

    /**
     * @Title: str2Subtract 
     * @Description: 两个Double相减并保留小数位结果->String 返回  
     * @param str 参数
     * @param str1 参数
     * @param num 参数
     * @return String 值
     */
    public static String str2Subtract(String str, String str1, int num) {
        Double d = Double.valueOf(str);
        Double d1 = Double.valueOf(str1);
        Double d2 = d - d1;
        BigDecimal bg = new BigDecimal(d2);
        double f1 = bg.setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(f1);
    }

    
    /**
     * @Title: str2Multiply 
     * @Description: 两个字符串相乘保留两位小数、四舍五入后返回字符串
     * @param str 参数
     * @param str1 参数
     * @return String 值
     */
    public static String str2Multiply(String str, String str1) {
        Double d = Double.valueOf(str);
        Double d1 = Double.valueOf(str1);
        Double c = d * d1 * 0.01;
        BigDecimal bd = new BigDecimal(c);
        double f1 = bd.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(f1);
    }

    
    /**
     * @Title: str2Multiply 
     * @Description:  两个字符串相乘保留两位小数、四舍五入后返回字符串 
     * @param str 参数
     * @param str1 参数
     * @param num 参数
     * @param flag 参数
     * @return String 值
     */
    public static String str2Multiply(String str, String str1, int num, boolean flag) {
        Double d = Double.valueOf(str);
        Double d1 = Double.valueOf(str1);
        Double c = d * d1 * (flag ? 0.01 : 1);
        BigDecimal bd = new BigDecimal(c);
        double f1 = bd.setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(f1);
    }

    /**
     * @Title: multiply2BigDecimal 
     * @Description:两个相乘再除以0.01
     * @param str 参数
     * @param str1 参数
     * @return String 值
     */
    public static String multiply2BigDecimal(String str, String str1) {
        BigDecimal b = new BigDecimal(str);
        BigDecimal b1 = new BigDecimal(str1);
        BigDecimal b2 = new BigDecimal("0.01");
        BigDecimal b3 = b.multiply(b1).multiply(b2);
        return b3.toString();
    }

    /**
     * @Title: multiply2BigDecimal 
     * @Description:两个相乘再保留小数
     * @param str 参数
     * @param str1 参数
     * @param num 参数
     * @return String 值
     */
    public static String multiply2BigDecimal(String str, String str1, int num) {
        BigDecimal b = new BigDecimal(str);
        BigDecimal b1 = new BigDecimal(str1);
        // BigDecimal b2 = new BigDecimal("0.01");
        BigDecimal b3 = b.multiply(b1).setScale(num, BigDecimal.ROUND_HALF_UP);
        return b3.toString();
    }

    /**
     * @Title: multiply2BigDecimals 
     * @Description:两个相乘
     * @param str 参数
     * @param str1 参数
     * @return String 值
     */
    public static String multiply2BigDecimals(String str, String str1) {
        BigDecimal b = new BigDecimal(str);
        BigDecimal b1 = new BigDecimal(str1);
        BigDecimal b3 = b.multiply(b1);
        return b3.toString();
    }

    /**
     * @Title: multiply2BigDecimalNum 
     * @Description:两个相乘并保留一定小数位 
     * @param str 参数
     * @param str1 参数
     * @param num 参数
     * @return String 值
     */
    public static String multiply2BigDecimalNum(String str, String str1, int num) {
        BigDecimal b = new BigDecimal(str);
        BigDecimal b1 = new BigDecimal(str1);
        BigDecimal b3 = b.multiply(b1).setScale(num, BigDecimal.ROUND_HALF_UP);
        return b3.toString();
    }

    /**
     * @Title: divide2BigDecimal 
     * @Description:两数相除 默认 10位小数并四舍五入
     * @param str 参数
     * @param str1 参数
     * @return String 值
     */
    public static String divide2BigDecimal(String str, String str1) {
        BigDecimal b = new BigDecimal(str);
        BigDecimal b1 = new BigDecimal(str1);
        BigDecimal b2 = b.divide(b1, 10, BigDecimal.ROUND_HALF_UP);
        return b2.toString();
    }

    /**
     * @Title: divide2BigDecimal 
     * @Description:两数相除
     * @param str 参数
     * @param str1 参数
     * @param scal 参数
     * @return String 值
     */
    public static String divide2BigDecimal(String str, String str1, Integer scal) {
        BigDecimal b = new BigDecimal(str);
        BigDecimal b1 = new BigDecimal(str1);
        BigDecimal b2 = b.divide(b1, scal, BigDecimal.ROUND_HALF_UP);
        return b2.toString();
    }

    /**
     * @Title: divide2BigDecimal2 
     * @Description:两数相除 默认保留两位小数 
     * @param str 参数
     * @param str1 参数
     * @return String 值
     */
    public static String divide2BigDecimal2(String str, String str1) {
        return divide2BigDecimal(str, str1, 2);
    }

    /**
     * @Title: add2BigDecimal 
     * @Description:判断两个参数是否是空值
     * @param str 参数
     * @param str1 参数
     * @return String 值
     */
    public static String add2BigDecimal(String str, String str1) {
        if (str == null || str.equals("") || str.equals("null")) {
            str = "0";
        }
        if (str1 == null || str1.equals("") || str1.equals("null")) {
            str1 = "0";
        }
        BigDecimal b = new BigDecimal(str);
        BigDecimal b1 = new BigDecimal(str1);
        BigDecimal b2 = b.add(b1);
        double f1 = b2.setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(f1);
    }

    /**
     * @Title: add2BigDecimalNonull 
     * @Description: 两个数相加保留NUM位小数并判断null转换
     * @param str 参数
     * @param str1 参数
     * @param num 参数
     * @return String 值
     */
    public static String add2BigDecimalNonull(String str, String str1, Integer num) {
        if (str == null || str.equals("") || str.equals("null")) {
            str = "0";
        }
        if (str1 == null || str1.equals("") || str1.equals("null")) {
            str1 = "0";
        }
        return add2BigDecimal(str, str1, num);
    }


    /**
     * @Title: add2BigDecimal 
     * @Description: 两个数相加保留NUM位小数
     * @param str 参数
     * @param str1 参数
     * @param num 参数
     * @return String 值
     */
    public static String add2BigDecimal(String str, String str1, Integer num) {
        BigDecimal b = new BigDecimal(str);
        BigDecimal b1 = new BigDecimal(str1);
        BigDecimal b2 = b.add(b1);
        // String s =
        // b2.stripTrailingZeros().toPlainString();//让bigdecimal不用科学计数法显示
        String f1 = b2.setScale(num, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
        return String.valueOf(f1);
    }

    
    /**
     * @Title: add2BigDecimals 
     * @Description: 两个数相加不保留小数
     * @param str 参数
     * @param str1 参数
     * @return String 值
     */
    public static String add2BigDecimals(String str, String str1) {
        BigDecimal b = new BigDecimal(str);
        BigDecimal b1 = new BigDecimal(str1);
        BigDecimal b2 = b.add(b1);
        BigDecimal bd = new BigDecimal(b2 + "");
        double f1 = bd.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(f1);
    }


    /**
     * @Title: add2BigDecimal1 
     * @Description: 两个数相加保留1位小数
     * @param str 参数
     * @param str1 参数
     * @return String 值
     */
    public static String add2BigDecimal1(String str, String str1) {
        BigDecimal b = new BigDecimal(str);
        BigDecimal b1 = new BigDecimal(str1);
        BigDecimal b2 = b.add(b1);
        double f1 = b2.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(f1);
    }

    
    /**
     * @Title: str8mitor 
     * @Description: 字符串并四舍五入并保留8位小数
     * @param str 参数
     * @return String 值
     */
    public static String str8mitor(String str) {
        BigDecimal b = new BigDecimal(str);
        double f1 = b.setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(f1);
    }


    /**
     * @Title: str2mitor 
     * @Description: 字符串并四舍五入并保留2位小数 
     * @param str 参数
     * @return String 值
     */
    public static String str2mitor(String str) {
        if (str == null || str.trim().equals("null") || str.trim().equals("")) {
            return "0";
        }
        BigDecimal b = new BigDecimal(str);
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(f1);
    }

   
    /**
     * @Title: comToBigDecimal 
     * @Description: 判断一个BigDecimal是否小于0
     * @param str 参数
     * @return boolean 值
     */
    public static boolean comToBigDecimal(String str) {
        BigDecimal amt = new BigDecimal(str);
        int i = amt.compareTo(BigDecimal.ZERO);
        if (i == -1) {// amt小于0 例如：amt=-10.00
            return false;
        }
        return true;
    }
    

    /**
     * @Title: absBigDecimal 
     * @Description: 获取一个字符串的绝对值
     * @param str 参数
     * @return String 值
     */
    public static String absBigDecimal(String str) {
        BigDecimal bd2 = new BigDecimal(str);
        String s = bd2.abs().toString();
        return s;
    }

}
