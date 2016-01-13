package com.fwq.duobao;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test {

		public static void main(String[] args) throws IOException {
			String url="http://1.163.com/user/charge/get.do?pageSize=1000&pageNum=1&region=0&startTime=&endTime=&totalCnt=141&cid=37601355&t=1452595369244&token=18b75ddd-2394-417b-abd3-d598763e36a1";
			URL u = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) u.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream is = connection.getInputStream();
			
			String json="";
			int dataByte=0;
			byte[] b=new byte[100000];
			while((is.read(b))!=-1)
			{
				json=json+new String(b);
			}
			
			
			System.out.println(json);
			
		}
}
