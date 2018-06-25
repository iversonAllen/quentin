package com.chanshiguan.quentinapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.chanshiguan"})
@MapperScan("com.chanshiguan.quentindao")
public class QuentinApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuentinApiApplication.class, args);
	}
}
