package com.chmei.nzbmanage.redpacket.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.redpacket.bean.LinkRedPacketForm;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 链接红包控制器
 * 
 * @author zhangshixu
 * @since 2019年11月05日 15点47分
 */
@RestController
@RequestMapping("/api/linkRedPacket")
public class LinkRedPacketController extends BaseController {

    /**
     * 视频红包发布新增
     * @param linkRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveLinkRedPacketInfo")
    public OutputDTO saveLinkRedPacketInfo(@ModelAttribute LinkRedPacketForm linkRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(linkRedPacketForm);
        outputDTO = getOutputDTO(params, "linkRedPacketService", "saveLinkRedPacketInfo");
        return outputDTO;
    }

    /**
     * 返回红包信息
     * @param linkRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/updateRedPacketInfoById")
    public OutputDTO updateImgRedPacketInfoById(@ModelAttribute LinkRedPacketForm linkRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(linkRedPacketForm);
        outputDTO = getOutputDTO(params, "linkRedPacketService", "updateRedPacketInfoById");
        return outputDTO;
    }

    /**
     * 链接红包抢红包
     * @param linkRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/robRedPacketInfo")
    public OutputDTO robRedPacketInfo(@ModelAttribute LinkRedPacketForm linkRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(linkRedPacketForm);
        outputDTO = getOutputDTO(params, "linkRedPacketService", "robRedPacketInfo");
        return outputDTO;
    }

    /**
     * 查看已经领取过得红包,参数是:抢红包用户ID,红包ID
     * @param linkRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/viewRedPacketDone")
    public OutputDTO viewRedPacketDone(@ModelAttribute LinkRedPacketForm linkRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(linkRedPacketForm);
        outputDTO = getOutputDTO(params, "linkRedPacketService", "viewRedPacketDone");
        return outputDTO;
    }

    /**
     * 查看红包详细的信息,比如未领取完毕,谁领取了多少钱,谁是手气最佳,参数为:红包id和当前用户ID
     * @param linkRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/viewRedPacketInfo")
    public OutputDTO viewRedPacketInfo(@ModelAttribute LinkRedPacketForm linkRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(linkRedPacketForm);
        outputDTO = getOutputDTO(params, "linkRedPacketService", "viewRedPacketInfo");
        return outputDTO;
    }

    /**
     * 根据红包ID查询红包详细信息
     * @param linkRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryRedPacketDetail")
    public OutputDTO queryRedPacketDetail(@ModelAttribute LinkRedPacketForm linkRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(linkRedPacketForm);
        outputDTO = getOutputDTO(params, "linkRedPacketService", "queryRedPacketDetail");
        return outputDTO;
    }

    /**
     * 根据红包ID和用户ID查看此用户是否抢当前这个红包
     * @param linkRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/checkUserIsRobRedPacket")
    public OutputDTO checkUserIsRobRedPacket(@ModelAttribute LinkRedPacketForm linkRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(linkRedPacketForm);
        outputDTO = getOutputDTO(params, "linkRedPacketService", "checkUserIsRobRedPacket");
        return outputDTO;
    }

    /**
     * 当前用户是否领取该红包 参数为 memberAccount（当前用户账号）,redPackageIds（红包Id） 返回值
     * @param linkRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/selectListStockByRedPacketId")
    public OutputDTO selectListStockByRedPacketId(@ModelAttribute LinkRedPacketForm linkRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(linkRedPacketForm);
        outputDTO = getOutputDTO(params, "linkRedPacketService", "selectListStockByRedPacketId");
        return outputDTO;
    }

    /**
     * 查询所有红包根据用户权限
     * @param linkRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryAllRedPacketByAuth")
    public OutputDTO queryAllRedPacketByAuth(@ModelAttribute LinkRedPacketForm linkRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(linkRedPacketForm);
        outputDTO = getOutputDTO(params, "linkRedPacketService", "queryAllRedPacketByAuth");
        return outputDTO;
    }

}
