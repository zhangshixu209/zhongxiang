package com.chmei.nzbmanage.member.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbcommon.util.StringUtil;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.common.util.SmUtils;
import com.chmei.nzbmanage.member.bean.MemberForm;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 实名认证信息控制器
 *
 * @author zhangshixu
 * @since 2019年11月08日 14点02分
 */
@RestController
@RequestMapping("/api/realNameAuth")
public class RealNameAuthController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(RealNameAuthController.class);

    /**
     * 查询实名认证信息
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/queryRealNameInfo")
    public OutputDTO queryRealNameInfo(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO;
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(memberForm);
            outputDTO = getOutputDTO(map, "realNameAuthService", "queryRealNameInfo");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 新增实名认证信息
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/saveRealNameAuthInfo")
    public OutputDTO saveRealNameAuthInfo(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO;
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(memberForm);
            // 修改实名信息
            if (StringUtil.isNotEmpty(memberForm.getCardNum())) {
                map.put("mobile", memberForm.getMemberAccount());
                //锁定密码判断是否释放
                OutputDTO dto = getOutputDTO(map, "userService", "getUserMobileTORedis");
                boolean data = (boolean) dto.getData();
                if(data){
                    return new OutputDTO("-1", "请于24小时后再次提交！");
                }
                OutputDTO output = SmUtils.authcIdCardReal(memberForm.getCardNum(), memberForm.getRealName());
                if ("0".equals(output.getCode())) {
                    output = getOutputDTO(map, "realNameAuthService", "saveRealNameAuthInfo");
                    return output;
                } else {
                    map.put("mobile", memberForm.getMemberAccount());
                    OutputDTO dto_ = getOutputDTO(map, "userService", "addUserMobileTORedis"); // 将账号添加到redis中并设置过期时间是5分钟
                    Integer num = (Integer) dto_.getData();
                    System.out.println(num);
                    if (num == 3) {
                        return new OutputDTO("-1", "请于24小时后再次提交！");
                    } else if (num == 1) {
                        return new OutputDTO("-1", "输入信息有误，您还有两次机会！");
                    } else if(num == 2) {
                        return new OutputDTO("-1", "输入信息有误，您还有一次机会！");
                    }
                    return output;
                }
            } else {
                // 修改更多信息
                outputDTO = getOutputDTO(map, "realNameAuthService", "saveRealNameAuthInfo");
            }
        } catch (Exception e) {
            LOGGER.error("保存失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 修改实名认证信息
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/updateRealNameAuthInfo")
    public OutputDTO updateRealNameAuthInfo(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO;
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(memberForm);
            // 修改实名信息
            if (StringUtil.isNotEmpty(memberForm.getCardNum())) {
                map.put("mobile", memberForm.getMemberAccount());
                //锁定密码判断是否释放
                OutputDTO dto = getOutputDTO(map, "userService", "getUserMobileTORedis");
                boolean data = (boolean) dto.getData();
                if(data){
                    return new OutputDTO("-1", "请于24小时后再次提交！");
                }
                OutputDTO output = SmUtils.authcIdCardReal(memberForm.getCardNum(), memberForm.getRealName());
                if ("0".equals(output.getCode())) {
                    output = getOutputDTO(map, "realNameAuthService", "updateRealNameAuthInfo");
                    return output;
                } else {
                    map.put("mobile", memberForm.getMemberAccount());
                    OutputDTO dto_ = getOutputDTO(map, "userService", "addUserMobileTORedis"); // 将账号添加到redis中并设置过期时间是5分钟
                    Integer num = (Integer) dto_.getData();
                    System.out.println(num);
                    if (num == 3) {
                        return new OutputDTO("-1", "请于24小时后再次提交！");
                    } else if (num == 1) {
                        return new OutputDTO("-1", "输入信息有误，您还有两次机会！");
                    } else if(num == 2) {
                        return new OutputDTO("-1", "输入信息有误，您还有一次机会！");
                    }
                    return output;
                }
            } else {
                // 修改更多信息
                outputDTO = getOutputDTO(map, "realNameAuthService", "updateRealNameAuthInfo");
            }
        } catch (Exception e) {
            LOGGER.error("保存失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 查询实名认证信息是否第一次修改
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/queryRealNameIsExist")
    public OutputDTO queryRealNameIsExist(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO;
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(memberForm);
            outputDTO = getOutputDTO(map, "realNameAuthService", "queryRealNameInfo");
            Map<String, Object> item = outputDTO.getItem();
            if (null != item) {
                String cardNum = (String) item.get("cardNum");
                if (StringUtil.isEmpty(cardNum)) {
                    return  new OutputDTO("0", "新增实名信息"); // 第一次添加实名信息
                } else {
                    return  new OutputDTO("1", "编辑实名信息"); // 如果身份证号存在代表实名信息已添加（请修改）
                }
            } else {
                return  new OutputDTO("2", "新增实名信息"); // 第一次添加实名信息
            }
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("-1", "系统错误");
        }
        return outputDTO;
    }

}
