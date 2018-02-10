package nettyServer.system.dao;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 
 * @author 王亮亮   2017.5
 *
 * @param <M>
 */
public class Entity {

	//状态位,0为未更改,1为已更改
	private volatile byte state = 0;
	//上次查询时间
	private volatile int lastSelectTime = 0;
	
	private final ReadWriteLock rwlock = new ReentrantReadWriteLock();
	
	public boolean isNotify(){
		return state == 1;
	}
	
	public void notifyData() {
		if(this.state != 1){
			this.state = 1;
		}
	}
	
	protected void reset(){
		if(this.state != 0){
			this.state = 0;
		}
	}

	protected int getLastSelectTime() {
		return lastSelectTime;
	}

	protected void setLastSelectTime(int lastSelectTime) {
		rwlock.readLock().lock();
		try{
			this.lastSelectTime = lastSelectTime;
		} finally {
			rwlock.readLock().unlock();
		}
	}

	protected ReadWriteLock getRwlock() {
		return rwlock;
	}
	
}
