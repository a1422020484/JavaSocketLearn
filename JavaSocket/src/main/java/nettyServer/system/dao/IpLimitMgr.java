package nettyServer.system.dao;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class IpLimitMgr {
	//黑名单
	private Set<String> blackSet = new HashSet<String>();
	//白名单
	private Set<String> whiteSet = new HashSet<String>();
	
	private static IpLimitMgr instance = new IpLimitMgr();
	
	private ReadWriteLock blackLock = new ReentrantReadWriteLock();
	
	private ReadWriteLock whiteLock = new ReentrantReadWriteLock();
	
	public static IpLimitMgr getInstance(){
		return instance;
	}
	//黑名单部分
	public Set<String> getBlackSet() {
		blackLock.readLock().lock();
		try{
			return new HashSet<String>(blackSet);
		} finally {
			blackLock.readLock().unlock();
		}
	}

	public void addBlackSet(String ip) {
		blackLock.writeLock().lock();
		try{
			blackSet.add(ip);
		} finally {
			blackLock.writeLock().unlock();
		}
	}
	
	public void subBlackSet(String ip) {
		blackLock.writeLock().lock();
		try{
			blackSet.remove(ip);
		} finally {
			blackLock.writeLock().unlock();
		}
	}

	//白名单部分
	public Set<String> getWhiteSet() {
		whiteLock.readLock().lock();
		try{
			return new HashSet<String>(whiteSet);
		} finally {
			whiteLock.readLock().unlock();
		}
	}

	public void addWhiteSet(String ip) {
		whiteLock.writeLock().lock();
		try{
			whiteSet.add(ip);
		} finally {
			whiteLock.writeLock().unlock();
		}
	}
	
	public void subWhiteSet(String ip) {
		whiteLock.writeLock().lock();
		try{
			whiteSet.remove(ip);
		} finally {
			whiteLock.writeLock().unlock();
		}
	}

	public static void setInstance(IpLimitMgr instance) {
		IpLimitMgr.instance = instance;
	}
	public void setWhiteSet(Set<String> whiteSet) {
		this.whiteSet = whiteSet;
	}
	public void setBlackSet(Set<String> blackSet) {
		this.blackSet = blackSet;
	}
}
