package com.chmei.nzbmanage.member.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.member.bean.HelpArchiveForm;
import com.chmei.nzbmanage.member.bean.HelpArchiveForm;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 帮助文档控制器
 *
 * @author zhangshixu
 * @since 2020年5月20日 17点51分
 */
@RestController
@RequestMapping("/api/help")
public class HelpArchiveController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(HelpArchiveController.class);

    /**
     * 查询帮助文档列表
     *
     * @param helpArchiveForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/queryHelpArchiveList")
    public OutputDTO queryHelpArchiveList(@ModelAttribute HelpArchiveForm helpArchiveForm) {
        OutputDTO outputDTO;
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(helpArchiveForm);
            outputDTO = getOutputDTO(map, "helpArchiveService", "queryHelpArchiveList");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 新增帮助文档
     *
     * @param helpArchiveForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/saveHelpArchiveInfo")
    public OutputDTO saveHelpArchiveInfo(@ModelAttribute HelpArchiveForm helpArchiveForm) {
        OutputDTO outputDTO;
        try {
            // 创建人， 姓名
            helpArchiveForm.setCrtUserId(getCurrUserId());
            helpArchiveForm.setCrtUserName(getCurrUserName());
            Map<String, Object> map = BeanUtil.convertBean2Map(helpArchiveForm);
            outputDTO = getOutputDTO(map, "helpArchiveService", "saveHelpArchiveInfo");
        } catch (Exception e) {
            LOGGER.error("保存失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 更新帮助文档
     *
     * @param helpArchiveForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/updateHelpArchiveInfo")
    public OutputDTO updateHelpArchiveInfo(@ModelAttribute HelpArchiveForm helpArchiveForm) {
        OutputDTO outputDTO;
        try {
            // 创建人， 姓名
            helpArchiveForm.setModfUserId(getCurrUserId());
            helpArchiveForm.setModfUserName(getCurrUserName());
            Map<String, Object> map = BeanUtil.convertBean2Map(helpArchiveForm);
            outputDTO = getOutputDTO(map, "helpArchiveService", "updateHelpArchiveInfo");
        } catch (Exception e) {
            LOGGER.error("编辑失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 删除帮助文档
     *
     * @param helpArchiveForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/deleteHelpArchiveInfo")
    public OutputDTO deleteHelpArchiveInfo(@ModelAttribute HelpArchiveForm helpArchiveForm) {
        OutputDTO outputDTO;
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(helpArchiveForm);
            outputDTO = getOutputDTO(map, "helpArchiveService", "deleteHelpArchiveInfo");
        } catch (Exception e) {
            LOGGER.error("保存失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 查询帮助文档详情
     *
     * @param helpArchiveForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/queryHelpArchiveDetail")
    public OutputDTO queryHelpArchiveDetail(@ModelAttribute HelpArchiveForm helpArchiveForm) {
        OutputDTO outputDTO;
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(helpArchiveForm);
            outputDTO = getOutputDTO(map, "helpArchiveService", "queryHelpArchiveDetail");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

}
