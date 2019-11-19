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

}
