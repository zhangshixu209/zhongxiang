package com.chmei.nzbcommon.util.excel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 判断excel中整行有重复的数据
 * @author ZCF
 * @since 2018-11-26
 */
public class ExcelRepeatUtils {
    /**
     * 03Excel格式
     */
    private static final String ext03 = "xls";
    
    /**
     * 07Excel格式
     */
    private static final String ext07 = "xlsx";
    
    /**
     * 私有构造器
     */
    private ExcelRepeatUtils() {
    }
    

    /**
     * 根据list数据结构处理之后判断是否有重复数据
     * @param list 数据
     * @param headers 表头数据
     * @return -1表示正常，否则表示重复行数
     */
    public static int dealWithExcelRepeat(List<Map<String, Object>> list,List<String> headers){
    	if(list == null || list.size() == 0){
    		return -1;//没有数据不做处理
    	}
    	//过滤使用
    	Map<String,Object> map_all = new HashMap<String, Object>();
    	for (int i = 0; i < list.size(); i++) {
    		Map<String, Object> map = list.get(i);
    		String key = "";
    		for (int j = 0; j < headers.size(); j++) {
    			String head = headers.get(j);
    			Object object = map.get(head);
    			key += object;
			}
    		if(!map_all.containsKey(key)){
    			map_all.put(key, i+1);
    		}else{
    			return i+2;
    		}
		}
    	return -1;
    }
    
    
    
    
    
    
    
}
