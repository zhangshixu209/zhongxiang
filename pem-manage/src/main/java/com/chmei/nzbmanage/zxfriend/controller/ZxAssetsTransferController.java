package com.chmei.nzbmanage.zxfriend.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.zxfriend.bean.ZxAssetsTransferForm;
import com.chmei.nzbmanage.zxfriend.bean.ZxFriendForm;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 资产转让控制器
 * 
 * @author zhangshixu
 * @since 2019年11月19日 10点54分
 */
@RestController
@RequestMapping("/api/transfer")
public class ZxAssetsTransferController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(ZxAssetsTransferController.class);

    /**
     * 资产转让列表查询
     *
     * @param zxAssetsTransferForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryAssetsTransferList")
    public OutputDTO queryAssetsTransferList(@ModelAttribute ZxAssetsTransferForm zxAssetsTransferForm) {
    	LOGGER.info("资产转让列表查询...ZxAssetsTransferController.queryAssetsTransferList()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(zxAssetsTransferForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxAssetsTransferService", "queryAssetsTransferList");
        return outputDTO;
    }

    /**
     * 发布资产转让信息
     *
     * @param zxAssetsTransferForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/releaseTransferInfo")
    public OutputDTO releaseTransferInfo(@ModelAttribute ZxAssetsTransferForm zxAssetsTransferForm) {
        LOGGER.info("资产转让列表查询...ZxAssetsTransferController.releaseTransferInfo()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(zxAssetsTransferForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxAssetsTransferService", "releaseTransferInfo");
        return outputDTO;
    }

    /**
     * 购买资产转让信息
     *
     * @param zxAssetsTransferForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/buyTransferFee")
    public OutputDTO buyTransferFee(@ModelAttribute ZxAssetsTransferForm zxAssetsTransferForm) {
        LOGGER.info("资产转让列表查询...ZxAssetsTransferController.buyTransferFee()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(zxAssetsTransferForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxAssetsTransferService", "buyTransferFee");
        return outputDTO;
    }

    /**
     * 取消资产转让信息
     *
     * @param zxAssetsTransferForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/cancelTransferInfo")
    public OutputDTO cancelTransferInfo(@ModelAttribute ZxAssetsTransferForm zxAssetsTransferForm) {
        LOGGER.info("资产转让列表查询...ZxAssetsTransferController.cancelTransferInfo()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(zxAssetsTransferForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxAssetsTransferService", "cancelTransferInfo");
        return outputDTO;
    }

    /**
     * 查询交易记录信息
     *
     * @param zxAssetsTransferForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryDealRecordList")
    public OutputDTO queryDealRecordList(@ModelAttribute ZxAssetsTransferForm zxAssetsTransferForm) {
        LOGGER.info("资产转让列表查询...ZxAssetsTransferController.queryDealRecordList()...");
        Map<String, Object> params = BeanUtil.convertBean2Map(zxAssetsTransferForm);
        OutputDTO outputDTO = getOutputDTO(params, "zxAssetsTransferService", "queryDealRecordList");
        return outputDTO;
    }

}
