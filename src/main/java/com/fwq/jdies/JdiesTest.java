package com.fwq.jdies;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;


/**
 * @Description: 
 * @author fwq
 * @date 2015-12-17 下午2:56:28
 */
public class JdiesTest {


	/**
	 * @Description: 普通测试
	 * 
	 * @author FWQ
	 * @date 2015-12-17 下午4:47:02
	 */
	@Test
	public void testJedis()
	{
		Jedis jedis = new Jedis("localhost",1111);
	    long start = System.currentTimeMillis();
	    for (int i = 0; i < 100000; i++) {
	        String result = jedis.set("n" + i, "n" + i);
	    }
	    long end = System.currentTimeMillis();
	    System.out.println("Simple SET: " + ((end - start)/1000.0) + " seconds");
	    jedis.disconnect();
	}
	
	@Test
	public void testJedis1()
	{
		Jedis jedis = new Jedis("localhost",1111);
	    long start = System.currentTimeMillis();
	    Transaction multi = jedis.multi();
	    for (int i = 0; i < 100000; i++) {
	         multi.set("n" + i, "n" + i);
	    }
	    multi.exec();
	    long end = System.currentTimeMillis();
	    System.out.println("Simple SET: " + ((end - start)/1000.0) + " seconds");
	    jedis.disconnect();
	}
	
	
	public static void main(String[] args) {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxActive(10);
		jedisPoolConfig.setMaxActive(5);
		jedisPoolConfig.setMaxWait(3000);
		
		JedisPool jedisPool = new JedisPool(jedisPoolConfig,"localhost",1111);
		 
		Jedis jedis = jedisPool.getResource();
		
		jedis.set("", "");
		jedis.setex("name", 1, "fanweiqi");
		
		jedisPool.returnResource(jedis);
		 
	}
	
	
	
		
}
