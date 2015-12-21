package com.fwq.jdies;

import java.util.logging.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author fwq
 * @date 2015-12-17 下午3:47:45
 * @Description: 对jedisPool对象进行封装，通常保证在项目中只有一个pool对象
 * 
 */
public class UcfJedisPool {
	public static final Logger logger = Logger.getLogger(UcfJedisPool.class
			.getName());

	private static JedisPool jedisPool;// 池对象

	private JedisPoolConfig jedisPoolConfig;// 池配置对象，

	private String host;// redies 服务端主机 默认

	private int port;// redies 服务器端端口号 默认

	public UcfJedisPool() {
		this(new JedisPoolConfig());
	}

	public UcfJedisPool(JedisPoolConfig jedisPoolConfig) {
		this(jedisPoolConfig, "127.0.0.1", 1111);
	}

	public UcfJedisPool(JedisPoolConfig jedisPoolConfig, String host, int port) {
		this.jedisPoolConfig = jedisPoolConfig;
		this.host = host;
		this.port = port;

	}

	/**
	 * @author FWQ
	 * @date 2015-12-17 下午3:55:12
	 * @Description: 初始化jedisPool对象
	 */
	public void init() {
		if (jedisPool == null) {
			synchronized (UcfJedisPool.class) {
				if (jedisPool == null) {
					jedisPool = new JedisPool(jedisPoolConfig, host, port);
					System.out.println("init----");
				}
			}
		}
	}

	public Jedis getJedisInstance() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
		} catch (Exception e) {
			logger.info("获取资源异常");
		}
		return jedis;
	}

	public void returnJedis(Jedis jedis) {
		jedisPool.returnResource(jedis);
	}

	public void closePool() {
		jedisPool.destroy();
		jedisPool = null;
	}

}