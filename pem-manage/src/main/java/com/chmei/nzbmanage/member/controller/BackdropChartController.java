package com.chmei.nzbmanage.member.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.member.bean.BackdropChartForm;
import com.chmei.nzbmanage.member.bean.TransferAccountsForm;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 二维码背景图控制器
 *
 * @author zhangshixu
 * @since 2020年5月20日 17点51分
 */
@RestController
@RequestMapping("/api/backdrop")
public class BackdropChartController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(BackdropChartController.class);

    /**
     * 查询二维码背景图列表
     *
     * @param backdropChartForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/queryBackdropChartList")
    public OutputDTO queryBackdropChartList(@ModelAttribute BackdropChartForm backdropChartForm) {
        OutputDTO outputDTO;
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(backdropChartForm);
            outputDTO = getOutputDTO(map, "backdropChartService", "queryBackdropChartList");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 新增二维码背景图
     *
     * @param backdropChartForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/saveBackdropChartInfo")
    public OutputDTO saveBackdropChartInfo(@ModelAttribute BackdropChartForm backdropChartForm) {
        OutputDTO outputDTO;
        try {
            // 创建人， 姓名
            backdropChartForm.setCrtUserId(getCurrUserId());
            backdropChartForm.setCrtUserName(getCurrUserName());
            Map<String, Object> map = BeanUtil.convertBean2Map(backdropChartForm);
            outputDTO = getOutputDTO(map, "backdropChartService", "saveBackdropChartInfo");
        } catch (Exception e) {
            LOGGER.error("保存失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 更新二维码背景图
     *
     * @param backdropChartForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/updateBackdropChartInfo")
    public OutputDTO updateBackdropChartInfo(@ModelAttribute BackdropChartForm backdropChartForm) {
        OutputDTO outputDTO;
        try {
            // 创建人， 姓名
            backdropChartForm.setModfUserId(getCurrUserId());
            backdropChartForm.setModfUserName(getCurrUserName());
            Map<String, Object> map = BeanUtil.convertBean2Map(backdropChartForm);
            outputDTO = getOutputDTO(map, "backdropChartService", "updateBackdropChartInfo");
        } catch (Exception e) {
            LOGGER.error("编辑失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 删除二维码背景图
     *
     * @param backdropChartForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/deleteBackdropChartInfo")
    public OutputDTO deleteBackdropChartInfo(@ModelAttribute BackdropChartForm backdropChartForm) {
        OutputDTO outputDTO;
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(backdropChartForm);
            outputDTO = getOutputDTO(map, "backdropChartService", "deleteBackdropChartInfo");
        } catch (Exception e) {
            LOGGER.error("保存失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 查询二维码背景图详情
     *
     * @param backdropChartForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/queryBackdropChartDetail")
    public OutputDTO queryBackdropChartDetail(@ModelAttribute BackdropChartForm backdropChartForm) {
        OutputDTO outputDTO;
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(backdropChartForm);
            outputDTO = getOutputDTO(map, "backdropChartService", "queryBackdropChartDetail");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 设置默认二维码背景图
     *
     * @param backdropChartForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/setDefaultBackdropChartInfo")
    public OutputDTO setDefaultBackdropChartInfo(@ModelAttribute BackdropChartForm backdropChartForm) {
        OutputDTO outputDTO;
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(backdropChartForm);
            outputDTO = getOutputDTO(map, "backdropChartService", "setDefaultBackdropChartInfo");
        } catch (Exception e) {
            LOGGER.error("设置失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

}
