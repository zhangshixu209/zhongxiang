package com.chmei.nzbcommon.onlinereport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chmei.nzbcommon.reportcommon.CHARUtils;
import com.chmei.nzbcommon.reportcommon.ConnectionDataSearchUtils;
import com.chmei.nzbcommon.reportcommon.TableUtils;

/**
 * 封装公用的连接远程数据库获取数据,在线数据管理模块
 * 
 * @author zcf
 * @since 2019年5月7日 16点37分
 */
public final class ConnRemoteDataSearchUtils {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ConnRemoteDataSearchUtils.class);

	/**
	 * 私有构造器 不允许实例化
	 */
	private ConnRemoteDataSearchUtils() {
	}

	/**年月日时分秒*/
	public static SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	/**年月日*/
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	
	
	// 使用static，这样每个线程拿到的是同一把锁
    private static ReentrantLock lock = new ReentrantLock(true);

	
	/**
	 * 连接远程数据库,执行sql（创建数据表，sql查询）|查询数据|
	 * @param connInfo   数据库信息
	 * @param sql        执行sql
	 * @param colList    查询展示列
	 * @param searchList 查询条件列
	 * @param search_Sort 排序列
	 * @param map        人为干预字段信息
	 * @param start      起始
	 * @param limit      结束
	 * @param nums       类型：2、执行sql并有返回值 1、执行sql的insert没有返回值
	 * @return
	 */
	@SuppressWarnings("resource")
	public static Map<String, Object> connRemotoDB(Map<String, Object> connInfo,String sql,String totalSQL,
													List<String> colList,String searchSQL,Map<String,Object> map,
													String search_Sort,String fileName, int start, int limit,int nums,
													List<Map<String,Object>> headerList) {
		Map<String, Object> mapAll = new HashMap<>();//全局存储
		
		// 存放结果
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		// 数据库链接
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			lock.lock();//加锁
			String data_type = MapUtils.getString(connInfo, "dataType");// 1jdbc// 2odbc
			String data_basees = MapUtils.getString(connInfo, "dataBasees");// Mysql// Oracle
			String realSql = sql+searchSQL;
			// 如果SQL中带有order by 拼接排序 -- 2019年8月26日修改---start
			if(search_Sort != null){//说明前台传递有排序字段
				int indexOf = realSql.toUpperCase().indexOf(" BY ");
				if(indexOf != -1){//存在
					realSql = realSql.substring(0, indexOf) + " by "+ search_Sort +","+realSql.substring(indexOf+3);
				}else{//原始sql没有排序
					realSql = realSql + " order by "+search_Sort;
				}
			}
			LOGGER.info("完整的分页sql语句::" + realSql);
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
			String url = MapUtils.getString(connInfo, "connMode"); // "jdbc:mysql://192.168.0.206:3306/report_manage_db";
			String user = MapUtils.getString(connInfo, "loginAcct"); // "report_manage";
			String password = MapUtils.getString(connInfo, "loginPwd"); // "1qaz@WSX";
			conn = DriverManager.getConnection(url, user, password);
			if (conn == null) {//需要处理
				return null;
			}
			String pagesSQL = pagesDealWith(data_basees, realSql,start, limit);
			switch(nums){
			case 0://查询是否存在
				pstmt = conn.prepareStatement(pagesSQL);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					Map<String,Object> map_emp = new HashMap<String, Object>();
					//执行表示存在
					Object object = rs.getObject(fileName);
					if(object == null){
						return map_emp;
					}else{
						map_emp.put(fileName, object);
						return map_emp;
					}
				}
				break;
			case 1:
				pstmt = conn.prepareStatement(pagesSQL);
				pstmt.executeUpdate();//insert和update,不需要返回值
				break;
			case 2:
				//获取总条数
				pstmt = conn.prepareStatement(totalSQL);
				rs = pstmt.executeQuery();
				int total = 0;
	            if (rs.next()) {
	               total = rs.getInt("row_s");
	               mapAll.put("total", total);
	            }
	            if(total > 0){
	                String pagesSQL2 = pagesDealWithDT(data_basees, realSql,start, limit);
	            	pstmt = conn.prepareStatement(pagesSQL2);
	            	rs = pstmt.executeQuery();
	            	//组装执行结果并返回
	            	items = dealWithResult(rs, items, colList,map,headerList,data_basees);
	            	mapAll.put("items", items);
	            }
				break;
			default:
				LOGGER.info("没有满足需要执行...");
			}
			return mapAll;
		} catch (Exception e) {
			close(conn, pstmt, rs);
			LOGGER.error("查询失败," + e.getMessage());
			return null;
		} finally {
			lock.unlock();//释放
			close(conn, pstmt, rs);
		}
	}

	/**
	 * 分页拼接sql处理
	 * @param data_basees
	 * @param realSql
	 * @param start
	 * @param limit
	 * @return
	 */
	private static String pagesDealWith(String data_basees,String realSql,
			                            int start,int limit){
		//分页处理
		if (data_basees.equals(CHARUtils.MYSQL)) {
			//添加排序
			realSql += " LIMIT "+ start+" , "+limit;
		} else if (data_basees.equals(CHARUtils.ORACLE)) {
		    // 计算出开始行数和结束行数 如:查询第一页传入 0，10，计算出 1,10
            int startRows = start + 1; // >= 开始行 +1
            int endRows = start + limit; // <= 开始行+每页展示条数
		    
			StringBuilder querySqlStart = new StringBuilder("select * from (select * from (select * from (select a.*,rownum rn from(");
			StringBuilder querySqlEnd = new StringBuilder(")a where rownum <=" + endRows + ") where rn>=")
					.append(startRows); // >= 放到里面那一层效率更高
			realSql = querySqlStart.toString() + realSql + querySqlEnd.toString();
		}
		return realSql;
	}
	
	
	/**
	 * 查询结果组装数据并返回
	 * @param rs
	 * @param items
	 * @param selectColumns
	 * @param headerList  字段名称和类型
	 * @return
	 */
	@SuppressWarnings("unused")
	private static List<Map<String, Object>> dealWithResult(ResultSet rs,List<Map<String, Object>> items,
			                                                List<String> selectColumns,Map<String,Object> map,
			                                                List<Map<String,Object>> headerList,String data_basees){
		//获取字段名称和类型
		Map<String,String> mapFormat = dealWithFormat(headerList);
		// 获取结果集
		try {
			while (rs.next()) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				for (int i = 0; i < selectColumns.size(); i++) {
					String columnName = selectColumns.get(i); // 列名
					Object obj_emp = rs.getObject(columnName.trim());
					String obj_value = obj_emp == null ? "" : obj_emp.toString().trim();
					//人工干预字段下拉框和文本域特殊处理
					Object obj_type = map.get(columnName.trim());
					if(obj_type != null){
						//已经录入显示成功录入的值；没有录入显示下拉框或文本域初始化
						resultMap.put(columnName.trim(), obj_value == "" ?obj_type:obj_value);
					}else{
						//做日期类型格式化处理
						String char_type = mapFormat.get(columnName.trim());//字段类型
						String typeFormat = TableUtils.typeFormat(data_basees,char_type, obj_value,4);
						resultMap.put(columnName.trim(), typeFormat);
					}
				}
				items.add(resultMap);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
		
	}
	
	
	/**
	 * 数据格式处理 map 字段名称和类型
	 * @param headerList
	 * @return
	 */
	private static Map<String,String> dealWithFormat(List<Map<String,Object>> headerList){
		Map<String,String> map = new HashMap<>();//存储类型和字段名称
		if(headerList == null || headerList.size() == 0){
			return map;
		}
		for (int i = 0; i < headerList.size(); i++) {
			Map<String, Object> map2 = headerList.get(i);
			Object name = map2.get("colName");
			Object type = map2.get("colType");
			//单独处理日期格式问题
			if(type.toString().trim().indexOf("date") != -1){
				type = map2.get("dbColType");
			}
			map.put(name.toString(), type.toString());
		}
		return map;
	}
	
	
	
	/**
     * 连接远程数据库,执行SQL(更新人工干预字段信息)
     * @param connInfo   数据库信息
     * @param sql        执行SQL
     * @return
     */
    public static Map<String, Object> connRemotoDB(Map<String, Object> connInfo,String sql) {
        Map<String, Object> mapAll = new HashMap<>(); // 全局存储
        // 数据库链接
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            lock.lock();//加锁
            String data_type = MapUtils.getString(connInfo, "dataType");
            String data_basees = MapUtils.getString(connInfo, "dataBasees"); // MySql // Oracle
            if (data_type.equals("1")) { // JDBC
                if (data_basees.equals(CHARUtils.MYSQL)) {
                    Class.forName("com.mysql.jdbc.Driver");
                } else {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                }
            } else if (data_type.equals("2")) {// ODBC
                return null;
            } else {
                return null;
            }
            String url = MapUtils.getString(connInfo, "connMode"); // "jdbc:mysql://192.168.0.206:3306/report_manage_db";
            String user = MapUtils.getString(connInfo, "loginAcct"); // "report_manage";
            String password = MapUtils.getString(connInfo, "loginPwd"); // "1qaz@WSX";
            conn = DriverManager.getConnection(url, user, password);
            if (conn == null) {//需要处理
                return null;
            }
            //预置对象
            pstmt = conn.prepareStatement(sql);
            //执行SQL语句，返回影响行数
            int res = pstmt.executeUpdate();
            if(res > 0){
                mapAll.put("flag", "success");
            } else {
                mapAll.put("flag", "error");
            }
            return mapAll;
        } catch (Exception e) {
            close(conn, pstmt, rs);
            LOGGER.error("查询失败," + e.getMessage());
            return null;
        } finally {
            lock.unlock(); // 释放
            close(conn, pstmt, rs);
        }
    }
    
    /**
     * 分页拼接sql处理
     * @param data_basees
     * @param realSql
     * @param start
     * @param limit
     * @return
     */
    private static String pagesDealWithDT(String data_basees,String realSql,
                                        int start,int limit){
        //分页处理
        if (data_basees.equals(CHARUtils.MYSQL)) {
            //添加排序
            realSql += " LIMIT "+ start+" , "+limit;
        } else if (data_basees.equals(CHARUtils.ORACLE)) {
            // 计算出开始行数和结束行数 如:查询第一页传入 0，10，计算出 1,10
            int startRows = start + 1; // >= 开始行 +1
            int endRows = start + limit; // <= 开始行+每页展示条数
            
            StringBuilder querySqlStart = new StringBuilder("select * from (select a.*,rownum rn from(");
            StringBuilder querySqlEnd = new StringBuilder(")a where rownum <=" + endRows + ") where rn>=")
                    .append(startRows); // >= 放到里面那一层效率更高
            realSql = querySqlStart.toString() + realSql + querySqlEnd.toString();
        }
        return realSql;
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
		ConnectionDataSearchUtils.closeResultSet(rs);
		ConnectionDataSearchUtils.closeStatement(st);
		ConnectionDataSearchUtils.closeConn(conn);
	}
	
}
