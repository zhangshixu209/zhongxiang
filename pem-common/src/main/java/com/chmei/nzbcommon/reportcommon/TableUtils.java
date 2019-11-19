package com.chmei.nzbcommon.reportcommon;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chmei.nzbcommon.util.ReckonTools;
import com.chmei.nzbcommon.util.StringUtil;


/**
 * 使用JDBC解析数据库表信息（表名称，字段类型，字段长度，字段注释）
 * 
 * @author zhangsx
 * @since 2019年5月7日 16点37分
 */
public class TableUtils {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TableUtils.class);

	/**
	 * 数据源配置中检验数据库连接信息是否正确
	 * 
	 * @param url
	 *            数据库链接地址
	 * @param user
	 *            用户名
	 * @param password
	 *            密码
	 * @param sql
	 *            查询SQL
	 * @param type
	 *            1 jdbc 2 odbc
	 * @return 成功 0, 失败 其他信息
	 */
	public static String databaseConnectionCheck(String url, String user, String password, int type,
			String dataBasees) {
		String msg = "连接失败,请检查连接数据库信息!";
		Connection connection = null;
		if (type == 1 || type == 2) {
			switch (type) {
			case 1:// jdbc
				try {
					if (dataBasees.equals("Mysql")) {// Mysql
						Class.forName("com.mysql.jdbc.Driver");
					} else {// Oracle
						Class.forName("oracle.jdbc.driver.OracleDriver");
					}
					connection = DriverManager.getConnection(url, user, password);
					connection.close();
					return "0";
				} catch (Exception e) {
					if (connection != null) {
						try {
							connection.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
					e.printStackTrace();
				}
				break;
			case 2:// odbc
				try {
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					connection = DriverManager.getConnection(url, user, password);
					DatabaseMetaData connectionData = connection.getMetaData();
					connection.close();
					return "0";
				} catch (Exception e) {
					if (connection != null) {
						try {
							connection.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					e.printStackTrace();
				}
				break;
			default:
				LOGGER.info("未知连接错误........url:" + url);
				return "未知连接错误";
			}
		} else {
			return "仅支持JDBC或ODBC连接!";
		}
		return msg;

	}

	/**
	 * 使用sql获取字段类型和详情信息
	 * 
	 * @param url
	 *            数据库链接地址
	 * @param user
	 *            用户名
	 * @param password
	 *            密码
	 * @param type
	 *            1 jdbc 2 odbc
	 * @param sql
	 *            查询SQL
	 * @throws SQLException
	 */
	@SuppressWarnings("resource")
	public static List<Object> SQLValidation(String url, String user, String password, int type, String sql,
			String dataBasees) {
		String conn = databaseConnectionCheck(url, user, password, type, dataBasees);
		Connection connection = null;
		DatabaseMetaData connectionData = null;
		ResultSet rst = null;// sql直接结果
		if (conn.equals("0")) {// 连接成功
			try {
				if (type == 1) {// jdbc
					if (dataBasees.equals("Mysql")) {
						Class.forName("com.mysql.jdbc.Driver");
						connection = DriverManager.getConnection(url, user, password);
						connectionData = connection.getMetaData();
					} else if (dataBasees.equals("Oracle")) {
						Class.forName("oracle.jdbc.driver.OracleDriver");
						Properties props =new Properties();
						props.put("remarksReporting","true");
						props.put("user", user);
						props.put("password",password);
						Class.forName("oracle.jdbc.driver.OracleDriver");
						connection = DriverManager.getConnection(url, props);
						connectionData = connection.getMetaData();
					} else {// 未知
						return null;
					}
				} else if (type == 2) {// odbc
					return null;
				} else {// 未知类型
					return null;
				}
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				// SQL解析表名称
				String tableName = "";
				if (StringUtil.isNotEmpty(sql)) {
					String regex = "\\bFROM\\s*\\S*";
					Pattern p = Pattern.compile(regex);
					Matcher m = p.matcher(sql.toUpperCase());
					if (m.find()) {
						tableName = m.group().replace("FROM", "");
					} else {
						LOGGER.info("not found table !");
					}
				}
				if (StringUtil.isNotEmpty(tableName)) {
					rst = connectionData.getColumns(null, "%", tableName.trim(), "%");
				} else {
					connection.close();
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				try {// 报错直接关闭
					connection.close();
					rst.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				return null;
			}
			List<Object> listColumn = new ArrayList<>();
			List<Object> list = null;
			try {
				while (rst.next()) {
					// 列名
					String columnName = rst.getString("COLUMN_NAME");
					// 类型
					String typeName = rst.getString("TYPE_NAME");
					// 长度
					String columnSize = rst.getString("COLUMN_SIZE");
					// 注释
					String remarks = rst.getString("REMARKS");
					list = new ArrayList<>();
					list.add(columnName);
					list.add(typeName);
					list.add(columnSize);
					list.add(remarks);
					listColumn.add(list);
				}
				return listColumn;
			} catch (SQLException e) {
				e.printStackTrace();
				try {// 报错直接关闭
					if (connection != null) {
						connection.close();
					};
					if (rst != null) {
						rst.close();
					};
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				return null;
			} finally {
				try {// 报错直接关闭
					if (connection != null) {
						connection.close();
					};
					if (rst != null) {
						rst.close();
					};
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} else {
			return null;
		}
	}
	
	/**
	 * 查询或导出数据格式化
	 * @param dataTypes 数据库类型
	 * @param char_type 字段类型
	 * @param obj_value 值
	 * @param decimalPlaces 保留小数位数
	 * @return
	 */
	public static String typeFormat(String dataTypes,String char_type,String obj_value,Integer decimalPlaces){
		try{
			Date parse = null;//转换格式
			obj_value = dealWithData(obj_value);
			if(obj_value != null){
				if(CHARUtils.Date.equals(char_type)){//日期格式
					if(dataTypes.equals(CHARUtils.MYSQL)){
						parse = ConnectionDataSearchUtils.dateFormat.parse(obj_value); 
						obj_value = ConnectionDataSearchUtils.dateFormat.format(parse);
					}else if(dataTypes.equals(CHARUtils.ORACLE)){
						parse = ConnectionDataSearchUtils.dateFormats.parse(obj_value); 
						obj_value = ConnectionDataSearchUtils.dateFormats.format(parse);
					}
				}else if(CHARUtils.DateTime.equals(char_type)){//日期格式
					parse = ConnectionDataSearchUtils.dateFormats.parse(obj_value); 
					obj_value = ConnectionDataSearchUtils.dateFormats.format(parse);
				}else if(CHARUtils.Num.equals(char_type)){//数字格式
					obj_value = ReckonTools.add2BigDecimal(obj_value, "0", decimalPlaces);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return obj_value;
		}
		return obj_value;
	}
	
	
	/**
	 * 处理格式
	 * @param obj_value
	 * @return
	 */
	private static String dealWithData(String obj_value){
		if(obj_value == null || obj_value.equals("null") || obj_value.equals("")){
			return null;
		}
		return obj_value;
	}

	/**
	 * 根据选择类型查询数据信息
	 * 
	 * @param list
	 *            入参
	 * @param connection
	 *            数据链接信息
	 * @param tableName
	 *            表名称
	 * @return 返回查询信息
	 */
	public static List<Map<String, Object>> queryTableInfoByType(List<Map<String, Object>> list, Connection connection,
			String tableName) {
		// 存储需要拼装的信息
		Map<String, String> resultMap = new HashMap<>();
		for (Map<String, Object> map : list) {
			// 字符串类WHERE条件拼装
			String varcharCol = (String) map.get("varcharCol");
			if (!StringUtil.isEmpty(varcharCol) && !StringUtil.isEmpty((String) map.get("name"))) {
				switch (varcharCol) {
				case "包含":
					resultMap.put("sqlTypeVar",
							map.get("fuzzyColumn") + " LIKE CONCAT('%'," + "'" + map.get("name") + "'" + ",'%')");
					break;
				case "等于":
					resultMap.put("sqlTypeVar", map.get("preciseColumn") + " = " + map.get("name"));
					break;
				default:
					resultMap.put("sqlTypeVar", "");
				}
			}
			// INT类WHERE条件拼装
			String intCol = (String) map.get("intCol");
			if (!StringUtil.isEmpty(intCol) && !StringUtil.isEmpty((String) map.get("age"))) {
				switch (intCol) {
				case ">=":
					resultMap.put("sqlTypeInt", map.get("preciseColumn") + " >= " + "'" + map.get("age") + "'");
					break;
				case "<=":
					resultMap.put("sqlTypeInt", map.get("preciseColumn") + " <= " + "'" + map.get("age") + "'");
					break;
				case "=":
					resultMap.put("sqlTypeInt", map.get("preciseColumn") + " = " + "'" + map.get("age") + "'");
					break;
				default:
					resultMap.put("sqlTypeInt", "");
				}
			}
			// 日期类WHERE条件拼装
			String dateCol = (String) map.get("dateCol");
			if (!StringUtil.isEmpty(dateCol) && !StringUtil.isEmpty((String) map.get("startTime"))) {
				switch (dateCol) {
				case "date":
					resultMap.put("sqlTypeDate", "DATE_FORMAT(" + "'" + map.get("dateColumn") + "'" + ", "
							+ "'%Y-%m-%d')" + " = " + map.get("startTime"));
					break;
				default:
					resultMap.put("sqlTypeDate", "");
				}
			}
			String sumColumn = (String) map.get("sumColumn"); // 获取sum
			String groupBy = (String) map.get("groupByColumn"); // 获取分组
			String orderBy = (String) map.get("sortColumn"); // 获取排序
			String start = (String) map.get("start"); // 页码
			String limit = (String) map.get("limit"); // 每页条数
			// 验证获取参数是否为空
			if (!StringUtil.isEmpty(sumColumn) && !StringUtil.isEmpty((String) map.get("preciseColumn"))) {
				resultMap.put("sumColumn", sumColumn + "(" + map.get("preciseColumn") + ") ");
			}
			if (!StringUtil.isEmpty(groupBy)) {
				resultMap.put("groupBy", groupBy + " " + map.get("preciseColumn") + " ");
			}
			if (!StringUtil.isEmpty(orderBy)) {
				resultMap.put("orderBy", "ORDER BY " + map.get("dateColumn") + " " + orderBy);
			}
			if (!StringUtil.isEmpty(start) && !StringUtil.isEmpty(limit)) {
				resultMap.put("limit", " LIMIT " + start + ", " + limit);
			}
		}
		StringBuilder sql = new StringBuilder();
		// SQL拼装
		if (!StringUtil.isEmpty(resultMap.get("sumColumn"))) {
			sql.append("SELECT *, ").append(resultMap.get("sumColumn")).append(" FROM ").append(tableName)
					.append(" WHERE 1=1 ");
		} else {
			sql.append("SELECT * FROM ").append(tableName).append(" WHERE 1=1 ");
		}
		if (!StringUtil.isEmpty(resultMap.get("sqlTypeVar"))) {
			sql.append(" AND ").append(resultMap.get("sqlTypeVar"));
		}
		if (!StringUtil.isEmpty(resultMap.get("sqlTypeInt"))) {
			sql.append(" AND ").append(resultMap.get("sqlTypeInt"));
		}
		if (!StringUtil.isEmpty(resultMap.get("sqlTypeDate"))) {
			sql.append(" AND ").append(resultMap.get("sqlTypeDate"));
		}
		if (!StringUtil.isEmpty(resultMap.get("groupBy"))) {
			sql.append(resultMap.get("groupBy"));
		}
		if (!StringUtil.isEmpty(resultMap.get("orderBy"))) {
			sql.append(resultMap.get("orderBy"));
		}
		if (!StringUtil.isEmpty(resultMap.get("limit"))) {
			sql.append(resultMap.get("limit"));
		}
		// 根据SQL拼装条件查询数据
		com.mysql.jdbc.PreparedStatement pstmt = null;
		List<Map<String, Object>> listAll = new ArrayList<>();
		ResultSet rs = null;
		try {
			pstmt = (com.mysql.jdbc.PreparedStatement) connection.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Map<String, Object> result = new HashMap<>();
				String id = rs.getString(1);
				String name = rs.getString(2);
				String age = rs.getString(3);
				String crtTime = rs.getString(4);
				String sumAge = rs.getString(5);
				result.put("id", id);
				result.put("name", name);
				result.put("age", age);
				result.put("crtTime", crtTime);
				result.put("ageTotal", sumAge);
				listAll.add(result);
			}
		} catch (SQLException e) {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			LOGGER.error("系统错误" + e);
		}
		return listAll;
	}

}
