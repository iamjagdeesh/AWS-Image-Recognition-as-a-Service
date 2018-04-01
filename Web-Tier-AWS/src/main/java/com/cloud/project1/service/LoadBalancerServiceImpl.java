package com.cloud.project1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.project1.constants.Constants;

@Service
public class LoadBalancerServiceImpl implements LoadBalancerService {

	@Autowired
	private SqsService sqsService;

	@Autowired
	private Ec2Service ec2Service;

	@Override
	public void scaleInscaleOut() {
		Integer nameCount = 0;
		while (true) {
			Integer numOfMsgs = sqsService.getApproximateNumberOfMsgs(Constants.INPUTQUEUENAME);
			Integer countOfRunningInstances = ec2Service.getNumberOfInstances();
			Integer numberOfAppInstances = countOfRunningInstances - 1;
			if (numOfMsgs > 0 && numOfMsgs > numberOfAppInstances) {
				Integer temp = Constants.MAXRUNNINGINSTANCES - numberOfAppInstances;
				if (temp > 0) {
					Integer temp1 = numOfMsgs - numberOfAppInstances;
					if (temp1 >= temp) {
						nameCount = ec2Service.startInstances(temp, nameCount);
					} else {
						nameCount = ec2Service.startInstances(temp1, nameCount);
					}
					nameCount++;
				}
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
