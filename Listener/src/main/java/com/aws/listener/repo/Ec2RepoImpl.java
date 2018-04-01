package com.aws.listener.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.util.EC2MetadataUtils;
import com.aws.listener.config.AwsConfiguration;

@Repository
public class Ec2RepoImpl implements Ec2Repo {

	private static final Logger LOGGER = LoggerFactory.getLogger(Ec2RepoImpl.class);

	@Autowired
	private AwsConfiguration awsConfiguration;

	public void endInstance() {
		LOGGER.debug("Ending the instance itself.");
		String myId = EC2MetadataUtils.getInstanceId();
		TerminateInstancesRequest request = new TerminateInstancesRequest().withInstanceIds(myId);
		awsConfiguration.amazonEC2().terminateInstances(request);
	}

}
