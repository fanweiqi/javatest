package com.fwq.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;

public class UtilsTest {

	
	@Test
	public void testSendPost(){
		String url="http://www.sf-express.com/sf-service-web/service/bills/976455293179/routes?app=bill&lang=sc&region=cn&translate=";
		String sendGet = Utils.sendHttp(url,"get");
		
		System.out.println(sendGet);
	}
	
}
