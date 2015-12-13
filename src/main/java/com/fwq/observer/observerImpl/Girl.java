package com.fwq.observer.observerImpl;

import java.util.LinkedList;
import java.util.List;

import com.fwq.observer.observerInterface.Watched;
import com.fwq.observer.observerInterface.Watcher;

public class Girl implements Watched {
	
	/**
	 * 自己的观察者(关注者)集合，所有关注自己的人，都会被放到该集合中，
	 * 有了动态，则发给到每个关注自己的对象
	 */
	private List<Watcher> watchers=new LinkedList<Watcher>();

	public void addWatcher(Watcher watcher) {
		watchers.add(watcher);
	}

	public void removeWacher(Watcher watcher) {
		watchers.remove(watcher);
		
	}
	public void notifyAllWatcher(String happend) {
		for (Watcher watcher : watchers) {
			watcher.receiveModify(happend);
		}
	}


	

}
