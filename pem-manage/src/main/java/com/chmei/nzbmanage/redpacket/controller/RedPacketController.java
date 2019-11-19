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
     * 视频红包发布新增
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

}
