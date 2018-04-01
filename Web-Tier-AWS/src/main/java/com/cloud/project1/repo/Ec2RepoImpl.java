package com.cloud.project1.repo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.ec2.model.AmazonEC2Exception;
import com.amazonaws.services.ec2.model.DescribeInstanceStatusRequest;
import com.amazonaws.services.ec2.model.DescribeInstanceStatusResult;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.TagSpecification;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.cloud.project1.config.AwsConfiguration;
import com.cloud.project1.constants.Constants;

@Repository
public class Ec2RepoImpl implements Ec2Repo {

	private static final Logger LOGGER = LoggerFactory.getLogger(Ec2RepoImpl.class);

	@Autowired
	private AwsConfiguration awsConfiguration;
	
	@Override
	public Integer createInstance(String imageId, Integer maxNumberOfInstances, Integer nameCount) {

		LOGGER.debug("Creating the instance.");
		int minInstanceCount = maxNumberOfInstances - 1; // create 1 instance
		int maxInstanceCount = maxNumberOfInstances;
		if(minInstanceCount == 0)
			minInstanceCount = 1;
		List<String> securityGroupIds = new ArrayList<String>();
		securityGroupIds.add(Constants.SECURITYGROUPID);
		Collection<TagSpecification> tagSpecifications = new ArrayList<TagSpecification>();
		TagSpecification tagSpecification = new TagSpecification();
		Collection<Tag> tags = new ArrayList<Tag>();
		Tag t = new Tag();
		t.setKey("Name");
		t.setValue("App-Instance");
		tags.add(t);
		tagSpecification.setResourceType("instance");
		tagSpecification.setTags(tags);
		tagSpecifications.add(tagSpecification);
		// Placement placement = new Placement(Constants.AVAILABILITYZONE);
		RunInstancesRequest rir = new RunInstancesRequest(imageId, minInstanceCount, maxInstanceCount);
		rir.setInstanceType("t2.micro");
		rir.setSecurityGroupIds(securityGroupIds);
		rir.setTagSpecifications(tagSpecifications);
		// rir.setPlacement(placement);
		// Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		// ec2.setRegion(usWest2);
		RunInstancesResult result = null;
		try {
			result = awsConfiguration.amazonEC2().runInstances(rir);
		} catch (AmazonEC2Exception amzEc2Exp) {
			return nameCount;
		} catch (Exception e) {
			return nameCount;
		}
		// List<Instance> resultInstance = result.getReservation().getInstances();
		return nameCount;
	}

	@Override
	public void stopInstance(String instanceId) {
		LOGGER.debug("Stopping the instance.");
		StopInstancesRequest request = new StopInstancesRequest().withInstanceIds(instanceId);
		awsConfiguration.amazonEC2().stopInstances(request);
	}

	@Override
	public void startInstance(String instanceId) {
		LOGGER.debug("Starting the instance.");
		StartInstancesRequest request = new StartInstancesRequest().withInstanceIds(instanceId);
		awsConfiguration.amazonEC2().startInstances(request);
	}

	@Override
	public void terminateInstance(String instanceId) {
		LOGGER.debug("Terminating the instance.");
		TerminateInstancesRequest request = new TerminateInstancesRequest().withInstanceIds(instanceId);
		awsConfiguration.amazonEC2().terminateInstances(request);
	}

	@Override
	public DescribeInstanceStatusResult describeInstance(DescribeInstanceStatusRequest describeRequest) {
		return awsConfiguration.amazonEC2().describeInstanceStatus(describeRequest);
	}

}
