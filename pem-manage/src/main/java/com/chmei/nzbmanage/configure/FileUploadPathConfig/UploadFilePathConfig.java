package com.chmei.nzbmanage.configure.FileUploadPathConfig;

import org.apache.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//import com.chmei.nzbmanage.reportmanage.controller.ReportCategoryController;

@ConfigurationProperties(prefix = "fileUpload")
@Configuration
public class UploadFilePathConfig extends WebMvcConfigurerAdapter {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(UploadFilePathConfig.class);

    private String fileUrlPath;
    
    private String staticAccessPath;

    private String uploadFolder;

    /**
     * 静态资源初始化映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	LOGGER.info("静态资源初始化映射:staticAccessPath"+staticAccessPath+"....uploadFolder"+uploadFolder);
        registry.addResourceHandler(staticAccessPath).addResourceLocations("file:"+uploadFolder);
    }
    

    public String getStaticAccessPath() {
        return staticAccessPath;
    }

    public void setStaticAccessPath(String staticAccessPath) {
        this.staticAccessPath = staticAccessPath;
    }

    public String getUploadFolder() {
        return uploadFolder;
    }

    public void setUploadFolder(String uploadFolder) {
        this.uploadFolder = uploadFolder;
    }

    public String getFileUrlPath() {
        return fileUrlPath;
    }

    public void setFileUrlPath(String fileUrlPath) {
        this.fileUrlPath = fileUrlPath;
    }
}
