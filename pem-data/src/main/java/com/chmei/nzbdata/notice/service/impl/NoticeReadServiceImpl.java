package com.chmei.nzbdata.notice.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.notice.service.INoticeReadService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/**
 * 公告阅读信息接口实现类
 *
 * @author wangteng
 * @since 2019年5月13日
 */
@Service("noticeReadService")
public class NoticeReadServiceImpl extends BaseServiceImpl implements INoticeReadService {

    /** LOGGER */
    private static final Logger LOGGER = Logger.getLogger(NoticeReadServiceImpl.class);

    /**
     * 插入公告阅读信息信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void insertNoticeReadMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int count = getBaseDao().insert("noticeReadMapper.insertNoticeReadMessageInfo", params);
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
     * 查询公告阅读信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void queryNoticeReadMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int count = getBaseDao().getTotalCount("noticeReadMapper.queryNoticeReadMessageInfoTotal", params);
            if (count > 0) {
                List<Map<String, Object>> list = getBaseDao()
                        .queryForList("noticeReadMapper.queryNoticeReadMessageInfoList", params);
                output.setItems(list);
            }
            output.setTotal(count);
        } catch (Exception e) {
            LOGGER.error("查询失败" + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }
}
