package com.chmei.nzbdata.notice.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.notice.service.INoticePermissionService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Map;
/**
 * 公告权限接口实现类
 *
 * @author wangteng
 * @since 2019年5月13日
 */
@Service("noticePermissionService")
public class NoticePermissionServiceImpl extends BaseServiceImpl implements INoticePermissionService {

    /** LOGGER */
    private static final Logger LOGGER = Logger.getLogger(NoticePermissionServiceImpl.class);
    /**
     * 批量插入公告权限信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void insertNoticePermissionMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int count = getBaseDao().insert("noticePermissionMapper.insertNoticePermissionMessageInfo", params);
            if (count <= 0) {
                output.setCode("-1");
                output.setMsg("新增失败");
            }
        } catch (Exception e) {
            LOGGER.error("新增失败" + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }

    /**
     * 批量更新公告权限信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void updateNoticePermissiontypeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int count = getBaseDao().update("noticePermissionMapper.updateNoticePermissiontypeMessageInfo", params);
        } catch (Exception e) {
            LOGGER.error("新增失败" + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }

    /**
     * 批量删除公告分类信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void deleteNoticePermissiontypeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int count = getBaseDao().delete("noticePermissionMapper.deleteNoticePermissiontypeMessageInfo", params);
        } catch (Exception e) {
            LOGGER.error("删除失败" + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }
}
