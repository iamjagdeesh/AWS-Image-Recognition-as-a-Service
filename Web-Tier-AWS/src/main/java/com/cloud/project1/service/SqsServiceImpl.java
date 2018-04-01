package com.cloud.project1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.Message;
import com.cloud.project1.repo.SqsRepo;

@Service
public class SqsServiceImpl implements SqsService {

	@Autowired
	private SqsRepo sqsRepo;

	@Override
	public void deleteMessage(Message message, String queueName) {
		sqsRepo.deleteMessage(message, queueName);
	}

	@Override
	public CreateQueueResult createQueue(String queueName) {
		// TODO Auto-generated method stub
		CreateQueueResult createQueueResult = sqsRepo.createQueue(queueName);
		return createQueueResult;
	}

	@Override
	public Message receiveMessage(String queueName, Integer waitTime, Integer visibilityTimeout) {
		return sqsRepo.receiveMessage(queueName, waitTime, visibilityTimeout);
	}

	@Override
	public void sendMessage(String messageBody, String queueName, Integer delaySeconds) {
		sqsRepo.sendMessage(messageBody, queueName, delaySeconds);
	}

	@Override
	public Integer getApproximateNumberOfMsgs(String queueName) {
		return sqsRepo.getApproximateNumberOfMsgs(queueName);
	}

}
