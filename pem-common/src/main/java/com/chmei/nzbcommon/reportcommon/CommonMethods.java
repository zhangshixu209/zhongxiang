package com.chmei.nzbcommon.reportcommon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 报表公用方法抽取
 * @author zcf 
 * @date 
 *
 */
public class CommonMethods {

	/**
     * 解析前端参数
     * @param sMap 参数信息
     * @return 返回解析数据
     */
    public static Map<String, Object> resloveParams(Map<String, String[]> sMap) {
        Map<String, Object> params = new HashMap<String, Object>();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> containMap = new HashMap<String, Object>(); 
        for (Map.Entry<String, String[]> entry : sMap.entrySet()) {
            if ("dwreportId".equals(entry.getKey())) {
                params.put("id", entry.getValue()[0]);
            } else if ("dwreportNum".equals(entry.getKey())) {
                params.put("reportNum", entry.getValue()[0]);
            } else if ("dwreportName".equals(entry.getKey())) {
                params.put("reportName", entry.getValue()[0]);
            } else if ("searchType".equals(entry.getKey())) {
                params.put("searchType", entry.getValue()[0]);
            } else if ("sortsSearch".equals(entry.getKey())) {
            	params.put("sortsSearch", entry.getValue()[0]);
            } else {
                if (containMap.containsKey(entry.getKey())) {
                    continue;
                }
                if (entry.getValue().length > 0) {
                    if (!StringUtils.isEmpty(entry.getValue()[0])) {
                        Map<String, Object> param = new HashMap<String, Object>();
                        if (entry.getKey().indexOf("opt") > -1) {
                            String t_key = entry.getKey().substring(0, entry.getKey().length() - 3);
                            sMap.get(t_key);
                            param.put(t_key, sMap.get(t_key)[0]);
                            param.put("opt", entry.getValue()[0]);
                            containMap.put(t_key, t_key);
                            containMap.put(entry.getKey(), entry.getKey());
                        } else {
                            String t_key = entry.getKey() + "opt";
                            param.put("opt", sMap.get(t_key)[0]);
                            param.put(entry.getKey(), entry.getValue()[0]);
                            containMap.put(t_key, t_key);
                            containMap.put(entry.getKey(), entry.getKey());
                        }
                        list.add(param);
                    }
                }
            }
        }
        params.put("query", list);
        return params;
    }
}
