package com.chmei.nzbdata.complaint.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 会员投诉dao接口
 * 
 * @author zhangshixu
 * @since 2019年05月10日 15点30分
 *
 */
public interface IMemberComplaintService {
	/**
	 * 列表查询
	 * 
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void queryMemberComplaintList(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 会员投诉详情查询
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void queryMemberComplaintDetail(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 修改会员投诉状态
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void updateMemberComplaintInfo(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 新增会员投诉
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 */
	void saveMemberComplaintInfo(InputDTO input, OutputDTO output) throws NzbDataException;

}
