package com.chmei.nzbmanage.zxfriend.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbcommon.util.StringUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.zxfriend.bean.AdShareOutBonusForm;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 广告分红控制器
 * 
 * @author zhangshixu
 * @since 2019年11月14日 10点00分
 */
@RestController
@RequestMapping("/api/shareOutBonus")
public class AdShareOutBonusController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(AdShareOutBonusController.class);

    /**
     * 激活广告分红
     *
     * @param adShareOutBonusForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/adShareOutBonusActivate")
    public OutputDTO adShareOutBonusActivate(@ModelAttribute AdShareOutBonusForm adShareOutBonusForm) {
    	LOGGER.info("激活广告分红...AdShareOutBonusController.upgradeChatGroupInfo()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(adShareOutBonusForm);
        OutputDTO outputDTO = getOutputDTO(params, "adShareOutBonusService", "adShareOutBonusActivate");
        return outputDTO;
    }

    /**
     * 追加广告分红
     *
     * @param adShareOutBonusForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/appendShareOutBonus")
    public OutputDTO appendShareOutBonus(@ModelAttribute AdShareOutBonusForm adShareOutBonusForm) {
        LOGGER.info("追加广告分红...AdShareOutBonusController.appendShareOutBonus()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(adShareOutBonusForm);
        OutputDTO outputDTO = getOutputDTO(params, "adShareOutBonusService", "appendShareOutBonus");
        return outputDTO;
    }

    /**
     * 查询是否第一次绑定推荐人
     *
     * @param adShareOutBonusForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryIsFirstRecommend")
    public OutputDTO queryIsFirstRecommend(@ModelAttribute AdShareOutBonusForm adShareOutBonusForm) {
        LOGGER.info("追加广告分红...AdShareOutBonusController.queryIsFirstRecommend()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(adShareOutBonusForm);
        OutputDTO outputDTO = getOutputDTO(params, "adShareOutBonusService", "queryIsFirstRecommend");
        return outputDTO;
    }

    /**
     * 根据账号查询推荐人信息
     *
     * @param adShareOutBonusForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryRecommendInfo")
    public OutputDTO queryRecommendInfo(@ModelAttribute AdShareOutBonusForm adShareOutBonusForm) {
        LOGGER.info("追加广告分红...AdShareOutBonusController.queryRecommendInfo()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(adShareOutBonusForm);
        outputDTO = getOutputDTO(params, "adShareOutBonusService", "queryRecommendInfo");
        return outputDTO;
    }

    /**
     * 申请分红之前的查询条件接口,比如 直推有效人数,广告费余额额度,计算分红周期
     *
     * @param adShareOutBonusForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/findApplyForAdvertisingInfo")
    public OutputDTO findApplyForAdvertisingInfo(@ModelAttribute AdShareOutBonusForm adShareOutBonusForm) {
        LOGGER.info("申请分红查询...AdShareOutBonusController.findApplyForAdvertisingInfo()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(adShareOutBonusForm);
        OutputDTO outputDTO = getOutputDTO(params, "adShareOutBonusService", "findApplyForAdvertisingInfo");
        return outputDTO;
    }

    /**
     * 申请分红
     *
     * @param adShareOutBonusForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/applyShareOutBonus")
    public OutputDTO applyShareOutBonus(@ModelAttribute AdShareOutBonusForm adShareOutBonusForm) {
        LOGGER.info("申请分红查询...AdShareOutBonusController.applyShareOutBonus()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(adShareOutBonusForm);
        OutputDTO outputDTO = getOutputDTO(params, "adShareOutBonusService", "applyShareOutBonus");
        return outputDTO;
    }

}
