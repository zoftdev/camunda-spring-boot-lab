package com.hlex.camundacleantest;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class CamundaCleanTestApplication {
	Logger logger=LoggerFactory.getLogger(this.getClass());
	

	public static void main(String[] args) {
		SpringApplication.run(CamundaCleanTestApplication.class, args);
	}

	// @EventListener
    // public void createUser(PostDeployEvent postDeployEvent){
	// 	logger.info("event detect");
	// }

}
