package com.chmei.nzbmanage.zxfriend.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.zxfriend.bean.ZxFriendForm;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 众享好友控制器
 * 
 * @author zhangshixu
 * @since 2019年11月28日 09点12分
 */
@RestController
@RequestMapping("/api/friend")
public class ZxFriendController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(ZxFriendController.class);

    /**
     * 众享好友添加
     *
     * @param zxFriendForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/addZxFriendInfo")
    public OutputDTO addZxFriendInfo(@ModelAttribute ZxFriendForm zxFriendForm) {
    	LOGGER.info("众享好友添加...ZxFriendController.addZxFriendInfo()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFriendForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxFriendService", "addZxFriendInfo");
        return outputDTO;
    }

    /**
     * 众享好友删除
     *
     * @param zxFriendForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/deleteZxFriendInfo")
    public OutputDTO deleteZxFriendInfo(@ModelAttribute ZxFriendForm zxFriendForm) {
        LOGGER.info("众享好友删除...ZxFriendController.deleteZxFriendInfo()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFriendForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxFriendService", "deleteZxFriendInfo");
        return outputDTO;
    }

    /**
     * 众享好友关注
     *
     * @param zxFriendForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/notesZxFriendInfo")
    public OutputDTO notesZxFriendInfo(@ModelAttribute ZxFriendForm zxFriendForm) {
        LOGGER.info("众享好友关注...ZxFriendController.notesZxFriendInfo()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFriendForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxFriendService", "notesZxFriendInfo");
        return outputDTO;
    }

    /**
     * 众享好友取消关注
     *
     * @param zxFriendForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/cancelNotesZxFriendInfo")
    public OutputDTO cancelNotesZxFriendInfo(@ModelAttribute ZxFriendForm zxFriendForm) {
        LOGGER.info("众享好友取消关注...ZxFriendController.deleteZxFriendInfo()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFriendForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxFriendService", "cancelNotesZxFriendInfo");
        return outputDTO;
    }

    /**
     * 按分组查询众享好友列表
     *
     * @param zxFriendForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryZxFriendList")
    public OutputDTO queryZxFriendList(@ModelAttribute ZxFriendForm zxFriendForm) {
        LOGGER.info("按分组查询众享好友列表...ZxFriendController.queryZxFriendList()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFriendForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxFriendService", "queryZxFriendList");
        return outputDTO;
    }

    /**
     * 众享好友分组新增
     *
     * @param zxFriendForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveZxFriendGroupingInfo")
    public OutputDTO saveZxFriendGroupingInfo(@ModelAttribute ZxFriendForm zxFriendForm) {
        LOGGER.info("众享好友分组新增...ZxFriendController.saveZxFriendGroupingInfo()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFriendForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxFriendService", "saveZxFriendGroupingInfo");
        return outputDTO;
    }

    /**
     * 众享好友分组编辑
     *
     * @param zxFriendForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/updateZxFriendGroupingInfo")
    public OutputDTO updateZxFriendGroupingInfo(@ModelAttribute ZxFriendForm zxFriendForm) {
        LOGGER.info("众享好友分组编辑...ZxFriendController.updateZxFriendGroupingInfo()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFriendForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxFriendService", "updateZxFriendGroupingInfo");
        return outputDTO;
    }

    /**
     * 众享好友分组删除
     *
     * @param zxFriendForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/delZxFriendGroupingInfo")
    public OutputDTO delZxFriendGroupingInfo(@ModelAttribute ZxFriendForm zxFriendForm) {
        LOGGER.info("众享好友分组删除...ZxFriendController.delZxFriendGroupingInfo()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFriendForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxFriendService", "delZxFriendGroupingInfo");
        return outputDTO;
    }

    /**
     * 移动我的好友到指定分组
     *
     * @param zxFriendForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/moveZxFriendGroupingInfo")
    public OutputDTO moveZxFriendGroupingInfo(@ModelAttribute ZxFriendForm zxFriendForm) {
        LOGGER.info("移动我的好友到指定分组...ZxFriendController.moveZxFriendGroupingInfo()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFriendForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxFriendService", "moveZxFriendGroupingInfo");
        return outputDTO;
    }

    /**
     * 查询众享好友分组列表
     *
     * @param zxFriendForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryZxFriendGroupingList")
    public OutputDTO queryZxFriendGroupingList(@ModelAttribute ZxFriendForm zxFriendForm) {
        LOGGER.info("查询众享好友分组列表...ZxFriendController.queryZxFriendGroupingList()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFriendForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxFriendService", "queryZxFriendGroupingList");
        return outputDTO;
    }

    /**
     * 查询众享好友朋友圈列表
     *
     * @param zxFriendForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryZxFriendCircleList")
    public OutputDTO queryZxFriendCircleList(@ModelAttribute ZxFriendForm zxFriendForm) {
        LOGGER.info("查询众享好友朋友圈列表...ZxFriendController.queryZxFriendCircleList()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFriendForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxFriendService", "queryZxFriendCircleList");
        return outputDTO;
    }

    /**
     * 修改好友备注
     *
     * @param zxFriendForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/updateZxFriendRemarkInfo")
    public OutputDTO updateZxFriendRemarkInfo(@ModelAttribute ZxFriendForm zxFriendForm) {
        LOGGER.info("修改好友备注...ZxFriendController.updateZxFriendRemarkInfo()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFriendForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxFriendService", "updateZxFriendRemarkInfo");
        return outputDTO;
    }

    /**
     * 查询众享好友手机通讯录列表
     *
     * @param zxFriendForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryZxFriendPhoneList")
    public OutputDTO queryZxFriendPhoneList(@ModelAttribute ZxFriendForm zxFriendForm) {
        LOGGER.info("查询众享好友手机通讯录列表...ZxFriendController.queryZxFriendPhoneList()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFriendForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxFriendService", "queryZxFriendPhoneList");
        return outputDTO;
    }

}
