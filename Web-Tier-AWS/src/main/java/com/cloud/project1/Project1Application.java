package com.cloud.project1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.cloud.project1.config.AppConfig;
import com.cloud.project1.service.LoadBalancerService;
import com.cloud.project1.service.LoadBalancerServiceImpl;

@SpringBootApplication
public class Project1Application {

	public static void main(String[] args) {
		SpringApplication.run(Project1Application.class, args);
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		// context.refresh();
		LoadBalancerService loadBalancerService = context.getBean(LoadBalancerService.class);
		loadBalancerService.scaleInscaleOut();
		context.close();
	}
}
