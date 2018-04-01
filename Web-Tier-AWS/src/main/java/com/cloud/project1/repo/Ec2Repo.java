package com.cloud.project1.repo;

import com.amazonaws.services.ec2.model.DescribeInstanceStatusRequest;
import com.amazonaws.services.ec2.model.DescribeInstanceStatusResult;

public interface Ec2Repo {

	public void stopInstance(String instanceId);

	public void startInstance(String instanceId);

	public void terminateInstance(String instanceId);

	public DescribeInstanceStatusResult describeInstance(DescribeInstanceStatusRequest describeRequest);

	public Integer createInstance(String imageId, Integer maxNumberOfInstances, Integer nameCount);

}
