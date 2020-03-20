package com.chmei.nzbmanage.zxgoods.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbcommon.util.UuidUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.common.exception.NzbManageException;
import com.chmei.nzbmanage.configure.FileUploadPathConfig.UploadFilePathConfig;
import com.chmei.nzbmanage.zxgoods.bean.ZxBusinessAuthForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 商家认证控制器
 * 
 * @author zhangshixu
 * @since 2020年3月17日 18点46分
 */
@RestController
@RequestMapping("/api/business")
public class ZxBusinessAuthController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(ZxBusinessAuthController.class);
    
    /**
     * 上传下载文件
     */
    @Autowired
    private UploadFilePathConfig uploadFilePathConfig;
    
    /**
     * 初始化加载商家认证查询列表
     *
     * @param zxBusinessAuthForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryBusinessAuthList")
    public OutputDTO queryBusinessAuthList(@ModelAttribute ZxBusinessAuthForm zxBusinessAuthForm) {
    	LOGGER.info("初始化加载商家认证查询列表...RotationChartController.queryBusinessAuthList()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxBusinessAuthForm);
        outputDTO = getOutputDTO(params, "zxBusinessAuthService", "queryBusinessAuthList");
        return outputDTO;
    }

    /**
     * 文件上传
     * @param file 文件
     * @param request 请求
     * @param response 响应
     * @return outputDTO
     * @throws NzbManageException  异常处理
     */
    @RequestMapping("/uploadImg")
    public OutputDTO uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request,
                               HttpServletResponse response) throws NzbManageException {
        String path = uploadFilePathConfig.getUploadFolder();
        String url = uploadFilePathConfig.getFileUrlPath();

        String fileName = UuidUtil.get32UUID()
                + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        File targetFile = new File(path);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        OutputDTO outputDTO = new OutputDTO();
        // 保存
        try {
            File file1 = new File(path + fileName);
            file.transferTo(file1);
            map.put("fileName", url + fileName);
            outputDTO.setCode("0");
            outputDTO.setMsg("上传成功！");
            outputDTO.setItem(map);
        } catch (Exception e) {
            outputDTO.setCode("-1");
            outputDTO.setMsg("文件保存失败，请重新上传！");
        }
        return outputDTO;
    }

    /**
     * 新增商家认证
     * @param zxBusinessAuthForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveBusinessInfo")
    public OutputDTO saveBusinessInfo(@ModelAttribute ZxBusinessAuthForm zxBusinessAuthForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxBusinessAuthForm);
        outputDTO = getOutputDTO(params, "zxBusinessAuthService", "saveBusinessInfo");
        return outputDTO;
    }

    /**
     * 商家认证详情
     * @param zxBusinessAuthForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryBusinessAuthDetail")
    public OutputDTO queryBusinessAuthDetail(@ModelAttribute ZxBusinessAuthForm zxBusinessAuthForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxBusinessAuthForm);
        outputDTO = getOutputDTO(params, "zxBusinessAuthService", "queryBusinessAuthDetail");
        return outputDTO;
    }

    /**
     * 编辑商家认证
     * @param zxBusinessAuthForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/updateBusinessAuthInfo")
    public OutputDTO updateBusinessAuthInfo(@ModelAttribute ZxBusinessAuthForm zxBusinessAuthForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxBusinessAuthForm);
        outputDTO = getOutputDTO(params, "zxBusinessAuthService", "updateBusinessAuthInfo");
        return outputDTO;
    }

    /**
     * 删除商家认证
     * @param zxBusinessAuthForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/delBusinessAuthInfo")
    public OutputDTO delBusinessAuthInfo(@ModelAttribute ZxBusinessAuthForm zxBusinessAuthForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxBusinessAuthForm);
        outputDTO = getOutputDTO(params, "zxBusinessAuthService", "delBusinessAuthInfo");
        return outputDTO;
    }

    /**
     * 商家认证审核
     * @param zxBusinessAuthForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/authBusinessInfo")
    public OutputDTO authBusinessInfo(@ModelAttribute ZxBusinessAuthForm zxBusinessAuthForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxBusinessAuthForm);
        outputDTO = getOutputDTO(params, "zxBusinessAuthService", "authBusinessInfo");
        return outputDTO;
    }

    /**
     * 商家认证开通发布窗口
     * @param zxBusinessAuthForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/openReleaseWindow")
    public OutputDTO openReleaseWindow(@ModelAttribute ZxBusinessAuthForm zxBusinessAuthForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxBusinessAuthForm);
        outputDTO = getOutputDTO(params, "zxBusinessAuthService", "openReleaseWindow");
        return outputDTO;
    }
}
