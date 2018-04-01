package com.aws.listener.repo;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.Message;

public interface SqsRepo {
	
	/*public static final AmazonSQS SQS = AmazonSQSClientBuilder.defaultClient();*/
	
	public static final String INPUTQUEUENAME = "inputMessageQueue";
	
	public static final String OUTPUTQUEUE = "outputMessageQueue";
	
	public CreateQueueResult createQueue(String queueName);
	
	public void deleteMessage(Message message, String queueName);
	
	public Message receiveMessage(String queueName, Integer waitTime, Integer visibilityTimeout);
	
	public void sendMessage(String messageBody, String queueName, Integer delaySeconds);
	
	//changed
	public String DeepLearning(String urlInput);
	public String parseURL(String urlInput);
	//
}
