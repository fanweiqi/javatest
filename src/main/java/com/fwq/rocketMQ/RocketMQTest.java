package com.fwq.rocketMQ;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.junit.Test;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

public class RocketMQTest {
	static final String NAMESER_ADDR="10.20.70.45:9876";
	static final String INSTANCE_NAME="ucfGatewayPreProducer";
	static final String GROUP="ucfGatewayPreProducerGroup";
			

	public static void main(String[] args) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {

		final DefaultMQProducer producer = new DefaultMQProducer();
		producer.setNamesrvAddr(NAMESER_ADDR);
		producer.setInstanceName(INSTANCE_NAME);
		producer.setProducerGroup(GROUP);
		producer.start();
		Message message = new Message();
		message.setWaitStoreMsgOK(true);
		Date date = new Date();
		String format = new SimpleDateFormat("yyyyMMddHHmmss").format(date)+"A";
		message.setBody(format.getBytes());
		message.setTopic("gatewayZtrzQueryTopic");
		message.setTags("gatewayZtrzQueryTag");
		message.setDelayTimeLevel(1);
		SendResult send = producer.send(message);
		System.out.println(send);
		producer.shutdown();
	}
	
	@Test
	public void test11(){
		int sum=0;
		for (int i = 1; i < 11; i++) {
			sum=sum+1<<1;
		}
		System.out.println(sum);
	}
}
