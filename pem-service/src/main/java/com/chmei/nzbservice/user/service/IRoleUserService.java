package com.chmei.nzbservice.user.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 用户角色管理接口
 * date: 2018年8月8日 下午5:06:46  
 * @author lianziyu  
 * @version   
 * @since JDK 1.7
 */
public interface IRoleUserService {
	
	/**
	 * <p>李新杰
	 */
	void addRoleUserForUser(InputDTO input, OutputDTO output);
	
	/**
	 * <p>李新杰
	 */
	void removeRoleUserForUser(InputDTO input, OutputDTO output);
	
	/**
	 * 分页查询管理端用户角色列表
	 * @author lianziyu  
	 * @param input 
	 * @param output
	 * @throws NzbServiceException
	 */
	void queryRoleUserList(InputDTO input, OutputDTO output) throws NzbServiceException;
	
	/**
	 * 分页查询发包商用户角色列表
	 * @author lianziyu  
	 * @param input 
	 * @param output
	 * @throws NzbServiceException
	 */
	void queryRoleErUserList(InputDTO input, OutputDTO output) throws NzbServiceException;
	
	/**
	 * 不分页查询用户角色列表
	 * queryRoleUserAll:(这里用一句话描述这个方法的作用).  
	 * @author lianziyu  
	 * @param input
	 * @param output
	 * @throws NzbServiceException
	 */
	void queryRoleUserAll(InputDTO input, OutputDTO output) throws NzbServiceException;
	
	/**
	 * 修改角色的关联用户（根据角色，修改关联的用户）
	 * @author lianziyu  
	 * @param input  需传入roleId,去除的关联用户id的数组removeUserIds，以及新增的关联用户的id的数组addUserIds
	 * @param output
	 * @throws GenlCoreException
	 */
	public void updateRoleUserByRole(InputDTO input, OutputDTO output) throws NzbServiceException;
	
	/**
	 * 修改角色的关联用户（根据用户，修改关联的角色）
	 * @author lianziyu  
	 * @param input  需传入userId,去除的关联用户id的数组removeRoleIds，以及新增的关联用户的id的数组addRoleIds
	 * @param output
	 * @throws GenlCoreException
	 */
	public void updateRoleUserByUser(InputDTO input, OutputDTO output) throws NzbServiceException;
}
