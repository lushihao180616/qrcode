package com.lushihao.qrcode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lushihao.qrcode.dao")
public class QrcodeApplication {

	public static void main(String[] args) {
		System.setProperty("java.awt.headless","false");
		SpringApplication.run(QrcodeApplication.class, args);
	}

}
