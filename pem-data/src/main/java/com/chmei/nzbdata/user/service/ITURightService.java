package com.chmei.nzbdata.user.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 权限菜单管理接口
 * 
 * @author lianziyu
 * @version
 * @since JDK 1.7
 */
public interface ITURightService {
	/**
	 * 查询单个实体
	 */
	void queryObject(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 根据地址查询权限
	 */
	void queryRightByUrlAddr(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 新增
	 */
	void save(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 删除
	 */
	void delete(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 修改
	 * 
	 * @author lianziyu
	 * @param input
	 * @param output
	 * @throws NzbDataException
	 */
	public void update(InputDTO input, OutputDTO output) throws NzbDataException;
	
	/**
	 * 获取权限树
	 * 不传入roleId获取的为所有权限
	 * 传入roleId则可获取所有权限，该角色拥有的权限hasState为1
	 * @author lianziyu  
	 * @param input
	 * @param output
	 * @throws NzbDataException
	 */
	public void queryRightTree(InputDTO input, OutputDTO output) throws NzbDataException;
	
	/**
	 * 获取所有权限
	 * @author lianziyu  
	 * @param input
	 * @param output
	 * @throws NzbDataException
	 */
	public void queryAllRight(InputDTO input, OutputDTO output) throws NzbDataException;
	
	public void queryUserDepartment(InputDTO input, OutputDTO output) throws NzbDataException;
}
