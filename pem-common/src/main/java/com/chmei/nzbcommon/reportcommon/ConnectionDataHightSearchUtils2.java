package com.chmei.nzbcommon.reportcommon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.chmei.nzbcommon.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 使用jdbc或odbc连接数据库做高级查询
 * @author 翟超锋
 * @since 2019年6月24日
 */
public final class ConnectionDataHightSearchUtils2 {
    
    /**
     *LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionDataHightSearchUtils2.class);
    
    /**
     * 私有构造器 不允许实例化
     */
    private ConnectionDataHightSearchUtils2() {
    }
    
    
    /**
     * 高级查询数量, 查询时调用此方法即可
     * @param connInfo 数据库连接信息
     * @param selectColumns 列信息
     * @param selectColType 列类型信息
     * @param whereParams where条件信息 
     * @param whereColumns 高级汇总信息(key: 字段名  value: 操作类型(分组，求和等))
     * @param decimalPlaces 数字型小数保留位数
     * @return 返回查询数据
     */
    public static Integer highQueryCount(Map<String, Object> connInfo) {
        //数据库链接
        Connection conn = null;
        java.sql.PreparedStatement pstmt = null;
        ResultSet rs = null;
        Integer count = 0;
        try {
            String srcSql = MapUtils.getString(connInfo, "sql_sentence");
            String data_type = MapUtils.getString(connInfo, "data_type"); // 1
            String data_basees = MapUtils.getString(connInfo, "data_basees"); // Mysql or Oracle
            if (data_type.equals("1")) { // jdbc
                if (data_basees.equals("Mysql")) {
                    Class.forName("com.mysql.jdbc.Driver");
                } else {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                }
            } else if (data_type.equals("2") ) { // odbc
                return null;
            } else { //其它
                return null;
            }
            String url = MapUtils.getString(connInfo, "conn_mode"); //"jdbc:mysql://192.168.0.206:3306/report_manage_db"; //
            String user = MapUtils.getString(connInfo, "login_acct"); // "report_manage";
            String password = MapUtils.getString(connInfo, "login_pwd"); // "1qaz@WSX";
            conn = DriverManager.getConnection(url, user, password);
            String tableName = getTableName(srcSql);
            // 获取拼接的countsql
            String realSql = " select count(1) as row_s from (select "
                                       +MapUtils.getString(connInfo, "select_back")+" from "+tableName
            		                   +" where 1=1 "
        	                           +MapUtils.getString(connInfo, "where_back")
        	                           +MapUtils.getString(connInfo, "end_group")+" )tmp";
            LOGGER.info("完整的高级汇总数量sql语句::" + realSql);
            connInfo.put("finalSql", realSql); // 返回拼接的完整sql
            pstmt = conn.prepareStatement(realSql);
            rs = pstmt.executeQuery();
            // 获取结果集
            if (rs.next()) {
               count = rs.getInt("row_s");//总条数
            }
        } catch (ClassNotFoundException ee) {
            LOGGER.error("驱动加载异常," + ee.getMessage());
        } catch (SQLException e) {
            close(conn, pstmt, rs);
            LOGGER.error("查询失败," + e.getMessage());
            count = -2;
        } finally {
            close(conn, pstmt, rs);
        }
        return count;
    }
    
    
    /**
     * 高级查询, 查询时调用此方法即可
     * @param connInfo 数据库连接信息
     * @param selectColumns 列信息
     * @param selectColType 列类型信息
     * @param whereParams where条件信息 
     * @param whereColumns 高级汇总信息(key: 字段名  value: 操作类型(分组，求和等))
     * @param decimalPlaces 数字型小数保留位数
     * @return 返回查询数据
     */
    @SuppressWarnings("unchecked")
	public static List<Map<String, Object>> highQueryList(Map<String, Object> connInfo,Integer decimalPlaces) {
        Long begin = System.currentTimeMillis();
        Integer start = MapUtils.getInteger(connInfo, "page_start"); // 分页参数
        Integer limit = MapUtils.getInteger(connInfo, "page_limit"); // 分页条数
        
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); //存放结果
        //数据库链接
        Connection conn = null;
        java.sql.PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String srcSql = MapUtils.getString(connInfo, "sql_sentence");
            String data_type = MapUtils.getString(connInfo, "data_type"); // 1
            String data_basees = MapUtils.getString(connInfo, "data_basees"); // Mysql or Oracle
            if (data_type.equals("1")) { // jdbc
                if (data_basees.equals("Mysql")) {
                    Class.forName("com.mysql.jdbc.Driver");
                } else {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                }
            } else if (data_type.equals("2") ) { // odbc
                return null;
            } else { //其它
                return null;
            }
            String url = MapUtils.getString(connInfo, "conn_mode"); //"jdbc:mysql://192.168.0.206:3306/report_manage_db"; //
            String user = MapUtils.getString(connInfo, "login_acct"); // "report_manage";
            String password = MapUtils.getString(connInfo, "login_pwd"); // "1qaz@WSX";
            conn = DriverManager.getConnection(url, user, password);
            String tableName = getTableName(srcSql);
            // 获取拼接的sql
            String realSql = " select "+MapUtils.getString(connInfo, "select_back")+" from "+tableName
            		                   +" where 1=1 "
        	                           +MapUtils.getString(connInfo, "where_back")
        	                           +MapUtils.getString(connInfo, "end_group");
            
            StringBuilder querySql = new StringBuilder(realSql);
            if (data_basees.equals(CHARUtils.MYSQL)) {
                //添加排序
                querySql.append(" LIMIT ").append(start).append(" , ").append(limit);
                realSql = querySql.toString();
            } else if (data_basees.equals(CHARUtils.ORACLE)) {
                // 计算出开始行数和结束行数 如:查询第一页传入 0，10，计算出 1,10
                int startRows = start + 1; // >= 开始行 +1
                int endRows = start + limit; // <= 开始行+每页展示条数
                
                StringBuilder querySqlStart = new StringBuilder("select * from (select * from (select a.*,rownum rn from(");
                StringBuilder querySqlEnd = new StringBuilder(")a ) where rownum <=" + endRows + ") where rn>=")
                        .append(startRows); // >= 放到里面那一层效率更高
                realSql = querySqlStart.toString() + querySql.toString()  + querySqlEnd.toString();
            }
            LOGGER.info("完整的高级汇总分页sql语句::" + realSql);
            connInfo.put("finalSql", realSql); // 返回拼接的完整sql
            pstmt = conn.prepareStatement(realSql);
            rs = pstmt.executeQuery();
            //查询远程数据库数据表列
            List<String> selectColumns = (List<String>) MapUtils.getObject(connInfo, "listColus");
            List<String> listType = (List<String>) MapUtils.getObject(connInfo, "listType");
            //获取结果集
            while (rs.next()) {
                Map<String, Object> resultMap = new HashMap<String, Object>();
                for (int i = 0; i < selectColumns.size(); i++) {                    
                    String columnName = selectColumns.get(i); //列名
                    Object obj_emp = rs.getObject(columnName.trim());
                    String obj_value = obj_emp == null ? "" : obj_emp.toString();
                    String char_type = listType.get(i);//字段类型
                    //类型值格式化
                    String typeFormat = TableUtils.typeFormat(data_basees,char_type, obj_value, decimalPlaces);
                    resultMap.put(columnName.trim(), typeFormat);
                }
                list.add(resultMap);
            }
            
        } catch (ClassNotFoundException ee) {
            LOGGER.error("驱动加载异常," + ee.getMessage());
        } catch (SQLException e) {
            close(conn, pstmt, rs);
            LOGGER.error("查询失败," + e.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        Long end = System.currentTimeMillis();
        LOGGER.info("本次查询耗时" + (end - begin) + "ms");
        return list;
    }

    /**
     * 根据查询条件组装sql
     * @param jsonArray
     * @param mapInfo 数据库信息
     * @return map 三个键值对 1 字段类型
     *                    2 select后面的字段类型
     *                    3 where后面的条件拼接
     */
    @SuppressWarnings("unused")
	public static Map<String,Object> assembleSQL(JSONArray jsonArray,Map<String,Object> mapInfo){
    	List<String> listType = new ArrayList<>();//存储字段类型
    	List<String> listColus = new ArrayList<>();//存储字段名称
    	Map<String,Object> map = new HashMap<>();//存储三个片段sql
    	String select_back = " "; //select后面字段
    	String end_group = "";//最后的分组
    	String where_back = "";//where后面条件
    	for (int i = 0; i < jsonArray.size(); i++) {
    		JSONObject job = jsonArray.getJSONObject(i); 
    		String chartype = job.get("chartype").toString();//字段类型
    		String valueTitle = job.get("value").toString();//字段中文名称
    		listType.add(chartype.toString());
    		
    		String titleName = job.get("columnvalue").toString();//字段英文名称
    		listColus.add(titleName);
    		
            String opt = job.get("opt").toString();//字段操作
            switch(opt){
	            case "求和" :
	            	select_back += " sum("+titleName+") as "+titleName+" ,";
	            	//解决日期不允许求和
	            	if(chartype.equals("date") || chartype.equals("datetime")){
	            		map.put("-1", "日期类型["+valueTitle+"],不允许求和!");
	            		break;
	            	}
	            	break; 
	            case "分组" ://最后一步需要拼接group by
	            	select_back += titleName+" ,";
	            	end_group += titleName+" ,";
	            	break; 
	            case "最大值" :
	            	select_back += " max("+titleName+") as "+titleName+" ,";
	            	break; 
	            case "最小值" :
	            	select_back += " min("+titleName+") as "+titleName+" ,";
	            	break; 
	            case "平均值" :
	            	select_back += " avg("+titleName+") as "+titleName+" ,";
	            	//解决日期不允许求平均值
                    if(chartype.equals("date") || chartype.equals("datetime")){
                        map.put("-1", "日期类型["+valueTitle+"],不允许求平均值!");
                        break;
                    }
	            	break;
	            case "计数" :
	                select_back += " count("+titleName+") as "+titleName+" ,";
	                break;
	            default : 
	            	select_back += "";
	        }
            if(map.containsKey("-1")){//说明有错误返回
            	return map;
            }
            String screen_f = job.get("screen_f").toString();//字段筛选符
            String screen_v = job.get("screen_v").toString();//字段筛选值
            where_back += joiningToWhere(screen_f, screen_v, chartype, titleName, mapInfo);
		}
    	//重新组装分组
    	if(end_group.trim().length() > 0) {
    	    end_group = " group by "+ end_group.substring(0, end_group.length()-1);
    	}
    	//存储返回
    	map.put("select_back", select_back.substring(0,select_back.length()-1));
    	map.put("end_group", end_group);
    	map.put("where_back", where_back);
    	map.put("listColus", listColus);
    	map.put("listType", listType);
    	return map;
    }
   

    /**
     *根据类型拼接查询sql 
     * @param screen_f
     * @param screen_v
     * @param chartype
     * @param where_back
     * @param titleName
     * @param mapInfo 数据库连接信息
     * @return
     */
    public static String joiningToWhere(String screen_f,String screen_v,String chartype,
    		                            String titleName,Map<String,Object> mapInfo){
    	String where_back = "";
    	String data_basees = MapUtils.getString(mapInfo, "data_basees"); // Mysql or Oracle
    	//有值还要有条件才能成立
        if(screen_f != null && !screen_f.trim().equals("") && screen_v != null && !screen_v.trim().equals("")){
        	switch(chartype){
	            case "string" ://String
	            	String[] str = null;
	            	if(screen_f.equals("包含")){
	            		if(screen_v.indexOf(",") != -1){//多个值英文逗号
	            			str = screen_v.split(",");
	            		}else if(screen_v.indexOf("，") != -1){//多个值中文逗号
	            			str = screen_v.split("，");
	            		}
	            		if(str != null){
	            			where_back += " and (";
	            			for (int j = 0; j < str.length; j++) {
	            				where_back +=  titleName+" like '%"+str[j]+"%' or ";
	            			}
	            			where_back = where_back.substring(0, where_back.length()-3);
	            			where_back += " ) ";
	            		}else{
	            			where_back += " and " + titleName+" like '%"+screen_v+"%'";
	            		}
	            	}else if(screen_f.equals("匹配")){
	            		if(screen_v.indexOf(",") != -1){//多个值英文逗号
	            			str = screen_v.split(",");
	            		}else if(screen_v.indexOf("，") != -1){//多个值中文逗号
	            			str = screen_v.split("，");
	            		}
	            		if(str != null){
	            			for (int j = 0; j < str.length; j++) {
	            				where_back += " and " + titleName+"='"+str[j]+"'";
	            			}
	            		}else{
	            			where_back += " and " + titleName+"='"+screen_v+"'";
	            		}
	            	}
	            	break; 
	            case "num" ://num
	            	if(screen_f.equals(">")){
	            		where_back += " and " + titleName+" > "+screen_v+" ";
	            	}else if(screen_f.equals("<")){
	            		where_back += " and " + titleName+" < "+screen_v+" ";
		        	}else if(screen_f.equals("=")){
		        		where_back += " and " + titleName+" = "+screen_v+" ";
		        	}else if(screen_f.equals(">=")){
		        		where_back += " and " + titleName+" >= "+screen_v+" ";
		        	}else if(screen_f.equals("<=")){
		        		where_back += " and " + titleName+" <= "+screen_v+" ";
		        	}
	            	break; 
	            case "date" ://date
	            	str = screen_v.split(",");//日期截取
	            	if(str.length > 0){//保证有值
            			if(str[0].trim().length() > 0){
            			   where_back += " and " + titleName+" >= '"+str[0].trim()+"'";
            			}
            			if(str.length == 2){//保证有值
	            			if(str[1].trim().length() > 0){
	            			    where_back += " and " + titleName+" <= '"+str[1].trim()+"'";
	            			}
            			}
	            	}
	            	break; 
	            case "datetime" ://datetime
	            	str = screen_v.split(",");//日期截取
	            	if(str.length > 0){//保证有值
	            		if(data_basees.equals("Mysql")){//只有年月日
		            		if(str[0].trim().length() > 0){
		            			where_back += " and unix_timestamp(" + titleName+") >= unix_timestamp('"+str[0].trim()+"') ";
		            		}
		            		if(str.length == 2){//保证有值
			            		if(str[1].trim().length() > 0){
			            		    where_back += " and unix_timestamp(" + titleName+") <= unix_timestamp('"+str[1].trim()+"') ";
			            		}
		            		}
	            		}else if(data_basees.equals("Oracle")){//年月日时分秒//to_date('2018/12/26 10:05:17','yyyy-MM-dd hh:mi:ss');
	            			if(str[0].trim().length() > 0){
	            			    where_back += " and " + titleName+" >= to_date('"+str[0].trim()+"','yyyy-MM-dd HH24:MI:SS') ";
	            			}
	            			if(str.length == 2){//保证有值
		            			if(str[1].trim().length() > 0){
		            			    where_back += " and " + titleName+" <= to_date('"+str[1].trim()+"','yyyy-MM-dd HH24:MI:SS') ";
		            			}
	            			}
	            		}
	            	}
	            	break; 
	            default : 
	            	where_back += "";
	            }			
        	
        }
    	return where_back;
    }
    
    
    /**
     * 关闭数据连接
     * @param conn 数据库连接
     */
    public static void closeConn(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("关闭数据库连接失败", e);
            }
        }
    }
    
    /**
     * 关闭数据库结果集
     * @param rs 结果集
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LOGGER.error("关闭数据库ResultSet失败", e);
            }
        }
    }
    
    /**
     * 关闭数据库statement
     * @param st 数据库连接对象
     */
    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                LOGGER.error("关闭数据库Statement失败", e);
            }
        }
    }
    
    /**
     * 关闭数据连接
     * @param conn 数据库连接
     * @param st 连接对象
     * @param rs 结果集
     */
    public static void close(Connection conn, Statement st, ResultSet rs) {
        closeResultSet(rs);
        closeStatement(st);
        closeConn(conn);
    }
    
    /**
     * 根据传入的sql获取表名
     * @param sql sql语句参数
     * @return 表名
     */
    public static String getTableName(String sql) {
        String tableName = ""; //表名
        String regex = "\\bFROM\\s*\\S*";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(sql.toUpperCase());
        if (match.find()) {
            tableName = match.group().replace("FROM", "");
        } else {
            LOGGER.info("not found table !");
        }
        return tableName;
    }
    
}
