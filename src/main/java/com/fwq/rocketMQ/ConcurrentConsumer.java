package com.fwq.rocketMQ;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.format.datetime.DateFormatter;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;

public class ConcurrentConsumer implements MessageListenerConcurrently {

	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,ConsumeConcurrentlyContext consumeconcurrentlycontext) {
		MessageExt messageExt = list.get(0);
		byte[] body = messageExt.getBody();
		String bodystr = new String(body);
		int reconsumeTimes = messageExt.getReconsumeTimes();
		Date date = new Date();
		String format = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
		System.out.println("内容："+bodystr+"，时间"+format+",次数"+reconsumeTimes);
		if(bodystr.contains("A")){
			System.out.println("重复消费");
			if(reconsumeTimes==5){
				System.out.println("重复消费了5次，返回成功");
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
			consumeconcurrentlycontext.setDelayLevelWhenNextConsume(1);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}
	
	public static void main(String[] args) throws MQClientException, SQLException {
		new ConcurrentConsumer().startConsumer();
	}
	
	
	public void startConsumer() throws MQClientException{
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        consumer.setNamesrvAddr(RocketMQTest.NAMESER_ADDR);
        consumer.setInstanceName(RocketMQTest.PRODUCER_INSTANCE_NAME);
        consumer.setConsumerGroup(RocketMQTest.GROUP);
        consumer.setConsumeMessageBatchMaxSize(1);
        consumer.setConsumeThreadMin(1);
        consumer.setConsumeThreadMax(1);
        consumer.subscribe("testucf", "testucf");
        consumer.registerMessageListener(new ConcurrentConsumer());
        consumer.start();
        System.out.println("consumer start......");
	}

}
