package com.chmei.nzbmanage.rotation.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbcommon.util.UuidUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.common.exception.NzbManageException;
import com.chmei.nzbmanage.configure.FileUploadPathConfig.UploadFilePathConfig;
import com.chmei.nzbmanage.rotation.bean.RotationChartForm;
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
 * 轮播图控制器
 * 
 * @author zhangshixu
 * @since 2019年10月21日 16点56分
 */
@RestController
@RequestMapping("/api/rotation")
public class RotationChartController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(RotationChartController.class);
    
    /**
     * 上传下载文件
     */
    @Autowired
    private UploadFilePathConfig uploadFilePathConfig;
    
    /**
     * 初始化加载轮播图查询列表
     *
     * @param rotationChartForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryRotationChartList")
    public OutputDTO queryRotationChartList(@ModelAttribute RotationChartForm rotationChartForm) {
    	LOGGER.info("初始化加载反馈意见查询列表...RotationChartController.queryRotationChartList()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(rotationChartForm);
        outputDTO = getOutputDTO(params, "rotationChartService", "queryRotationChartList");
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
     * 新增轮播图
     * @param rotationChartForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveRotationChartInfo")
    public OutputDTO saveRotationChartInfo(@ModelAttribute RotationChartForm rotationChartForm) {
        OutputDTO outputDTO = new OutputDTO();
        // 创建人， 姓名
        rotationChartForm.setCrtUserId(getCurrUserId());
        rotationChartForm.setCrtUserName(getCurrUserName());
        Map<String, Object> params = BeanUtil.convertBean2Map(rotationChartForm);
        outputDTO = getOutputDTO(params, "rotationChartService", "saveRotationChartInfo");
        return outputDTO;
    }

    /**
     * 轮播图详情
     * @param rotationChartForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryRotationChartDetail")
    public OutputDTO queryRotationChartDetail(@ModelAttribute RotationChartForm rotationChartForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(rotationChartForm);
        outputDTO = getOutputDTO(params, "rotationChartService", "queryRotationChartDetail");
        return outputDTO;
    }

    /**
     * 编辑轮播图
     * @param rotationChartForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/updateRotationChartInfo")
    public OutputDTO updateRotationChartInfo(@ModelAttribute RotationChartForm rotationChartForm) {
        OutputDTO outputDTO = new OutputDTO();
        // 修改人， 姓名
        rotationChartForm.setModfUserId(getCurrUserId());
        rotationChartForm.setModfUserName(getCurrUserName());
        Map<String, Object> params = BeanUtil.convertBean2Map(rotationChartForm);
        outputDTO = getOutputDTO(params, "rotationChartService", "updateRotationChartInfo");
        return outputDTO;
    }

    /**
     * 删除轮播图
     * @param rotationChartForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/deleteRotationChartInfo")
    public OutputDTO deleteRotationChartInfo(@ModelAttribute RotationChartForm rotationChartForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(rotationChartForm);
        outputDTO = getOutputDTO(params, "rotationChartService", "deleteRotationChartInfo");
        return outputDTO;
    }

    /**
     * 修改轮播图在线状态
     * @param rotationChartForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/updateOnlineStatus")
    public OutputDTO updateOnlineStatus(@ModelAttribute RotationChartForm rotationChartForm) {
        OutputDTO outputDTO = new OutputDTO();
        // 修改人， 姓名
        rotationChartForm.setModfUserId(getCurrUserId());
        rotationChartForm.setModfUserName(getCurrUserName());
        Map<String, Object> params = BeanUtil.convertBean2Map(rotationChartForm);
        outputDTO = getOutputDTO(params, "rotationChartService", "updateOnlineStatus");
        return outputDTO;
    }

}
