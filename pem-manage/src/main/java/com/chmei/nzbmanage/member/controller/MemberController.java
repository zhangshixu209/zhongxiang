package com.chmei.nzbmanage.member.controller;

import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.BeanUtil;
import com.chmei.nzbcommon.util.MD5Util;
import com.chmei.nzbcommon.util.StringUtil;
import com.chmei.nzbmanage.common.constant.Constants;
import com.chmei.nzbmanage.common.controller.BaseController;
import com.chmei.nzbmanage.common.util.Global;
import com.chmei.nzbmanage.common.util.Md5Utils;
import com.chmei.nzbmanage.common.util.SmUtils;
import com.chmei.nzbmanage.member.bean.MemberForm;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 会员管理控制器
 *
 * @author zhangshixu
 * @since 2019年10月24日 14点22分
 */
@RestController
@RequestMapping("/api/member")
public class MemberController extends BaseController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(MemberController.class);

    /**
     * 新增会员信息
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/saveMemberInfo")
    public OutputDTO saveMemberInfo(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(memberForm);
            outputDTO = getOutputDTO(map, "memberService", "saveMemberInfo");
        } catch (Exception e) {
            LOGGER.error("新增失败!", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 查询会员列表
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/queryMemberList")
    public OutputDTO queryMemberList(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(memberForm);
            outputDTO = getOutputDTO(map, "memberService", "queryMemberList");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 查询会员被投诉记录
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/queryMemberComplaintRecord")
    public OutputDTO queryMemberComplaintRecord(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(memberForm);
            outputDTO = getOutputDTO(map, "memberService", "queryMemberComplaintRecord");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 查询会员详细信息
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/queryMemberDetail")
    public OutputDTO queryMemberDetail(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(memberForm);
            outputDTO = getOutputDTO(map, "memberService", "queryMemberDetail");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 会员充值（钱包余额、广告费）
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/saveMemberRechargeInfo")
    public OutputDTO saveMemberRechargeInfo(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            // 修改人， 姓名
            memberForm.setModfUserId(getCurrUserId());
            memberForm.setModfUserName(getCurrUserName());
            Map<String, Object> map = BeanUtil.convertBean2Map(memberForm);
            outputDTO = getOutputDTO(map, "memberService", "saveMemberRechargeInfo");
        } catch (Exception e) {
            LOGGER.error("保存失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 存储session的map集合
     */
    private static final Map<String, Object> SESSION_MAP = new HashMap<String, Object>();

    /**
     * 发送六位验证码接口 0:发送短信成功;1:发送验证码失败;2:手机号非法;500:服务器错误
     *
     * @param phoneNumber 手机号
     * @return 发送验证码是否成功
     */
    @GetMapping("/sendCode/{phoneNumber}")
    public OutputDTO sendCode(@PathVariable("phoneNumber") String phoneNumber, HttpServletRequest request) {
        OutputDTO outputDTO = new OutputDTO();
        // 验证手机号码是否符合标准
        String match = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
        boolean matches = phoneNumber.matches(match);
        if (!matches) {
            outputDTO.setCode("2");
            outputDTO.setMsg("非法手机号码!");
            return outputDTO;
        }
//        String codeIp = IpUtil.getIpAddr(request);
        HttpSession session = request.getSession();
        LOGGER.info("存储session对象 {}  " + session);
        SESSION_MAP.put(Md5Utils.hash(session.getId()), session);
        return SmUtils.mobileQuery(phoneNumber, session);
    }

    /**
     * 使用短信验证码和密码注册
     * @param memberForm
     * @return
     */
    @RequestMapping("/userRegister")
    public OutputDTO userRegister(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO = new OutputDTO();
        String phoneNumber = memberForm.getMemberAccount();
        String code = memberForm.getCode();
        String loginPassword = memberForm.getMemberPwd();
        String sessionId = memberForm.getSessionId();
        HttpSession session = (HttpSession) SESSION_MAP.get(sessionId);
        LOGGER.info("获取session对象 {} " + session);
        outputDTO = checkVerificationCode(phoneNumber, code, session);
        if (outputDTO.getCode() == "0") {
            // 清除验证码
            SESSION_MAP.remove(sessionId);
            // 否则的话,就是新用户,需要注册到本地服务和本地服务注册环信用户,本地密码和环信密码一致
            String password_ = MD5Util.generate(loginPassword);
            memberForm.setMemberPwd(password_);
            Map<String, Object> map = BeanUtil.convertBean2Map(memberForm);
            outputDTO = getOutputDTO(map, "memberService", "saveMemberInfo");
        }
        return outputDTO;
    }

    /**
     * 校验手机号验证码是否过期和没有输入验证码请求验证登录
     * 0:登录成功;1:非法手机号码;2:验证码为发送;3:验证码已过期;500:服务器错误
     *
     * @param phoneNumber
     * @param code
     * @param session
     * @return
     */
    public OutputDTO checkVerificationCode(String phoneNumber, String code, HttpSession session) {
        OutputDTO outputDTO = new OutputDTO();
        String match = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
        // 验证手机号码是否符合标准
        boolean matches = phoneNumber.matches(match);
        if (!matches) {
            outputDTO.setCode("1");
            outputDTO.setMsg("无此会员!");
            return outputDTO;
        }
        // 防止没有填写验证码就提交表单
        if (StringUtils.isEmpty(code)) {
            outputDTO.setCode("2");
            outputDTO.setMsg("请先发送验证码!");
            return outputDTO;
        }
        String phone = (String) session.getAttribute("phone");
        if (StringUtils.isNotEmpty(phone) && !phone.equals(phoneNumber)) {
            outputDTO.setCode("3");
            outputDTO.setMsg("输入手机号不一致!");
            return outputDTO;
        }
        String code_ = (String) session.getAttribute(phoneNumber);
        if (StringUtils.isNotEmpty(code_) && !code_.equals(code)) {
            outputDTO.setCode("3");
            outputDTO.setMsg("验证码输入错误!");
            return outputDTO;
        }
        if (StringUtils.isEmpty(code_)) {
            outputDTO.setCode("3");
            outputDTO.setMsg("验证码已过期!");
            return outputDTO;
        }
        outputDTO.setCode("0");
        outputDTO.setMsg("验证成功!");
        return outputDTO;
    }

    /**
     * APP登录接口
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/memberLogin")
    public OutputDTO memberLogin(@ModelAttribute MemberForm memberForm, HttpServletRequest request) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            String jsessionId = request.getRequestedSessionId();
            System.out.println(jsessionId);
            @SuppressWarnings("deprecation")
            String decodePaw = URLDecoder.decode(memberForm.getMemberPwd());
            memberForm.setMemberPwd(decodePaw);
            // 验证帐号密码
            outputDTO = userPasswordIsMatch(memberForm.getMemberAccount(), memberForm.getMemberPwd(), jsessionId);
            return outputDTO;
        } catch (Exception e) {
            LOGGER.error("登录失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 校验用户帐号密码匹配
     *
     * @author zhangshixu
     * @param mobile
     * @param password
     * @return
     */
    public OutputDTO userPasswordIsMatch(String mobile, String password, String jsessionId) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberAccount", mobile);
        OutputDTO output = getOutputDTO(params, "memberService", "queryMemberDetail");
        if (output.getItem() == null || output.getItem().isEmpty()) {
            return new OutputDTO("-1", "帐号不存在");
        }
        if ("3".equals(String.valueOf(output.getItem().get("status")))) {
            return new OutputDTO("-1", "帐号已冻结,请联系管理员解冻!");
        }
        String pwd = (String) output.getItem().get("memberPwd");
        if (MD5Util.verify(password, pwd)) {
            OutputDTO outputDTO = new OutputDTO();
            // 匹配
            getSession().setAttribute(Constants.SESSION_USER.ID, output.getItem().get("id"));
            getSession().setAttribute(Constants.SESSION_USER.ROLE_IDS, Constants.ROOT_ROLE);
            outputDTO.setCode("0");
            outputDTO.setMsg("登录成功");
            output.getItem().put("jsessionId", jsessionId);
            outputDTO.setItem(output.getItem());
            return outputDTO;
        } else {
            return new OutputDTO("-1", "帐号或密码错误");
        }
    }

    /**
     * APP修改登录密码
     *
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/updateLoginPwd")
    public OutputDTO updateLoginPwd(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> params = BeanUtil.convertBean2Map(memberForm);
            OutputDTO output = getOutputDTO(params, "memberService", "queryMemberDetail");
            String pwd = (String) output.getItem().get("memberPwd");
            String password = memberForm.getOldPassword();
            if (MD5Util.verify(password, pwd)) {
                if(memberForm.getNewPassword().equals(memberForm.getOldPassword())){
                    return new OutputDTO("-1", "新密码不能与旧密码相同");
                }
                params.put("memberPwd", MD5Util.generate(memberForm.getNewPassword()));
                params.put("newPassword", memberForm.getNewPassword()); // 不加密，环信使用
                // 修改登录密码
                outputDTO = getOutputDTO(params, "memberService", "updateLoginOrPayPwd");
            } else {
                return new OutputDTO("-1", "旧密码错误");
            }
        } catch (Exception e) {
            LOGGER.error("保存失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * APP修改支付密码
     *
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/updatePaymentPwd")
    public OutputDTO updatePaymentPwd(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> params = BeanUtil.convertBean2Map(memberForm);
            OutputDTO output = getOutputDTO(params, "memberService", "queryMemberDetail");
            String paymentPwd = (String) output.getItem().get("memberPaymentPwd");
            String password = memberForm.getOldPassword();
            if (MD5Util.verify(password, paymentPwd)) {
                if(memberForm.getNewPassword().equals(memberForm.getOldPassword())){
                    return new OutputDTO("-1", "新密码不能与旧密码相同");
                }
                params.put("memberPaymentPwd", MD5Util.generate(memberForm.getNewPassword()));
                // 修改登录密码
                outputDTO = getOutputDTO(params, "memberService", "updateLoginOrPayPwd");
            } else {
                return new OutputDTO("-1", "旧密码错误");
            }
        } catch (Exception e) {
            LOGGER.error("保存失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 忘记登录密码
     *
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/forgetLoginPwd")
    public OutputDTO forgetLoginPwd(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO = new OutputDTO();
        String phoneNumber = memberForm.getMemberAccount();
        String code = memberForm.getCode();
        String newPassword = memberForm.getNewPassword();
        String sessionId = memberForm.getSessionId();
        HttpSession session = (HttpSession) SESSION_MAP.get(sessionId);
        if(session == null){
            return new OutputDTO("-1", "验证码已过期");
        }
        LOGGER.info("获取session对象 {} " + session);
        outputDTO = checkVerificationCode(phoneNumber, code, session);
        if (outputDTO.getCode() == "0") {
            // 清除验证码
            SESSION_MAP.remove(sessionId);
            // 需要同步修改环信IM用户的登录密码
            String password_ = MD5Util.generate(newPassword);
            memberForm.setMemberPwd(password_);
            Map<String, Object> map = BeanUtil.convertBean2Map(memberForm);
            outputDTO = getOutputDTO(map, "memberService", "updateLoginOrPayPwd");
        }
        return outputDTO;
    }

    /**
     * 忘记支付密码
     *
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/forgetPaymentPwd")
    public OutputDTO forgetPaymentPwd(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO = new OutputDTO();
        String phoneNumber = memberForm.getMemberAccount();
        String code = memberForm.getCode();
        String newPassword = memberForm.getNewPassword();
        String sessionId = memberForm.getSessionId();
        HttpSession session = (HttpSession) SESSION_MAP.get(sessionId);
        if(session == null){
            return new OutputDTO("-1", "验证码已过期");
        }
        outputDTO = checkVerificationCode(phoneNumber, code, session);
        if (outputDTO.getCode() == "0") {
            // 清除验证码
            SESSION_MAP.remove(sessionId);
            // 需要同步修改环信IM用户的登录密码
            Map<String, Object> map = BeanUtil.convertBean2Map(memberForm);
            map.put("memberPaymentPwd", MD5Util.generate(newPassword));
            outputDTO = getOutputDTO(map, "memberService", "updateLoginOrPayPwd");
        }
        return outputDTO;
    }

    /**
     * 设置支付密码
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/setupPaymentPwd")
    public OutputDTO setupPaymentPwd(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(memberForm);
            map.put("memberPaymentPwd", MD5Util.generate(memberForm.getMemberPaymentPwd()));
            outputDTO = getOutputDTO(map, "memberService", "updateLoginOrPayPwd");
        } catch (Exception e) {
            LOGGER.error("保存失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * APP查询是否设置支付密码
     *
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/queryPaymentPwdInfo")
    public OutputDTO queryPaymentPwdInfo(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> params = BeanUtil.convertBean2Map(memberForm);
            OutputDTO output = getOutputDTO(params, "memberService", "queryMemberDetail");
            String paymentPwd = (String) output.getItem().get("memberPaymentPwd");
            if (StringUtil.isEmpty(paymentPwd)){
                return new OutputDTO("-1", "请设置支付密码");
            }
        } catch (Exception e) {
            LOGGER.error("保存失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * APP校验支付密码是否正确
     *
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/checkPaymentPwd")
    public OutputDTO checkPaymentPwd(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> params = BeanUtil.convertBean2Map(memberForm);
            OutputDTO output = getOutputDTO(params, "memberService", "queryMemberDetail");
            String paymentPwd = (String) output.getItem().get("memberPaymentPwd");
            String password = memberForm.getMemberPaymentPwd(); // 页面输入的支付密码
            if (!MD5Util.verify(password, paymentPwd)) {
                return new OutputDTO("-1", "支付密码错误");
            }
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 修改绑定手机号（旧手机号验证）
     *
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/oldBindPhoneNum")
    public OutputDTO oldBindPhoneNum(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO;
        try {
            String phoneNumber = memberForm.getBindPhone();
            String code = memberForm.getCode();
            String sessionId = memberForm.getSessionId();
            HttpSession session = (HttpSession) SESSION_MAP.get(sessionId);
            if(session == null){
                return new OutputDTO("-1", "验证码已过期");
            }
            outputDTO = checkVerificationCode(phoneNumber, code, session);
            if ("0".equals(outputDTO.getCode())) {
                // 清除验证码
                SESSION_MAP.remove(sessionId);
                outputDTO = new OutputDTO("0", "旧手机号验证成功");
            }
        } catch (Exception e) {
            LOGGER.error("修改绑定手机号失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 绑定新手机号
     *
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/bindPhoneNum")
    public OutputDTO bindPhoneNum(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO;
        try {
            String phoneNumber = memberForm.getBindPhone();
            String code = memberForm.getCode();
            String sessionId = memberForm.getSessionId();
            HttpSession session = (HttpSession) SESSION_MAP.get(sessionId);
            if(session == null){
                return new OutputDTO("-1", "验证码已过期");
            }
            outputDTO = checkVerificationCode(phoneNumber, code, session);
            if ("0".equals(outputDTO.getCode())) {
                // 清除验证码
                SESSION_MAP.remove(sessionId);
                Map<String, Object> params = BeanUtil.convertBean2Map(memberForm);
                outputDTO = getOutputDTO(params, "memberService", "updateBindPhoneNum");
            }
        } catch (Exception e) {
            LOGGER.error("修改绑定手机号失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 查询会员管理账户列表
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/queryMemberAccountList")
    public OutputDTO queryMemberAccountList(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(memberForm);
            outputDTO = getOutputDTO(map, "memberService", "queryMemberAccountList");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 新增会员管理账户
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/saveMemberAccountInfo")
    public OutputDTO saveMemberAccountInfo(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(memberForm);
            outputDTO = getOutputDTO(map, "memberService", "saveMemberAccountInfo");
        } catch (Exception e) {
            LOGGER.error("保存失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 删除会员管理账户
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/delMemberAccountInfo")
    public OutputDTO delMemberAccountInfo(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(memberForm);
            outputDTO = getOutputDTO(map, "memberService", "delMemberAccountInfo");
        } catch (Exception e) {
            LOGGER.error("删除失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 修改会员个人信息
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/updateMemberInfo")
    public OutputDTO updateMemberInfo(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO = new OutputDTO();
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(memberForm);
            outputDTO = getOutputDTO(map, "memberService", "updateMemberInfo");
        } catch (Exception e) {
            LOGGER.error("保存失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 修改头像信息
     * @param memberForm
     * @return
     */
    @RequestMapping("/uploadPersonalHeadImg")
    public OutputDTO uploadPersonalHeadImg(@ModelAttribute MemberForm memberForm) {
        Map<String, Object> map = new HashMap<>();
        OutputDTO outputDTO;
        String prefix = "/profile/upload/app/head/";
        try {
            String memberAccount = memberForm.getMemberAccount();
            String base64Data = memberForm.getHeadImgUrl(); // 用户头像
            String uploadPath_ = Global.getUploadPath() + "app/head/";
            LOGGER.info("上传图片到服务器的路径全路径 {} " + uploadPath_);
            String dataPrix = "";
            String data = "";
            if (StringUtils.isEmpty(base64Data)) {
                return new OutputDTO("500", "上传图片为空!");
            }
            // data:image/gif;base64,R0lGODlhHAAmAKIHAKqqqsvLy0hISObm5vf394uLiwAAAP///yH5B…EoqQqJKAIBaQOVKHAXr3t7txgBjboSvB8EpLoFZywOAo3LFE5lYs/QW9LT1TRk1V7S2xYJADs=
            String[] d = base64Data.split("base64,");
            if (d != null && d.length == 2) {
                dataPrix = d[0];
                data = d[1];
            } else {
                return new OutputDTO("500", "上传图片数据不合法!");
            }
            String headImgLastPrix = getHeadImgLastPrix(dataPrix);
            if ("-1".equals(headImgLastPrix)) {
                return new OutputDTO("500", "上传图片格式不合法!");
            }
            // 查看是不是第一次上传,如果不是讲上传的图片直接删除,之后再重新上传即可
            outputDTO = queryMemberDetail(memberForm);
            Map<String, Object> item = outputDTO.getItem();
            if (null != item) {
                String headImgUrl = (String) item.get("headImgUrl");
                if(headImgUrl != null && headImgUrl != ""){
                    File file = new File(headImgUrl);
                    if (headImgUrl != null && file.exists()) {
                        FileUtils.forceDelete(file);
                    }
                }
            }
            // 生成新的图片名称,并上传图片:
            String tempFileName = getRandomFileName() + headImgLastPrix;
            LOGGER.info("文件名称 {} " + tempFileName);
            byte[] bytes = Base64Utils.decodeFromString(data);
            File file = new File(uploadPath_, tempFileName);
            FileUtils.writeByteArrayToFile(file, bytes);

            // 保存图片到数据库
            memberForm.setMemberAccount(memberAccount);
            memberForm.setHeadImgUrl(prefix + tempFileName);
            Map<String, Object> params = BeanUtil.convertBean2Map(memberForm);
            outputDTO = getOutputDTO(params, "memberService", "updateMemberInfo");
            if ("0".equals(outputDTO.getCode())) {
                map.put("headImgUrl", prefix + tempFileName);
                outputDTO.setItem(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return new OutputDTO("500", "上传图片失败!");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("保存图片路径到数据库失败:" + e.getMessage());
            return new OutputDTO("500", "服务器错误(1,-1)!");
        }
        return outputDTO;
    }

    /**
     * 文件名随机生成
     */
    private static final String getRandomFileName() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取图片格式:
     *
     * @param dataPrix
     * @return
     */
    private String getHeadImgLastPrix(String dataPrix) {
        String suffix = "";
        String DATA_IMAGE_JPEG = "data:image/jpeg;";
        String DATA_IMAGE_JPG = "data:image/jpg;";
        String DATA_IMAGE_GIF = "data:image/gif;";
        String DATA_IMAGE_PNG = "data:image/png;";
        /**
         * data:image/jpeg;base64,base64编码的jpeg图片数据
         * data:image/gif;base64,base64编码的gif图片数据
         * data:image/png;base64,base64编码的png图片数据
         */
        if (DATA_IMAGE_JPEG.equalsIgnoreCase(dataPrix) || DATA_IMAGE_JPG.equalsIgnoreCase(dataPrix)) {
            suffix = ".jpg";
        } else if (DATA_IMAGE_GIF.equalsIgnoreCase(dataPrix)) {
            suffix = ".gif";
        } else if (DATA_IMAGE_PNG.equalsIgnoreCase(dataPrix)) {
            suffix = ".png";
        } else {
            suffix = "-1";
        }
        return suffix;
    }

    /**
     * 查询我的众享信息
     *
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/queryMyZxMessageList")
    public OutputDTO queryMyZxMessageList(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO;
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(memberForm);
            outputDTO = getOutputDTO(map, "memberService", "queryMyZxMessageList");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

    /**
     * 查询我的广告红包列表
     *
     * @param memberForm 封装实体类
     * @return OutputDTO 出参
     */
    @RequestMapping("/queryAllRedPacketList")
    public OutputDTO queryAllRedPacketList(@ModelAttribute MemberForm memberForm) {
        OutputDTO outputDTO;
        try {
            Map<String, Object> map = BeanUtil.convertBean2Map(memberForm);
            outputDTO = getOutputDTO(map, "memberService", "queryAllRedPacketList");
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
            outputDTO = new OutputDTO("1", "系统错误");
        }
        return outputDTO;
    }

}
