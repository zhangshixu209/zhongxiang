package com.chmei.nzbdata.user.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.util.MD5Util;
import com.chmei.nzbcommon.util.StringUtil;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.user.service.TUAdminService;
import com.chmei.nzbdata.util.Constants;

@Transactional
@Service("tUAdminService")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TUAdminServiceImpl extends BaseServiceImpl implements TUAdminService {
    /**
     * 日志类
     */
    private static final Logger logger = LoggerFactory.getLogger(RightManageServiceImpl.class);

    @Override
    public void queryObject(InputDTO input, OutputDTO output) throws NzbDataException {
        Map queryForObject = getBaseDao().queryForObject("TUAdminMapper.queryObject", input.getParams(), Map.class);
        output.setItem(queryForObject);
    }

    @Override
    public void queryList(InputDTO input, OutputDTO output) throws NzbDataException {
        // 获取起始索引
        Integer l = (Integer) input.getParams().get("limit");
        Integer limit = l == null ? 10 : l;
        Integer pageNumber = (Integer) input.getParams().get("pageNumber");
        Integer offset = (pageNumber - 1) * limit;
        input.getParams().put("offset", offset);
        int count = getBaseDao().getTotalCount("TUAdminMapper.queryTotal", input.getParams());
        output.setTotal(count);
        if (count > 0) {
            // 存在数据
            List<Map<String, Object>> list = getBaseDao().queryForList("TUAdminMapper.queryList", input.getParams());
            output.setItems(list);
        }
    }

    @Override
    public void save(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> map = input.getParams();
        long id = getSequence();
        map.put("id", id);
        getCrtInfo(map, input);
        int insert = getBaseDao().insert("TUAdminMapper.save", map);
        if (insert > 0) {
            logger.info("新增管理员，管理员Id=[{}],操作人Id=[{}]", id, map.get("crtUserId"));
            addOperLog(input, JSONObject.toJSONString(input.getParams()));
        }
        output.setTotal(insert);

    }

    @Override
    public void update(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> map = input.getParams();
        getModfInfo(map, input);
        int i = getBaseDao().update("TUAdminMapper.update", map);
        if (i > 0) {
            logger.info("修改管理员，管理员Id=[{}],操作人Id=[{}]", input.getParams().get("id"), map.get("modfUserId"));
            addOperLog(input, JSONObject.toJSONString(input.getParams()));
        }
        output.setTotal(i);
    }

    @Override
    public void queryListWithRole(InputDTO input, OutputDTO output) throws NzbDataException {
        // 获取起始索引
        Integer l = (Integer) input.getParams().get("limit");
        Integer limit = l == null ? 10 : l;
        Integer pageNumber = (Integer) input.getParams().get("pageNumber");
        Integer offset = (pageNumber - 1) * limit;
        input.getParams().put("offset", offset);
        int count = getBaseDao().getTotalCount("TUAdminMapper.queryListWithRoleTotal", input.getParams());
        output.setTotal(count);
        if (count > 0) {
            // 存在数据
            List<Map<String, Object>> list = getBaseDao().queryForList("TUAdminMapper.queryListWithRole",
                    input.getParams());
            output.setItems(list);
        }
    }

    @Override
    public void queryAdminByLoginId(InputDTO input, OutputDTO output) throws NzbDataException {
        Map queryForObject = getBaseDao().queryForObject("TUAdminMapper.queryAdminByLoginId", input.getParams(),
                Map.class);
        output.setItem(queryForObject);
    }
    
    /**
     * 根据id删除用户
     * @param input
     * @param output
     * @throws NzbDataException
     */
    @Override
    public void delAdminDetail(InputDTO input, OutputDTO output) throws NzbDataException {
    	//删除用户信息
    	getBaseDao().delete("TUAdminMapper.delAdminDetail",input.getParams());
    	//删除用户关联角色信息
    	getBaseDao().delete("TURoleUserMapper.deleteRoleForUser", input.getParams());
    }
    
    /**
     * 根据用户id禁用账号和启用账号
     * @param input
     * @param output
     * @throws NzbDataException
     */
    @Override
    public void enableORdisable(InputDTO input, OutputDTO output) throws NzbDataException {
    	//删除用户信息
    	getBaseDao().update("TUAdminMapper.enableORdisable",input.getParams());
    }

    @Override
    public void queryAdminByMobile(InputDTO input, OutputDTO output) throws NzbDataException {
        Map queryForObject = getBaseDao().queryForObject("TUAdminMapper.queryAdminByMobile", input.getParams(),
                Map.class);
        output.setItem(queryForObject);
    }

    @Override
    public void updateAdminPwd(InputDTO inputDTO, OutputDTO outputDTO) throws NzbDataException {
        Map<String, Object> map = inputDTO.getParams();
        getModfInfo(map, inputDTO);
        int i = getBaseDao().update("TUAdminMapper.updateAdminPwd", map);
        if (i > 0) {
            logger.info("修改管理员，管理员Id=[{}],操作人Id=[{}]", inputDTO.getParams().get("mobile"), map.get("modfUserId"));
            addOperLog(inputDTO, JSONObject.toJSONString(inputDTO.getParams()));
        }
        outputDTO.setTotal(i);
    }
    
    
    
    /**
     * 获取部门人员树状
     */
    @Override
    public void getDeepartmentUserTree(InputDTO input, OutputDTO output) throws NzbDataException {
    	logger.info("获取部门人员树状...TUAdminServiceImpl.getDeepartmentUserTree()...");
        List<Map<String, Object>> list = getBaseDao().queryForList("TUAdminMapper.getDeepartmentUserTree", input.getParams());
        output.setItems(list);
    }
    
    /**
     * 获取部门人员树状
     */
    @Override
    public void getReportUserTree(InputDTO input, OutputDTO output) throws NzbDataException {
    	logger.info("获取部门人员树状...TUAdminServiceImpl.getReportUserTree()...");
    	Map<String, Object> param = input.getParams();
    	try {
            Object object = input.getParams().get("busiNumArr");
            JSONArray jsonArray = JSONArray.fromObject(object);
            if (object == null  && jsonArray != null && jsonArray.size() > 0) {
                output.setCode("-1");
                output.setMsg("高级查询表头获取失败!");
                return;
            }
    	    String[] busiNumArr = new String[jsonArray.size()];
            for (int i = 0; i < busiNumArr.length; i++) {
                busiNumArr[i] = jsonArray.getString(i);
            }
    	    param.put("busiNumArr", busiNumArr);
    	    List<Map<String, Object>> userList = getBaseDao().queryForList("reportConfigMapper.queryRoleUserId", param);
    	    List<Map<String, Object>> list = getBaseDao().queryForList("TUAdminMapper.getReportUserTree", userList);
            output.setItems(list);
        } catch (Exception e) {
            logger.error("查询失败");
        }
    }

    /**
     * 批量导入用户信息
     * @param input 入參
     * @param output 返回对象
     * @throws NzbDataException  自定义异常
     */
    @Override
    public void importExcelUserData(InputDTO input, OutputDTO output)
            throws NzbDataException {
        Map<String, Object> params = input.getParams();
        List<Map<String, Object>> listAll = new ArrayList<Map<String,Object>>();
        List<String> tel = new ArrayList<String>();
        List<String> card = new ArrayList<String>();
        // 查询用户角色信息
        List<Map<String, Object>> roleList = getBaseDao().queryForList("TUAdminMapper.queryTURoleInfo", input.getParams());
        // 查询部门信息
        List<Map<String, Object>> dpList = getBaseDao().queryForList("TUAdminMapper.getDeepartmentInfo", input.getParams());
        List<Map<String,Object>> roleSaveList = new ArrayList<>();
        try {
            List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("items");
            String msg = "";
            for (int i = 1; i < list.size() + 1; i++) {
            	msg = "第 "+(i+1)+" 行，";//提示行数
                Map<String, Object> map = list.get(i - 1);
                map.put("id", getSequence());
                map.put("loginPw", MD5Util.generate(Constants.DEFAULT_USER_PWD));
                map.put("crtUserId", input.getComParams().get("accountId"));
                String userName = (String) map.get("userName");
                if (StringUtil.isEmpty(userName)) {
                    output.setCode("-1");
                    output.setMsg(msg+"用户姓名不允许为空！");
                    return;
                }
                String loginId = (String) map.get("loginId");
                if (StringUtil.isNotEmpty(loginId)) {
                    if (!isTel(loginId)) {
                        output.setCode("-1");
                        output.setMsg(msg+"工号（手机号）格式不正确！");
                        return;
                    } else {
                        List<String> telCount = checkTel(input, output);
                        if (telCount.contains(loginId)) {
                            output.setCode("-1");
                            output.setMsg(msg+"工号（手机号）已存在！");
                            return;
                        } else {
                            if (tel.contains(loginId)) {
                                output.setCode("-1");
                                output.setMsg(msg+"工号（手机号）存在重复数据！");
                                return;
                            } else {
                                tel.add(loginId);
                            }
                        }
                    }
                } else {
                    output.setCode("-1");
                    output.setMsg(msg+"工号（手机号）不允许为空！");
                    return;
                }
                String cardNum = (String) map.get("cardNum");
                if (StringUtil.isNotEmpty(cardNum)) {
                    boolean isOKCrdNum = isCredum(cardNum);
                    if (!isOKCrdNum) {
                        output.setCode("-1");
                        output.setMsg(msg+"身份证格式不正确！");
                        return;
                    } else {
                        List<String> cardList = checkCardNum(input, output);
                        if (cardList.contains(cardNum)) {
                            output.setCode("-1");
                            output.setMsg(msg+"身份证号已存在！");
                            return;
                        } else {
                            if (card.contains(cardNum)) {
                                output.setCode("-1");
                                output.setMsg(msg+"身份证号存在重复数据！");
                                return;
                            } else {
                                card.add(cardNum);
                            }
                        }
                    }
                } else {
                    output.setCode("-1");
                    output.setMsg(msg+"身份证号码不允许为空！");
                    return;
                }
                String sex = (String) map.get("sex");
                if (StringUtil.isNotEmpty(sex)) {
                    if ("男".equals(sex)) {
                        map.put("sex", "0");
                    } else if("女".equals(sex)) {
                        map.put("sex", "1");
                    } else {
                        output.setCode("-1");
                        output.setMsg(msg+"性别编码不正确！");
                        return;
                    }
                } else {
                    output.setCode("-1");
                    output.setMsg(msg+"性别不允许为空！");
                    return;
                }
                String userDepartmentName = (String) map.get("userDepartmentName");
                if (StringUtil.isNotEmpty(userDepartmentName)) {
                    boolean flag = false;
                    for (Map<String, Object> map2 : dpList) {
                        // 判断是否存在部门
                        if (userDepartmentName.equals(map2.get("name"))) {
                            map.put("userDepartmentId", map2.get("id"));
                            map.put("userDepartmentName", map2.get("name"));
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        output.setCode("-1");
                        output.setMsg(msg+"不存在该部门！");
                        return; 
                    }
                } else {
                    output.setCode("-1");
                    output.setMsg(msg+"部门不允许为空！");
                    return;
                }
                String roleUser = (String) map.get("roleUser");
                
                if (StringUtil.isNotEmpty(roleUser)) {
                    String roleUserRe = roleUser.replaceAll("，", ",");
                    boolean flag = false;
                    String[] roles = roleUserRe.split(",");
                    List<String> roleA = new ArrayList<String>();
                    for (Map<String, Object> roleMap : roleList) {
                        for (String string : roles) {
                            Map<String, Object> result = new HashMap<>();
                            // 判断角色是否存在
                            if (string.equals(roleMap.get("roleName"))) {
                                result.put("userId", map.get("id"));
                                result.put("roleId", roleMap.get("roleId"));
                                flag = true;
                                if(!roleA.contains(string)){
                                    roleA.add(string);
                                    roleSaveList.add(result);
                                }
                            }
                        }
                        
                    }
                    if (!flag) {
                        output.setCode("-1");
                        output.setMsg(msg+"关联角色不存在！");
                        return;
                    }
                } else {
                    output.setCode("-1");
                    output.setMsg(msg+"关联角色不允许为空！");
                    return;
                }
                listAll.add(map);
                params.put("roleList", roleSaveList);
            }
            output.setItem(params); // 新增关联用户角色使用
            int total = getBaseDao().insert("TUAdminMapper.importExcelUserData", listAll);
            if (total > 0) {
                batchInsertRoleUser(input, output); // 批量新增关联用户角色
                output.setTotal(total);
                output.setMsg("导入数据成功！");
            }
        } catch (Exception e) {
            logger.error("导入数据失败", e);
            output.setCode("1");
            output.setMsg("导入数据失败");
        }
    }
    
    /**
     * 批量新增关联用户角色
     * @param input
     * @param output
     * @throws NzbDataException
     */
    private void batchInsertRoleUser(InputDTO input, OutputDTO output)
            throws NzbDataException {
        Map<String, Object> params = output.getItem();
        List<Map<String, Object>> listAll = new ArrayList<>();
        try {
            List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("roleList");
            for (Map<String, Object> map : list) {
                map.put("id", getSequence());
                listAll.add(map);
            }
            int total = getBaseDao().insert("TUAdminMapper.batchInsertRoleUser", listAll);
            if (total > 0) {
                logger.error("导入用户角色数据成功");
            }
        } catch (Exception e) {
            logger.error("导入用户角色数据失败", e);
            output.setCode("1");
            output.setMsg("导入数据失败");
        }
    }
    
    /**
     * 重新封装工号手机号
     * @param input
     * @param output
     * @return
     * @throws NzbDataException
     */
    private List<String> checkTel(InputDTO input, OutputDTO output)
            throws NzbDataException {
        Map<String, Object> param = input.getParams();
        List<String> telList = new ArrayList<String>();
        try {
            List<Map<String, Object>> list = getBaseDao().
                    queryForList("TUAdminMapper.checkUserTelAndCardNum", param);
            for (Map<String, Object> map : list) {
                telList.add((String) map.get("loginId"));
            }
        } catch (Exception e) {
            logger.error("查询失败", e);
        }
        return telList;
    }
    
    /**
     * 重新封装身份证号
     * @param input
     * @param output
     * @return
     * @throws NzbDataException
     */
    private List<String> checkCardNum(InputDTO input, OutputDTO output)
            throws NzbDataException {
        Map<String, Object> param = input.getParams();
        List<String> telList = new ArrayList<String>();
        try {
            List<Map<String, Object>> list = getBaseDao().
                    queryForList("TUAdminMapper.checkUserTelAndCardNum", param);
            for (Map<String, Object> map : list) {
                telList.add((String) map.get("cardNum"));
            }
        } catch (Exception e) {
            logger.error("查询失败", e);
        }
        return telList;
    }
    
    /**
     * 判断是否为准确身份证
     * 
     * @param param 身份证号码
     * @return
     */
    public static boolean isCredum(String param) {
        if (StringUtil.isEmpty(param)) {
            return false;
        }
        if (param.length() == 18 || param.length() == 20) {
            //18位身份证号或20位残疾人证
            int year = Integer.parseInt(param.substring(6, 10));
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyyy");
            int yearNow = Integer.parseInt(format.format(date));
            if (yearNow - year > 110 || yearNow - year < 0) {
                return false;
            } else {
                String regx18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
                String regx20 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)\\d{2}$";
                if(param.matches(regx18)){
                    return true;
                }else if(param.matches(regx20)){
                    return true;
                }else{
                    return false;
                }
            }
        } else {
            String regx15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
            if (param.matches(regx15)) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    /**
     * 判断是否为准确号码格式
     * 
     * @param param 手机号码
     * @return
     */
    public static boolean isTel(String param) {
        Pattern p = Pattern.compile("^(13[0-9]|14[0-9]|15[^4,\\D]|17[0-9]|18[0-9])\\d{8}$");
        Matcher m = p.matcher(param);
        return m.matches();
    }

    /**
     * 获取所有用户及部门权限信息
     * @param input 入參
     * @param output 返回对象
     * @throws NzbDataException  自定义异常
     */
    @Override
    public void getDataTableUserTree(InputDTO input, OutputDTO output)
            throws NzbDataException {
        logger.info("获取部门人员树状...TUAdminServiceImpl.getDataTableUserTree()...");
        Map<String, Object> param = input.getParams();
        try {
            List<Map<String, Object>> list = getBaseDao().queryForList("TUAdminMapper.getDataTableUserTree", param);
            output.setItems(list);
        } catch (Exception e) {
            logger.error("查询失败");
        }
    }

}
