package com.chmei.nzbcommon.reportcommon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SQL获取字段类型和详情信息(支持select *, select xx,xx)
 * 
 * @author ZhangShiXu
 * @since 2019年8月7日 14点48分
 */
public class SQLResultSetUtils {

    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SQLResultSetUtils.class);
    
    /**
     * 使用SQL获取字段类型和详情信息
     * @param url 数据库链接地址
     * @param user 用户名
     * @param password 密码
     * @param type 1 JDBC 2 ODBC
     * @param sql 查询SQL
     * @param dataBasees 数据库类型
     * @return 返回list
     */
    public static List<Object> SQLValidation(String url, String user, String password, int type, String sql,
            String dataBasees) {
        // 根据入参查询整张表的字段信息
        List<Object> listCol = TableUtils.SQLValidation(url, user, password, 
                type, sql, dataBasees);
        List<Object> listArr = new ArrayList<Object>(); // 存储最终结果
        // 判断查询的总数据是否为空
        if (null != listCol && listCol.size() > 0) {
            // 截取SELECT FROM
            List<String> subList = mergeSubSql(sql.toLowerCase());
            if (null != subList && !subList.isEmpty()) {
                if ("*".equals(subList.get(0))) { // 截取为*时查询，直接返回所有数据
                    return listCol;
                }
                listArr = contaColList(listCol, subList); // 调用字段信息比对结果list
            } else {
                LOGGER.error("SQL截取字段失败 !");
            }
        } else {
            LOGGER.error("SQL解析失败，请重新检查SQL");
        }
        
        return listArr;
    }
    
    /**
     * 字段信息比对结果
     * @param listCol 所有字段信息
     * @param subList 截取字段信息
     * @return 返回list
     */
    public static List<Object> contaColList(List<Object> listCol, List<String> subList) {
        // 存储最终结果
        List<Object> listArr = new ArrayList<Object>();
        // 循环所有字段信息
        for (int i = 0; i < listCol.size(); i++) {
            @SuppressWarnings("unchecked")
            List<T> colList = (List<T>) listCol.get(i);
            String[] array = (String[]) colList.toArray(new String[colList.size()]);
            // 截取字段进行循环对比
            for (String str : subList) {
                if (str.indexOf("@@") != -1) { // 判断是否带有AS别名
                    String subID = str.substring(0, str.indexOf("@@")); // 截取@@之前的字符串
                    boolean flag = Arrays.asList(array).contains(subID);
                    if (flag) {
                        String subName = str.substring(str.lastIndexOf("@@") + 2);
                        array[3] = subName; // 使用自定义AS的字段别名
                        List<String> arrList = new ArrayList<>(Arrays.asList(array));
                        listArr.add(arrList);
                    }
                } else {
                    // 没有自定义AS别名字段信息对比
                    boolean flag = Arrays.asList(array).contains(str.trim());
                    if (flag) {
                        List<String> arrList = new ArrayList<>(Arrays.asList(array));
                        listArr.add(arrList);
                    }
                }
            }
        }
        
        return listArr;
    }
    
    /**
     * 截取带有as的字段信息
     * @param str SQL
     * @return 返回截取后的字段及字段别名list.
     */
    private static List<Map<String, Object>> subStrAsList(String str) {
        // 截取select from之间字段，兼容中英文
        Pattern p = Pattern.compile("\\w+\\s+as\\s+[\\w[\u4E00-\u9FFF]]+");
        Matcher m = p.matcher(str.toLowerCase());
        List<Map<String, Object>> list = new ArrayList<>();
        while (m.find()) {
            Map<String, Object> map = new HashMap<>();
            String tt = m.group();
            String[] sry = tt.split("\\s+as\\s+");
            map.put("colId", sry[0]); // 截取字段信息
            map.put("colName", sry[1]); // 截取字段别名
            list.add(map);
        }
        return list; // 返回截取后的字段及字段别名list.
    }
    
    /**
     * 截取不带AS字段
     * @param str SQL
     * @return 返回截取不带AS字段
     */
    public static String matchSql(String str) {
        Pattern p = Pattern.compile("\\w+\\s(.+)from\\s.*[\\w[\u4E00-\u9FFF]]+");
        Matcher m = p.matcher(str.toLowerCase());
        String sub = "";
        if (m.find()) {
            if (m.group(1).indexOf(".") != -1) { // 如果字段为a.id, 截取.后的字段
                sub += m.group(1).substring(m.group(1).lastIndexOf(".") + 1) + ",";
            } else {
                sub += m.group(1); // 添加截取字段信息
            }
        }
        return sub; // 返回截取不带AS字段
    }
    
    /**
     * 合并带有AS, 非AS的字段信息
     * @param sql SQL
     * @return 返回合并好的list
     */
    public static List<String> mergeSubSql(String sql) {
        List<String> list = new ArrayList<>();
        String subNoAs = matchSql(sql); // 调用不带as截取
        List<Map<String, Object>> subAs = subStrAsList(sql); // 调用带as截取
        
        String[] subArrNoAs = subNoAs.split(","); // 根据逗号分隔
        List<String> subList = Arrays.asList(subArrNoAs); // 转换成List
        // Arrays内部类的ArrayList, 不能调用add、remove方法，重新new一个
        List<String> arrList = new ArrayList<>(subList);
        
        if (null != subAs && !subAs.isEmpty()) { // 如果包含as截取信息不为空
            if (null != arrList && !arrList.isEmpty()) {
                for (int i = 0; i < arrList.size(); i++) {
                    for (Map<String, Object> map : subAs) {
                        // 对比两个SQL截取中的字段信息
                        boolean flag = arrList.get(i).contains((CharSequence) map.get("colId"));
                        if (flag) {
                            arrList.add(map.get("colId") + "@@" + map.get("colName")); // 添加as中截取的字段信息
                            arrList.remove(i); // 删除普通截取中相对应的字段例:(id as pId)
                        }
                    }
                }
            }
            return arrList; // 返回合并好的list
        } else {
            String[] subA = subNoAs.split(",");
            for (String sub : subA) {
                list.add(sub.trim()); // 添加截取字段信息
            }
            return list; // 带有AS的SQL截取为空时，直接返回非AS字段截取信息
        }
    }
    
}
