package org.eop.common.idgene;

import com.chmei.nzbcommon.idgene.CycleRadixIdGenerator;
import com.chmei.nzbcommon.idgene.IdGenerator;

/**
 * @author lixinjie
 * @since 2018-07-19
 */
public class IdGeneratorTest {

	public static void main(String[] args) throws Exception {
		IdGenerator idGenerator = new CycleRadixIdGenerator();
		//这样测试肯定会有重复Id，for循环执行的很快的
		for (int i = 0; i < 1; i++) {
			System.out.println(idGenerator.nextId());
			//System.out.println(idGenerator.nextId() % 65536);
		}
	}

}
