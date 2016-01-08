package com.fwq.rocketMQ;

import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;

public class ConcurrentConsumer implements MessageListenerConcurrently {

	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,ConsumeConcurrentlyContext consumeconcurrentlycontext) {
		MessageExt messageExt = list.get(0);
		System.out.println(messageExt);
		String string = consumeconcurrentlycontext.toString();
		System.out.println(string);
		consumeconcurrentlycontext.setDelayLevelWhenNextConsume(2);
		return ConsumeConcurrentlyStatus.RECONSUME_LATER;
	}
	
	public static void main(String[] args) throws MQClientException {
		new ConcurrentConsumer().startConsumer();
	}
	
	
	public void startConsumer() throws MQClientException{
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        consumer.setNamesrvAddr(RocketMQTest.NAMESER_ADDR);
        consumer.setInstanceName(RocketMQTest.PRODUCER_INSTANCE_NAME);
        consumer.setConsumerGroup(RocketMQTest.GROUP);
        consumer.setConsumeMessageBatchMaxSize(1);
        consumer.setConsumeThreadMin(10);
        consumer.setConsumeThreadMax(10);
        consumer.setPullInterval(0L);
        consumer.subscribe("testaaa", "testaaa");
        consumer.registerMessageListener(this);
        consumer.start();
	}

}
