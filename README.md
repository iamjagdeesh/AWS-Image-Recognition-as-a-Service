Cloud Computing project - CSE 546

Project 1 - IaaS - Amazon Web Services

The aim of the project was to develop a cloud app which will provide Image Recognition as a Service to users 
by using the by using the AWS cloud resources to perform deep learning on images provided by the users.

The deep learning model was provided to us in an AWS image (ID: ami-07303b67, Name: imageRecognition, Region: us-west-1a). 
This application invokes this model to perform image recognition on the received images.

The application will handle multiple requests concurrently. 
It will automatically scale out when the request demand increases, and automatically scale in when the demand drops.

Further details are provided in the report.

Team Members:-
Harsh Patel (1211434273)
Jagadeesh Basavaraju (1213004713)
Shiksha Patel (1211045638)
Suraj S Kattige (1211230381)

Instructions:-

HTTP Requests should be sent in the below format
http://[IP Address]/cloudimagerecognition?input=[URL of the image]
example: http://13.57.186.192:8080/cloudimagerecognition?input=http://visa.lab.asu.edu/cifar-10/104_automobile.png