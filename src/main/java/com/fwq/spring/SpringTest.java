package com.fwq.spring;

import java.util.concurrent.TimeUnit;

import org.junit.runner.RunWith;

public class SpringTest {

	 private static boolean stopRequested;

	   public static void main(String[] args) throws InterruptedException {
	      Thread backgroundThread = new Thread(new Runnable() {
	         public void run() {
	            int i = 0;
	            while (!stopRequested){
	               i++;
	               //这段System.out语句会导致线程结束，原因？
	               System.out.println(i);
	            }
	         }
	      });
	      backgroundThread.start();
	      TimeUnit.SECONDS.sleep(2);
	      stopRequested = true;
	   }
	
	
	
}
