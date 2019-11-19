package com.chmei.nzbmanage.common.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.chmei.nzbmanage.common.exception.NzbManageException;

/**
 * 文件上传存储实现
 *
 * @author
 */
public interface Storage {

    /**
     * 保存上传文件
     *
     * @param file 上传文件
     */
	UploadFileConfig store(MultipartFile file,UploadFileConfig uploadFileConfig) throws NzbManageException;

    /**
     * <p>用于文件下载等用途
     *
     * @return
     */
    void download(UploadFileConfig uploadFileConfig,HttpServletResponse response,HttpServletRequest request) throws NzbManageException;
    
    void download(UploadFileConfig uploadFileConfig,HttpServletResponse response) throws NzbManageException;

}
