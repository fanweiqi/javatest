package com.fwq.hash;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Test {
public static void main(String[] args) throws NoSuchAlgorithmException {
	
	String str="OSuTzyS29be1apdzbUxRtQIvTP07r3hV";
	String md5 = Test.md5(str);
	System.out.println(md5);
	
}


public static void  md5Test() throws NoSuchAlgorithmException
{
	MessageDigest md = MessageDigest.getInstance("MD5"); 
	String str1="12321dffd";
	String str2="fdsaf";
	
	md.update(str1.getBytes());
	
	byte[] digest = md.digest();
	
	StringBuilder sb = new StringBuilder();
	for (byte b : digest) {
		sb.append(Integer.toHexString(b&0xFF));
	}
	System.out.println(sb);
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







public static String md5(String str) {

    if (str == null) {
        return null;
    }

    MessageDigest messageDigest = null;

    try {
        messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        messageDigest.update(str.getBytes("UTF-8"));
    } catch (NoSuchAlgorithmException e) {

        return str;
    } catch (UnsupportedEncodingException e) {
        return str;
    }

    byte[] byteArray = messageDigest.digest();

    StringBuffer md5StrBuff = new StringBuffer();

    for (int i = 0; i < byteArray.length; i++) {
        if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
            md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
        else
            md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
    }

    return md5StrBuff.toString();
}










}
