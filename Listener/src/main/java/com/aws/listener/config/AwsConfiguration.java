package com.aws.listener.config;

import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.aws.listener.constants.Constants;

@Configuration
public class AwsConfiguration {

	public BasicAWSCredentials basicAWSCredentials() {
		return new BasicAWSCredentials(Constants.ACCESSKEY, Constants.SECRETKEY);
	}

	public AmazonS3 amazonS3() {
		AmazonS3 amazonS3Client = AmazonS3ClientBuilder.standard()
				.withRegion(Constants.REGION)
				.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials())).build();
		//amazonS3Client.setRegion(Region.getRegion(Regions.fromName(Constants.REGION)));
		return amazonS3Client;
	}

	public AmazonSQS amazonSQS() {
		AmazonSQS amazonSQSClient = AmazonSQSClientBuilder.standard()
				.withRegion(Constants.REGION)
				.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials())).build();
		//amazonSQSClient.setRegion(Region.getRegion(Regions.fromName(Constants.REGION)));
		return amazonSQSClient;
	}

	public AmazonEC2 amazonEC2() {
		AmazonEC2 amazonEC2 = AmazonEC2ClientBuilder.standard()
				.withRegion(Constants.REGION)
				.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials())).build();
		//amazonEC2.setRegion(Region.getRegion(Regions.fromName(Constants.REGION)));
		return amazonEC2;
	}

}
