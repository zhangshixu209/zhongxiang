package com.chmei.nzbmanage.common.upload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.chmei.nzbmanage.common.exception.NzbManageException;

/**
 * 文件系统存储实现
 *
 * @author
 */
@Service("FileSystemStorageService")
public class FileSystemStorage extends AbstractStorage {

	private static final Logger logger = LoggerFactory
			.getLogger(FileSystemStorage.class);
	private Path rootLocation = null;

	public void FilePathCheck(UploadFileConfig uploadFileConfig) {
		if (uploadFileConfig.getStorageClass().endsWith(
				this.getClass().getSimpleName())) {
			if (StringUtils.isEmpty(uploadFileConfig.getBasePath())) {
				throw new RuntimeException("文件保存路径不能为空");
			}
			rootLocation = Paths.get(uploadFileConfig.getBasePath()
					+ uploadFileConfig.getCusPath());
			File uploadDir = rootLocation.toFile();
			if (!uploadDir.exists()) {
				try {
					Files.createDirectories(rootLocation);
				} catch (IOException e) {
					logger.error("创建文件路径失败", e);
					throw new RuntimeException(e);
				}
			}
		}
	}

	@Override
	public UploadFileConfig store(MultipartFile file,
			UploadFileConfig uploadFileConfig) throws NzbManageException {
		try {
			uploadFileConfig.setErrorCode("0");
			uploadFileConfig.setErrorMsg("上传成功");
			if (!file.isEmpty()) {
				FilePathCheck(uploadFileConfig);
				uploadFileConfig = checkFile(file, uploadFileConfig);
				if (uploadFileConfig.getErrorCode() == "-1") {
					return uploadFileConfig;
				}
				String uuid = UUID.randomUUID().toString()
						.replaceAll("\\-", "");
				String fileName = file.getOriginalFilename();// 获取文件名
				String suffix = uploadFileConfig.getFileSuffix();// 文件后缀
				String newFileName = uploadFileConfig.getNewFileNamePref()
						+ uploadFileConfig.getNewFileNameSeparator() + uuid
						+ "." + suffix;// 新文件名
				Path path = this.rootLocation.resolve(newFileName);
				try (InputStream inputStream = file.getInputStream()) {
					Files.copy(inputStream, path);
				} catch (IOException e) {
					throw new NzbManageException("保存文件失败 "
							+ file.getOriginalFilename(), e);
				}
				uploadFileConfig.setNewFileName(newFileName);
				uploadFileConfig.setOriginalFilename(fileName);
				uploadFileConfig.setBasePath(uploadFileConfig.getBasePath());
				uploadFileConfig.setCusPath(uploadFileConfig.getCusPath());
			}
		} catch (Exception ex) {
			uploadFileConfig.setErrorCode("-1");
			uploadFileConfig.setErrorMsg("上传失败");
			logger.error("保存文件失败！",ex);
		}
		
		return uploadFileConfig;
	}

	@Override
	public void download(UploadFileConfig uploadFileConfig,
			HttpServletResponse response,HttpServletRequest request) throws NzbManageException

	{
		String fileUrl = uploadFileConfig.getBasePath()
				+ uploadFileConfig.getCusPath()
				+ uploadFileConfig.getNewFileName();
		File file = new File(fileUrl);
		if (file.exists()) {
			try {
				Path path = Paths.get(fileUrl);
				String contentType = null;

				contentType = Files.probeContentType(path);

				response.setContentType(contentType);// 设置Content-Type为文件的MimeType
				
				
				//获取浏览器信息
				logger.info(request.getHeader("User-Agent"));
				if(request.getHeader("User-Agent").toUpperCase().indexOf("MSIE")>=0
				   ||request.getHeader("User-Agent").indexOf("Trident/7.0")>=0){
					
					response.addHeader("Content-Disposition",
							"attachment;filename="
									+ URLEncoder.encode(uploadFileConfig
											.getOriginalFilename(),"UTF-8"));
					
				}else{
					response.addHeader("Content-Disposition",
							"attachment;filename="
									+ new String(uploadFileConfig
											.getOriginalFilename()
											.getBytes("UTF-8"), "ISO8859-1"));// 设置文件名
				}
				
								
				response.setHeader("Content-Length",
						String.valueOf(file.length()));

				try (InputStream inputStream = new BufferedInputStream(
						new FileInputStream(file))) {
					try (OutputStream outputStream = response.getOutputStream()) {
						IOUtils.copy(inputStream, outputStream);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		} else {
			throw new NzbManageException("文件不存在");
		}
	}
	
	
	
	@Override
	public void download(UploadFileConfig uploadFileConfig,
			HttpServletResponse response) throws NzbManageException

	{
		String fileUrl = uploadFileConfig.getBasePath()
				+ uploadFileConfig.getCusPath()
				+ uploadFileConfig.getNewFileName();
		File file = new File(fileUrl);
		if (file.exists()) {
			try {
				Path path = Paths.get(fileUrl);
				String contentType = null;

				contentType = Files.probeContentType(path);

				response.setContentType(contentType);// 设置Content-Type为文件的MimeType

				response.addHeader("Content-Disposition",
							"attachment;filename="
									+ new String(uploadFileConfig
											.getOriginalFilename()
											.getBytes("UTF-8"), "ISO8859-1"));// 设置文件名
							
				response.setHeader("Content-Length",
						String.valueOf(file.length()));

				try (InputStream inputStream = new BufferedInputStream(
						new FileInputStream(file))) {
					try (OutputStream outputStream = response.getOutputStream()) {
						IOUtils.copy(inputStream, outputStream);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		} else {
			throw new NzbManageException("文件不存在");
		}
	}

}
