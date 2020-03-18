package com.chmei.nzbmanage.zxgoods.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbcommon.util.UuidUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.common.exception.NzbManageException;
import com.chmei.nzbmanage.configure.FileUploadPathConfig.UploadFilePathConfig;
import com.chmei.nzbmanage.zxgoods.bean.ZxGoodsTypeForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 商品类别控制器
 * 
 * @author zhangshixu
 * @since 2020年3月17日 18点46分
 */
@RestController
@RequestMapping("/api/goodsType")
public class ZxGoodsTypeController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(ZxGoodsTypeController.class);
    
    /**
     * 初始化加载商品类别查询列表
     *
     * @param zxGoodsTypeForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryGoodsTypeList")
    public OutputDTO queryGoodsTypeList(@ModelAttribute ZxGoodsTypeForm zxGoodsTypeForm) {
    	LOGGER.info("初始化加载商品类别查询列表...RotationChartController.queryGoodsTypeList()...");
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxGoodsTypeForm);
        outputDTO = getOutputDTO(params, "zxGoodsTypeService", "queryGoodsTypeList");
        return outputDTO;
    }
    
    /**
     * 新增商品类别
     * @param zxGoodsTypeForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveGoodsTypeInfo")
    public OutputDTO saveGoodsTypeInfo(@ModelAttribute ZxGoodsTypeForm zxGoodsTypeForm) {
        OutputDTO outputDTO = new OutputDTO();
        // 创建人， 姓名
        zxGoodsTypeForm.setCrtUserId(getCurrUserId());
        zxGoodsTypeForm.setCrtUserName(getCurrUserName());
        Map<String, Object> params = BeanUtil.convertBean2Map(zxGoodsTypeForm);
        outputDTO = getOutputDTO(params, "zxGoodsTypeService", "saveGoodsTypeInfo");
        return outputDTO;
    }

    /**
     * 商品类别详情
     * @param zxGoodsTypeForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryGoodsTypeDetail")
    public OutputDTO queryGoodsTypeDetail(@ModelAttribute ZxGoodsTypeForm zxGoodsTypeForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxGoodsTypeForm);
        outputDTO = getOutputDTO(params, "zxGoodsTypeService", "queryGoodsTypeDetail");
        return outputDTO;
    }

    /**
     * 编辑商品类别
     * @param zxGoodsTypeForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/updateGoodsTypeInfo")
    public OutputDTO updateGoodsTypeInfo(@ModelAttribute ZxGoodsTypeForm zxGoodsTypeForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxGoodsTypeForm);
        outputDTO = getOutputDTO(params, "zxGoodsTypeService", "updateGoodsTypeInfo");
        return outputDTO;
    }

    /**
     * 删除商品类别
     * @param zxGoodsTypeForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/delGoodsTypeInfo")
    public OutputDTO delGoodsTypeInfo(@ModelAttribute ZxGoodsTypeForm zxGoodsTypeForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(zxGoodsTypeForm);
        outputDTO = getOutputDTO(params, "zxGoodsTypeService", "delGoodsTypeInfo");
        return outputDTO;
    }
}
