package com.aws.listener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aws.listener.repo.Ec2Repo;

@Service
public class Ec2ServiceImpl implements Ec2Service {
	
	@Autowired
	private Ec2Repo ec2Repo;

	@Override
	public void endInstance() {
		// TODO Auto-generated method stub
		ec2Repo.endInstance();
	}

}
