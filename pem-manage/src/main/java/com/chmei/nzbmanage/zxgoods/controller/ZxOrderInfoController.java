package com.chmei.nzbmanage.zxgoods.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.zxgoods.bean.ZxOrderInfoForm;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 订单信息控制器
 * 
 * @author zhangshixu
 * @since 2020年3月17日 18点46分
 */
@RestController
@RequestMapping("/api/order")
public class ZxOrderInfoController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(ZxOrderInfoController.class);
    
    /**
     * 初始化加载订单信息查询列表
     *
     * @param zxOrderInfoForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryOrderInfoList")
    public OutputDTO queryOrderInfoList(@ModelAttribute ZxOrderInfoForm zxOrderInfoForm) {
    	LOGGER.info("初始化加载订单信息查询列表...RotationChartController.queryOrderInfoList()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxOrderInfoForm);
        outputDTO = getOutputDTO(params, "zxOrderInfoService", "queryOrderInfoList");
        return outputDTO;
    }
    
    /**
     * 新增订单信息
     * @param zxOrderInfoForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveOrderInfoInfo")
    public OutputDTO saveOrderInfoInfo(@ModelAttribute ZxOrderInfoForm zxOrderInfoForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxOrderInfoForm);
        outputDTO = getOutputDTO(params, "zxOrderInfoService", "saveOrderInfoInfo");
        return outputDTO;
    }

    /**
     * 订单信息详情
     * @param zxOrderInfoForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryOrderInfoDetail")
    public OutputDTO queryOrderInfoDetail(@ModelAttribute ZxOrderInfoForm zxOrderInfoForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxOrderInfoForm);
        outputDTO = getOutputDTO(params, "zxOrderInfoService", "queryOrderInfoDetail");
        return outputDTO;
    }

    /**
     * 编辑订单信息
     * @param zxOrderInfoForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/updateOrderInfoInfo")
    public OutputDTO updateOrderInfoInfo(@ModelAttribute ZxOrderInfoForm zxOrderInfoForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxOrderInfoForm);
        outputDTO = getOutputDTO(params, "zxOrderInfoService", "updateOrderInfoInfo");
        return outputDTO;
    }

    /**
     * 删除订单信息
     * @param zxOrderInfoForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/delOrderInfoInfo")
    public OutputDTO delOrderInfoInfo(@ModelAttribute ZxOrderInfoForm zxOrderInfoForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxOrderInfoForm);
        outputDTO = getOutputDTO(params, "zxOrderInfoService", "delOrderInfoInfo");
        return outputDTO;
    }

    /**
     * 查询我发布的商品列表
     * @param zxOrderInfoForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryMyReleaseGoodsList")
    public OutputDTO queryMyReleaseGoodsList(@ModelAttribute ZxOrderInfoForm zxOrderInfoForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxOrderInfoForm);
        outputDTO = getOutputDTO(params, "zxOrderInfoService", "queryMyReleaseGoodsList");
        return outputDTO;
    }

    /**
     * 查询我发布的订单详情
     * @param zxOrderInfoForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryMyReleaseOrderDetail")
    public OutputDTO queryMyReleaseOrderDetail(@ModelAttribute ZxOrderInfoForm zxOrderInfoForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxOrderInfoForm);
        outputDTO = getOutputDTO(params, "zxOrderInfoService", "queryMyReleaseOrderDetail");
        return outputDTO;
    }

    /**
     * 用户确认收货
     * @param zxOrderInfoForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/userConfirmReceipt")
    public OutputDTO userConfirmReceipt(@ModelAttribute ZxOrderInfoForm zxOrderInfoForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxOrderInfoForm);
        outputDTO = getOutputDTO(params, "zxOrderInfoService", "userConfirmReceipt");
        return outputDTO;
    }

    /**
     * 查询在途商品
     * @param zxOrderInfoForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryGoodsInTransitList")
    public OutputDTO queryGoodsInTransitList(@ModelAttribute ZxOrderInfoForm zxOrderInfoForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxOrderInfoForm);
        outputDTO = getOutputDTO(params, "zxOrderInfoService", "queryGoodsInTransitList");
        return outputDTO;
    }

}
