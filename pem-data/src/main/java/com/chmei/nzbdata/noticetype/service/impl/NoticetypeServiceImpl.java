package com.chmei.nzbdata.noticetype.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.noticetype.service.INoticetypeService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * 公告分类接口实现类
 *
 * @author wangteng
 * @since 2019年5月13日
 */
@Service("noticetypeService")
@Transactional
public class NoticetypeServiceImpl extends BaseServiceImpl implements INoticetypeService {

    /** LOGGER */
    private static final Logger LOGGER = Logger.getLogger(NoticetypeServiceImpl.class);

    /**
     * 插入公告分类信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void insertNoticetypeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            // 判断名称是否重复
            int num = getBaseDao().getTotalCount("noticetypeMapper.queryNoticetypeByName", params);
            if (num > 0) {
                output.setCode("2");
                output.setMsg("该公告分类名称已经存在,不允许重复");
                return;
            }
            //生成主键
            long id = getSequence();
            params.put("id", id);
            params.put("code", "GGFL" + Long.valueOf(id).toString());
            params.put("insertTime", new Date());
            params.put("updateTime", new Date());
            int count = getBaseDao().insert("noticetypeMapper.insertNoticetypeMessageInfo", params);
            if (count < 1) {
                output.setCode("-1");
                output.setMsg("新增失败");
            }
        } catch (Exception e) {
            LOGGER.error("新增失败" + e);
            output.setCode("1");
            output.setMsg("系统错误");
            throw e; //抛出异常
        }
    }

    /**
     * 更新公告分类信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void updateNoticetypeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        params.put("updateTime", new Date());
        try {
            // 判断名称是否重复
            int num = getBaseDao().getTotalCount("noticetypeMapper.queryNoticetypeByName", params);
            if (num > 0) {
                output.setCode("2");
                output.setMsg("该公告分类名称已经存在,不允许重复");
                return;
            }
            
            int count = getBaseDao().update("noticetypeMapper.updateNoticetypeMessageInfo", params);

        } catch (Exception e) {
            LOGGER.error("更新失败" + e);
            output.setCode("1");
            output.setMsg("系统错误");
            throw e; //抛出异常
        }
    }

    /**
     * 删除公告分类信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void deleteNoticetypeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        Long id = (Long) params.get("id");
        try {
            //判断当前公告分类是否在使用中
            List<Long> noticeTypeIds = getBaseDao().queryForList("noticetypeMapper.queryUsedNoticeTypeMessageInfo", params, Long.class);
            if (noticeTypeIds != null && !noticeTypeIds.isEmpty()) {
                if (noticeTypeIds.contains(id)) {
                    output.setCode("-2");
                    output.setMsg("正在使用中的公告分类不可删除");
                } else {
                    int count = getBaseDao().delete("noticetypeMapper.deteleNoticetypeMessageInfo", params);
                    if (count < 1) {
                        output.setCode("-1");
                        output.setMsg("删除失败");
                    }

                }
            }
        } catch (Exception e) {
            LOGGER.error("删除失败" + e);
            output.setCode("1");
            output.setMsg("系统错误");
            throw e; //抛出异常
        }
    }

    /**
     * 查询公共分类信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @Override
    public void queryNoticetypeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int count = getBaseDao().getTotalCount("noticetypeMapper.queryNoticetypeMessageInfoTotal", params);
            if (count > 0) {
                List<Map<String, Object>> list = getBaseDao()
                        .queryForList("noticetypeMapper.queryNoticetypeMessageInfoList", params);
                output.setItems(list);
            }
            output.setTotal(count);

        } catch (Exception e) {
            LOGGER.error("查询失败" + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }

    /**
     * 查看单条公共分类信息详情
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 异常信息
     */
    @SuppressWarnings("unchecked")
    @Override
    public void queryNoticrtypeDetailMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            Map<String, Object> item = (Map<String, Object>) getBaseDao()
                    .queryForObject("noticetypeMapper.queryNoticrtypeDetailMessageInfo", params);
            output.setItem(item);
        } catch (Exception e) {
            LOGGER.error("查询失败" + e.getMessage());
            output.setCode("1");
            output.setMsg("系统错误");
        }

    }

    /**
     * 查询所有的公告分类信息
     * @param input 入参
     * @param output 出参
     * @throws NzbDataException 自定义异常信息
     */
    @Override
    public void queryAllNoticetypeMessageInfo(InputDTO input, OutputDTO output) throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            List<Map<String, Object>> list = getBaseDao().queryForList("noticetypeMapper.queryAllNoticetypeMessageInfo", params);
            output.setItems(list);
        } catch (Exception e) {
            LOGGER.error("查询失败" + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }

}
