package com.sivaprakash.eureka_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MyBankEurekaserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBankEurekaserverApplication.class, args);
	}

}
