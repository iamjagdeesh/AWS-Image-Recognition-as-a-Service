package com.cloud.project1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.ec2.model.DescribeInstanceStatusRequest;
import com.amazonaws.services.ec2.model.DescribeInstanceStatusResult;
import com.amazonaws.services.ec2.model.InstanceState;
import com.amazonaws.services.ec2.model.InstanceStateName;
import com.amazonaws.services.ec2.model.InstanceStatus;
import com.cloud.project1.constants.Constants;
import com.cloud.project1.repo.Ec2Repo;

@Service
public class Ec2ServiceImpl implements Ec2Service {

	@Autowired
	private Ec2Repo ec2Repo;
	
	@Override
	public Integer startInstances(Integer count, Integer nameCount) {
		return ec2Repo.createInstance(Constants.IMAGEID, count, nameCount);
	}

	@Override
	public Integer getNumberOfInstances() {
		// TODO Auto-generated method stub
		DescribeInstanceStatusRequest describeRequest = new DescribeInstanceStatusRequest();
		describeRequest.setIncludeAllInstances(true);
		DescribeInstanceStatusResult describeInstances = ec2Repo.describeInstance(describeRequest);
		List<InstanceStatus> instanceStatusList = describeInstances.getInstanceStatuses();
		Integer countOfRunningInstances = 0;
		for (InstanceStatus instanceStatus : instanceStatusList) {
			InstanceState instanceState = instanceStatus.getInstanceState();
			if (instanceState.getName().equals(InstanceStateName.Running.toString())) {
				countOfRunningInstances++;
			}
		}
		
		return countOfRunningInstances;
	}
	
}
