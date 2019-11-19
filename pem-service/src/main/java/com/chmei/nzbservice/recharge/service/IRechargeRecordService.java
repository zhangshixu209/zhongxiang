package com.chmei.nzbservice.recharge.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 充值管理service接口
 * 
 * @author zhangshixu
 * @since 2019年10月29日 17点46分
 *
 */
public interface IRechargeRecordService {

	/**
	 * 初始化加载充值记录查询列表
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 */
	void queryRechargeRecordList(InputDTO input, OutputDTO output) throws NzbServiceException;

}
