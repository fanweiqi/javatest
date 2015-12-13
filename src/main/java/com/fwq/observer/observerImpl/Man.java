package com.fwq.observer.observerImpl;

import com.fwq.observer.observerInterface.Watcher;

/**
 * @author FWQ
 *	创建  man 类，来观察 girl 类
 */
public class Man implements Watcher {
	private String name;
	
	public Man(String name){
		this.name=name;
	}
	
	public void receiveModify(String event) {
		System.out.println("我是："+name+",我听说她现在："+event);
	}

}
