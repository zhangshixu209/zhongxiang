package com.chmei.nzbcommon.cmservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmutil.JsonUtil;

/**
 * 转换基类
 */
public class BaseConvertor {
	public static final String DEFAULT_METHOD = "convertor";
	private Logger logger = LoggerFactory.getLogger(BaseConvertor.class);
	/**
	 *  默认方法
	 */
	public String convertor(InputDTO inputDTO, OutputDTO outputDTO) {
		logger.info(DEFAULT_METHOD,inputDTO.toString()+","+outputDTO.toString(),"Start");
		String json = JsonUtil.OutputDTO2Json(outputDTO);
		logger.info("result", "Json", json);
		return json;
	}
}
