package com.chmei.nzbservice.user.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 权限菜单管理接口
 * date: 2018年8月8日 下午4:57:57 
 * @author lianziyu
 * @version
 * @since JDK 1.7
 */
public interface IRightService {
	/**
	 * 查询权限详情
	 * @author lianziyu  
	 * @param input 参数需传入id
	 * @param output
	 * @throws NzbServiceException
	 */
	void queryRightDetail(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 查询权限链接是否已存在
	 * @author lianziyu  
	 * @param input 参数需传入id
	 * @param output
	 * @throws NzbServiceException
	 */
	void queryRightByUrlAddr(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 新增权限
	 * @author lianziyu  
	 * @param input
	 * @param output
	 * @throws NzbServiceException
	 */
	void saveRight(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 删除权限
	 * @author lianziyu  
	 * @param input 参数需传入id
	 * @param output
	 * @throws NzbServiceException
	 */
	void deleteRight(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 修改权限
	 * @author lianziyu  
	 * @param input 参数id为必传项
	 * @param output
	 * @throws NzbServiceException
	 */
	public void updateRight(InputDTO input, OutputDTO output) throws NzbServiceException;
	
	/**
	 * 获取权限树
	 * 不传入roleId获取的为所有权限
	 * 传入roleId则可获取所有权限，该角色拥有的权限hasState为1
	 * @author lianziyu  
	 * @param input
	 * @param output
	 * @throws NzbServiceException
	 */
	public void queryRightTree(InputDTO input, OutputDTO output) throws NzbServiceException;
	
	/**
	 * 获取所有的权限
	 * @author lianziyu  
	 * @param input
	 * @param output
	 * @throws NzbServiceException
	 */
	public void queryAllRight(InputDTO input, OutputDTO output) throws NzbServiceException;
	/**
	 * 获取部门树   
	 * @param input 入参
	 * @param output 返回结果
	 * @throws NzbServiceException 自定义异常
	 */
	public void queryUserDepartment(InputDTO input, OutputDTO output) throws NzbServiceException;
}
