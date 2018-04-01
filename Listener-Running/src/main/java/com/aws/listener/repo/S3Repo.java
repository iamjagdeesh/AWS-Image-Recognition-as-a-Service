package com.aws.listener.repo;

import com.amazonaws.services.s3.model.Bucket;

public interface S3Repo {
	
	public Bucket createBucket();
	
	public Bucket getBucket();
	
	public void putObject(String key, String value);

}
