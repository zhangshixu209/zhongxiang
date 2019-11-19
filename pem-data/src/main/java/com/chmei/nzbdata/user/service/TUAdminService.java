package com.chmei.nzbdata.user.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 管理员信息表
 * 
 * @author xzq
 * @email xiezhenqiang@qq.com
 * @date 2018-08-06 16:50:31
 */
public interface TUAdminService {
    /**
     * 查询单个实体
     * 
     * @param input  传入的参数
     * @param output 输出的数据
     * @throws NzbDataException 异常处理
     */
    void queryObject(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 分页查询
     * 
     * @param input  传入的参数
     * @param output 输出的数据
     * @throws NzbDataException 异常处理
     */
    void queryList(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 分页查询,传入roleId返回包括是否拥有某角色（hasState:1表示拥有该角色，0表示没有该角色）
     * 
     * @param input  传入的参数
     * @param output 输出的数据
     * @throws NzbDataException 异常处理
     */
    void queryListWithRole(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 根据手机号查询管理员
     * 
     * @param input  传入的参数
     * @param output 输出的数据
     * @throws NzbDataException 异常处理
     */
    void queryAdminByLoginId(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 新增
     * 
     * @param input  传入的参数
     * @param output 输出的数据
     * @throws NzbDataException 异常处理
     */
    void save(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 修改
     * 
     * @param input  传入的参数
     * @param output 输出的数据
     * @throws NzbDataException 异常处理
     */
    void update(InputDTO input, OutputDTO output) throws NzbDataException;

    
    /**
     * 根据用户id删除用户
     * 
     * @param input  传入的参数
     * @param output 输出的数据
     * @throws NzbDataException 异常处理
     */
    void delAdminDetail(InputDTO input, OutputDTO output) throws NzbDataException;
    
    /**
     * 根据用户id禁用账号和启用账号
     * 
     * @param input  传入的参数
     * @param output 输出的数据
     * @throws NzbDataException 异常处理
     */
    void enableORdisable(InputDTO input, OutputDTO output) throws NzbDataException;
    
    /**
     * 根据手机号查询管理员信息
     * 
     * @param input  传入的参数
     * @param output 输出的数据
     * @throws NzbDataException 异常处理
     * @user songyw
     * @date 2019年3月15日
     */
    void queryAdminByMobile(InputDTO input, OutputDTO output) throws NzbDataException;

    /**
     * 修改密码
     * 
     * @param inputDTO  参数：手机号，新密码
     * @param outputDTO 返回结果
     * @throws NzbDataException 异常处理
     * @user songyw
     * @date 2019年3月15日
     */
    void updateAdminPwd(InputDTO inputDTO, OutputDTO outputDTO) throws NzbDataException;
    
    
    /**
     * 获取部门人员树状
     * @param inputDTO  参数：手机号，新密码
     * @param outputDTO 返回结果
     * @throws NzbDataException 异常处理
     * @user songyw
     * @date 2019年3月15日
     */
    public void getDeepartmentUserTree(InputDTO input, OutputDTO output) throws NzbDataException;
    /**
     * 报表配置人员权限的时候获取部门和人员树状，需要过滤没有分类权限人员 
     * @param inputDTO  参数：手机号，新密码
     * @param outputDTO 返回结果
     * @throws NzbDataException 异常处理
     * @user songyw
     * @date 2019年3月15日
     */
    public void getReportUserTree(InputDTO input, OutputDTO output) throws NzbDataException;
    
    /**
     * 批量导入用户信息
     * @param input 入參
     * @param output 返回对象
     * @throws NzbDataException  自定义异常
     */
    public void importExcelUserData(InputDTO input, OutputDTO output) throws NzbDataException;
    
    /**
     * 获取所有用户及部门权限信息
     * @param input 入參
     * @param output 返回对象
     * @throws NzbDataException  自定义异常
     */
    public void getDataTableUserTree(InputDTO input, OutputDTO output) throws NzbDataException;
}
