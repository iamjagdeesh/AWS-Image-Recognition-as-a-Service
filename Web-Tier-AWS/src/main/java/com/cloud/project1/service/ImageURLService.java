package com.cloud.project1.service;

import com.cloud.project1.domain.ImageURL;

public interface ImageURLService {
	
	public ImageURL getImageURL();
	
	public String inputURL(String url);

	public String getFromHashOrQueue(String imageName);

}
