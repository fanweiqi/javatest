package com.fwq.jdies.lock;

import redis.clients.jedis.JedisPool;

public class TestJedisLock {
	
	
	public static void main(String[] args) {
		TestJedisLock testJedisLock = new TestJedisLock();
		for (int i = 0; i < 2; i++) {
			testJedisLock.startT();
		}
	}
	
	
	private JedisLock lock=new JedisLock(getJedisPool());
	private int publicValue=0;

	public void startT(){
			new Thread(){
				public void run() {
							incr();
						System.out.println(TestJedisLock.this.publicValue);
				}
			}.start();
	}
	
	public void  incr(){
		if(this.lock.getLock("test")){
			this.publicValue++;
			this.lock.unLock("test", false);
		}
		
	}
	
	public JedisPool getJedisPool(){
		return new JedisPool("localhost",1111);
	}
	
}
