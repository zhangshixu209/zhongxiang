package com.chmei.nzbmanage.right.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbmanage.common.constant.Constants;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.configure.FileUploadPathConfig.UploadFilePathConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**  
 * 数据库备份还原
 * @author   zhangshixu
 * @since    2019年12月7日 21点13分
 */
@RestController
@RequestMapping("/api/sys/backups")
public class SysSqlController extends BaseController{
	 /** 日志信息 */
	 private static final Logger LOGGER = LoggerFactory.getLogger(SysSqlController.class);

	 /**
	  * 上传下载文件
	  */
	 @Autowired
	 private UploadFilePathConfig uploadFilePathConfig;

	 /**
	  * 数据库备份
	  * @author zhangshixu
	  * @return
	  */
	 @RequestMapping("/backupsDBInfo")
     public OutputDTO backupsDBInfo() throws Exception{
		 String backName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date())+".sql";
		 String path = uploadFilePathConfig.getUploadFolder();
		 File fileSql = new File(path+"sql");
		 // 如果目录不存在
		 if (!fileSql.exists()) {
			 // 创建文件夹
			 fileSql.mkdirs();
		 }
		 File saveSql = new File(path+"sql/"+"pem_manage_db-"+backName);
		 StringBuilder sb = new StringBuilder();
		 //创建备份sql文件
		 if (!saveSql.exists()){
			 saveSql.createNewFile();
		 }
		 try {
			 String os = System.getProperty("os.name"); // 系统名称
			 if (os.toLowerCase().startsWith("win")) {
				 sb.append("D:/user/bin/mysqldump");
			 } else if (os.toLowerCase().startsWith("linux")) {
				 sb.append("/user/bin/mysqldump");
			 }
			 sb.append(" -h" + Constants.DB_HOST);       // 数据库地址
			 sb.append(" -u" + Constants.USER_NAME);     // 数据库用户名
			 sb.append(" -p" + Constants.USER_PWD);      // 数据库密码
			 sb.append(" " + Constants.DB_NAME + " > "); // 数据库名称
			 sb.append(path+"sql/"+Constants.DB_NAME+"-"+backName);
			 LOGGER.info("===========开始备份数据============");
			 Runtime runtime = Runtime.getRuntime();
			 int i = 0;
			 if (os.toLowerCase().startsWith("win")) {
				 Process process = runtime.exec("cmd /c" + sb.toString()); // window
				 if(process.waitFor() == 0){
					 Map<String,Object> map = new HashMap<>();
					 map.put("url", "/uploadFiles/sql/" + Constants.DB_NAME + "-"+backName);
					 i = 1; // mysqlService.insertMysql(map);
				 }
			 } else if (os.toLowerCase().startsWith("linux")) {
				 Process process = runtime.exec("/bin/sh -c " + sb.toString()); // linux
				 if(process.waitFor() == 0){
					 Map<String,Object> map = new HashMap<>();
					 map.put("url", "/uploadFiles/sql/" + Constants.DB_NAME + "-" + backName);
					 i = 0; // mysqlService.insertMysql(map);
				 }
			 }
			 if(i > 0){
				 return new OutputDTO("0", "备份成功");
			 }
			 return new OutputDTO("-1", "备份失败");
		 } catch (IOException e) {
			 LOGGER.error("系统异常", e);
		 }
		 return new OutputDTO("-1", "备份失败");
     }

	/**
	 * 数据库还原
	 * @author zhangshixu
	 * @return
	 */
	@RequestMapping("/reductionDBInfo")
	public OutputDTO reductionDBInfo(@RequestParam String filePath) throws Exception{
		String os = System.getProperty("os.name"); // 系统名称
		StringBuilder sb = new StringBuilder();
		if (os.toLowerCase().startsWith("win")) {
			sb.append("D:/user/bin/mysql");
		} else if (os.toLowerCase().startsWith("linux")) {
			sb.append("/user/bin/mysql");
		}
		sb.append(" -h" + Constants.DB_HOST);       // 数据库地址
		sb.append(" -u" + Constants.USER_NAME);     // 数据库用户名
		sb.append(" -p" + Constants.USER_PWD);      // 数据库密码
		sb.append(" " + Constants.DB_NAME + " < "); // 数据库名称
		sb.append(filePath);
		System.out.println("cmd命令为："+sb.toString());
		Runtime runtime = Runtime.getRuntime();
		LOGGER.info("===========开始还原数据============");
		try {
			int i = 0;
			if (os.toLowerCase().startsWith("win")) {
				Process process = runtime.exec("cmd /c" + sb.toString()); // window
				if(process.waitFor() == 0){
					Map<String,Object> map = new HashMap<>();
					map.put("url", filePath); // 数据库还原本地文件地址
					i = 1; // mysqlService.insertMysql(map);  // 存储还原记录到数据库
				}
			} else if (os.toLowerCase().startsWith("linux")) {
				Process process = runtime.exec("/bin/sh -c " + sb.toString()); // linux
				if(process.waitFor() == 0){
					Map<String,Object> map = new HashMap<>();
					map.put("url", filePath); // 数据库还原本地文件地址
					i = 0; // mysqlService.insertMysql(map); // 存储还原记录到数据库
				}
			}
			if(i > 0){
				return new OutputDTO("0", "还原成功");
			}
			return new OutputDTO("-1", "还原失败");
		} catch (Exception e) {
			LOGGER.error("系统异常", e);
		}
		return new OutputDTO("0", "还原失败");
	}

}
  
