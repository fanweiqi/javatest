package com.fwq.testMQ;

import java.util.concurrent.locks.Lock;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.spring.ActiveMQConnectionFactory;

public class Test {
	private static ActiveMQConnectionFactory ac = new ActiveMQConnectionFactory();
	private Lock lock=(Lock) new Object();
	public static void main(String[] args) throws JMSException {
		
		final Connection connection = ac.createConnection();
		connection.start();
		new Thread(){
			public void run() {
				try {
					Session session=connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
					Queue queue = session.createQueue("my-first-mq");
					MessageProducer mp = session.createProducer(queue);
					
					for (int i = 0; i < 10; i++) {
						MapMessage mapMessage = session.createMapMessage();
						mapMessage.setString("firstkey", "firstvalue");
						mp.send(mapMessage);
						
					}
					
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
		
		new Thread(){
			public void run() {
				consumer();
			};
		}.start();
		
	}
	
	
	public static void consumer()
	{
		try {
			Connection connection = ac.createConnection();
			Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("my-fist-mq");
			MessageConsumer consumer = session.createConsumer(queue);
			
			Message message = consumer.receive();
			
			System.out.println(message);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
