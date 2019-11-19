package com.chmei.nzbservice.dubbo.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;
import com.chmei.nzbcommon.cmservice.IControlService;
import com.chmei.nzbcommon.cmutil.JsonUtil;
import com.chmei.nzbservice.dubbo.IRemoteService;

/**
 * pem-data工程的服务消费者
 * @author lixinjie
 * @since 2018-11-28
 */
@Component
public class NzbDataRemoteServiceConsumer implements IRemoteService {
	
    private static final Logger logger = LoggerFactory.getLogger(NzbDataRemoteServiceConsumer.class);
   
    @Reference(timeout = 60000, retries = -1, group = "pem-data", check = false)
    private IControlService controlService;

    public void execute(InputDTO inputDTO, OutputDTO outputDTO) {
        long start = System.currentTimeMillis();
        logger.info("START INVOKE WEBAPP...service=" + inputDTO.getService() + "   method=" + inputDTO.getMethod());

        // 转换成Json
        logger.info("InputDTO=" + JsonUtil.convertObject2Json(inputDTO));
        try {
	        // 调用WebApp服务
	        OutputDTO obj = controlService.execute(inputDTO);
	        // 转换成
	        outputDTO.setItem(obj.getItem());
	        outputDTO.setItems(obj.getItems());
	        outputDTO.setItemList(obj.getItemList());
	        outputDTO.setSerachList(obj.getSerachList());
	        outputDTO.setBigSerachList(obj.getBigSerachList());
	        outputDTO.setJavaBean(obj.getJavaBean());
	        outputDTO.setJavaBeans(obj.getJavaBeans());
	        outputDTO.setTotal(obj.getTotal());
	        outputDTO.setData(obj.getData());
	        outputDTO.setCode(obj.getCode());
	        outputDTO.setMsg(obj.getMsg());
        } catch (Exception ex) {
        	outputDTO.setCode("");
        	outputDTO.setMsg("");
        	logger.error("", ex);
        }
        long end = System.currentTimeMillis();
        logger.info("OutputDTO=" + JsonUtil.convertObject2Json(outputDTO));
        logger.info("END INVOKE WEBAPP..." + String.valueOf(end - start) + "ms");
    }

}