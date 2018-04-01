package com.cloud.project1.config;

import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.cloud.project1.constants.Constants;

@Configuration
public class AwsConfiguration {

	public BasicAWSCredentials basicAWSCredentials() {
		return new BasicAWSCredentials(Constants.ACCESSKEY, Constants.SECRETKEY);
	}

	public AmazonSQS amazonSQS() {
		AmazonSQS amazonSQSClient = AmazonSQSClientBuilder.standard().withRegion(Constants.REGION)
				.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials())).build();
		return amazonSQSClient;
	}

	public AmazonEC2 amazonEC2() {
		AmazonEC2 amazonEC2 = AmazonEC2ClientBuilder.standard().withRegion(Constants.REGION)
				.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials())).build();
		return amazonEC2;
	}

}
