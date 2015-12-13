package com.fwq.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** 
 * 理解  hash
 * 哈希通常是将  一些格式不同的值 转换成 位数，格式相同的值，以便于查找。提高效率
 * @author FWQ
 *
 */
public class HashTest {

	/**
	 * @throws NoSuchAlgorithmException
	 * 常见的用md5  加密的例子。
	 */
	@org.junit.Test
	public void md5() throws NoSuchAlgorithmException
	{
		StringBuilder sb = new StringBuilder();
		MessageDigest md = MessageDigest.getInstance("md5");
		md.update(" ".getBytes());
		//digetst方法返回md5加密过的长度为16的字节数组。
		byte[] result = md.digest();
		for (int i = 0; i < result.length; i++) {
			sb.append(Integer.toHexString(result[i]&0xFF));
		}
		System.out.println(sb);
	}
	
	public static void main(String[] args) {
		System.out.println(1+2+4+8+16+32+64);
		String value = Integer.toBinaryString(127);
		while(value.length()<8)
			value="0"+value;
		
		System.out.println(value);
	}
}
