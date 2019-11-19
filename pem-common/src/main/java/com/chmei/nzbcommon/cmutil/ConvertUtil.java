package com.chmei.nzbcommon.cmutil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 
 */
public final class ConvertUtil {
	private static Logger logger = LoggerFactory.getLogger(ConvertUtil.class);
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final char[] HEXCHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/** 私有构造器 **/
	private ConvertUtil() {
	}
	
	/**
	 * 将sql对象转换成java对象
	 * 
	 * @param list 
	 * @return
	 */
	public static List<Map<String, Object>> convertSqlMap2JavaMap(
			List<Map<String, Object>> list) {
		List<Map<String, Object>> beans = new ArrayList<Map<String, Object>>();
		try {
			for (Map<String, Object> map : list) {
				Map<String, Object> bean = new HashMap<String, Object>();
				Iterator<?> iter = map.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					Object key = entry.getKey();
					Object val = entry.getValue();
					if (val == null) {
						bean.put(key.toString(), "");
						continue;
					} else {
						if (val instanceof Date) {
							val = dateFormat.format(val);
							bean.put(key.toString(), val.toString());
							continue;
						} else if (val instanceof Clob) {
							try {
								val = clobToString((Clob) val);// 自己处理
								bean.put(key.toString(), val.toString());
								continue;
							} catch (Exception e) {
								logger.error("convertSqlMap2JavaMap", "clobToString", e);
							}
						} else if (val instanceof Blob) {
							try {
								val = blobToString((Blob) val);// 自己处理
								bean.put(key.toString(), val.toString());
								continue;
							} catch (Exception e) {
								logger.error("convertSqlMap2JavaMap",  "blobToString", e);
							}
						} else if (val.getClass() == byte[].class) {
							try {
								val = new String((byte[]) val, "UTF-8");
								bean.put(key.toString(), val.toString());
								continue;
							} catch (UnsupportedEncodingException e) {
								logger.error("convertSqlMap2JavaMap",  "byte[]ToString", e);
							}
						} else if (val instanceof Byte) {
							val = val.toString();
							bean.put(key.toString(), val.toString());
							continue;
						} else {// double int float boolean long char[] char
								// BigDecimal
							val = String.valueOf(val);
							bean.put(key.toString(), val.toString());
							continue;
						}
					}
				}
				beans.add(bean);
			}
		} catch (Exception e) {
			logger.error("convertSqlMap2JavaMap", "", e);
		}
		return beans;
	}

	/**
	 * 将Blob 转换成 String
	 * 
	 * @param val
	 * @return String
	 */

	public static String blobToString(Blob blob) throws Exception{
		byte[] returnValue = blob.getBytes(1L, (int) blob.length());
		return bytes2String(returnValue);
	}

	/**
	 * Clob数据转为String
	 * 
	 * @param clob
	 * @return
	 */
	public static String clobToString(Clob val) throws Exception {
		if (null == val) {
			return null;
		}
		Reader reader = null;
		StringBuffer buf = new StringBuffer();
		BufferedReader bfReader = null;
		try {
			reader = val.getCharacterStream();
			// 得到流
			bfReader = new BufferedReader(reader);
			String s = bfReader.readLine();
			while (null != s) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成String
				buf.append(s);
				s = bfReader.readLine();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (null != reader) {
					reader.close();
				}
				if (null != bfReader) {
					bfReader.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return buf.toString();
	}

	/**
	 * convert byte[] to String
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytes2String(byte[] bytes) {
		StringBuilder builder = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			builder.append(HEXCHAR[(bytes[i] & 0xf0) >>> 4]);
			builder.append(HEXCHAR[bytes[i] & 0x0f]);
		}
		return builder.toString();
	}

	/**
	 * convert String to byte[]
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] string2Bytes(String str) {
		byte[] bytes = new byte[str.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(str.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}
		
	/**
	 * Convert InputStream to String
	 * 
	 * @param str
	 * @return
	 */
	public static InputStream string2InputStream(String str) {
		try {
			return new ByteArrayInputStream(string2Bytes(str));
		} catch (Exception e) {
			logger.error("string2InputStream", "", e);
			e.printStackTrace();
		}
		return null;
	}
		
	public static String inputStream2String(InputStream inputStream) throws Exception{
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream(); 
		byte[] buff = new byte[100]; //buff用于存放循环读取的临时数据 
		int rc = 0; 
		while ((rc = inputStream.read(buff, 0, 100)) > 0) { 
		swapStream.write(buff, 0, rc); 
		} 
		byte[] in_b = swapStream.toByteArray(); //in_b为转换之后的结果
		return bytes2String(in_b);
	}
	
	/** 通过属性名获取get方法名 **/
	public static String getMethodByField(String field) {
		if (field == null) {
			return null;
		}
		return "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
	}
	
	
	public static Map<String, String> convertObjMap2StringMap(Map<String, Object> objMap) throws Exception {
		if (objMap == null) {
			return null;
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		for (Map.Entry<String, Object> entry : objMap.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			String strValue = null;
			if (value != null) {
				strValue = convertObject2Json(value);
			}
			resultMap.put(key, strValue);
		 }
		return resultMap;
	}
	
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * 将Object对象转换成Json
	 * 
	 * @param object
	 *            Object对象
	 * @return Json字符串
	 */
	private static String convertObject2Json(Object object) {
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
	private static <T> T convertJson2Object(String json, Class<T> cls) {
		String method = "convertJson2Object";
		try {
			return objectMapper.readValue(json, cls);
		} catch (Exception e) {
			logger.error(method, "convert json error.", json, e);
		}
		return null;
	}
}
