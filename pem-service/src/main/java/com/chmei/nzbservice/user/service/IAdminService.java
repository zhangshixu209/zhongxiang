package com.chmei.nzbservice.user.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 管理员服务接口  
 * Date:     2018年8月13日 上午10:54:17 
 * @author   lianziyu  
 * @version    
 * @since    JDK 1.7  
 */
public interface IAdminService {
	/**
	 * 查询管理员详情
	 * @author lianziyu  
	 * @param input 参数需传入id
	 * @param output
	 * @throws NzbServiceException
	 */
	void queryAdminDetail(InputDTO input, OutputDTO output) throws NzbServiceException;
	

	/**
	 * 分页查询管理员
	 * @author lianziyu  
	 * @param input 
	 * @param output
	 * @throws NzbServiceException
	 */
	void queryAdminList(InputDTO input, OutputDTO output) throws NzbServiceException;
	/**
	 * 根据手机号查询管理员
	 * @author lianziyu  
	 * @param input
	 * @param output
	 * @throws NzbServiceException
	 */
	void queryAdminByLoginId(InputDTO input, OutputDTO output) throws NzbServiceException;
	/**
	 * 分页查询管理员
	 * 传入roleId时返回管理员列表包含是否包含该角色（hasState为1表示有，为0表示没有）
	 * @author lianziyu  
	 * @param input 
	 * @param output
	 * @throws NzbServiceException
	 */
	void queryAdminListByHasRoleState(InputDTO input, OutputDTO output) throws NzbServiceException;
	/**
	 * 新增管理员
	 * @author lianziyu  
	 * @param input
	 * @param output
	 * @throws NzbServiceException
	 */
	void saveAdmin(InputDTO input, OutputDTO output) throws NzbServiceException;
	
	/**
	 * 修改管理员
	 * @author lianziyu  
	 * @param input 参数需传入id
	 * @param output
	 * @throws NzbServiceException
	 */
	public void updateAdmin(InputDTO input, OutputDTO output) throws NzbServiceException;
	
	/**
	 * 根据id删除用户
	 * @author lianziyu  
	 * @param input 参数需传入id
	 * @param output
	 * @throws NzbServiceException
	 */
	public void delAdminDetail(InputDTO input, OutputDTO output) throws NzbServiceException; 
	
	/**
	 * 根据id启用禁用账号
	 * @author lianziyu  
	 * @param input 参数需传入id
	 * @param output
	 * @throws NzbServiceException
	 */
	public void enableORdisable(InputDTO input, OutputDTO output) throws NzbServiceException; 
	
    /**
     * 根据手机号查询管理员信息
     * 
     * @param input  参数:手机号
     * @param output 输出数据
     * @throws NzbServiceException
     * @user songyw
     * @date 2019年3月15日
     */
    void queryAdminByMobile(InputDTO input, OutputDTO output) throws NzbServiceException;

    /**
     * 修改密码
     * 
     * @param inputDTO  参数：手机号，新密码
     * @param outputDTO
     * @throws NzbServiceException
     * @user songyw
     * @date 2019年3月15日
     */
    void updateAdminPwd(InputDTO inputDTO, OutputDTO outputDTO) throws NzbServiceException;
    
    /**
     * 获取部门人员树状
     * @param inputDTO  参数
     * @param outputDTO
     * @throws NzbServiceException
     * @user songyw
     * @date 2019年3月15日
     */
    void getDeepartmentUserTree(InputDTO inputDTO, OutputDTO outputDTO) throws NzbServiceException;
    /**
     * 报表配置人员权限的时候获取部门和人员树状，需要过滤没有分类权限人员 
     * @param inputDTO  参数
     * @param outputDTO
     * @throws NzbServiceException
     * @user songyw
     * @date 2019年3月15日
     */
    void getReportUserTree(InputDTO inputDTO, OutputDTO outputDTO) throws NzbServiceException;
    
    /**
     * 批量导入用户数据
     * @param input 入参
     * @param output 返回对象
     * @throws NzbServiceException 自定义异常
     */
    void importExcelUserData(InputDTO inputDTO, OutputDTO outputDTO) throws NzbServiceException;
    
    /**
     * 获取所有用户及部门权限信息
     * @param input 入参
     * @param output 返回对象
     * @throws NzbServiceException 自定义异常
     */
    void getDataTableUserTree(InputDTO inputDTO, OutputDTO outputDTO) throws NzbServiceException;
}
  
