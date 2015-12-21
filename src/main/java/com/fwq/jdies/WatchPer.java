package com.fwq.jdies;

import java.util.Date;


public class WatchPer {

	private long startTime;
	private long endTime;
	public WatchPer(Date startTime){
		this.startTime=startTime.getTime();
	}
	
	public String useTime(Date endTime)
	{
		this.endTime=endTime.getTime();
		return calculateTime();
	}
	
	private String calculateTime(){
		long a=this.endTime-this.startTime;
		double s=a/1000.00;
		return s+"";
	}
	
	
	
}
