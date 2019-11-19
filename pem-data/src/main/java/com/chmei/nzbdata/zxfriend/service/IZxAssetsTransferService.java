package com.chmei.nzbdata.zxfriend.service;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbdata.common.exception.NzbDataException;

/**
 * 资产转让dao接口
 * 
 * @author zhangshixu
 * @since 2019年11月19日 11点19分
 *
 */
public interface IZxAssetsTransferService {

	/**
	 * 资产转让列表查询
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
     * @return
	 */
	void queryAssetsTransferList(InputDTO input, OutputDTO output) throws NzbDataException;

	/**
	 * 发布资产转让信息
	 *
	 * @param input 入參
	 * @param output 返回对象
	 * @throws NzbDataException 自定义异常
	 * @return
	 */
	void releaseTransferInfo(InputDTO input, OutputDTO output) throws NzbDataException;
}
