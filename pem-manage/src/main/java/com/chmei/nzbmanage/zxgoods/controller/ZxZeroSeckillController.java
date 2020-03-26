package com.chmei.nzbmanage.zxgoods.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.zxgoods.bean.ZxGoodsExamineForm;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 零元秒杀控制器
 * 
 * @author zhangshixu
 * @since 2020年3月17日 18点46分
 */
@RestController
@RequestMapping("/api/seckill")
public class ZxZeroSeckillController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(ZxZeroSeckillController.class);
    
    /**
     * 初始化加载零元秒杀查询列表
     *
     * @param zxGoodsExamineForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryGoodsExamineList")
    public OutputDTO queryGoodsExamineList(@ModelAttribute ZxGoodsExamineForm zxGoodsExamineForm) {
    	LOGGER.info("初始化加载零元秒杀查询列表...RotationChartController.queryGoodsExamineList()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxGoodsExamineForm);
        outputDTO = getOutputDTO(params, "zxGoodsExamineService", "queryGoodsExamineList");
        return outputDTO;
    }
    
    /**
     * 新增零元秒杀
     * @param zxGoodsExamineForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveGoodsExamineInfo")
    public OutputDTO saveGoodsExamineInfo(@ModelAttribute ZxGoodsExamineForm zxGoodsExamineForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxGoodsExamineForm);
        outputDTO = getOutputDTO(params, "zxGoodsExamineService", "saveGoodsExamineInfo");
        return outputDTO;
    }

    /**
     * 零元秒杀详情
     * @param zxGoodsExamineForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryGoodsExamineDetail")
    public OutputDTO queryGoodsExamineDetail(@ModelAttribute ZxGoodsExamineForm zxGoodsExamineForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxGoodsExamineForm);
        outputDTO = getOutputDTO(params, "zxGoodsExamineService", "queryGoodsExamineDetail");
        return outputDTO;
    }

    /**
     * 编辑零元秒杀
     * @param zxGoodsExamineForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/updateGoodsExamineInfo")
    public OutputDTO updateGoodsExamineInfo(@ModelAttribute ZxGoodsExamineForm zxGoodsExamineForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxGoodsExamineForm);
        outputDTO = getOutputDTO(params, "zxGoodsExamineService", "updateGoodsExamineInfo");
        return outputDTO;
    }

    /**
     * 删除零元秒杀
     * @param zxGoodsExamineForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/delGoodsExamineInfo")
    public OutputDTO delGoodsExamineInfo(@ModelAttribute ZxGoodsExamineForm zxGoodsExamineForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxGoodsExamineForm);
        outputDTO = getOutputDTO(params, "zxGoodsExamineService", "delGoodsExamineInfo");
        return outputDTO;
    }

    /**
     * 零元秒杀审核
     * @param zxGoodsExamineForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/authGoodsExamineInfo")
    public OutputDTO authGoodsExamineInfo(@ModelAttribute ZxGoodsExamineForm zxGoodsExamineForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxGoodsExamineForm);
        outputDTO = getOutputDTO(params, "zxGoodsExamineService", "authGoodsExamineInfo");
        return outputDTO;
    }

    /**
     * 零元秒杀上架/下架
     * @param zxGoodsExamineForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/goodsShelfInfo")
    public OutputDTO goodsShelfInfo(@ModelAttribute ZxGoodsExamineForm zxGoodsExamineForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxGoodsExamineForm);
        outputDTO = getOutputDTO(params, "zxGoodsExamineService", "goodsShelfInfo");
        return outputDTO;
    }

    /**
     * 零元秒杀购买
     * @param zxGoodsExamineForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/buyGoodsExamineInfo")
    public OutputDTO buyGoodsExamineInfo(@ModelAttribute ZxGoodsExamineForm zxGoodsExamineForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxGoodsExamineForm);
        outputDTO = getOutputDTO(params, "zxGoodsExamineService", "buyGoodsExamineInfo");
        return outputDTO;
    }

    /**
     * 零元秒杀幸运榜
     * @param zxGoodsExamineForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryZeroSeckillLuckyList")
    public OutputDTO queryZeroSeckillLuckyList(@ModelAttribute ZxGoodsExamineForm zxGoodsExamineForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxGoodsExamineForm);
        outputDTO = getOutputDTO(params, "zxGoodsExamineService", "queryZeroSeckillLuckyList");
        return outputDTO;
    }

    /**
     * 零元秒杀幸运榜参与详情
     * @param zxGoodsExamineForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryZeroSeckillPartakeList")
    public OutputDTO queryZeroSeckillPartakeList(@ModelAttribute ZxGoodsExamineForm zxGoodsExamineForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxGoodsExamineForm);
        outputDTO = getOutputDTO(params, "zxGoodsExamineService", "queryZeroSeckillPartakeList");
        return outputDTO;
    }

    /**
     * 关注人数新增
     * @param zxGoodsExamineForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveGoodsViewCount")
    public OutputDTO saveGoodsViewCount(@ModelAttribute ZxGoodsExamineForm zxGoodsExamineForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxGoodsExamineForm);
        outputDTO = getOutputDTO(params, "zxGoodsExamineService", "saveGoodsViewCount");
        return outputDTO;
    }

    /**
     * 校验开通发布窗口数量
     * @param zxGoodsExamineForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/checkReleaseWindow")
    public OutputDTO checkReleaseWindow(@ModelAttribute ZxGoodsExamineForm zxGoodsExamineForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxGoodsExamineForm);
        outputDTO = getOutputDTO(params, "zxGoodsExamineService", "checkReleaseWindow");
        return outputDTO;
    }
}
