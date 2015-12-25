package com.fwq.jdies.lock;

import java.io.ObjectInputStream.GetField;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class TestJedisLock {
	private static JedisLock lock=new JedisLock(getJedisPool());
	private int publicValue=0;
	static String key="nihao";
	
	public static void main(String[] args) {
		
		for (int i = 0; i < 10; i++) {
			startT();
		}
	}
	
	@Test
	public void testtt()
	{
		test();
	}
	

	public static void startT(){
			new Thread(){
				public void run() {
					test();
				}
			}.start();
	}
	
	public static void test()
	{
		JedisPool jedisPool = getJedisPool();
		JedisLock lock1 = new JedisLock(jedisPool);
		boolean flag =lock1.getLock(key);
		if(flag)
		{
			System.err.println(Thread.currentThread().getName()+"：获取成功");
			lock1.unLock();
		}else
		{
			System.out.println(Thread.currentThread().getName()+"：获取失败");
		}
		
	}
	
	
	public static JedisPool getJedisPool(){
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(100);
		config.setMaxIdle(100);
		config.setMaxWait(30);
		return new JedisPool("localhost",1111);
	}
	
}
