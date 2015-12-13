package com.fwq.observer.observerInterface;

/**
 * @author FWQ
 * 被观察者，
 * 首先该接口需要一个容器来保存想要监听该被观察者对象的观察者对象。
 * 然后提供对该容器的增加  删除方法。
 * 最后提供对 该被观察者对象 的容器里面的所有提供观察者通知的方法。
 */
public interface Watched {

	void addWatcher(Watcher watcher);
	
	void removeWacher(Watcher watcher);
	
	void notifyAllWatcher(String happend);
}
