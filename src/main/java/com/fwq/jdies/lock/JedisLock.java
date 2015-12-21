package com.fwq.jdies.lock;

import java.sql.Time;

import javax.print.attribute.standard.MediaSize.ISO;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author fwq
 * @date 2015-12-21 下午5:44:48
 * @Description: 
 * 用jedis来实现锁功能
 */
public class JedisLock {
	 
	   private static  String DEFAULT_VALUE="TRUE";

		private static  int DEFALULT_TIME_OUT=3;//秒
		
		private static  int DEFALUT_EXPIRE=180;//秒
		
		private JedisPool JedisPool;
		
		private Jedis jedis;

		private boolean isLock=true;

		
		public JedisLock(JedisPool jedisPool){
			this.JedisPool=jedisPool;
			this.jedis=jedisPool.getResource();
		}
		
		public boolean getLock(String key){
			return getLock(key, DEFALULT_TIME_OUT);
		}
		/**
		 * @param key
		 * @param timeOut
		 * @return
		 * 获取锁时，先setnx，根据返回值判断是否已经存在该key，
		 * 如果返回0表示已存在，循环等待获取，等待时间3秒（默认），直到超时
		 *如果返回1表示该key不存在redis中，返回true，表示已获取锁。
		 */
	public boolean getLock(String key, int timeOut) {
		long nanoTime = System.nanoTime();
		long nanoTimeOut = timeOut * 1000 * 1000;

		while ((System.nanoTime() - nanoTime) < nanoTimeOut) {

			if (jedis.setnx(key, DEFAULT_VALUE) == 1) {
				jedis.expire(key, DEFALUT_EXPIRE);
				isLock = true;
			} else {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					isLock = false;
				}
				isLock=false;
			}
		}
		return isLock;
	}
		
		
	
		/**
		 * @param key 
		 * @param closeJedis 是否还使用该JedisLock对象
		 *  如果本对象已经获取到锁，删除该key
		 */
		public void unLock(String key,boolean closeJedis){
			if(isLock){
				jedis.del(key);
				isLock=false;
			}
			if(closeJedis)
				JedisPool.returnResource(jedis);
		}
	
}
