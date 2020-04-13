package com.chmei.nzbmanage.member.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.member.bean.TransferAccountsForm;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 众享好友转账控制器
 *
 * @author zhangshixu
 * @since 2019年11月12日 17点51分
 */
@RestController
@RequestMapping("/api/transferAccounts")
public class TransferAccountsController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(TransferAccountsController.class);

    /**
     * 众享好友转账
     *
     * @param transferAccountsForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/memberTransferAccounts")
    public OutputDTO memberTransferAccounts(@ModelAttribute TransferAccountsForm transferAccountsForm) {
        OutputDTO outputDTO;
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(transferAccountsForm);
            outputDTO = getOutputDTO(map, "transferAccountsService", "memberTransferAccounts");
        } catch (Exception e) {
            LOGGER.error("保存失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 校验是否显示广告费
     *
     * @param transferAccountsForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/checkIsAdvertisingFee")
    public OutputDTO checkIsAdvertisingFee(@ModelAttribute TransferAccountsForm transferAccountsForm) {
        OutputDTO outputDTO;
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(transferAccountsForm);
            outputDTO = getOutputDTO(map, "transferAccountsService", "checkIsAdvertisingFee");
        } catch (Exception e) {
            LOGGER.error("保存失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

}
