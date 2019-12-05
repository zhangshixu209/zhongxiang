package com.chmei.nzbservice.complaint.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 群投诉dao接口
 * 
 * @author zhangshixu
 * @since 2019年12月05日 13点53分
 *
 */
public interface IGroupComplaintService {
	/**
	 * 列表查询
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryGroupComplaintList(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 群投诉详情查询
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryGroupComplaintDetail(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 修改群投诉状态
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void updateGroupComplaintInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 新增群投诉
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void saveGroupComplaintInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

}
