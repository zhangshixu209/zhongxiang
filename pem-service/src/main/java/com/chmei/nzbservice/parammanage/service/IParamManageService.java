package com.chmei.nzbservice.parammanage.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 参数管理和字典表配置接口
 * @author 翟超锋
 * @since 2019年05月07日 15点30分
 *
 */
public interface IParamManageService {
    /**
     * 参数管理和字典表配置查询
     * @param input 入參
     * @param output 返回对象
     * @throws NzbServiceException  自定义异常
     */
    public void queryParamManageList(InputDTO input, OutputDTO output) throws NzbServiceException;
    
    /**
     * 参数管理和字典表配置新增或修改
     * @param input 入參
     * @param output 返回对象
     * @throws NzbServiceException  自定义异常
     */
    public void addEditParamManage(InputDTO input, OutputDTO output) throws NzbServiceException;
    
    /**
     * 参数管理和字典表配置删除
     * @param input 入參
     * @param output 返回对象
     * @throws NzbServiceException  自定义异常
     */
    public void delParamManage(InputDTO input, OutputDTO output) throws NzbServiceException;
}
