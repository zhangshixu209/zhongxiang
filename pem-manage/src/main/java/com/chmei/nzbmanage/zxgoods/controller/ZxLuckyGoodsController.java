package com.chmei.nzbmanage.zxgoods.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.zxgoods.bean.ZxLuckyGoodsForm;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 幸运购物控制器
 * 
 * @author zhangshixu
 * @since 2020年3月17日 18点46分
 */
@RestController
@RequestMapping("/api/lucky")
public class ZxLuckyGoodsController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(ZxLuckyGoodsController.class);
    
    /**
     * 初始化加载幸运购物查询列表
     *
     * @param zxLuckyGoodsForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryLuckyGoodsList")
    public OutputDTO queryLuckyGoodsList(@ModelAttribute ZxLuckyGoodsForm zxLuckyGoodsForm) {
    	LOGGER.info("初始化加载幸运购物查询列表...RotationChartController.queryLuckyGoodsList()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxLuckyGoodsForm);
        outputDTO = getOutputDTO(params, "zxLuckyGoodsService", "queryLuckyGoodsList");
        return outputDTO;
    }
    
    /**
     * 新增幸运购物
     * @param zxLuckyGoodsForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveLuckyGoodsInfo")
    public OutputDTO saveLuckyGoodsInfo(@ModelAttribute ZxLuckyGoodsForm zxLuckyGoodsForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxLuckyGoodsForm);
        outputDTO = getOutputDTO(params, "zxLuckyGoodsService", "saveLuckyGoodsInfo");
        return outputDTO;
    }

    /**
     * 幸运购物详情
     * @param zxLuckyGoodsForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryLuckyGoodsDetail")
    public OutputDTO queryLuckyGoodsDetail(@ModelAttribute ZxLuckyGoodsForm zxLuckyGoodsForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxLuckyGoodsForm);
        outputDTO = getOutputDTO(params, "zxLuckyGoodsService", "queryLuckyGoodsDetail");
        return outputDTO;
    }

    /**
     * 编辑幸运购物
     * @param zxLuckyGoodsForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/updateLuckyGoodsInfo")
    public OutputDTO updateLuckyGoodsInfo(@ModelAttribute ZxLuckyGoodsForm zxLuckyGoodsForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxLuckyGoodsForm);
        outputDTO = getOutputDTO(params, "zxLuckyGoodsService", "updateLuckyGoodsInfo");
        return outputDTO;
    }

    /**
     * 删除幸运购物
     * @param zxLuckyGoodsForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/delLuckyGoodsInfo")
    public OutputDTO delLuckyGoodsInfo(@ModelAttribute ZxLuckyGoodsForm zxLuckyGoodsForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxLuckyGoodsForm);
        outputDTO = getOutputDTO(params, "zxLuckyGoodsService", "delLuckyGoodsInfo");
        return outputDTO;
    }

    /**
     * 幸运购物审核
     * @param zxLuckyGoodsForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/authLuckyGoodsInfo")
    public OutputDTO authLuckyGoodsInfo(@ModelAttribute ZxLuckyGoodsForm zxLuckyGoodsForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxLuckyGoodsForm);
        outputDTO = getOutputDTO(params, "zxLuckyGoodsService", "authLuckyGoodsInfo");
        return outputDTO;
    }

    /**
     * 幸运购物上架/下架
     * @param zxLuckyGoodsForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/goodsShelfInfo")
    public OutputDTO goodsShelfInfo(@ModelAttribute ZxLuckyGoodsForm zxLuckyGoodsForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxLuckyGoodsForm);
        outputDTO = getOutputDTO(params, "zxLuckyGoodsService", "goodsShelfInfo");
        return outputDTO;
    }

    /**
     * 幸运购物购买
     * @param zxLuckyGoodsForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/buyLuckyGoodsInfo")
    public OutputDTO buyLuckyGoodsInfo(@ModelAttribute ZxLuckyGoodsForm zxLuckyGoodsForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxLuckyGoodsForm);
        outputDTO = getOutputDTO(params, "zxLuckyGoodsService", "buyLuckyGoodsInfo");
        return outputDTO;
    }
}
