package com.chmei.nzbmanage.common.upload;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.chmei.nzbmanage.common.exception.NzbManageException;

public abstract class AbstractStorage implements Storage {
	private static final Logger logger = LoggerFactory.getLogger(AbstractStorage.class);
	
	 public UploadFileConfig checkFile(MultipartFile file,UploadFileConfig uploadFileConfig) throws NzbManageException {
		 String regex = "\\.(\\w+)$";
	        Pattern pattern = Pattern.compile(regex);
	        String[] fileTypes = StringUtils.isEmpty(uploadFileConfig.getFileTypes()) ? new String[0] : uploadFileConfig.getFileTypes().split(",");
	            if (!file.isEmpty()) {
	                String fileName = file.getOriginalFilename();
	                if (StringUtils.isBlank(fileName)) {
	                	uploadFileConfig.setErrorCode("-1");
	                	uploadFileConfig.setErrorMsg("文件名不能为空");
	                	return uploadFileConfig;
	                }
	                if (!fileName.contains(".")) {
	                	uploadFileConfig.setErrorCode("-1");
	                	uploadFileConfig.setErrorMsg("文件没有后缀");
	                	return uploadFileConfig;
	                }
	        	
	                Matcher matcher = pattern.matcher(fileName);
	                String ext = matcher.find() ? matcher.group(1) : "未知文件类型";
	                String extension = checkMimeType(file);
	                uploadFileConfig.setFileSuffix(ext);
	                logger.info("当前文件：" + fileName + "；后缀名：" + ext + "；文件类型："  + extension);
	                if (!ArrayUtils.contains(fileTypes, ext.toLowerCase()) || !ArrayUtils.contains(fileTypes, extension.toLowerCase())) {
	                	uploadFileConfig.setErrorCode("-1");
	                	uploadFileConfig.setErrorMsg("文件类型不匹配");
	                	return uploadFileConfig;
	                }
	            	long fileSize = file.getSize();
					logger.info("文件名称=" + fileName + "&文件大小=" + fileSize+"字节");
					if (fileSize <= 0) {
			        	uploadFileConfig.setErrorCode("-1");
	                	uploadFileConfig.setErrorMsg("文件不能为空");
	                	return uploadFileConfig;
					} 
						if (fileSize >uploadFileConfig.getFileSize()) {
			        	uploadFileConfig.setErrorCode("-1");
	                	uploadFileConfig.setErrorMsg("上传失败,文件大小不能超过限制");
	                	return uploadFileConfig;
					}
	            }
	            return uploadFileConfig;
	    }
	 private String checkMimeType(MultipartFile file) throws NzbManageException {
	        org.apache.tika.mime.MediaType fileType;
	        String extension;
	        TikaConfig tikaConfig = TikaConfig.getDefaultConfig();
	        Metadata metadata = new Metadata();
	        metadata.set(Metadata.RESOURCE_NAME_KEY, file.getOriginalFilename());
	        try (
	                TikaInputStream tikaInputStream = TikaInputStream.get(file.getInputStream());
	        ) {
	            fileType = tikaConfig.getDetector().detect(tikaInputStream, metadata);
	            MimeType mimeType = tikaConfig.getMimeRepository().forName(fileType.toString());
	            extension = mimeType.getExtension();
	        } catch (IOException | MimeTypeException e) {
	            logger.error("获取文件类型失败",e);
	            throw new NzbManageException("获取文件类型失败");
	        }
	        return extension == null || "".equals(extension) ? "" : extension.substring(1);
	    }

	 
}
