package com.chmei.nzbdata.user.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 角色员工
 * 
 * @author xzq
 * @email xiezhenqiang@qq.com
 * @date 2018-08-06 16:50:31
 */
public interface ITURoleUserService {
	
	/**
	 * 插入一条记录，给用户分配一个角色
	 * <p>李新杰
	 */
	void insertRoleUserForUser(InputDTO input, OutputDTO output);
	
	/**
	 * 删除一条记录，给用户取消一种角色
	 * <p>李新杰
	 */
	void deleteRoleUserForUser(InputDTO input, OutputDTO output);
	
	/**
	 * 分页查询管理端角色用户信息
	 */
	void queryRoleAdminList(InputDTO input, OutputDTO output) throws NzbDataException;
	
	/**
	 * 分页查询发包商角色用户信息
	 */
	void queryRoleErAdminList(InputDTO input, OutputDTO output) throws NzbDataException;
	
	/**
	 * 分页查询
	 */
	void queryAll(InputDTO input, OutputDTO output) throws NzbDataException;
	
	/**
	 * 修改角色的关联用户（根据角色，修改关联的用户）
	 * @author lianziyu  
	 * @param input  需传入roleId,去除的关联用户id的数组removeUserIds，以及新增的关联用户的id的数组addUserIds
	 * @param output
	 * @throws NzbDataException
	 */
	public void updateRoleUserByRole(InputDTO input, OutputDTO output) throws NzbDataException;
	
	/**
	 * 修改角色的关联用户（根据用户，修改关联的角色）
	 * @author lianziyu  
	 * @param input  需传入userId,去除的关联用户id的数组removeRoleIds，以及新增的关联用户的id的数组addRoleIds
	 * @param output
	 * @throws NzbDataException
	 */
	public void updateRoleUserByUser(InputDTO input, OutputDTO output) throws NzbDataException;
}
