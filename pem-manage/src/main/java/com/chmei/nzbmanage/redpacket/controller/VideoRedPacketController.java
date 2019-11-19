package com.chmei.nzbmanage.redpacket.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbcommon.util.UuidUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.common.exception.NzbManageException;
import com.chmei.nzbmanage.configure.FileUploadPathConfig.UploadFilePathConfig;
import com.chmei.nzbmanage.redpacket.bean.VideoRedPacketForm;
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
import java.util.Optional;

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
     * 视频红包发布新增
     * @param videoRedPacketForm 参数
     * @return outputDTO 返回结果
     */
    @RequestMapping("/robRedPacket")
    public OutputDTO robRedPacket(@ModelAttribute VideoRedPacketForm videoRedPacketForm) {
        OutputDTO outputDTO = new OutputDTO();
        String robUserId = videoRedPacketForm.getRobUserId();
        // TODO 发布红包未完成===================
        if (null != robUserId) {
            // 判断答题,年龄,和性别的限制
            outputDTO = getOutputDTO(robUserId, "memberService", "queryMemberDetail");
            Map<String, Object> zxAppUser = new HashMap<>();
            zxAppUser = outputDTO.getItem();
            // 通过用户ID和红包ID查询出红包信息:
            Long redPacketId = videoRedPacketForm.getRedPacketVideoId();
            String redPacketUserId = videoRedPacketForm.getRedPacketVideoUserId();
        }

        Map<String, Object> params = BeanUtil.convertBean2Map(videoRedPacketForm);

        outputDTO = getOutputDTO(params, "videoRedPacketService", "saveVideoRedPacketInfo");
        return outputDTO;
    }


}
