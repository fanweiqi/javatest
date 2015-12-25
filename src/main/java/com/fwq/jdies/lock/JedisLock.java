package com.fwq.jdies.lock;

import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author fwq
 * @date 2015-12-21 下午5:44:48
 * @Description: 
 * 用jedis来实现锁功能
 * 
 * 锁的使用场景通常有两种情况：
 * ①：当发现该锁已经被占用时，不再进行操作，直接返回false，比如一个定时任务只需执行一次。
 * ②：当发现该锁已经被占用时，进入等待队列，循环获取锁，当获取到锁时，开始执行。
 * 
 * 
 */
public class JedisLock {
	 

		private static  int DEFALULT_TIME_OUT=3;//秒，获取锁的默认超时时间
		
		private static  int DEFALUT_EXPIRE=180;//秒，key的过期时间
		
		private static JedisPool JedisPool;
		
		private boolean isLock;

		
		public JedisLock(JedisPool jedisPool){
			if(JedisPool==null)
				JedisPool=jedisPool;
		}
		
		
		/**
		 * @param key
		 * @return 
		 */
		public boolean getLock(String key){
			return getLock(key, DEFALULT_TIME_OUT);
		}
		
		/**
		 * @param key
		 * @param timeOut
		 * @return 
		 */
		public boolean getLock (String key,int timeOut)
		{
			return getLock(key, timeOut, DEFALUT_EXPIRE);
		}
		/**
		 * 循环等待获取
		 * @param key
		 * @param timeOut
		 * @return
		 * 当前时间+超时时间=expireTime作为获取锁的最终时间也就是到了该最终时间还没有获取到锁，则返回false，
		 * while循环来获取锁，每次循环都将过期时间expireTime与当前时间做比较，如果expireTime小于了当前时间，则表示最终时间到了，返回false
		 * 否则，循环获取该锁。
		 */
	public boolean getLock(String key, int timeOut,int expireTime) {
		Jedis jedis= JedisPool.getResource();
		long nanoTime = System.nanoTime()+TimeUnit.SECONDS.toNanos(timeOut);
		try {
			while(nanoTime>System.nanoTime())
			{
				Long flag = jedis.setnx(key,String.valueOf(expireTime));
				if(flag==1)
				{
					jedis.expire(key, expireTime);
					isLock=true;
					return true;
				}else{
					isLock=false;
					try {
						TimeUnit.MILLISECONDS.sleep(500);
					} catch (InterruptedException e) {
						return false;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("getLock时异常："+e.getMessage());
		}finally{
			JedisPool.returnResource(jedis);
		}
		return isLock;
	}
		
	
	
	/**
	 * @param key
	 * @param timeOut
	 * @param dEFALUT_EXPIRE2
	 * @return 
	 * 不等待获取锁
	 */
	public boolean getLockNowait(String key)
	{
		return getLockNowait(key, DEFALUT_EXPIRE);
	}
	public boolean getLockNowait(String key,int expireTime) 
	{
		Jedis jedis = JedisPool.getResource();
		Long setnx=0L;
		try {
			setnx = jedis.setnx(key, key);
			if(setnx==1)
			{
				jedis.expire(key, expireTime);
				isLock=true;
				return isLock;
			}else{
				isLock=false;
				return isLock;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JedisPool.returnResource(jedis);
		}
		return setnx==1?true:false;
	}


	/**
	 * @param key
	 *            删除该key
	 */
	public void unLock(String key)
	{
		Jedis jedis = JedisPool.getResource();
		try {
			if (isLock){
				Long del = jedis.del(key);
				isLock = false;
			}
		} catch (Exception e) {
			System.err.println("释放锁"+key+"异常"+e);
		}finally{
			JedisPool.returnResource(jedis);
		}
		
	}
	
}
