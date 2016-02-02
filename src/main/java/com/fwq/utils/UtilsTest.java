package com.fwq.utils;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void testSendPost(){
		
		byte [] bs=new byte[]{99,99};
		System.out.println(new String(bs));
		System.out.println(System.getProperty("java.ext.dirs"));
		
		String url="http://1.163.com/user/duobaoRecord/get.do?pageNum=1&pageSize=100&totalCnt=0&cid=70206513";
		String sendGet = Utils.sendHttp(url,"get");
		System.out.println(sendGet);
//		System.out.println(sendGet);
		
		
	}
	
}
