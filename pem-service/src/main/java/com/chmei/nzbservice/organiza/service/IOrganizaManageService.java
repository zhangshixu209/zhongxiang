package com.chmei.nzbservice.organiza.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 组织机构管理接口
 * @author zhangsx
 * @since 2019年05月10日 10点30分
 *
 */
public interface IOrganizaManageService {
    
    /**
     * 组织机构管理查询
     * @param input 入參
     * @param output 返回对象
     * @throws NzbServiceException  自定义异常
     */
    void queryOrganizaManageList(InputDTO input, OutputDTO output) throws NzbServiceException;
    
    /**
     * 组织机构管理新增
     * @param input 入參
     * @param output 返回对象
     * @throws NzbServiceException  自定义异常
     */
    void saveOrganizaManageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;
    
    /**
     * 组织机构管理查询详细信息
     * @param input 入參
     * @param output 返回对象
     * @throws NzbServiceException  自定义异常
     */
    void queryOrganizaManageDetail(InputDTO input, OutputDTO output) throws NzbServiceException;
    
    /**
     * 组织机构管理编辑
     * @param input 入參
     * @param output 返回对象
     * @throws NzbServiceException  自定义异常
     */
    void updateOrganizaManageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;
    
    /**
     * 组织机构管理删除
     * @param input 入參
     * @param output 返回对象
     * @throws NzbServiceException  自定义异常
     */
    void delOrganizaManageInfo(InputDTO input, OutputDTO output) throws NzbServiceException;
    
    /**
     * 查询父机构名称
     * @param input 入參
     * @param output 返回对象
     * @throws NzbServiceException  自定义异常
     */
    void queryParentOrganizaList(InputDTO input, OutputDTO output) throws NzbServiceException;
    
    /**
     * 校验父机构是否被使用
     * @param input 入參
     * @param output 返回对象
     * @throws NzbServiceException  自定义异常
     */
    void checkParentOrganiza(InputDTO input, OutputDTO output) throws NzbServiceException;
    
   /**
    * 校验机构是否关联了用户
    * @param input 入參
    * @param output 返回对象
    * @throws NzbServiceException  自定义异常
    */
   void checkOrganizaUserDept(InputDTO input, OutputDTO output) throws NzbServiceException;
}
