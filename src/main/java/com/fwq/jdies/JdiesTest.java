package com.fwq.jdies;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	Jedis jedis = new Jedis("localhost",1111);

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
	    for (int i = 0; i < 1; i++) {
	         jedis.set("n" + i, "n" + i);
	    }
	    for (int i = 0; i < 1; i++) {
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
	 * 测试jedis管道   10W读10W写，管道为普通的十倍性能
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
		
		jedis.set("name", "fanweiqi");
		jedis.expire("name", 2);
		System.out.println(jedis.get("name"));
		Thread.sleep(3000);
		System.out.println(jedis.get("name"));
	}
	
	/**
	 *  测试 setnx和 map
	 */
	@Test
	public void testSetnx()
	{
		Long setnx = jedis.setnx("name1", "fanweiqi");
		System.out.println(setnx);
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("k1", "v1");
		map.put("k2", "v2");
		map.put("k3", "v3");
		String hmset = jedis.hmset("map", map);
		System.out.println(hmset);
		List<String> hmget = jedis.hmget("map","k1","k2");
		System.out.println(hmget);
	}
	
	/**
	 *  测试 list  
	 */
	@Test
	public void testList1()
	{
		jedis.lpush("list", "list1");
		jedis.lpush("list", "list2");
		jedis.lpop("list");
		jedis.del("list");
		jedis.lpush("list", "list3");
		System.out.println(jedis.lrange("list", 0,1111));//lrange 来取值
		jedis.rpush("rpush", "rpush");
		System.out.println(jedis.rpop("rpush"));
	}
	
	@Test
	public void testWatch()
	{
		Jedis jedis = new Jedis("127.0.0.1",1111);
		String watch = jedis.watch("name");
		System.out.println(watch);
		String unwatch = jedis.unwatch();
		System.out.println(unwatch);
	}
	
	
	
	
	

    
    /**
     * redis存储字符串
     */
    @Test
    public void testString() {
        //-----添加数据----------  
        jedis.set("name","xinxin");//向key-->name中放入了value-->xinxin  
        System.out.println(jedis.get("name"));//执行结果：xinxin  
        
        jedis.append("name", " is my lover"); //拼接
        System.out.println(jedis.get("name")); 
        
        jedis.del("name");  //删除某个键
        System.out.println(jedis.get("name"));
        //设置多个键值对
        jedis.mset("name","liuling","age","23","qq","476777XXX");
        jedis.incr("age"); //进行加1操作
        System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-" + jedis.get("qq"));
        
    }
    
    /**
     * redis操作Map
     */
    @Test
    public void testMap() {
        //-----添加数据----------  
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "xinxin");
        map.put("age", "22");
        map.put("qq", "123456");
        jedis.hmset("user",map);
        //取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List  
        //第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数  
        List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
        System.out.println(rsmap);  
  
        //删除map中的某个键值  
        jedis.hdel("user","age");
        System.out.println(jedis.hmget("user", "age")); //因为删除了，所以返回的是null  
        System.out.println(jedis.hlen("user")); //返回key为user的键中存放的值的个数2 
        System.out.println(jedis.exists("user"));//是否存在key为user的记录 返回true  
        System.out.println(jedis.hkeys("user"));//返回map对象中的所有key  
        System.out.println(jedis.hvals("user"));//返回map对象中的所有value 
  
        Iterator<String> iter=jedis.hkeys("user").iterator();  
        while (iter.hasNext()){  
            String key = iter.next();  
            System.out.println(key+":"+jedis.hmget("user",key));  
        }  
    }
    
    /** 
     * jedis操作List 
     */  
    @Test  
    public void testList(){  
        //开始前，先移除所有的内容  
        jedis.del("java framework");  
        System.out.println(jedis.lrange("java framework",0,-1));  
        //先向key java framework中存放三条数据  
        jedis.lpush("java framework","spring");  
        jedis.lpush("java framework","struts");  
        jedis.lpush("java framework","hibernate");  
        //再取出所有数据jedis.lrange是按范围取出，  
        // 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有  
        System.out.println(jedis.lrange("java framework",0,-1));  
        
        jedis.del("java framework");
        jedis.rpush("java framework","spring");  
        jedis.rpush("java framework","struts");  
        jedis.rpush("java framework","hibernate"); 
        System.out.println(jedis.lrange("java framework",0,-1));
    }  
    
    /** 
     * jedis操作Set 
     */  
    @Test  
    public void testSet(){  
        //添加  
        jedis.sadd("user","liuling");  
        jedis.sadd("user","xinxin");  
        jedis.sadd("user","ling");  
        jedis.sadd("user","zhangxinxin");
        jedis.sadd("user","who");  
        //移除noname  
        jedis.srem("user","who");  
        System.out.println(jedis.smembers("user"));//获取所有加入的value  
        System.out.println(jedis.sismember("user", "who"));//判断 who 是否是user集合的元素  
        System.out.println(jedis.srandmember("user"));  
        System.out.println(jedis.scard("user"));//返回集合的元素个数  
    }  
  
    @Test  
    public void test() throws InterruptedException {  
        //jedis 排序  
        //注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）  
        jedis.del("a");//先清除数据，再加入数据进行测试  
        jedis.rpush("a", "1");  
        jedis.lpush("a","6");  
        jedis.lpush("a","3");  
        jedis.lpush("a","9");  
        System.out.println(jedis.lrange("a",0,-1));// [9, 3, 6, 1]  
        System.out.println(jedis.sort("a")); //[1, 3, 6, 9]  //输入排序后结果  
        System.out.println(jedis.lrange("a",0,-1));  
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
