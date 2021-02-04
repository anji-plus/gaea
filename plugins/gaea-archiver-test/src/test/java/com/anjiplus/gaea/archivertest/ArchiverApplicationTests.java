package com.anjiplus.gaea.archivertest;

import com.anjiplus.gaea.archiver.service.ArchiverService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
/**
 * 归档测试示例
 *
 * @author 木子李·De
 * @since 2021/2/3 14:16
 */
@SpringBootTest(classes = ArchiverApplication.class)
class ArchiverApplicationTests {

	@Autowired
	private ArchiverService archiverService;

	@Test
	void contextLoads() {
		archiverService.doArchiveTable();
		System.out.println("finish");
	}

}
