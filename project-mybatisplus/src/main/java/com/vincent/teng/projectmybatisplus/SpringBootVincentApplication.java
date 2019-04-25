package com.vincent.teng.projectmybatisplus;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.vincent.teng.projectmybatisplus.mapper")
public class SpringBootVincentApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootVincentApplication.class,args);
	}

}
