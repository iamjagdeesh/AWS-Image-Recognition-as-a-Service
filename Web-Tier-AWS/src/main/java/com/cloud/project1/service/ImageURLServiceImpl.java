package com.cloud.project1.service;

import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.model.Message;
import com.cloud.project1.constants.Constants;
import com.cloud.project1.domain.ImageURL;

@Service
public class ImageURLServiceImpl implements ImageURLService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageURLServiceImpl.class);

	private static Hashtable<String, String> hashTable = new Hashtable<String, String>();

	@Autowired
	private SqsService sqsService;

	@Override
	public ImageURL getImageURL() {
		// TODO Auto-generated method stub
		ImageURL imageURL = new ImageURL("http://defaultURL");
		return imageURL;
	}

	@Override
	public String inputURL(String url) {
		LOGGER.debug("Received the image url.");
		String messageBody = url;
		sqsService.sendMessage(messageBody, Constants.INPUTQUEUENAME, 0);
		String imageName = null;
		String[] tokens = url.split("/");
		for (String temp : tokens)
			imageName = temp;
		String outputMessage = getFromHashOrQueue(imageName);
		return outputMessage;
	}

	@Override
	public String getFromHashOrQueue(String imageName) {
		while (true) {
			String predictedName = hashTable.get(imageName);
			if (predictedName == null) {
				Message outputMessageFromQueue = sqsService.receiveMessage(Constants.OUTPUTQUEUE, 20, 5);
				if (outputMessageFromQueue == null) {
					continue;
				}
				String outputMessageBodyFromQueue = outputMessageFromQueue.getBody();
				String[] tokens = outputMessageBodyFromQueue.split("/", 2);
				Integer count = 0;
				String imageNameInQueue = null;
				String prediction = null;
				for (String string : tokens) {
					if (count == 0)
						imageNameInQueue = string;
					else
						prediction = string;
					count++;
				}
				if (imageNameInQueue.equals(imageName)) {
					sqsService.deleteMessage(outputMessageFromQueue, Constants.OUTPUTQUEUE);
					return prediction;
				} else {
					hashTable.put(imageNameInQueue, prediction);
					sqsService.deleteMessage(outputMessageFromQueue, Constants.OUTPUTQUEUE);
				}
			} else {
				hashTable.remove(imageName);
				return predictedName;
			}
		}
	}

}
