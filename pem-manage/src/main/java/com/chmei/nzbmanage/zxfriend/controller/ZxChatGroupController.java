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
 * 众享好友群管理控制器
 * 
 * @author zhangshixu
 * @since 2019年11月13日 11点36分
 */
@RestController
@RequestMapping("/api/chatGroup")
public class ZxChatGroupController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(ZxChatGroupController.class);

    /**
     * 众享好友群升级
     *
     * @param zxFriendForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/upgradeChatGroupInfo")
    public OutputDTO upgradeChatGroupInfo(@ModelAttribute ZxFriendForm zxFriendForm) {
    	LOGGER.info("众享好友群升级...ZxChatGroupController.upgradeChatGroupInfo()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFriendForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxChatGroupService", "upgradeChatGroupInfo");
        return outputDTO;
    }

}
