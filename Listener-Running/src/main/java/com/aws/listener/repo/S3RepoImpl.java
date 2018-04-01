package com.aws.listener.repo;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.aws.listener.config.AwsConfiguration;
import com.aws.listener.constants.Constants;

@Repository
public class S3RepoImpl implements S3Repo {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(S3RepoImpl.class);
	
	@Autowired
	private AwsConfiguration awsConfiguration;

	@Override
	public Bucket createBucket() {
		LOGGER.debug("Creating the bucket if not exists.");
		Bucket b = null;
		if (awsConfiguration.amazonS3().doesBucketExist(Constants.BUCKETNAME)) {
			LOGGER.debug("The bucket exits, so returning the existing bucket.");
			b = getBucket();
		} else {
			LOGGER.debug("Creating the bucket "+Constants.BUCKETNAME);
			b = awsConfiguration.amazonS3().createBucket(Constants.BUCKETNAME);
		}

		return b;
	}

	@Override
	public Bucket getBucket() {
		LOGGER.debug("Returning the bucket.");
		Bucket namedBucket = null;
		List<Bucket> buckets = awsConfiguration.amazonS3().listBuckets();
		for (Bucket b : buckets) {
			if (b.getName().equals(Constants.BUCKETNAME)) {
				namedBucket = b;
			}
		}

		return namedBucket;
	}

	@Override
	public void putObject(String key, String value) {
		LOGGER.debug("Inserting the object into the bucket.");
		this.createBucket();
		byte[] contentAsBytes = null;
		try {
			contentAsBytes = value.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ByteArrayInputStream contentsAsStream = new ByteArrayInputStream(contentAsBytes);
		ObjectMetadata md = new ObjectMetadata();
		md.setContentLength(contentAsBytes.length);
		awsConfiguration.amazonS3().putObject(new PutObjectRequest(Constants.BUCKETNAME, key, contentsAsStream, md));
	}

}
