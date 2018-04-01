package com.aws.listener.service;

import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.Message;

public interface SqsService {
	
	public CreateQueueResult createQueue(String queueName);
	
	void deleteMessage(Message message, String queueName);
	
	public Message receiveMessage(String queueName, Integer waitTime, Integer visibilityTimeout);
	
	public void sendMessage(String messageBody, String queueName, Integer delaySeconds);

	public String deepLearningOutput(String inputURL);

	public String parseImageName(String inputURL);
	
}
