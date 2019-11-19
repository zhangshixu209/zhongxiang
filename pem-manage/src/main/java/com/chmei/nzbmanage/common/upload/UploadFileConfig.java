package com.chmei.nzbmanage.common.upload;

import com.chmei.nzbmanage.common.constant.Constants;

/**
 * 上传下载模型类
 */
public class UploadFileConfig {

	private String storageClass;
	private String basePath;// 文件保存目录根路径
	private String cusPath;// 文件自定义子目录
	private String fileTypes;// 扩展名白名单
	private String newFileNamePref="";// 新文件名前缀
	private String newFileNameSeparator = "";// 文件名前缀分隔符
	private String newFileName;// 新文件名
	private String fileSuffix;//文件后缀
	private String originalFilename;// 原文件名
	private String errorCode;//错误代码
	private String errorMsg;//错误信息
	private long fileSize=10*1024*1024L;

	public UploadFileConfig() {

	}

	public UploadFileConfig(String basePath, String cusPath, String fileTypes) {
		this.basePath = basePath;
		this.cusPath = cusPath;
		this.fileTypes = fileTypes;
	}
	public UploadFileConfig(String basePath, String cusPath, String fileTypes,String newFileNamePref) {
		this.basePath = basePath;
		this.cusPath = cusPath;
		this.fileTypes = fileTypes;
		this.newFileNamePref=newFileNamePref;
	}
	public UploadFileConfig(String basePath, String cusPath, String fileTypes,String newFileNamePref,long fileSize) {
		this.basePath = basePath;
		this.cusPath = cusPath;
		this.fileTypes = fileTypes;
		this.newFileNamePref=newFileNamePref;
		this.fileSize=fileSize;
	}
	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}



	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		if(null!=basePath||basePath!="")
		{
		this.basePath = basePath;
		}
	}

	public String getFileTypes() {
		return fileTypes;
	}

	public void setFileTypes(String fileTypes) {
		this.fileTypes = fileTypes;
	}

	public String getNewFileNamePref() {
		return newFileNamePref;
	}

	public void setNewFileNamePref(String newFileNamePref) {
		this.newFileNamePref = newFileNamePref;
	}

	public String getNewFileNameSeparator() {
		if(null!=newFileNamePref&&newFileNamePref!="")
		{
		this.newFileNameSeparator = "-";
		}
		else
		{
			this.newFileNameSeparator ="";
		}
		return newFileNameSeparator;
	}

	public void setNewFileNameSeparator(String newFileNameSeparator) {
		if(null!=newFileNamePref&&newFileNamePref!="")
		{
		this.newFileNameSeparator = newFileNameSeparator;
		}	
	}

	public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

	public String getStorageClass() {
	    if(null==storageClass ||storageClass=="")
        {
            this.storageClass = Constants.DEFAULT_STORAGE_CLASS;
        }
		return storageClass;
	}

	public void setStorageClass(String storageClass) {
		if(null==storageClass ||storageClass=="")
		{			
			this.storageClass = storageClass;
		}
	}

	public String getCusPath() {
		return cusPath;
	}

	public void setCusPath(String cusPath) {
		this.cusPath = cusPath;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

}
