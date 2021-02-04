package com.anjiplus.gaea.archivertest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * 归档测试示例
 *
 * @author 木子李·De
 * @since 2021/2/3 14:16
 */
@SpringBootApplication
public class ArchiverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArchiverApplication.class, args);
	}

}
