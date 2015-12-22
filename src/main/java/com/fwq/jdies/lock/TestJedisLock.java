package com.fwq.jdies.lock;

import java.io.ObjectInputStream.GetField;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class TestJedisLock {
	private JedisLock lock=new JedisLock(getJedisPool());
	private int publicValue=0;
	static String key="fdafdaf";
	
	public static void main(String[] args) {
		
		JedisLock lock1 = new JedisLock(getJedisPool());
		boolean gg = lock1.getLock(key);
		System.out.println(gg);
		if(gg)
//			lock1.unLock(key);
		
		gg= lock1.getLock(key);
		System.out.println(gg);
//		
//		for (int i = 0; i < 100; i++) {
//			startT();
//		}
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
		
		JedisLock  a=new TestJedisLock().lock;
		boolean flag = a.getLockNowait(key);
		if(flag)
		{
			System.err.println(Thread.currentThread().getName()+"获取成功");
			a.unLock(key);
		}else
		{
			System.out.println(Thread.currentThread().getName()+"获取失败");
		}
	}
	
	
	public static JedisPool getJedisPool(){
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(100);
		config.setMaxIdle(10);
		config.setMaxWait(3);
		return new JedisPool(config,"localhost",1111);
	}
	
}
