package com.chmei.nzbmanage.common.upload;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储服务实例工厂
 * <p>根据用户配置获取指定的存储实例
 *
 * @author 
 */
@Service
public class StorageFactory implements InitializingBean, ApplicationContextAware {

    // 存储实现类集合
    private static Map<String, Storage> storageImplMap = new HashMap<>();

    private ApplicationContext applicationContext;


    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, Storage> beanMap = applicationContext.getBeansOfType(Storage.class);
        for (Storage serviceImpl : beanMap.values()) {
            storageImplMap.put(serviceImpl.getClass().getName(), serviceImpl);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 获取指定的存储实例
     * @param storageImpl 存储实现类类名
     * @return 存储实例
     */
    public static Storage getStorage(String storageImpl) {
        return storageImplMap.get(storageImpl);
    }

}
