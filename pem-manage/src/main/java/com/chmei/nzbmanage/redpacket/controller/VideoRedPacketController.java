package com.chmei.nzbmanage.redpacket.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.redpacket.bean.VideoRedPacketForm;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 视频红包控制器
 * 
 * @author zhangshixu
 * @since 2019年10月21日 16点56分
 */
@RestController
@RequestMapping("/api/videoRedPacket")
public class VideoRedPacketController extends BaseController {

    /**
     * 视频红包发布新增
     * @param videoRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveVideoRedPacketInfo")
    public OutputDTO saveVideoRedPacketInfo(@ModelAttribute VideoRedPacketForm videoRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(videoRedPacketForm);
        outputDTO = getOutputDTO(params, "videoRedPacketService", "saveVideoRedPacketInfo");
        return outputDTO;
    }

    /**
     * 返回红包信息
     * @param videoRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/updateRedPacketInfoById")
    public OutputDTO updateRedPacketInfoById(@ModelAttribute VideoRedPacketForm videoRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(videoRedPacketForm);
        outputDTO = getOutputDTO(params, "videoRedPacketService", "updateRedPacketInfoById");
        return outputDTO;
    }

    /**
     * 视频红包抢红包
     * @param videoRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/robRedPacketInfo")
    public OutputDTO robRedPacketInfo(@ModelAttribute VideoRedPacketForm videoRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(videoRedPacketForm);
        outputDTO = getOutputDTO(params, "videoRedPacketService", "robRedPacketInfo");
        return outputDTO;
    }

    /**
     * 查看已经领取过得红包,参数是:抢红包用户ID,红包ID
     * @param videoRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/viewRedPacketDone")
    public OutputDTO viewRedPacketDone(@ModelAttribute VideoRedPacketForm videoRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(videoRedPacketForm);
        outputDTO = getOutputDTO(params, "videoRedPacketService", "viewRedPacketDone");
        return outputDTO;
    }

    /**
     * 查看红包详细的信息,比如未领取完毕,谁领取了多少钱,谁是手气最佳,参数为:红包id和当前用户ID
     * @param videoRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/viewRedPacketInfo")
    public OutputDTO viewRedPacketInfo(@ModelAttribute VideoRedPacketForm videoRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(videoRedPacketForm);
        outputDTO = getOutputDTO(params, "videoRedPacketService", "viewRedPacketInfo");
        return outputDTO;
    }

    /**
     * 根据红包ID查询红包详细信息
     * @param videoRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryRedPacketDetail")
    public OutputDTO queryRedPacketDetail(@ModelAttribute VideoRedPacketForm videoRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(videoRedPacketForm);
        outputDTO = getOutputDTO(params, "videoRedPacketService", "queryRedPacketDetail");
        return outputDTO;
    }

    /**
     * 根据红包ID和用户ID查看此用户是否抢当前这个红包
     * @param videoRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/checkUserIsRobRedPacket")
    public OutputDTO checkUserIsRobRedPacket(@ModelAttribute VideoRedPacketForm videoRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(videoRedPacketForm);
        outputDTO = getOutputDTO(params, "videoRedPacketService", "checkUserIsRobRedPacket");
        return outputDTO;
    }

    /**
     * 当前用户是否领取该红包 参数为 memberAccount（当前用户账号）,redPackageIds（红包Id） 返回值
     * @param videoRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/selectListStockByRedPacketId")
    public OutputDTO selectListStockByRedPacketId(@ModelAttribute VideoRedPacketForm videoRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(videoRedPacketForm);
        outputDTO = getOutputDTO(params, "videoRedPacketService", "selectListStockByRedPacketId");
        return outputDTO;
    }

    /**
     * 查询所有红包根据用户权限
     * @param videoRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryAllRedPacketByAuth")
    public OutputDTO queryAllRedPacketByAuth(@ModelAttribute VideoRedPacketForm videoRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(videoRedPacketForm);
        outputDTO = getOutputDTO(params, "videoRedPacketService", "queryAllRedPacketByAuth");
        return outputDTO;
    }

}
