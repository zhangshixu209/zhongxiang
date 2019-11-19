package com.chmei.nzbcommon.idgene;

import java.util.Calendar;

/**
 * <p>基于snowflake的实现
 * <p>单节点每秒最大并发数65535
 * <p>超过该数后生成的ID会重复
 * <p>造成主键冲突
 * <p>可以一个表对应一个ID生成器
 * <p>或多个表共享一个ID生成器
 * @author lixinjie
 * @since 2017-12-18
 * @since 2018-02-09
 */
public class CycleRadixIdGenerator implements IdGenerator {
	
	//共63位/20年=40位/127节点=7位/65535基数=16位
	private long baseTime = -1;//基准时间
	private long cycleNum = -1;//循环基数
	private long nodeNum = -1;//节点编号
	
	public CycleRadixIdGenerator() {
		initBaseTime();
		initNodeNum();
	}
	
	public CycleRadixIdGenerator(long baseTime, long nodeNum) {
		assert baseTime < System.currentTimeMillis();
		assert nodeNum >= 0;
		this.baseTime = baseTime;
		this.nodeNum = nodeNum;
	}
	
	public long getCycleNum() {
		if (cycleNum >= 65535) {
			cycleNum = -1;
		}
		return ++cycleNum;
	}
	
	public long getNodeNum() {
		return nodeNum;
	}
	
	/**
	 * 从基准时间到现在经过的秒数，本来应该是毫秒数，
	 * 但那样最终生成的long数字太长，在前端js里无法
	 * 正确显示，所以退化为秒
	 */
	public long getTimeNum() {
		return (System.currentTimeMillis() - baseTime) / 1000;
	}
	
	@Override
	public synchronized long nextId() {
		long id = getTimeNum();
		id = (id << 7) | getNodeNum();
		id = (id << 16) | getCycleNum();
		return id;
	}
	
	private void initBaseTime() {
		if (baseTime < 0) {
			Calendar now = Calendar.getInstance();
			//2018-01-01 00:00:00.000
			now.set(2019, 0, 1, 0, 0, 0);
			now.set(Calendar.MILLISECOND, 0);
			baseTime = now.getTimeInMillis();
		}
	}
	
	private void initNodeNum() {
		if (nodeNum < 0) {
			String nodeNumStr = System.getProperty("nodeId");
			if (nodeNumStr == null) {
				nodeNum = 0;
			} else {
				nodeNum = Long.parseLong(nodeNumStr);
			}
		}
	}
}
