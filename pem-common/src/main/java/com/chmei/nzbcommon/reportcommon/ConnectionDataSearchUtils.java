package com.chmei.nzbcommon.reportcommon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.chmei.nzbcommon.util.DateUtil;
import com.chmei.nzbcommon.util.StringUtil;

/**
 * 使用jdbc或odbc连接数据库做普通查询和高级查询
 * 
 * @author gkm
 * @since 2019年5月7日 16点37分
 */
public final class ConnectionDataSearchUtils {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionDataSearchUtils.class);

	/**
	 * 私有构造器 不允许实例化
	 */
	private ConnectionDataSearchUtils() {
	}

	/**年月日时分秒*/
	public static SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	/**年月日*/
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	
	
	
	/**
	 * 根据传入的sql获取表名
	 * 
	 * @param sql
	 * @return 返回表名
	 */
	public static String getTableName(String sql) {
		String tableName = ""; // 表名
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

	/**
	 * 获取应该放到where条件后的值
	 * 
	 * @param columnType
	 *            列类型
	 * @param columnVal
	 *            列的值
	 * @return 转换后的值
	 */
	public static String getRealColumnVal(String columnType, String columnVal, String data_basees) {
		StringBuilder returnStr = new StringBuilder();
		String type = columnType.trim();
		switch (type) {
		case "string":
			returnStr.append("\'").append(columnVal).append("\'");
			break;
		case "number":
			returnStr.append(columnVal);
			break;
		case "num":
			returnStr.append(columnVal);
			break;
		case "date":
			if (data_basees.equals(CHARUtils.MYSQL)) {
				Date date = DateUtil.string2Date(columnVal, DateUtil.DATE_PATTERN.YYYY_MM_DD);
				returnStr.append("\'").append(DateUtil.date2String(date, DateUtil.DATE_PATTERN.YYYY_MM_DD))
						.append("\'");
			} else if (data_basees.equals(CHARUtils.ORACLE)) {
				Date date = DateUtil.string2Date(columnVal, DateUtil.DATE_PATTERN.YYYY_MM_DD);
				returnStr.append("to_date( \'")
						.append(DateUtil.date2String(date, DateUtil.DATE_PATTERN.YYYY_MM_DD))
						.append("\' ,'yyyy-MM-dd')");
			}
			break;
		case "datetime":
            if (data_basees.equals(CHARUtils.MYSQL)) {
                Date date = DateUtil.string2Date(columnVal);
                returnStr.append("\'").append(DateUtil.date2String(date, DateUtil.DATE_PATTERN.YYYY_MM_DD_HH_MM_SS))
                        .append("\'");
            } else if (data_basees.equals(CHARUtils.ORACLE)) {
                Date date = DateUtil.string2Date(columnVal);
                returnStr.append("to_date( \'")
                        .append(DateUtil.date2String(date, DateUtil.DATE_PATTERN.YYYY_MM_DD_HH_MM_SS))
                        .append("\' ,'yyyy-MM-dd HH24:MI:SS')");
            }
            break;	
		default:
			returnStr.append(columnVal);
		}

		return returnStr.toString();
	}

	/**
	 * 获取where条件
	 * 
	 * @param whereColumn
	 *            条件列
	 * @param list
	 *            查询参数
	 * @return 返回语句
	 */
	@SuppressWarnings("unchecked")
	public static String getWhereSql(String whereColumn, List<Object> list, String data_basees) {
		StringBuffer sb = new StringBuffer();
		if (list.size() <= 0) {
			return "";
		} else {
			String columnType = (String) list.get(0); // 字段类型
			List<String> optList = (List<String>) list.get(1); // 操作类型
			List<String> valList = (List<String>) list.get(2); // 字段值
			for (int i = 0; i < optList.size(); i++) {
				String columnValue = valList.get(i);
				String optType = optList.get(i);
				// 若传入值为空 则跳过
				if (StringUtil.isEmpty(columnValue) || "null".equalsIgnoreCase(columnValue)) {
					continue;
				}
				sb.append(" AND "); //
				
				// string类型的查询条件进行 ,分割 然后括起来 进行 OR 运算 . 2019-6-13修改 by gkm
				if("string".equalsIgnoreCase(columnType)) {
				    String regx = ",|，"; // 中英文逗号分割
				    String[] valArray = valList.get(i).trim().split(regx);
				    if(valArray.length > 1) { //存在1个以上的值
				        whereColumn = getORString(optType, whereColumn, valArray);
				        sb.append(whereColumn);
				        continue; //拼接完成结束本次循环
				    }
				} 
				
				sb.append(whereColumn); // where后的字段名
				switch (optType) {
				case "等于":
					sb.append(" = ");
					break;
				case "不等于":
					sb.append(" <> ");
					break;
				case "大于":
					sb.append(" > ");
					break;
				case "大于等于":
					sb.append(" >= ");
					break;
				case "小于":
					sb.append(" < ");
					break;
				case "小于等于":
					sb.append(" <= ");
					break;
				case "包含":
					optType = "like";
					sb.append(" like ");
					break;
				case "模糊":
					optType = "like";
					sb.append(" like ");
					break;
				case "匹配":
					sb.append(" = ");
					break;
				default:
					sb.append(" ").append(optType).append(" ");
					break;
				}

				if ("like".equals(optType.trim().toLowerCase())) {
					sb.append("\'%").append(valList.get(i)).append("%\'");
				} else {
					sb.append(getRealColumnVal(columnType, valList.get(i), data_basees)); // 获取
				}
			}
		}

		if (sb.toString().endsWith("AND ")) { // 去掉最后的 AND
			sb.delete(sb.length() - 3, sb.length());
		}
		return sb.toString();
	}

	/**
	 * 拼接一个完整的普通查询sql (不含SUM,GROUP BY的)
	 * 
	 * @param sql
	 *            SQL语句
	 * @param selectColumns
	 *            查询列
	 * @param whereColumns
	 *            条件列
	 * @return 返回SQL
	 */
	@SuppressWarnings("unchecked")
	public static String getNormalSql(String sql, List<String> selectColumns, Map<String, Object> whereColumns,
			String data_basees) {
		String tableName = getTableName(sql); // 根据传入的sql获取表名
		StringBuilder selectSql = new StringBuilder("");
		StringBuilder selectSqlTemp = new StringBuilder("SELECT ");
		for (String selectCol : selectColumns) {
			selectSqlTemp.append(selectCol + ",");
		}
		// 截取最后的 ,
		if (selectSqlTemp.toString().trim().endsWith(",")) {
			String trimStr = selectSqlTemp.toString().trim();
			int index = trimStr.lastIndexOf(",");
			selectSql.append(trimStr.substring(0, index));
		}

		selectSql.append(" FROM " + tableName);

		// 拼接where条件
		StringBuilder whereSql = new StringBuilder("");
		whereSql.append(" WHERE 1=1");
		for (Entry<String, Object> set : whereColumns.entrySet()) {
			String column = set.getKey();
			if (!StringUtils.isEmpty(set.getValue())) {
				List<Object> infoList = (List<Object>) set.getValue();
				whereSql.append(getWhereSql(column, infoList, data_basees));
			}
		}
		selectSql.append(whereSql);
		String order_by = "";
		//添加排序字段
		if(sql.toUpperCase().indexOf(" ORDER ") != -1){
			order_by = sql.substring(sql.toUpperCase().indexOf(" ORDER "));
		}
		return selectSql.toString()+"  "+order_by;
	}

	/**
	 * 查询条数SQL语句
	 * 
	 * @param sql
	 *            SQL
	 * @param whereColumns
	 *            条件列
	 * @return 返回SQL
	 */
	@SuppressWarnings("unchecked")
	public static String getNormalCountSql(String sql, Map<String, Object> whereColumns, String data_basees) {
		String tableName = getTableName(sql); // 根据传入的sql获取表名
		StringBuilder selectSql = new StringBuilder("SELECT COUNT(*) FROM ");

		selectSql.append(tableName);

		// 拼接where条件
		StringBuilder whereSql = new StringBuilder();
		whereSql.append(" WHERE 1=1 ");
		if (whereColumns != null) {
			for (Entry<String, Object> set : whereColumns.entrySet()) {
				String column = set.getKey();
				if (!StringUtils.isEmpty(set.getValue())) {
					List<Object> infoList = (List<Object>) set.getValue();
					whereSql.append(getWhereSql(column, infoList, data_basees));
				}
			}
		}
		selectSql.append(whereSql);
		LOGGER.info("拼接的完整count sql语句::" + selectSql.toString());
		return selectSql.toString();
	}

	/**
	 * 普通查询, 调用此方法即可
	 * 
	 * @param connInfo
	 *            数据库连接信息
	 * @param selectColumns
	 *            展示的字段
	 * @param selectColumnsType
	 *            展示的字段类型
	 * @param decimalPlaces
	 *            小数保留位数
	 * @param whereColumns
	 *            条件字段和值以及操作类型(大于，小于，包含等)
	 * @param start
	 *            起始页
	 * @param limit
	 *            页数据个数
	 * @return 返回数据集合
	 */
	public static List<Map<String, Object>> normalQueryList(Map<String, Object> connInfo, List<String> selectColumns,
								List<String> selectColumnsType,Integer decimalPlaces,Map<String, Object> whereColumns,
								String search_Sort, int start, int limit) {
		//处理排序字段
		search_Sort = dealWithSort(search_Sort);//如果没有排序字段返回null
		// 存放结果
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		// 数据库链接
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			String srcSql = MapUtils.getString(connInfo, "sql_sentence");
			String data_type = MapUtils.getString(connInfo, "data_type");// 1jdbc// 2odbc
			String data_basees = MapUtils.getString(connInfo, "data_basees");// Mysql// Oracle
			String realSql = getNormalSql(srcSql, selectColumns, whereColumns, data_basees);
			// 如果SQL中带有order by 拼接排序 -- 2019年8月26日修改---start
			if(search_Sort != null){//说明前台传递有排序字段
				int indexOf = realSql.toUpperCase().indexOf(" BY ");
				if(indexOf != -1){//存在
					realSql = realSql.substring(0, indexOf) + " by "+ search_Sort +","+realSql.substring(indexOf+3);
				}else{//原始sql没有排序
					realSql = realSql + " order by "+search_Sort;
				}
			}
			// ------------------2019年8月26日修改--------------end
			if (data_basees.equals(CHARUtils.MYSQL)) {
				//添加排序
				realSql += " LIMIT "+ start+" , "+limit;
			} else if (data_basees.equals(CHARUtils.ORACLE)) {
			    // 计算出开始行数和结束行数 如:查询第一页传入 0，10，计算出 1,10
	            int startRows = start + 1; // >= 开始行 +1
	            int endRows = start + limit; // <= 开始行+每页展示条数
			    
				StringBuilder querySqlStart = new StringBuilder("select * from (select * from (select a.*,rownum rn from(");
				StringBuilder querySqlEnd = new StringBuilder(")a ) where rownum <=" + endRows + ") where rn>=")
						.append(startRows); // >= 放到里面那一层效率更高
				realSql = querySqlStart.toString() + realSql + querySqlEnd.toString();
			}
			LOGGER.info("完整的分页sql语句::" + realSql);
			try {
				if (data_type.equals("1")) {// jdbc
					if (data_basees.equals(CHARUtils.MYSQL)) {
						Class.forName("com.mysql.jdbc.Driver");
					} else {
						Class.forName("oracle.jdbc.driver.OracleDriver");
					}
				} else if (data_type.equals("2")) {// odbc
					return null;
				} else {
					return null;
				}
				String url = MapUtils.getString(connInfo, "conn_mode"); // "jdbc:mysql://192.168.0.206:3306/report_manage_db";
																		// //
				String user = MapUtils.getString(connInfo, "login_acct"); // "report_manage";
				String password = MapUtils.getString(connInfo, "login_pwd"); // "1qaz@WSX";
				conn = DriverManager.getConnection(url, user, password);
			} catch (ClassNotFoundException e) {
				close(conn, pstmt, rs);
				LOGGER.error("获取数据库连接异常." + e.getMessage());
			}
			if (conn == null) {
				return null;
			}
			connInfo.put("finalSql", realSql); // 返回拼接的完整sql
			pstmt = conn.prepareStatement(realSql);
			rs = pstmt.executeQuery();
			// 获取结果集
			while (rs.next()) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				for (int i = 0; i < selectColumns.size(); i++) {
					String columnName = selectColumns.get(i); // 列名
					Object obj_emp = rs.getObject(columnName.trim());
					String obj_value = obj_emp == null ? "" : obj_emp.toString().trim();
					String char_type = selectColumnsType.get(i);//类型
					//类型值格式化
					String typeFormat = TableUtils.typeFormat(data_basees,char_type, obj_value,decimalPlaces);
					resultMap.put(columnName.trim(), typeFormat);
				}
				items.add(resultMap);
			}
			return items;
		} catch (SQLException e) {
			close(conn, pstmt, rs);
			LOGGER.error("查询失败," + e.getMessage());
		} catch (Exception e) {
			close(conn, pstmt, rs);
			LOGGER.error("查询失败," + e.getMessage());
		} finally {
			close(conn, pstmt, rs);
		}
		return items;
	}
	
	
	/**
	 * 处理排序字段并拼接成sql
	 * @param search_Sort
	 * @return
	 */
	private static String dealWithSort(String search_Sort){
		String sql = null;
		if(search_Sort == null || search_Sort.trim().length() == 0){
			return sql;
		}
		sql = " ";
		String[] sort_params = search_Sort.split(",");
		for (int i = 0; i < sort_params.length; i++) {
			String params = sort_params[i];
			String[] s = params.split("\\*");
			sql += s[0]+"  "+s[1]+"  ,";
		}
		sql = sql.substring(0,sql.length()-1);
		return sql;
	}
	

	/**
	 * 普通查询 查询条数
	 * 备注：重新组装sql，没有涉及排序问题
	 * 
	 * @param connInfo
	 *            数据源配置信息
	 * @param whereColumns
	 *            条件列
	 * @return 返回查询条数
	 * @throws Exception
	 *             抛出异常信息
	 */
	public static Integer normalQueryCount(Map<String, Object> connInfo, Map<String, Object> whereColumns) {
		// 数据库链接
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Integer count = 0;
		try {
			String srcSql = MapUtils.getString(connInfo, "sql_sentence");
			String data_type = MapUtils.getString(connInfo, "data_type");// 1jdbc 2odbc
			String data_basees = MapUtils.getString(connInfo, "data_basees");// Mysql Oracle
			String realSql = getNormalCountSql(srcSql, whereColumns, data_basees);
			StringBuilder querySql = new StringBuilder(realSql);
			realSql = querySql.toString();
			try {
				if (data_type.equals("1")) {// jdbc
					if (data_basees.equals(CHARUtils.MYSQL)) {
						Class.forName("com.mysql.jdbc.Driver");
					} else {
						Class.forName("oracle.jdbc.driver.OracleDriver");
					}
				} else if (data_type.equals("2")) {// odbc
					return 0;
				} else {
					return -1;
				}
				String url = MapUtils.getString(connInfo, "conn_mode"); // "jdbc:mysql://192.168.0.206:3306/report_manage_db";
																		// //
				String user = MapUtils.getString(connInfo, "login_acct"); // "report_manage";
				String password = MapUtils.getString(connInfo, "login_pwd"); // "1qaz@WSX";
				conn = DriverManager.getConnection(url, user, password);
			} catch (ClassNotFoundException e) {
				close(conn, pstmt, rs);
				LOGGER.error("获取数据库连接异常." + e.getMessage());
			}
			if (conn == null) {
				return -1;
			}
			connInfo.put("finalSql", realSql); // 返回拼接的完整sql
			pstmt = conn.prepareStatement(realSql);
			rs = pstmt.executeQuery();
			// 获取结果集
			if (rs.next()) {
				count = rs.getInt(1);
			}
			return count;

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
	 * 普通查询下载, 调用此方法即可
	 * 
	 * @param connInfo
	 *            数据库连接信息
	 * @param selectColumns
	 *            展示的字段
	 * @param whereColumns
	 *            条件字段和值以及操作类型(大于，小于，包含等)
	 * @return 返回数据集合
	 */
	public static List<Map<String, Object>> downLoanNormalQueryList(Map<String, Object> connInfo,
			List<String> selectColumns, Map<String, Object> whereColumns,
			List<String> selectColumnsType,String search_Sort,Integer decimalPlaces,int count) {
		//处理排序字段
		search_Sort = dealWithSort(search_Sort);//如果没有排序字段返回null
		// 存放结果
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		// 数据库链接
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			String srcSql = MapUtils.getString(connInfo, "sql_sentence");
			String data_type = MapUtils.getString(connInfo, "data_type");// 1// jdbc// 2odbc
			String data_basees = MapUtils.getString(connInfo, "data_basees");// Mysql// Oracle
			String realSql = getNormalSql(srcSql, selectColumns, whereColumns, data_basees);
			// 如果SQL中带有order by 拼接排序 -- 2019年8月26日修改---start
			if(search_Sort != null){//说明前台传递有排序字段
				int indexOf = realSql.toUpperCase().indexOf(" BY ");
				if(indexOf != -1){//存在
					realSql = realSql.substring(0, indexOf) + " by "+ search_Sort +","+realSql.substring(indexOf+3);
				}else{//原始sql没有排序
					realSql = realSql + " order by "+search_Sort;
				}
			}
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String url = MapUtils.getString(connInfo, "conn_mode"); // "jdbc:mysql://192.168.0.206:3306/report_manage_db";
				String user = MapUtils.getString(connInfo, "login_acct"); // "report_manage";
				String password = MapUtils.getString(connInfo, "login_pwd"); // "1qaz@WSX";
				conn = DriverManager.getConnection(url, user, password);
			} catch (ClassNotFoundException e) {
				close(conn, pstmt, rs);
				LOGGER.error("获取数据库连接异常." + e.getMessage());
			}
			if (conn == null) {
				return null;
			}
			connInfo.put("finalSql", realSql); // 返回拼接的完整squeal
			int num = 500;
			String realSql_1 = null;
			if(count > num){//每次200请求一次
	        	int page = count/num;
	        	int remain = count%num;
	        	if(remain > 0){
	        		for (int i = 1; i <= page+1; i++) {
	        			realSql_1 = getLimit(realSql,data_basees,i,num);
	        			List<Map<String, Object>> dealConnData = dealConnData(count, conn, pstmt, rs, realSql_1, selectColumns, selectColumnsType, data_basees, decimalPlaces);
	        			items.addAll(dealConnData);
	        		}
	        	}else{
	        		for (int i = 1; i <= page; i++) {
	        			realSql_1 = getLimit(realSql,data_basees,i,num);
	        			List<Map<String, Object>> dealConnData = dealConnData(count, conn, pstmt, rs, realSql_1, selectColumns, selectColumnsType, data_basees, decimalPlaces);
	        			items.addAll(dealConnData);
					}
	        	}
	        }else{
	        	realSql_1 = getLimit(realSql,data_basees,1,num);
	        	List<Map<String, Object>> dealConnData = dealConnData(count, conn, pstmt, rs, realSql_1, selectColumns, selectColumnsType, data_basees, decimalPlaces);
	        	items.addAll(dealConnData);
	        }
		} catch (SQLException e) {
			close(conn, pstmt, rs);
			LOGGER.error("查询失败," + e.getMessage());
		} catch (Exception e) {
			close(conn, pstmt, rs);
			LOGGER.error("查询失败," + e.getMessage());
		} finally {
			close(conn, pstmt, rs);
		}
		return items;
	}
	
	/**
	 * 大量数据下载过程使用分段下载然后组装excel
	 * @param count
	 * @param conn
	 * @param pstmt
	 * @param rs
	 * @param realSql
	 * @param selectColumns
	 * @param selectColumnsType
	 * @param data_basees
	 * @param decimalPlaces
	 * @return List
	 * @throws Exception
	 */
	public static List<Map<String, Object>> dealConnData(Integer count,Connection conn,PreparedStatement pstmt,
			                                             ResultSet rs,String realSql,List<String> selectColumns,
			                                             List<String> selectColumnsType,String data_basees,
			                                             Integer decimalPlaces) throws Exception{
		// 存放结果
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		pstmt = conn.prepareStatement(realSql);
		rs = pstmt.executeQuery();
		// 获取结果集
		while (rs.next()) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			for (int i = 0; i < selectColumns.size(); i++) {
				String columnName = selectColumns.get(i); // 列名
				Object obj_emp = rs.getObject(columnName.trim());
				String obj_value = obj_emp == null ? "" : obj_emp.toString().trim();
				String char_type = selectColumnsType.get(i);//类型
				//类型值格式化
				String typeFormat = TableUtils.typeFormat(data_basees,char_type, obj_value,decimalPlaces);
				resultMap.put(columnName, typeFormat);
			}
			items.add(resultMap);
		}
		return items;
	}
	
	
	/**
	 * 接收sql根据数据库类型拼接分页
	 * 普通下载
	 * @return 拼接好分页sql
	 */
	public static String getLimit(String sql,String type,int page,int num){
		int count = num;
		String realSql = null;
		//Mysql// Oracle
		if(type.equals("Mysql")){
			realSql = sql+" LIMIT "+(page-1)*count+","+count;
		}else{
			StringBuilder querySqlStart = new StringBuilder("select * from (select * from (select a.*,rownum rn from(");
			
			StringBuilder querySqlEnd = new StringBuilder(")a ) where rownum <=" + (count*page) + ") where rn>=")
					                                     .append((page-1)*count+1); // >= 放到里面那一层效率更高
			realSql = querySqlStart.toString() + sql+querySqlEnd.toString();
		}
		LOGGER.info("下载时的完整sql语句::" + realSql);
		return realSql;
	}

	/**
	 * 关闭数据连接
	 * 
	 * @param conn
	 *            数据库连接
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
	 * 
	 * @param rs
	 *            结果集
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
	 * 
	 * @param st
	 *            数据库连接对象
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
	 * 
	 * @param conn
	 *            数据库连接
	 * @param st
	 *            连接对象
	 * @param rs
	 *            结果集
	 */
	public static void close(Connection conn, Statement st, ResultSet rs) {
		closeResultSet(rs);
		closeStatement(st);
		closeConn(conn);
	}

	

	/**
	 * 获取数据库元数据信息
	 * 
	 * @param rs
	 *            结果集
	 * @return 返回元数据信息
	 * @throws Exception
	 *             异常
	 */
	public static Map<String, Object> getMetaData(ResultSet rs) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		ResultSetMetaData rsd = rs.getMetaData();
		int count = rsd.getColumnCount();
		for (int i = 1; i <= count; i++) {
			map.put(rsd.getColumnName(i), rsd.getScale(i));
		}
		return map;
	}
	
	/**
	 *  条件值是多个的进行 OR 运算拼接 
	 * @param optType 操作类型
	 * @param whereColumn 字段名
	 * @param valArray 值数组
	 * @return
	 */
	public static String getORString (String type, String whereColumn, String[] valArray) {
        if(valArray.length <= 1) {
            return "";
        }
        StringBuilder OrStr = new StringBuilder("");
        if(valArray.length > 1) { //存在1个及以上的值
            OrStr.append(" ("); //左括号
            for(int i=0; i < valArray.length; i++) {
                OrStr.append(whereColumn); //拼上字段名称
                //操作符
                String optType = type; //重新赋值
                switch (optType) {
                    case "包含":
                        optType = "like";
                        OrStr.append(" like ");
                        break;
                    case "模糊":
                        optType = "like";
                        OrStr.append(" like ");
                        break;
                    case "匹配":
                        OrStr.append(" = ");
                        break;
                    default:
                        OrStr.append(optType);
                        break;
                }
                if ("like".equals(optType.trim().toLowerCase())) {
                    OrStr.append("\'%").append(valArray[i]).append("%\'");
                } else {
                    OrStr.append(getRealColumnVal("string", valArray[i], "")); // 获取
                }
                OrStr.append(" OR ");
            }
            
            if (OrStr.toString().endsWith(" OR ")) { // 去掉最后的  OR 
                OrStr.delete(OrStr.length() - 3, OrStr.length());
            }
            OrStr.append(") "); //右括号
        }
	    
	    return OrStr.toString();
	}
}
