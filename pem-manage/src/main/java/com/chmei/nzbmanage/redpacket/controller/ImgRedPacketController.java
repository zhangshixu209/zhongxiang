package com.chmei.nzbmanage.redpacket.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.redpacket.bean.ImgRedPacketForm;
import com.chmei.nzbmanage.redpacket.bean.ImgRedPacketForm;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 图文红包控制器
 * 
 * @author zhangshixu
 * @since 2019年10月21日 16点56分
 */
@RestController
@RequestMapping("/api/imgRedPacket")
public class ImgRedPacketController extends BaseController {

    /**
     * 视频红包发布新增
     * @param imgRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveImgRedPacketInfo")
    public OutputDTO saveImgRedPacketInfo(@ModelAttribute ImgRedPacketForm imgRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(imgRedPacketForm);
        outputDTO = getOutputDTO(params, "imgRedPacketService", "saveImgRedPacketInfo");
        return outputDTO;
    }

    /**
     * 返回红包信息
     * @param imgRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/updateRedPacketInfoById")
    public OutputDTO updateImgRedPacketInfoById(@ModelAttribute ImgRedPacketForm imgRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(imgRedPacketForm);
        outputDTO = getOutputDTO(params, "imgRedPacketService", "updateRedPacketInfoById");
        return outputDTO;
    }

    /**
     * 链接红包抢红包
     * @param imgRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/robRedPacketInfo")
    public OutputDTO robRedPacketInfo(@ModelAttribute ImgRedPacketForm imgRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(imgRedPacketForm);
        outputDTO = getOutputDTO(params, "imgRedPacketService", "robRedPacketInfo");
        return outputDTO;
    }

    /**
     * 查看已经领取过得红包,参数是:抢红包用户ID,红包ID
     * @param imgRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/viewRedPacketDone")
    public OutputDTO viewRedPacketDone(@ModelAttribute ImgRedPacketForm imgRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(imgRedPacketForm);
        outputDTO = getOutputDTO(params, "imgRedPacketService", "viewRedPacketDone");
        return outputDTO;
    }

    /**
     * 查看红包详细的信息,比如未领取完毕,谁领取了多少钱,谁是手气最佳,参数为:红包id和当前用户ID
     * @param imgRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/viewRedPacketInfo")
    public OutputDTO viewRedPacketInfo(@ModelAttribute ImgRedPacketForm imgRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(imgRedPacketForm);
        outputDTO = getOutputDTO(params, "imgRedPacketService", "viewRedPacketInfo");
        return outputDTO;
    }

    /**
     * 根据红包ID查询红包详细信息
     * @param imgRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryRedPacketDetail")
    public OutputDTO queryRedPacketDetail(@ModelAttribute ImgRedPacketForm imgRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(imgRedPacketForm);
        outputDTO = getOutputDTO(params, "imgRedPacketService", "queryRedPacketDetail");
        return outputDTO;
    }

    /**
     * 根据红包ID和用户ID查看此用户是否抢当前这个红包
     * @param imgRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/checkUserIsRobRedPacket")
    public OutputDTO checkUserIsRobRedPacket(@ModelAttribute ImgRedPacketForm imgRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(imgRedPacketForm);
        outputDTO = getOutputDTO(params, "imgRedPacketService", "checkUserIsRobRedPacket");
        return outputDTO;
    }

    /**
     * 当前用户是否领取该红包 参数为 memberAccount（当前用户账号）,redPackageIds（红包Id） 返回值
     * @param imgRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/selectListStockByRedPacketId")
    public OutputDTO selectListStockByRedPacketId(@ModelAttribute ImgRedPacketForm imgRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(imgRedPacketForm);
        outputDTO = getOutputDTO(params, "imgRedPacketService", "selectListStockByRedPacketId");
        return outputDTO;
    }

    /**
     * 查询所有红包根据用户权限
     * @param imgRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryAllRedPacketByAuth")
    public OutputDTO queryAllRedPacketByAuth(@ModelAttribute ImgRedPacketForm imgRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(imgRedPacketForm);
        outputDTO = getOutputDTO(params, "imgRedPacketService", "queryAllRedPacketByAuth");
        return outputDTO;
    }

}
