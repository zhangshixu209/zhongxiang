package com.chmei.nzbcommon.cmservice;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;

/**
 * 统一调用接口。
 */
public interface IControlService {

	
	/**
	 * 接口定义
	 */
	OutputDTO execute(InputDTO inputDTO);
}
