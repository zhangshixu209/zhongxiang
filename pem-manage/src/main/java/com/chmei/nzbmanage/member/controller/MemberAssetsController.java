package com.chmei.nzbmanage.member.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.member.bean.MemberAssetsForm;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 会员资产明细控制器
 *
 * @author zhangshixu
 * @since 2019年11月08日 14点02分
 */
@RestController
@RequestMapping("/api/memberAssets")
public class MemberAssetsController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(MemberAssetsController.class);

    /**
     * 查询会员资产明细
     * @param memberAssetsForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/queryMemberAssetsInfo")
    public OutputDTO queryRealNameInfo(@ModelAttribute MemberAssetsForm memberAssetsForm) {
        OutputDTO outputDTO;
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(memberAssetsForm);
            outputDTO = getOutputDTO(map, "memberAssetsService", "queryMemberAssetsInfo");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

}
