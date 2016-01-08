package com.fwq.rocketMQ;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

public class RocketMQTest {
	static final String NAMESER_ADDR="10.20.70.45:9876";
	static final String PRODUCER_INSTANCE_NAME="ucfProducerTest";
	static final String CONSUMER_INSTANCE_NAME="ucfConsumerTest";
	static final String GROUP="ucfGroupTest";
			

	public static void main(String[] args) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
		
		System.out.println(1<<1); 
		
		System.exit(1);
		final DefaultMQProducer producer = new DefaultMQProducer();
		producer.setNamesrvAddr(NAMESER_ADDR);
		producer.setInstanceName(PRODUCER_INSTANCE_NAME);
		producer.setProducerGroup(GROUP);
		producer.start();
		
//		for (int i = 0; i < 100; i++) {
//			new Thread(){
//				public void run() {
//					try {
						Message message = new Message("testaaa","testaaa","hello".getBytes());
//						SendResult send = producer.send(message);
//					} catch (Exception e) {
//						e.printStackTrace();
//						System.out.println(e.getMessage());
//					}
//				};
//			}.start();
//		}
		producer.shutdown();
		System.out.println("end");
	}
}
