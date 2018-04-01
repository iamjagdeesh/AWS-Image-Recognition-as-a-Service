package com.aws.listener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aws.listener.repo.S3Repo;

@Service
public class S3ServiceImpl implements S3Service {
	
	@Autowired
	private S3Repo s3Repo;

	@Override
	public void putObject(String key, String value) {
		// TODO Auto-generated method stub
		s3Repo.putObject(key, value);
	}

}
