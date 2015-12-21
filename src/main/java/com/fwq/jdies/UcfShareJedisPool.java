package com.fwq.jdies;


import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class UcfShareJedisPool {

	private List<JedisShardInfo> list=new ArrayList<JedisShardInfo>();
	
	private JedisPoolConfig jedisPoolConfig;
	
	private  ShardedJedisPool shardedJedisPool;
	
	public void addJedisSharedInfo(JedisShardInfo jedisShardInfo)
	{
		this.list.add(jedisShardInfo);
	}
	
	public ShardedJedis getJedis()
	{
		return this.shardedJedisPool.getResource();
	}
	
	public void returnToShareJedisFactory(ShardedJedis shardedJedis)
	{
		this.shardedJedisPool.returnResource(shardedJedis);
	}
	
}
