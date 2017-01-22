package com.lxwde.spaghetti;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lxwde.spaghetti")
public class SpaghettiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpaghettiApplication.class, args);
	}
}
