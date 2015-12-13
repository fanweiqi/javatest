package com.fwq.observer;

import com.fwq.observer.observerImpl.Girl;
import com.fwq.observer.observerImpl.Man;
import com.fwq.observer.observerInterface.Watched;
import com.fwq.observer.observerInterface.Watcher;

/**
 * @author FWQ
 * 测试类  ---  观察者模式observer
 * 当一个girl对象有调用notify方法时，所有在该girl对象的容器里面的
 * boy对象将会收到通知
 */
public class Test {

	public static void main(String[] args) {
		//创建 三个观察者对象
		Watcher boy0 = new Man("小明");
		Watcher boy1 = new Man("小强");
		Watcher boy2 = new Man("小王");
		//创建一个被观察者对象
		Watched girl0=new Girl();
		//将三个观察者对象添加到自己的 关注者容器 中
		girl0.addWatcher(boy0);
		girl0.addWatcher(boy1);
		girl0.addWatcher(boy2);
		//发送一条心情，则三个对象都会收到通知
		girl0.notifyAllWatcher("心情好");
		
		
	}
}
