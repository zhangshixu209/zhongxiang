package com.chmei.nzbdata.autoconfigure.ertaskcode.gene;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lixinjie
 * @since 2018-12-05
 */
@ConfigurationProperties(prefix = "ertaskcodegenerator")
public class ErTaskCodeGeneratorProperties {

	private String name;
	private Long initNo;
	private Integer step;
	private Long maxNo;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getInitNo() {
		return initNo;
	}
	public void setInitNo(Long initNo) {
		this.initNo = initNo;
	}
	public Integer getStep() {
		return step;
	}
	public void setStep(Integer step) {
		this.step = step;
	}
	public Long getMaxNo() {
		return maxNo;
	}
	public void setMaxNo(Long maxNo) {
		this.maxNo = maxNo;
	}
	
}
