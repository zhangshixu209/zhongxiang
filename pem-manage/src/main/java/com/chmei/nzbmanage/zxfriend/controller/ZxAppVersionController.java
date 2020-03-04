package com.chmei.nzbmanage.zxfriend.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.common.upload.FileSystemStorage;
import com.chmei.nzbmanage.common.upload.Storage;
import com.chmei.nzbmanage.common.upload.UploadFileConfig;
import com.chmei.nzbmanage.configure.FileUploadPathConfig.UploadFilePathConfig;
import com.chmei.nzbmanage.zxfriend.bean.ZxAppVersionForm;
import com.chmei.nzbmanage.zxfriend.bean.ZxFriendForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 版本号控制器
 * 
 * @author zhangshixu
 * @since 2019年11月15日 16点13分
 */
@RestController
@RequestMapping("/api/appVersion")
public class ZxAppVersionController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(ZxAppVersionController.class);

    /**
     * 上传下载文件
     */
    @Autowired
    private UploadFilePathConfig uploadFilePathConfig;
    @Autowired
    FileSystemStorage fileSystemStorage;

    /**
     * 版本号新增
     *
     * @param zxFriendForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveZxAppVersionInfo")
    public OutputDTO saveZxAppVersionInfo(@ModelAttribute ZxAppVersionForm zxFriendForm) {
    	LOGGER.info("版本号新增...ZxAppVersionController.saveZxAppVersionInfo()...");
        // 创建人， 姓名
        zxFriendForm.setCrtUserId(getCurrUserId());
        zxFriendForm.setCrtUserName(getCurrUserName());
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFriendForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxAppVersionService", "saveZxAppVersionInfo");
        return outputDTO;
    }

    /**
     * 版本号列表查询
     *
     * @param zxFriendForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryZxAppVersionList")
    public OutputDTO queryZxAppVersionList(@ModelAttribute ZxAppVersionForm zxFriendForm) {
        LOGGER.info("版本号列表查询...ZxAppVersionController.queryZxAppVersionList()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFriendForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxAppVersionService", "queryZxAppVersionList");
        return outputDTO;
    }

    /**
     * 版本号列表查询
     *
     * @param zxFriendForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/downloadQRCode")
    public void downloadQRCode(@ModelAttribute ZxAppVersionForm zxFriendForm, HttpServletResponse response) throws FileNotFoundException, UnsupportedEncodingException {
        String path = uploadFilePathConfig.getUploadFolder();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFriendForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxAppVersionService", "queryZxAppVersionList");
        Map<String, Object> map = outputDTO.getItems().get(0);
        String appUrl = (String) map.get("appUrl");
//        path += appUrl.replace("/upload/", "");
//        path = "D:\\uploadFiles\\b31048863fae48ec9c65e325026bccc1.apk";
        LOGGER.info("path========================"+appUrl);
        // path是指欲下载的文件的路径。
        File file = new File(appUrl);
        // 下载本地文件
        // 取得文件名。
        String fileName = file.getName(); // 文件的默认保存名
        String newName = "zhongxiang";
        // 读到流中
        InputStream inStream = new FileInputStream(appUrl);// 文件的存放路径
        // 设置输出的格式
        response.reset();
//        response.setContentType("bin");
        response.setHeader("Content-type", "application/vnd.android.package-archive");
        response.setHeader("Content-Disposition","inline; filename=\""+ URLEncoder.encode(newName, "UTF-8")+ ".apk\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
           while ((len = inStream.read(b)) > 0)
           response.getOutputStream().write(b, 0, len);
           inStream.close();
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

}
