package com.chmei.nzbcommon.ertask.serial;

import org.springframework.beans.factory.annotation.Autowired;

import com.chmei.nzbcommon.redis.ICacheService;

/**
 * <p>基于redis的实现
 * @author lixinjie
 * @since 2019-02-18
 */
public class RedisErTaskcodeGenerator implements ErTaskcodeGenerator {

	@Autowired
	private ICacheService cacheService;
	
	private String name;
	private long initNo;
	private int step;
	private long maxNo;
	
	public RedisErTaskcodeGenerator(String name, long initNo, int step, long maxNo) {
		this.name = name;
		this.initNo = initNo;
		this.step = step;
		this.maxNo = maxNo;
	}
	
	//初始化方法
	//在bean的依赖被装配后执行
	public void init() {
		if (!cacheService.hexists(getName(), "flagField")) {
			cacheService.hsetnx(getName(), "flagField", "0");
		}
	}
	
	@Override
	public Long nextNo(String platformId) {
		Long no = cacheService.hincrBy(getName(), platformId, getStep());
		if (no > getMaxNo()) {
			return no & getMaxNo();
		}
		return no;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public long getInitNo() {
		return initNo;
	}

	@Override
	public int getStep() {
		return step;
	}

	@Override
	public long getMaxNo() {
		return maxNo;
	}

}
