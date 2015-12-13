package com.fwq.observer.observerInterface;

/**
 * @author FWQ
 * 观察者接口，当发生事件时 收到通知
 */
public interface Watcher {

	/**
	 * @param event
	 * 当观察者观察的对象也就是被观察者发生改变时，触发此方法
	 */
	void receiveModify(String event);
		
}
