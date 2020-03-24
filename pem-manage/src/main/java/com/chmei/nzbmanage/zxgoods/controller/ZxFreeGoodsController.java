package com.chmei.nzbmanage.zxgoods.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.zxgoods.bean.ZxFreeGoodsForm;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 免费兑换控制器
 * 
 * @author zhangshixu
 * @since 2020年3月17日 18点46分
 */
@RestController
@RequestMapping("/api/free")
public class ZxFreeGoodsController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(ZxFreeGoodsController.class);
    
    /**
     * 初始化加载免费兑换查询列表
     *
     * @param zxFreeGoodsForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryFreeGoodsList")
    public OutputDTO queryFreeGoodsList(@ModelAttribute ZxFreeGoodsForm zxFreeGoodsForm) {
    	LOGGER.info("初始化加载免费兑换查询列表...RotationChartController.queryFreeGoodsList()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFreeGoodsForm);
        outputDTO = getOutputDTO(params, "zxFreeGoodsService", "queryFreeGoodsList");
        return outputDTO;
    }
    
    /**
     * 新增免费兑换
     * @param zxFreeGoodsForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveFreeGoodsInfo")
    public OutputDTO saveFreeGoodsInfo(@ModelAttribute ZxFreeGoodsForm zxFreeGoodsForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFreeGoodsForm);
        outputDTO = getOutputDTO(params, "zxFreeGoodsService", "saveFreeGoodsInfo");
        return outputDTO;
    }

    /**
     * 免费兑换详情
     * @param zxFreeGoodsForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryFreeGoodsDetail")
    public OutputDTO queryFreeGoodsDetail(@ModelAttribute ZxFreeGoodsForm zxFreeGoodsForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFreeGoodsForm);
        outputDTO = getOutputDTO(params, "zxFreeGoodsService", "queryFreeGoodsDetail");
        return outputDTO;
    }

    /**
     * 编辑免费兑换
     * @param zxFreeGoodsForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/updateFreeGoodsInfo")
    public OutputDTO updateFreeGoodsInfo(@ModelAttribute ZxFreeGoodsForm zxFreeGoodsForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFreeGoodsForm);
        outputDTO = getOutputDTO(params, "zxFreeGoodsService", "updateFreeGoodsInfo");
        return outputDTO;
    }

    /**
     * 删除免费兑换
     * @param zxFreeGoodsForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/delFreeGoodsInfo")
    public OutputDTO delFreeGoodsInfo(@ModelAttribute ZxFreeGoodsForm zxFreeGoodsForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFreeGoodsForm);
        outputDTO = getOutputDTO(params, "zxFreeGoodsService", "delFreeGoodsInfo");
        return outputDTO;
    }

    /**
     * 免费兑换审核
     * @param zxFreeGoodsForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/authFreeGoodsInfo")
    public OutputDTO authFreeGoodsInfo(@ModelAttribute ZxFreeGoodsForm zxFreeGoodsForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFreeGoodsForm);
        outputDTO = getOutputDTO(params, "zxFreeGoodsService", "authFreeGoodsInfo");
        return outputDTO;
    }

    /**
     * 免费兑换上架/下架
     * @param zxFreeGoodsForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/goodsShelfInfo")
    public OutputDTO goodsShelfInfo(@ModelAttribute ZxFreeGoodsForm zxFreeGoodsForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFreeGoodsForm);
        outputDTO = getOutputDTO(params, "zxFreeGoodsService", "goodsShelfInfo");
        return outputDTO;
    }

    /**
     * 免费兑换购买
     * @param zxFreeGoodsForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/buyFreeGoodsInfo")
    public OutputDTO buyFreeGoodsInfo(@ModelAttribute ZxFreeGoodsForm zxFreeGoodsForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxFreeGoodsForm);
        outputDTO = getOutputDTO(params, "zxFreeGoodsService", "buyFreeGoodsInfo");
        return outputDTO;
    }
}
