package com.chmei.nzbmanage.notice.controller;

import com.alibaba.fastjson.JSONObject;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.constant.Constants;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.notice.bean.Notice;
import com.chmei.nzbmanage.notice.bean.NoticeQueryForm;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 公告管理与我的公告控制器
 * 
 * @author wangteng
 * @since 2019年5月13日
 */
@RestController
@RequestMapping("/api/notice")
public class NoticeController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(NoticeController.class);

    /**
     *加载公告状态列表
     * @return 出参
     */
    @RequestMapping("/queryNoticeStatusMessageInfo")
    public OutputDTO queryNoticeStatusMessageInfo() {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> map = new HashMap<>();
        try {
            outputDTO = getOutputDTO(map, "noticeService", "queryNoticeStatusMessageInfo");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 加载紧急程度下拉列表
     * @return 出参
     */
    @RequestMapping("/queryInstancyLevelMessageInfo")
    public OutputDTO queryInstancyLevelMessageInfo() {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> map = new HashMap<>();
        try {
            outputDTO = getOutputDTO(map, "noticeService", "queryInstancyLevelMessageInfo");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 创建存草稿
     * @param notice 公告实体类
     * @param userId 当前用户id
     * @param userName 当前用户名称
     * @param req 请求
     * @return 出参
     */
    @RequestMapping("/insertNoticeMessageInfo")
    public OutputDTO insertNoticeMessageInfo(@ModelAttribute Notice notice,
                                             @SessionAttribute(Constants.SESSION_USER.ID) Long userId,
                                             @SessionAttribute(Constants.SESSION_USER.USERNAME) String userName,
                                             HttpServletRequest req) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> map = new HashMap<>();
        notice.setCrtId(userId);
        notice.setCrtName(userName);
        notice.setModfId(userId);
        notice.setModfName(userName);
        notice.setPublishId(userId);
        notice.setPublishName(userName);
        notice.setCrtTime(new Date());
        notice.setModfTime(new Date());
        notice.setStatus(1001L);
        notice.setPublishTime(new Date());
        String userIdsString = req.getParameter("peopleIds");
        List<Long> userIds = JSONObject.parseArray(userIdsString, Long.class);
        if (userIds != null && !userIds.isEmpty()) {
            //判断当前选中的人员是否包含当前用户
            if (!userIds.contains(userId)) { //若不包含则添加到该集合中
                userIds.add(userId);
            }
            notice.setSendTotal(userIds.size());
        } else {
            notice.setSendTotal(-9999);
        }
        Map<String, Object> map1 = BeanUtil.convertBean2Map(notice);
        map.put("notice", map1);
        map.put("userIds", userIds);
        try {
            outputDTO = getOutputDTO(map, "noticeService", "insertNoticeMessageInfo");
        } catch (Exception e) {
            LOGGER.error("创建失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 创建发布
     * @param notice 公告实体封装类
     * @param userId 当前用户id
     * @param userName 当前用户名称
     * @param req 请求
     * @return 出参
     */
    @RequestMapping("/publishNoticeMessageInfo")
    public OutputDTO publishNoticeMessageInfo(@ModelAttribute Notice notice,
                                             @SessionAttribute(Constants.SESSION_USER.ID) Long userId,
                                             @SessionAttribute(Constants.SESSION_USER.USERNAME) String userName,
                                             HttpServletRequest req) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> map = new HashMap<>();
        notice.setCrtId(userId);
        notice.setCrtName(userName);
        notice.setModfId(userId);
        notice.setModfName(userName);
        notice.setPublishId(userId);
        notice.setPublishName(userName);
        notice.setCrtTime(new Date());
        notice.setModfTime(new Date());
        notice.setStatus(1003L);
        notice.setPublishTime(new Date());
        String userIdsString = req.getParameter("peopleIds");
        List<Long> userIds = JSONObject.parseArray(userIdsString, Long.class);
        if (userIds != null && !userIds.isEmpty()) {
            //判断当前选中的人员是否包含当前用户
            if (!userIds.contains(userId)) {//若不包含则添加到该集合中
                userIds.add(userId);
            }
            notice.setSendTotal(userIds.size());
        } else {
            notice.setSendTotal(-9999);
        }
        Map<String, Object> map1 = BeanUtil.convertBean2Map(notice);
        map.put("notice", map1);
        map.put("userIds", userIds);
        try {
            outputDTO = getOutputDTO(map, "noticeService", "publishNoticeMessageInfo");
        } catch (Exception e) {
            LOGGER.error("创建失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }


    /**
     * 编辑存草稿
     * @param notice 公告实体封装类
     * @param userId 当前用户id
     * @param userName 当前用户名称
     * @return 出参
     */
    @RequestMapping("/updateNoticeMessageInfo")
    public OutputDTO updateNoticeMessageInfo(@ModelAttribute Notice notice,
                                             @SessionAttribute(Constants.SESSION_USER.ID) Long userId,
                                             @SessionAttribute(Constants.SESSION_USER.USERNAME) String userName,
                                             HttpServletRequest req) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> map = new HashMap<>();
        notice.setModfTime(new Date());
        notice.setStatus(1001L);
        notice.setPublishTime(new Date());
        String userIdsString = req.getParameter("peopleIds");
        List<Long> userIds = JSONObject.parseArray(userIdsString, Long.class);
        if (userIds != null && !userIds.isEmpty()) {
            //判断当前选中的人员是否包含当前用户
            if (!userIds.contains(userId)) { //若不包含则添加到该集合中
                userIds.add(userId);
            }
            notice.setSendTotal(userIds.size());
        } else {
            notice.setSendTotal(-9999);
        }
        Map<String, Object> map1 = BeanUtil.convertBean2Map(notice);
        map.put("notice", map1);
        map.put("userIds", userIds);
        try {
            outputDTO = getOutputDTO(map, "noticeService", "updateNoticeMessageInfo");
        } catch (Exception e) {
            LOGGER.error("存草稿失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;

    }


    /**
     * 编辑发布
     * @param notice 公告实体封装类
     * @param userId 当前用户id
     * @param userName 当前用户名称
     * @param req 请求
     * @return 出参
     */
    @RequestMapping("/editPublishNoticeMessageInfo")
    public OutputDTO editPublishNoticeMessageInfo(@ModelAttribute Notice notice,
                                             @SessionAttribute(Constants.SESSION_USER.ID) Long userId,
                                             @SessionAttribute(Constants.SESSION_USER.USERNAME) String userName,
                                             HttpServletRequest req) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> map = new HashMap<>();
        notice.setModfTime(new Date());
        notice.setStatus(1003L);
        notice.setPublishTime(new Date());
        String userIdsString = req.getParameter("peopleIds");
        List<Long> userIds = JSONObject.parseArray(userIdsString, Long.class);
        if (userIds != null && !userIds.isEmpty()) {
            //判断当前选中的人员是否包含当前用户
            if (!userIds.contains(userId)) { //若不包含则添加到该集合中
                userIds.add(userId);
            }
            notice.setSendTotal(userIds.size());
        } else {
            notice.setSendTotal(-9999);
        }
        Map<String, Object> map1 = BeanUtil.convertBean2Map(notice);
        map.put("notice", map1);
        map.put("userIds", userIds);
        try {
            outputDTO = getOutputDTO(map, "noticeService", "editPublishNoticeMessageInfo");
        } catch (Exception e) {
            LOGGER.error("发布失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;

    }

    /**
     * 删除公告信息
     * @param notice 公告实体封装
     * @return 出参
     */
    @RequestMapping("/deleteNoticeMessageInfo")
    public OutputDTO deleteNoticeMessageInfo(@ModelAttribute Notice notice) {
        LOGGER.info("NoticeController.deleteNoticeMessageInfo删除数据:" + notice.getId());
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> map = BeanUtil.convertBean2Map(notice);
        try {
            outputDTO = getOutputDTO(map, "noticeService", "deleteNoticeMessageInfo");
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 外部发布按钮
     * @param notice 公告实体封装类
     * @return 出参
     */
    @RequestMapping("/publishBtnNoticeMessageInfo")
    public OutputDTO publishBtnNoticeMessageInfo(@ModelAttribute Notice notice) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> map = BeanUtil.convertBean2Map(notice);
        try {
            outputDTO = getOutputDTO(map, "noticeService", "publishBtnNoticeMessageInfo");
        } catch (Exception e) {
            LOGGER.error("发布失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 点击公告主题查询公告详情
     * @param notice 公告实体封装类
     * @param userId 当前用户id
     * @return 出参
     */
    @RequestMapping("/queryNoticeContentById")
    public OutputDTO queryNoticeContentById(@ModelAttribute Notice notice,
                                            @SessionAttribute(Constants.SESSION_USER.ID) Long userId) {
        OutputDTO outputDTO = new OutputDTO();
        notice.setCurrentUserId(userId);
        Map<String, Object> map = BeanUtil.convertBean2Map(notice);
        try {
            outputDTO = getOutputDTO(map, "noticeService", "queryNoticeContentById");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 外部撤回按钮
     * @param notice 公告实体封装类
     * @return 出参
     */
    @RequestMapping("/recallBtnNoticeMessageInfo")
    public OutputDTO recallBtnNoticeMessageInfo(@ModelAttribute Notice notice) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> map = BeanUtil.convertBean2Map(notice);
        try {
            outputDTO = getOutputDTO(map, "noticeService", "recallBtnNoticeMessageInfo");
        } catch (Exception e) {
            LOGGER.error("发布失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 分页查询公告信息
     * @param noticeQueryForm 公告查询页面封装类
     * @param req 请求
     * @param userId 当前用户id
     * @param roleIds 当前用户角色的id集合
     * @return 出参
     */
    @RequestMapping("/queryNoticeMessageInfo")
    public OutputDTO queryNoticeMessageInfo(@ModelAttribute NoticeQueryForm noticeQueryForm,
                                            HttpServletRequest req,
                                            @SessionAttribute(Constants.SESSION_USER.ID) Long userId,
                                            @SessionAttribute(Constants.SESSION_USER.ROLE_IDS) String roleIds) {
        OutputDTO outputDTO = new OutputDTO();
        //判断该用户是否是超级管理员
        noticeQueryForm.setIsRoot("0");
        if (roleIds != null && !roleIds.isEmpty()) {
            if (roleIds.contains(Constants.ROOT_ROLE)) {
                noticeQueryForm.setIsRoot("1");
            }
        }
        noticeQueryForm.setUserId(userId);
        String endTime = noticeQueryForm.getEndTime();
        if (endTime != null && !"".equals(endTime)) {
            noticeQueryForm.setEndTime(endTime + " 23:59:59");
        }
        Map<String, Object> map1 = BeanUtil.convertBean2Map(noticeQueryForm);
        try {
            outputDTO = getOutputDTO(map1, "noticeService", "queryNoticeMessageInfo");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 分页查询我的公告
     * @param noticeQueryForm 公告查询封装实体类
     * @param req 请求
     * @param userId 当前用户id
     * @param roleIds 当前用户角色的id集合
     * @return 出参
     */
    @RequestMapping("/queryMyNoticeMessageInfo")
    public OutputDTO queryMyNoticeMessageInfo(@ModelAttribute NoticeQueryForm noticeQueryForm,
                                                HttpServletRequest req,
                                              @SessionAttribute(Constants.SESSION_USER.ID) Long userId,
                                              @SessionAttribute(Constants.SESSION_USER.ROLE_IDS) String roleIds) {
        OutputDTO outputDTO = new OutputDTO();
        //判断该用户是否是超级管理员
        noticeQueryForm.setIsRoot("0");
        if (roleIds != null && !roleIds.isEmpty()) {
            if (roleIds.contains(Constants.ROOT_ROLE)) {
                noticeQueryForm.setIsRoot("1");
            }
        }
        noticeQueryForm.setUserId(userId);
        String endTime = noticeQueryForm.getEndTime();
        if (endTime != null && !"".equals(endTime)) {
            noticeQueryForm.setEndTime(endTime + " 23:59:59");
        }
        Map<String, Object> map1 = BeanUtil.convertBean2Map(noticeQueryForm);
        try {
            outputDTO = getOutputDTO(map1, "noticeService", "queryMyNoticeMessageInfo");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 分页查询已阅人信息信息
     * @param noticeQueryForm 公告查询封装类
     * @param req 请求
     * @param userId 当前用户id
     * @return 出参
     */
    @RequestMapping("/queryReadMessageInfo")
    public OutputDTO queryReadMessageInfo(@ModelAttribute NoticeQueryForm noticeQueryForm,
                                            HttpServletRequest req,@SessionAttribute(Constants.SESSION_USER.ID) Long userId) {
        OutputDTO outputDTO = new OutputDTO();
        noticeQueryForm.setUserId(userId);
        Map<String, Object> map1 = BeanUtil.convertBean2Map(noticeQueryForm);
        try {
            outputDTO = getOutputDTO(map1, "noticeService", "queryReadMessageInfo");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 分页获取未阅读人信息
     * @param noticeQueryForm 公告查询封装类
     * @param req 请求
     * @param userId 当前用户id
     * @return 出参
     */
    @RequestMapping("/queryUnReadMeaasgeInfo")
    public OutputDTO queryUnReadMeaasgeInfo(@ModelAttribute NoticeQueryForm noticeQueryForm,
                                          HttpServletRequest req,@SessionAttribute(Constants.SESSION_USER.ID) Long userId) {
        OutputDTO outputDTO = new OutputDTO();
        noticeQueryForm.setUserId(userId);
        Map<String, Object> map1 = BeanUtil.convertBean2Map(noticeQueryForm);
        try {
            outputDTO = getOutputDTO(map1, "noticeService", "queryUnReadMeaasgeInfo");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 分页获取阅读人与未阅读人详细信息
     * @param noticeQueryForm 公告查询封装类
     * @param req 请求
     * @param userId 当前用户id
     * @return 出参
     */
    @RequestMapping("/queryAllMeaasgeInfo")
    public OutputDTO queryAllMeaasgeInfo(@ModelAttribute NoticeQueryForm noticeQueryForm,
                                            HttpServletRequest req,@SessionAttribute(Constants.SESSION_USER.ID) Long userId) {
        OutputDTO outputDTO = new OutputDTO();
        noticeQueryForm.setUserId(userId);
        Map<String, Object> map1 = BeanUtil.convertBean2Map(noticeQueryForm);
        try {
            outputDTO = getOutputDTO(map1, "noticeService", "queryAllMeaasgeInfo");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 查询单条公告信息详情
     * @param noticeQueryForm 公告查询封装类
     * @return 出参
     */
    @RequestMapping("/queryNoticeDetailMessageInfo")
    public OutputDTO queryNoticeDetailMessageInfo(@ModelAttribute Notice noticeQueryForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> map1 = BeanUtil.convertBean2Map(noticeQueryForm);
        try {
            outputDTO = getOutputDTO(map1, "noticeService", "queryNoticeDetailMessageInfo");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 获取到该公告对应的权限信息(用于权限树回显)
     * @param noticeId 公告id
     * @return 出参
     */
    @RequestMapping("/getNoticePermissionTree")
    public OutputDTO getNoticePermissionTree(Long noticeId) {
        OutputDTO outputDTO = new OutputDTO();
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("noticeId", noticeId);
        try {
            outputDTO = getOutputDTO(map1, "noticeService", "getNoticePermissionTree");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

}
