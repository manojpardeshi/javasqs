package com.cognizant.sqs;

import java.util.List;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

public class SimpleQueueServiceSample {

	public static void main(String[] args) {
		AmazonSQS sqs = new AmazonSQSClient();
		Region useast1 = Region.getRegion(Regions.US_EAST_1);
		String queueUrl = "https://sqs.us-east-1.amazonaws.com/426655134338/MyQueue";
		System.out.println("SQS endpoint is :" + queueUrl);
		sqs.setEndpoint(queueUrl);
		sqs.setRegion(useast1);
		boolean flag = true;
		ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(
				queueUrl);
		
		while (flag) {
			List<Message> messages = sqs.receiveMessage(receiveMessageRequest)
					.getMessages();
			System.out.println("Messages size : " + messages.size());
			if (messages == null || messages.size() == 0) {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				for (Message message : messages) {
					String messageText = message.getBody();
					System.out.println("SQS message is -> " + messageText);
					String messageRecieptHandle = message.getReceiptHandle();
					sqs.deleteMessage(queueUrl, messageRecieptHandle);
				}

			}

		}
	}
}
