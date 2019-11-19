package com.chmei.nzbdata.organiza.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;
import com.chmei.nzbdata.common.service.impl.BaseServiceImpl;
import com.chmei.nzbdata.organiza.service.IOrganizaManageService;
import com.chmei.nzbcommon.orderutils.IdGeneratorUtils;

/**
 * 组织机构管理接口
 * @author zhangsx
 * @since 2019年05月10日 10点30分
 *
 */
@Service("organizaManageService")
public class OrganizaManageServiceImpl extends BaseServiceImpl implements IOrganizaManageService   {

    /** LOGGER */
    private static final Logger LOGGER = Logger.getLogger(OrganizaManageServiceImpl.class);
    
    /**
     * 组织机构管理查询
     * @param input 入參
     * @param output 返回对象
     * @throws NzbDataException  自定义异常
     */
    @Override
    public void queryOrganizaManageList(InputDTO input, OutputDTO output)
            throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int total = getBaseDao().getTotalCount("organizaManageMapper.queryOrganizaManageCount", params);
            if (total > 0) {
                List<Map<String, Object>> list = getBaseDao()
                        .queryForList("organizaManageMapper.queryOrganizaManageList", params);
                output.setItems(list);
            }
            output.setTotal(total);
        } catch (Exception e) {
            LOGGER.error("查询失败: " + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }

    /**
     * 组织机构管理新增
     * @param input 入參
     * @param output 返回对象
     * @throws NzbDataException  自定义异常
     */
    @Override
    public void saveOrganizaManageInfo(InputDTO input, OutputDTO output)
            throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            //生成主键ID
            Long id = getSequence();
            input.getParams().put("id", id);
            input.getParams().put("status", "1");//有效
            input.getParams().put("organizaId", IdGeneratorUtils.nextId("JG"));
            Integer count = getBaseDao().insert("organizaManageMapper.saveOrganizaManageInfo", params);
            if (count < 1) {
                output.setCode("-1");
                output.setMsg("新增失败");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("orgId", params.get("id"));
            map.put("organizaId", params.get("organizaId"));
            output.setItem(map);
        } catch (Exception e) {
            LOGGER.error("新增失败: " + e);
        }
    }
    
    /**
     * 组织机构管理查询详细信息
     * @param input 入參
     * @param output 返回对象
     * @throws NzbDataException  自定义异常
     */
    @SuppressWarnings("unchecked")
    @Override
    public void queryOrganizaManageDetail(InputDTO input, OutputDTO output)
            throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            Map<String, Object> item = (Map<String, Object>) getBaseDao()
                    .queryForObject("organizaManageMapper.queryOrganizaManageDetail", params);
            output.setItem(item);
        } catch (Exception e) {
            LOGGER.error("查询失败: " + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }

    /**
     * 组织机构管理编辑
     * @param input 入參
     * @param output 返回对象
     * @throws NzbDataException  自定义异常
     */
    @Override
    public void updateOrganizaManageInfo(InputDTO input, OutputDTO output)
            throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            Integer count = getBaseDao().insert("organizaManageMapper.updateOrganizaManageInfo", params);
            if (count < 1) {
                output.setCode("-1");
                output.setMsg("更新失败");
            }
        } catch (Exception e) {
            LOGGER.error("更新失败: " + e);
        }
    }

    /**
     * 组织机构管理删除
     * @param input 入參
     * @param output 返回对象
     * @throws NzbDataException  自定义异常
     */
    @Override
    public void delOrganizaManageInfo(InputDTO input, OutputDTO output)
            throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            Integer count = getBaseDao().insert("organizaManageMapper.delOrganizaManageInfo", params);
            if (count < 1) {
                output.setCode("-1");
                output.setMsg("删除失败");
            }
        } catch (Exception e) {
            LOGGER.error("删除失败: " + e);
        }
    }

    /**
     * 查询父机构名称
     * @param input 入參
     * @param output 返回对象
     * @throws NzbDataException  自定义异常
     */
    @Override
    public void queryParentOrganizaList(InputDTO input, OutputDTO output)
            throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            List<Map<String, Object>> list = getBaseDao()
                    .queryForList("organizaManageMapper.queryParentOrganizaList", params);
            output.setItems(list);
        } catch (Exception e) {
            LOGGER.error("查询失败: " + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }

    /**
     * 校验父机构是否被使用
     * @param input 入參
     * @param output 返回对象
     * @throws NzbDataException  自定义异常
     */
    @Override
    public void checkParentOrganiza(InputDTO input, OutputDTO output)
            throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            List<Map<String, Object>> list = getBaseDao()
                    .queryForList("organizaManageMapper.checkParentOrganiza", params);
            output.setItems(list);
        } catch (Exception e) {
            LOGGER.error("查询失败: " + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }

    /**
     * 校验机构是否关联了用户
     * @param input 入參
     * @param output 返回对象
     * @throws NzbDataException  自定义异常
     */
    @Override
    public void checkOrganizaUserDept(InputDTO input, OutputDTO output)
            throws NzbDataException {
        Map<String, Object> params = input.getParams();
        try {
            int total = getBaseDao().getTotalCount("organizaManageMapper.checkOrganizaUserDept", params);
            output.setTotal(total);
        } catch (Exception e) {
            LOGGER.error("查询失败: " + e);
            output.setCode("1");
            output.setMsg("系统错误");
        }
    }
}
