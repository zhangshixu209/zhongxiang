package com.chmei.nzbmanage.noticetype.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.constant.Constants;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.noticetype.bean.Noticetype;
import com.chmei.nzbmanage.noticetype.bean.NoticetypeQueryForm;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 公告分类控制器
 *
 * @author wangteng
 * @since 2019年5月13日
 */
@RestController
@RequestMapping("/api/noticetype")
public class NoticetypeController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(NoticetypeController.class);

    /**
     * 插入公告分类信息
     * @param noticetypeQueryForm 封装实体类
     * @param request http请求
     * @param userId 登录用户id
     * @return OutputDTO 出参
     */
    @RequestMapping("/insertNoticetypeMessageInfo")
    public OutputDTO insertNoticetypeMessageInfo(@ModelAttribute Noticetype noticetypeQueryForm,
                                                 HttpServletRequest request, @SessionAttribute(Constants.SESSION_USER.ID) Long userId) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> map = BeanUtil.convertBean2Map(noticetypeQueryForm);
        map.put("insertId", userId);
        map.put("updateId", userId);
        try {
            outputDTO = getOutputDTO(map, "noticetypeService", "insertNoticetypeMessageInfo");
        } catch (Exception e) {
            LOGGER.error("新增失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     *更新公告分类信息
     * @param noticetypeQueryForm 实体封装类
     * @param request 请求
     * @param userId 当前登录用户id
     * @return OutputDTO 出参
     */
    @RequestMapping("/updateNoticetypeMessageInfo")
    public OutputDTO updateNoticetypeMessageInfo(@ModelAttribute Noticetype noticetypeQueryForm, HttpServletRequest request, @SessionAttribute(Constants.SESSION_USER.ID) Long userId) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> map = BeanUtil.convertBean2Map(noticetypeQueryForm);
        map.put("updateId", userId);
        try {
            outputDTO = getOutputDTO(map, "noticetypeService", "updateNoticetypeMessageInfo");
        } catch (Exception e) {
            LOGGER.error("更新失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     *删除公告分类信息
     * @param noticetypeQueryForm 实体封装类
     * @param request 请求
     *  @return OutputDTO 出参
     */
    @RequestMapping("/deleteNoticetypeMessageInfo")
    public OutputDTO deleteNoticetypeMessageInfo(@ModelAttribute Noticetype noticetypeQueryForm, HttpServletRequest request) {
        LOGGER.info("NoticetypeController.deleteNoticetypeMessageInfo删除数据:" + noticetypeQueryForm.getId());
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> map = BeanUtil.convertBean2Map(noticetypeQueryForm);
        try {
            outputDTO = getOutputDTO(map, "noticetypeService", "deleteNoticetypeMessageInfo");
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     *分页查询公告分类信息
     * @param noticetypeQueryForm 页面封装查询类
     * @param request 请求
     *  @return OutputDTO 出参
     */
    @RequestMapping("/queryNoticetypeMessageInfo")
    public OutputDTO queryNoticetypeMessageInfo(@ModelAttribute NoticetypeQueryForm noticetypeQueryForm, HttpServletRequest request) {
        OutputDTO outputDTO = new OutputDTO();
        String endTime = noticetypeQueryForm.getEndTime();
//        if (endTime != null && !"".equals(endTime)) {
//            noticetypeQueryForm.setEndTime(endTime + " 23:59:59");
//        }
        Map<String, Object> map = BeanUtil.convertBean2Map(noticetypeQueryForm);
        try {
            outputDTO = getOutputDTO(map, "noticetypeService", "queryNoticetypeMessageInfo");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     *查询单条公告分类信息(回显使用)
     * @param noticetypeQueryForm 实体封装类
     * @param request 请求
     *  @return OutputDTO 出参
     */
    @RequestMapping("/queryNoticrtypeDetailMessageInfo")
    public OutputDTO queryNoticrtypeDetailMessageInfo(@ModelAttribute Noticetype  noticetypeQueryForm, HttpServletRequest request) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> map = BeanUtil.convertBean2Map(noticetypeQueryForm);
        try {
            outputDTO = getOutputDTO(map, "noticetypeService", "queryNoticrtypeDetailMessageInfo");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 公告类型下拉列表加载
     * @return 出参
     */
    @RequestMapping("/queryAllNoticetypeMessageInfo")
    public OutputDTO queryAllNoticetypeMessageInfo() {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> map = new HashMap<>();
        try {
            outputDTO = getOutputDTO(map, "noticetypeService", "queryAllNoticetypeMessageInfo");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }




}
