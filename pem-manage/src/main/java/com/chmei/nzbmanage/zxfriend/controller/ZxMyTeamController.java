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
 * 我的团队控制器
 * 
 * @author zhangshixu
 * @since 2019年11月15日 16点13分
 */
@RestController
@RequestMapping("/api/myTeam")
public class ZxMyTeamController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(ZxMyTeamController.class);

    /**
     * 我的团队列表查询
     *
     * @param zxFriendForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryTheLower")
    public OutputDTO queryTheLower(@ModelAttribute ZxFriendForm zxFriendForm) {
    	LOGGER.info("我的团队列表查询...ZxMyTeamController.queryTheLower()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFriendForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxMyTeamService", "queryTheLower");
        return outputDTO;
    }

}
