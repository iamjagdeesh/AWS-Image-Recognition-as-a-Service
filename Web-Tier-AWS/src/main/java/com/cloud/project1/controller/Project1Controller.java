package com.cloud.project1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.project1.domain.ImageURL;
import com.cloud.project1.service.ImageURLService;

@RestController
@EnableAutoConfiguration
@RequestMapping("/")
public class Project1Controller {

	private static final Logger LOGGER = LoggerFactory.getLogger(Project1Controller.class);

	@Autowired
	private ImageURLService imageURLService;

	@RequestMapping(value = "info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getInfo() {
		return new ResponseEntity<String>("Project 1 is running!", HttpStatus.OK);
	}

	@RequestMapping(value = "defaultURL", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ImageURL> getImageURL() {
		ImageURL imageURL = imageURLService.getImageURL();
		return new ResponseEntity<ImageURL>(imageURL, HttpStatus.OK);
	}

	@RequestMapping(value = "cloudimagerecognition", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> inputURL(@RequestParam("input") String url) {
		LOGGER.debug("Received the HTTP request from the user.");
		String outputMessage = imageURLService.inputURL(url);
		return new ResponseEntity<String>(outputMessage, HttpStatus.OK);
	}
}
