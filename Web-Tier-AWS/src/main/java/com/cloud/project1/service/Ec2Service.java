package com.cloud.project1.service;

public interface Ec2Service {
	
	public Integer getNumberOfInstances();

	public Integer startInstances(Integer count, Integer nameCount);

}
