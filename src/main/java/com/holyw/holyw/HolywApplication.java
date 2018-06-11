package com.holyw.holyw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(value = "com.holyw.holyw.mapper")
@EnableScheduling
public class HolywApplication {

	public static void main(String[] args) {
		SpringApplication.run(HolywApplication.class, args);
	}
}
