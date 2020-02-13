package com.chmei.nzbservice.complaint.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 会员投诉service接口
 * 
 * @author zhangshixu
 * @since 2019年10月28日 16点18分
 *
 */
public interface IMemberComplaintService {
	/**
	 * 列表查询
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryMemberComplaintList(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 会员投诉详情查询
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryMemberComplaintDetail(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 修改会员投诉状态
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void updateMemberComplaintInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 新增会员投诉
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void saveMemberComplaintInfo(InputDTO input, OutputDTO output) throws NzbServiceException;

	/**
	 * 会员投诉次数
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void checkComplaintCount(InputDTO input, OutputDTO output) throws NzbServiceException;

}
