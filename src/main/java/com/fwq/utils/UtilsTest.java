package com.fwq.utils;

public class UtilsTest {

	
	public void testSendPost(){
		System.out.println(System.getProperty("java.ext.dirs"));
		
		String url="http://www.sf-express.com/sf-service-web/service/bills/976455293179/routes?app=bill&lang=sc&region=cn&translate=";
		String sendGet = Utils.sendHttp(url,"get");
		
		System.out.println(sendGet);
	}
	
}
