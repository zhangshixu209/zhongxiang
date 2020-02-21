package com.chmei.nzbdata.notice.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.util.Constants;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.notice.service.INoticeService;
import com.chmei.nzbdata.operatelog.service.IOperateLogService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
/**
 * 公告管理接口实现类
 *
 * @author wangteng
 * @since 2019年5月13日
 */
@Service("noticeService")
public class NoticeServiceImpl extends BaseServiceImpl implements INoticeService {

    /** LOGGER */
    private static final Logger LOGGER = Logger.getLogger(NoticeServiceImpl.class);
    /**
     * 调用操作日志
     */
    @Autowired
    private IOperateLogService operateLogService;

    /**
     * 存草稿插入公告信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void insertNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            long id = getSequence();
            params.put("id", id);
            params.put("code", "GG" + id);
            //根据userId获取到部门信息
            Map<String, Object> noticeMap = (Map<String, Object>) params.get("notice");
            Long crtId = (Long) noticeMap.get("crtId");
            Map<String, Object> userMeaasge = (Map<String, Object>) getBaseDao().queryForObject("TUAdminMapper.queryObject", crtId);
            String publishDepartment = (String) userMeaasge.get("user_department_id");
            params.put("publishDepartment", publishDepartment);
            int count = getBaseDao().insert("noticeMapper.insertNoticeMessageInfo", params);
            if (count <= 0) {
                output.setCode("-1");
                output.setMsg("新增失败");
            }
           List<Map<String, Object>> noticePermissions = new ArrayList<>();
            //添加权限信息
            List<Long> userIds = (List<Long>) params.get("userIds");
            if (userIds != null && !userIds.isEmpty()) {
                for (Long userId : userIds) {
                    Map<String, Object> noticePermission = new HashMap<>();
                    noticePermission.put("noticeId", id);
                    noticePermission.put("crtId", userId);
                    noticePermission.put("status", 0);
                    noticePermissions.add(noticePermission);
                }
            } else {
                Map<String, Object> noticePermission = new HashMap<>();
                noticePermission.put("noticeId", id);
                noticePermission.put("crtId", -9999);
                noticePermission.put("status", 0);
                noticePermissions.add(noticePermission);
            }
            //插入权限信息
            getBaseDao().insert("noticePermissionMapper.insertNoticePermissionMessageInfo", noticePermissions);
            output.setCode("0");
            if ("0".equals(output.getCode())) {
                //保存成功插入操作日志信息
                input.getParams().put("operatePage",
                        Constants.OPERATE_PAGE_CD.NOTICE_TYPE_CD); //操作页面代码
                input.getParams().put("operateType",
                        Constants.OPERATE_TYPE_CD.OPERATE_INSERT_DRAF_CD); //操作类型代码
                operateLogService.saveOperateLogInfo(input, output);
            }
        } catch (Exception e) {
            LOGGER.error("新增失败" + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }

    /**
     * 发布插入公告信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @SuppressWarnings("unchecked")
    @Override
    public void publishNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            long id = getSequence();
            params.put("id", id);
            params.put("code", "GG" + id);
            //根据userId获取到部门信息
            Map<String, Object> noticeMap = (Map<String, Object>) params.get("notice");
            Long crtId = (Long) noticeMap.get("crtId");
            Map<String, Object> userMeaasge = (Map<String, Object>) getBaseDao().queryForObject("TUAdminMapper.queryObject", crtId);
            String publishDepartment = (String) userMeaasge.get("user_department_id");
            params.put("publishDepartment", publishDepartment);
            int count = getBaseDao().insert("noticeMapper.insertNoticeMessageInfo", params);
            if (count <= 0) {
                output.setCode("-1");
                output.setMsg("新增失败");
            }
            // 添加推送
            Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject("noticeMapper.queryNoticeDetailInfo", params);
            map.put("id", getSequence());
            map.put("messageTitle", map.get("name"));
            map.put("messageContent", map.get("content"));
            map.put("messageStatus", "1");
            map.put("messageType", com.chmei.nzbdata.util.Constants.MESSAGE_TYPE_1002);
            map.put("memberAccount", "9999");
            // 添加推送消息
            getBaseDao().insert("ZxPushMessageMapper.savePushMessageInfo", map);
            List<Map<String, Object>> noticePermissions = new ArrayList<>();
            //添加权限信息
            List<Long> userIds = (List<Long>) params.get("userIds");
            if (userIds != null && !userIds.isEmpty()) {
                for (Long userId : userIds) {
                    Map<String, Object> noticePermission = new HashMap<>();
                    noticePermission.put("noticeId", id);
                    noticePermission.put("crtId", userId);
                    noticePermission.put("status", 1);
                    noticePermissions.add(noticePermission);
                }
            } else {
                Map<String, Object> noticePermission = new HashMap<>();
                noticePermission.put("noticeId", id);
                noticePermission.put("crtId", -9999);
                noticePermission.put("status", 1);
                noticePermissions.add(noticePermission);
            }
            //插入权限信息
            getBaseDao().insert("noticePermissionMapper.insertNoticePermissionMessageInfo", noticePermissions);
            if ("0".equals(output.getCode())) {
                //保存成功插入操作日志信息
                input.getParams().put("operatePage",
                        Constants.OPERATE_PAGE_CD.NOTICE_TYPE_CD); //操作页面代码
                input.getParams().put("operateType",
                        Constants.OPERATE_TYPE_CD.OPERATE_INSERT_SUBMIT_CD); //操作类型代码
                operateLogService.saveOperateLogInfo(input, output);
            }
        } catch (Exception e) {
            LOGGER.error("新增失败" + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }

    /**
     * 编辑存草稿
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void updateNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            //更新公告信息
            int count = getBaseDao().update("noticeMapper.updateNoticeMessageInfo", params);
            //删除权限信息
            getBaseDao().delete("noticePermissionMapper.deleteNoticeMessageInfo", params);
            //获取到公告id
            Map<String, Object> notice = (Map<String, Object>) params.get("notice");
            Long id = (Long) notice.get("id");
            //添加权限信息
            List<Map<String, Object>> noticePermissions = new ArrayList<>();
            List<Long> userIds = (List<Long>) params.get("userIds");
            if (userIds != null && !userIds.isEmpty()) {
                for (Long userId : userIds) {
                    Map<String, Object> noticePermission = new HashMap<>();
                    noticePermission.put("noticeId", id);
                    noticePermission.put("crtId", userId);
                    noticePermission.put("status", 0);
                    noticePermissions.add(noticePermission);
                }
            } else {
                Map<String, Object> noticePermission = new HashMap<>();
                noticePermission.put("noticeId", id);
                noticePermission.put("crtId", -9999);
                noticePermission.put("status", 0);
                noticePermissions.add(noticePermission);
            }
            //插入权限信息
            getBaseDao().insert("noticePermissionMapper.insertNoticePermissionMessageInfo", noticePermissions);
            if ("0".equals(output.getCode())) {
                //保存成功插入操作日志信息
                input.getParams().put("operatePage",
                        Constants.OPERATE_PAGE_CD.NOTICE_TYPE_CD); //操作页面代码
                input.getParams().put("operateType",
                        Constants.OPERATE_TYPE_CD.OPERATE_UPDATE_DRAF_CD); //操作类型代码
                operateLogService.saveOperateLogInfo(input, output);
            }

        } catch (Exception e) {
            LOGGER.error("新增失败" + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }

    /**
     * 编辑发布
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void editPublishNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            //更新公告信息
            int count = getBaseDao().update("noticeMapper.updateNoticeMessageInfo", params);
            //删除权限信息
            getBaseDao().delete("noticePermissionMapper.deleteNoticeMessageInfo", params);
            //获取到公告id
            Map<String, Object> notice =(Map<String, Object>) params.get("notice");
            Long id = (Long) notice.get("id");
            params.put("id", id);
            // 添加推送
            Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject("noticeMapper.queryNoticeDetailInfo", params);
            map.put("id", getSequence());
            map.put("messageTitle", map.get("name"));
            map.put("messageContent", map.get("content"));
            map.put("messageStatus", "1");
            map.put("messageType", com.chmei.nzbdata.util.Constants.MESSAGE_TYPE_1002);
            map.put("memberAccount", "9999");
            // 添加推送消息
            getBaseDao().insert("ZxPushMessageMapper.savePushMessageInfo", map);
            //添加权限信息
            List<Map<String, Object>> noticePermissions = new ArrayList<>();
            List<Long> userIds =(List<Long>) params.get("userIds");
            if (userIds != null && !userIds.isEmpty()) {
                for (Long userId : userIds) {
                    Map<String, Object> noticePermission = new HashMap<>();
                    noticePermission.put("noticeId", id);
                    noticePermission.put("crtId", userId);
                    noticePermission.put("status", 1);
                    noticePermissions.add(noticePermission);
                }
            } else {
                Map<String, Object> noticePermission = new HashMap<>();
                noticePermission.put("noticeId", id);
                noticePermission.put("crtId", -9999);
                noticePermission.put("status", 1);
                noticePermissions.add(noticePermission);
            }
            //插入权限信息
            getBaseDao().insert("noticePermissionMapper.insertNoticePermissionMessageInfo", noticePermissions);
            if ("0".equals(output.getCode())) {
                //保存成功插入操作日志信息
                input.getParams().put("operatePage",
                        Constants.OPERATE_PAGE_CD.NOTICE_TYPE_CD); //操作页面代码
                input.getParams().put("operateType",
                        Constants.OPERATE_TYPE_CD.OPERATE_UPDATE_SUBMIT_CD); //操作类型代码
                operateLogService.saveOperateLogInfo(input, output);
            }
        } catch (Exception e) {
            LOGGER.error("新增失败" + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }

    /**
     * 删除公告信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void deleteNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            //删除公告信息
            int count = getBaseDao().delete("noticeMapper.deleteNoticeMessageInfo", params);
            //删除公告权限信息
            getBaseDao().delete("noticePermissionMapper.deleteNoticePermissionMessageInfo", params);
            if ("0".equals(output.getCode())) {
                //保存成功插入操作日志信息
                input.getParams().put("operatePage",
                        Constants.OPERATE_PAGE_CD.NOTICE_TYPE_CD); //操作页面代码
                input.getParams().put("operateType",
                        Constants.OPERATE_TYPE_CD.OPERATE_DELETE_CD); //操作类型代码
                operateLogService.saveOperateLogInfo(input, output);
            }
        } catch (Exception e) {
            LOGGER.error("删除失败" + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }

    /**
     * 外部发布按钮
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void publishBtnNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            //1.判断该公告状态
            Map<String, Object> notice = (Map<String, Object>) getBaseDao().queryForObject("noticeMapper.queryNoticeDetailMessageInfo", params);
            Integer status = (Integer) notice.get("status");
            if (1003 == status) { //如果是已发布则提示已发布不可重复发布
                output.setCode("-1");
                output.setMsg("该公告已是发布状态");
            } else if (1001 == status || 1004  == status) { //如果是未发布则发布
                //更新该条公告的发布状态为已发布
                params.put("publishTime", new Date());
                params.put("modfTime", new Date());
                params.put("status", 1003);
                getBaseDao().update("noticeMapper.updateNoticeStatus", params);
                //更新该条公告对应的权限信息状态为有效
                params.put("permissionStatus", 1);
                getBaseDao().update("noticePermissionMapper.updateNoticePermissionStatus", params);
                // 添加推送
                Map<String, Object> map = (Map<String, Object>) getBaseDao().queryForObject("noticeMapper.queryNoticeDetailInfo", params);
                map.put("id", getSequence());
                map.put("messageTitle", map.get("name"));
                map.put("messageContent", map.get("content"));
                map.put("messageStatus", "1");
                map.put("messageType", com.chmei.nzbdata.util.Constants.MESSAGE_TYPE_1002);
                map.put("memberAccount", "9999");
                // 添加推送消息
                getBaseDao().insert("ZxPushMessageMapper.savePushMessageInfo", map);
            }
            if ("0".equals(output.getCode())) {
                //保存成功插入操作日志信息
                input.getParams().put("operatePage",
                        Constants.OPERATE_PAGE_CD.NOTICE_TYPE_CD); //操作页面代码
                input.getParams().put("operateType",
                        Constants.OPERATE_TYPE_CD.OPERATE_PUBLISH_CD); //操作类型代码
                operateLogService.saveOperateLogInfo(input, output);
            }
        } catch (Exception e) {
            output.setCode("-9999");
            output.setMsg("系统错误");
        }

    }

    /**
     * 外部撤回按钮
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void recallBtnNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            //1.判断该公告状态
            Map<String, Object> notice = (Map<String, Object>) getBaseDao().queryForObject("noticeMapper.queryNoticeDetailMessageInfo", params);
            Integer status = (Integer) notice.get("status");
            if (1004 == status) { //已撤回
                output.setCode("-1");
                output.setMsg("该公告已撤回状态");
            } else if (1001 == status){ //草稿
                output.setCode("-2");
                output.setMsg("该公告是草稿状态,无法进行此操作");
            } else if(1003 == status){ //发布
                //修改状态为撤回
                params.put("publishTime", new Date());
                params.put("modfTime", new Date());
                params.put("status", 1004);
                getBaseDao().update("noticeMapper.updateNoticeStatus", params);
                //修改权限信息状态为无效
                params.put("permissionStatus", 0);
                getBaseDao().update("noticePermissionMapper.updateNoticePermissionStatus", params);
            }
            if ("0".equals(output.getCode())) {
                //保存成功插入操作日志信息
                input.getParams().put("operatePage",
                        Constants.OPERATE_PAGE_CD.NOTICE_TYPE_CD); //操作页面代码
                input.getParams().put("operateType",
                        Constants.OPERATE_TYPE_CD.OPERATE_REBACK_CD); //操作类型代码
                operateLogService.saveOperateLogInfo(input, output);
            }
        } catch (Exception e) {
            output.setCode("-9999");
            output.setMsg("系统错误");
        }
    }

    /**
     * 查询公告信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void queryNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        String isRoot = (String) params.get("isRoot");
        try {
            if ("1".equals(isRoot)) { //是超级管理员
                int count = getBaseDao().getTotalCount("noticeMapper.queryIsRootNoticeMessageInfoTotal", params);
                if (count > 0) {
                    List<Map<String, Object>> list = getBaseDao()
                            .queryForList("noticeMapper.queryIsRootNoticeMessageInfoList", params);
                    output.setItems(list);
                }
                output.setTotal(count);
             } else { //不是超级管理员
                int count = getBaseDao().getTotalCount("noticeMapper.queryNoticeMessageInfoTotal", params);
                if (count > 0) {
                    List<Map<String, Object>> list = getBaseDao()
                            .queryForList("noticeMapper.queryNoticeMessageInfoList", params);
                    output.setItems(list);
                }
                output.setTotal(count);
            }
            if ("0".equals(output.getCode())) {
                //保存成功插入操作日志信息
                input.getParams().put("operatePage",
                        Constants.OPERATE_PAGE_CD.NOTICE_MANAGE_CD); //操作页面代码
                input.getParams().put("operateType",
                        Constants.OPERATE_TYPE_CD.OPERATE_QUERY_CD); //操作类型代码
                operateLogService.saveOperateLogInfo(input, output);
            }
        } catch (Exception e) {
            LOGGER.error("查询失败" + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }

    /**
     * 查看单条公告信息详情
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void queryNoticeDetailMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            Map<String, Object> item = (Map<String, Object>)  getBaseDao()
                    .queryForObject("noticeMapper.queryNoticeDetailMessageInfo", params);
            //查询权限信息

            output.setItem(item);
        } catch (Exception e) {
            LOGGER.error("查询失败" + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }

    /**
     * 加载公告状态列表
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void queryNoticeStatusMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            List<Map<String, Object>> list = getBaseDao()
                    .queryForList("noticeMapper.queryNoticeStatusMessageInfo", params);
            output.setItems(list);
        } catch (Exception e) {
            LOGGER.error("查询失败" + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }

    /**
     * 加载紧急程度列表
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void queryInstancyLevelMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            List<Map<String, Object>> list = getBaseDao()
                    .queryForList("noticeMapper.queryInstancyLevelMessageInfo", params);
            output.setItems(list);
        } catch (Exception e) {
            LOGGER.error("查询失败" + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }

    /**
     * 点击公告主题请求接口
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void queryNoticeContentById(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        Map<String, Object> map = new HashMap<>();
        try {
            //查询出公告相关信息
            Map<String, Object> item = (Map<String, Object>) getBaseDao()
                    .queryForObject("noticeMapper.queryNoticeDetailMessageInfo", params);
            //查询出已经阅读的人数
            int readTotalCount = getBaseDao().getTotalCount("noticeReadMapper.queryReadTotal", params);
            //查询出未阅读人数
            int sendTotal = (int) item.get("sendTotal");
            int unReadCount = 0;
            if (sendTotal == -9999) {
                //查询出全部的人员数量
                int allPeopleTotalCount = getBaseDao().getTotalCount("noticeMapper.queryAllPeople", params);
                item.put("sendTotal", allPeopleTotalCount);
                unReadCount = allPeopleTotalCount - readTotalCount;
            } else {
                unReadCount = sendTotal - readTotalCount;
            }
            //3.判断阅读表中是否有数据,若没有则插入数据
            Map<String, Object> noticeRead = (Map<String, Object>) getBaseDao().queryForObject("noticeReadMapper.queryByNoticeIdAndCurrentUserId", params);
            if (noticeRead == null) {
                //在阅读表中插入数据
                Map<String, Object>  noticeReadMap = new HashMap<>();
                noticeReadMap.put("id", getSequence());
                noticeReadMap.put("noticeId", (Long) item.get("id"));
                noticeReadMap.put("crtId", (Long) params.get("currentUserId"));
                noticeReadMap.put("readTime", new Date());
                noticeReadMap.put("crtTime", new Date());
                getBaseDao().insert("noticeReadMapper.insertReadMeaasgeInfo", noticeReadMap);
            }
            map.put("item", item);
            map.put("readTotalCount", readTotalCount);
            map.put("unReadCount", unReadCount);
            output.setItem(map);
            if ("0".equals(output.getCode())) {
                //保存成功插入操作日志信息
                input.getParams().put("operatePage",
                        Constants.OPERATE_PAGE_CD.NOTICE_TYPE_CD); //操作页面代码
                input.getParams().put("operateType",
                        Constants.OPERATE_TYPE_CD.OPERATE_NOTICENAME_CD); //操作类型代码
                operateLogService.saveOperateLogInfo(input, output);
            }
        } catch (NzbDataException e) {
            LOGGER.error("查询失败" + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }

    /**
     * 查询我的公告(推送给我的)
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void queryMyNoticeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        //判断是否是超级管理员
        String isRoot = (String) params.get("isRoot");
        String isSee = (String) params.get("isSee");
        try {
            if ("1".equals(isRoot)) { //是超级管理员
                if (isSee.equals("0")) { //未阅读
                    int count = getBaseDao().getTotalCount("noticeMapper.unReadIsRootNoticeTotal", params);
                    if (count > 0) {
                        List<Map<String, Object>> items = getBaseDao().queryForList("noticeMapper.unReadIsRootNoticeList", params);
                        output.setItems(items);
                    }
                    output.setTotal(count);
                } else if (isSee.equals("1")) { //已阅读
                    int count = getBaseDao().getTotalCount("noticeMapper.readIsRootNoticeTotal", params);
                    if (count > 0) {
                        List<Map<String, Object>> items = getBaseDao().queryForList("noticeMapper.readIsRootNoticeList", params);
                        output.setItems(items);
                    }
                    output.setTotal(count);
                } else { //全部
                    int count = getBaseDao().getTotalCount("noticeMapper.queryAllIsRootNoticeTotal", params);
                    if (count > 0) {
                        List<Map<String, Object>> items = getBaseDao().queryForList("noticeMapper.queryAllIsRootNoticeList", params);
                        output.setItems(items);
                    }
                    output.setTotal(count);
                }
            } else { //不是超级管理员
                //判断是否已阅
                if (isSee.equals("0")) { //未阅读
                    int count = getBaseDao().getTotalCount("noticeMapper.unReadNoticeTotal", params);
                    if (count > 0) {
                        List<Map<String, Object>> items = getBaseDao().queryForList("noticeMapper.unReadNoticeList", params);
                        output.setItems(items);
                    }
                    output.setTotal(count);
                } else if (isSee.equals("1")) { //已阅读
                    int count = getBaseDao().getTotalCount("noticeMapper.readNoticeTotal", params);
                    if (count > 0) {
                        List<Map<String, Object>> items = getBaseDao().queryForList("noticeMapper.readNoticeList", params);
                        output.setItems(items);
                    }
                    output.setTotal(count);
                } else { //全部
                    int count = getBaseDao().getTotalCount("noticeMapper.queryAllNoticeTotal", params);
                    if (count > 0) {
                        List<Map<String, Object>> items = getBaseDao().queryForList("noticeMapper.queryAllNoticeList", params);
                        output.setItems(items);
                    }
                    output.setTotal(count);
                }
            }
            if ("0".equals(output.getCode())) {
                //保存成功插入操作日志信息
                input.getParams().put("operatePage",
                        Constants.OPERATE_PAGE_CD.MY_NOTICE_CD); //操作页面代码
                input.getParams().put("operateType",
                        Constants.OPERATE_TYPE_CD.OPERATE_QUERY_CD); //操作类型代码
                operateLogService.saveOperateLogInfo(input, output);
            }
        } catch (Exception e) {
            LOGGER.error("查询失败" + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }

    /**
     * 查看已阅读人信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void queryReadMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int count = getBaseDao().getTotalCount("noticeMapper.readOneNoticeTotal", params);
            if (count > 0) {
                List<Map<String, Object>> items = getBaseDao().queryForList("noticeMapper.readOneNoticeList", params);
                output.setItems(items);
            }
            output.setTotal(count);
            if ("0".equals(output.getCode())) {
                //保存成功插入操作日志信息
                input.getParams().put("operatePage",
                        Constants.OPERATE_PAGE_CD.NOTICE_TYPE_CD); //操作页面代码
                input.getParams().put("operateType",
                        Constants.OPERATE_TYPE_CD.OPERATE_QUERYREADDETAIL_CD); //操作类型代码
                operateLogService.saveOperateLogInfo(input, output);
            }
        } catch (Exception e) {
            LOGGER.error("查询失败" + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }

    /**
     * 查看未阅读人信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void queryUnReadMeaasgeInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        //1.判断该公告是全部发送还是部分发送
        Map<String, Object> params = input.getParams();
        try {
            //查询出公告相关信息
            Map<String, Object> notice = (Map<String, Object>) getBaseDao()
                    .queryForObject("noticeMapper.queryNoticeDetailMessageInfo", params);
            Integer sendTotal = (Integer) notice.get("sendTotal");
            if (sendTotal == -9999) { //若是全部发送使用用户表
                //查询未阅读人总数量
                int count = getBaseDao().getTotalCount("noticeMapper.unReadOneNoticeTotal", params);
                output.setTotal(count);
                if (count > 0) {
                    List<Map<String, Object>> items = (List<Map<String, Object>>) getBaseDao().queryForList("noticeMapper.unReadOneNoticeList", params);
                    output.setItems(items);
                }
            } else { //若是部分发送则使用权限表
                //查询未阅读人总数量
                int count = getBaseDao().getTotalCount("noticePermissionMapper.unReadOneNoticeTotal", params);
                output.setTotal(count);
                if (count > 0) {
                    List<Map<String, Object>> items = (List<Map<String, Object>>) getBaseDao().queryForList("noticePermissionMapper.unReadOneNoticeList", params);
                    output.setItems(items);
                }
            }
            if ("0".equals(output.getCode())) {
                //保存成功插入操作日志信息
                input.getParams().put("operatePage",
                        Constants.OPERATE_PAGE_CD.NOTICE_TYPE_CD); //操作页面代码
                input.getParams().put("operateType",
                        Constants.OPERATE_TYPE_CD.OPERATE_QUERYUNREADDETAIL_CD); //操作类型代码
                operateLogService.saveOperateLogInfo(input, output);
            }
        } catch (Exception e) {
            LOGGER.error("查询失败" + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }

    }

    /**
     * 查看详情
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void queryAllMeaasgeInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        List<Map<String, Object>> items = new ArrayList<>();
        //1.判断该公告是全部发送还是部分发送
        Map<String, Object> params = input.getParams();
        //查询出公告相关信息
        try {
            Map<String, Object> notice = (Map<String, Object>) getBaseDao()
                    .queryForObject("noticeMapper.queryNoticeDetailMessageInfo", params);
            Integer sendTotal = (Integer) notice.get("sendTotal");
            if (sendTotal == -9999) { //若是全部发送使用用户表
                //先查询出已阅读用户
                List<Map<String, Object>> readList = (List<Map<String, Object>>) getBaseDao().queryForList("noticePermissionMapper.readOneNoticeList",params);
                //查询出所有的用户信息数量
                int allTotal = getBaseDao().getTotalCount("noticeMapper.AllUserMeaasgeTotal", params);
                output.setTotal(allTotal);
                if (allTotal > 0) {
                    //查询出所有的用户信息
                    List<Map<String, Object>> allList = (List<Map<String, Object>>) getBaseDao().queryForList("noticeMapper.AllUserMeaasgeList",params);
                    if (readList != null && !readList.isEmpty()) {
                        for (Map<String, Object> all : allList) {
                            Long allUserId = (Long) all.get("userId");
                            for (Map<String, Object> some : readList) {
                                Long someUserId =(Long) some.get("userId");
                                if (allUserId.longValue() == someUserId.longValue()) {
                                    all.put("readTime", some.get("readTime"));
                                    all.put("remark", 1);
                                }
                            }
                        }
                    }
                    output.setItems(allList);
                }
            } else { //若是部分发送则使用权限表
                //先查询出已阅读用户
                List<Map<String, Object>> readList = (List<Map<String, Object>>) getBaseDao().queryForList("noticePermissionMapper.readOneNoticeList", params);
                //查询出该公告对应的有效权限信息数量
                int allListTotal = getBaseDao().getTotalCount("noticePermissionMapper.queryNoticePermissionTotalByNoticeId", params);
                output.setTotal(allListTotal);
                if (allListTotal > 0) {
                    //查出该公告对应的有效权限信息
                    List<Map<String, Object>> allList = (List<Map<String, Object>>) getBaseDao().queryForList("noticePermissionMapper.queryNoticePermissionByNoticeId", params);
                    if (readList != null && !readList.isEmpty()) {
                        for (Map<String, Object> all : allList) {
                            Long allUserId = (Long) all.get("userId");
                            for (Map<String, Object> some : readList) {
                                Long someUserId = (Long) some.get("userId");
                                if (allUserId.longValue() == someUserId.longValue()) {
                                    all.put("readTime", some.get("readTime"));
                                    all.put("remark", 1);
                                }
                            }
                        }
                    }
                    output.setItems(allList);
                }
            }
            if ("0".equals(output.getCode())) {
                //保存成功插入操作日志信息
                input.getParams().put("operatePage",
                        Constants.OPERATE_PAGE_CD.NOTICE_TYPE_CD); //操作页面代码
                input.getParams().put("operateType",
                        Constants.OPERATE_TYPE_CD.OPERATE_MYREPORT_QUERY_CD); //操作类型代码
                operateLogService.saveOperateLogInfo(input, output);
            }
        } catch (Exception e) {
            LOGGER.error("查询失败" + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }

    /**
     * 获取到该公告的用户权限信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void getNoticePermissionTree(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            List<Map<String, Object>> items = getBaseDao().queryForList("noticeMapper.getNoticePermissionTree", params);
            if (items != null && !items.isEmpty()) {
                output.setItems(items);
            }
        } catch (Exception e) {
            LOGGER.error(e);
            output.setCode("-1");
            output.setMsg("权限信息加载失败");
        }
    }
}
