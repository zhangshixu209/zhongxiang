package com.chmei.nzbmanage.redpacket.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
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

}
