package com.chmei.nzbcommon.property;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 处理bean的属性工具类
 * @author lixinjie
 * @since 2019-04-10
 */
public class PropertyUtils {

    /**
     * 将bean转化为Map，包含null值
     * @param bean bean
     * @return map
     */
    public static Map<String, Object> convertBeanToMap(Object bean) {
        return convertBeanToMap(bean, true);
    }
    
    /**
     * 将bean转化为Map
     * @param bean bean
     * @param includeNull true则包含null值，false则不包含null值
     * @return map
     */
    public static Map<String, Object> convertBeanToMap(Object bean, boolean includeNull) {
        try {
            Map<String, Object> map = org.apache.commons.beanutils.PropertyUtils.describe(bean);
            //getClass()
            map.remove("class");
            if (!includeNull) {
                Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().getValue() == null) {
                        iterator.remove();
                    }
                }
            }
            return map;
        } catch (Exception e) {
            
        }
        return null;
    }
    
    /**
     * 将bean里面指定的属性转化为Map，包含null值
     * @param bean bean
     * @param properties 要转化的属性
     * @return map
     */
    public static Map<String, Object> convertBeanToMap(Object bean, String[] properties) {
        return convertBeanToMap(bean, properties, true);
    }
    
    /**
     * 将bean里面指定的属性转化为Map
     * @param bean bean
     * @param properties 要转化的属性
     * @param includeNull true则包含null值，false则不包含null值
     * @return map
     */
    public static Map<String, Object> convertBeanToMap(Object bean, String[] properties, boolean includeNull) {
        try {
            Map<String, Object> map = new HashMap<>();
            for (String property : properties) {
                Object value = org.apache.commons.beanutils.PropertyUtils.getProperty(bean, property);
                if (includeNull || value != null) {
                    map.put(property, value);
                }
            }
            return map;
        } catch (Exception e) {
            
        }
        return null;
    }
    
}
