package com.chmei.nzbdata.autoconfigure.ertaskcode.gene;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chmei.nzbcommon.ertask.serial.ErTaskcodeGenerator;
import com.chmei.nzbcommon.ertask.serial.RedisErTaskcodeGenerator;



/**
 * @author lixinjie
 * @since 2018-12-05
 */
@Configuration
@EnableConfigurationProperties(ErTaskCodeGeneratorProperties.class)
public class ErTaskCodeGeneratorAutoConfiguration {

	private ErTaskCodeGeneratorProperties erTaskcodeGeneratorProperties;
	
	public ErTaskCodeGeneratorAutoConfiguration(ErTaskCodeGeneratorProperties erTaskcodeGeneratorProperties) {
		this.erTaskcodeGeneratorProperties = erTaskcodeGeneratorProperties;
	}
	
	@Bean(initMethod = "init")
	public ErTaskcodeGenerator erTaskcodeGenerator() {
		return new RedisErTaskcodeGenerator(erTaskcodeGeneratorProperties.getName(), erTaskcodeGeneratorProperties.getInitNo(),
				erTaskcodeGeneratorProperties.getStep(), erTaskcodeGeneratorProperties.getMaxNo());
	}
}
