package com.chmei.nzbmanage.zxgoods.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.zxgoods.bean.ZxReceivingAddressForm;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 收货地址控制器
 * 
 * @author zhangshixu
 * @since 2020年3月17日 18点46分
 */
@RestController
@RequestMapping("/api/address")
public class ZxReceivingAddressController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(ZxReceivingAddressController.class);
    
    /**
     * 初始化加载收货地址查询列表
     *
     * @param zxReceivingAddressForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryReceivingAddressList")
    public OutputDTO queryReceivingAddressList(@ModelAttribute ZxReceivingAddressForm zxReceivingAddressForm) {
    	LOGGER.info("初始化加载收货地址查询列表...RotationChartController.queryReceivingAddressList()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxReceivingAddressForm);
        outputDTO = getOutputDTO(params, "zxReceivingAddressService", "queryReceivingAddressList");
        return outputDTO;
    }
    
    /**
     * 新增收货地址
     * @param zxReceivingAddressForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveReceivingAddressInfo")
    public OutputDTO saveReceivingAddressInfo(@ModelAttribute ZxReceivingAddressForm zxReceivingAddressForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxReceivingAddressForm);
        outputDTO = getOutputDTO(params, "zxReceivingAddressService", "saveReceivingAddressInfo");
        return outputDTO;
    }

    /**
     * 收货地址详情
     * @param zxReceivingAddressForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryReceivingAddressDetail")
    public OutputDTO queryReceivingAddressDetail(@ModelAttribute ZxReceivingAddressForm zxReceivingAddressForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxReceivingAddressForm);
        outputDTO = getOutputDTO(params, "zxReceivingAddressService", "queryReceivingAddressDetail");
        return outputDTO;
    }

    /**
     * 编辑收货地址
     * @param zxReceivingAddressForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/updateReceivingAddressInfo")
    public OutputDTO updateReceivingAddressInfo(@ModelAttribute ZxReceivingAddressForm zxReceivingAddressForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxReceivingAddressForm);
        outputDTO = getOutputDTO(params, "zxReceivingAddressService", "updateReceivingAddressInfo");
        return outputDTO;
    }

    /**
     * 删除收货地址
     * @param zxReceivingAddressForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/delReceivingAddressInfo")
    public OutputDTO delReceivingAddressInfo(@ModelAttribute ZxReceivingAddressForm zxReceivingAddressForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxReceivingAddressForm);
        outputDTO = getOutputDTO(params, "zxReceivingAddressService", "delReceivingAddressInfo");
        return outputDTO;
    }

    /**
     * 校验默认收货地址
     * @param zxReceivingAddressForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/checkAddressIsDefault")
    public OutputDTO checkAddressIsDefault(@ModelAttribute ZxReceivingAddressForm zxReceivingAddressForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxReceivingAddressForm);
        outputDTO = getOutputDTO(params, "zxReceivingAddressService", "checkAddressIsDefault");
        return outputDTO;
    }

    /**
     * 校验默认收货地址
     * @param zxReceivingAddressForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/setDefaultAddress")
    public OutputDTO setDefaultAddress(@ModelAttribute ZxReceivingAddressForm zxReceivingAddressForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxReceivingAddressForm);
        outputDTO = getOutputDTO(params, "zxReceivingAddressService", "setDefaultAddress");
        return outputDTO;
    }
}
