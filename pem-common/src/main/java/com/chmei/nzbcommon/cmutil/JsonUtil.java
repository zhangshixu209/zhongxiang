package com.chmei.nzbcommon.cmutil;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmservice.BaseConvertor;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Json格式处理类
 */
public final class JsonUtil {
	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	static {
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}
	
	private static Map<String, BaseConvertor> convertorMap = new HashMap<String, BaseConvertor>();

	/** 私有构造器 **/
	private JsonUtil() {
	}
	
	/**
	 * 将Object对象转换成Json
	 * 
	 * @param object
	 *            Object对象
	 * @return Json字符串
	 */
	public static String convertObject2Json(Object object) {
		String method = "convertObject2Json";
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			logger.error(method, "convert json error.", object.toString(), e);
		}
		return null;
	}

	/**
	 * 将Json转换成Object对象
	 * 
	 * @param json
	 *            Json字符串
	 * @param cls
	 *            转换成的对象类型
	 * @return 转换之后的对象
	 */
	public static <T> T convertJson2Object(String json, Class<T> cls) {
		String method = "convertJson2Object";
		try {
			if(json!=null&&!json.isEmpty()){
				return objectMapper.readValue(json, cls);
			}
		} catch (Exception e) {
			logger.error(method, "convert json error.", json, e);
		}
		return null;
	}

	/**
	 * 将InputDTO对象转换成Json
	 * 
	 * @param inputDTO
	 *            InputDTO对象
	 * @return Json字符串
	 */
	public static String InputDTO2Json(InputDTO inputDTO) {
		String method = "InputDTO2Json";
		try {
			return objectMapper.writeValueAsString(inputDTO);
		} catch (Exception e) {
			logger.error(method, "convert error.", e);
		}
		return null;
	}
	
	/**
	 * 将Json转换成InputDTO对象
	 * 
	 * @param json
	 *            Json字符串
	 * @return InputDTO对象
	 */
	public static InputDTO json2InputDTO(String json) {
		String method = "json2InputDTO";
		try {
			return objectMapper.readValue(json, InputDTO.class);
		} catch (Exception e) {
			logger.error(method, "convert error.", json, e);
		}
		return null;
	}

	/**
	 * 将OutputDTO转换成Json
	 * 
	 * @param outputDTO
	 *            OutputDTO对象
	 * @return Json字符串
	 */
	public static String OutputDTO2Json(OutputDTO outputDTO) {
		String method = "OutputDTO2Json";
		try {
			return objectMapper.writeValueAsString(outputDTO);
		} catch (Exception e) {
			logger.error(method, "convert error.", e);
		}
		return null;
	}
	
	/**
	 * 将Json字符串转换成OutputDTO
	 * 
	 * @param json
	 *            Json字符串
	 * @return OutputDTO对象
	 */
	public static OutputDTO json2OutputDTO(String json) {
		String method = "json2OutputDTO";
		try {
			return objectMapper.readValue(json, OutputDTO.class);
		} catch (Exception e) {
			logger.error(method, "convert error.", json, e);
		}
		return null;
	}
	
	/**
	 * 将OutputDTO对象转换成Json字符串
	 * 
	 * @param clsName
	 *            类名(含包名)
	 * @param method
	 *            方法名
	 * @param inputDTO
	 *            入参1：InputDTO对象
	 * @param outputDTO
	 *            入参2：OutputDTO对象
	 * @return Json字符串
	 * @throws Exception
	 */
	public static String OutputDTO2Json(String clsName, String method,
			InputDTO inputDTO, OutputDTO outputDTO)
			throws Exception {
		BaseConvertor convertor = getConvertor(clsName);
		if (method == null || "".equals(method)) {
			method = BaseConvertor.DEFAULT_METHOD;
		}
		Method mth = convertor.getClass().getMethod(method, InputDTO.class,
				OutputDTO.class);
		return (String) mth.invoke(convertor, inputDTO, outputDTO);
	}
	
	/**
	 * 获取转换类
	 * 
	 * @param key
	 *            类的完整声明
	 */
	private static BaseConvertor getConvertor(String key)throws Exception {
		if (!convertorMap.containsKey(key)) {
			try {
				BaseConvertor convertor = (BaseConvertor) Class.forName(key)
						.newInstance();
				convertorMap.put(key, convertor);
			} catch (Exception e) {
				logger.error("Init Class Error.>>", key);
				throw e;
			}
		}
		return convertorMap.get(key);
	}
}
