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
 * 消息推送控制器
 * 
 * @author zhangshixu
 * @since 2019年11月15日 16点13分
 */
@RestController
@RequestMapping("/api/pushMessage")
public class ZxPushMessageController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(ZxPushMessageController.class);

    /**
     * 消息推送列表查询
     *
     * @param zxFriendForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryPushMessageList")
    public OutputDTO queryPushMessageList(@ModelAttribute ZxFriendForm zxFriendForm) {
    	LOGGER.info("消息推送列表查询...ZxPushMessageController.queryPushMessageList()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFriendForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxPushMessageService", "queryPushMessageList");
        return outputDTO;
    }

    /**
     * 修改消息推送
     *
     * @param zxFriendForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/updatePushMessageStatus")
    public OutputDTO updatePushMessageStatus(@ModelAttribute ZxFriendForm zxFriendForm) {
        LOGGER.info("消息推送列表查询...ZxPushMessageController.updatePushMessageStatus()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFriendForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxPushMessageService", "updatePushMessageStatus");
        return outputDTO;
    }

}
