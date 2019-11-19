package com.chmei.nzbcommon.onlinereport;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chmei.nzbcommon.reportcommon.CHARUtils;

import java.sql.PreparedStatement;


/**
 * 数据表管理创建数据库, 导入数据源
 * 
 * @author zhangsx
 * @since 2019年9月9日 16点37分
 */
public final class ConnRemoteCreatTableUtils {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ConnRemoteCreatTableUtils.class);

	static Lock lock = new ReentrantLock();//添加同步锁
	
	/**
	 * 私有构造器 不允许实例化
	 */
	private ConnRemoteCreatTableUtils() {
	}

	/**年月日时分秒*/
	public static SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	/**年月日*/
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	
	
	/**
	 * 根据数据库字段拼接创建表SQL
	 * @param list  字段信息
	 * @param tableName  字段信息
	 * @return
	 */
	public static String createTableJoinTo(List<Map<String,Object>> list,String tableName,
	        String reportName, Connection connection){
		String flag = ""; // 创建成功状态
		PreparedStatement ps = null;
		try {
			lock.lock();
		    StringBuilder sql = new StringBuilder();
		    sql.append("CREATE TABLE ").append("EMP_"+tableName).append("(id bigint(20) primary key not null,");
	        if (list != null && list.size() > 0) {
	            for (Map<String, Object> map : list) {
	            	Object object = map.get("dbColType");
                    //sql需要根据字段类型拼接
                	switch(object.toString()){
                	case "float"://处理数字问题保证小数至少6位，小数位统一保证4位
                		Integer colLength = Integer.valueOf(map.get("colLength")+"");
                		colLength = colLength > 7?colLength:8;
                		sql.append(map.get("colName")).append(" ").append(object+("("+colLength+",4)"))
  	                   		.append(" ").append("COMMENT '").append(map.get("colRemark")).append("',");
                		break;
                	case "decimal"://处理数字问题保证小数至少6位，小数位统一保证4位
                		Integer colLength_d = Integer.valueOf(map.get("colLength")+"");
                		colLength_d = colLength_d > 7?colLength_d:8;
                		sql.append(map.get("colName")).append(" ").append(object+("("+colLength_d+",4)"))
  	                   		.append(" ").append("COMMENT '").append(map.get("colRemark")).append("',");
                		break;
                	case "date":case "datetime":
                		sql.append(map.get("colName")).append(" ").append(object).append(" ")
                			.append("COMMENT '").append(map.get("colRemark")).append("',");
                		break;
                	default://BIGINT,VARCHAR,INT,char
                		LOGGER.info("拼接sql：BIGINT,VARCHAR,INT,char->"+object);
                		sql.append(map.get("colName")).append(" ").append(map.get("dbColType")+("("+map.get("colLength")+")"))
                		.append(" ").append("COMMENT '").append(map.get("colRemark")).append("',");
                	}
                }
	        }
	        StringBuilder subSql = new StringBuilder();
	        subSql.append(sql.substring(0, sql.length() - 1)); // 去除最后的逗号
	        subSql.append(",crt_time").append(" datetime ").append("COMMENT '创建时间'");
	        subSql.append(",crt_user_name").append(" VARCHAR(20) ").append("COMMENT '创建人姓名'");
	        subSql.append(",modf_time").append(" datetime ").append("COMMENT '修改时间'");
	        subSql.append(",modf_user_name").append(" VARCHAR(20) ").append("COMMENT '修改人姓名'");
	        // 拼凑完 建表语句 设置默认字符集
	        subSql.append(")DEFAULT CHARSET=utf8 COMMENT = '").append(reportName).append("';");
            LOGGER.info("建表语句是：" + subSql);
            ps = (PreparedStatement) connection.prepareStatement(subSql.toString());
            int crt = ps.executeUpdate(); // DDL语句返回值为0;
            if (crt == 0) {
                flag = "数据表创建成功！";
            }
        } catch (SQLException e) {
        	e.printStackTrace();
            LOGGER.error("建表失败", e.getMessage());
		}finally{
			lock.unlock();
 			try {
	 			if(ps != null)
	 				ps.close();
	 			if(connection != null)
	 				connection.close(); // 关闭数据库连接
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	/**
     * 根据数据库字段拼接创建表SQL
     * @param list  字段信息
     * @param tableName  字段信息
     * @return
     */
    public static String createOracleTableJoinTo(List<Map<String,Object>> list, String tableName,
            String reportName, Connection connection){
        String flag = ""; // 创建成功状态
        Statement stmt = null;
        try {
        	lock.lock();
            StringBuilder sql = new StringBuilder();
            sql.append("CREATE TABLE ").append("EMP_"+tableName).append("(id number(20) not null primary key,");
            if (list != null && list.size() > 0) {
                for (Map<String, Object> map : list) {
                	Object object = map.get("dbColType");
                    //sql需要根据字段类型拼接
                	switch(object.toString()){
                	case "number":
                		//处理数字问题保证小数至少6位，小数位统一保证4位
                		Integer colLength = Integer.valueOf(map.get("colLength")+"");
                		colLength = colLength > 7?colLength:8;
                		sql.append(map.get("colName")).append(" ").append(object+("("+colLength+",4)")).append(",");
                		break;
                	case "date":
                		sql.append(map.get("colName")).append(" ").append(object).append(",");
                		break;
                	default://varchar2,char
                		LOGGER.info("拼接sql：varchar2,char->"+object);
                		sql.append(map.get("colName")).append(" ").append(object+("("+map.get("colLength")+")")).append(",");
                	}
                }
            }
            StringBuilder subSql = new StringBuilder();
            subSql.append(sql.substring(0, sql.length() - 1)); // 去除最后的逗号
            subSql.append(",crt_time").append(" DATE ");
            subSql.append(",crt_user_name").append(" VARCHAR2(20) ");
            subSql.append(",modf_time").append(" DATE ");
            subSql.append(",modf_user_name").append(" VARCHAR2(20) ");
            //添加其他备注
            list = addOtherFiles(list);
            
            // 拼凑完 建表语句 设置默认字符集
            subSql.append(")");
            LOGGER.info("建表语句是：" + subSql);
            stmt = connection.createStatement();
            int crt = stmt.executeUpdate(subSql.toString()); // DDL语句返回值为0;
            if (crt == 0) { // 表创建成功时执行
                StringBuilder subRem = new StringBuilder();
                subRem.append("COMMENT ON TABLE ").append("EMP_"+tableName)
                .append(" IS '").append(reportName).append("'");
                // 创建时带有 ; 会报ORA-00911: 无效字符，所以单独插入
                stmt.executeUpdate(subRem.toString());
                if (list != null && list.size() > 0) {
                    for (Map<String, Object> map2 : list) {
                        StringBuilder subCol = new StringBuilder();
                        subCol.append("COMMENT ON COLUMN ").append("EMP_"+tableName).append(".")
                        .append(map2.get("colName")).append(" is '").append(map2.get("colRemark"))
                        .append("'");
                        stmt.executeUpdate(subCol.toString()); // 单条增加注释
                    }
                }
                flag = "数据表创建成功！";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("建表失败", e.getMessage());
        }finally{
        	lock.unlock();
    		try {
    			if(stmt != null)
    				stmt.close();
    			if(connection != null)
    				connection.close(); // 关闭数据库连接
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        return flag;
    }
    
    
    /**
     * 添加额外字段备注  oracle
     * @param list
     * @return
     */
    private static List<Map<String,Object>> addOtherFiles(List<Map<String,Object>> list){
    	 //添加其他备注
    	Map<String,Object> map = new HashMap<>();
    	map.put("colName", "id");
    	map.put("colRemark", "ID");
    	list.add(map);
    	Map<String,Object> map0 = new HashMap<>();
    	map0.put("colName", "crt_time");
    	map0.put("colRemark", "创建时间");
    	list.add(map0);
    	//添加其他备注
    	Map<String,Object> map1 = new HashMap<>();
    	map1.put("colName", "crt_user_name");
    	map1.put("colRemark", "创建人");
    	list.add(map1);
    	//添加其他备注
    	Map<String,Object> map2 = new HashMap<>();
    	map2.put("colName", "modf_time");
    	map2.put("colRemark", "修改时间");
    	list.add(map2);
    	//添加其他备注
    	Map<String,Object> map3 = new HashMap<>();
    	map3.put("colName", "modf_user_name");
    	map3.put("colRemark", "修改人");
    	list.add(map3);
    	return list;
    }
    
    /**
     * 执行数据库插入操作
     *
     * @param datas     插入数据表中key为列名和value为列对应的值的Map对象的List集合
     * @param tableName 要插入的数据库的表名
     * @return 影响的行数
     * @throws SQLException SQL异常
     */
    public static int dataTableBatchInsert(List<Map<String, Object>> datas, String tableName,
    									   Connection connection, Map<String, Object> mapFileInfo,
    									   String dataBasees) throws SQLException {
    	
    	mapFileInfo.put("crt_time", "date");//单独处理创建时间
        /**影响的行数**/
        int affectRowCount = -1;
        PreparedStatement pst = null;
        try {
        	lock.lock();
            /**从数据库连接池中获取数据库连接**/
            Map<String, Object> valueMap = datas.get(0);
            /**获取数据库插入的Map的键值对的值**/
            Set<String> keySet = valueMap.keySet();
            Iterator<String> iterator = keySet.iterator();
            /**要插入的字段sql，其实就是用key拼起来的**/
            StringBuilder columnSql = new StringBuilder();
            /**要插入的字段值，其实就是？**/
            StringBuilder unknownMarkSql = new StringBuilder();
            Object[] keys = new Object[valueMap.size()];
            int i = 0;
            while (iterator.hasNext()) {
                String key = iterator.next();
                keys[i] = key;
                columnSql.append(i == 0 ? "" : ",").append(key);
                String fileType = mapFileInfo.get(key)+"";//字段类型\
                switch(fileType){
		           	 case "date"://oracle|mysql
		           		 if(dataBasees.equals("Oracle")){
		           			 unknownMarkSql.append(i == 0 ? "" : ",").append("to_date(?,'yyyy-MM-dd HH24:MI:SS')");
		           		 } else {
		           		     unknownMarkSql.append(i == 0 ? "" : ",").append("?");
		           		 }
		                 break;
		           	 default:
		           		 unknownMarkSql.append(i == 0 ? "" : ",").append("?");
		           		 LOGGER.info("仅处理日期类型字段....key_filename:"+key);
	           	}
                i++;
            }
            /**开始拼插入的SQL语句**/
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO ");
            sql.append("EMP_" + tableName);
            sql.append(" (");
            sql.append(columnSql);
            sql.append(" )  VALUES (");
            sql.append(unknownMarkSql);
            sql.append(" )");
            /**执行SQL预编译**/
            pst = connection.prepareStatement(sql.toString());
            /**设置不自动提交，以便于在出现异常的时候数据库回滚**/
            connection.setAutoCommit(false);
            LOGGER.info("新增远程数据库->SQL：" + sql.toString());
            for (int j = 0; j < datas.size(); j++) {
            	//日期类型的字段需要格式化
                for (int k = 0; k < keys.length; k++) {
                	Object value = datas.get(j).get(keys[k]);
                	pst.setObject(k + 1,value.equals("")?null:value);
                }
                pst.addBatch();
            }
            int[] arr = pst.executeBatch();
            connection.commit();
            affectRowCount = arr.length;
            System.out.println("成功了插入了" + affectRowCount + "行");
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
        	lock.unlock();
            if (pst != null) {
            	pst.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return affectRowCount;
    }
    
    
   
	/**
	 * 判断表是否存在
	 * @param tableName 表名称
	 * @param conn 连接信息
	 * @return
	 * @throws SQLException
	 */
    public static boolean validateTableExist(String tableName, Connection conn) {
        boolean flag = false;
        ResultSet rs = null;
        try {
            DatabaseMetaData meta = conn.getMetaData();
            String type [] = {"TABLE"};
            rs = meta.getTables(null, null, tableName.toUpperCase(), type);
            flag = rs.next(); // 存在返回true
        } catch (SQLException e) {
            e.printStackTrace();
            try { // 报错直接关闭连接
                conn.close();
                rs.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }finally{
			try {
				if(conn != null)// 关闭连接
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
        }
        return flag;
    }
    
    /**
     * 删除远程数据信息
     * @param tableName 表名称
     * @param dataId 数据ID
     * @param conn 连接信息
     * @throws ClassNotFoundException 
     */
    public static String delDataTableInfo(String tableName, String dataId, 
            Map<String, Object> connInfo) throws ClassNotFoundException{
        Connection conn = null;
        String flag = "";
        try {
            String data_type = MapUtils.getString(connInfo, "dataType");
            String data_basees = MapUtils.getString(connInfo, "dataBasees"); // MySql // Oracle
            if (data_type.equals("1")) { // JDBC
                if (data_basees.equals(CHARUtils.MYSQL)) {
                    Class.forName("com.mysql.jdbc.Driver");
                } else {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                }
            } else if (data_type.equals("2")) {// ODBC
                return flag;
            } else {
                return flag;
            }
            String url = MapUtils.getString(connInfo, "connMode"); // "jdbc:mysql://192.168.0.206:3306/report_manage_db";
            String user = MapUtils.getString(connInfo, "loginAcct"); // "report_manage";
            String password = MapUtils.getString(connInfo, "loginPwd"); // "1qaz@WSX";
            conn = DriverManager.getConnection(url, user, password);
            if (conn == null) {//需要处理
                return flag;
            }
            String sql = "delete from " + tableName + " where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, dataId);
            int count = pstmt.executeUpdate();
            if (count > 0) {
                flag = "数据删除成功！";
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try { // 报错直接关闭连接
                conn.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }finally{
            try {
                if(conn != null)// 关闭连接
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } 
        }
        return flag;
    }
    
	/**
     * 数据源配置中检验数据库连接信息是否正确
     * @param url 数据库链接地址
     * @param user 用户名
     * @param password 密码
     * @param sql 查询SQL
     * @param type 1 JDBC 2 ODBC
     * @return 成功 0, 失败 其他信息
     */
    public static Connection databaseConnectionCheck(String url, String user, String password, int type,
            String dataBasees) {
        Connection connection = null;
        if (type == 1 || type == 2) {
            switch (type) {
            case 1:// JDBC
                try {
                    if (dataBasees.equals("Mysql")) { // MySql
                        Class.forName("com.mysql.jdbc.Driver");
                    } else { // Oracle
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                    }
                    connection = DriverManager.getConnection(url, user, password);
                    return connection;
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
            case 2:// ODBC
                try {
                    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                    connection = DriverManager.getConnection(url, user, password);
                    return connection;
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
            default:
                LOGGER.info("未知连接错误........url:" + url);
            }
        } else {
            LOGGER.info("未知连接错误........url:" + url);
        }
        return connection;
    }
	
}
