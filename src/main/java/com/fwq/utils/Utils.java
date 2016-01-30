package com.fwq.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

public class Utils {

	/**
	 * 生成随机密码，包括所有可显示字符
	 * @return 
	 */
	public static String generateDisplayChar(int lenth){
		StringBuilder sb = new StringBuilder();
		int minChar=33;
		int maxChar=126;
		Random random = new  Random(System.nanoTime());
		for (int i = 0; i < lenth; i++) {
			char c=(char)(random.nextInt(maxChar-minChar)+minChar);
			sb.append(c);
		}
		
		return sb.toString();
	}
	
	
	 /**
	 * @param len
	 * @return
	 */
	public static String generateEncryptKey(int lenth) {
	    	char[] HEXCHAR = {'0', '1', '2', '3', '4', '5', '6', '7','8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	        String str = "";
	        Random rander = new Random(System.nanoTime());
	        for(int i=0; i<lenth; i++) {
	            str+= HEXCHAR[rander.nextInt(16)];
	        }
	        return str;
	    }
	
	
	
		/**
		 * @param e  
		 * @return
		 * 返回String类型的异常栈信息
		 */
		public static String exceptionStack2Str(Exception e) {
			StringWriter sw=null;//字符流不用关闭
			PrintWriter pw=null;
			try {
				sw = new StringWriter();
				pw = new PrintWriter(sw);
				e.printStackTrace(pw);
			} catch (Exception e1) {
				return e1.getMessage();
			}finally{
					if(pw!=null)
						pw.close();
			}
			return sw.toString();
		}
		
		
		
		public static String sendHttp(String requestAddr,String requestMethond){
			String charset="UTF-8";
			String result="";
			try {
				  URL url = null;
				  URLConnection connection=null;
		          
					if ("get".equals(requestMethond)) {
						url = new URL(requestAddr);
						connection = url.openConnection();
					} else if ("post".equals(requestMethond)) {
						url = new URL(requestAddr.split("\\?")[0]);
						connection = url.openConnection();
						connection.setDoOutput(true);
						OutputStream outputStream = connection.getOutputStream();
						outputStream.write(requestAddr.split("\\?")[1].getBytes(charset));
						outputStream.flush();
					} else {
						throw new IllegalArgumentException("requestMethond 参数异常");
					}
				  connection.setRequestProperty("accept", "*/*");
		          connection.setRequestProperty("connection", "Keep-Alive");
		          connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		          InputStream  inputStream= connection.getInputStream();
		          ByteArrayOutputStream bos = new ByteArrayOutputStream();
		          int b=0;
		          while((b=inputStream.read())!=-1){
		        	  bos.write(b);
		          }
				result = bos.toString(charset);
			} catch (Exception e) {
				result=Utils.exceptionStack2Str(e);
			}
			return result;
		}
}
