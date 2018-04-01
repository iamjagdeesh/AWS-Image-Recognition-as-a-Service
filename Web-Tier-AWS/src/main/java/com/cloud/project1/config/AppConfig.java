package com.cloud.project1.config;

import org.springframework.context.annotation.Bean;

import com.cloud.project1.repo.Ec2Repo;
import com.cloud.project1.repo.Ec2RepoImpl;
import com.cloud.project1.repo.SqsRepo;
import com.cloud.project1.repo.SqsRepoImpl;
import com.cloud.project1.service.Ec2Service;
import com.cloud.project1.service.Ec2ServiceImpl;
import com.cloud.project1.service.LoadBalancerService;
import com.cloud.project1.service.LoadBalancerServiceImpl;
import com.cloud.project1.service.SqsService;
import com.cloud.project1.service.SqsServiceImpl;

public class AppConfig {
	
	@Bean
	public SqsService sqsService() {
		return new SqsServiceImpl();
	}

	@Bean
	public SqsRepo sqsRepo() {
		return new SqsRepoImpl();
	}
	
	@Bean
	public Ec2Repo ec2Repo() {
		return new Ec2RepoImpl();
	}

	@Bean
	public Ec2Service ec2Service() {
		return new Ec2ServiceImpl();
	}
	
	@Bean
	public AwsConfiguration awsConfiguration() {
		return new AwsConfiguration();
	}
	
	@Bean
	public LoadBalancerService loadBalancerService() {
		return new LoadBalancerServiceImpl();
	}

}
