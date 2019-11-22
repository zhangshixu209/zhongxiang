package com.chmei.nzbmanage.redpacket.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.redpacket.bean.RedPacketForm;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 众享红包控制器
 * 
 * @author zhangshixu
 * @since 2019年11月05日 15点55分
 */
@RestController
@RequestMapping("/api/redPacket")
public class RedPacketController extends BaseController {

    /**
     * 众享红包发布新增
     * @param redPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/saveRedPacketInfo")
    public OutputDTO saveRedPacketInfo(@ModelAttribute RedPacketForm redPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(redPacketForm);
        outputDTO = getOutputDTO(params, "redPacketService", "saveRedPacketInfo");
        return outputDTO;
    }

    /**
     * 返回红包信息
     * @param redPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/updateImgRedPacketInfoById")
    public OutputDTO updateImgRedPacketInfoById(@ModelAttribute RedPacketForm redPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(redPacketForm);
        outputDTO = getOutputDTO(params, "redPacketService", "updateImgRedPacketInfoById");
        return outputDTO;
    }

    /**
     * 众享红包抢红包
     * @param redPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/robRedPacketInfo")
    public OutputDTO robRedPacketInfo(@ModelAttribute RedPacketForm redPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(redPacketForm);
        outputDTO = getOutputDTO(params, "redPacketService", "robRedPacketInfo");
        return outputDTO;
    }

    /**
     * 查看已经领取过得红包,参数是:抢红包用户ID,红包ID
     * @param redPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/viewRedPacketDone")
    public OutputDTO viewRedPacketDone(@ModelAttribute RedPacketForm redPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(redPacketForm);
        outputDTO = getOutputDTO(params, "redPacketService", "viewRedPacketDone");
        return outputDTO;
    }

    /**
     * 查看红包详细的信息,比如未领取完毕,谁领取了多少钱,谁是手气最佳,参数为:红包id和当前用户ID
     * @param redPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/viewRedPacketInfo")
    public OutputDTO viewRedPacketInfo(@ModelAttribute RedPacketForm redPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(redPacketForm);
        outputDTO = getOutputDTO(params, "redPacketService", "viewRedPacketInfo");
        return outputDTO;
    }

    /**
     * 根据红包ID查询红包详细信息
     * @param redPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/queryRedPacketDetail")
    public OutputDTO queryRedPacketDetail(@ModelAttribute RedPacketForm redPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        Map<String, Object> params = BeanUtil.convertBean2Map(redPacketForm);
        outputDTO = getOutputDTO(params, "redPacketService", "queryRedPacketDetail");
        return outputDTO;
    }

}
