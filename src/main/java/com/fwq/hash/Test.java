package com.fwq.hash;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Test {
public static void main(String[] args) throws NoSuchAlgorithmException {
	
	Map ma=new HashMap();
	
	ma.put("name", "fanweiqi");
//	MessageDigest md = MessageDigest.getInstance("MD5"); 
//	String str1="12321dffd";
//	String str2="fdsaf";
//	
//	md.update(str1.getBytes());
//	
//	byte[] digest = md.digest();
//	
//	StringBuilder sb = new StringBuilder();
//	for (byte b : digest) {
//		sb.append(Integer.toHexString(b&0xFF));
//	}
//	System.out.println(sb);
	
//	for (int i = 0; i < 100; i++) {
//		startT();
//	}
}

public static  long RSHash(String str)
{
   int b     = 378551;
   int a     = 63689;
   long hash = 0;
   for(int i = 0; i < str.length(); i++)
   {
      hash = hash * a + str.charAt(i);
      a    = a * b;
   }
   return hash;
}

public static void startT()
{
	new Thread(){
		@Override
		public void run() {
			byte[] b=new byte[1024*100];
			while(true)
			{}
		}
	}.start();
}

}
