package com.fwq.jdies;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;


/**
 * @Description: 
 * @author fwq
 * @date 2015-12-17 下午2:56:28
 */
public class JdiesTest {


	/**
	 * @author FWQ
	 * @date 2015-12-21 下午5:09:54
	 * @Description: 
	 * 正常读写
	 */
	@Test
	public void testJedis()
	{
		Jedis jedis = new Jedis("localhost",1111);
		WatchPer watchPer = new WatchPer(new Date());
	    for (int i = 0; i < 100000; i++) {
	         jedis.set("n" + i, "n" + i);
	    }
	    for (int i = 0; i < 100000; i++) {
	         jedis.get("n" + i);
	    }
	    String useTime = watchPer.useTime(new Date());
		System.out.println("共计条操作"+useTime);
	    jedis.disconnect();
	}
	
	
	/**
	 * @author FWQ
	 * @date 2015-12-21 下午4:42:05
	 * @Description: 
	 * 测试jedis管道
	 */
	@Test
	public void testPipeline()
	{
		Jedis jedis = new Jedis("localhost",1111);
		Pipeline pipelined = jedis.pipelined();
		
		WatchPer watchPer = new WatchPer(new Date());
		 for (int i = 0; i < 100000; i++) {
			 pipelined.set("pn" + i, "n" + i);
	    }
	    for (int i = 0; i < 100000; i++) {
	    	pipelined.get("pn" + i);
	    }
		List<Object> syncAndReturnAll = pipelined.syncAndReturnAll();
		String useTime = watchPer.useTime(new Date());
		System.out.println("共计"+syncAndReturnAll.size()+"条操作，"+useTime);
	}
	
	/**
	 * @author FWQ
	 * @date 2015-12-21 下午5:41:16
	 * @Description: 
	 * 测试expire方法，为key设置过期时间
	 */
	@Test
	public void testExpire() throws InterruptedException
	{
		Jedis jedis = new Jedis("localhost",1111);
		jedis.set("name", "fanweiqi");
		jedis.expire("name", 2);
		System.out.println(jedis.get("name"));
		Thread.sleep(3000);
		System.out.println(jedis.get("name"));
	}
	
	
	
	public static void main(String[] args) {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxActive(10);
		jedisPoolConfig.setMaxActive(5);
		jedisPoolConfig.setMaxWait(3000);
		
		JedisPool jedisPool = new JedisPool(jedisPoolConfig,"localhost",1111);
		 
		Jedis j1 = jedisPool.getResource();
		Jedis j2 = jedisPool.getResource();
		for (int i = 0; i < 100; i++) {
			Long incrBy = j1.incrBy("hello",1);
			System.out.println(incrBy);
		}
		
		jedisPool.returnResource(j1);
		jedisPool.destroy();
	}
	
	
	
		
}
