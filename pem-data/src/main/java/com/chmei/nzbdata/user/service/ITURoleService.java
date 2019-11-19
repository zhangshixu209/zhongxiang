package com.chmei.nzbdata.user.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 角色
 * 
 * @author xzq
 * @email xiezhenqiang@qq.com
 * @date 2018-08-06 16:50:31
 */
public interface ITURoleService {
	
	/**
	 * 查询所有角色，且包括每个角色当前用户是否具有，用于给用户分配角色
	 * <p>李新杰
	 */
	void selectRolesForUser(InputDTO input, OutputDTO output);
	
	/**
	 * 查询单个实体
	 */
	void queryObject(InputDTO input, OutputDTO output) throws NzbDataException;
	/**
	 * 分页查询
	 */
	void queryList(InputDTO input, OutputDTO output) throws NzbDataException;
	/**
	 * 不分页查询
	 */
	void queryAllRole(InputDTO input, OutputDTO output) throws NzbDataException;
	/**
	 * 查询角色，包含管理员是否拥有该角色
	 */
	void queryRoleListWithHasAdmin(InputDTO input, OutputDTO output) throws NzbDataException;
	/**
	 * 新增
	 */
	void save(InputDTO input, OutputDTO output) throws NzbDataException;
	/**
	 * 删除
	 */
	void delete(InputDTO input, OutputDTO output) throws NzbDataException;
	/**
	 * 批量删除
	 * @author lianziyu  
	 * @param input
	 * @param output
	 * @throws NzbDataException
	 */
	void deleteBatch(InputDTO input, OutputDTO output) throws NzbDataException;
	/**
	 * 修改
	 * @author lianziyu  
	 * @param input
	 * @param output
	 * @throws NzbDataException
	 */
	public void update(InputDTO input, OutputDTO output) throws NzbDataException;
	
}
