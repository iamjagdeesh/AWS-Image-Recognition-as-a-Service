package com.aws.listener.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.model.Message;
import com.aws.listener.constants.Constants;

@Service
public class ListenerAndDispatchingServiceImpl implements ListenerAndDispatchingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ListenerAndDispatchingServiceImpl.class);

	@Autowired
	private SqsService sqsService;

	@Autowired
	private S3Service s3Service;

	@Override
	public void generalFunction() {
		LOGGER.debug("Listener and dispatch main routine.");
		while (true) {
			Message message = sqsService.receiveMessage(Constants.INPUTQUEUENAME, 20, 15);
			if (message == null) {
				continue;
			}
			String predictValue = sqsService.deepLearningOutput(message.getBody());
			if (predictValue == null) {
				predictValue = "NoPrediction";
			}
			predictValue = predictValue.trim();
			String nameImage = sqsService.parseImageName(message.getBody());
			s3Service.putObject(nameImage, predictValue);
			// Write in Output SQS and then delete
			sqsService.sendMessage(nameImage + "/" + predictValue, Constants.OUTPUTQUEUE, 0);
			sqsService.deleteMessage(message, Constants.INPUTQUEUENAME);
		}
	}

}
