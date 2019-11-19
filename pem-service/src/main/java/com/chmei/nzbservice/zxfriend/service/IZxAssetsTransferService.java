package com.chmei.nzbservice.zxfriend.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;

/**
 * 资产转让service接口
 * 
 * @author zhangshixu
 * @since 2019年11月19日 11点14分
 *
 */
public interface IZxAssetsTransferService {

	/**
	 * 资产转让列表查询
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbServiceException 自定义异常
	 * @return
	 */
	void queryAssetsTransferList(InputDTO input, OutputDTO output) throws NzbServiceException;

}
