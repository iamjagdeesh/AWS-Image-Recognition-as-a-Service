package com.aws.listener.repo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.QueueDoesNotExistException;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.aws.listener.config.AwsConfiguration;

@Repository
public class SqsRepoImpl implements SqsRepo {

	private static final Logger LOGGER = LoggerFactory.getLogger(SqsRepoImpl.class);

	@Autowired
	private AwsConfiguration awsConfiguration;

	@Override
	public void deleteMessage(Message message, String queueName) {
		LOGGER.debug("Deleting the message in the queue.");
		String queueUrl = awsConfiguration.amazonSQS().getQueueUrl(queueName).getQueueUrl();
		String messageReceiptHandle = message.getReceiptHandle();
		DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest(queueUrl, messageReceiptHandle);
		awsConfiguration.amazonSQS().deleteMessage(deleteMessageRequest);
	}

	@Override
	public CreateQueueResult createQueue(String queueName) {
		LOGGER.debug("Creating the queue.");
		CreateQueueResult createQueueResult = awsConfiguration.amazonSQS().createQueue(queueName);
		return createQueueResult;
	}

	public String DeepLearning(String urlInput) {
		LOGGER.debug("Running the deep learning model.");
		String termOutput = null;
		Process p;
		try {
			p = new ProcessBuilder("/bin/bash", "-c", "source /home/ubuntu/tensorflow/bin/activate && "
					+ "python /home/ubuntu/tensorflow/models/tutorials/image/imagenet/classify_image.py --image_file "
					+ urlInput + " --num_top_predictions 1 | tail -n 1 | cut -d '(' -f1").start();
			p.waitFor();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			termOutput = br.readLine();
			p.destroy();
		} catch (Exception e) {
		}
		return termOutput;
	}

	public String parseURL(String urlInput) {
		LOGGER.debug("Parsing the deep learning output.");
		String nameImage = null;
		String[] tokens = urlInput.split("/");
		for (String temp : tokens)
			nameImage = temp;
		return nameImage;
	}

	@Override
	public Message receiveMessage(String queueName, Integer waitTime, Integer visibilityTimeout) {
		LOGGER.debug("Receiving the message from the queue.");
		String queueUrl = awsConfiguration.amazonSQS().getQueueUrl(queueName).getQueueUrl();
		ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
		receiveMessageRequest.setMaxNumberOfMessages(1);
		receiveMessageRequest.setWaitTimeSeconds(waitTime);
		receiveMessageRequest.setVisibilityTimeout(visibilityTimeout);
		ReceiveMessageResult receiveMessageResult = awsConfiguration.amazonSQS().receiveMessage(receiveMessageRequest);
		List<Message> messageList = receiveMessageResult.getMessages();
		if (messageList.isEmpty()) {
			return null;
		}
		return messageList.get(0);
	}

	@Override
	public void sendMessage(String messageBody, String queueName, Integer delaySeconds) {
		LOGGER.debug("Sending the message into the queue.");
		String queueUrl = null;
		try {
			queueUrl = awsConfiguration.amazonSQS().getQueueUrl(queueName).getQueueUrl();
		} catch (QueueDoesNotExistException queueDoesNotExistException) {
			CreateQueueResult createQueueResult = this.createQueue(queueName);
		}
		queueUrl = awsConfiguration.amazonSQS().getQueueUrl(queueName).getQueueUrl();
		SendMessageRequest sendMessageRequest = new SendMessageRequest().withQueueUrl(queueUrl)
				.withMessageBody(messageBody).withDelaySeconds(delaySeconds);
		awsConfiguration.amazonSQS().sendMessage(sendMessageRequest);
		
	}
	
}
