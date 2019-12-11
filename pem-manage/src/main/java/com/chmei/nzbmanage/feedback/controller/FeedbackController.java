package com.chmei.nzbmanage.feedback.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbcommon.util.UuidUtil;
import com.chmei.nzbmanage.common.constant.Constants;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.common.exception.NzbManageException;
import com.chmei.nzbmanage.configure.FileUploadPathConfig.UploadFilePathConfig;
import com.chmei.nzbmanage.feedback.bean.FeedbackForm;

/**
 * 反馈意见控制器
 * 
 * @author zhangshixu
 * @since 2019年10月22日 14点53分
 */
@RestController
@RequestMapping("/api/feedback")
public class FeedbackController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(FeedbackController.class);
    
    /**
     * 上传下载文件
     */
    @Autowired
    private UploadFilePathConfig uploadFilePathConfig;
    
    /**
     * 
     * 初始化加载反馈意见查询列表     
     * @param feedbackForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryList")
    public OutputDTO queryList(@ModelAttribute FeedbackForm feedbackForm) {
    	LOGGER.info("初始化加载反馈意见查询列表...FeedbackController.queryList()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(feedbackForm);
        //过滤权限
        Long user_Id = (Long) getSession().getAttribute(Constants.SESSION_USER.ID);
        String user_roles = (String) getSession().getAttribute(Constants.SESSION_USER.ROLE_IDS);
        params.put("startPensonId", user_Id);
        if((user_roles).indexOf(Constants.ROOT_ROLE) > -1){
        	params.put("startPensonId", null);
        }
        outputDTO = getOutputDTO(params, "feedbackService", "queryList");
        return outputDTO;
    }
    
    /**
     * 查询详情
     * @param feedbackForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/selectByPrimaryKey")
    public OutputDTO selectByPrimaryKey(@ModelAttribute FeedbackForm feedbackForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(feedbackForm);
        outputDTO = getOutputDTO(params, "feedbackService", "selectByPrimaryKey");
        return outputDTO;
    }
    
    
    /**
     * 新增 /提交工单
     * @param feedbackForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/insertSelective")
    public OutputDTO insertSelective(@ModelAttribute FeedbackForm feedbackForm) {
        OutputDTO outputDTO = new OutputDTO();
        //获取当前登录人部门id
//        String userName = (String) getSession().getAttribute(Constants.SESSION_USER.USERNAME);
//        Long userId = (Long) getSession().getAttribute(Constants.SESSION_USER.ID);
//        feedbackForm.setStartPensonName(userName);
//        feedbackForm.setStartPensonId(userId+"");
        feedbackForm.setStartPensonTime(new Date()); //工单提交时间
        Map<String, Object> params = BeanUtil.convertBean2Map(feedbackForm);
        outputDTO = getOutputDTO(params, "feedbackService", "insertSelective");
        return outputDTO;
    }
    
    /**
     * 建议反馈回复
    * @param feedbackForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/doUpdateFeedback")
    public OutputDTO doUpdateFeedback(@ModelAttribute FeedbackForm feedbackForm) {
        OutputDTO outputDTO = new OutputDTO();
        //获取当前登录人id
        Long user_Id = (Long) getSession().getAttribute(Constants.SESSION_USER.ID);
        String userName = (String) getSession().getAttribute(Constants.SESSION_USER.USERNAME);
        feedbackForm.setEndPensonId(user_Id+"");
        feedbackForm.setEndPensonName(userName);
        Map<String, Object> params = BeanUtil.convertBean2Map(feedbackForm);
        outputDTO = getOutputDTO(params, "feedbackService", "doUpdateFeedback");
        return outputDTO;
    }
    
    /**
     * 删除
     * @param feedbackForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/deleteByPrimaryKey")
    public OutputDTO deleteByPrimaryKey(@ModelAttribute FeedbackForm feedbackForm) {
        LOGGER.info("FeedbackController.deleteByPrimaryKey删除数据:" + feedbackForm.getId());
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(feedbackForm);
        params.put("modfUserId", getCurrUserId());
        outputDTO = getOutputDTO(params, "feedbackService", "deleteByPrimaryKey");
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
    @RequestMapping("/uploadFile")
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

}
