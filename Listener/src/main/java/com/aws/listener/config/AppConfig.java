package com.aws.listener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aws.listener.repo.Ec2Repo;
import com.aws.listener.repo.Ec2RepoImpl;
import com.aws.listener.repo.S3Repo;
import com.aws.listener.repo.S3RepoImpl;
import com.aws.listener.repo.SqsRepo;
import com.aws.listener.repo.SqsRepoImpl;
import com.aws.listener.service.Ec2Service;
import com.aws.listener.service.Ec2ServiceImpl;
import com.aws.listener.service.ListenerAndDispatchingService;
import com.aws.listener.service.ListenerAndDispatchingServiceImpl;
import com.aws.listener.service.S3Service;
import com.aws.listener.service.S3ServiceImpl;
import com.aws.listener.service.SqsService;
import com.aws.listener.service.SqsServiceImpl;

@Configuration
public class AppConfig {

	@Bean
	public ListenerAndDispatchingService listenerAndDispatchingService() {
		return new ListenerAndDispatchingServiceImpl();
	}

	@Bean
	public SqsService sqsService() {
		return new SqsServiceImpl();
	}

	@Bean
	public SqsRepo sqsRepo() {
		return new SqsRepoImpl();
	}

	@Bean
	public S3Repo s3Repo() {
		return new S3RepoImpl();
	}

	@Bean
	public S3Service s3Service() {
		return new S3ServiceImpl();
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

}
