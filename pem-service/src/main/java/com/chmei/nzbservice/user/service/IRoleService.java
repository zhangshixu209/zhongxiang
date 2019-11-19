package com.chmei.nzbservice.user.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 角色管理接口
 * date: 2018年8月8日 下午5:02:07  
 * @author lianziyu  
 * @version   
 * @since JDK 1.7
 */
public interface IRoleService {
	
	/**
	 * <p>李新杰
	 */
	void queryRolesForUser(InputDTO input, OutputDTO output);
	
	/**
	 * 查询角色详情
	 * @author lianziyu  
	 * @param input 参数需传入id
	 * @param output
	 * @throws NzbServiceException
	 */
	void queryRoleDetail(InputDTO input, OutputDTO output) throws NzbServiceException;
	

	/**
	 * 分页查询角色
	 * @author lianziyu  
	 * @param input 
	 * @param output
	 * @throws NzbServiceException
	 */
	void queryRoleList(InputDTO input, OutputDTO output) throws NzbServiceException;
	
	/**
	 * 不分页查询所哟角色
	 * @author lianziyu  
	 * @param input 
	 * @param output
	 * @throws NzbServiceException
	 */
	void queryAllRole(InputDTO input, OutputDTO output) throws NzbServiceException;
	/**
	 * 查询角色，包含管理员是否拥有该角色
	 * @author lianziyu  
	 * @param input 
	 * @param output
	 * @throws NzbServiceException
	 */
	void queryRoleListWithHasAdmin(InputDTO input, OutputDTO output) throws NzbServiceException;
	
	/**
	 * 新增角色
	 * @author lianziyu  
	 * @param input
	 * @param output
	 * @throws NzbServiceException
	 */
	void saveRole(InputDTO input, OutputDTO output) throws NzbServiceException;
	
	/**
	 * 删除角色
	 * @author lianziyu  
	 * @param input 参数需传入id
	 * @param output
	 * @throws NzbServiceException
	 */
	void deleteRole(InputDTO input, OutputDTO output) throws NzbServiceException;
	
	/**
	 * 修改角色
	 * @author lianziyu  
	 * @param input 参数需传入id
	 * @param output
	 * @throws NzbServiceException
	 */
	public void updateRole(InputDTO input, OutputDTO output) throws NzbServiceException;
	
	/**
	 * 批量删除角色
	 * @author lianziyu  
	 * @param input 必传参数ids,多个id,以逗号分割
	 * @param output
	 * @throws NzbServiceException
	 */
	void deleteRoleBatch(InputDTO input, OutputDTO output) throws NzbServiceException;
}
