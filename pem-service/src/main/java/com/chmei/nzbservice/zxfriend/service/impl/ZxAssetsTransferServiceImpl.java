package com.chmei.nzbservice.zxfriend.service.impl;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbservice.common.exception.NzbServiceException;
import com.chmei.nzbservice.common.service.impl.BaseServiceImpl;
import com.chmei.nzbservice.zxfriend.service.IZxAssetsTransferService;
import org.springframework.stereotype.Service;

/**
 * 资产转让service接口实现
 * 
 * @author zhangshixu
 * @since 2019年11月19日 11点17分
 *
 */
@Service("zxAssetsTransferService")
public class ZxAssetsTransferServiceImpl extends BaseServiceImpl implements IZxAssetsTransferService {

	/**
	 * 资产转让列表查询
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void queryAssetsTransferList(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxAssetsTransferService");
		input.setMethod("queryAssetsTransferList");
		getNzbDataService().execute(input, output);
	}

	/**
	 * 发布资产转让信息
	 *
	 * @param input  入參
	 * @param output 返回对象
	 * @return
	 * @throws NzbServiceException 自定义异常
	 */
	@Override
	public void releaseTransferInfo(InputDTO input, OutputDTO output) throws NzbServiceException {
		input.setService("zxAssetsTransferService");
		input.setMethod("releaseTransferInfo");
		getNzbDataService().execute(input, output);
	}
}
